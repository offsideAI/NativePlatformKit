# UI Testlabs (standalone)

A self-contained Android app that hosts Offside's **UI component browser** ("UI Redesign Library")
and **all of its UI components**, extracted from the `mbl-massmarket-android` monorepo so it
builds and runs **without the Offside corporate network, VPN, or private Artifactory**.

It uses only public dependencies (AndroidX, Material, Picasso, Facebook Shimmer, Jake Wharton
Timber). All proprietary/internal couplings have been replaced by thin local stubs.

## Modules

| Module    | Origin in monorepo            | Notes |
|-----------|-------------------------------|-------|
| `:ui`     | `components/ui` (`main` only) | The ~150 UI components + resources. Namespace `ai.offside.mobile.android.component.ui`. |
| `:app`    | `helpers/testlabs` (`main`) | The component-browser app (`TestlabsMainActivity`). Namespace `ai.offside.mobile.android.helper.testlabs`. |
| `:stubs`  | n/a (new)                     | Minimal replacements for the private modules the UI touched. |

## Build & run

Requires Android SDK (API 34) and JDK 17.

```bash
./gradlew :app:assembleDebug          # builds app/build/outputs/apk/debug/app-debug.apk
./gradlew installDebug                 # install on a connected device/emulator
```

Or just open the folder in Android Studio and Run `app`. The launcher label is **"Testlabs"**
(applicationId `ai.offside.mobile.android.testlabs`).

## What was changed during extraction

The monorepo's `components/ui` declared a large transitive dependency graph (~30 modules,
including Glassbox, Akamai, Trusteer, Adobe and Offside-internal SDKs), but the actual **source**
coupling was tiny. Only the following had to be substituted:

- **`:stubs`** provides minimal stand-ins for the symbols the UI/testlabs actually used:
  - `component.application`: `ApplicationComponent`, `ApplicationComponentInitializer`,
    `AppActivityLifecycleCallbacks`
  - `component.storage`: `AppStorage` (+ key/read/write request types) — backed by
    `SharedPreferences`, so theme / UI-mode selections persist
  - `component.security.ui.SecureWebViewClient` (plain `WebViewClient`)
  - `lib.glassbox.LibGlassbox` (no-op analytics)
  - `lib.timber` — the **real** monorepo `TimberExtensions`/`OffsideLogger`, on public Timber
- The `component.ui` **`debug` source set** was dropped (it required the `io.palaima` debug-drawer
  SDK and was not referenced by the browser). A few resources the testlabs still referenced
  (3 `debug_storage_preference_ui_mode_*` strings, 4 `ic_navigation_*_selector` drawables from
  `components/navigation`) were copied in; the orphan `DebugUIRedesignModule` + its layout were removed.
- Bootstrapping uses **androidx.startup** (`UIComponentConfigInitializer` → `ApplicationComponentInitializer`),
  declared in `app/src/main/AndroidManifest.xml`.

### Two build-config notes
- `vectorDrawables.useSupportLibrary = true` in both modules — the component vectors use theme
  attributes (`?attr/offside_primary`) as colors, which AGP's build-time vector→PNG rasterizer can't
  resolve. minSdk 23 supports vectors natively, so PNG generation is skipped.
- `org.gradle.vfs.watch=false` in `gradle.properties` — this folder lives under iCloud Drive, where
  Gradle's native file-watcher is unreliable.
- `sop_background_image_retail.xml` (a ~1965-path decorative sign-on background vector that broke the
  resource merger) was replaced with a solid-color placeholder of the same name. Unused by the browser.

## Caveats
- This is the **Offside** product flavor's component set with the **debug** tooling removed.
- WebView demos load real URLs (needs internet); analytics/Glassbox calls are no-ops.
