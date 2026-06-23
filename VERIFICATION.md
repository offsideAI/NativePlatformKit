 0. One-time shell setup (each new terminal)

  The macOS build needs Flutter on PATH and chruby's Ruby 3.4.4 active (for CocoaPods):
  source ~/.zprofile && source ~/.zshrc                       # Flutter on PATH
  source /opt/homebrew/opt/chruby/share/chruby/chruby.sh
  chruby ruby-3.4.4                                            # pod needs this Ruby
  cd /Users/coder/repos/arunabhdas/githubrepos/NativePlatformKit/NativePlatformKit-testharness
  ruby --version   # → ruby 3.4.4 ;  flutter --version → 3.44.2

  1. Launch the harness
  
  cd NativePlatformKit-testharness
  flutter run -d macos
  (or open the prebuilt app: open build/macos/Build/Products/Debug/npk_test_harness.app). flutter run is better — you get hot-reload and logs, and press q to quit.

  ---
  M0 — Shell & navigation

  Expect: a native macOS window with a left sidebar: Environment · Emulator · Build & Install · Flow Runner · Gallery · Settings, and a guided-setup banner across the top.
  - ✅ Click each sidebar item → the content area switches; Flow Runner/Gallery/Settings show polished "milestone" placeholders.
  - ✅ The top banner says "Guided setup — step 1 of 3: Environment" with Go to Environment and Skip setup. Click Skip setup → banner disappears (free navigation). (It also auto-completes once setup is done.)

  M1 — Native bridge (Developer Console)

  Expect: a thin status bar at the bottom; on its right, a Console toggle (</> icon).
  - ✅ Click Console → a bottom drawer opens.
  - ✅ Echo → prints $ /bin/echo … then NativePlatformKit bridge OK then — exited with code 0 (proves live streaming).
  - ✅ Sleep 30s → status bar shows a spinner + "Running…"; click Cancel → process stops (proves cancellation).
  - ✅ adb version → prints the adb version banner (proves real tool invocation through the Swift bridge).
  - ✅ Pick dir… → a native macOS folder picker opens; choose a folder → its path is logged (proves NSOpenPanel; only works because the sandbox is disabled).

  M2 — Environment detection & SDK bootstrap

  Open Environment (it auto-detects on first view; Re-detect in the toolbar re-runs).
  Expect (since I already provisioned the SDK): a green banner "Ready to create an emulator." and rows with green checks for: Java (JDK), adb, Android Emulator, Command-line Tools, sdkmanager, avdmanager, System image · API 34 · google_apis ·
  arm64-v8a, plus the SDK root path.
  - ✅ Because everything's present, the install buttons are hidden. (If you want to see the install flow from scratch, you could remove a component — tell me and I'll guide it; I won't do anything destructive unprompted.)

  M3 — Emulator manager

  Open Emulator.
  Expect: status shows Stopped (the AVD npk_pixel_6_api34 already exists), with a Boot emulator button and Cold boot / Wipe data checkboxes.
  - ✅ Click Boot emulator → status goes Booting… with a live log; a Pixel 6 emulator window appears. Within ~15–60s status flips to Online — emulator-5554.
  - ✅ Click Stop → emulator shuts down, status returns to Stopped.

  ---
  Automated checks (separate terminal, same shell setup)

  flutter analyze      # → No issues found!
  flutter test         # → All tests passed!  (17 tests)

  ---
  Notes

  - If the build fails on pod/Ruby: you forgot chruby ruby-3.4.4 in that shell.
  - Sandbox: disabled by design (D-decision) — required so the app can launch adb/emulator and write screenshots.
  - The SDK + AVD are already set up from my verification, so M2 shows all-green and M3 has the AVD ready. Booting in M3 will likely be fast (snapshot).


