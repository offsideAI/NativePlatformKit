<!--
  Copyright 2026 The NativePlatformKit Authors
  Licensed under the Apache License, Version 2.0. See the LICENSE file.
-->
# NativePlatformKit (NPK)

[![Maven Central](https://img.shields.io/maven-central/v/das.arunabh.nativeplatformkit/nativeplatformkit.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/das.arunabh.nativeplatformkit/nativeplatformkit)
[![CI](https://github.com/arunabhdas/nativeplatformkit/actions/workflows/ci.yml/badge.svg)](https://github.com/arunabhdas/nativeplatformkit/actions/workflows/ci.yml)
[![API check](https://github.com/arunabhdas/nativeplatformkit/actions/workflows/api-check.yml/badge.svg)](https://github.com/arunabhdas/nativeplatformkit/actions/workflows/api-check.yml)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.4.0-blue.svg?logo=kotlin)](https://kotlinlang.org)

**NativePlatformKit** is an extensible, Material 3-based **Jetpack Compose** UI component library,
distributed as an AAR via Maven Central and designed to be consumed and re-licensed by third
parties building their own Android apps.

> [!NOTE]
> This repository is currently the **production-grade scaffold**. The design-system foundation
> (theme + tokens) and a single canonical reference component — [`NpkButton`](nativeplatformkit/src/main/kotlin/das/arunabh/nativeplatformkit/component/NpkButton.kt) —
> are in place. The component catalog itself is built in a later phase. To add a component, follow
> [docs/adding-a-component.md](docs/adding-a-component.md).

## Install

```kotlin
// settings.gradle.kts → dependencyResolutionManagement { repositories { mavenCentral() } }

// build.gradle.kts
dependencies {
    implementation("das.arunabh.nativeplatformkit:nativeplatformkit:0.1.0")
}
```

Requirements for consumers: **minSdk 24**, **compileSdk 36+**, Kotlin 2.x, and the Compose
compiler plugin applied to your module.

## Quick start

Wrap your UI in `NpkTheme` and use NPK components. Components only ever read from the theme — never
hard-coded values.

```kotlin
import androidx.compose.material3.Text
import das.arunabh.nativeplatformkit.component.NpkButton
import das.arunabh.nativeplatformkit.component.NpkButtonStyle
import das.arunabh.nativeplatformkit.theme.NpkTheme

@Composable
fun Greeting() {
    NpkTheme(dynamicColor = true) { // light/dark + Material You on Android 12+
        NpkButton(onClick = { /* ... */ }, style = NpkButtonStyle.Filled) {
            Text("Get started")
        }
    }
}
```

Read design tokens anywhere inside `NpkTheme`:

```kotlin
val accent = NpkTheme.colors.primary
val gap = NpkTheme.spacing.medium     // 16.dp
val card = NpkTheme.shapes.large
```

## Theming

`NpkTheme` is the single entry point. It exposes design tokens through `CompositionLocal`s and wraps
a Material 3 `MaterialTheme` derived from the same tokens, so Material components stay visually
consistent with NPK components.

| Token | Accessor | CompositionLocal |
|---|---|---|
| Colors | `NpkTheme.colors` | `LocalNpkColors` |
| Typography | `NpkTheme.typography` | `LocalNpkTypography` |
| Shapes | `NpkTheme.shapes` | `LocalNpkShapes` |
| Spacing | `NpkTheme.spacing` | `LocalNpkSpacing` |
| Elevation | `NpkTheme.elevation` | `LocalNpkElevation` |

Supports light/dark (`darkTheme`), Material You dynamic color (`dynamicColor`, Android 12+), and
fully custom palettes by passing your own `NpkColors`, `NpkTypography`, etc.

## Module map

| Module | Published? | Purpose |
|---|---|---|
| **`:nativeplatformkit`** | ✅ AAR | The library. `theme/`, `token/`, `foundation/`, `component/`. The artifact consumers depend on. |
| **`:catalog`** | ❌ | Demo app showcasing components; doubles as manual QA and living documentation. |
| **`build-logic/`** | ❌ | Composite build of Gradle convention plugins (`npk.*`). |

## Building & testing

```bash
./gradlew build                          # assemble + unit/UI tests + lint + spotless + detekt + apiCheck
./gradlew test                           # JVM (Robolectric) unit + Compose UI tests
./gradlew :nativeplatformkit:verifyRoborazziDebug   # verify screenshots against committed references
./gradlew :nativeplatformkit:recordRoborazziDebug   # re-record reference screenshots
./gradlew apiCheck                       # fail on unintended public-API changes
./gradlew apiDump                        # regenerate the committed .api dump after an intended change
./gradlew spotlessApply                  # auto-format + apply license headers
./gradlew detekt                         # static analysis
./gradlew dokkaGenerate                  # API docs → build/dokka/html
./gradlew publishToMavenLocal            # publish to ~/.m2 for local testing
./gradlew :catalog:installDebug          # run the demo app on a device/emulator
```

> Run `./gradlew clean` as a **separate** invocation (not `clean build`) — see ADR-0011.

## Compatibility

| Tool | Version |
|---|---|
| Android Gradle Plugin | 8.13.2 |
| Gradle | 8.14.5 |
| Kotlin | 2.4.0 |
| Compose BOM | 2026.06.00 (Material 3) |
| compileSdk / minSdk / JDK | 36 / 24 / 17 |

See [`gradle/libs.versions.toml`](gradle/libs.versions.toml) for the authoritative version catalog
and [DECISIONS.md](DECISIONS.md) for the rationale behind each choice.

## Contributing

We welcome contributions! Please read [CONTRIBUTING.md](CONTRIBUTING.md) (including the
[Semantic Versioning policy](CONTRIBUTING.md#semantic-versioning-policy)) and our
[Code of Conduct](CODE_OF_CONDUCT.md). Contributions are accepted under the
[DCO](CONTRIBUTING.md#developer-certificate-of-origin-dco).

To add a component, follow the step-by-step recipe in
[docs/adding-a-component.md](docs/adding-a-component.md).

## License

NativePlatformKit is licensed under the [Apache License 2.0](LICENSE). It is intended to be
consumed and re-licensed by third parties building their own apps.
