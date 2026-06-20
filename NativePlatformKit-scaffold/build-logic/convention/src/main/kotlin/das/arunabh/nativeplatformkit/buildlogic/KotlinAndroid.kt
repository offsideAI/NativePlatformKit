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
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

/**
 * Shared Android + Kotlin configuration applied by every NPK module: SDK levels (from the version
 * catalog), Java/Kotlin toolchain, and strict lint.
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = libs.intVersion("compileSdk")

        defaultConfig {
            minSdk = libs.intVersion("minSdk")
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        lint {
            // Fail the build on genuine lint *errors*; do not fail on warnings by default so a
            // clean checkout stays green. CI opts into stricter checks via -PwarningsAsErrors=true.
            abortOnError = true
            warningsAsErrors = providers.gradleProperty("warningsAsErrors").orNull.toBoolean()
            sarifReport = true
            htmlReport = true
        }
    }
}

/**
 * Configures the Kotlin compiler options shared by all modules. Applied via the
 * `npk.kotlin.options` convention plugin once the Kotlin Android plugin is present.
 */
internal fun Project.configureKotlin() {
    // Treat warnings as errors only when explicitly requested (e.g. on CI: -PwarningsAsErrors=true).
    val warningsAsErrors = providers.gradleProperty("warningsAsErrors")
        .map(String::toBoolean)
        .orElse(false)
    extensions.configure<KotlinAndroidProjectExtension> {
        compilerOptions {
            // Use .set(): the Gradle assignment-operator plugin is not applied to these sources.
            jvmTarget.set(JvmTarget.JVM_17)
            allWarningsAsErrors.set(warningsAsErrors)
        }
    }
}
