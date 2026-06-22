import 'package:flutter/cupertino.dart';
import 'package:macos_ui/macos_ui.dart';
import 'package:provider/provider.dart';

import '../../app/app_state.dart';
import '../../app/widgets/placeholder_content.dart';

/// Section that detects tooling and installs the Android SDK pieces (E2 / M2).
class EnvironmentScreen extends StatelessWidget {
  /// Creates the Environment section.
  const EnvironmentScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return MacosScaffold(
      toolBar: ToolBar(title: Text(AppSection.environment.title)),
      children: [
        ContentArea(
          builder: (context, _) {
            return Stack(
              children: [
                const PlaceholderContent(
                  icon: CupertinoIcons.checkmark_shield,
                  title: 'Environment',
                  milestone: 'M2 · Epic E2',
                  summary:
                      'Detect Flutter, JDK, adb, emulator and the Android SDK — and install '
                      'cmdline-tools + the API 34 system image in-app.',
                ),
                // Dev stub so the wizard → free-navigation transition is demonstrable
                // before real detection lands. Removed in E2.
                Positioned(
                  right: 20,
                  bottom: 20,
                  child: PushButton(
                    controlSize: ControlSize.regular,
                    secondary: true,
                    onPressed: () =>
                        context.read<AppState>().setEnvironmentReady(true),
                    child: const Text('Mark ready (dev stub)'),
                  ),
                ),
              ],
            );
          },
        ),
      ],
    );
  }
}
