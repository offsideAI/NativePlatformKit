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

import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.HasUnitTestBuilder
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import das.arunabh.nativeplatformkit.buildlogic.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

/**
 * `npk.android.library` — baseline configuration for a publishable NPK Android library:
 * SDK levels, Kotlin options, **strict explicit-API mode**, consumer R8 rules, and JVM-side
 * (Robolectric) test options. Compose is layered on top by `npk.android.library.compose`.
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
            apply("npk.kotlin.options")
        }

        extensions.configure<LibraryExtension> {
            configureKotlinAndroid(this)

            defaultConfig {
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                // R8 rules shipped *inside* the AAR for consumers.
                consumerProguardFiles("consumer-rules.pro")
            }

            testOptions {
                unitTests {
                    isIncludeAndroidResources = true
                    isReturnDefaultValues = true
                }
            }

            // A redistributed library should not leak BuildConfig. (Android resource processing
            // stays at its library default of enabled, which Robolectric tests rely on.)
            buildFeatures {
                buildConfig = false
            }
            // NOTE: release-variant publishing (sources + javadoc jars) is configured by the
            // `npk.publish` convention via the vanniktech plugin's AndroidSingleVariantLibrary.
        }

        // Binary stability: every public declaration must be explicitly qualified.
        extensions.configure<KotlinAndroidProjectExtension> {
            explicitApi = ExplicitApiMode.Strict
        }

        // Run JVM (Robolectric/Compose) unit tests once, on the debug variant only. Library
        // bytecode is variant-independent for testing, and the Compose UI test harness
        // (compose-ui-test-manifest) is debug-scoped — so a release unit-test variant would fail
        // to resolve its host ComponentActivity. This keeps `./gradlew test` clean.
        extensions.configure<LibraryAndroidComponentsExtension> {
            beforeVariants(selector().withBuildType("release")) { variantBuilder ->
                (variantBuilder as HasUnitTestBuilder).enableUnitTest = false
            }
        }
    }
}
