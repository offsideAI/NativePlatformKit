import 'dart:convert';

import 'package:flutter/services.dart' show rootBundle;

import 'flow_catalog.dart';

/// Loads and validates the bundled flow catalog (`assets/catalog.json`).
class CatalogService {
  /// Asset path of the catalog.
  static const String assetPath = 'assets/catalog.json';

  /// Loads + validates the catalog from the app bundle.
  Future<FlowCatalog> load() async {
    final raw = await rootBundle.loadString(assetPath);
    return parse(raw);
  }

  /// Parses + validates [raw] JSON. Throws [FormatException] on invalid data.
  /// Exposed for testing.
  FlowCatalog parse(String raw) {
    final json = jsonDecode(raw);
    if (json is! Map<String, dynamic>) {
      throw const FormatException('catalog root must be an object');
    }
    final catalog = FlowCatalog.fromJson(json);
    _validate(catalog);
    return catalog;
  }

  void _validate(FlowCatalog catalog) {
    if (catalog.categories.isEmpty) {
      throw const FormatException('catalog has no categories');
    }
    final ids = <String>{};
    for (final category in catalog.categories) {
      for (final screen in category.screens) {
        if (screen.id.isEmpty) throw const FormatException('screen with empty id');
        if (!ids.add(screen.id)) {
          throw FormatException('duplicate screen id: ${screen.id}');
        }
        if (screen.navInstructions.isEmpty) {
          throw FormatException('screen ${screen.id} has no nav instructions');
        }
        if (screen.screenshot.isEmpty) {
          throw FormatException('screen ${screen.id} has no screenshot path');
        }
      }
    }
  }
}
