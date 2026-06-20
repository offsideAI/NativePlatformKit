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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import das.arunabh.nativeplatformkit.theme.NpkTheme
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * Roborazzi screenshot tests for the reference [NpkButton]. JVM-based (Robolectric, no emulator).
 *
 * Record reference images:  `./gradlew :nativeplatformkit:recordRoborazziDebug`
 * Verify against them:       `./gradlew :nativeplatformkit:verifyRoborazziDebug`
 *
 * Reference images are committed under `src/test/screenshots/`. Uses Roborazzi's rule-free Compose
 * capture API, which composes the content, measures it to its intrinsic size, and renders the full
 * tree to a tightly-cropped bitmap.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel5)
class NpkButtonScreenshotTest {
    @Test
    fun filledButton_light() =
        capture("NpkButton_filled_light", darkTheme = false) {
            NpkButton(onClick = {}) { Text("Filled button") }
        }

    @Test
    fun filledButton_dark() =
        capture("NpkButton_filled_dark", darkTheme = true) {
            NpkButton(onClick = {}) { Text("Filled button") }
        }

    @Test
    fun allStyles_light() =
        capture("NpkButton_styles_light", darkTheme = false) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NpkButton(onClick = {}, style = NpkButtonStyle.Filled) { Text("Filled") }
                NpkButton(onClick = {}, style = NpkButtonStyle.Tonal) { Text("Tonal") }
                NpkButton(onClick = {}, style = NpkButtonStyle.Outlined) { Text("Outlined") }
            }
        }

    private fun capture(
        fileName: String,
        darkTheme: Boolean,
        content: @Composable () -> Unit,
    ) {
        captureRoboImage(filePath = "src/test/screenshots/$fileName.png") {
            NpkTheme(darkTheme = darkTheme) {
                Box(
                    modifier =
                        Modifier
                            .background(NpkTheme.colors.background)
                            .padding(24.dp),
                ) {
                    content()
                }
            }
        }
    }
}
