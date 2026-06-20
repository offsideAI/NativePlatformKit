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
    alias(libs.plugins.npk.android.application)
}

android {
    namespace = "das.arunabh.nativeplatformkit.catalog"

    defaultConfig {
        applicationId = "das.arunabh.nativeplatformkit.catalog"
        versionCode = 1
        versionName = "0.1.0"
    }

    buildTypes {
        release {
            // The catalog is a demo app and is never published; keep it un-minified for clarity.
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":nativeplatformkit"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
}
