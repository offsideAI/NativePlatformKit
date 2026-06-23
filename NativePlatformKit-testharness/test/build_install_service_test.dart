import 'dart:async';
import 'dart:io';

import 'package:flutter_test/flutter_test.dart';
import 'package:npk_test_harness/core/command_runner.dart';
import 'package:npk_test_harness/core/native_bridge.dart';
import 'package:npk_test_harness/core/process_event.dart';
import 'package:npk_test_harness/features/build_install/build_install_service.dart';
import 'package:path/path.dart' as p;

/// Records every startProcess call and emits exit 0, so command construction can be asserted.
class _RecordingBridge implements NativeBridge {
  final _controller = StreamController<ProcessEvent>.broadcast();
  final List<List<String>> calls = []; // [executable, ...arguments]

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
    calls.add([executable, ...arguments]);
    scheduleMicrotask(() {
      _controller.add(ProcessEvent(handle: handle, type: ProcessEventType.stdout, data: 'ok'));
      _controller.add(ProcessEvent(handle: handle, type: ProcessEventType.exit, exitCode: 0));
    });
    return handle;
  }

  @override
  Future<bool> cancelProcess(String handle) async => true;

  @override
  Future<String?> chooseDirectory({String? initialDirectory}) async => null;

  @override
  Future<String?> chooseFile({String? initialDirectory, List<String>? allowedExtensions}) async => null;

  void dispose() => _controller.close();
}

void main() {
  late _RecordingBridge bridge;
  late BuildInstallService service;

  setUp(() {
    bridge = _RecordingBridge();
    service = BuildInstallService(CommandRunner(bridge), sdkRoot: '/opt/android-sdk');
  });

  tearDown(() => bridge.dispose());

  test('launch component uses applicationId (not namespace) + fully-qualified activity', () {
    expect(
      service.launchComponent,
      'ai.offside.mobile.android.testlabs/ai.offside.mobile.android.helper.testlabs.nav.TestlabsMainActivity',
    );
  });

  test('launch issues the correct adb am start command', () async {
    await service.launch('emulator-5554');
    expect(bridge.calls.single, [
      '/opt/android-sdk/platform-tools/adb',
      '-s', 'emulator-5554',
      'shell', 'am', 'start', '-n', service.launchComponent,
    ]);
  });

  test('install issues adb install -r with the apk path', () async {
    await service.install(serial: 'emulator-5554', apkPath: '/tmp/app.apk').toList();
    expect(bridge.calls.single, [
      '/opt/android-sdk/platform-tools/adb',
      '-s', 'emulator-5554',
      'install', '-r', '/tmp/app.apk',
    ]);
  });

  test('findApk returns the newest .apk or null', () {
    final tmp = Directory.systemTemp.createTempSync('npk_apk_');
    try {
      expect(service.findApk(tmp.path), isNull);
      final debugDir = Directory(p.join(tmp.path, 'app', 'build', 'outputs', 'apk', 'debug'))
        ..createSync(recursive: true);
      File(p.join(debugDir.path, 'app-debug.apk')).writeAsStringSync('x');
      expect(service.findApk(tmp.path), endsWith('app-debug.apk'));
    } finally {
      tmp.deleteSync(recursive: true);
    }
  });
}
