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
  ---
  Notes

  - If the build fails on pod/Ruby: you forgot chruby ruby-3.4.4 in that shell.
  - Sandbox: disabled by design (D-decision) — required so the app can launch adb/emulator and write screenshots.
  - The SDK + AVD are already set up from my verification, so M2 shows all-green and M3 has the AVD ready. Booting in M3 will likely be fast (snapshot).

  ✅ NativePlatformKit Test Harness — M0–M8 complete

  ┌────────────────────────────────┬──────┬────────┬─────────────────────────────────────────────────┐
  │           Milestone            │ Epic │ Status │                    Verified                     │
  ├────────────────────────────────┼──────┼────────┼─────────────────────────────────────────────────┤
  │ M0 Scaffold & App Shell        │ E0   │ ✅     │ build + tests                                   │
  ├────────────────────────────────┼──────┼────────┼─────────────────────────────────────────────────┤
  │ M1 Native Bridge (Swift)       │ E1   │ ✅     │ compiles/links + tests                          │
  ├────────────────────────────────┼──────┼────────┼─────────────────────────────────────────────────┤
  │ M2 Environment + SDK Bootstrap │ E2   │ 🟢     │ cmdline-tools + image installed live            │
  ├────────────────────────────────┼──────┼────────┼─────────────────────────────────────────────────┤
  │ M3 Emulator Manager            │ E3   │ 🟢     │ AVD created + booted (boot_completed) + stopped │
  ├────────────────────────────────┼──────┼────────┼─────────────────────────────────────────────────┤
  │ M4 Build & Install             │ E4   │ 🟢     │ playground built → installed → foreground       │
  ├────────────────────────────────┼──────┼────────┼─────────────────────────────────────────────────┤
  │ M5 Flow Catalog                │ E5   │ ✅     │ 82 screens/19 cats, validated                   │
  ├────────────────────────────────┼──────┼────────┼─────────────────────────────────────────────────┤
  │ M6 Flow Runner                 │ E6   │ 🟢     │ capture produced a real 1080×2400 PNG           │
  ├────────────────────────────────┼──────┼────────┼─────────────────────────────────────────────────┤
  │ M7 Gallery, Manifest, Reports  │ E7   │ ✅     │ manifest/report unit-tested + Gallery           │
  ├────────────────────────────────┼──────┼────────┼─────────────────────────────────────────────────┤
  │ M8 Polish, Docs, Hygiene       │ E8   │ ✅     │ Makefile, README, docs, gitignore               │
  └────────────────────────────────┴──────┴────────┴─────────────────────────────────────────────────┘

  Final gate: flutter analyze clean · 30 unit tests pass · flutter build macos succeeds · make verify green.

  What the harness does (end to end)

  A native-macOS (macos_ui) Flutter app that walks you from nothing to screenshots:
  Environment (detect + install SDK pieces in-app) → Emulator (create/boot Pixel 6 API 34) → Build & Install (gradlew :app:assembleDebug → adb install -r → launch) → Flow Runner (three-pane guided capture over all ~82 screens, one-click
  Capture/Skip/Note, resumable) → Gallery (browse runs). Output to screenshots/runs/<ts>/… + latest/ + manifest.json + Markdown/CSV reports.

  Architecture highlights

  - Swift bridge: MethodChannel (commands) + EventChannel (live stdout/stderr/exit, cancel/kill).
  - Binary-safe capture: adb shell screencap → adb pull (avoids corrupting PNGs over the text channel).
  - Provider + MVVM, feature-first; services wrap adb/emulator/gradle/sdkmanager.
  - Sandbox disabled (required to spawn tools); CocoaPods via chruby Ruby 3.4.4 (wired into the Makefile).

  What I verified on real hardware

  SDK bootstrap (137 MB cmdline-tools + 1.5 GB API-34 image), AVD boot to sys.boot_completed=1, playground build → install → topResumedActivity is the playground, and a captured 1080×2400 screenshot of the "Test Labs" home screen.

  The one thing left for you

  The full GUI guided-capture pass — I can't drive the macOS window in this session, so I verified every underlying mechanism but not the on-screen click-through. Run it with:
  cd NativePlatformKit-testharness && make run
  Then: Environment (all green) → Emulator → Boot → Build & Install → Flow Runner → Capture through the screens → Finish → Gallery.

  Notes / future polish (non-blocking)

  - Catalog navInstructions are a structural baseline; the real home grid labels (Theme, Typography, Buttons, Modals, …) can be refined in assets/catalog.json or tool/generate_catalog.py.
  - Paths default to a machine-specific HarnessPaths.defaultRoot; Settings shows them read-only — editable/persisted settings are a noted enhancement.



  ---
  ---

    A new Auto Capture button in the Flow Runner toolbar (top). Click it and the harness drives the playground itself — no manual navigation — capturing a screenshot of every screen, then toggles to a Stop button so you can cancel mid-sweep.

  How it works (the hard part: no deep links)

  The playground has no deep links, so the harness automates the actual UI via UI Automator:
  1. adb shell uiautomator dump → reads the on-screen view hierarchy (XML).
  2. Finds each navigation row by its text and taps the center coordinate (the key discovery: the rows aren't clickable themselves — they're custom views — so tapping the text node's center dispatches to the clickable parent).
  3. Captures the screen, presses Back, moves to the next.
  4. Walks 2 levels — Home → section → screen — which matches the app's menu structure, skipping persistent chrome (title, Navigate up, Settings, Color Generator Form), with package-guard recovery if Back overshoots, and a capture cap +
  cancellation.

  Output lands in screenshots/runs/<id>/auto/<home__section__screen>.png (+ mirrored to latest/auto/), plus an auto-manifest.json listing everything captured. Matching catalog screens light up green in the tree.

  Verification

  I proved the exact algorithm on the real emulator before porting it to Dart — a prototype sweep navigated Home → Theme/Typography/Buttons/Modals…, into leaves (Primary Button, Micro Buttons, Snackbar, …), and saved valid screenshots (I confirmed
  the "Primary Button" leaf renders fully). The Dart AutoCaptureService mirrors that algorithm; its XML parser is unit-tested.

  - flutter analyze — clean
  - flutter test — 33 pass (added auto_capture_service_test.dart)
  - flutter build macos — succeeds

  To use it

  make run → Emulator (Boot) → Build & Install → Flow Runner → Auto Capture. Watch the status line report progress ("Section: Buttons", "Auto-capture done: N screens"). Browse results in Gallery or under screenshots/.

  Honest caveats

  - This extends the original guided-manual decision (D10) — both modes now exist; manual capture remains the reliable fallback (UI automation is inherently more brittle than a human).
  - It's 2 levels deep, so deeper nested flows (e.g., individual stepper steps, bottom-sheet variants behind a leaf) are captured at the section/leaf level but not recursed into further — that matches "each screen and section." If you want it to
  also drill into those, I can raise the depth.
  - Files are named by navigation path (e.g., home__buttons__primary-button.png), which may differ from the catalog ids; the auto-manifest records the exact mapping.

  ROADMAP (T6.2.5) and memory updated. Want me to increase the crawl depth to capture nested sub-flows too, or leave it at the section/screen level?
