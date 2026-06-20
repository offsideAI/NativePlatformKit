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
package das.arunabh.nativeplatformkit.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

/**
 * Enables Jetpack Compose for a module: turns on the build feature, wires the Compose BOM into the
 * main/test/androidTest configurations, and optionally emits compiler stability metrics/reports.
 *
 * Assumes the `org.jetbrains.kotlin.plugin.compose` plugin is already applied.
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        dependencies {
            val bom = libs.findLibrary("compose-bom").get()
            add("implementation", platform(bom))
            add("testImplementation", platform(bom))
            add("androidTestImplementation", platform(bom))
            add("debugImplementation", libs.findLibrary("compose-ui-tooling").get())
        }
    }

    // Compose compiler metrics & stability reports.
    // Enable with: ./gradlew assembleRelease -Pnpk.enableComposeCompilerReports=true
    extensions.configure<ComposeCompilerGradlePluginExtension> {
        val enableReports = providers.gradleProperty("npk.enableComposeCompilerReports")
            .map(String::toBoolean)
            .orElse(false)
        if (enableReports.get()) {
            val dir = layout.buildDirectory.dir("compose-compiler")
            metricsDestination.set(dir)
            reportsDestination.set(dir)
        }
    }
}
