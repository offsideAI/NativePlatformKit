import 'package:flutter/cupertino.dart';
import 'package:macos_ui/macos_ui.dart';
import 'package:provider/provider.dart';

import '../../app/app_state.dart';
import '../../core/harness_paths.dart';
import '../build_install/build_install_service.dart';
import '../emulator/emulator_service.dart';
import '../flow_runner/capture_service.dart';

/// Section showing the harness's resolved paths and target configuration (Epic E8 / M8).
///
/// Read-only for now; the defaults are derived from [HarnessPaths.defaultRoot] and the playground
/// folder can be overridden via the picker in the Build & Install tab.
class SettingsScreen extends StatelessWidget {
  /// Creates the Settings section.
  const SettingsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final paths = context.read<HarnessPaths>();
    final sdk = context.read<CaptureService>().sdkRoot;
    final avd = context.read<EmulatorService>().avdName;
    final build = context.read<BuildInstallService>();

    return MacosScaffold(
      toolBar: ToolBar(title: Text(AppSection.settings.title)),
      children: [
        ContentArea(
          builder: (context, scrollController) {
            return SingleChildScrollView(
              controller: scrollController,
              padding: const EdgeInsets.all(20),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  _Group(title: 'Paths', rows: {
                    'Harness root': paths.root,
                    'Screenshots': paths.screenshots,
                    'Runs archive': paths.runsDir,
                    'Latest (canonical)': paths.latestDir,
                    'Config / run-state': paths.harnessDir,
                    'Playground project': paths.playground,
                  }),
                  const SizedBox(height: 20),
                  _Group(title: 'Android', rows: {
                    'SDK root': sdk,
                    'AVD': avd,
                    'System image': 'system-images;android-34;google_apis;arm64-v8a',
                  }),
                  const SizedBox(height: 20),
                  _Group(title: 'Target app', rows: {
                    'applicationId': build.applicationId,
                    'Launch activity': build.launchActivity,
                  }),
                  const SizedBox(height: 16),
                  Text(
                    'Paths are derived from the harness root (HarnessPaths.defaultRoot). The playground '
                    'folder can be overridden in Build & Install. Editable, persisted settings are a '
                    'planned enhancement.',
                    style: MacosTheme.of(context).typography.caption1.copyWith(
                          color: MacosTheme.of(context).typography.caption1.color?.withValues(alpha: 0.6),
                        ),
                  ),
                ],
              ),
            );
          },
        ),
      ],
    );
  }
}

class _Group extends StatelessWidget {
  const _Group({required this.title, required this.rows});

  final String title;
  final Map<String, String> rows;

  @override
  Widget build(BuildContext context) {
    final theme = MacosTheme.of(context);
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(title, style: theme.typography.title3),
        const SizedBox(height: 8),
        for (final entry in rows.entries)
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 4),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                SizedBox(
                  width: 160,
                  child: Text(entry.key, style: theme.typography.body),
                ),
                Expanded(
                  child: Text(
                    entry.value,
                    style: theme.typography.caption1.copyWith(fontFamily: 'Menlo'),
                  ),
                ),
              ],
            ),
          ),
      ],
    );
  }
}
