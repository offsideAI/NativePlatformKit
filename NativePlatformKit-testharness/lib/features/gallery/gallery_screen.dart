import 'dart:io';

import 'package:flutter/cupertino.dart';
import 'package:flutter/scheduler.dart';
import 'package:macos_ui/macos_ui.dart';
import 'package:provider/provider.dart';

import '../../app/app_state.dart';
import 'gallery_view_model.dart';

/// Section to browse captured runs and their screenshots (Epic E7 / M7).
class GalleryScreen extends StatefulWidget {
  /// Creates the Gallery section.
  const GalleryScreen({super.key});

  @override
  State<GalleryScreen> createState() => _GalleryScreenState();
}

class _GalleryScreenState extends State<GalleryScreen> {
  @override
  void initState() {
    super.initState();
    SchedulerBinding.instance.addPostFrameCallback((_) {
      context.read<GalleryViewModel>().refresh();
    });
  }

  @override
  Widget build(BuildContext context) {
    final vm = context.watch<GalleryViewModel>();
    return MacosScaffold(
      toolBar: ToolBar(
        title: Text(AppSection.gallery.title),
        actions: [
          ToolBarIconButton(
            label: 'Refresh',
            icon: const MacosIcon(CupertinoIcons.arrow_clockwise),
            showLabel: true,
            onPressed: vm.refresh,
          ),
        ],
      ),
      children: [
        ContentArea(
          builder: (context, scrollController) {
            if (vm.runIds.isEmpty) {
              return const Center(child: Text('No capture runs yet. Run the Flow Runner to create one.'));
            }
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

  final GalleryViewModel vm;

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    final manifest = vm.manifest;
    final summary = manifest?['summary'] as Map<String, dynamic>?;
    final screens = (manifest?['screens'] as List<dynamic>? ?? []).cast<Map<String, dynamic>>();

    // Group screens by category, captured first.
    final byCategory = <String, List<Map<String, dynamic>>>{};
    for (final s in screens) {
      byCategory.putIfAbsent((s['category'] as String?) ?? 'Other', () => []).add(s);
    }

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Row(
          children: [
            Text('Run', style: theme.typography.body),
            const SizedBox(width: 10),
            MacosPopupButton<String>(
              value: vm.selectedRunId,
              items: [
                for (final id in vm.runIds) MacosPopupMenuItem(value: id, child: Text(id)),
              ],
              onChanged: (id) {
                if (id != null) vm.select(id);
              },
            ),
          ],
        ),
        if (summary != null) ...[
          const SizedBox(height: 10),
          Text(
            '${summary['captured']} captured · ${summary['skipped']} skipped · '
            '${summary['failed']} failed · ${summary['total']} total',
            style: theme.typography.caption1,
          ),
        ],
        const SizedBox(height: 16),
        for (final entry in byCategory.entries) ...[
          Text(entry.key, style: theme.typography.title3),
          const SizedBox(height: 8),
          Wrap(
            spacing: 12,
            runSpacing: 12,
            children: [
              for (final screen in entry.value) _Thumb(vm: vm, screen: screen),
            ],
          ),
          const SizedBox(height: 20),
        ],
      ],
    );
  }
}

class _Thumb extends StatelessWidget {
  const _Thumb({required this.vm, required this.screen});

  final GalleryViewModel vm;
  final Map<String, dynamic> screen;

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    final path = vm.screenshotPath(screen['file'] as String?);
    final exists = path != null && File(path).existsSync();
    final status = screen['status'] as String? ?? 'pending';

    return GestureDetector(
      onTap: exists ? () => _showFull(context, path, screen['title'] as String? ?? '') : null,
      child: SizedBox(
        width: 120,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Container(
              width: 120,
              height: 240,
              decoration: BoxDecoration(
                color: theme.canvasColor,
                border: Border.all(color: theme.dividerColor),
                borderRadius: BorderRadius.circular(6),
              ),
              clipBehavior: Clip.antiAlias,
              child: exists
                  ? Image.file(File(path), fit: BoxFit.cover)
                  : Center(
                      child: Text(
                        status,
                        style: theme.typography.caption2.copyWith(
                          color: theme.typography.caption2.color?.withValues(alpha: 0.5),
                        ),
                      ),
                    ),
            ),
            const SizedBox(height: 4),
            Text(
              screen['title'] as String? ?? '',
              maxLines: 2,
              overflow: TextOverflow.ellipsis,
              style: theme.typography.caption1,
            ),
          ],
        ),
      ),
    );
  }

  void _showFull(BuildContext context, String path, String title) {
    showMacosSheet<void>(
      context: context,
      builder: (context) => MacosSheet(
        child: Column(
          children: [
            Padding(
              padding: const EdgeInsets.all(12),
              child: Row(
                children: [
                  Expanded(child: Text(title, style: MacosTheme.of(context).typography.title2)),
                  PushButton(
                    controlSize: ControlSize.large,
                    onPressed: () => Navigator.of(context).pop(),
                    child: const Text('Close'),
                  ),
                ],
              ),
            ),
            Expanded(child: Image.file(File(path), fit: BoxFit.contain)),
            const SizedBox(height: 12),
          ],
        ),
      ),
    );
  }
}
