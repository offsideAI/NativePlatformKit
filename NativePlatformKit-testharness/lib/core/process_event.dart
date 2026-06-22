/// The kind of a [ProcessEvent] streamed from the native bridge.
enum ProcessEventType {
  /// A chunk of standard output.
  stdout,

  /// A chunk of standard error.
  stderr,

  /// The process exited; [ProcessEvent.exitCode] is set.
  exit,

  /// An unrecognized event type (forward-compat).
  unknown,
}

/// A single event emitted by a native child process over the `npk/events` channel.
class ProcessEvent {
  /// Creates a process event.
  const ProcessEvent({
    required this.handle,
    required this.type,
    this.data,
    this.exitCode,
  });

  /// Builds an event from the native payload map.
  factory ProcessEvent.fromMap(Map<dynamic, dynamic> map) {
    final type = switch (map['type'] as String?) {
      'stdout' => ProcessEventType.stdout,
      'stderr' => ProcessEventType.stderr,
      'exit' => ProcessEventType.exit,
      _ => ProcessEventType.unknown,
    };
    return ProcessEvent(
      handle: map['handle'] as String? ?? '',
      type: type,
      data: map['data'] as String?,
      exitCode: (map['code'] as num?)?.toInt(),
    );
  }

  /// The Dart-generated handle identifying the owning process.
  final String handle;

  /// The event type.
  final ProcessEventType type;

  /// Output text for [ProcessEventType.stdout] / [ProcessEventType.stderr].
  final String? data;

  /// Exit code for [ProcessEventType.exit].
  final int? exitCode;

  /// Whether this is an exit event.
  bool get isExit => type == ProcessEventType.exit;

  @override
  String toString() => 'ProcessEvent($handle, $type, code=$exitCode, data=${data?.length ?? 0}b)';
}
