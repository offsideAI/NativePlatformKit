import 'dart:async';
import 'dart:io' show Platform;

import 'package:flutter/foundation.dart';

import '../../core/command_runner.dart';
import '../../core/native_bridge.dart';
import '../../core/process_event.dart';

/// The origin/styling of a console line.
enum ConsoleLineKind {
  /// Standard output.
  stdout,

  /// Standard error.
  stderr,

  /// Harness-emitted informational line (command echo, exit code, notices).
  info,
}

/// A single rendered console line.
class ConsoleLine {
  /// Creates a console line.
  const ConsoleLine(this.text, this.kind);

  /// The line text.
  final String text;

  /// The line kind.
  final ConsoleLineKind kind;
}

/// View model for the Developer Console: runs commands through the [CommandRunner], streams their
/// output into a capped line buffer, and exposes cancel + native directory-picker actions. Doubles
/// as the M1 demonstration of the native bridge.
class ConsoleViewModel extends ChangeNotifier {
  /// Creates the console view model.
  ConsoleViewModel(this._runner, this._bridge);

  final CommandRunner _runner;
  final NativeBridge _bridge;

  static const int _maxLines = 2000;

  final List<ConsoleLine> _lines = [];

  /// The current console buffer (newest last).
  List<ConsoleLine> get lines => List.unmodifiable(_lines);

  bool _open = false;

  /// Whether the console drawer is visible.
  bool get isOpen => _open;

  bool _running = false;

  /// Whether a command is currently running.
  bool get isRunning => _running;

  String? _handle;
  StreamSubscription<ProcessEvent>? _sub;

  /// Toggles the console drawer.
  void toggle() {
    _open = !_open;
    notifyListeners();
  }

  /// Clears the console buffer.
  void clear() {
    _lines.clear();
    notifyListeners();
  }

  /// Runs [executable] with [arguments], streaming output into the buffer.
  Future<void> runCommand(String executable, List<String> arguments) async {
    if (_running) return;
    _running = true;
    _handle = null;
    _append('\$ $executable ${arguments.join(' ')}', ConsoleLineKind.info);

    _sub = _runner.stream(executable, arguments, onHandle: (h) => _handle = h).listen(
      (event) {
        switch (event.type) {
          case ProcessEventType.stdout:
            if (event.data != null) _append(event.data!, ConsoleLineKind.stdout);
          case ProcessEventType.stderr:
            if (event.data != null) _append(event.data!, ConsoleLineKind.stderr);
          case ProcessEventType.exit:
            _append('— exited with code ${event.exitCode}', ConsoleLineKind.info);
            _finish();
          case ProcessEventType.unknown:
            break;
        }
      },
      onError: (Object error, StackTrace _) {
        _append('error: $error', ConsoleLineKind.stderr);
        _finish();
      },
    );
  }

  /// Requests cancellation of the running command.
  Future<void> cancel() async {
    final handle = _handle;
    if (handle == null) return;
    _append('— cancel requested', ConsoleLineKind.info);
    await _runner.cancel(handle);
  }

  /// Opens a native directory picker and logs the selection.
  Future<void> pickDirectory() async {
    final path = await _bridge.chooseDirectory();
    _append(
      path == null ? '— directory selection cancelled' : 'selected directory: $path',
      ConsoleLineKind.info,
    );
  }

  // --- M1 bridge demo quick-actions ---

  /// Echoes a string (proves live streaming).
  Future<void> runEcho() => runCommand('/bin/echo', ['NativePlatformKit bridge OK']);

  /// Sleeps for 30s (use with [cancel] to prove cancellation).
  Future<void> runSleep() => runCommand('/bin/sleep', ['30']);

  /// Runs `adb version` from the detected SDK (proves a real tool invocation).
  Future<void> runAdbVersion() {
    final env = Platform.environment;
    final home = env['ANDROID_HOME'] ??
        env['ANDROID_SDK_ROOT'] ??
        '${env['HOME']}/Library/Android/sdk';
    return runCommand('$home/platform-tools/adb', ['version']);
  }

  void _append(String text, ConsoleLineKind kind) {
    for (final part in text.split('\n')) {
      if (part.isEmpty && kind != ConsoleLineKind.info) continue;
      _lines.add(ConsoleLine(part, kind));
    }
    if (_lines.length > _maxLines) {
      _lines.removeRange(0, _lines.length - _maxLines);
    }
    notifyListeners();
  }

  void _finish() {
    _running = false;
    _handle = null;
    _sub?.cancel();
    _sub = null;
    notifyListeners();
  }

  @override
  void dispose() {
    _sub?.cancel();
    super.dispose();
  }
}
