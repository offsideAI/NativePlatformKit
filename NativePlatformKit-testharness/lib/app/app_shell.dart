import 'package:flutter/cupertino.dart';
import 'package:macos_ui/macos_ui.dart';
import 'package:provider/provider.dart';

import '../features/build_install/build_install_screen.dart';
import '../features/console/console_drawer.dart';
import '../features/console/console_view_model.dart';
import '../features/emulator/emulator_screen.dart';
import '../features/environment/environment_screen.dart';
import '../features/flow_runner/flow_runner_screen.dart';
import '../features/gallery/gallery_screen.dart';
import '../features/settings/settings_screen.dart';
import 'app_state.dart';
import 'widgets/status_bar.dart';
import 'widgets/wizard_banner.dart';

/// The top-level window: a macOS sidebar over an [IndexedStack] of the six section
/// screens, with the guided-setup [WizardBanner] shown until setup is complete (D15).
class AppShell extends StatelessWidget {
  /// Creates the app shell.
  const AppShell({super.key});

  static const List<Widget> _pages = [
    EnvironmentScreen(),
    EmulatorScreen(),
    BuildInstallScreen(),
    FlowRunnerScreen(),
    GalleryScreen(),
    SettingsScreen(),
  ];

  @override
  Widget build(BuildContext context) {
    final state = context.watch<AppState>();
    final consoleOpen = context.select<ConsoleViewModel, bool>((vm) => vm.isOpen);
    final showWizard = !state.wizardComplete;

    return MacosWindow(
      sidebar: Sidebar(
        minWidth: 220,
        builder: (context, scrollController) {
          return SidebarItems(
            currentIndex: state.index,
            onChanged: state.select,
            scrollController: scrollController,
            items: [
              for (final section in AppSection.values)
                SidebarItem(
                  leading: MacosIcon(section.icon),
                  label: Text(section.title),
                ),
            ],
          );
        },
      ),
      child: Column(
        children: [
          if (showWizard) const WizardBanner(),
          Expanded(
            child: IndexedStack(
              index: state.index,
              children: _pages,
            ),
          ),
          if (consoleOpen) const ConsoleDrawer(),
          const StatusBar(),
        ],
      ),
    );
  }
}
