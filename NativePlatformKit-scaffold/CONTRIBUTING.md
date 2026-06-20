<!--
  Copyright 2026 The NativePlatformKit Authors
  Licensed under the Apache License, Version 2.0. See the LICENSE file.
-->
# Contributing to NativePlatformKit

Thanks for your interest in contributing! NativePlatformKit is a redistributed, API-stable
library, so we hold a high bar for API design, binary compatibility, documentation, and testing.
This guide explains how to work in the repo and what every change must satisfy.

By participating you agree to abide by our [Code of Conduct](CODE_OF_CONDUCT.md).

## Prerequisites

- **JDK 17** (the build targets Java 17).
- **Android SDK** with platform `android-36` (or the installed `android-36.1`) and build-tools.
- No global Gradle install needed — use the committed wrapper (`./gradlew`).

## Project layout

| Path | What |
|---|---|
| `nativeplatformkit/` | The published library. Internal packages: `theme/`, `token/`, `foundation/`, `component/`. |
| `catalog/` | Demo app (not published) — showcase + manual QA. |
| `build-logic/` | Gradle convention plugins (`npk.android.library`, `npk.android.library.compose`, `npk.android.application`, `npk.kotlin.options`, `npk.publish`). |
| `gradle/libs.versions.toml` | The single source of truth for all versions. |
| `config/` | detekt config and the Spotless license header. |
| `docs/` | Contributor docs, including the recipe for adding a component. |

## Common commands

```bash
./gradlew build              # everything CI gates on (assemble, test, lint, spotless, detekt, apiCheck)
./gradlew test               # JVM (Robolectric) unit + Compose UI tests
./gradlew :nativeplatformkit:verifyRoborazziDebug   # verify screenshots
./gradlew :nativeplatformkit:recordRoborazziDebug   # update reference screenshots (review the diff!)
./gradlew spotlessApply      # auto-format + add license headers (run before committing)
./gradlew detekt             # static analysis
./gradlew apiCheck           # fail on unintended public-API changes
./gradlew apiDump            # regenerate the committed .api dump after an *intended* API change
./gradlew dokkaGenerate      # build API docs
```

Run `./gradlew clean` on its own, never as `clean build` (see [ADR-0011](DECISIONS.md)).

## Before you open a pull request

Every PR must pass the same checks CI runs:

1. **`./gradlew build`** is green.
2. **`./gradlew spotlessCheck detekt`** is clean (run `spotlessApply` to fix formatting/headers).
3. **`./gradlew apiCheck`** passes. If you intentionally changed the public API, run
   **`./gradlew apiDump`** and commit the updated `nativeplatformkit/api/nativeplatformkit.api`.
   Reviewers treat that diff as the contract change.
4. **Screenshots** verify (`verifyRoborazziDebug`). If a visual change is intended, re-record with
   `recordRoborazziDebug` and commit the updated PNGs with a justification in the PR.
5. **KDoc** exists on every public declaration you add or change.
6. New components follow [docs/adding-a-component.md](docs/adding-a-component.md).

## Code style

- Formatting and import order are enforced by **Spotless + ktlint**; `spotlessApply` is the source
  of truth. `@Composable` functions are intentionally PascalCase (configured in `.editorconfig`).
- Every source file carries the Apache 2.0 license header (added by `spotlessApply`).
- Components must read all visual values from `NpkTheme` — never hard-code colors, dimensions, or
  type. No wildcard imports.
- Keep public types `@Stable`/`@Immutable` correct, and never expose internal types through public
  signatures.

## Public API discipline

This is a redistributed library; the public API is a contract.

- `:nativeplatformkit` builds with Kotlin **explicit API mode (strict)** — declare visibility and
  return types explicitly.
- Unstable surfaces must be annotated `@ExperimentalNpkApi` (opt-in required by callers).
- The committed `.api` dump (binary-compatibility-validator) gates all public changes.

## Semantic Versioning policy

NativePlatformKit follows [Semantic Versioning 2.0.0](https://semver.org). Given
`MAJOR.MINOR.PATCH`:

- **MAJOR** — incompatible changes to the **stable** public API: removing/renaming public
  declarations, changing signatures or behavior in a breaking way, raising `minSdk`, or removing a
  previously-stable capability. Accompanied by migration notes in the [CHANGELOG](CHANGELOG.md).
- **MINOR** — backward-compatible additions: new components, new parameters **with defaults**, new
  tokens, graduating an `@ExperimentalNpkApi` API to stable (which removes the annotation).
- **PATCH** — backward-compatible bug fixes and internal/doc changes with no public-API impact.

Additional rules:

- **`@ExperimentalNpkApi` is exempt.** Experimental declarations may change or be removed in any
  release without a deprecation cycle.
- **Deprecation cycle.** Stable APIs are removed only after at least one MINOR release marking them
  `@Deprecated` (with a `ReplaceWith` where possible). Earliest removal is the next MAJOR.
- **`0.x`** — while the version is `0.x`, MINOR may contain breaking changes as the API stabilizes;
  the rules above apply fully from `1.0.0`.
- The `.api` dump is the mechanical guard: an unexpected diff there means the SemVer impact must be
  reconsidered before merging.

## Commit messages

Use clear, imperative messages; [Conventional Commits](https://www.conventionalcommits.org)
(`feat:`, `fix:`, `docs:`, `build:`, `test:`, `refactor:`) are encouraged and help generate the
changelog.

## Maintainers: repository settings

Branch protection on `main` (Settings → Branches → Branch protection rules) should require:

- **Require a pull request before merging** — at least 1 approval; dismiss stale approvals on new
  commits; require review from **Code Owners** (see [.github/CODEOWNERS](.github/CODEOWNERS)).
- **Require status checks to pass** (and require branches to be up to date) for:
  - `CI / Unit & Compose UI tests`
  - `CI / Screenshot verification (Roborazzi)`
  - `CI / Spotless & detekt`
  - `CI / Assemble release AAR`
  - `CI / Dokka API docs`
  - `API check / Binary compatibility (apiCheck)`
  - `DCO / Check sign-off`
- **Require conversation resolution before merging.**
- **Require linear history** (squash or rebase merges).
- **Do not allow bypassing** the above (apply to administrators).

Releases are cut by pushing a `v*` tag (see [docs/publishing.md](docs/publishing.md)); the
`Publish` workflow is additionally gated behind the `maven-central` GitHub Environment, where you
can add required reviewers for a manual approval gate.

## Developer Certificate of Origin (DCO)

This project uses the [DCO](https://developercertificate.org/) instead of a CLA. By signing off,
you certify that you wrote the change or otherwise have the right to submit it under the project's
license.

Sign off every commit:

```bash
git commit -s -m "feat: add NpkChip component"
```

This appends a `Signed-off-by: Your Name <you@example.com>` trailer (which must match your commit
author). A DCO check on each PR enforces this. To sign off a branch retroactively:

```bash
git rebase --signoff origin/main
```

## License

By contributing, you agree that your contributions are licensed under the
[Apache License 2.0](LICENSE).
