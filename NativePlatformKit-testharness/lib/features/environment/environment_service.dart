import 'dart:io';

import 'package:path/path.dart' as p;

import '../../core/command_runner.dart';
import '../../core/process_event.dart';
import 'environment_report.dart';

/// Detects the local Android tooling and performs the in-app SDK bootstrap (cmdline-tools +
/// system image). Filesystem checks use `dart:io`; version probes and installs run external tools
/// through the [CommandRunner] (native bridge).
class EnvironmentService {
  /// Creates the service. [sdkRoot]/[environment] are injectable for tests.
  EnvironmentService(
    this._runner, {
    String? sdkRoot,
    String? javaHome,
    Map<String, String>? environment,
    this.systemImagePackage = 'system-images;android-34;google_apis;arm64-v8a',
  })  : _environment = environment ?? Platform.environment,
        _explicitSdkRoot = sdkRoot,
        _explicitJavaHome = javaHome;

  final CommandRunner _runner;
  final Map<String, String> _environment;
  final String? _explicitSdkRoot;
  final String? _explicitJavaHome;

  /// The sdkmanager package id of the target system image.
  final String systemImagePackage;

  /// Verified Google download for the macOS command-line tools (see ROADMAP E2).
  static const String cmdlineToolsUrl =
      'https://dl.google.com/android/repository/commandlinetools-mac-13114758_latest.zip';

  /// The resolved Android SDK root.
  String get sdkRoot =>
      _explicitSdkRoot ??
      _environment['ANDROID_HOME'] ??
      _environment['ANDROID_SDK_ROOT'] ??
      p.join(_environment['HOME'] ?? '', 'Library', 'Android', 'sdk');

  /// `JAVA_HOME`, if known.
  String? get javaHome => _explicitJavaHome ?? _environment['JAVA_HOME'];

  /// Probes the environment and returns a fresh [EnvironmentReport].
  Future<EnvironmentReport> detect() async {
    final tools = <ToolStatus>[
      await _detectJdk(),
      await _detectBinary(
        id: 'adb',
        label: 'Android Debug Bridge (adb)',
        path: p.join(sdkRoot, 'platform-tools', 'adb'),
        versionArgs: const ['version'],
        missingHint: 'Part of the platform-tools package.',
      ),
      await _detectBinary(
        id: 'emulator',
        label: 'Android Emulator',
        path: p.join(sdkRoot, 'emulator', 'emulator'),
        versionArgs: const ['-version'],
        missingHint: 'Install the emulator package.',
      ),
      ..._detectCmdlineTools(),
      _detectSystemImage(),
    ];
    return EnvironmentReport(
      sdkRoot: sdkRoot,
      javaHome: javaHome,
      tools: tools,
      avds: _listAvds(),
    );
  }

  Future<ToolStatus> _detectBinary({
    required String id,
    required String label,
    required String path,
    required List<String> versionArgs,
    String? missingHint,
  }) async {
    if (!File(path).existsSync()) {
      return ToolStatus(id: id, label: label, found: false, hint: missingHint);
    }
    String? version;
    try {
      final result = await _runner.run(path, versionArgs, timeout: const Duration(seconds: 8));
      version = _firstLine('${result.stdout}\n${result.stderr}');
    } catch (_) {
      // Version probe is best-effort; presence on disk is authoritative.
    }
    return ToolStatus(id: id, label: label, found: true, version: version, path: path);
  }

  Future<ToolStatus> _detectJdk() async {
    final home = javaHome;
    final executable = home != null ? p.join(home, 'bin', 'java') : '/usr/bin/env';
    final args = home != null ? const ['-version'] : const ['java', '-version'];
    try {
      final result = await _runner.run(executable, args, timeout: const Duration(seconds: 8));
      // `java -version` prints to stderr.
      return ToolStatus(
        id: 'jdk',
        label: 'Java (JDK)',
        found: true,
        version: _firstLine('${result.stderr}\n${result.stdout}'),
        path: home,
      );
    } catch (_) {
      return ToolStatus(
        id: 'jdk',
        label: 'Java (JDK)',
        found: false,
        hint: 'JDK 17 is required for the Gradle build.',
      );
    }
  }

