import 'dart:io';

import 'package:collection/collection.dart';
import 'package:path/path.dart' as p;

import '../../core/command_runner.dart';

/// A text/tappable node parsed from a UI Automator hierarchy dump.
class UiText {
  /// Creates a node.
  const UiText({required this.text, required this.cx, required this.cy, required this.top});

  /// The node's text (or content-desc).
  final String text;

  /// Center x.
  final int cx;

  /// Center y.
  final int cy;

  /// Top edge (for vertical ordering).
  final int top;
}

/// Drives an automated Home → section → screen sweep of the playground using UI Automator
/// (`uiautomator dump` → tap a row by its text → capture → back). The app has no deep links, so
/// this taps by on-screen label. Two levels deep, which matches the app's menu structure.
class AutoCaptureService {
  /// Creates the service.
  AutoCaptureService(this._runner, {String? sdkRoot, Map<String, String>? environment})
      : _environment = environment ?? Platform.environment,
        _explicitSdkRoot = sdkRoot;

  final CommandRunner _runner;
  final Map<String, String> _environment;
  final String? _explicitSdkRoot;

  /// Persistent chrome to never treat as a navigation target (lower-cased).
  static const Set<String> deny = {
    'test labs',
    'ui component library',
    'navigate up',
    'settings',
    'color generator form',
  };

  static const Duration _settleDuration = Duration(milliseconds: 1300);

  /// Resolved SDK root.
  String get sdkRoot =>
      _explicitSdkRoot ??
      _environment['ANDROID_HOME'] ??
      _environment['ANDROID_SDK_ROOT'] ??
      p.join(_environment['HOME'] ?? '', 'Library', 'Android', 'sdk');

  String get _adb => p.join(sdkRoot, 'platform-tools', 'adb');

  /// Parses tappable text nodes from a UI Automator XML dump: non-empty text/content-desc, valid
  /// bounds, not in [deny], deduped by text and ordered top-to-bottom. Exposed for testing.
  static List<UiText> navTargets(String xml) {
    final seen = <String>{};
    final out = <UiText>[];
    for (final tag in RegExp(r'<node\b([^>]*)>').allMatches(xml)) {
      final attrs = <String, String>{};
      for (final a in RegExp(r'([\w-]+)="([^"]*)"').allMatches(tag.group(1)!)) {
        attrs[a.group(1)!] = a.group(2)!;
      }
      final text = _decode((attrs['text']?.isNotEmpty ?? false) ? attrs['text']! : (attrs['content-desc'] ?? '')).trim();
      if (text.isEmpty || text.length > 40 || deny.contains(text.toLowerCase())) continue;
      final b = RegExp(r'\[(-?\d+),(-?\d+)\]\[(-?\d+),(-?\d+)\]').firstMatch(attrs['bounds'] ?? '');
      if (b == null) continue;
      final x1 = int.parse(b.group(1)!), y1 = int.parse(b.group(2)!);
      final x2 = int.parse(b.group(3)!), y2 = int.parse(b.group(4)!);
      if (x2 <= x1 || y2 <= y1) continue;
      if (!seen.add(text)) continue;
      out.add(UiText(text: text, cx: (x1 + x2) ~/ 2, cy: (y1 + y2) ~/ 2, top: y1));
    }
    out.sort((a, b) => a.top.compareTo(b.top));
    return out;
  }

  /// Extracts the foreground package from a dump (`package="..."`). Exposed for testing.
  static String? rootPackage(String xml) => RegExp(r'package="([^"]+)"').firstMatch(xml)?.group(1);

  static String _decode(String s) => s
      .replaceAll('&amp;', '&')
      .replaceAll('&lt;', '<')
      .replaceAll('&gt;', '>')
      .replaceAll('&quot;', '"')
      .replaceAll('&#39;', "'");

