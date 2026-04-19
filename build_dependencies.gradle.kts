// ─────────────────────────────────────────────────────────────────────────────
//  UNSW Digital ID – Android Gradle dependencies
//
//  Copy the contents of the [dependencies] block below into your app-level
//  build.gradle.kts file to enable all frontend features.
//
//  Minimum SDK   : 26 (Android 8.0)
//  Target SDK    : 34
//  Compile SDK   : 34
//  Kotlin        : 1.9.x
//  AGP           : 8.x
// ─────────────────────────────────────────────────────────────────────────────

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace   = "com.unsw.digitalid"
    compileSdk  = 34

    defaultConfig {
        applicationId = "com.unsw.digitalid"
        minSdk        = 26
        targetSdk     = 34
        versionCode   = 1
        versionName   = "1.0.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    // ── Compose BOM – pins all Compose library versions together ──────────
    val composeBom = platform("androidx.compose:compose-bom:2024.05.00")
    implementation(composeBom)

    // ── Core Compose ──────────────────────────────────────────────────────
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // ── Material 3 ────────────────────────────────────────────────────────
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    // ── Navigation Compose ────────────────────────────────────────────────
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // ── Activity + Lifecycle ──────────────────────────────────────────────
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // ── Core KTX ─────────────────────────────────────────────────────────
    implementation("androidx.core:core-ktx:1.13.1")

    // ── Animations ───────────────────────────────────────────────────────
    implementation("androidx.compose.animation:animation")

    // ── Testing ───────────────────────────────────────────────────────────
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}
