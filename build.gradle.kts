buildscript {
    repositories {
        maven {
            url =
                uri(path = "$rootDir/baka3k-react-native/node_modules/node_modules/react-native/android")
        }
        maven {
            url =
                uri(path = "$rootDir/baka3k-react-native/node_modules/node_modules/jsc-android/dist")
        }
        google()
        mavenCentral()

        // Android Build Server
        maven { url = uri("../hi-prebuilts/m2repository") }
    }
    dependencies {
        classpath(libs.google.oss.licenses.plugin) {
            exclude(group = "com.google.protobuf")
        }
        classpath("com.facebook.react:react-native-gradle-plugin")
    }
}

// Lists all plugins used throughout the project without applying them.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.baselineprofile) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.dependencyGuard) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.roborazzi) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.androidDynamicFeature) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}
apply(plugin = "com.facebook.react.rootproject")