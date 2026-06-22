plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.parcelize")
    // Required so DataBinding discovers the @BindingAdapter functions defined in Kotlin.
    id("org.jetbrains.kotlin.kapt")
}

// The component library splits its resources across many `res-<component>` directories
// (and `icons/res-*`), exactly as the original monorepo module did. Collect them dynamically.
val componentResDirs: List<String> =
    file("src/main").listFiles { f -> f.isDirectory && f.name.startsWith("res-") }
        ?.map { it.path }?.sorted().orEmpty()
val iconResDirs: List<String> =
    file("src/main/icons").listFiles { f -> f.isDirectory && f.name.startsWith("res-") }
        ?.map { it.path }?.sorted().orEmpty()

android {
    namespace = "ai.offside.mobile.android.component.ui"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
        // The component vectors use theme attributes (e.g. ?attr/offside_primary) as colors, which AGP's
        // build-time vector->PNG rasterizer cannot resolve. minSdk 23 supports vectors natively, so
        // disable PNG generation and let them render at runtime via the support library.
        vectorDrawables.useSupportLibrary = true
    }

    buildFeatures {
        resValues = false
        buildConfig = false
        viewBinding = true
        dataBinding = true
    }

    sourceSets {
        getByName("main") {
            res.setSrcDirs(listOf("src/main/res") + componentResDirs + iconResDirs)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Private monorepo modules (component.application/security/storage, lib.glassbox, lib.timber)
    // are replaced by the local stub module. `api` so :app sees these types transitively.
    api(project(":stubs"))

    api(libs.bundles.androidx.widgets)
    api(libs.bundles.androidx.components)
    api(libs.androidx.core)
    api(libs.androidx.core.ktx)
    api(libs.material)

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.annotation.experimental)
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.androidx.startup)
    implementation(libs.picasso)
    implementation(libs.shimmer)
}
