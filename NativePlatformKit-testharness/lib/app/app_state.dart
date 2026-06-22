import 'package:flutter/cupertino.dart';

/// The six top-level sections of the harness, in wizard order.
enum AppSection { environment, emulator, buildInstall, flowRunner, gallery, settings }

/// Display metadata for [AppSection].
extension AppSectionMeta on AppSection {
  /// Human-readable section title (shown in the sidebar and toolbar).
  String get title => switch (this) {
        AppSection.environment => 'Environment',
        AppSection.emulator => 'Emulator',
        AppSection.buildInstall => 'Build & Install',
        AppSection.flowRunner => 'Flow Runner',
        AppSection.gallery => 'Gallery',
        AppSection.settings => 'Settings',
      };

  /// The sidebar icon for this section.
  IconData get icon => switch (this) {
        AppSection.environment => CupertinoIcons.checkmark_shield,
        AppSection.emulator => CupertinoIcons.device_phone_portrait,
        AppSection.buildInstall => CupertinoIcons.hammer,
        AppSection.flowRunner => CupertinoIcons.play_rectangle,
        AppSection.gallery => CupertinoIcons.photo_on_rectangle,
        AppSection.settings => CupertinoIcons.gear_alt,
      };
}

/// Root application state (Provider / `ChangeNotifier`).
///
/// Holds the selected section, the setup-readiness flags that drive the guided
/// wizard (D15), and whether the user has left wizard mode for free navigation.
/// Readiness flags are stubbed here and will be set by the Environment, Emulator,
/// and Build & Install view models in later milestones (E2–E4).
class AppState extends ChangeNotifier {
  int _index = 0;

  /// Index of the currently selected section.
  int get index => _index;

  /// The currently selected section.
  AppSection get section => AppSection.values[_index];

  /// Whether the Android SDK + tooling are detected and ready (set by E2).
  bool environmentReady = false;

  /// Whether an emulator has been booted and is online (set by E3).
  bool emulatorReady = false;

  /// Whether the playground app has been installed on the emulator (set by E4).
  bool appInstalled = false;

  bool _wizardComplete = false;

  /// Whether the user has finished/skipped the guided wizard and may navigate freely.
  bool get wizardComplete => _wizardComplete;

  /// The first setup step that is not yet satisfied, or `null` when fully ready.
  AppSection? get nextSetupStep {
    if (!environmentReady) return AppSection.environment;
    if (!emulatorReady) return AppSection.emulator;
    if (!appInstalled) return AppSection.buildInstall;
    return null;
  }

  /// Whether the sidebar should allow jumping to any section.
  bool get canFreelyNavigate => _wizardComplete || nextSetupStep == null;

  /// Selects the section at [i].
  void select(int i) {
    if (i == _index) return;
    _index = i;
    notifyListeners();
  }

  /// Selects [target].
  void goTo(AppSection target) => select(target.index);

  /// Leaves wizard mode and unlocks free navigation.
  void completeWizard() {
    if (_wizardComplete) return;
    _wizardComplete = true;
    notifyListeners();
  }

  // --- Temporary setters until real detection lands (E2–E4). ---

  /// Stub: marks the environment ready (replaced by EnvironmentViewModel in E2).
  void setEnvironmentReady(bool value) {
    environmentReady = value;
    notifyListeners();
  }

  /// Stub: marks the emulator ready (replaced by EmulatorViewModel in E3).
  void setEmulatorReady(bool value) {
    emulatorReady = value;
    notifyListeners();
  }

  /// Stub: marks the app installed (replaced by BuildInstallViewModel in E4).
  void setAppInstalled(bool value) {
    appInstalled = value;
    notifyListeners();
  }
}
