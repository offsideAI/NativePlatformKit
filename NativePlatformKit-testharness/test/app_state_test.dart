import 'package:flutter_test/flutter_test.dart';
import 'package:npk_test_harness/app/app_state.dart';

void main() {
  group('AppState', () {
    test('starts on Environment with nothing ready', () {
      final state = AppState();
      expect(state.section, AppSection.environment);
      expect(state.nextSetupStep, AppSection.environment);
      expect(state.canFreelyNavigate, isFalse);
      expect(state.wizardComplete, isFalse);
    });

    test('nextSetupStep advances as readiness flags flip', () {
      final state = AppState();
      state.setEnvironmentReady(true);
      expect(state.nextSetupStep, AppSection.emulator);
      state.setEmulatorReady(true);
      expect(state.nextSetupStep, AppSection.buildInstall);
      state.setAppInstalled(true);
      expect(state.nextSetupStep, isNull);
      expect(state.canFreelyNavigate, isTrue);
    });

    test('completeWizard unlocks free navigation even before setup is done', () {
      final state = AppState();
      expect(state.canFreelyNavigate, isFalse);
      state.completeWizard();
      expect(state.wizardComplete, isTrue);
      expect(state.canFreelyNavigate, isTrue);
    });

    test('select and goTo change the active section and notify', () {
      final state = AppState();
      var notifications = 0;
      state.addListener(() => notifications++);

      state.goTo(AppSection.flowRunner);
      expect(state.section, AppSection.flowRunner);

      state.select(AppSection.flowRunner.index); // same index → no extra notify
      expect(notifications, 1);

      state.select(AppSection.gallery.index);
      expect(state.section, AppSection.gallery);
      expect(notifications, 2);
    });
  });
}
