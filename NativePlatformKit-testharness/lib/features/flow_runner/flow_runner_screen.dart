import 'dart:io';

import 'package:flutter/cupertino.dart';
import 'package:flutter/scheduler.dart';
import 'package:macos_ui/macos_ui.dart';
import 'package:provider/provider.dart';

import '../../app/app_state.dart';
import '../../data/flow_catalog/flow_catalog.dart';
import 'flow_runner_view_model.dart';
import 'run_models.dart';

/// The guided three-pane capture experience (Epic E6 / M6).
class FlowRunnerScreen extends StatefulWidget {
  /// Creates the Flow Runner section.
  const FlowRunnerScreen({super.key});

  @override
  State<FlowRunnerScreen> createState() => _FlowRunnerScreenState();
}

class _FlowRunnerScreenState extends State<FlowRunnerScreen> {
  @override
  void initState() {
    super.initState();
    SchedulerBinding.instance.addPostFrameCallback((_) {
      context.read<FlowRunnerViewModel>().init();
    });
  }

  @override
  Widget build(BuildContext context) {
    final vm = context.watch<FlowRunnerViewModel>();
    return MacosScaffold(
      toolBar: ToolBar(
        title: Text(AppSection.flowRunner.title),
        actions: [
          ToolBarIconButton(
            label: vm.isAutoRunning ? 'Stop' : 'Auto Capture',
            icon: MacosIcon(
              vm.isAutoRunning ? CupertinoIcons.stop_fill : CupertinoIcons.wand_stars,
            ),
            showLabel: true,
            onPressed: vm.isAutoRunning
                ? vm.cancelAutoCapture
                : (vm.isBusy ? null : vm.autoCapture),
          ),
          if (vm.run != null && !vm.run!.isFinished)
            ToolBarIconButton(
              label: 'Finish run',
              icon: const MacosIcon(CupertinoIcons.checkmark_seal),
              showLabel: true,
              onPressed: vm.isBusy ? null : vm.finishRun,
            ),
        ],
      ),
      children: [
        ContentArea(
          builder: (context, _) {
            if (vm.catalog == null) {
              return const Center(child: ProgressCircle());
            }
            final divider = Container(width: 1, color: MacosTheme.of(context).dividerColor);
            return Row(
              children: [
                SizedBox(width: 300, child: _TreePane(vm: vm)),
                divider,
                Expanded(flex: 3, child: _DetailPane(key: ValueKey(vm.selectedId), vm: vm)),
                divider,
                SizedBox(width: 360, child: _PreviewPane(vm: vm)),
              ],
            );
          },
        ),
      ],
    );
  }
}

// ---------------------------------------------------------------------------
// Left: category → screen tree with progress + run controls.
// ---------------------------------------------------------------------------

class _TreePane extends StatelessWidget {
  const _TreePane({required this.vm});

  final FlowRunnerViewModel vm;

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    final run = vm.run;
    return Column(
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: [
        Padding(
          padding: const EdgeInsets.all(12),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              if (run == null)
                PushButton(
                  controlSize: ControlSize.large,
                  onPressed: vm.isBusy ? null : vm.startRun,
                  child: const Text('Start capture run'),
                )
              else ...[
                Text(
                  '${run.captured} captured · ${run.skipped} skipped · ${run.failed} failed',
                  style: theme.typography.caption1,
                ),
                const SizedBox(height: 6),
                ProgressBar(value: run.progress * 100),
                const SizedBox(height: 8),
                Row(
                  children: [
                    PushButton(
                      controlSize: ControlSize.small,
                      secondary: true,
                      onPressed: vm.isBusy ? null : vm.startRun,
                      child: const Text('New run'),
                    ),
                  ],
                ),
              ],
            ],
          ),
        ),
        Container(height: 1, color: theme.dividerColor),
        Expanded(
          child: ListView(
            padding: const EdgeInsets.symmetric(vertical: 6),
            children: [
              for (final category in vm.catalog!.categories) ...[
                Padding(
                  padding: const EdgeInsets.fromLTRB(12, 10, 12, 4),
                  child: Text(
                    category.title.toUpperCase(),
                    style: theme.typography.caption2.copyWith(
                      fontWeight: FontWeight.bold,
                      color: theme.typography.caption2.color?.withValues(alpha: 0.6),
                    ),
                  ),
                ),
                for (final screen in category.screens)
                  _ScreenRow(
                    vm: vm,
                    screen: screen,
                    selected: screen.id == vm.selectedId,
                    status: vm.recordOf(screen.id)?.status ?? CaptureStatus.pending,
                  ),
              ],
            ],
          ),
        ),
      ],
    );
  }
}

class _ScreenRow extends StatelessWidget {
  const _ScreenRow({
    required this.vm,
    required this.screen,
    required this.selected,
    required this.status,
  });

