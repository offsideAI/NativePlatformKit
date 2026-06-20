<!--
  Copyright 2026 The NativePlatformKit Authors
  Licensed under the Apache License, Version 2.0. See the LICENSE file.
-->
# Changelog

All notable changes to this project are documented here.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project
adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html). See the
[SemVer policy](CONTRIBUTING.md#semantic-versioning-policy) for what each bump means.

## [Unreleased]

_Add entries here as you work. Group under Added / Changed / Deprecated / Removed / Fixed / Security._

## [0.1.0] - 2026-06-19

Initial scaffold release: a production-grade, fully-wired but intentionally near-empty library.
Adding a component is a mechanical operation — see
[docs/adding-a-component.md](docs/adding-a-component.md).

### Added

- **Theming foundation**: `NpkTheme` entry point with light/dark and Material You dynamic color,
  exposing design tokens via CompositionLocals (`LocalNpkColors`, `LocalNpkTypography`,
  `LocalNpkShapes`, `LocalNpkSpacing`, `LocalNpkElevation`) and clean Material 3 interop.
- **Design tokens**: `NpkColors`, `NpkTypography`, `NpkShapes`, `NpkSpacing`, `NpkElevation`.
- **Reference component**: `NpkButton` (with `NpkButtonStyle`) — the canonical end-to-end template
  (composable + KDoc + previews + unit/UI test + screenshot test + catalog entry + API-dump entry).
  Not part of the eventual production catalog.
- **`@ExperimentalNpkApi`** opt-in annotation for unstable surfaces.
- **Build infrastructure**: `build-logic` convention plugins, version catalog, explicit-API mode,
  binary-compatibility-validator, Spotless + detekt, Roborazzi screenshot testing, Dokka, and
  Maven Central (Central Portal) publishing via the vanniktech plugin.
- **`:catalog`** demo app showcasing `NpkButton`.

[Unreleased]: https://github.com/arunabhdas/nativeplatformkit/compare/v0.1.0...HEAD
[0.1.0]: https://github.com/arunabhdas/nativeplatformkit/releases/tag/v0.1.0
