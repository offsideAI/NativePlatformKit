import 'dart:io';

import 'package:path/path.dart' as p;

import '../../core/command_runner.dart';

/// Captures device screenshots in a binary-safe way: `adb shell screencap -p` to a file on the
/// device, then `adb pull` to the host (avoids streaming binary over the text event channel).
class CaptureService {
  /// Creates the service.
  CaptureService(this._runner, {String? sdkRoot, Map<String, String>? environment})
      : _environment = environment ?? Platform.environment,
        _explicitSdkRoot = sdkRoot;

  final CommandRunner _runner;
  final Map<String, String> _environment;
  final String? _explicitSdkRoot;

  static const String _devicePath = '/sdcard/npk_capture.png';

  /// Resolved SDK root.
  String get sdkRoot =>
      _explicitSdkRoot ??
      _environment['ANDROID_HOME'] ??
      _environment['ANDROID_SDK_ROOT'] ??
      p.join(_environment['HOME'] ?? '', 'Library', 'Android', 'sdk');

  /// Absolute path to `adb`.
  String get adbPath => p.join(sdkRoot, 'platform-tools', 'adb');

  String get _adb => adbPath;

  /// Captures the current screen of [serial] to [destPath] (and optionally mirrors to [mirrorPath]).
  /// Returns `true` if a non-empty PNG was written.
  Future<bool> capture({
    required String serial,
    required String destPath,
    String? mirrorPath,
  }) async {
    File(destPath).parent.createSync(recursive: true);

    final cap = await _runner.run(
      _adb,
      ['-s', serial, 'shell', 'screencap', '-p', _devicePath],
      timeout: const Duration(seconds: 30),
    );
    if (!cap.success) return false;

    final pull = await _runner.run(
      _adb,
      ['-s', serial, 'pull', _devicePath, destPath],
      timeout: const Duration(seconds: 30),
    );
    // Clean up the device-side file (best-effort).
    try {
      await _runner.run(_adb, ['-s', serial, 'shell', 'rm', '-f', _devicePath],
          timeout: const Duration(seconds: 10));
    } catch (_) {}

    final file = File(destPath);
    final ok = pull.success && file.existsSync() && file.lengthSync() > 0;
    if (ok && mirrorPath != null) {
      final mirror = File(mirrorPath);
      mirror.parent.createSync(recursive: true);
      file.copySync(mirrorPath);
    }
    return ok;
  }
}
