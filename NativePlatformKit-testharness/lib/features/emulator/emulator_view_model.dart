import 'dart:async';

import 'package:flutter/foundation.dart';

import '../../app/app_state.dart';
import '../../core/process_event.dart';
import 'emulator_service.dart';

/// High-level emulator state for the UI.
enum EmulatorState {
  /// State not yet determined.
  unknown,

  /// The target AVD does not exist yet.
  noAvd,

  /// AVD exists but no emulator is running.
  stopped,

  /// The AVD is being created.
  creating,

  /// The emulator is booting.
  booting,

  /// The emulator is online and booted.
  online,

  /// The emulator is shutting down.
  stopping,
}

/// View model for the Emulator Manager: AVD lifecycle, boot with readiness detection, and stop.
class EmulatorViewModel extends ChangeNotifier {
  /// Creates the view model.
  EmulatorViewModel(this._service, this._appState);

  final EmulatorService _service;
  final AppState _appState;

  EmulatorState _state = EmulatorState.unknown;

  /// The current high-level state.
  EmulatorState get state => _state;

  List<String> _avds = [];

  /// Known AVD names.
  List<String> get avds => List.unmodifiable(_avds);

  String? _serial;

  /// The running emulator serial, if any.
  String? get serial => _serial;

  /// Whether the target AVD exists.
  bool get hasTargetAvd => _avds.contains(_service.avdName);

  /// The harness AVD name.
  String get avdName => _service.avdName;

  /// Cold-boot toggle (no snapshot).
  bool coldBoot = false;

  /// Wipe-data toggle.
  bool wipeData = false;

  final List<String> _log = [];

  /// Live boot/create log.
  List<String> get log => List.unmodifiable(_log);

  /// Whether a long-running operation is active.
  bool get isBusy =>
      _state == EmulatorState.creating ||
      _state == EmulatorState.booting ||
      _state == EmulatorState.stopping;

  String? _bootHandle;
  StreamSubscription<ProcessEvent>? _bootSub;

  /// Re-detects AVDs and running state.
  Future<void> refresh() async {
    _avds = await _service.listAvds();
    final serial = await _service.runningSerial();
    if (serial != null && await _service.isBootCompleted(serial)) {
      _serial = serial;
      _setState(EmulatorState.online);
      _appState.setEmulatorReady(true);
    } else {
      _serial = null;
      _setState(hasTargetAvd ? EmulatorState.stopped : EmulatorState.noAvd);
      _appState.setEmulatorReady(false);
    }
  }

  /// Creates the target AVD, then refreshes.
  Future<void> createAvd() async {
    if (isBusy) return;
    _setState(EmulatorState.creating);
    _log.clear();
    await _drain(_service.createAvd());
    await refresh();
  }

  /// Boots the AVD and waits for boot completion (updates [AppState]).
  Future<void> bootAndWait() async {
    if (isBusy || _state == EmulatorState.online) return;
    if (!hasTargetAvd) return;
    _setState(EmulatorState.booting);
    _log.clear();
    _appendLog('Launching emulator @$avdName…');

    _bootSub = _service
        .boot(coldBoot: coldBoot, wipeData: wipeData, onHandle: (h) => _bootHandle = h)
        .listen(
      (event) {
        if (event.data != null) _appendLog(event.data!);
        if (event.isExit) {
          // The emulator process ended; if we weren't already online, treat as stopped.
          if (_state != EmulatorState.online) {
            _appendLog('— emulator process exited (code ${event.exitCode})');
            _serial = null;
            _setState(hasTargetAvd ? EmulatorState.stopped : EmulatorState.noAvd);
            _appState.setEmulatorReady(false);
          }
        }
      },
      onError: (Object error, StackTrace _) => _appendLog('error: $error'),
    );

    final ok = await _waitForBoot();
    if (ok && _serial != null) {
      await _service.dismissKeyguard(_serial!);
      _appendLog('Boot complete: $_serial');
      _setState(EmulatorState.online);
      _appState.setEmulatorReady(true);
    } else if (_state == EmulatorState.booting) {
      _appendLog('Timed out waiting for boot.');
    }
  }

  /// Stops the running/booting emulator.
  Future<void> stop() async {
    if (_state == EmulatorState.stopped || _state == EmulatorState.noAvd) return;
    _setState(EmulatorState.stopping);
    final serial = _serial ?? await _service.runningSerial();
    if (serial != null) {
      await _service.stop(serial);
    }
    final handle = _bootHandle;
    if (handle != null) await _service.cancelBoot(handle);
    await _bootSub?.cancel();
    _bootSub = null;
    _bootHandle = null;
    _serial = null;
    _appState.setEmulatorReady(false);
    await refresh();
  }

  Future<bool> _waitForBoot({Duration timeout = const Duration(minutes: 3)}) async {
    final deadline = DateTime.now().add(timeout);
    while (DateTime.now().isBefore(deadline)) {
      if (_state != EmulatorState.booting) return false; // cancelled/exited
      final serial = await _service.runningSerial();
      if (serial != null && await _service.isBootCompleted(serial)) {
        _serial = serial;
        return true;
      }
      await Future<void>.delayed(const Duration(seconds: 2));
    }
    return false;
  }

  Future<void> _drain(Stream<ProcessEvent> stream) async {
    final completer = Completer<void>();
    stream.listen(
      (event) {
        if (event.data != null) _appendLog(event.data!);
        if (event.isExit && !completer.isCompleted) completer.complete();
      },
      onError: (Object error, StackTrace _) {
        _appendLog('error: $error');
        if (!completer.isCompleted) completer.complete();
      },
    );
    await completer.future;
  }

  void _appendLog(String text) {
    for (final line in text.split('\n')) {
      if (line.trim().isEmpty) continue;
      _log.add(line);
    }
    if (_log.length > 500) _log.removeRange(0, _log.length - 500);
    notifyListeners();
  }

  void _setState(EmulatorState state) {
    _state = state;
    notifyListeners();
  }

  @override
  void dispose() {
    _bootSub?.cancel();
    super.dispose();
  }
}
