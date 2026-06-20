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
import com.diffplug.gradle.spotless.SpotlessExtension
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension

plugins {
    // Applied to subprojects by the NPK convention plugins; declared here to lock versions.
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.vanniktech.publish) apply false
    alias(libs.plugins.roborazzi) apply false

    // Applied across the whole build.
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.binary.compatibility.validator)
    alias(libs.plugins.dokka)
}

// ---------------------------------------------------------------------------
// Binary compatibility validation — committed .api dumps gate every public change.
// ---------------------------------------------------------------------------
apiValidation {
    // The demo app is not a published API surface.
    ignoredProjects.add("catalog")
}

// ---------------------------------------------------------------------------
// Aggregated API documentation (Dokka v2).  ./gradlew dokkaGenerate
// ---------------------------------------------------------------------------
dependencies {
    dokka(project(":nativeplatformkit"))
}

// ---------------------------------------------------------------------------
// Spotless (formatting + license headers) and detekt (static analysis) for every module.
// ---------------------------------------------------------------------------
allprojects {
    apply(plugin = "com.diffplug.spotless")
    configure<SpotlessExtension> {
        val licenseHeader = rootProject.file("config/spotless/license-header.txt")
        kotlin {
            target("src/**/*.kt")
            targetExclude("**/build/**")
            ktlint()
            licenseHeaderFile(licenseHeader)
            trimTrailingWhitespace()
            endWithNewline()
        }
        kotlinGradle {
            target("*.gradle.kts")
            ktlint()
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    configure<DetektExtension> {
        config.setFrom(rootProject.file("config/detekt/detekt.yml"))
        buildUponDefaultConfig = true
        parallel = true
        ignoreFailures = false
    }
    tasks.withType<Detekt>().configureEach {
        reports {
            html.required.set(true)
            sarif.required.set(true)
            md.required.set(false)
        }
    }
}
