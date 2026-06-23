# MEMORY.md — resume/handoff state

Snapshot to continue from where we left off. See `CLAUDE.md` for working guidance, `ROADMAP.md` for
the full test-harness breakdown, and `VERIFICATION.md` for the manual run-through.

_Last updated: 2026-06-23._

## Where things stand

### 1. `NativePlatformKit-testharness/` — Flutter macOS app (ACTIVE focus)
**All 9 epics complete (E0–E8) + an Auto Capture feature added afterward.** Status:
- **M0** App shell (macos_ui sidebar + guided wizard, Provider/MVVM). ✅
- **M1** Swift native bridge (`npk/commands` MethodChannel + `npk/events` EventChannel, cancel/kill); Developer Console. ✅
- **M2** Environment detection + in-app SDK bootstrap (cmdline-tools + API 34 image). 🟢 installs run on-device.
- **M3** Emulator Manager — create/boot/stop the `npk_pixel_6_api34` AVD. 🟢 booted `emulator-5554` to `sys.boot_completed=1`.
- **M4** Build & Install — `./gradlew :app:assembleDebug` → `adb install -r` → launch. 🟢 playground ran in foreground.
- **M5** Flow Catalog — `assets/catalog.json` (82 screens, 19 categories) via `tool/generate_catalog.py`; loader + validation. ✅
- **M6** Flow Runner — three-pane guided capture; binary-safe `screencap`→`adb pull`; resumable run-state in `.harness/`. 🟢 captured a real 1080×2400 PNG.
- **M7** Gallery + manifest + Markdown/CSV reports (`RunReporter`). ✅
- **M8** Makefile (chruby), README, `docs/adding-a-screen.md`, Settings screen, gitignore. ✅
- **Auto Capture** (post-E8, user request): toolbar button runs an automated UI-Automator sweep
  (Home → section → screen via `uiautomator dump` + tap-by-text-center + Back), 2 levels deep, saving
  to `runs/<id>/auto/` + `auto-manifest.json`; cancellable. Mechanism verified on a Python prototype
  the Dart mirrors. 🟢

**Quality gate (current):** `flutter analyze` clean · **33 tests pass** (9 test files) · `flutter build macos` OK · `make verify` green.

**Verified on real hardware:** SDK bootstrap (137 MB cmdline-tools + ~1.5 GB API-34 image) · AVD boot · build→install→launch (playground `topResumedActivity`) · capture (1080×2400 PNG of "Test Labs" home) · auto-sweep navigation (Home→Buttons→Primary Button, etc.).

**NOT yet verified (the user's to do):** the in-app GUI click-through (no visible macOS GUI in the
agent session) — running the wizard, manual Flow Runner pass, and the Auto Capture button end to end.

### 2. `NativePlatformKit-scaffold/` — the publishable library (phase 1)
Production-grade scaffold done: `NpkTheme` + design tokens + reference `NpkButton`, explicit-API +
binary-compatibility-validator, Roborazzi screenshot tests, Dokka, vanniktech publishing, CI. Its own
`CLAUDE.md`/`DECISIONS.md`. Phase 2 (the real component catalog) is future work. Not touched recently.

### 3. `NativePlatformKit-TestLabs-Android-Playground/` — target app
Pre-existing single-activity Jetpack-Navigation app, ~80 demo fragments. Builds with Gradle 8.9 /
compileSdk 34. **Launch component:** applicationId `ai.offside.mobile.android.testlabs` ≠ namespace
`ai.offside.mobile.android.helper.testlabs`; launch =
`ai.offside.mobile.android.testlabs/ai.offside.mobile.android.helper.testlabs.nav.TestlabsMainActivity`.

## Git
- HEAD: `7621e6b Moved screenshot to repo root` (the user has been committing; not just initial).
- The user controls commits — don't commit unless asked.

## Open items / next steps (pick up here)
1. **Reconcile the screenshots location** (see CLAUDE.md ⚠️): repo-root `screenshots/` now exists, but
   `HarnessPaths.defaultRoot` makes the harness write to `NativePlatformKit-testharness/screenshots/`.
   Point `HarnessPaths` at the chosen single location (likely repo root).
2. **User to run the GUI end-to-end** (wizard → Flow Runner → Auto Capture → Gallery) and report.
3. **Optional:** increase Auto Capture crawl depth (>2) to capture nested sub-flows (stepper steps,
   bottom-sheet variants) — currently captures Home + sections + their direct screens.
4. **Optional:** refine `assets/catalog.json` nav instructions to match the real home-grid labels
   (Theme, Typography, Buttons, Modals, Slider, Controls, Info Boxes, Amount Input Field, Card Tile
   Component, Stepper, WebViews, Account Tile, …) — current text is a structural baseline.
5. **Settings persistence:** make playground path + screenshots dir editable/persisted (currently
   machine-specific defaults; Settings screen is read-only).

## Resume commands
```bash
source ~/.zprofile && source ~/.zshrc
source /opt/homebrew/opt/chruby/share/chruby/chruby.sh && chruby ruby-3.4.4
cd NativePlatformKit-testharness
make verify        # analyze + 33 tests
make run           # launch the harness on macOS
```
