<!--
  Copyright 2026 The NativePlatformKit Authors
  Licensed under the Apache License, Version 2.0. See the LICENSE file.
-->
# Adding a component

This is the canonical, mechanical recipe for adding a new component to NativePlatformKit. It is
derived directly from the reference component, **`NpkButton`**. When in doubt, open
[`NpkButton.kt`](../nativeplatformkit/src/main/kotlin/das/arunabh/nativeplatformkit/component/NpkButton.kt)
and its tests and mirror them.

A component is "done" when **all of these exist**: the composable + KDoc + previews, a unit/UI test,
a screenshot test with committed reference images, a catalog entry, and an updated public-API dump.
CI gates on every one of these.

Throughout, replace `Foo`/`NpkFoo` with your component name.

---

## 0. Decide the API first

Components are a contract. Sketch the public API before writing the body and follow Compose
conventions (mirrored by `NpkButton`):

- A **leading required lambda** for the primary action (e.g. `onClick: () -> Unit`), if any.
- **`modifier: Modifier = Modifier`** as the **first optional** parameter.
- Sensible **defaults** for everything else; enum/sealed types for variants (see `NpkButtonStyle`).
- A trailing **`content` slot** (`@Composable RowScope.() -> Unit` etc.) where it makes sense.
- **Read every visual value from `NpkTheme`** (`colors`, `typography`, `shapes`, `spacing`,
  `elevation`). Never hard-code a color or dimension.
- Keep parameter types stable; don't leak internal types. Mark unstable surfaces
  `@ExperimentalNpkApi`.

## 1. Create the composable

Create `nativeplatformkit/src/main/kotlin/das/arunabh/nativeplatformkit/component/NpkFoo.kt`.

- Start with the Apache license header (or just run `./gradlew spotlessApply` to add it).
- Declare it `public` with an explicit return type (the module is **explicit-API strict**).
- Write **KDoc on every public declaration** — summary, an `@sample`-style usage block, and a
  `@param` for each parameter. KDoc is required.

```kotlin
package das.arunabh.nativeplatformkit.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import das.arunabh.nativeplatformkit.theme.NpkTheme

/**
 * One-line summary of NpkFoo.
 *
 * ```
 * NpkFoo(onAction = { /* ... */ }) { Text("Hi") }
 * ```
 *
 * @param onAction called when ...
 * @param modifier the [Modifier] to apply to this component.
 * @param enabled controls the enabled state.
 */
@Composable
public fun NpkFoo(
    onAction: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colors = NpkTheme.colors
    val spacing = NpkTheme.spacing
    // ... build from tokens only ...
}
```

## 2. Add previews (private)

In the **same file**, add `private` `@Preview` composables covering light, dark, and key states.
Private keeps them out of the public API. Wrap each in `NpkTheme`.

```kotlin
@Preview(name = "Light", showBackground = true)
@Composable
private fun NpkFooLightPreview() {
    NpkTheme(darkTheme = false) { NpkFoo(onAction = {}) { Text("Foo") } }
}

@Preview(name = "Dark", showBackground = true)
@Composable
private fun NpkFooDarkPreview() {
    NpkTheme(darkTheme = true) { NpkFoo(onAction = {}) { Text("Foo") } }
}
```

## 3. Write a unit / UI + accessibility test

Create `nativeplatformkit/src/test/kotlin/.../component/NpkFooTest.kt`. Use the **v2** Compose test
rule (`androidx.compose.ui.test.junit4.v2.createComposeRule`) under Robolectric. Mirror
[`NpkButtonTest.kt`](../nativeplatformkit/src/main/kotlin/das/arunabh/nativeplatformkit/component/NpkButton.kt).
Assert behavior **and accessibility** (role, click action, enabled/disabled state):

```kotlin
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class NpkFooTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun exposesRole_forAccessibility() {
        composeRule.setContent { NpkTheme { NpkFoo(onAction = {}) { Text("Foo") } } }
        composeRule.onNodeWithText("Foo")
            .assertHasClickAction()
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
    }
}
```

Pure-logic tests (no Compose) can be plain JUnit — see
[`NpkTokensTest.kt`](../nativeplatformkit/src/test/kotlin/das/arunabh/nativeplatformkit/token/NpkTokensTest.kt).

Run: `./gradlew :nativeplatformkit:testDebugUnitTest`.

## 4. Write a screenshot test and record references

Create `nativeplatformkit/src/test/kotlin/.../component/NpkFooScreenshotTest.kt` using Roborazzi's
rule-free capture API (it renders the full tree reliably under Robolectric). Mirror
`NpkButtonScreenshotTest.kt`:

```kotlin
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel5)
class NpkFooScreenshotTest {
    @Test fun foo_light() {
        captureRoboImage("src/test/screenshots/NpkFoo_light.png") {
            NpkTheme(darkTheme = false) {
                Box(Modifier.background(NpkTheme.colors.background).padding(24.dp)) {
                    NpkFoo(onAction = {}) { Text("Foo") }
                }
            }
        }
    }
}
```

Record the references, then verify:

```bash
./gradlew :nativeplatformkit:recordRoborazziDebug   # writes PNGs to src/test/screenshots/
./gradlew :nativeplatformkit:verifyRoborazziDebug   # must pass
```

**Commit the generated PNGs.** (The emulated test runtime is pinned to SDK 34 with NATIVE graphics
via `robolectric.properties` — don't change that, or screenshots render blank. See ADR-0007.)

## 5. Add a catalog entry

Showcase the component in the demo app. In
[`CatalogApp.kt`](../catalog/src/main/kotlin/das/arunabh/nativeplatformkit/catalog/CatalogApp.kt),
add a `private @Composable NpkFooDemo()` section (light + dark + states) and call it from the
`Column` in `CatalogApp`. Verify it builds and renders:

```bash
./gradlew :catalog:assembleDebug
./gradlew :catalog:installDebug   # optional, on a device/emulator
```

## 6. Update the public-API dump

Explicit-API + binary-compatibility-validator guard the public surface. After adding a public
declaration, regenerate and commit the dump:

```bash
./gradlew apiDump
git add nativeplatformkit/api/nativeplatformkit.api
```

Review the diff — it *is* your API change. Reconsider the [SemVer impact](../CONTRIBUTING.md#semantic-versioning-policy).

## 7. Quality gates, changelog, PR

```bash
./gradlew spotlessApply         # format + license headers
./gradlew build                 # assemble + tests + lint + spotless + detekt + apiCheck
./gradlew :nativeplatformkit:verifyRoborazziDebug
./gradlew detekt
```

- Add an entry to the `Unreleased` section of [CHANGELOG.md](../CHANGELOG.md).
- Sign off your commits (`git commit -s`) per the [DCO](../CONTRIBUTING.md#developer-certificate-of-origin-dco).
- Open a PR; fill in the template.

---

## Checklist (copy into your PR)

- [ ] `NpkFoo.kt`: public composable, explicit API, full KDoc, reads only from `NpkTheme`
- [ ] Private `@Preview`s (light, dark, key states)
- [ ] Unit/UI test incl. an accessibility assertion
- [ ] Screenshot test + committed reference PNGs (`verifyRoborazziDebug` passes)
- [ ] Catalog demo section added
- [ ] `apiDump` regenerated and committed
- [ ] `CHANGELOG.md` updated
- [ ] `./gradlew build spotlessCheck detekt apiCheck` all green
- [ ] Commits signed off (DCO)
