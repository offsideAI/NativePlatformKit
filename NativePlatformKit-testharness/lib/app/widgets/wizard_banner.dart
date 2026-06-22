import 'package:flutter/cupertino.dart';
import 'package:macos_ui/macos_ui.dart';
import 'package:provider/provider.dart';

import '../app_state.dart';

/// A slim banner shown during first-run guided setup (D15). It points at the next
/// setup step, and once the environment, emulator, and install are ready it offers
/// to start capturing. Disappears once [AppState.canFreelyNavigate] is true and the
/// wizard has been completed.
class WizardBanner extends StatelessWidget {
  /// Creates the guided-setup banner.
  const WizardBanner({super.key});

  @override
  Widget build(BuildContext context) {
    final state = context.watch<AppState>();
    final theme = MacosTheme.of(context);
    final next = state.nextSetupStep;

    final String message;
    final String actionLabel;
    final VoidCallback onAction;

    if (next != null) {
      final stepNumber = next.index + 1; // environment=0, emulator=1, buildInstall=2
      message = 'Guided setup — step $stepNumber of 3: ${next.title}';
      actionLabel = 'Go to ${next.title}';
      onAction = () => state.goTo(next);
    } else {
      message = 'Setup complete — ready to capture screens.';
      actionLabel = 'Start capturing';
      onAction = () {
        state.completeWizard();
        state.goTo(AppSection.flowRunner);
      };
    }

    return Container(
      width: double.infinity,
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 10),
      color: theme.primaryColor.withValues(alpha: 0.10),
      child: Row(
        children: [
          MacosIcon(
            next != null ? CupertinoIcons.wand_stars : CupertinoIcons.checkmark_seal,
            size: 16,
            color: theme.primaryColor,
          ),
          const SizedBox(width: 8),
          Expanded(
            child: Text(message, style: theme.typography.body),
          ),
          PushButton(
            controlSize: ControlSize.small,
            onPressed: onAction,
            child: Text(actionLabel),
          ),
          const SizedBox(width: 8),
          PushButton(
            controlSize: ControlSize.small,
            secondary: true,
            onPressed: state.completeWizard,
            child: const Text('Skip setup'),
          ),
        ],
      ),
    );
  }
}
