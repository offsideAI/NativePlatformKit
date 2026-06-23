import 'dart:convert';
import 'dart:io';

import 'package:collection/collection.dart';
import 'package:flutter/foundation.dart';
import 'package:path/path.dart' as p;

import '../../core/command_runner.dart';
import '../../core/harness_paths.dart';
import '../../data/flow_catalog/catalog_service.dart';
import '../../data/flow_catalog/flow_catalog.dart';
import '../emulator/emulator_service.dart';
import 'auto_capture_service.dart';
import 'capture_service.dart';
import 'run_models.dart';
import 'run_reporter.dart';
import 'run_store.dart';

/// View model for the Flow Runner: loads the catalog, manages a resumable capture run, captures
/// screenshots, and tracks per-screen status/notes.
class FlowRunnerViewModel extends ChangeNotifier {
  /// Creates the view model.
  FlowRunnerViewModel(
    this._catalogService,
    this._capture,
    this._emulator,
    this._store,
    this._reporter,
    this._paths,
    this._runner,
    this._auto,
  );

  final CatalogService _catalogService;
  final CaptureService _capture;
  final EmulatorService _emulator;
  final RunStore _store;
  final RunReporter _reporter;
  final HarnessPaths _paths;
  final CommandRunner _runner;
  final AutoCaptureService _auto;

  FlowCatalog? _catalog;

  /// The loaded catalog (null until [init]).
  FlowCatalog? get catalog => _catalog;

  RunState? _run;

  /// The current run (null if none started).
  RunState? get run => _run;

  String? _selectedId;

  /// The selected screen id.
  String? get selectedId => _selectedId;

  bool _busy = false;
  bool _autoRunning = false;

  /// Whether a capture/start/finish is in progress.
  bool get isBusy => _busy || _autoRunning;

  /// Whether an automated sweep is running.
  bool get isAutoRunning => _autoRunning;

  String? _status;

  /// Last status/error message for the UI.
  String? get statusMessage => _status;

  bool _initialized = false;

  /// Loads the catalog and any resumable run. Idempotent.
  Future<void> init() async {
    if (_initialized) return;
    _initialized = true;
    _catalog = await _catalogService.load();
    _run = await _store.loadCurrent();
    _selectedId ??= _catalog?.allScreens.firstOrNull?.id;
    notifyListeners();
  }

  /// The currently-selected screen, if any.
  FlowScreen? get selectedScreen {
    final id = _selectedId;
    if (id == null || _catalog == null) return null;
    for (final s in _catalog!.allScreens) {
      if (s.id == id) return s;
    }
    return null;
  }

  /// The record for [id] in the current run, if any.
  ScreenRecord? recordOf(String id) => _run?.records[id];

  /// Absolute path of a captured screenshot for [screen], or null if not captured.
  String? capturedPathOf(FlowScreen screen) {
    final rec = _run?.records[screen.id];
    if (rec?.file == null || _run == null) return null;
    return p.join(_paths.runDir(_run!.runId), rec!.file!);
  }

  /// Selects a screen.
  void select(String id) {
    _selectedId = id;
    notifyListeners();
  }

  /// Starts a fresh run over all catalog screens.
  Future<void> startRun() async {
    final catalog = _catalog;
    if (catalog == null || _busy) return;
    _setBusy(true);
    final serial = await _emulator.runningSerial();
    final run = RunState(
      runId: _timestampId(),
      startedAt: DateTime.now().toIso8601String(),
      serial: serial,
      records: {for (final s in catalog.allScreens) s.id: ScreenRecord(id: s.id)},
    );
    await _collectMetadata(run, serial);
    _run = run;
    _selectedId = catalog.allScreens.firstOrNull?.id;
    await _store.saveCurrent(run);
    _status = serial == null ? 'Run started — boot an emulator before capturing.' : 'Run started.';
    _setBusy(false);
  }

  /// Captures the selected screen.
  Future<void> captureSelected() async {
    final run = _run;
    final screen = selectedScreen;
    if (run == null || screen == null || _busy) return;
    final serial = run.serial ?? await _emulator.runningSerial();
    if (serial == null) {
      _status = 'No running emulator — boot one in the Emulator tab.';
      notifyListeners();
      return;
    }
    _setBusy(true);
    run.serial ??= serial;
    final dest = p.join(_paths.runDir(run.runId), screen.screenshot);
    final mirror = p.join(_paths.latestDir, screen.screenshot);
    final ok = await _capture.capture(serial: serial, destPath: dest, mirrorPath: mirror);
    final rec = run.records[screen.id]!;
    rec
      ..status = ok ? CaptureStatus.captured : CaptureStatus.failed
      ..file = ok ? screen.screenshot : null
      ..capturedAt = DateTime.now().toIso8601String();
    await _store.saveCurrent(run);
    _status = ok ? 'Captured ${screen.title}.' : 'Capture failed for ${screen.title}.';
    if (ok) _advanceToNextPending();
    _setBusy(false);
  }

  /// Marks the selected screen as skipped.
  Future<void> skipSelected() async {
    final run = _run;
    final screen = selectedScreen;
    if (run == null || screen == null) return;
    run.records[screen.id]!.status = CaptureStatus.skipped;
    await _store.saveCurrent(run);
    _advanceToNextPending();
    notifyListeners();
  }

  /// Sets a note on the selected screen.
  Future<void> setNote(String text) async {
    final run = _run;
    final id = _selectedId;
    if (run == null || id == null) return;
    run.records[id]!.notes = text.isEmpty ? null : text;
    await _store.saveCurrent(run);
  }

