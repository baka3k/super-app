import com.android.build.api.dsl.Packaging

import groovy.lang.Closure

plugins {
    alias(libs.plugins.androidDynamicFeature)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}
apply(plugin = "com.facebook.react")
android {
    namespace = "hi.baka.feature.react"

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    buildFeatures {
        compose = true
    }
}
kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":app"))
    implementation (libs.react.android) // From node_modules
    implementation (libs.hermes.android)
    implementation(libs.kotlinx.coroutines.android)
    // for test
    implementation(project(":core_database"))
    implementation(project(":core_model"))
    implementation(project(":core_data"))
    implementation(project(":core_network"))
    implementation(project(":core_common"))
    implementation(project(":core_ui"))
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.kotlinx.serialization.json) // for data Json parser
}
apply(from = file("$rootDir/baka3k-react-native/node_modules/@react-native-community/cli-platform-android/native_modules.gradle"))
val applyNativeModules: Closure<Any> = extra.get("applyNativeModulesAppBuildGradle") as Closure<Any>
applyNativeModules(project)