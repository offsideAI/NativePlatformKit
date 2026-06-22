import 'package:flutter/cupertino.dart';
import 'package:macos_ui/macos_ui.dart';

import '../../app/app_state.dart';
import '../../app/widgets/placeholder_content.dart';

/// Section to browse captured runs and their screenshots (E7 / M7).
class GalleryScreen extends StatelessWidget {
  /// Creates the Gallery section.
  const GalleryScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return MacosScaffold(
      toolBar: ToolBar(title: Text(AppSection.gallery.title)),
      children: [
        ContentArea(
          builder: (context, _) {
            return const PlaceholderContent(
              icon: CupertinoIcons.photo_on_rectangle,
              title: 'Gallery',
              milestone: 'M7 · Epic E7',
              summary:
                  'Browse captured runs: thumbnail grid by category, full-size viewer, and '
                  'per-run manifest + Markdown/CSV reports.',
            );
          },
        ),
      ],
    );
  }
}
