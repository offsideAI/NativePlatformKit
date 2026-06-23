import 'dart:async';

import 'package:flutter/foundation.dart';

import '../../app/app_state.dart';
import '../../core/process_event.dart';
import 'environment_report.dart';
import 'environment_service.dart';

/// Which install task (if any) is currently running.
enum InstallTask {
  /// Nothing installing.
  none,

  /// Installing the command-line tools.
  cmdlineTools,

  /// Installing the system image.
  systemImage,
}

/// View model for the Environment screen: runs detection, drives the in-app SDK bootstrap, and
/// publishes readiness to [AppState].
class EnvironmentViewModel extends ChangeNotifier {
  /// Creates the view model.
  EnvironmentViewModel(this._service, this._appState);

  final EnvironmentService _service;
  final AppState _appState;

  EnvironmentReport? _report;

  /// The latest detection report (null until first [detect]).
  EnvironmentReport? get report => _report;

  bool _detecting = false;

  /// Whether detection is in progress.
  bool get isDetecting => _detecting;

  InstallTask _installing = InstallTask.none;

  /// The currently-running install task.
  InstallTask get installing => _installing;

  /// Whether any long-running work is happening.
  bool get isBusy => _detecting || _installing != InstallTask.none;

  final List<String> _log = [];

  /// Live output of the current/last install.
  List<String> get log => List.unmodifiable(_log);

  /// The resolved SDK root.
  String get sdkRoot => _service.sdkRoot;

  StreamSubscription<ProcessEvent>? _sub;

  /// Probes the environment and updates [AppState] readiness.
  Future<void> detect() async {
    if (_detecting) return;
    _detecting = true;
    notifyListeners();
    try {
      _report = await _service.detect();
      _appState.setEnvironmentReady(_report!.canCreateEmulator);
    } finally {
      _detecting = false;
      notifyListeners();
    }
  }

  /// Installs the command-line tools, then re-detects.
  Future<void> installCmdlineTools() =>
      _runInstall(InstallTask.cmdlineTools, _service.installCmdlineTools());

  /// Installs the system image, then re-detects.
  Future<void> installSystemImage() =>
      _runInstall(InstallTask.systemImage, _service.installSystemImage());

  Future<void> _runInstall(InstallTask task, Stream<ProcessEvent> stream) async {
    if (isBusy) return;
    _installing = task;
    _log.clear();
    notifyListeners();

    final completer = Completer<void>();
    _sub = stream.listen(
      (event) {
        switch (event.type) {
          case ProcessEventType.stdout:
          case ProcessEventType.stderr:
            if (event.data != null) _appendLog(event.data!);
          case ProcessEventType.exit:
            _appendLog('— finished (exit code ${event.exitCode})');
            if (!completer.isCompleted) completer.complete();
          case ProcessEventType.unknown:
            break;
        }
      },
      onError: (Object error, StackTrace _) {
        _appendLog('error: $error');
        if (!completer.isCompleted) completer.complete();
      },
    );

    await completer.future;
    _installing = InstallTask.none;
    _sub = null;
    notifyListeners();
    await detect();
  }

  void _appendLog(String text) {
    for (final line in text.split('\n')) {
      if (line.trim().isEmpty) continue;
      _log.add(line);
    }
    if (_log.length > 500) _log.removeRange(0, _log.length - 500);
    notifyListeners();
  }

  @override
  void dispose() {
    _sub?.cancel();
    super.dispose();
  }
}
