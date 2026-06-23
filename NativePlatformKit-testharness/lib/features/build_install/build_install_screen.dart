import 'package:flutter/cupertino.dart';
import 'package:macos_ui/macos_ui.dart';
import 'package:provider/provider.dart';

import '../../app/app_state.dart';
import 'build_install_view_model.dart';

/// Section that builds the playground APK and installs/launches it on the emulator (Epic E4 / M4).
class BuildInstallScreen extends StatelessWidget {
  /// Creates the Build & Install section.
  const BuildInstallScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final vm = context.watch<BuildInstallViewModel>();
    return MacosScaffold(
      toolBar: ToolBar(title: Text(AppSection.buildInstall.title)),
      children: [
        ContentArea(
          builder: (context, scrollController) {
            return SingleChildScrollView(
              controller: scrollController,
              padding: const EdgeInsets.all(20),
              child: _Body(vm: vm),
            );
          },
        ),
      ],
    );
  }
}

class _Body extends StatelessWidget {
  const _Body({required this.vm});

  final BuildInstallViewModel vm;

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        _PhaseBanner(vm: vm),
        const SizedBox(height: 16),
        Text('Playground project', style: theme.typography.title3),
        const SizedBox(height: 6),
        Row(
          children: [
            Expanded(
              child: Text(
                vm.playgroundPath,
                style: theme.typography.caption1.copyWith(fontFamily: 'Menlo'),
              ),
            ),
            const SizedBox(width: 10),
            PushButton(
              controlSize: ControlSize.regular,
              secondary: true,
              onPressed: vm.isBusy ? null : vm.choosePlaygroundFolder,
              child: const Text('Choose…'),
            ),
          ],
        ),
        const SizedBox(height: 8),
        Text(
          'Pipeline: ./gradlew :app:assembleDebug  →  adb install -r  →  am start ${vm.launchComponent}',
          style: theme.typography.caption1.copyWith(
            color: theme.typography.caption1.color?.withValues(alpha: 0.7),
          ),
        ),
        const SizedBox(height: 16),
        PushButton(
          controlSize: ControlSize.large,
          onPressed: vm.isBusy ? null : vm.buildInstallLaunch,
          child: const Text('Build, Install & Launch'),
        ),
        const SizedBox(height: 8),
        Text(
          'Requires a running emulator (Emulator tab).',
          style: theme.typography.caption1.copyWith(
            color: theme.typography.caption1.color?.withValues(alpha: 0.6),
          ),
        ),
        if (vm.log.isNotEmpty) ...[
          const SizedBox(height: 16),
          _LogPanel(lines: vm.log),
        ],
      ],
    );
  }
}

class _PhaseBanner extends StatelessWidget {
  const _PhaseBanner({required this.vm});

  final BuildInstallViewModel vm;

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    final (label, color, icon) = switch (vm.phase) {
      BuildPhase.idle => ('Ready', MacosColors.systemGrayColor, CupertinoIcons.hammer),
      BuildPhase.building => ('Building…', theme.primaryColor, CupertinoIcons.hammer),
      BuildPhase.installing => ('Installing…', theme.primaryColor, CupertinoIcons.tray_arrow_down),
      BuildPhase.launching => ('Launching…', theme.primaryColor, CupertinoIcons.rocket),
      BuildPhase.success => ('Installed & launched', MacosColors.systemGreenColor, CupertinoIcons.checkmark_seal_fill),
      BuildPhase.failed => ('Failed — see log', MacosColors.systemRedColor, CupertinoIcons.xmark_octagon),
    };
    return Container(
      width: double.infinity,
      padding: const EdgeInsets.all(14),
      decoration: BoxDecoration(
        color: color.withValues(alpha: 0.10),
        borderRadius: BorderRadius.circular(10),
        border: Border.all(color: color.withValues(alpha: 0.4)),
      ),
      child: Row(
        children: [
          if (vm.isBusy)
            const SizedBox(width: 18, height: 18, child: ProgressCircle())
          else
            MacosIcon(icon, color: color),
          const SizedBox(width: 10),
          Expanded(child: Text(label, style: theme.typography.body)),
        ],
      ),
    );
  }
}

class _LogPanel extends StatelessWidget {
  const _LogPanel({required this.lines});

  final List<String> lines;

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    return Container(
      height: 280,
      width: double.infinity,
      padding: const EdgeInsets.all(10),
      decoration: BoxDecoration(
        color: theme.brightness == Brightness.dark
            ? const Color(0xFF1E1E1E)
            : const Color(0xFFF2F2F2),
        borderRadius: BorderRadius.circular(8),
        border: Border.all(color: theme.dividerColor),
      ),
      child: ListView.builder(
        reverse: true,
        itemCount: lines.length,
        itemBuilder: (context, i) {
          final line = lines[lines.length - 1 - i];
          return Text(
            line,
            style: const TextStyle(fontFamily: 'Menlo', fontSize: 11.5, height: 1.4),
          );
        },
      ),
    );
  }
}
