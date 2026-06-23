# NativePlatformKit Test Harness — Implementation Roadmap

A human-in-the-loop **Flutter macOS desktop** app (`NativePlatformKit-testharness/`) that boots an
Android emulator, builds & installs the `NativePlatformKit-TestLabs-Android-Playground` app, then
guides a human through every one of its ~78 UI screens, capturing a screenshot of each into
`NativePlatformKit-testharness/screenshots/`.

This roadmap breaks the work into **Epics → Stories → Tasks**, with a **one-to-one mapping between
Epics and Milestones** (Epic `Ek` ⇔ Milestone `Mk`).

---

> Status legend: ⬜ not started · 🟡 in progress · ✅ done · ⏸️ blocked/deferred · 🟢 verified on-device

---

## Locked architectural decisions

Captured during the design interview. These are fixed unless explicitly revisited.

| # | Area | Decision |
|---|------|----------|
| D1 | Harness platform | Flutter **macOS desktop** app |
| D2 | Architecture | **Provider + MVVM**, feature-first layering |
| D3 | Native OS integration | **Swift** layer: `MethodChannel` (commands) + `EventChannel` (live stdout/stderr/progress/exit), per-process handles for cancel/kill |
| D4 | macOS build toolchain | **CocoaPods** (pod-based), build scripts pin **chruby Ruby 3.4.4** (NOT Swift Package Manager) |
| D5 | Visual design | Native macOS look via **`macos_ui`**, follows system light/dark |
| D6 | Emulator image | **API 34 · google_apis · arm64-v8a** |
| D7 | AVD profile | **Pixel 6 · 4 GB RAM · hardware GPU** |
| D8 | SDK bootstrap | **In-app**: Environment screen installs cmdline-tools + system image + accepts licenses |
| D9 | App acquisition | Harness runs `./gradlew :app:assembleDebug` → `adb install -r` (JDK 17) |
| D10 | Step-through | **Guided manual** + 1-click capture |
| D11 | Live view | **Native emulator window** + harness side panel |
| D12 | Flow catalog | Auto-enumerated from nav graphs + main-page index maps, then **curated** instructions |
| D13 | Capture granularity | **One capture per destination fragment** (~78); transient states ad hoc |
| D14 | Flow Runner layout | **Three-pane**: tree · detail · preview |
| D15 | App navigation | **Guided wizard** (Environment→Emulator→Build→Run) then free **sidebar** |
| D16 | Run persistence | **Resumable** + per-screen status/notes + exported **Markdown + CSV** report |
| D17 | Screenshot capture | `adb exec-out screencap -p`, **full-screen PNG as-is** |
| D18 | Output layout | `screenshots/runs/<timestamp>/<category>/<id>.png` + canonical `latest/` + per-run `manifest.json` |
| D19 | Config/state store | Project-local **`.harness/`** (config.json + resumable run-state) |
| D20 | Git policy | **Commit everything** incl. all timestamped runs and `.harness/` |

---

## Epic ⇄ Milestone map

| Epic | Milestone | Title |
|------|-----------|-------|
| **E0** | M0 | Project Scaffold & App Shell |
| **E1** | M1 | Native Bridge (Swift) |
| **E2** | M2 | Environment Detection & SDK Bootstrap |
| **E3** | M3 | Emulator Manager |
| **E4** | M4 | Build & Install Pipeline |
| **E5** | M5 | Flow Catalog |
| **E6** | M6 | Flow Runner (Guided Capture) |
| **E7** | M7 | Gallery, Manifest & Reports |
| **E8** | M8 | Polish, Docs & Git Hygiene |

**ID scheme:** `E<epic>` → `S<epic>.<story>` → `T<epic>.<story>.<task>`. Status markers use the legend at the top of this document.

---

## E0 — Project Scaffold & App Shell  (M0)  ✅

