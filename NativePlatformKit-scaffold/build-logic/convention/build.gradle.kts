import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "das.arunabh.nativeplatformkit.buildlogic"

// Convention plugins must target the same JVM the Gradle daemon runs on (JDK 17).
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
        // The kotlin-dsl plugin embeds an older Kotlin than the plugin artifacts we compile
        // against (KGP/Compose 2.4.x, vanniktech, Dokka 2.x are built with newer Kotlin
        // metadata). Allow reading their newer metadata.
        freeCompilerArgs.add("-Xskip-metadata-version-check")
    }
}

dependencies {
    // compileOnly: these Gradle plugins are on the *consuming* build's classpath at runtime.
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.compiler.gradlePlugin)
    compileOnly(libs.vanniktech.publish.gradlePlugin)
    compileOnly(libs.dokka.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("kotlinOptions") {
            id = "npk.kotlin.options"
            implementationClass = "KotlinOptionsConventionPlugin"
        }
        register("androidLibrary") {
            id = "npk.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "npk.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "npk.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("publish") {
            id = "npk.publish"
            implementationClass = "PublishConventionPlugin"
        }
    }
}
