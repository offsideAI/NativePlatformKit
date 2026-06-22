# CLAUDE.md

Guidance for Claude Code (and humans) working in this repository.

## What this is

**NativePlatformKit (NPK)** — an extensible, Material 3-based Jetpack Compose UI component library,
published as an AAR to Maven Central (`das.arunabh.nativeplatformkit:nativeplatformkit`) and meant
to be consumed and re-licensed by third parties. Quality, public-API stability, and a frictionless
contributor experience are the priorities — over speed.

**Status: this is the scaffold (phase 1).** The theming foundation and exactly one canonical
reference component (`NpkButton`) exist. The real component catalog is **phase 2** — add components
by following [`docs/adding-a-component.md`](docs/adding-a-component.md). Do **not** treat `NpkButton`
as a production component; it is the gold-standard template.

> **Location:** the project root is this `NativePlatformKit-scaffold/` directory (a subfolder of the
> git repo). Run all `./gradlew` commands from here.

## Layout

| Path | Purpose |
|---|---|
| `nativeplatformkit/` | The published library. Packages: `theme/`, `token/`, `foundation/`, `component/`. |
| `nativeplatformkit/api/nativeplatformkit.api` | Committed public-API dump (binary-compatibility-validator). |
| `nativeplatformkit/src/test/screenshots/` | Committed Roborazzi reference images. |
| `catalog/` | Demo app (NOT published) — showcase + manual QA. |
| `build-logic/convention/` | Gradle convention plugins: `npk.android.library`, `npk.android.library.compose`, `npk.android.application`, `npk.kotlin.options`, `npk.publish`. |
| `gradle/libs.versions.toml` | Single source of truth for all versions. |
| `config/{detekt,spotless}/` | detekt config; Spotless license header. |
| `DECISIONS.md` | ADRs — read this for *why* anything is the way it is. |

## Common commands (run from this directory)

```bash
./gradlew build                                   # assemble + tests + lint + spotless + detekt + apiCheck
./gradlew test                                    # JVM (Robolectric) unit + Compose UI tests (debug only)
./gradlew :nativeplatformkit:verifyRoborazziDebug # verify screenshots vs committed refs
./gradlew :nativeplatformkit:recordRoborazziDebug # re-record refs (review + commit the PNGs)
./gradlew apiCheck            # fail on unintended public-API change
./gradlew apiDump             # regenerate the .api dump after an INTENDED API change (commit it)
./gradlew spotlessApply       # format + add license headers (run before committing)
./gradlew detekt
./gradlew dokkaGenerate       # Dokka v2 → build/dokka/html  (note: NOT dokkaHtml)
./gradlew publishToMavenLocal # → ~/.m2 (works without credentials for SNAPSHOTs)
./gradlew :catalog:assembleDebug
```

## Conventions (non-negotiable)

- **Explicit API strict** on `:nativeplatformkit` — declare `public`/`internal` and explicit return
  types. Every public declaration needs **KDoc**.
- **Components read only from `NpkTheme`** (`colors`, `typography`, `shapes`, `spacing`,
  `elevation`). Never hard-code a color/dimension/text style.
- **Public API is a contract.** Any change to the surface must update the committed `.api` dump
  (`apiDump`) and be weighed against the [SemVer policy](CONTRIBUTING.md#semantic-versioning-policy).
  Mark unstable APIs `@ExperimentalNpkApi`.
- **Formatting** is owned by Spotless/ktlint (`spotlessApply`); `@Composable` functions are
  PascalCase (configured in `.editorconfig`). No wildcard imports. Apache-2.0 header on every file.
- **DCO**: sign off commits with `git commit -s`. Conventional Commit subjects.
- New components must ship: composable + previews + unit/UI test (with an a11y assertion) +
  screenshot test (+committed refs) + catalog entry + `apiDump` + CHANGELOG entry.

## Gotchas (learned the hard way — see DECISIONS.md)

- **compileSdk is capped at 36** in this environment (only `android-34`/`android-36.1` SDK platforms
  installed; no `sdkmanager`). This forced `core-ktx 1.18.0`, `lifecycle 2.10.0`, and AGP 8.13.2 —
  the newest versions that don't require AGP 9.1 / compileSdk 37. Don't bump those without
  installing SDK platform 37 first (ADR-0004).
- **Roborazzi screenshots run at SDK 34 with NATIVE graphics** (`src/test/resources/robolectric.properties`).
  At the `android-36.1` runtime, Compose rendered blank even though composition succeeded. The
  library still compiles against SDK 36; only the test runtime is 34 (ADR-0007).
- **Library unit tests run on debug only** (release is disabled in the library convention) because
  the Compose `ui-test-manifest` (host activity) is debug-scoped (ADR-0008).
- **Don't run `./gradlew clean` together with other tasks** — a parallel clean-vs-task race on
  `build/` fails Spotless. Run `clean` as its own invocation (ADR-0011).
- Convention plugins compile with `-Xskip-metadata-version-check` because Gradle 8.14.5's
  `kotlin-dsl` embeds an older Kotlin than the plugin artifacts we compile against (ADR-0005).
- Screenshot tests use Roborazzi's **rule-free** `captureRoboImage { }`; behavior tests use the
  **v2** Compose rule (`androidx.compose.ui.test.junit4.v2`).

## Publishing

Maven Central via the **Central Portal** (vanniktech plugin); OSSRH is EOL. Version is `VERSION_NAME`
in `gradle.properties` (SNAPSHOT vs release). Credentials/GPG come from env vars only — see
[`docs/publishing.md`](docs/publishing.md). A `v*` tag triggers `.github/workflows/publish.yml`.
