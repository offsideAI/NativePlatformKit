import 'package:flutter/cupertino.dart';
import 'package:macos_ui/macos_ui.dart';
import 'package:provider/provider.dart';

import '../../features/console/console_view_model.dart';

/// A slim bottom status bar with a Developer Console toggle (and room for device/run status later).
class StatusBar extends StatelessWidget {
  /// Creates the status bar.
  const StatusBar({super.key});

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    final console = context.watch<ConsoleViewModel>();

    return Container(
      height: 28,
      padding: const EdgeInsets.symmetric(horizontal: 12),
      decoration: BoxDecoration(
        color: theme.canvasColor,
        border: Border(top: BorderSide(color: theme.dividerColor)),
      ),
      child: Row(
        children: [
          if (console.isRunning) ...[
            const SizedBox(
              width: 12,
              height: 12,
              child: ProgressCircle(radius: 6),
            ),
            const SizedBox(width: 6),
            Text('Running…', style: theme.typography.caption1),
          ],
          const Spacer(),
          _ConsoleToggle(active: console.isOpen, onPressed: console.toggle),
        ],
      ),
    );
  }
}

class _ConsoleToggle extends StatelessWidget {
  const _ConsoleToggle({required this.active, required this.onPressed});

  final bool active;
  final VoidCallback onPressed;

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    return MacosTooltip(
      message: 'Toggle Developer Console',
      child: GestureDetector(
        onTap: onPressed,
        child: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            MacosIcon(
              CupertinoIcons.chevron_left_slash_chevron_right,
              size: 14,
              color: active ? theme.primaryColor : theme.typography.caption1.color,
            ),
            const SizedBox(width: 4),
            Text(
              'Console',
              style: theme.typography.caption1.copyWith(
                color: active ? theme.primaryColor : theme.typography.caption1.color,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
