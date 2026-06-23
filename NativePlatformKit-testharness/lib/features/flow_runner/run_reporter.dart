import 'dart:convert';
import 'dart:io';

import 'package:path/path.dart' as p;

import '../../core/harness_paths.dart';
import '../../data/flow_catalog/flow_catalog.dart';
import 'run_models.dart';

/// Writes a run's `manifest.json` + `report.md` + `report.csv` into its run directory (E7).
class RunReporter {
  /// Creates a reporter rooted at [_paths].
  RunReporter(this._paths);

  final HarnessPaths _paths;

  /// Harness version stamped into the manifest.
  static const String harnessVersion = '0.1.0';

  /// Builds the manifest map for [run] against [catalog].
  Map<String, dynamic> buildManifest(RunState run, FlowCatalog catalog) {
    final titles = <String, ({String category, String title})>{};
    for (final category in catalog.categories) {
      for (final screen in category.screens) {
        titles[screen.id] = (category: category.title, title: screen.title);
      }
    }
    return {
      'runId': run.runId,
      'startedAt': run.startedAt,
      'finishedAt': run.finishedAt,
      'harnessVersion': harnessVersion,
      'device': {'avd': run.avd, 'serial': run.serial, 'api': run.api},
      'app': {
        'package': catalog.appPackage,
        'versionName': run.appVersionName,
        'gitSha': run.appGitSha,
      },
      'summary': {
        'total': run.total,
        'captured': run.captured,
        'skipped': run.skipped,
        'failed': run.failed,
      },
      'screens': [
        for (final screen in catalog.allScreens)
          {
            'id': screen.id,
            'category': titles[screen.id]?.category,
            'title': titles[screen.id]?.title,
            'status': (run.records[screen.id]?.status ?? CaptureStatus.pending).name,
            'file': run.records[screen.id]?.file,
            'capturedAt': run.records[screen.id]?.capturedAt,
            'notes': run.records[screen.id]?.notes,
          },
      ],
    };
  }

  /// Writes manifest + Markdown + CSV for [run]. Returns the run directory.
  Future<String> write(RunState run, FlowCatalog catalog) async {
    final dir = Directory(_paths.runDir(run.runId))..createSync(recursive: true);
    final manifest = buildManifest(run, catalog);

    File(p.join(dir.path, 'manifest.json'))
        .writeAsStringSync(const JsonEncoder.withIndent('  ').convert(manifest));
    File(p.join(dir.path, 'report.md')).writeAsStringSync(_markdown(manifest));
    File(p.join(dir.path, 'report.csv')).writeAsStringSync(_csv(manifest));
    return dir.path;
  }

  String _markdown(Map<String, dynamic> m) {
    final s = m['summary'] as Map<String, dynamic>;
    final app = m['app'] as Map<String, dynamic>;
    final device = m['device'] as Map<String, dynamic>;
    final buf = StringBuffer()
      ..writeln('# Capture run ${m['runId']}')
      ..writeln()
      ..writeln('- Started: ${m['startedAt']}')
      ..writeln('- Finished: ${m['finishedAt']}')
      ..writeln('- Device: ${device['avd']} (${device['serial']}, API ${device['api']})')
      ..writeln('- App: ${app['package']} ${app['versionName'] ?? ''} '
          '${app['gitSha'] != null ? '@${app['gitSha']}' : ''}')
      ..writeln('- Harness: ${m['harnessVersion']}')
      ..writeln()
      ..writeln('**Summary:** ${s['captured']} captured · ${s['skipped']} skipped · '
          '${s['failed']} failed · ${s['total']} total')
      ..writeln()
      ..writeln('| Category | Screen | Status | File | Notes |')
      ..writeln('|---|---|---|---|---|');
    for (final screen in m['screens'] as List<dynamic>) {
      final r = screen as Map<String, dynamic>;
      buf.writeln('| ${r['category'] ?? ''} | ${r['title'] ?? r['id']} | ${r['status']} '
          '| ${r['file'] ?? ''} | ${(r['notes'] ?? '').toString().replaceAll('|', '\\|')} |');
    }
    return buf.toString();
  }

  String _csv(Map<String, dynamic> m) {
    String esc(Object? v) {
      final s = (v ?? '').toString();
      return '"${s.replaceAll('"', '""')}"';
    }

    final buf = StringBuffer()..writeln('category,screen,id,status,file,capturedAt,notes');
    for (final screen in m['screens'] as List<dynamic>) {
      final r = screen as Map<String, dynamic>;
      buf.writeln([
        esc(r['category']),
        esc(r['title']),
        esc(r['id']),
        esc(r['status']),
        esc(r['file']),
        esc(r['capturedAt']),
        esc(r['notes']),
      ].join(','));
    }
    return buf.toString();
  }
}
