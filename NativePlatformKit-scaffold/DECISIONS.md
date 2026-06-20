# Architecture Decision Records (ADRs)

Lightweight ADRs for NativePlatformKit (NPK). Each entry records a decision, its context, and the
rationale, so future contributors understand *why* the project is shaped the way it is. Newest
last. Status: Accepted unless noted.

Verified toolchain versions are in [`gradle/libs.versions.toml`](gradle/libs.versions.toml) (the
single source of truth). Versions below were verified against live Maven/Gradle metadata on
**2026-06-19**.

---

## ADR-0001 — Configuration values for the blanks

The task brief left several configuration values blank; staff-level defaults were chosen:

| Field | Value | Rationale |
|---|---|---|
| GitHub org/repo | `arunabhdas/nativeplatformkit` | Matches the Maven group owner. |
| License | **Apache-2.0** | Permissive and business-friendly for third parties re-licensing the AAR; the explicit default in the brief. |
| Min SDK | **24** | Brief default; ~99%+ device coverage while allowing modern APIs. |
| Compile/Target SDK | **36** | See ADR-0004 — capped by the installed SDK platform. |
| JVM/Java target | **17** | Brief default; current LTS, required by AGP 8.x. |
| Screenshot tool | **Roborazzi** | JVM-based, no device/emulator; brief default. See ADR-0007. |
| Module strategy | **Single publishable module now** | `:nativeplatformkit` + `:catalog`; structured (convention plugins, tokens) to split later. |
| Contribution model | **DCO** | See ADR-0009. |

---

## ADR-0002 — Build toolchain: AGP 8.13.2 + Gradle 8.14.5 + Kotlin 2.4.0

**Decision.** Pin AGP **8.13.2** (latest stable 8.x), Gradle **8.14.5** (latest 8.x), Kotlin
**2.4.0**, Java target **17**.

**Context.** At build time the absolute-latest stable was AGP 9.2.1 / Gradle 9.6.0. The brief
flagged AGP 9 / compileSdk 37 as "arriving" and asked us to "target current stable and verify
compatibility before pinning."

**Rationale.** The wider plugin ecosystem we depend on — detekt 1.23.8, binary-compatibility-
validator 0.18.1, vanniktech 0.36.0, Roborazzi 1.64.0 — is battle-tested on AGP 8.x. AGP 9 also
requires Gradle 9 and changes Kotlin integration. More decisively, our dependency set is capped at
compileSdk 36 (ADR-0004), and AGP 8.13 fully supports compileSdk 36. AGP 8.13.2 pairs with Gradle
8.14.5 (8.13 requires Gradle ≥ 8.13). Revisit when the toolchain and SDK platform 37 are available
(see ADR-0004).

---

## ADR-0003 — Compose compiler via the Gradle plugin (not `composeOptions`)

**Decision.** Apply `org.jetbrains.kotlin.plugin.compose` version-matched to Kotlin (2.4.0). Manage
all Compose artifacts through the Compose BOM (`platform(...)`), currently **2026.06.00**; never
hard-pin individual `androidx.compose.*` versions.

**Rationale.** Since Kotlin 2.0 the Compose compiler ships as a version-matched Gradle plugin; the
legacy `composeOptions { kotlinCompilerExtensionVersion }` is obsolete. The BOM keeps the many
Compose artifacts mutually consistent. BOM 2026.06.00 resolves to Compose `ui` 1.11.3 / Material3
1.4.0, both compatible with compileSdk 36.

---

## ADR-0004 — compileSdk = 36, pinned dependency versions

**Decision.** `compileSdk`/`targetSdk = 36`. Pin `androidx.core:core-ktx` to **1.18.0** and the
lifecycle suite to **2.10.0**.

**Context.** The CI/build environment has only the `android-34` and `android-36.1` SDK platforms
installed (and no command-line `sdkmanager` to add more). AGP 8.13.2 resolves `compileSdk = 36`
against the installed `android-36.1` platform. The newest dependency versions require more:
- `androidx.core:core-ktx:1.19.0` → requires AGP 9.1 **and** compileSdk 37.
- `androidx.lifecycle:lifecycle-runtime-compose:2.11.0` → requires AGP 9.1.

**Decision detail.** `core-ktx` 1.18.0 (minCompileSdk 36, minAGP 8.9.1) and lifecycle 2.10.0
(compose artifact minCompileSdk 35, minAGP 8.6.0) are the newest versions compatible with
AGP 8.13.2 / compileSdk 36. Compose BOM 2026.06.00, activity-compose 1.13.0, and Material3 1.4.0
are all already compileSdk-36 compatible and are kept current.

**Consequence.** When SDK platform 37 + AGP 9 are adopted, bump `core-ktx`, `lifecycle`, and the
toolchain together (ADR-0002).

---

## ADR-0005 — `build-logic` composite build with class-based convention plugins

