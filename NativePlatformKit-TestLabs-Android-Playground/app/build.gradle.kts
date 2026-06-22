plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.parcelize")
    // Required so DataBinding discovers the @BindingAdapter functions defined in Kotlin.
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "ai.offside.mobile.android.helper.testlabs"
    compileSdk = 34

    defaultConfig {
        applicationId = "ai.offside.mobile.android.testlabs"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        // Match :ui — testlabs vectors also use theme-attribute colors; skip build-time rasterization.
        vectorDrawables.useSupportLibrary = true
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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
    implementation(project(":ui"))

    implementation(libs.bundles.androidx.components)
    implementation(libs.bundles.androidx.widgets)
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.androidx.core)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.material)
}
