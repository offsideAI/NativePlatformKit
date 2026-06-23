import 'dart:convert';
import 'dart:io';

import '../../core/harness_paths.dart';
import 'run_models.dart';

/// Persists the current (resumable) run to `.harness/run-state.json`.
class RunStore {
  /// Creates a store rooted at [_paths].
  RunStore(this._paths);

  final HarnessPaths _paths;

  /// Loads the current run, or `null` if none/invalid.
  Future<RunState?> loadCurrent() async {
    final file = File(_paths.runStateFile);
    if (!file.existsSync()) return null;
    try {
      final json = jsonDecode(await file.readAsString());
      return RunState.fromJson(json as Map<String, dynamic>);
    } catch (_) {
      return null;
    }
  }

  /// Saves [state] as the current run.
  Future<void> saveCurrent(RunState state) async {
    final file = File(_paths.runStateFile);
    file.parent.createSync(recursive: true);
    await file.writeAsString(const JsonEncoder.withIndent('  ').convert(state.toJson()));
  }

  /// Clears the current run-state file.
  Future<void> clearCurrent() async {
    final file = File(_paths.runStateFile);
    if (file.existsSync()) await file.delete();
  }
}
