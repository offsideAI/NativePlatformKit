import 'dart:async';

import 'package:flutter_test/flutter_test.dart';
import 'package:npk_test_harness/core/command_runner.dart';
import 'package:npk_test_harness/core/native_bridge.dart';
import 'package:npk_test_harness/core/process_event.dart';

/// A scriptable [NativeBridge] fake: each started process emits the events queued for its
/// executable, routed by the Dart-generated handle.
class FakeNativeBridge implements NativeBridge {
  final _controller = StreamController<ProcessEvent>.broadcast();
  final List<String> startedExecutables = [];
  final List<String> cancelledHandles = [];

  /// Per-executable scripted events (handle is filled in at start time).
  final Map<String, List<ProcessEvent> Function(String handle)> scripts = {};

  @override
  Stream<ProcessEvent> get events => _controller.stream;

  @override
  Future<String> startProcess({
    required String handle,
    required String executable,
    required List<String> arguments,
    String? workingDirectory,
    Map<String, String>? environment,
  }) async {
    startedExecutables.add(executable);
    final script = scripts[executable];
    if (script != null) {
      // Emit after the caller has subscribed.
      scheduleMicrotask(() {
        for (final e in script(handle)) {
          _controller.add(e);
        }
      });
    }
    return handle;
  }

  @override
  Future<bool> cancelProcess(String handle) async {
    cancelledHandles.add(handle);
    return true;
  }

  @override
  Future<String?> chooseDirectory({String? initialDirectory}) async => '/tmp/picked';

  @override
  Future<String?> chooseFile({String? initialDirectory, List<String>? allowedExtensions}) async =>
      '/tmp/picked.txt';

  void dispose() => _controller.close();
}

void main() {
  late FakeNativeBridge bridge;
  late CommandRunner runner;

  setUp(() {
    bridge = FakeNativeBridge();
    runner = CommandRunner(bridge);
  });

  tearDown(() => bridge.dispose());

  test('run() collects stdout and exit code', () async {
    bridge.scripts['/bin/echo'] = (h) => [
          ProcessEvent(handle: h, type: ProcessEventType.stdout, data: 'hello '),
          ProcessEvent(handle: h, type: ProcessEventType.stdout, data: 'world'),
          ProcessEvent(handle: h, type: ProcessEventType.exit, exitCode: 0),
        ];

    final result = await runner.run('/bin/echo', ['hello']);

    expect(result.success, isTrue);
    expect(result.exitCode, 0);
    expect(result.stdout, 'hello world');
    expect(bridge.startedExecutables, ['/bin/echo']);
  });

  test('run() captures stderr and a non-zero exit code', () async {
    bridge.scripts['/bin/false'] = (h) => [
          ProcessEvent(handle: h, type: ProcessEventType.stderr, data: 'boom'),
          ProcessEvent(handle: h, type: ProcessEventType.exit, exitCode: 1),
        ];

    final result = await runner.run('/bin/false', const []);

    expect(result.success, isFalse);
    expect(result.exitCode, 1);
    expect(result.stderr, 'boom');
  });

  test('stream() forwards events in order and closes on exit', () async {
    bridge.scripts['/bin/echo'] = (h) => [
          ProcessEvent(handle: h, type: ProcessEventType.stdout, data: 'a'),
          ProcessEvent(handle: h, type: ProcessEventType.exit, exitCode: 0),
        ];

    final events = await runner.stream('/bin/echo', ['a']).toList();

    expect(events.map((e) => e.type), [ProcessEventType.stdout, ProcessEventType.exit]);
  });

  test('run() with timeout cancels the process and throws', () async {
    // Script never emits an exit event → should time out and cancel.
    bridge.scripts['/bin/sleep'] = (h) => [
          ProcessEvent(handle: h, type: ProcessEventType.stdout, data: 'tick'),
        ];

    await expectLater(
      runner.run('/bin/sleep', ['30'], timeout: const Duration(milliseconds: 50)),
      throwsA(isA<TimeoutException>()),
    );
    expect(bridge.cancelledHandles, isNotEmpty);
  });
}
