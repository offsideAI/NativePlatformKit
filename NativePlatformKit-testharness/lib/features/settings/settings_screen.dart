import 'package:flutter/cupertino.dart';
import 'package:macos_ui/macos_ui.dart';

import '../../app/app_state.dart';
import '../../app/widgets/placeholder_content.dart';

/// Section for configurable paths and preferences (E8 / M8).
class SettingsScreen extends StatelessWidget {
  /// Creates the Settings section.
  const SettingsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return MacosScaffold(
      toolBar: ToolBar(title: Text(AppSection.settings.title)),
      children: [
        ContentArea(
          builder: (context, _) {
            return const PlaceholderContent(
              icon: CupertinoIcons.gear_alt,
              title: 'Settings',
              milestone: 'M8 · Epic E8',
              summary:
                  'Detected tool paths (SDK, adb, JDK, Gradle), AVD profile, and the screenshot '
                  'output directory — stored in the project-local .harness/ config.',
            );
          },
        ),
      ],
    );
  }
}
