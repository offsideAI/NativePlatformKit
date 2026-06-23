# CLAUDE.md — NativePlatformKit repo

Guidance for working in this repository. Three independent sub-projects live side by side; know which
one you're touching before you build or run anything.

## Repository map

| Path | What it is | Build system |
|---|---|---|
| `NativePlatformKit-scaffold/` | **Phase-1 scaffold** of the publishable Jetpack Compose UI library (`das.arunabh.nativeplatformkit:nativeplatformkit`) — theme/tokens + one reference component `NpkButton`. Has its own `CLAUDE.md`, `DECISIONS.md`, `ROADMAP`-style docs. | Gradle (Kotlin), Android |
| `NativePlatformKit-TestLabs-Android-Playground/` | The **target app** being screenshotted — a single-activity (`TestlabsMainActivity`) Jetpack-Navigation app with ~80 demo fragments. Pre-existing; we consume it, we don't own its design. | Gradle 8.9, Android |
| `NativePlatformKit-testharness/` | **Flutter macOS desktop app** that boots an emulator, builds/installs the playground, and captures a screenshot of every screen (guided **and** automated). The current focus of active work. | Flutter 3.44.2, macOS |
| `spec/` | Original product spec (`NativePlatformKit.html` + `spec_*.png`). Reference material. |
| `screenshots/` | Repo-root screenshot output (see the discrepancy note below). |
| `ROADMAP.md` | Test-harness implementation roadmap: 9 Epics ⇄ 9 Milestones (E0–E8), each story/task tagged with a status marker. **All E0–E8 complete.** |
| `VERIFICATION.md` | Step-by-step manual run/verify walkthrough for the harness. |

## Environment (this machine — non-obvious, verify before building)

- **Flutter 3.44.2** at `~/Developer/flutter/flutter-3_44_2`; get it on PATH with
  `source ~/.zprofile && source ~/.zshrc`.
- **The harness macOS build needs CocoaPods, which lives under chruby Ruby 3.4.4**, not system Ruby.
  Before `flutter build/run macos`:
  `source /opt/homebrew/opt/chruby/share/chruby/chruby.sh && chruby ruby-3.4.4`.
  The harness `Makefile` does this automatically (`make run`, `make build`).
- **Android SDK** at `~/Library/Android/sdk`. The `emulator` binary is present; command-line tools +
  the **API 34 google_apis arm64** system image + the `npk_pixel_6_api34` AVD were installed (the
  harness can also install them in-app). `JAVA_HOME` via `/usr/libexec/java_home` (jbr-17).
- The harness's **macOS app sandbox is disabled** (both entitlements) — required to spawn
  adb/emulator/gradle. Don't re-enable it.
- The **scaffold** is capped at `compileSdk 36` (only android-34 & android-36.1 platforms installed),
  forcing AGP 8.13.2 / core-ktx 1.18.0 / lifecycle 2.10.0 — see `NativePlatformKit-scaffold/DECISIONS.md`.
- `Date.now()`/`Math.random()` restrictions apply only to Workflow scripts, not app/test code.

## Working on the test harness

```bash
cd NativePlatformKit-testharness
make verify     # flutter analyze + test (must be clean; 33 tests)
make run        # build (chruby Ruby active) + launch on macOS
make build      # build only
make catalog    # regenerate assets/catalog.json from the playground (tool/generate_catalog.py)
```
- **Provider + MVVM**, feature-first (`lib/features/*`), native macOS look via `macos_ui`.
- **Native bridge** (`macos/Runner/NpkBridge.swift` + `ProcessRunner.swift`): `MethodChannel`
  (`npk/commands`) + `EventChannel` (`npk/events`, live stdout/stderr/exit, cancel/kill). New Swift
  files must be registered in `Runner.xcodeproj` (use the `xcodeproj` gem under chruby ruby-3.4.4).
- Long-running ops stream output and are cancellable; keep `flutter analyze` at **zero issues**.
- `macos_ui`/cupertino do NOT export Material widgets (`Divider`, `SelectableText`, `ThemeMode`) —
  use plain containers / import `package:flutter/material.dart` selectively.
- Screenshots are captured binary-safe: `adb shell screencap -p` → `adb pull` (never stream PNG over
  the text channel). Auto Capture navigates via `uiautomator dump` + tap the text node's **center**
  (rows aren't `clickable`; the tap dispatches to the clickable parent).

## ⚠️ Known discrepancy to reconcile (next session)

The harness writes screenshots under `NativePlatformKit-testharness/screenshots/` (per
`lib/core/harness_paths.dart`, `HarnessPaths.defaultRoot`), but a commit "Moved screenshot to repo
root" created a **repo-root `screenshots/`**. Decide the single source of truth and point
`HarnessPaths` at it (likely the repo root). Also: the playground path and screenshots dir are
machine-specific absolute defaults in `HarnessPaths`/`BuildInstallService` — a planned Settings
override.

## Conventions

- Don't commit unless asked. If asked: branch off `main` first; sign off commits (`git commit -s`)
  if the user uses DCO; end commit messages with the Co-Authored-By trailer.
- Verify on-device when feasible (boot the AVD headless, run the real adb/gradle commands) before
  claiming something works; the agent session has no visible macOS GUI, so GUI click-throughs are
  the user's to verify.
- Keep `ROADMAP.md` status markers honest (legend at its top: ⬜ 🟡 ✅ ⏸️ 🟢).
