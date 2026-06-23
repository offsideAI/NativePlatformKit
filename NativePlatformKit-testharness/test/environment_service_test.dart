import 'dart:async';
import 'dart:io';

import 'package:flutter_test/flutter_test.dart';
import 'package:npk_test_harness/core/command_runner.dart';
import 'package:npk_test_harness/core/native_bridge.dart';
import 'package:npk_test_harness/core/process_event.dart';
import 'package:npk_test_harness/features/environment/environment_service.dart';
import 'package:path/path.dart' as p;

/// A bridge that returns a version line + exit 0 for any process (so version probes succeed).
class _EchoBridge implements NativeBridge {
  final _controller = StreamController<ProcessEvent>.broadcast();

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
    scheduleMicrotask(() {
      _controller.add(ProcessEvent(handle: handle, type: ProcessEventType.stdout, data: 'version 1.0'));
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

void _touch(String path) {
  final file = File(path);
  file.parent.createSync(recursive: true);
  file.writeAsStringSync('#!/bin/sh\n');
}

void main() {
  late Directory tmp;
  late _EchoBridge bridge;
  late CommandRunner runner;

  setUp(() {
    tmp = Directory.systemTemp.createTempSync('npk_sdk_');
    bridge = _EchoBridge();
    runner = CommandRunner(bridge);
  });

  tearDown(() {
    bridge.dispose();
    tmp.deleteSync(recursive: true);
  });

  EnvironmentService service() => EnvironmentService(
        runner,
        sdkRoot: tmp.path,
        environment: {'HOME': tmp.path},
      );

  test('empty SDK → components missing, cannot create emulator', () async {
    final report = await service().detect();
    expect(report.hasAdb, isFalse);
    expect(report.hasEmulator, isFalse);
    expect(report.hasCmdlineTools, isFalse);
    expect(report.hasSystemImage, isFalse);
    expect(report.canCreateEmulator, isFalse);
    expect(report.sdkRoot, tmp.path);
  });

  test('fully provisioned SDK → canCreateEmulator true', () async {
    _touch(p.join(tmp.path, 'platform-tools', 'adb'));
    _touch(p.join(tmp.path, 'emulator', 'emulator'));
    _touch(p.join(tmp.path, 'cmdline-tools', 'latest', 'bin', 'sdkmanager'));
    _touch(p.join(tmp.path, 'cmdline-tools', 'latest', 'bin', 'avdmanager'));
    Directory(p.join(tmp.path, 'system-images', 'android-34', 'google_apis', 'arm64-v8a'))
        .createSync(recursive: true);

    final report = await service().detect();
    expect(report.hasAdb, isTrue);
    expect(report.hasEmulator, isTrue);
    expect(report.hasCmdlineTools, isTrue);
    expect(report.hasSystemImage, isTrue);
    expect(report.canCreateEmulator, isTrue);
    expect(report.tool('adb')!.version, isNotNull); // probed via the echo bridge
  });

  test('detects command-line tools under a versioned dir (not just latest)', () async {
    _touch(p.join(tmp.path, 'cmdline-tools', '13.0', 'bin', 'sdkmanager'));
    _touch(p.join(tmp.path, 'cmdline-tools', '13.0', 'bin', 'avdmanager'));

    final report = await service().detect();
    expect(report.hasCmdlineTools, isTrue);
  });

  test('installCmdlineTools runs a shell pipeline to completion', () async {
    final events = await service().installCmdlineTools().toList();
    expect(events.any((e) => e.isExit), isTrue);
  });
}
