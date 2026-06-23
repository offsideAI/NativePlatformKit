/// Detection status of a single tool/component.
class ToolStatus {
  /// Creates a tool status.
  const ToolStatus({
    required this.id,
    required this.label,
    required this.found,
    this.version,
    this.path,
    this.hint,
  });

  /// Stable identifier (e.g. `adb`, `sdkmanager`, `system-image`).
  final String id;

  /// Human-readable label.
  final String label;

  /// Whether the tool/component was found.
  final bool found;

  /// Detected version line, if available.
  final String? version;

  /// Filesystem path, if found.
  final String? path;

  /// Guidance shown when missing.
  final String? hint;
}

/// A snapshot of the local Android tooling environment.
class EnvironmentReport {
  /// Creates an environment report.
  const EnvironmentReport({
    required this.sdkRoot,
    required this.tools,
    required this.avds,
    this.javaHome,
  });

  /// The resolved Android SDK root.
  final String sdkRoot;

  /// `JAVA_HOME`, if set.
  final String? javaHome;

  /// Per-tool detection results.
  final List<ToolStatus> tools;

  /// Names of existing AVDs.
  final List<String> avds;

  /// Returns the status for [id], or `null` if not probed.
  ToolStatus? tool(String id) {
    for (final t in tools) {
      if (t.id == id) return t;
    }
    return null;
  }

  bool _found(String id) => tool(id)?.found ?? false;

  /// Whether `adb` is present.
  bool get hasAdb => _found('adb');

  /// Whether the emulator binary is present.
  bool get hasEmulator => _found('emulator');

  /// Whether the command-line tools (incl. sdkmanager + avdmanager) are present.
  bool get hasCmdlineTools => _found('cmdline-tools') && _found('sdkmanager') && _found('avdmanager');

  /// Whether the target system image is installed.
  bool get hasSystemImage => _found('system-image');

  /// Whether a JDK was detected (needed later for the Gradle build).
  bool get hasJdk => _found('jdk');

  /// Whether everything required to create + boot an AVD is present.
  bool get canCreateEmulator => hasAdb && hasEmulator && hasCmdlineTools && hasSystemImage;
}
