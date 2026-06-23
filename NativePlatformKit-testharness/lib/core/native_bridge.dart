import 'dart:async';

import 'package:flutter/services.dart';

import 'process_event.dart';

/// Abstraction over the native (Swift) bridge so view models and [CommandRunner] can be tested
/// against a fake. The concrete implementation is [MethodChannelNativeBridge].
abstract class NativeBridge {
  /// A broadcast stream of all process events (routed by [ProcessEvent.handle]).
  Stream<ProcessEvent> get events;

  /// Starts [executable] with [arguments], keyed by the caller-supplied [handle].
  /// Output arrives asynchronously on [events]. Returns the handle.
  Future<String> startProcess({
    required String handle,
    required String executable,
    required List<String> arguments,
    String? workingDirectory,
    Map<String, String>? environment,
  });

  /// Requests cancellation of the process identified by [handle].
  Future<bool> cancelProcess(String handle);

  /// Shows a native directory picker; returns the chosen path or `null` if cancelled.
  Future<String?> chooseDirectory({String? initialDirectory});

  /// Shows a native file picker; returns the chosen path or `null` if cancelled.
  Future<String?> chooseFile({String? initialDirectory, List<String>? allowedExtensions});
}

/// [NativeBridge] backed by the `npk/commands` MethodChannel and `npk/events` EventChannel.
class MethodChannelNativeBridge implements NativeBridge {
  /// Creates the bridge, optionally with injected channels (for tests).
  MethodChannelNativeBridge({
    MethodChannel? methodChannel,
    EventChannel? eventChannel,
  })  : _method = methodChannel ?? const MethodChannel('npk/commands'),
        _eventChannel = eventChannel ?? const EventChannel('npk/events') {
    _events = _eventChannel
        .receiveBroadcastStream()
        .map((dynamic e) => ProcessEvent.fromMap((e as Map).cast<dynamic, dynamic>()));
    // Keep the platform EventChannel subscription hot for the app's lifetime so the native
    // sink is wired before any process starts (otherwise early output could be dropped).
    _keepAlive = _events.listen((_) {});
  }

  final MethodChannel _method;
  final EventChannel _eventChannel;
  late final Stream<ProcessEvent> _events;
  late final StreamSubscription<ProcessEvent> _keepAlive;

  /// Releases the keep-alive subscription (call on app teardown).
  void dispose() => _keepAlive.cancel();

  @override
  Stream<ProcessEvent> get events => _events;

  @override
  Future<String> startProcess({
    required String handle,
    required String executable,
    required List<String> arguments,
    String? workingDirectory,
    Map<String, String>? environment,
  }) async {
    final result = await _method.invokeMethod<String>('startProcess', {
      'handle': handle,
      'executable': executable,
      'arguments': arguments,
      'workingDirectory': ?workingDirectory,
      'environment': ?environment,
    });
    return result ?? handle;
  }

  @override
  Future<bool> cancelProcess(String handle) async {
    final result = await _method.invokeMethod<bool>('cancelProcess', {'handle': handle});
    return result ?? false;
  }

  @override
  Future<String?> chooseDirectory({String? initialDirectory}) {
    return _method.invokeMethod<String?>('chooseDirectory', {
      'initialDirectory': ?initialDirectory,
    });
  }

  @override
  Future<String?> chooseFile({String? initialDirectory, List<String>? allowedExtensions}) {
    return _method.invokeMethod<String?>('chooseFile', {
      'initialDirectory': ?initialDirectory,
      'allowedExtensions': ?allowedExtensions,
    });
  }
}
