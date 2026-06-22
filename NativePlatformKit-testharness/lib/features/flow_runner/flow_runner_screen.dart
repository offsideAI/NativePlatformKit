import 'package:flutter/cupertino.dart';
import 'package:macos_ui/macos_ui.dart';

import '../../app/app_state.dart';
import '../../app/widgets/placeholder_content.dart';

/// The core guided-capture section: three-pane stepper over the ~78 screens (E6 / M6).
class FlowRunnerScreen extends StatelessWidget {
  /// Creates the Flow Runner section.
  const FlowRunnerScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return MacosScaffold(
      toolBar: ToolBar(title: Text(AppSection.flowRunner.title)),
      children: [
        ContentArea(
          builder: (context, _) {
            return const PlaceholderContent(
              icon: CupertinoIcons.play_rectangle,
              title: 'Flow Runner',
              milestone: 'M6 · Epic E6',
              summary:
                  'Three-pane guided capture: category tree, current-screen instructions with '
                  'Capture / Skip / Note, and a live preview of the last screenshot.',
            );
          },
        ),
      ],
    );
  }
}
