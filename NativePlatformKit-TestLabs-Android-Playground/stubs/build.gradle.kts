plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "ai.offside.mobile.android.stubs"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
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
    // Exposed via `api` so :ui and :app see these types transitively, exactly as
    // the original monorepo modules (component.application/storage/security, lib.*) exposed them.
    api(libs.androidx.startup)
    api(libs.androidx.appcompat)
    api(libs.androidx.annotation)
    api(libs.androidx.webkit)
    api(libs.timber)
}