**Decision.** A `build-logic` composite build hosts class-based convention plugins
(`npk.android.library`, `npk.android.library.compose`, `npk.android.application`,
`npk.kotlin.options`, `npk.publish`).

**Rationale.** This is the AndroidX / now-in-android pattern. Class-based plugins (vs precompiled
script plugins) get clean, type-safe access to the version catalog via
`extensions.getByType<VersionCatalogsExtension>()`, which precompiled script plugins do not. Module
build files stay tiny and DRY. The convention plugins are compiled with
`-Xskip-metadata-version-check` because Gradle 8.14.5's `kotlin-dsl` embeds an older Kotlin than the
plugin artifacts (KGP/Compose 2.4, vanniktech, Dokka 2.x) we compile against.

---

## ADR-0006 — Binary stability: explicit API + binary-compatibility-validator

**Decision.** Enable Kotlin `explicitApi = Strict` on `:nativeplatformkit`. Commit a
`.api` dump via binary-compatibility-validator (BCV); `apiCheck` runs in CI. `:catalog` is ignored
by BCV.

**Rationale.** This artifact is redistributed; its public surface is a contract. Explicit API forces
intentional visibility and explicit return types. The committed `.api` dump turns any accidental
public-API change into a reviewable diff. See [SemVer policy](CONTRIBUTING.md#semantic-versioning-policy).

---

## ADR-0007 — Screenshot testing with Roborazzi, pinned to SDK 34 + NATIVE graphics

**Decision.** Use Roborazzi (JVM/Robolectric, no emulator). Tests run with
`@GraphicsMode(NATIVE)`; `robolectric.properties` pins the emulated runtime to **SDK 34**. Reference
images are committed under `nativeplatformkit/src/test/screenshots/`.

**Rationale.** Roborazzi needs Robolectric's NATIVE (Skia) graphics to actually rasterize Compose;
under the default/legacy canvas, screenshots render blank. Robolectric's native-graphics support is
most reliable on SDK 34 — rendering Compose at the (minor) `android-36.1` runtime produced blank
images even though composition succeeded. The library still **compiles** against SDK 36; only the
emulated test runtime is 34. Roborazzi's rule-free `captureRoboImage { }` API is used for
screenshots (it composes, measures to intrinsic size, and renders the full tree).

---

## ADR-0008 — Unit tests run on the debug variant only

**Decision.** Disable the `release` unit-test variant on `:nativeplatformkit`
(`HasUnitTestBuilder.enableUnitTest = false` for release).

**Rationale.** Compose UI tests use `androidx.compose.ui:ui-test-manifest`, which is `debug`-scoped
by design (it contributes the host `ComponentActivity`). The release unit-test variant therefore
can't resolve that activity. Library bytecode is variant-independent for unit testing, so running
the suite once on debug is correct and is the common library convention. The Compose v2 testing
APIs (`androidx.compose.ui.test.junit4.v2`, StandardTestDispatcher) are used per current guidance;
screenshot tests call `waitForIdle()`/use the rule-free API to synchronize.

---

## ADR-0009 — Contribution model: DCO (not CLA)

**Decision.** Use the Developer Certificate of Origin (sign-off via `git commit -s`), not a CLA.

**Rationale.** For a permissively-licensed (Apache-2.0) community library, a DCO is lightweight,
requires no central paperwork or contributor database, and is the norm for projects of this kind
(Linux kernel, many CNCF projects). A CLA adds friction that conflicts with the goal of a
frictionless contributor experience. Enforced in CI via a DCO check on pull requests.

---

## ADR-0010 — Publishing via the Central Portal (vanniktech), not OSSRH

**Decision.** Publish to Maven Central through the **Central Portal** using
`com.vanniktech.maven.publish` 0.36.0. GPG-sign all release publications.

**Rationale.** OSSRH (oss.sonatype.org / s01 / the legacy Nexus staging flow) reached end-of-life on
2025-06-30. The vanniktech plugin targets the Central Portal by default and auto-configures the
sources jar and a Dokka-generated Javadoc jar. Release vs SNAPSHOT is controlled solely by
`VERSION_NAME` in `gradle.properties`; SNAPSHOTs and `publishToMavenLocal` skip signing, so local
testing needs no key. Credentials/keys come from environment variables only — see
[docs/publishing.md](docs/publishing.md).

---

## ADR-0011 — Configuration cache & build cache enabled (problems = warn)

**Decision.** Enable the Gradle configuration cache and build cache. Set
`org.gradle.configuration-cache.problems=warn`.

**Rationale.** Both caches speed up local and CI builds. A few third-party plugins (BCV, detekt) are
not yet fully configuration-cache compatible; `warn` lets the rest of the build benefit instead of
failing outright. Note: do not combine `clean` with other tasks in a single parallel invocation
(a `clean`-vs-task race on `build/`); run `./gradlew clean` separately.
