import 'package:flutter_test/flutter_test.dart';
import 'package:npk_test_harness/features/flow_runner/auto_capture_service.dart';

const _xml = '''<?xml version='1.0' encoding='UTF-8'?>
<hierarchy>
  <node text="Test Labs" class="android.widget.TextView" package="ai.offside.mobile.android.testlabs" bounds="[40,150][400,250]" />
  <node content-desc="Navigate up" class="android.widget.ImageButton" package="ai.offside.mobile.android.testlabs" bounds="[20,150][120,250]" />
  <node text="Settings" class="android.widget.TextView" package="ai.offside.mobile.android.testlabs" bounds="[800,150][900,250]" />
  <node text="Buttons" class="android.widget.TextView" package="ai.offside.mobile.android.testlabs" bounds="[40,800][1040,920]" />
  <node text="Theme" class="android.widget.TextView" package="ai.offside.mobile.android.testlabs" bounds="[40,500][1040,620]" />
  <node text="Tom &amp; Jerry" class="android.widget.TextView" package="ai.offside.mobile.android.testlabs" bounds="[40,1000][1040,1120]" />
  <node text="" class="android.view.View" package="ai.offside.mobile.android.testlabs" bounds="[0,0][0,0]" />
</hierarchy>''';

void main() {
  group('AutoCaptureService.navTargets', () {
    test('keeps real rows, drops chrome, orders by Y, decodes entities', () {
      final targets = AutoCaptureService.navTargets(_xml);
      // 'Test Labs', 'Navigate up', 'Settings' are denied; empty node dropped.
      expect(targets.map((t) => t.text), ['Theme', 'Buttons', 'Tom & Jerry']);
    });

    test('computes the tap center', () {
      final buttons = AutoCaptureService.navTargets(_xml).firstWhere((t) => t.text == 'Buttons');
      expect(buttons.cx, (40 + 1040) ~/ 2);
      expect(buttons.cy, (800 + 920) ~/ 2);
    });
  });

  test('rootPackage extracts the foreground package', () {
    expect(AutoCaptureService.rootPackage(_xml), 'ai.offside.mobile.android.testlabs');
    expect(AutoCaptureService.rootPackage('<hierarchy></hierarchy>'), isNull);
  });
}
