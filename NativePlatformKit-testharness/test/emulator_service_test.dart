import 'package:flutter_test/flutter_test.dart';
import 'package:npk_test_harness/features/emulator/emulator_service.dart';

void main() {
  group('EmulatorService.parseAvdList', () {
    test('extracts AVD names and ignores INFO/blank lines', () {
      const output = '''
INFO    | Storing crashdata in: /tmp/x
npk_pixel_6_api34
Pixel_7_API_34
''';
      expect(
        EmulatorService.parseAvdList(output),
        ['npk_pixel_6_api34', 'Pixel_7_API_34'],
      );
    });

    test('returns empty for no AVDs', () {
      expect(EmulatorService.parseAvdList('\n  \n'), isEmpty);
    });
  });

  group('EmulatorService.parseEmulatorSerial', () {
    test('finds an online emulator serial', () {
      const output = '''
List of devices attached
emulator-5554\tdevice
''';
      expect(EmulatorService.parseEmulatorSerial(output), 'emulator-5554');
    });

    test('ignores offline/unauthorized and physical devices', () {
      const output = '''
List of devices attached
emulator-5554\toffline
ABC123XYZ\tdevice
''';
      expect(EmulatorService.parseEmulatorSerial(output), isNull);
    });

    test('returns null when no devices', () {
      expect(EmulatorService.parseEmulatorSerial('List of devices attached\n'), isNull);
    });
  });
}
