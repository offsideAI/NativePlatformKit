import 'package:flutter/material.dart' show ThemeMode;
import 'package:flutter/widgets.dart';
import 'package:macos_ui/macos_ui.dart';

import 'app_shell.dart';

/// Root widget: configures the macOS theme (system light/dark) and hosts [AppShell].
class NpkTestHarnessApp extends StatelessWidget {
  /// Creates the root app widget.
  const NpkTestHarnessApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MacosApp(
      title: 'NPK Test Harness',
      theme: MacosThemeData.light(),
      darkTheme: MacosThemeData.dark(),
      themeMode: ThemeMode.system,
      debugShowCheckedModeBanner: false,
      home: const AppShell(),
    );
  }
}