**Goal:** A buildable, analyzable Flutter macOS app with the macos_ui shell, Provider wiring, theme,
and the wizard-then-sidebar navigation containing placeholder screens for all six sections.

### S0.1 — Flutter macOS project bootstrap
- T0.1.1 `flutter create` a macOS-enabled project at `NativePlatformKit-testharness/` (org `ai.offside`, no iOS/web/win/linux targets).
- T0.1.2 Pin Flutter 3.44.2; add `pubspec.yaml` deps: `provider`, `macos_ui`, `path`, `path_provider`, `collection`; dev: `flutter_lints`.
- T0.1.3 Add `analysis_options.yaml` (strict lints) and confirm `flutter pub get` + `flutter analyze` are clean.
- T0.1.4 Verify a baseline `flutter build macos` succeeds (exercises the chruby Ruby 3.4.4 / CocoaPods path — see S8.x).

### S0.2 — App shell, theme & navigation
- T0.2.1 `MacosApp` root with `macos_ui` theme (light/dark following system), app accent + typography.
- T0.2.2 `MacosWindow` + `Sidebar` with six items: Environment · Emulator · Build & Install · Flow Runner · Gallery · Settings.
- T0.2.3 First-run **guided wizard** mode (Environment→Emulator→Build→Run) that, once readiness is met, unlocks free sidebar nav.
- T0.2.4 Placeholder `ContentArea` page per section with title + "not yet implemented" state.

### S0.3 — State management & app structure
- T0.3.1 Establish folders: `lib/{app,core,features,data,services}` per the spec.
- T0.3.2 Root `MultiProvider`; an `AppStateViewModel` (`ChangeNotifier`) holding readiness + current section.
- T0.3.3 MVVM base conventions doc-comment (View ⇄ ViewModel ⇄ Service) and a `Result<T>`/`AsyncValue` helper type.

**Acceptance:** app launches on macOS, shows the wizard then sidebar, navigates between six placeholder
screens; `flutter analyze` clean.

---

## E1 — Native Bridge (Swift)  (M1)  ✅

**Goal:** A robust Swift↔Dart bridge that can run arbitrary CLIs with live streaming output and
cancellation, plus native file dialogs.

### S1.1 — Channel plumbing
- T1.1.1 Swift `NpkBridge`: register `MethodChannel("npk/commands")` + `EventChannel("npk/events")` in `MainFlutterWindow`.
- T1.1.2 Dart `NativeBridge` service wrapping invoke + a broadcast `Stream<ProcessEvent>` keyed by handle.
- T1.1.3 Define payload contracts (`ProcessEvent{handle,type:stdout|stderr|progress|exit,data,code}`) and error mapping.

### S1.2 — Process runner
- T1.2.1 Swift `ProcessRunner` using `Foundation.Process`/`Pipe`, streaming stdout/stderr lines over EventChannel.
- T1.2.2 Per-process `handle` registry; `process.cancel(handle)` → terminate (SIGTERM→SIGKILL).
- T1.2.3 Environment injection (PATH, ANDROID_HOME, JAVA_HOME) and configurable working dir.
- T1.2.4 Dart-side `CommandRunner` with `run()` (await exit) and `stream()` (live) helpers + timeouts.

### S1.3 — Native file dialogs & misc
- T1.3.1 `panel.chooseDirectory` / `panel.chooseFile` via `NSOpenPanel`.
- T1.3.2 macOS entitlements review for subprocess + file access (keep CocoaPods/sandbox config consistent with D4).
- T1.3.3 A debug "Console" drawer in the UI that tails any running process stream.

**Acceptance:** from the UI, run `adb version` (or `echo`) and see live streamed output; cancel a
long `sleep`; pick a directory via native panel.

---

## E2 — Environment Detection & SDK Bootstrap  (M2)  🟢

**Goal:** The Environment screen detects all tooling and can install the missing Android SDK pieces
needed to create an emulator, with live progress.

