import Cocoa
import FlutterMacOS

class MainFlutterWindow: NSWindow {
  /// Strong reference so the bridge (and its channel handlers) outlives `awakeFromNib`.
  private var npkBridge: NpkBridge?

  override func awakeFromNib() {
    let flutterViewController = FlutterViewController()
    let windowFrame = self.frame
    self.contentViewController = flutterViewController
    self.setFrame(windowFrame, display: true)

    RegisterGeneratedPlugins(registry: flutterViewController)

    // Register the native bridge (npk/commands + npk/events).
    let bridge = NpkBridge()
    bridge.register(with: flutterViewController.engine.binaryMessenger)
    self.npkBridge = bridge

    super.awakeFromNib()
  }
}
