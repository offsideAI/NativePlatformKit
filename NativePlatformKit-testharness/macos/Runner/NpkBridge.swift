import Cocoa
import FlutterMacOS

/// The native side of the harness bridge.
///
/// Exposes a `MethodChannel` (`npk/commands`) for issuing commands (start/cancel a process, show
/// native open panels) and an `EventChannel` (`npk/events`) that streams live process
/// stdout/stderr/exit events back to Dart. Process lifetime is delegated to [ProcessRunner].
final class NpkBridge: NSObject, FlutterStreamHandler {
  private let runner = ProcessRunner()

  /// Registers the method + event channels on [messenger].
  func register(with messenger: FlutterBinaryMessenger) {
    let methodChannel = FlutterMethodChannel(name: "npk/commands", binaryMessenger: messenger)
    let eventChannel = FlutterEventChannel(name: "npk/events", binaryMessenger: messenger)
    eventChannel.setStreamHandler(self)
    methodChannel.setMethodCallHandler { [weak self] call, result in
      self?.handle(call, result)
    }
  }

  // MARK: - FlutterStreamHandler

  func onListen(withArguments arguments: Any?, eventSink events: @escaping FlutterEventSink) -> FlutterError? {
    runner.eventSink = events
    return nil
  }

  func onCancel(withArguments arguments: Any?) -> FlutterError? {
    runner.eventSink = nil
    return nil
  }

  // MARK: - Method dispatch

  private func handle(_ call: FlutterMethodCall, _ result: @escaping FlutterResult) {
    switch call.method {
    case "startProcess":
      startProcess(call.arguments, result)
    case "cancelProcess":
      guard let args = call.arguments as? [String: Any], let handle = args["handle"] as? String else {
        result(badArgs("cancelProcess requires 'handle'")); return
      }
      result(runner.cancel(handle: handle))
    case "chooseDirectory":
      let args = call.arguments as? [String: Any]
      choosePath(
        chooseDirectories: true,
        initial: args?["initialDirectory"] as? String,
        extensions: nil,
        result: result
      )
    case "chooseFile":
      let args = call.arguments as? [String: Any]
      choosePath(
        chooseDirectories: false,
        initial: args?["initialDirectory"] as? String,
        extensions: args?["allowedExtensions"] as? [String],
        result: result
      )
    default:
      result(FlutterMethodNotImplemented)
    }
  }

  private func startProcess(_ arguments: Any?, _ result: @escaping FlutterResult) {
    guard let args = arguments as? [String: Any],
          let handle = args["handle"] as? String,
          let executable = args["executable"] as? String,
          let processArgs = args["arguments"] as? [String] else {
      result(badArgs("startProcess requires 'handle', 'executable', 'arguments'")); return
    }
    let workingDirectory = args["workingDirectory"] as? String
    let environment = args["environment"] as? [String: String]
    do {
      try runner.start(
        handle: handle,
        executable: executable,
        arguments: processArgs,
        workingDirectory: workingDirectory,
        environment: environment
      )
      result(handle)
    } catch {
      result(FlutterError(code: "start_failed", message: error.localizedDescription, details: nil))
    }
  }

  private func choosePath(
    chooseDirectories: Bool,
    initial: String?,
    extensions: [String]?,
    result: @escaping FlutterResult
  ) {
    DispatchQueue.main.async {
      let panel = NSOpenPanel()
      panel.canChooseDirectories = chooseDirectories
      panel.canChooseFiles = !chooseDirectories
      panel.allowsMultipleSelection = false
      panel.canCreateDirectories = true
      if let initial = initial {
        panel.directoryURL = URL(fileURLWithPath: initial)
      }
      if !chooseDirectories, let extensions = extensions, !extensions.isEmpty {
        panel.allowedFileTypes = extensions
      }
      let response = panel.runModal()
      if response == .OK, let url = panel.url {
        result(url.path)
      } else {
        result(nil)
      }
    }
  }

  private func badArgs(_ message: String) -> FlutterError {
    FlutterError(code: "bad_args", message: message, details: nil)
  }
}
