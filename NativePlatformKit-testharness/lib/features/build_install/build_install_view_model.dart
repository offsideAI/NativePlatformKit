import 'dart:async';

import 'package:flutter/foundation.dart';

import '../../app/app_state.dart';
import '../../core/native_bridge.dart';
import '../../core/process_event.dart';
import '../emulator/emulator_service.dart';
import 'build_install_service.dart';

/// The build/install/launch pipeline phase.
enum BuildPhase {
  /// Not started.
  idle,

  /// Running the Gradle build.
  building,

  /// Installing the APK.
  installing,

  /// Launching the activity.
  launching,

  /// Completed successfully.
  success,

  /// A step failed.
  failed,
}

/// View model for the Build & Install screen: orchestrates Gradle build → adb install → launch on
/// the running emulator, with a live log.
class BuildInstallViewModel extends ChangeNotifier {
  /// Creates the view model.
  BuildInstallViewModel(this._service, this._emulator, this._appState, this._bridge);

  final BuildInstallService _service;
  final EmulatorService _emulator;
  final AppState _appState;
  final NativeBridge _bridge;

  /// Path to the playground Gradle project.
  String playgroundPath = BuildInstallService.defaultPlaygroundPath;

  BuildPhase _phase = BuildPhase.idle;

  /// Current pipeline phase.
  BuildPhase get phase => _phase;

  /// Whether the pipeline is running.
  bool get isBusy =>
      _phase == BuildPhase.building ||
      _phase == BuildPhase.installing ||
      _phase == BuildPhase.launching;

  String? _apkPath;

  /// The installed APK path (after a successful build).
  String? get apkPath => _apkPath;

  final List<String> _log = [];

  /// Live pipeline log.
  List<String> get log => List.unmodifiable(_log);

  /// The `am start` component, for display.
  String get launchComponent => _service.launchComponent;

  /// Lets the user pick the playground project folder.
  Future<void> choosePlaygroundFolder() async {
    final picked = await _bridge.chooseDirectory(initialDirectory: playgroundPath);
    if (picked != null) {
      playgroundPath = picked;
      notifyListeners();
    }
  }

  /// Builds, installs, and launches the playground on the running emulator.
  Future<void> buildInstallLaunch() async {
    if (isBusy) return;
    _log.clear();

    final serial = await _emulator.runningSerial();
    if (serial == null) {
      _appendLog('No running emulator. Boot one in the Emulator tab first.');
      _setPhase(BuildPhase.failed);
      return;
    }
    _appendLog('Target device: $serial');

    _setPhase(BuildPhase.building);
    if (!await _drainOk(_service.assembleDebug(playgroundPath))) {
      _appendLog('✗ Build failed.');
      _setPhase(BuildPhase.failed);
      return;
    }
    _apkPath = _service.findApk(playgroundPath);
    if (_apkPath == null) {
      _appendLog('✗ Build succeeded but no APK was found.');
      _setPhase(BuildPhase.failed);
      return;
    }
    _appendLog('APK: $_apkPath');

    _setPhase(BuildPhase.installing);
    if (!await _drainOk(_service.install(serial: serial, apkPath: _apkPath!))) {
      _appendLog('✗ Install failed.');
      _setPhase(BuildPhase.failed);
      return;
    }

    _setPhase(BuildPhase.launching);
    _appendLog('Launching ${_service.launchComponent} …');
    final result = await _service.launch(serial);
    if (result.stdout.trim().isNotEmpty) _appendLog(result.stdout.trim());
    if (result.stderr.trim().isNotEmpty) _appendLog(result.stderr.trim());
    if (!result.success) {
      _appendLog('✗ Launch failed.');
      _setPhase(BuildPhase.failed);
      return;
    }

    _appendLog('✓ Installed and launched.');
    _setPhase(BuildPhase.success);
    _appState.setAppInstalled(true);
  }

  Future<bool> _drainOk(Stream<ProcessEvent> stream) {
    final completer = Completer<bool>();
    stream.listen(
      (event) {
        if (event.data != null) _appendLog(event.data!);
        if (event.isExit && !completer.isCompleted) completer.complete(event.exitCode == 0);
      },
      onError: (Object error, StackTrace _) {
        _appendLog('error: $error');
        if (!completer.isCompleted) completer.complete(false);
      },
    );
    return completer.future;
  }

  void _appendLog(String text) {
    for (final line in text.split('\n')) {
      if (line.trim().isEmpty) continue;
      _log.add(line);
    }
    if (_log.length > 800) _log.removeRange(0, _log.length - 800);
    notifyListeners();
  }

  void _setPhase(BuildPhase phase) {
    _phase = phase;
    notifyListeners();
  }
}
