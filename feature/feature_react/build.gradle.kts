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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileSdk = 34
    defaultConfig {
        minSdk = 31
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}
kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":app"))
    implementation ("com.facebook.react:react-android:0.73.5") // From node_modules
    implementation ("com.facebook.react:hermes-android:0.73.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    // for test
    implementation(project(":core_database"))
    implementation(project(":core_model"))
    implementation(project(":core_data"))
    implementation(project(":core_network"))
    implementation(project(":core_common"))
    //
    implementation(libs.kotlinx.serialization.json)
}
apply(from = file("$rootDir/baka3k-react-native/node_modules/@react-native-community/cli-platform-android/native_modules.gradle"))
val applyNativeModules: Closure<Any> = extra.get("applyNativeModulesAppBuildGradle") as Closure<Any>
applyNativeModules(project)