import 'dart:io';

import 'package:path/path.dart' as p;

import '../../core/command_runner.dart';
import '../../core/process_event.dart';

/// Builds the playground debug APK (Gradle), installs it on the emulator (adb), and launches the
/// main activity. External tools run through the [CommandRunner].
class BuildInstallService {
  /// Creates the service.
  BuildInstallService(
    this._runner, {
    String? sdkRoot,
    Map<String, String>? environment,
    this.applicationId = 'ai.offside.mobile.android.testlabs',
    this.launchActivity = 'ai.offside.mobile.android.helper.testlabs.nav.TestlabsMainActivity',
  })  : _environment = environment ?? Platform.environment,
        _explicitSdkRoot = sdkRoot;

  final CommandRunner _runner;
  final Map<String, String> _environment;
  final String? _explicitSdkRoot;

  /// The playground's `applicationId` (note: differs from its namespace).
  final String applicationId;

  /// The fully-qualified launcher activity.
  final String launchActivity;

  /// Default location of the playground project (overridable in the UI; becomes a Setting in E8).
  static const String defaultPlaygroundPath =
      '/Users/coder/repos/arunabhdas/githubrepos/NativePlatformKit/NativePlatformKit-TestLabs-Android-Playground';

  /// Resolved SDK root.
  String get sdkRoot =>
      _explicitSdkRoot ??
      _environment['ANDROID_HOME'] ??
      _environment['ANDROID_SDK_ROOT'] ??
      p.join(_environment['HOME'] ?? '', 'Library', 'Android', 'sdk');

  String get _adb => p.join(sdkRoot, 'platform-tools', 'adb');

  /// The `am start` component (`applicationId/activity`).
  String get launchComponent => '$applicationId/$launchActivity';

  /// Runs `./gradlew :app:assembleDebug` in [playgroundPath], streaming output.
  Stream<ProcessEvent> assembleDebug(String playgroundPath) {
    final script = '''
set -e
export JAVA_HOME="\${JAVA_HOME:-\$(/usr/libexec/java_home 2>/dev/null)}"
export PATH="\$JAVA_HOME/bin:\$PATH"
export ANDROID_SDK_ROOT="$sdkRoot"
cd "$playgroundPath"
echo "Building :app:assembleDebug (JAVA_HOME=\$JAVA_HOME)…"
./gradlew :app:assembleDebug
''';
    return _runner.stream('/bin/sh', ['-c', script]);
  }

  /// Returns the newest debug APK under [playgroundPath], or `null` if none exists.
  String? findApk(String playgroundPath) {
    final dir = Directory(p.join(playgroundPath, 'app', 'build', 'outputs', 'apk', 'debug'));
    if (!dir.existsSync()) return null;
    final apks = dir.listSync().whereType<File>().where((f) => f.path.endsWith('.apk')).toList();
    if (apks.isEmpty) return null;
    apks.sort((a, b) => b.statSync().modified.compareTo(a.statSync().modified));
    return apks.first.path;
  }

  /// Installs (`adb install -r`) [apkPath] on [serial], streaming output.
  Stream<ProcessEvent> install({required String serial, required String apkPath}) =>
      _runner.stream(_adb, ['-s', serial, 'install', '-r', apkPath]);

  /// Launches the playground's main activity on [serial].
  Future<CommandResult> launch(String serial) => _runner.run(
        _adb,
        ['-s', serial, 'shell', 'am', 'start', '-n', launchComponent],
        timeout: const Duration(seconds: 25),
      );
}