### S2.1 — Detection
- T2.1.1 `EnvironmentService.detect()` → Flutter, JDK, adb, emulator, sdkmanager, avdmanager, cmdline-tools, installed system images, AVDs, paths.
- T2.1.2 Environment screen: per-tool status rows (found/version/path or missing) with an overall readiness summary.

### S2.2 — SDK bootstrap (in-app)
- T2.2.1 Install **cmdline-tools** (fetch + unzip into `$ANDROID_HOME/cmdline-tools/latest`) via the bridge.
- T2.2.2 `sdkmanager` install of `system-images;android-34;google_apis;arm64-v8a` with **license acceptance**, streaming progress.
- T2.2.3 Idempotent re-detection after each step; disable actions already satisfied.
- T2.2.4 Error/retry UX for network/license failures.

**Acceptance:** on a machine missing cmdline-tools + the image, the Environment screen installs both
and flips to "ready" without leaving the app.

---

## E3 — Emulator Manager  (M3)  🟢

**Goal:** Create, boot, and stop the Pixel 6 / API 34 AVD; detect boot completion reliably.

### S3.1 — AVD lifecycle
- T3.1.1 `avd.create` (Pixel 6 device, API 34 google_apis arm64 image, 4 GB RAM, hardware GPU); `avd.list`; `avd.delete`.
- T3.1.2 Emulator Manager screen: AVD list, Create button, status (cold/booting/online).

### S3.2 — Boot & readiness
- T3.2.1 `emulator.boot` (windowed, hardware GPU); capture serial; `emulator.stop`.
- T3.2.2 Readiness: `adb wait-for-device` + poll `sys.boot_completed` + dismiss/skip lockscreen; surface progress.
- T3.2.3 Cold-boot / wipe-data options; single-instance guard.

**Acceptance:** one click creates (if needed) and boots the emulator to home screen; status reflects
online; stop terminates it.

---

## E4 — Build & Install Pipeline  (M4)  ⬜

**Goal:** Build the playground debug APK and install it to the booted emulator, with live logs.

### S4.1 — Gradle build
- T4.1.1 `gradle.assembleDebug` runs `./gradlew :app:assembleDebug` in the playground dir (JDK 17), streaming output.
- T4.1.2 Locate the produced APK; surface success/failure + duration.

### S4.2 — Install & launch
- T4.2.1 `adb install -r` the APK to the target serial; handle reinstall/downgrade.
- T4.2.2 `app.launch` the main activity (`am start` `ai.offside.mobile.android.helper.testlabs.nav.TestlabsMainActivity`).
- T4.2.3 Build & Install screen: Build → Install → Launch actions with state + log pane.

**Acceptance:** from a clean emulator, the harness builds, installs, and launches the playground to
its home screen.

---

## E5 — Flow Catalog  (M5)  ⬜

**Goal:** A curated catalog of all ~78 destinations with human navigation instructions, derived from
the app's navigation structure.

### S5.1 — Enumeration
- T5.1.1 Parse `app/src/main/res/navigation/*.xml` + the main-page `index→action` mappings to enumerate destinations and category grouping.
- T5.1.2 Generator script/notes producing a `catalog.json` skeleton (`{categories:[{id,title,screens:[{id,title,navInstructions,description,screenshot}]}]}`).

### S5.2 — Curation & model
- T5.2.1 Hand-write human `navInstructions[]` and `description` for each screen (e.g., "From Home → tap 'Buttons' → 'Primary Buttons'").
- T5.2.2 Dart models (`FlowCategory`, `FlowScreen`) + `CatalogService` loader + validation (unique ids, non-empty instructions).
- T5.2.3 Coverage check: catalog screen count reconciles with the fragment inventory (~78).

**Acceptance:** `catalog.json` loads, validates, and lists every category/screen with usable
step-by-step instructions.

---

## E6 — Flow Runner (Guided Capture)  (M6)  ⬜

