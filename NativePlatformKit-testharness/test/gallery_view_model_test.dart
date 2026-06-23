import 'dart:io';

import 'package:flutter_test/flutter_test.dart';
import 'package:npk_test_harness/core/harness_paths.dart';
import 'package:npk_test_harness/features/gallery/gallery_view_model.dart';
import 'package:path/path.dart' as p;

void main() {
  test('listRuns finds run dirs with a manifest, newest first', () {
    final tmp = Directory.systemTemp.createTempSync('npk_gallery_');
    try {
      final runs = p.join(tmp.path, 'screenshots', 'runs');
      // Two valid runs + one dir without a manifest (ignored).
      for (final id in ['20260101-090000', '20260623-120000']) {
        final d = Directory(p.join(runs, id))..createSync(recursive: true);
        File(p.join(d.path, 'manifest.json')).writeAsStringSync('{"runId":"$id"}');
      }
      Directory(p.join(runs, 'no-manifest')).createSync(recursive: true);

      final ids = GalleryViewModel.listRuns(runs);
      expect(ids, ['20260623-120000', '20260101-090000']); // newest first, no-manifest excluded
    } finally {
      tmp.deleteSync(recursive: true);
    }
  });

  test('refresh loads the newest manifest', () async {
    final tmp = Directory.systemTemp.createTempSync('npk_gallery2_');
    try {
      final paths = HarnessPaths(root: tmp.path);
      Directory(paths.runDir('20260623-120000')).createSync(recursive: true);
      File(p.join(paths.runDir('20260623-120000'), 'manifest.json'))
          .writeAsStringSync('{"runId":"20260623-120000","summary":{"total":3}}');

      final vm = GalleryViewModel(paths);
      await vm.refresh();
      expect(vm.selectedRunId, '20260623-120000');
      expect((vm.manifest!['summary'] as Map)['total'], 3);
    } finally {
      tmp.deleteSync(recursive: true);
    }
  });
}
