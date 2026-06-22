import Foundation
import FlutterMacOS

/// Spawns and manages child processes (adb, emulator, sdkmanager, gradlew, …), streaming their
/// stdout/stderr and exit status to Dart over an `EventChannel` sink. Processes are keyed by a
/// Dart-supplied `handle` so the UI can route output and cancel a specific process.
final class ProcessRunner {
  /// Sink for the `npk/events` EventChannel; set while Dart is listening.
  var eventSink: FlutterEventSink?

  private var processes: [String: Process] = [:]
  private let lock = NSLock()

  /// Starts [executable] with [arguments], keyed by [handle]. Output streams over the event sink.
  /// Throws if the process cannot be launched.
  func start(
    handle: String,
    executable: String,
    arguments: [String],
    workingDirectory: String?,
    environment: [String: String]?
  ) throws {
    let process = Process()
    process.executableURL = URL(fileURLWithPath: executable)
    process.arguments = arguments
    if let workingDirectory = workingDirectory {
      process.currentDirectoryURL = URL(fileURLWithPath: workingDirectory)
    }
    var env = ProcessInfo.processInfo.environment
    if let environment = environment {
      for (key, value) in environment { env[key] = value }
    }
    process.environment = env

    let stdoutPipe = Pipe()
    let stderrPipe = Pipe()
    process.standardOutput = stdoutPipe
    process.standardError = stderrPipe

    stdoutPipe.fileHandleForReading.readabilityHandler = { [weak self] handleForReading in
      let data = handleForReading.availableData
      guard !data.isEmpty, let text = String(data: data, encoding: .utf8) else { return }
      self?.emit(handle: handle, type: "stdout", data: text)
    }
    stderrPipe.fileHandleForReading.readabilityHandler = { [weak self] handleForReading in
      let data = handleForReading.availableData
      guard !data.isEmpty, let text = String(data: data, encoding: .utf8) else { return }
      self?.emit(handle: handle, type: "stderr", data: text)
    }

    process.terminationHandler = { [weak self] proc in
      stdoutPipe.fileHandleForReading.readabilityHandler = nil
      stderrPipe.fileHandleForReading.readabilityHandler = nil
      self?.remove(handle: handle)
      self?.emit(handle: handle, type: "exit", data: nil, code: Int(proc.terminationStatus))
    }

    store(handle: handle, process: process)
    do {
      try process.run()
    } catch {
      remove(handle: handle)
      throw error
    }
  }

  /// Terminates the process for [handle] (SIGTERM, escalating to SIGKILL after a grace period).
  /// Returns `true` if a matching running process was found.
  @discardableResult
  func cancel(handle: String) -> Bool {
    lock.lock()
    let process = processes[handle]
    lock.unlock()
    guard let process = process, process.isRunning else { return false }
    process.terminate()
    DispatchQueue.global().asyncAfter(deadline: .now() + 3) {
      if process.isRunning { kill(process.processIdentifier, SIGKILL) }
    }
    return true
  }

  /// Terminates every tracked process (called on teardown).
  func cancelAll() {
    lock.lock()
    let all = Array(processes.values)
    lock.unlock()
    for process in all where process.isRunning { process.terminate() }
  }

  // MARK: - Private

  private func store(handle: String, process: Process) {
    lock.lock(); processes[handle] = process; lock.unlock()
  }

  private func remove(handle: String) {
    lock.lock(); processes[handle] = nil; lock.unlock()
  }

  private func emit(handle: String, type: String, data: String?, code: Int? = nil) {
    var payload: [String: Any] = ["handle": handle, "type": type]
    if let data = data { payload["data"] = data }
    if let code = code { payload["code"] = code }
    // FlutterEventSink must be invoked on the main thread.
    DispatchQueue.main.async { [weak self] in self?.eventSink?(payload) }
  }
}