  List<ToolStatus> _detectCmdlineTools() {
    final binDir = _cmdlineToolsBin();
    final found = binDir != null;
    final sdkmanager = found ? p.join(binDir, 'sdkmanager') : null;
    final avdmanager = found ? p.join(binDir, 'avdmanager') : null;
    return [
      ToolStatus(
        id: 'cmdline-tools',
        label: 'Command-line Tools',
        found: found,
        path: binDir,
        hint: found ? null : 'Install with the button below.',
      ),
      ToolStatus(
        id: 'sdkmanager',
        label: 'sdkmanager',
        found: sdkmanager != null && File(sdkmanager).existsSync(),
        path: sdkmanager,
      ),
      ToolStatus(
        id: 'avdmanager',
        label: 'avdmanager',
        found: avdmanager != null && File(avdmanager).existsSync(),
        path: avdmanager,
      ),
    ];
  }

  ToolStatus _detectSystemImage() {
    final dir = p.join(sdkRoot, 'system-images', 'android-34', 'google_apis', 'arm64-v8a');
    final found = Directory(dir).existsSync();
    return ToolStatus(
      id: 'system-image',
      label: 'System image · API 34 · google_apis · arm64-v8a',
      found: found,
      path: found ? dir : null,
      hint: found ? null : 'Install with the button below (needs command-line tools first).',
    );
  }

  /// Returns the `bin` dir of the command-line tools, or `null` if not installed.
  String? _cmdlineToolsBin() {
    final latest = p.join(sdkRoot, 'cmdline-tools', 'latest', 'bin');
    if (File(p.join(latest, 'sdkmanager')).existsSync()) return latest;
    final cltDir = Directory(p.join(sdkRoot, 'cmdline-tools'));
    if (cltDir.existsSync()) {
      for (final entry in cltDir.listSync()) {
        if (entry is Directory) {
          final bin = p.join(entry.path, 'bin');
          if (File(p.join(bin, 'sdkmanager')).existsSync()) return bin;
        }
      }
    }
    return null;
  }

  List<String> _listAvds() {
    final dir = Directory(p.join(_environment['HOME'] ?? '', '.android', 'avd'));
    if (!dir.existsSync()) return [];
    return dir
        .listSync()
        .whereType<File>()
        .where((f) => f.path.endsWith('.ini'))
        .map((f) => p.basenameWithoutExtension(f.path))
        .toList()
      ..sort();
  }

  String? _firstLine(String text) {
    final trimmed = text.trim();
    if (trimmed.isEmpty) return null;
    return trimmed.split('\n').first.trim();
  }

  // --- In-app SDK bootstrap ---

  /// Downloads and unpacks the command-line tools into `$SDK/cmdline-tools/latest`. Streams output.
  Stream<ProcessEvent> installCmdlineTools() {
    final script = '''
set -e
SDK="$sdkRoot"
mkdir -p "\$SDK/cmdline-tools"
cd "\$SDK/cmdline-tools"
echo "Downloading command-line tools…"
curl -fL --progress-bar -o cmdtools.zip "$cmdlineToolsUrl"
echo "Unpacking…"
rm -rf .tmp latest
unzip -q cmdtools.zip -d .tmp
mkdir -p latest
mv .tmp/cmdline-tools/* latest/
rm -rf .tmp cmdtools.zip
echo "Command-line tools installed at \$SDK/cmdline-tools/latest"
''';
    return _runner.stream('/bin/sh', ['-c', script]);
  }

  /// Accepts licenses and installs the target system image via sdkmanager. Streams output.
  Stream<ProcessEvent> installSystemImage() {
    final home = javaHome;
    final javaExports = home != null
        ? 'export JAVA_HOME="$home"\nexport PATH="\$JAVA_HOME/bin:\$PATH"'
        : '';
    final script = '''
set -e
SDK="$sdkRoot"
export ANDROID_SDK_ROOT="\$SDK"
$javaExports
SDKMANAGER="\$SDK/cmdline-tools/latest/bin/sdkmanager"
echo "Accepting licenses…"
yes | "\$SDKMANAGER" --sdk_root="\$SDK" --licenses >/dev/null 2>&1 || true
echo "Installing $systemImagePackage …"
"\$SDKMANAGER" --sdk_root="\$SDK" "$systemImagePackage"
echo "System image installed."
''';
    return _runner.stream('/bin/sh', ['-c', script]);
  }
}
