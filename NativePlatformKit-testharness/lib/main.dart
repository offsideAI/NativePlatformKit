import 'package:flutter/widgets.dart';
import 'package:macos_ui/macos_ui.dart';
import 'package:provider/provider.dart';

import 'app/app.dart';
import 'app/app_state.dart';
import 'core/command_runner.dart';
import 'core/native_bridge.dart';
import 'features/console/console_view_model.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  // Configure the macOS window (hidden title bar with traffic-light buttons)
  // before the first frame.
  await _configureMacosWindowUtils();

  runApp(
    MultiProvider(
      providers: [
        Provider<NativeBridge>(create: (_) => MethodChannelNativeBridge()),
        Provider<CommandRunner>(create: (c) => CommandRunner(c.read<NativeBridge>())),
        ChangeNotifierProvider(create: (_) => AppState()),
        ChangeNotifierProvider(
          create: (c) => ConsoleViewModel(c.read<CommandRunner>(), c.read<NativeBridge>()),
        ),
      ],
      child: const NpkTestHarnessApp(),
    ),
  );
}

/// Applies macOS window styling via macos_ui's window utils.
Future<void> _configureMacosWindowUtils() async {
  const config = MacosWindowUtilsConfig();
  await config.apply();
}