  /// Finishes the run: writes manifest + reports.
  Future<void> finishRun() async {
    final run = _run;
    final catalog = _catalog;
    if (run == null || catalog == null || _busy) return;
    _setBusy(true);
    run.finishedAt = DateTime.now().toIso8601String();
    final dir = await _reporter.write(run, catalog);
    await _store.saveCurrent(run);
    _status = 'Run finished — report written to $dir';
    _setBusy(false);
  }

  /// Runs an automated UI-Automator sweep: navigates Home → section → screen, capturing each.
  /// Saves PNGs under `runs/<id>/auto/` (+ mirror to `latest/auto/`), updates matching catalog
  /// records, and writes `auto-manifest.json`.
  Future<void> autoCapture() async {
    final catalog = _catalog;
    if (catalog == null || isBusy) return;
    final serial = await _emulator.runningSerial();
    if (serial == null) {
      _status = 'No running emulator — boot one in the Emulator tab.';
      notifyListeners();
      return;
    }
    // Ensure a run exists to hold the captures.
    if (_run == null || _run!.isFinished) {
      await startRun();
    }
    final run = _run!;
    run.serial ??= serial;
    _autoRunning = true;
    _status = 'Auto-capture started…';
    notifyListeners();

    final captured = <Map<String, String>>[];

    try {
      await _auto.sweep(
        serial: serial,
        component: '${catalog.appPackage}/${catalog.launchActivity}',
        isCancelled: () => !_autoRunning,
        onLog: (message) {
          _status = message;
          notifyListeners();
        },
        onScreen: (path) async {
          final slugPath = path.map(_slug).join('__');
          final rel = p.join('auto', '$slugPath.png');
          final dest = p.join(_paths.runDir(run.runId), rel);
          final mirror = p.join(_paths.latestDir, rel);
          final ok = await _capture.capture(serial: serial, destPath: dest, mirrorPath: mirror);
          if (!ok) return;
          captured.add({'path': path.join(' › '), 'file': rel});
          // Best-effort: light up a matching catalog record by the leaf label.
          final leaf = _slug(path.last);
          for (final screen in catalog.allScreens) {
            if (_slug(screen.title) == leaf && run.records[screen.id]?.status != CaptureStatus.captured) {
              run.records[screen.id]!
                ..status = CaptureStatus.captured
                ..file = rel
                ..capturedAt = DateTime.now().toIso8601String();
              break;
            }
          }
          await _store.saveCurrent(run);
          notifyListeners();
        },
      );
      // Write a manifest of everything the sweep captured.
      final dir = Directory(_paths.runDir(run.runId))..createSync(recursive: true);
      File(p.join(dir.path, 'auto-manifest.json')).writeAsStringSync(
        const JsonEncoder.withIndent('  ').convert({
          'runId': run.runId,
          'capturedAt': DateTime.now().toIso8601String(),
          'count': captured.length,
          'screens': captured,
        }),
      );
      _status = 'Auto-capture done: ${captured.length} screens → ${dir.path}';
    } catch (e) {
      _status = 'Auto-capture error: $e';
    } finally {
      _autoRunning = false;
      notifyListeners();
    }
  }

  /// Requests cancellation of a running auto-capture sweep.
  void cancelAutoCapture() {
    if (!_autoRunning) return;
    _autoRunning = false;
    _status = 'Stopping auto-capture…';
    notifyListeners();
  }

  static String _slug(String s) =>
      s.toLowerCase().replaceAll(RegExp(r'[^a-z0-9]+'), '-').replaceAll(RegExp(r'^-+|-+$'), '');

  void _advanceToNextPending() {
    final catalog = _catalog;
    final run = _run;
    if (catalog == null || run == null) return;
    final screens = catalog.allScreens;
    final start = _selectedId == null ? 0 : screens.indexWhere((s) => s.id == _selectedId) + 1;
    for (var i = start; i < screens.length; i++) {
      if (run.records[screens[i].id]?.status == CaptureStatus.pending) {
        _selectedId = screens[i].id;
        return;
      }
    }
  }

  Future<void> _collectMetadata(RunState run, String? serial) async {
    run.avd = _emulator.avdName;
    if (serial == null) return;
    final adb = _capture.adbPath;
    try {
      final r = await _runner.run(adb, ['-s', serial, 'shell', 'getprop', 'ro.build.version.sdk'],
          timeout: const Duration(seconds: 8));
      run.api = int.tryParse(r.stdout.trim());
    } catch (_) {}
    try {
      final r = await _runner.run('/usr/bin/git', ['-C', _paths.playground, 'rev-parse', '--short', 'HEAD'],
          timeout: const Duration(seconds: 8));
      if (r.success && r.stdout.trim().isNotEmpty) run.appGitSha = r.stdout.trim();
    } catch (_) {}
    final pkg = _catalog?.appPackage;
    if (pkg != null) {
      try {
        final r = await _runner.run(adb, ['-s', serial, 'shell', 'dumpsys', 'package', pkg],
            timeout: const Duration(seconds: 12));
        final match = RegExp(r'versionName=(\S+)').firstMatch(r.stdout);
        if (match != null) run.appVersionName = match.group(1);
      } catch (_) {}
    }
  }

  String _timestampId() {
    final now = DateTime.now();
    String two(int n) => n.toString().padLeft(2, '0');
    return '${now.year}${two(now.month)}${two(now.day)}-${two(now.hour)}${two(now.minute)}${two(now.second)}';
  }

  void _setBusy(bool value) {
    _busy = value;
    notifyListeners();
  }
}