**Goal:** The core three-pane guided experience: step through screens, capture each, track status.

### S6.1 — Three-pane UI
- T6.1.1 Left: category→screen **tree** with per-item status badges + overall progress.
- T6.1.2 Center: current screen **detail** (nav instructions, description, status, Capture / Skip / Note, Re-capture).
- T6.1.3 Right: **preview** of the just-captured image (with zoom).

### S6.2 — Capture & state
- T6.2.1 `screencap.capture` → write PNG to `runs/<ts>/<category>/<id>.png` and mirror to `latest/`.
- T6.2.2 Per-screen status (`captured`/`skipped`/`failed`) + free-text notes; advance to next on capture.
- T6.2.3 **Resumable** run-state persisted to `.harness/` (resume, restart, jump-to-screen).
- T6.2.4 Run lifecycle: start/resume run, run header (device, app SHA), completion summary.

**Acceptance:** a full guided pass over all screens produces one PNG per captured screen, with
statuses/notes saved and resumable across app restarts.

---

## E7 — Gallery, Manifest & Reports  (M7)  ⬜

**Goal:** Persist run metadata and make captured runs browsable and reportable.

### S7.1 — Manifest
- T7.1.1 Write per-run `manifest.json` (runId, device {avd,api,abi,resolution}, app {package,gitSha,versionName}, harnessVersion, per-screen records, summary).
- T7.1.2 Capture the playground git SHA + versionName at run start.

### S7.2 — Gallery & reports
- T7.2.1 Gallery screen: pick a run, grid of thumbnails by category, click to view full-size + metadata.
- T7.2.2 Export **Markdown** + **CSV** report alongside the manifest on run completion.
- T7.2.3 Show capture coverage (captured/skipped/failed) per run.

**Acceptance:** completing a run writes manifest + reports; the Gallery browses any run's screenshots
with metadata.

---

## E8 — Polish, Docs & Git Hygiene  (M8)  ⬜

**Goal:** Production-quality finish: reliable build, documentation, and committed outputs.

### S8.1 — Build reliability (CocoaPods / chruby)
- T8.1.1 `Makefile`/build script that activates **chruby Ruby 3.4.4** (or absolute `pod`) before `pod install` / `flutter build macos`.
- T8.1.2 Document the toolchain requirement; verify a clean `flutter build macos` from a fresh shell.

### S8.2 — Docs
- T8.2.1 `README.md` (prereqs, first-run wizard, how to run a capture pass, output layout).
- T8.2.2 Architecture notes (channel API surface, catalog/manifest schemas) + a CONTRIBUTING-style "adding a screen to the catalog" recipe.

### S8.3 — Git hygiene
- T8.3.1 `.gitignore` for Flutter (`.dart_tool/`, `build/`, ephemeral) — but per D20 **commit** screenshots/`runs/` and `.harness/`.
- T8.3.2 Confirm no secrets/keystores tracked; final repo-root `.gitignore` reconciliation.

### S8.4 — QA
- T8.4.1 End-to-end dry run: fresh emulator → build/install → full guided capture → reports.
- T8.4.2 Empty/error states (no device, build failure, capture failure) handled gracefully.

**Acceptance:** a fresh checkout can, with documented steps, run the full pipeline and produce a
committed run with screenshots, manifest, and reports.

---

## Cross-cutting Definition of Done
- `flutter analyze` clean; app builds & runs on macOS.
- Every long-running op streams live output and is cancellable.
- All decisions D1–D20 honored.
- Screenshots land in `NativePlatformKit-testharness/screenshots/` per D18.

## Risks & watch-items
- **CocoaPods/chruby** build env (D4) — mitigated by S8.1; primary integration risk.
- **Emulator image download** size/network (E2) and arm64 boot performance (E3).
- **Catalog drift** if the playground's navigation changes — coverage check (T5.2.3) guards this.
- **Repo size** from committing all runs (D20) — accepted by decision.
