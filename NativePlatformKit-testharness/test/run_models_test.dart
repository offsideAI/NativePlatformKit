import 'package:flutter_test/flutter_test.dart';
import 'package:npk_test_harness/data/flow_catalog/flow_catalog.dart';
import 'package:npk_test_harness/features/flow_runner/run_models.dart';
import 'package:npk_test_harness/features/flow_runner/run_reporter.dart';
import 'package:npk_test_harness/core/harness_paths.dart';

void main() {
  group('RunState', () {
    test('round-trips through JSON', () {
      final run = RunState(
        runId: '20260623-101500',
        startedAt: '2026-06-23T10:15:00.000',
        serial: 'emulator-5554',
        avd: 'npk_pixel_6_api34',
        api: 34,
        records: {
          'buttons.primary': ScreenRecord(id: 'buttons.primary', status: CaptureStatus.captured, file: 'buttons/primary.png'),
          'buttons.tonal': ScreenRecord(id: 'buttons.tonal', status: CaptureStatus.skipped, notes: 'n/a'),
          'buttons.outlined': ScreenRecord(id: 'buttons.outlined'),
        },
      );
      final restored = RunState.fromJson(run.toJson());
      expect(restored.runId, run.runId);
      expect(restored.api, 34);
      expect(restored.records['buttons.primary']!.status, CaptureStatus.captured);
      expect(restored.records['buttons.tonal']!.notes, 'n/a');
      expect(restored.records['buttons.outlined']!.status, CaptureStatus.pending);
    });

    test('computes counts and progress', () {
      final run = RunState(
        runId: 'r',
        startedAt: 't',
        records: {
          'a': ScreenRecord(id: 'a', status: CaptureStatus.captured),
          'b': ScreenRecord(id: 'b', status: CaptureStatus.skipped),
          'c': ScreenRecord(id: 'c', status: CaptureStatus.failed),
          'd': ScreenRecord(id: 'd'),
        },
      );
      expect(run.captured, 1);
      expect(run.skipped, 1);
      expect(run.failed, 1);
      expect(run.total, 4);
      expect(run.progress, 0.75);
    });
  });

  group('RunReporter', () {
    final catalog = FlowCatalog(
      appPackage: 'a.b.c',
      launchActivity: 'a.b.c.Main',
      categories: [
        FlowCategory(id: 'buttons', title: 'Buttons', screens: [
          const FlowScreen(id: 'buttons.primary', title: 'Primary', navInstructions: ['x'], description: '', screenshot: 'buttons/primary.png'),
        ]),
      ],
    );

    test('manifest includes summary, app, device, and per-screen rows', () {
      final run = RunState(
        runId: 'r1',
        startedAt: 's',
        finishedAt: 'f',
        serial: 'emulator-5554',
        api: 34,
        appGitSha: 'abc1234',
        records: {'buttons.primary': ScreenRecord(id: 'buttons.primary', status: CaptureStatus.captured, file: 'buttons/primary.png')},
      );
      final m = RunReporter(const HarnessPaths()).buildManifest(run, catalog);
      expect((m['summary'] as Map)['captured'], 1);
      expect((m['app'] as Map)['gitSha'], 'abc1234');
      expect((m['device'] as Map)['api'], 34);
      final screens = m['screens'] as List;
      expect(screens.single, containsPair('category', 'Buttons'));
      expect(screens.single, containsPair('status', 'captured'));
    });
  });
}
