<!--
  Copyright 2026 The NativePlatformKit Authors. Licensed under the Apache License, Version 2.0.
  Thanks for contributing! Please complete the checklist below.
-->
## Summary

<!-- What does this PR do and why? Link any related issue: "Closes #123". -->

## Type of change

- [ ] Bug fix (non-breaking)
- [ ] New feature / component (non-breaking)
- [ ] Breaking change (public API)
- [ ] Docs / build / chore

## Public API impact (SemVer)

<!-- See CONTRIBUTING.md#semantic-versioning-policy -->

- [ ] No public API change
- [ ] Additive (MINOR) — new declarations / params with defaults
- [ ] Breaking (MAJOR) — migration notes added to CHANGELOG
- [ ] `./gradlew apiDump` run and the updated `.api` committed (if API changed)

## Checklist

- [ ] `./gradlew build` passes (assemble + tests + lint + spotless + detekt + apiCheck)
- [ ] `./gradlew :nativeplatformkit:verifyRoborazziDebug` passes (screenshots committed if changed)
- [ ] KDoc added/updated on all public declarations
- [ ] New component follows [docs/adding-a-component.md](../blob/main/docs/adding-a-component.md)
      (composable, previews, unit/UI test, screenshot test, catalog entry, API dump)
- [ ] `CHANGELOG.md` updated under `Unreleased`
- [ ] All commits are signed off (`git commit -s`) per the DCO

## Screenshots / notes

<!-- For visual changes, attach before/after. For API changes, paste the .api diff highlights. -->
