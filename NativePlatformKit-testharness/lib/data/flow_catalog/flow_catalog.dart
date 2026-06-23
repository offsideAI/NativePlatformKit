/// A single capturable screen/destination in the playground.
class FlowScreen {
  /// Creates a flow screen.
  const FlowScreen({
    required this.id,
    required this.title,
    required this.navInstructions,
    required this.description,
    required this.screenshot,
  });

  /// Builds from a JSON map.
  factory FlowScreen.fromJson(Map<String, dynamic> json) => FlowScreen(
        id: json['id'] as String,
        title: json['title'] as String,
        navInstructions:
            (json['navInstructions'] as List<dynamic>).map((e) => e as String).toList(),
        description: json['description'] as String? ?? '',
        screenshot: json['screenshot'] as String,
      );

  /// Stable id, e.g. `buttons.primary`.
  final String id;

  /// Human-readable title.
  final String title;

  /// Step-by-step human navigation instructions.
  final List<String> navInstructions;

  /// Short description.
  final String description;

  /// Relative screenshot path (`<category>/<id>.png`).
  final String screenshot;
}

/// A group of related screens.
class FlowCategory {
  /// Creates a flow category.
  const FlowCategory({required this.id, required this.title, required this.screens});

  /// Builds from a JSON map.
  factory FlowCategory.fromJson(Map<String, dynamic> json) => FlowCategory(
        id: json['id'] as String,
        title: json['title'] as String,
        screens: (json['screens'] as List<dynamic>)
            .map((e) => FlowScreen.fromJson(e as Map<String, dynamic>))
            .toList(),
      );

  /// Category slug.
  final String id;

  /// Category label.
  final String title;

  /// Screens in this category.
  final List<FlowScreen> screens;
}

/// The full catalog of categories/screens plus the target app coordinates.
class FlowCatalog {
  /// Creates a catalog.
  const FlowCatalog({
    required this.appPackage,
    required this.launchActivity,
    required this.categories,
  });

  /// Builds from a JSON map.
  factory FlowCatalog.fromJson(Map<String, dynamic> json) => FlowCatalog(
        appPackage: json['appPackage'] as String,
        launchActivity: json['launchActivity'] as String,
        categories: (json['categories'] as List<dynamic>)
            .map((e) => FlowCategory.fromJson(e as Map<String, dynamic>))
            .toList(),
      );

  /// The playground applicationId.
  final String appPackage;

  /// The fully-qualified launcher activity.
  final String launchActivity;

  /// All categories.
  final List<FlowCategory> categories;

  /// Flattened list of every screen across categories.
  List<FlowScreen> get allScreens => [for (final c in categories) ...c.screens];

  /// Total screen count.
  int get screenCount => allScreens.length;
}
