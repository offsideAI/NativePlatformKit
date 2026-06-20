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
package das.arunabh.nativeplatformkit.component

import androidx.compose.material3.Text
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import das.arunabh.nativeplatformkit.theme.NpkTheme
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.GraphicsMode

/**
 * Compose UI + accessibility tests for the reference [NpkButton], running on the JVM via
 * Robolectric (no device/emulator). Demonstrates behavioural and a11y assertions every component
 * should ship.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class NpkButtonTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun click_invokesCallback() {
        var clicks = 0
        composeRule.setContent {
            NpkTheme {
                NpkButton(onClick = { clicks++ }) { Text("Save") }
            }
        }

        composeRule.onNodeWithText("Save").performClick()

        assertTrue("onClick should fire exactly once", clicks == 1)
    }

    @Test
    fun disabled_doesNotInvokeCallback_andReportsDisabledState() {
        var clicked = false
        composeRule.setContent {
            NpkTheme {
                NpkButton(onClick = { clicked = true }, enabled = false) { Text("Save") }
            }
        }

        composeRule
            .onNodeWithText("Save")
            .assertIsNotEnabled()
            .performClick()

        assertFalse("A disabled button must not invoke onClick", clicked)
    }

    @Test
    fun exposesButtonRoleAndClickAction_forAccessibility() {
        composeRule.setContent {
            NpkTheme {
                NpkButton(onClick = {}) { Text("Save") }
            }
        }

        composeRule
            .onNodeWithText("Save")
            .assertHasClickAction()
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
    }
}
