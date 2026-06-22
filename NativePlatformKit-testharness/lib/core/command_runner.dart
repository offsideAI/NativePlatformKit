import 'dart:async';

import 'native_bridge.dart';
import 'process_event.dart';

/// The result of a completed command.
class CommandResult {
  /// Creates a command result.
  const CommandResult({
    required this.exitCode,
    required this.stdout,
    required this.stderr,
  });

  /// Process exit code (`-1` if unknown).
  final int exitCode;

  /// Accumulated standard output.
  final String stdout;

  /// Accumulated standard error.
  final String stderr;

  /// Whether the command exited successfully (code 0).
  bool get success => exitCode == 0;
}

/// High-level runner over a [NativeBridge]: generates process handles, streams events for a single
/// invocation, and offers a convenience [run] that collects output to completion.
class CommandRunner {
  /// Creates a runner over [_bridge].
  CommandRunner(this._bridge);

  final NativeBridge _bridge;
  int _counter = 0;

  String _newHandle() => 'p${DateTime.now().microsecondsSinceEpoch}_${_counter++}';

  /// Starts [executable] and returns a single-subscription stream of its events, completing when
  /// the process exits. The generated handle is reported via [onHandle] (useful for cancellation).
  Stream<ProcessEvent> stream(
    String executable,
    List<String> arguments, {
    String? workingDirectory,
    Map<String, String>? environment,
    void Function(String handle)? onHandle,
  }) {
    final handle = _newHandle();
    final controller = StreamController<ProcessEvent>();

    // Subscribe to this handle's events BEFORE starting the process (no lost events).
    final sub = _bridge.events.where((e) => e.handle == handle).listen(
      (event) {
        controller.add(event);
        if (event.isExit) unawaited(controller.close());
      },
      onError: controller.addError,
    );
    controller.onCancel = sub.cancel;

    _bridge
        .startProcess(
          handle: handle,
          executable: executable,
          arguments: arguments,
          workingDirectory: workingDirectory,
          environment: environment,
        )
        .then((_) => onHandle?.call(handle))
        .catchError((Object error, StackTrace stackTrace) {
      controller.addError(error, stackTrace);
      unawaited(controller.close());
    });

    return controller.stream;
  }

  /// Runs [executable] to completion, accumulating output. If [timeout] elapses the process is
  /// cancelled and a [TimeoutException] is thrown.
  Future<CommandResult> run(
    String executable,
    List<String> arguments, {
    String? workingDirectory,
    Map<String, String>? environment,
    Duration? timeout,
  }) async {
    final out = StringBuffer();
    final err = StringBuffer();
    final completer = Completer<CommandResult>();
    String? handle;
    Timer? timer;

    final sub = stream(
      executable,
      arguments,
      workingDirectory: workingDirectory,
      environment: environment,
      onHandle: (h) => handle = h,
    ).listen(
      (event) {
        switch (event.type) {
          case ProcessEventType.stdout:
            out.write(event.data ?? '');
          case ProcessEventType.stderr:
            err.write(event.data ?? '');
          case ProcessEventType.exit:
            timer?.cancel();
            if (!completer.isCompleted) {
              completer.complete(CommandResult(
                exitCode: event.exitCode ?? -1,
                stdout: out.toString(),
                stderr: err.toString(),
              ));
            }
          case ProcessEventType.unknown:
            break;
        }
      },
      onError: (Object error, StackTrace stackTrace) {
        timer?.cancel();
        if (!completer.isCompleted) completer.completeError(error, stackTrace);
      },
    );

    if (timeout != null) {
      timer = Timer(timeout, () {
        final h = handle;
        if (h != null) unawaited(_bridge.cancelProcess(h));
        if (!completer.isCompleted) {
          completer.completeError(TimeoutException('Command timed out', timeout));
        }
      });
    }

    try {
      return await completer.future;
    } finally {
      timer?.cancel();
      await sub.cancel();
    }
  }

  /// Cancels the process identified by [handle].
  Future<bool> cancel(String handle) => _bridge.cancelProcess(handle);
}
