import 'dart:convert';
import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:path/path.dart' as p;

import '../../core/harness_paths.dart';

/// View model for the Gallery: lists captured runs and loads a run's manifest for browsing.
class GalleryViewModel extends ChangeNotifier {
  /// Creates the view model.
  GalleryViewModel(this._paths);

  final HarnessPaths _paths;

  List<String> _runIds = [];

  /// Run ids (newest first).
  List<String> get runIds => List.unmodifiable(_runIds);

  String? _selectedRunId;

  /// The selected run id.
  String? get selectedRunId => _selectedRunId;

  Map<String, dynamic>? _manifest;

  /// The selected run's manifest, if loaded.
  Map<String, dynamic>? get manifest => _manifest;

  /// Lists runs under the runs dir (those containing a `manifest.json`).
  static List<String> listRuns(String runsDir) {
    final dir = Directory(runsDir);
    if (!dir.existsSync()) return [];
    final ids = <String>[];
    for (final entry in dir.listSync()) {
      if (entry is Directory && File(p.join(entry.path, 'manifest.json')).existsSync()) {
        ids.add(p.basename(entry.path));
      }
    }
    ids.sort((a, b) => b.compareTo(a)); // timestamp ids → newest first
    return ids;
  }

  /// Refreshes the run list and selects the newest.
  Future<void> refresh() async {
    _runIds = listRuns(_paths.runsDir);
    if (_selectedRunId == null || !_runIds.contains(_selectedRunId)) {
      _selectedRunId = _runIds.isEmpty ? null : _runIds.first;
    }
    await _loadManifest();
    notifyListeners();
  }

  /// Selects a run and loads its manifest.
  Future<void> select(String runId) async {
    _selectedRunId = runId;
    await _loadManifest();
    notifyListeners();
  }

  /// Absolute path of a screenshot [file] (relative) within the selected run.
  String? screenshotPath(String? file) {
    if (file == null || _selectedRunId == null) return null;
    return p.join(_paths.runDir(_selectedRunId!), file);
  }

  Future<void> _loadManifest() async {
    final id = _selectedRunId;
    if (id == null) {
      _manifest = null;
      return;
    }
    final file = File(p.join(_paths.runDir(id), 'manifest.json'));
    if (!file.existsSync()) {
      _manifest = null;
      return;
    }
    try {
      _manifest = jsonDecode(await file.readAsString()) as Map<String, dynamic>;
    } catch (_) {
      _manifest = null;
    }
  }
}
