import 'package:flutter/cupertino.dart';
import 'package:macos_ui/macos_ui.dart';
import 'package:provider/provider.dart';

import '../../app/app_state.dart';
import '../../app/widgets/placeholder_content.dart';

/// Section that builds the playground APK and installs it on the emulator (E4 / M4).
class BuildInstallScreen extends StatelessWidget {
  /// Creates the Build & Install section.
  const BuildInstallScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return MacosScaffold(
      toolBar: ToolBar(title: Text(AppSection.buildInstall.title)),
      children: [
        ContentArea(
          builder: (context, _) {
            return Stack(
              children: [
                const PlaceholderContent(
                  icon: CupertinoIcons.hammer,
                  title: 'Build & Install',
                  milestone: 'M4 · Epic E4',
                  summary:
                      'Run ./gradlew :app:assembleDebug with live output, then adb install -r '
                      'and launch the playground on the emulator.',
                ),
                Positioned(
                  right: 20,
                  bottom: 20,
                  child: PushButton(
                    controlSize: ControlSize.regular,
                    secondary: true,
                    onPressed: () =>
                        context.read<AppState>().setAppInstalled(true),
                    child: const Text('Mark installed (dev stub)'),
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
