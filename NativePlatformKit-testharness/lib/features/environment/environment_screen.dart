import 'package:flutter/cupertino.dart';
import 'package:flutter/scheduler.dart';
import 'package:macos_ui/macos_ui.dart';
import 'package:provider/provider.dart';

import '../../app/app_state.dart';
import 'environment_report.dart';
import 'environment_view_model.dart';

/// Section that detects tooling and installs the Android SDK pieces needed to create an emulator
/// (Epic E2 / M2).
class EnvironmentScreen extends StatefulWidget {
  /// Creates the Environment section.
  const EnvironmentScreen({super.key});

  @override
  State<EnvironmentScreen> createState() => _EnvironmentScreenState();
}

class _EnvironmentScreenState extends State<EnvironmentScreen> {
  @override
  void initState() {
    super.initState();
    // Probe once when first shown.
    SchedulerBinding.instance.addPostFrameCallback((_) {
      final vm = context.read<EnvironmentViewModel>();
      if (vm.report == null && !vm.isDetecting) vm.detect();
    });
  }

  @override
  Widget build(BuildContext context) {
    final vm = context.watch<EnvironmentViewModel>();
    return MacosScaffold(
      toolBar: ToolBar(
        title: Text(AppSection.environment.title),
        actions: [
          ToolBarIconButton(
            label: 'Re-detect',
            icon: const MacosIcon(CupertinoIcons.arrow_clockwise),
            showLabel: true,
            onPressed: vm.isBusy ? null : vm.detect,
          ),
        ],
      ),
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

  final EnvironmentViewModel vm;

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    final report = vm.report;

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        _ReadinessBanner(vm: vm),
        const SizedBox(height: 16),
        Text('Toolchain', style: theme.typography.title3),
        const SizedBox(height: 8),
        if (report == null)
          const Padding(
            padding: EdgeInsets.symmetric(vertical: 24),
            child: Center(child: ProgressCircle()),
          )
        else
          ...report.tools.map((t) => _ToolRow(status: t)),
        const SizedBox(height: 8),
        if (report != null)
          Text(
            'SDK root: ${report.sdkRoot}',
            style: theme.typography.caption1.copyWith(
              color: theme.typography.caption1.color?.withValues(alpha: 0.7),
            ),
          ),
        const SizedBox(height: 20),
        _InstallActions(vm: vm),
        if (vm.log.isNotEmpty) ...[
          const SizedBox(height: 16),
          _InstallLog(lines: vm.log),
        ],
      ],
    );
  }
}

class _ReadinessBanner extends StatelessWidget {
  const _ReadinessBanner({required this.vm});

  final EnvironmentViewModel vm;

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    final ready = vm.report?.canCreateEmulator ?? false;
    final color = ready ? MacosColors.systemGreenColor : theme.primaryColor;
    final text = vm.report == null
        ? 'Detecting toolchain…'
        : ready
            ? 'Ready to create an emulator.'
            : 'Some components are missing — install them below to continue.';
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
          MacosIcon(
            ready ? CupertinoIcons.checkmark_seal_fill : CupertinoIcons.exclamationmark_triangle,
            color: color,
          ),
          const SizedBox(width: 10),
          Expanded(child: Text(text, style: theme.typography.body)),
        ],
      ),
    );
  }
}

class _ToolRow extends StatelessWidget {
  const _ToolRow({required this.status});

  final ToolStatus status;

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    final color = status.found ? MacosColors.systemGreenColor : MacosColors.systemRedColor;
    final subtitle = status.found ? (status.version ?? status.path ?? '') : (status.hint ?? 'Not found');
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 5),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          MacosIcon(
            status.found ? CupertinoIcons.checkmark_circle_fill : CupertinoIcons.xmark_circle_fill,
            color: color,
            size: 18,
          ),
          const SizedBox(width: 10),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(status.label, style: theme.typography.body),
                if (subtitle.isNotEmpty)
                  Text(
                    subtitle,
                    style: theme.typography.caption1.copyWith(
                      color: theme.typography.caption1.color?.withValues(alpha: 0.7),
                    ),
                  ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class _InstallActions extends StatelessWidget {
  const _InstallActions({required this.vm});

  final EnvironmentViewModel vm;

  @override
  Widget build(BuildContext context) {
    final report = vm.report;
    if (report == null) return const SizedBox.shrink();

    final needsCmdlineTools = !report.hasCmdlineTools;
    final needsImage = report.hasCmdlineTools && !report.hasSystemImage;

    if (!needsCmdlineTools && !needsImage) return const SizedBox.shrink();

    return Wrap(
      spacing: 10,
      runSpacing: 10,
      children: [
        if (needsCmdlineTools)
          PushButton(
            controlSize: ControlSize.large,
            onPressed: vm.isBusy ? null : vm.installCmdlineTools,
            child: Text(
              vm.installing == InstallTask.cmdlineTools
                  ? 'Installing command-line tools…'
                  : 'Install command-line tools',
            ),
          ),
        if (needsImage)
          PushButton(
            controlSize: ControlSize.large,
            onPressed: vm.isBusy ? null : vm.installSystemImage,
            child: Text(
              vm.installing == InstallTask.systemImage
                  ? 'Installing system image…'
                  : 'Install system image (API 34)',
            ),
          ),
      ],
    );
  }
}

class _InstallLog extends StatelessWidget {
  const _InstallLog({required this.lines});

  final List<String> lines;

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    return Container(
      height: 180,
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
