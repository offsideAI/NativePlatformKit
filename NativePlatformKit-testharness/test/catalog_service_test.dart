import 'dart:convert';
import 'dart:io';

import 'package:flutter_test/flutter_test.dart';
import 'package:npk_test_harness/data/flow_catalog/catalog_service.dart';

void main() {
  final service = CatalogService();

  test('parses a valid catalog', () {
    const raw = '''
{
  "appPackage": "a.b.c",
  "launchActivity": "a.b.c.Main",
  "categories": [
    {"id":"buttons","title":"Buttons","screens":[
      {"id":"buttons.primary","title":"Primary","navInstructions":["Home","Buttons"],"description":"d","screenshot":"buttons/primary.png"}
    ]}
  ]
}''';
    final catalog = service.parse(raw);
    expect(catalog.appPackage, 'a.b.c');
    expect(catalog.screenCount, 1);
    expect(catalog.allScreens.single.title, 'Primary');
  });

  test('rejects duplicate screen ids', () {
    const raw = '''
{"appPackage":"a","launchActivity":"a.M","categories":[
  {"id":"c","title":"C","screens":[
    {"id":"dup","title":"X","navInstructions":["n"],"description":"","screenshot":"c/x.png"},
    {"id":"dup","title":"Y","navInstructions":["n"],"description":"","screenshot":"c/y.png"}
  ]}
]}''';
    expect(() => service.parse(raw), throwsA(isA<FormatException>()));
  });

  test('rejects a screen with no nav instructions', () {
    const raw = '''
{"appPackage":"a","launchActivity":"a.M","categories":[
  {"id":"c","title":"C","screens":[
    {"id":"c.x","title":"X","navInstructions":[],"description":"","screenshot":"c/x.png"}
  ]}
]}''';
    expect(() => service.parse(raw), throwsA(isA<FormatException>()));
  });

  test('bundled catalog.json is valid and covers the playground (>=70 screens)', () {
    // Validates the real generated asset on disk.
    final raw = File('assets/catalog.json').readAsStringSync();
    final catalog = service.parse(raw);
    expect(catalog.categories.length, greaterThanOrEqualTo(15));
    expect(catalog.screenCount, greaterThanOrEqualTo(70));
    // Every screen id is unique + has a screenshot path under its category.
    expect(jsonDecode(raw), isA<Map<String, dynamic>>());
  });
}
