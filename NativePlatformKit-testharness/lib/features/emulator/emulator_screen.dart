import 'package:flutter/cupertino.dart';
import 'package:flutter/scheduler.dart';
import 'package:macos_ui/macos_ui.dart';
import 'package:provider/provider.dart';

import '../../app/app_state.dart';
import 'emulator_view_model.dart';

/// Section that creates, boots, and stops the Pixel 6 / API 34 AVD (Epic E3 / M3).
class EmulatorScreen extends StatefulWidget {
  /// Creates the Emulator section.
  const EmulatorScreen({super.key});

  @override
  State<EmulatorScreen> createState() => _EmulatorScreenState();
}

class _EmulatorScreenState extends State<EmulatorScreen> {
  @override
  void initState() {
    super.initState();
    SchedulerBinding.instance.addPostFrameCallback((_) {
      final vm = context.read<EmulatorViewModel>();
      if (vm.state == EmulatorState.unknown) vm.refresh();
    });
  }

  @override
  Widget build(BuildContext context) {
    final vm = context.watch<EmulatorViewModel>();
    return MacosScaffold(
      toolBar: ToolBar(
        title: Text(AppSection.emulator.title),
        actions: [
          ToolBarIconButton(
            label: 'Refresh',
            icon: const MacosIcon(CupertinoIcons.arrow_clockwise),
            showLabel: true,
            onPressed: vm.isBusy ? null : vm.refresh,
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

  final EmulatorViewModel vm;

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        _StatusBanner(vm: vm),
        const SizedBox(height: 16),
        Text('AVD: ${vm.avdName}', style: theme.typography.title3),
        const SizedBox(height: 4),
        Text(
          'Pixel 6 · API 34 · google_apis · arm64-v8a · 4 GB RAM · hardware GPU',
          style: theme.typography.caption1.copyWith(
            color: theme.typography.caption1.color?.withValues(alpha: 0.7),
          ),
        ),
        const SizedBox(height: 16),
        _Actions(vm: vm),
        const SizedBox(height: 12),
        _Options(vm: vm),
        if (vm.log.isNotEmpty) ...[
          const SizedBox(height: 16),
          _LogPanel(lines: vm.log),
        ],
      ],
    );
  }
}

class _StatusBanner extends StatelessWidget {
  const _StatusBanner({required this.vm});

  final EmulatorViewModel vm;

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    final (label, color, icon) = switch (vm.state) {
      EmulatorState.online => (
          'Online — ${vm.serial}',
          MacosColors.systemGreenColor,
          CupertinoIcons.checkmark_seal_fill,
        ),
      EmulatorState.booting => ('Booting…', theme.primaryColor, CupertinoIcons.hourglass),
      EmulatorState.creating => ('Creating AVD…', theme.primaryColor, CupertinoIcons.hourglass),
      EmulatorState.stopping => ('Stopping…', theme.primaryColor, CupertinoIcons.hourglass),
      EmulatorState.stopped => ('Stopped', MacosColors.systemGrayColor, CupertinoIcons.stop_circle),
      EmulatorState.noAvd => (
          'No AVD yet — create one to begin',
          MacosColors.systemOrangeColor,
          CupertinoIcons.exclamationmark_triangle,
        ),
      EmulatorState.unknown => ('Checking…', MacosColors.systemGrayColor, CupertinoIcons.question_circle),
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

class _Actions extends StatelessWidget {
  const _Actions({required this.vm});

  final EmulatorViewModel vm;

  @override
  Widget build(BuildContext context) {
    final online = vm.state == EmulatorState.online;
    final canBoot = vm.hasTargetAvd && !vm.isBusy && !online;
    return Wrap(
      spacing: 10,
      runSpacing: 10,
      children: [
        if (!vm.hasTargetAvd)
          PushButton(
            controlSize: ControlSize.large,
            onPressed: vm.isBusy ? null : vm.createAvd,
            child: const Text('Create AVD'),
          ),
        if (vm.hasTargetAvd)
          PushButton(
            controlSize: ControlSize.large,
            onPressed: canBoot ? vm.bootAndWait : null,
            child: const Text('Boot emulator'),
          ),
        if (online || vm.state == EmulatorState.booting)
          PushButton(
            controlSize: ControlSize.large,
            secondary: true,
            onPressed: vm.isBusy && vm.state != EmulatorState.booting ? null : vm.stop,
            child: const Text('Stop'),
          ),
      ],
    );
  }
}

class _Options extends StatelessWidget {
  const _Options({required this.vm});

  final EmulatorViewModel vm;

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    final enabled = !vm.isBusy && vm.state != EmulatorState.online;
    return Row(
      children: [
        MacosCheckbox(
          value: vm.coldBoot,
          onChanged: enabled ? (v) => vm.coldBoot = v : null,
        ),
        const SizedBox(width: 6),
        Text('Cold boot', style: theme.typography.body),
        const SizedBox(width: 20),
        MacosCheckbox(
          value: vm.wipeData,
          onChanged: enabled ? (v) => vm.wipeData = v : null,
        ),
        const SizedBox(width: 6),
        Text('Wipe data', style: theme.typography.body),
      ],
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
      height: 220,
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