  final FlowRunnerViewModel vm;
  final FlowScreen screen;
  final bool selected;
  final CaptureStatus status;

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    return GestureDetector(
      onTap: () => vm.select(screen.id),
      child: Container(
        color: selected ? theme.primaryColor.withValues(alpha: 0.15) : null,
        padding: const EdgeInsets.fromLTRB(16, 6, 12, 6),
        child: Row(
          children: [
            _StatusDot(status: status),
            const SizedBox(width: 8),
            Expanded(
              child: Text(
                screen.title,
                maxLines: 1,
                overflow: TextOverflow.ellipsis,
                style: theme.typography.body,
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class _StatusDot extends StatelessWidget {
  const _StatusDot({required this.status});

  final CaptureStatus status;

  @override
  Widget build(BuildContext context) {
    final color = switch (status) {
      CaptureStatus.captured => MacosColors.systemGreenColor,
      CaptureStatus.skipped => MacosColors.systemOrangeColor,
      CaptureStatus.failed => MacosColors.systemRedColor,
      CaptureStatus.pending => MacosColors.systemGrayColor.withValues(alpha: 0.5),
    };
    return Container(
      width: 9,
      height: 9,
      decoration: BoxDecoration(color: color, shape: BoxShape.circle),
    );
  }
}

// ---------------------------------------------------------------------------
// Center: selected screen detail with capture/skip/notes.
// ---------------------------------------------------------------------------

class _DetailPane extends StatefulWidget {
  const _DetailPane({required this.vm, super.key});

  final FlowRunnerViewModel vm;

  @override
  State<_DetailPane> createState() => _DetailPaneState();
}

class _DetailPaneState extends State<_DetailPane> {
  late final TextEditingController _notes;

  @override
  void initState() {
    super.initState();
    _notes = TextEditingController(
      text: widget.vm.recordOf(widget.vm.selectedId ?? '')?.notes ?? '',
    );
  }

  @override
  void dispose() {
    _notes.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final vm = widget.vm;
    final theme = MacosTheme.of(context);
    final screen = vm.selectedScreen;
    if (vm.run == null) {
      return const Center(child: Text('Start a capture run to begin.'));
    }
    if (screen == null) {
      return const Center(child: Text('Select a screen.'));
    }
    final record = vm.recordOf(screen.id);
    return SingleChildScrollView(
      padding: const EdgeInsets.all(20),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(screen.title, style: theme.typography.largeTitle),
          const SizedBox(height: 4),
          Row(
            children: [
              _StatusDot(status: record?.status ?? CaptureStatus.pending),
              const SizedBox(width: 6),
              Text((record?.status ?? CaptureStatus.pending).name, style: theme.typography.caption1),
            ],
          ),
          const SizedBox(height: 16),
          Text('How to get here', style: theme.typography.title3),
          const SizedBox(height: 6),
          for (var i = 0; i < screen.navInstructions.length; i++)
            Padding(
              padding: const EdgeInsets.symmetric(vertical: 2),
              child: Text('${i + 1}. ${screen.navInstructions[i]}', style: theme.typography.body),
            ),
          if (screen.description.isNotEmpty) ...[
            const SizedBox(height: 12),
            Text(screen.description, style: theme.typography.body),
          ],
          const SizedBox(height: 20),
          Row(
            children: [
              PushButton(
                controlSize: ControlSize.large,
                onPressed: vm.isBusy ? null : vm.captureSelected,
                child: Text(vm.isBusy ? 'Capturing…' : 'Capture'),
              ),
              const SizedBox(width: 10),
              PushButton(
                controlSize: ControlSize.large,
                secondary: true,
                onPressed: vm.isBusy ? null : vm.skipSelected,
                child: const Text('Skip'),
              ),
            ],
          ),
          const SizedBox(height: 20),
          Text('Notes', style: theme.typography.title3),
          const SizedBox(height: 6),
          MacosTextField(
            controller: _notes,
            placeholder: 'Optional note for this screen…',
            maxLines: 3,
            onChanged: vm.setNote,
          ),
          if (vm.statusMessage != null) ...[
            const SizedBox(height: 16),
            Text(
              vm.statusMessage!,
              style: theme.typography.caption1.copyWith(
                color: theme.typography.caption1.color?.withValues(alpha: 0.7),
              ),
            ),
          ],
        ],
      ),
    );
  }
}

// ---------------------------------------------------------------------------
// Right: preview of the last capture for the selected screen.
// ---------------------------------------------------------------------------

class _PreviewPane extends StatelessWidget {
  const _PreviewPane({required this.vm});

  final FlowRunnerViewModel vm;

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    final screen = vm.selectedScreen;
    final path = screen == null ? null : vm.capturedPathOf(screen);
    final exists = path != null && File(path).existsSync();
    return Container(
      color: theme.canvasColor,
      padding: const EdgeInsets.all(16),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          Text('Preview', style: theme.typography.headline),
          const SizedBox(height: 12),
          Expanded(
            child: exists
                ? Image.file(
                    File(path),
                    fit: BoxFit.contain,
                    errorBuilder: (_, _, _) => const Center(child: Text('Could not load image')),
                  )
                : Center(
                    child: Text(
                      'No capture yet',
                      style: theme.typography.body.copyWith(
                        color: theme.typography.body.color?.withValues(alpha: 0.5),
                      ),
                    ),
                  ),
          ),
        ],
      ),
    );
  }
}