  /// Runs the sweep. Calls [onScreen] for every reached screen (which performs the capture), logs
  /// via [onLog], stops early when [isCancelled] returns true, and caps at [maxCaptures]. Returns
  /// the number of screens reached.
  Future<int> sweep({
    required String serial,
    required String component,
    required Future<void> Function(List<String> path) onScreen,
    required void Function(String message) onLog,
    required bool Function() isCancelled,
    int maxCaptures = 300,
  }) async {
    final appPkg = component.split('/').first;
    var count = 0;

    await _amStart(serial, component);
    await _settle(extra: 500);
    if (isCancelled()) return count;

    await onScreen(['Home']);
    count++;

    final homeLabels = navTargets(await _dump(serial)).map((n) => n.text).toList();
    final homeSet = homeLabels.toSet();
    onLog('Found ${homeLabels.length} top-level sections.');

    for (final section in homeLabels) {
      if (isCancelled() || count >= maxCaptures) break;
      if (!await _tapLabel(serial, section)) {
        onLog('Could not open "$section".');
        continue;
      }
      await _settle();
      if (!await _inApp(serial, appPkg)) {
        await _relaunch(serial, component);
        continue;
      }
      onLog('Section: $section');
      await onScreen(['Home', section]);
      count++;

      final childLabels =
          navTargets(await _dump(serial)).map((n) => n.text).where((t) => !homeSet.contains(t)).toList();

      for (final child in childLabels) {
        if (isCancelled() || count >= maxCaptures) break;
        if (!await _tapLabel(serial, child)) continue;
        await _settle();
        if (!await _inApp(serial, appPkg)) {
          await _relaunch(serial, component);
          break;
        }
        await onScreen(['Home', section, child]);
        count++;
        await _back(serial);
        await _settle();
        // If Back overshot to Home, re-enter the section to continue its children.
        final current = navTargets(await _dump(serial)).map((n) => n.text).toSet();
        if (homeSet.intersection(current).length >= (homeSet.length * 0.6)) {
          await _tapLabel(serial, section);
          await _settle();
        }
      }

      await _back(serial);
      await _settle();
      if (!await _inApp(serial, appPkg)) await _relaunch(serial, component);
    }

    onLog('Auto-capture finished: $count screens.');
    return count;
  }

  // --- adb primitives ---

  Future<String> _dump(String serial) async {
    await _runner.run(_adb, ['-s', serial, 'shell', 'uiautomator', 'dump', '/sdcard/npk_ui.xml'],
        timeout: const Duration(seconds: 20));
    final r = await _runner.run(_adb, ['-s', serial, 'shell', 'cat', '/sdcard/npk_ui.xml'],
        timeout: const Duration(seconds: 15));
    return r.stdout;
  }

  Future<bool> _tapLabel(String serial, String label) async {
    for (var attempt = 0; attempt < 3; attempt++) {
      final hit = navTargets(await _dump(serial)).firstWhereOrNull((n) => n.text == label);
      if (hit != null) {
        await _runner.run(_adb, ['-s', serial, 'shell', 'input', 'tap', '${hit.cx}', '${hit.cy}'],
            timeout: const Duration(seconds: 10));
        return true;
      }
      if (attempt < 2) {
        await _swipeUp(serial);
        await _settle();
      }
    }
    return false;
  }

  Future<void> _back(String serial) =>
      _runner.run(_adb, ['-s', serial, 'shell', 'input', 'keyevent', '4'], timeout: const Duration(seconds: 10));

  Future<void> _swipeUp(String serial) => _runner.run(
        _adb,
        ['-s', serial, 'shell', 'input', 'swipe', '540', '1600', '540', '700', '300'],
        timeout: const Duration(seconds: 10),
      );

  Future<void> _amStart(String serial, String component) => _runner.run(
        _adb,
        ['-s', serial, 'shell', 'am', 'start', '-n', component],
        timeout: const Duration(seconds: 20),
      );

  Future<void> _relaunch(String serial, String component) async {
    await _amStart(serial, component);
    await _settle();
  }

  Future<bool> _inApp(String serial, String appPkg) async => rootPackage(await _dump(serial)) == appPkg;

  Future<void> _settle({int extra = 0}) =>
      Future<void>.delayed(_settleDuration + Duration(milliseconds: extra));
}
