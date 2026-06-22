import 'package:flutter/cupertino.dart';
import 'package:macos_ui/macos_ui.dart';
import 'package:provider/provider.dart';

import '../../app/app_state.dart';
import '../../app/widgets/placeholder_content.dart';

/// Section that creates, boots, and stops the Pixel 6 / API 34 AVD (E3 / M3).
class EmulatorScreen extends StatelessWidget {
  /// Creates the Emulator section.
  const EmulatorScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return MacosScaffold(
      toolBar: ToolBar(title: Text(AppSection.emulator.title)),
      children: [
        ContentArea(
          builder: (context, _) {
            return Stack(
              children: [
                const PlaceholderContent(
                  icon: CupertinoIcons.device_phone_portrait,
                  title: 'Emulator',
                  milestone: 'M3 · Epic E3',
                  summary:
                      'Create the Pixel 6 (API 34, google_apis, arm64) AVD, boot it with '
                      'hardware GPU, and detect boot completion.',
                ),
                Positioned(
                  right: 20,
                  bottom: 20,
                  child: PushButton(
                    controlSize: ControlSize.regular,
                    secondary: true,
                    onPressed: () =>
                        context.read<AppState>().setEmulatorReady(true),
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
