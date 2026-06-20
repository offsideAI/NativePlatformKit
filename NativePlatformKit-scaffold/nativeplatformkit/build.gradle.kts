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
plugins {
    alias(libs.plugins.npk.android.library.compose)
    alias(libs.plugins.npk.publish)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "das.arunabh.nativeplatformkit"
    // All resources in a redistributed library must be prefixed to avoid consumer collisions.
    resourcePrefix = "npk_"
}

dependencies {
    implementation(libs.androidx.core.ktx)

    // Compose API surface exposed transitively to consumers of the library.
    api(libs.compose.foundation)
    api(libs.compose.ui)
    api(libs.compose.ui.graphics)
    api(libs.compose.ui.text)
    api(libs.compose.material3)
    implementation(libs.compose.ui.tooling.preview)

    // ---- Unit + Robolectric Compose UI + Roborazzi screenshot tests (JVM side) ----
    testImplementation(libs.junit4)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.test.ext.junit)
    testImplementation(libs.compose.ui.test.junit4)
    testImplementation(libs.roborazzi)
    testImplementation(libs.roborazzi.compose)
    testImplementation(libs.roborazzi.junit.rule)
    // Provides the host activity used by createComposeRule() under Robolectric.
    debugImplementation(libs.compose.ui.test.manifest)
}
