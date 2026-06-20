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

import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * `npk.publish` — publishes the release AAR to **Maven Central via the Central Portal** using the
 * vanniktech plugin (the post-OSSRH process). Source + Dokka Javadoc jars are produced and GPG
 * signing is enabled. POM metadata is read from `gradle.properties` (GROUP, VERSION_NAME, POM_*).
 *
 * Credentials and signing keys are read from environment variables / Gradle properties only and
 * are never committed — see `docs/publishing.md`.
 */
class PublishConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.vanniktech.maven.publish")
            // Dokka supplies the contents of the Javadoc jar required by Maven Central.
            apply("org.jetbrains.dokka")
        }

        extensions.configure<MavenPublishBaseExtension> {
            // Upload to the Central Portal; never auto-release (a human promotes the deployment).
            publishToMavenCentral()
            // Signing is required for releases; vanniktech skips it for SNAPSHOTs and mavenLocal,
            // so `publishToMavenLocal` works with no key configured.
            signAllPublications()

            // GROUP / VERSION_NAME / POM_* are read automatically from gradle.properties.
            // artifactId defaults to the module name ("nativeplatformkit").
            configure(
                AndroidSingleVariantLibrary(
                    variant = "release",
                    sourcesJar = true,
                    publishJavadocJar = true,
                ),
            )
        }
    }
}
