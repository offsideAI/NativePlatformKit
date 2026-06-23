import 'package:path/path.dart' as p;

/// Resolves the harness's on-disk locations from a single [root] (the harness project folder).
///
/// `root` defaults to [defaultRoot] and is overridable in Settings (E8). Everything else —
/// screenshots, `.harness/` config + run-state, and the sibling playground — is derived from it,
/// so there is exactly one machine-specific assumption.
class HarnessPaths {
  /// Creates a paths resolver rooted at [root].
  const HarnessPaths({this.root = defaultRoot});

  /// Default harness project root on this machine.
  static const String defaultRoot =
      '/Users/coder/repos/arunabhdas/githubrepos/NativePlatformKit/NativePlatformKit-testharness';

  /// The harness project folder.
  final String root;

  /// Repo root (parent of the harness folder).
  String get repoRoot => p.dirname(root);

  /// The sibling playground project.
  String get playground => p.join(repoRoot, 'NativePlatformKit-TestLabs-Android-Playground');

  /// Screenshots output root.
  String get screenshots => p.join(root, 'screenshots');

  /// Timestamped per-run screenshot archive.
  String get runsDir => p.join(screenshots, 'runs');

  /// Canonical "latest" screenshot set.
  String get latestDir => p.join(screenshots, 'latest');

  /// Project-local config/state dir.
  String get harnessDir => p.join(root, '.harness');

  /// Persisted config file.
  String get configFile => p.join(harnessDir, 'config.json');

  /// Persisted resumable run-state file.
  String get runStateFile => p.join(harnessDir, 'run-state.json');

  /// Directory for run [runId].
  String runDir(String runId) => p.join(runsDir, runId);
}
