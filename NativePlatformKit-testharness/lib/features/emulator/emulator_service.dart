import 'dart:io';

import 'package:path/path.dart' as p;

import '../../core/command_runner.dart';
import '../../core/process_event.dart';

/// Manages the harness AVD: create / list / delete, boot (windowed, hardware GPU), boot-completion
/// detection, and stop. AVD/boot operations run external tools via the [CommandRunner].
class EmulatorService {
  /// Creates the service. [sdkRoot]/[environment] are injectable for tests.
  EmulatorService(
    this._runner, {
    String? sdkRoot,
    Map<String, String>? environment,
    this.avdName = 'npk_pixel_6_api34',
    this.systemImagePackage = 'system-images;android-34;google_apis;arm64-v8a',
    this.device = 'pixel_6',
  })  : _environment = environment ?? Platform.environment,
        _explicitSdkRoot = sdkRoot;

  final CommandRunner _runner;
  final Map<String, String> _environment;
  final String? _explicitSdkRoot;

  /// The AVD name the harness creates/boots.
  final String avdName;

  /// The system image package id.
  final String systemImagePackage;

  /// The device profile id (e.g. `pixel_6`).
  final String device;

  /// Resolved SDK root.
  String get sdkRoot =>
      _explicitSdkRoot ??
      _environment['ANDROID_HOME'] ??
      _environment['ANDROID_SDK_ROOT'] ??
      p.join(_environment['HOME'] ?? '', 'Library', 'Android', 'sdk');

  String get _adb => p.join(sdkRoot, 'platform-tools', 'adb');
  String get _emulator => p.join(sdkRoot, 'emulator', 'emulator');
  String get _avdmanager => p.join(sdkRoot, 'cmdline-tools', 'latest', 'bin', 'avdmanager');

  /// Lists existing AVD names via `emulator -list-avds`.
  Future<List<String>> listAvds() async {
    final result = await _runner.run(_emulator, const ['-list-avds'], timeout: const Duration(seconds: 20));
    return parseAvdList(result.stdout);
  }

  /// Parses `emulator -list-avds` output. Exposed for testing.
  static List<String> parseAvdList(String output) => output
      .split('\n')
      .map((l) => l.trim())
      .where((l) => l.isNotEmpty && !l.startsWith('INFO') && !l.contains(' '))
      .toList();

  /// Creates the AVD (Pixel 6 profile) and configures 4 GB RAM + hardware GPU. Streams output.
  Stream<ProcessEvent> createAvd({String? name}) {
    final target = name ?? avdName;
    final script = '''
set -e
export JAVA_HOME="\${JAVA_HOME:-\$(/usr/libexec/java_home 2>/dev/null)}"
export PATH="\$JAVA_HOME/bin:\$PATH"
AVDM="$_avdmanager"
echo "Creating AVD '$target' (device=$device, image=$systemImagePackage)…"
echo "no" | "\$AVDM" create avd -n "$target" -k "$systemImagePackage" -d "$device" --force
CFG="\$HOME/.android/avd/$target.avd/config.ini"
if [ -f "\$CFG" ]; then
  { echo "hw.ramSize=4096"; echo "hw.gpu.enabled=yes"; echo "hw.gpu.mode=auto"; } >> "\$CFG"
  echo "Configured 4 GB RAM + hardware GPU."
fi
echo "AVD '$target' created."
''';
    return _runner.stream('/bin/sh', ['-c', script]);
  }

  /// Deletes the named AVD. Streams output.
  Stream<ProcessEvent> deleteAvd(String name) {
    final script = '''
set -e
export JAVA_HOME="\${JAVA_HOME:-\$(/usr/libexec/java_home 2>/dev/null)}"
"$_avdmanager" delete avd -n "$name"
echo "Deleted AVD '$name'."
''';
    return _runner.stream('/bin/sh', ['-c', script]);
  }

  /// Boots the emulator (long-running). [onHandle] yields the process handle for [cancelBoot].
  Stream<ProcessEvent> boot({
    String? name,
    bool coldBoot = false,
    bool wipeData = false,
    void Function(String handle)? onHandle,
  }) {
    final target = name ?? avdName;
    final args = <String>[
      '@$target',
      '-gpu', 'auto',
      if (coldBoot) '-no-snapshot-load',
      if (wipeData) '-wipe-data',
    ];
    return _runner.stream(_emulator, args, onHandle: onHandle);
  }

  /// Cancels a boot in progress by process handle.
  Future<void> cancelBoot(String handle) => _runner.cancel(handle);

  /// Returns the serial of a running emulator (e.g. `emulator-5554`), or `null`.
  Future<String?> runningSerial() async {
    try {
      final result = await _runner.run(_adb, const ['devices'], timeout: const Duration(seconds: 15));
      return parseEmulatorSerial(result.stdout);
    } catch (_) {
      return null;
    }
  }

  /// Parses `adb devices` output for the first online emulator serial. Exposed for testing.
  static String? parseEmulatorSerial(String output) {
    for (final line in output.split('\n')) {
      final trimmed = line.trim();
      if (trimmed.startsWith('emulator-') && trimmed.endsWith('device')) {
        return trimmed.split(RegExp(r'\s+')).first;
      }
    }
    return null;
  }

  /// Whether `sys.boot_completed` is `1` on [serial].
  Future<bool> isBootCompleted(String serial) async {
    try {
      final result = await _runner.run(
        _adb,
        ['-s', serial, 'shell', 'getprop', 'sys.boot_completed'],
        timeout: const Duration(seconds: 10),
      );
      return result.stdout.trim() == '1';
    } catch (_) {
      return false;
    }
  }

  /// Dismisses the keyguard/lock screen (best-effort).
  Future<void> dismissKeyguard(String serial) async {
    try {
      await _runner.run(_adb, ['-s', serial, 'shell', 'input', 'keyevent', '82'],
          timeout: const Duration(seconds: 8));
    } catch (_) {
      // best-effort
    }
  }

  /// Stops a running emulator via `adb emu kill`.
  Future<void> stop(String serial) async {
    await _runner.run(_adb, ['-s', serial, 'emu', 'kill'], timeout: const Duration(seconds: 15));
  }
}
