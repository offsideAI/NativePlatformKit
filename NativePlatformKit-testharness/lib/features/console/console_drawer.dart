import 'package:flutter/cupertino.dart';
import 'package:flutter/scheduler.dart';
import 'package:macos_ui/macos_ui.dart';
import 'package:provider/provider.dart';

import 'console_view_model.dart';

/// A bottom drawer that runs commands through the native bridge and tails their output.
/// Also the M1 bridge demonstration (echo / sleep+cancel / adb version / pick directory).
class ConsoleDrawer extends StatefulWidget {
  /// Creates the console drawer.
  const ConsoleDrawer({super.key});

  @override
  State<ConsoleDrawer> createState() => _ConsoleDrawerState();
}

class _ConsoleDrawerState extends State<ConsoleDrawer> {
  final _scrollController = ScrollController();

  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }

  void _scrollToBottom() {
    SchedulerBinding.instance.addPostFrameCallback((_) {
      if (_scrollController.hasClients) {
        _scrollController.jumpTo(_scrollController.position.maxScrollExtent);
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    final vm = context.watch<ConsoleViewModel>();
    final theme = MacosTheme.of(context);
    _scrollToBottom();

    return Container(
      height: 240,
      decoration: BoxDecoration(
        color: theme.canvasColor,
        border: Border(top: BorderSide(color: theme.dividerColor)),
      ),
      child: Column(
        children: [
          _toolbar(context, vm, theme),
          Expanded(
            child: Container(
              width: double.infinity,
              color: theme.brightness == Brightness.dark
                  ? const Color(0xFF1E1E1E)
                  : const Color(0xFFF7F7F7),
              child: ListView.builder(
                controller: _scrollController,
                padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
                itemCount: vm.lines.length,
                itemBuilder: (context, i) {
                  final line = vm.lines[i];
                  return Text(
                    line.text,
                    style: TextStyle(
                      fontFamily: 'Menlo',
                      fontSize: 12,
                      height: 1.4,
                      color: switch (line.kind) {
                        ConsoleLineKind.stderr => MacosColors.systemRedColor,
                        ConsoleLineKind.info => theme.primaryColor,
                        ConsoleLineKind.stdout =>
                          theme.typography.body.color ?? MacosColors.textColor,
                      },
                    ),
                  );
                },
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _toolbar(BuildContext context, ConsoleViewModel vm, MacosThemeData theme) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
      child: Row(
        children: [
          Text('Developer Console', style: theme.typography.headline),
          const SizedBox(width: 16),
          _action(context, 'Echo', vm.isRunning ? null : vm.runEcho),
          _action(context, 'Sleep 30s', vm.isRunning ? null : vm.runSleep),
          _action(context, 'adb version', vm.isRunning ? null : vm.runAdbVersion),
          _action(context, 'Pick dir…', vm.pickDirectory),
          const Spacer(),
          if (vm.isRunning)
            _action(context, 'Cancel', vm.cancel, destructive: true),
          _action(context, 'Clear', vm.clear),
          _action(context, 'Close', context.read<ConsoleViewModel>().toggle),
        ],
      ),
    );
  }

  Widget _action(
    BuildContext context,
    String label,
    VoidCallback? onPressed, {
    bool destructive = false,
  }) {
    return Padding(
      padding: const EdgeInsets.only(left: 6),
      child: PushButton(
        controlSize: ControlSize.small,
        secondary: !destructive,
        onPressed: onPressed,
        child: Text(label),
      ),
    );
  }
}
