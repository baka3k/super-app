import baka3k.hi.HiBuildType

plugins {
    alias(libs.plugins.hi.android.application)
    alias(libs.plugins.hi.android.application.compose)
    alias(libs.plugins.hi.android.hilt)
    id("jacoco")
//    alias(libs.plugins.hi.android.application.firebase)
    id("com.google.android.gms.oss-licenses-plugin")
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "hi.baka.superapp"

    defaultConfig {
        applicationId = "hi.baka.superapp"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = HiBuildType.DEBUG.applicationIdSuffix
        }
        val release = getByName("release") {
            isMinifyEnabled = true
            applicationIdSuffix = HiBuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )

            // To publish on the Play store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
            signingConfig = signingConfigs.getByName("debug")
            // Ensure Baseline Profile is fresh for release builds.
            baselineProfile.automaticGenerationDuringBuild = true
        }
        create("benchmark") {
            // Enable all the optimizations from release build through initWith(release).
            initWith(release)
            matchingFallbacks.add("release")
            // Debug key signing is available to everyone.
            signingConfig = signingConfigs.getByName("debug")
            // Only use benchmark proguard rules
            proguardFiles("benchmark-rules.pro")
            isMinifyEnabled = true
            applicationIdSuffix = HiBuildType.BENCHMARK.applicationIdSuffix
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    dynamicFeatures += setOf(":feature_person", ":feature_react")
}

dependencies {
    api(platform(libs.androidx.compose.bom))
    // core split apk
    api(libs.play.core)
    // image
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
    // compose view
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.compose.compiler)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.constraintlayout)
    // core function
    implementation(libs.kotlinx.coroutines.android)
    kspTest(libs.hilt.compiler)

    implementation(project(":core_ui"))
    implementation(project(":core_navigation"))
    implementation(project(":core_splitInstall"))
    implementation(project(":feature_movie_list"))
    implementation(project(":feature_movie_detail"))

//    implementation(project(":feature_person"))
}

baselineProfile {
    // Don't build on every iteration of a full assemble.
    // Instead enable generation directly for the release build variant.
    automaticGenerationDuringBuild = false
}

dependencyGuard {
    configuration("prodReleaseRuntimeClasspath")
}