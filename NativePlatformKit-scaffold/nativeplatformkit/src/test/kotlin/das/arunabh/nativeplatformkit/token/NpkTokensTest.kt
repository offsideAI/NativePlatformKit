/*
 * Copyright 2026 The NativePlatformKit Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package das.arunabh.nativeplatformkit.token

import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Fast, pure-JVM unit tests for the design tokens (no Android runtime required). Demonstrates the
 * unit-test layer of the testing pipeline that every component contributes to.
 */
class NpkTokensTest {
    @Test
    fun spacing_followsEightDpGrid() {
        val spacing = NpkSpacing.default()
        assertEquals(0.dp, spacing.none)
        assertEquals(4.dp, spacing.xs)
        assertEquals(8.dp, spacing.small)
        assertEquals(16.dp, spacing.medium)
        assertEquals(24.dp, spacing.large)
        assertEquals(32.dp, spacing.xl)
    }

    @Test
    fun elevation_isMonotonicallyIncreasing() {
        val e = NpkElevation.default()
        assertTrue(e.level0 < e.level1)
        assertTrue(e.level1 < e.level2)
        assertTrue(e.level2 < e.level3)
        assertTrue(e.level3 < e.level4)
    }

    @Test
    fun spacing_copy_overridesOnlyRequestedValue() {
        val base = NpkSpacing.default()
        val modified = base.copy(medium = 20.dp)
        assertEquals(20.dp, modified.medium)
        // Everything else is untouched.
        assertEquals(base.small, modified.small)
        assertEquals(base.large, modified.large)
    }

    @Test
    fun colors_lightAndDarkDiffer() {
        val light = NpkColors.light()
        val dark = NpkColors.dark()
        assertTrue(light.isDark.not())
        assertTrue(dark.isDark)
        assertNotEquals(light.background, dark.background)
    }

    @Test
    fun colors_projectOntoMaterialScheme() {
        val colors = NpkColors.light()
        val scheme = colors.toMaterialColorScheme()
        assertEquals(colors.primary, scheme.primary)
        assertEquals(colors.onPrimary, scheme.onPrimary)
        assertEquals(colors.error, scheme.error)
    }
}
