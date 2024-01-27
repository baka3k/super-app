import groovy.lang.Closure

plugins {
    alias(libs.plugins.androidDynamicFeature)
    alias(libs.plugins.jetbrainsKotlinAndroid)
//    id("dagger.hilt.android.plugin")
//    id("com.google.devtools.ksp")
//    id("com.facebook.react")
}
 apply(plugin = "com.facebook.react")
//project.extensions.rea
//project.ext.react = [
//    entryFile: "index.js",
//root: "$rootDir/INGO-RN-AltAccoJS/",
//bundleInDebug: false,
//cliPath: "$rootDir/INGO-RN-AltAccoJS/node_modules/react-native/cli.js",
//hermesCommand: "$rootDir/INGO-RN-AltAccoJS/node_modules/hermes-engine/%OS-BIN%/hermesc",
//enableHermes: true // clean and rebuild if changing
//]
//project.ext.react = [
//    entryFile: "index.js",
//    root: "$rootDir/baka3k-react-native",
//    bundleInDebug: false,
//    cliPath: "$rootDir/baka3k-react-native/node_modules/react-native/cli.js",
//    hermesCommand: "$rootDir/baka3k-react-native/node_modules/hermes-engine/%OS-BIN%/hermesc",
//    enableHermes: true // clean and rebuild if changing
//]
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
//    buildFeatures {
//        compose = true
//    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}
kotlin {
    // For example:
    jvmToolchain(17)
}
dependencies {
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.kotlinx.datetime)
    implementation(project(":app"))
    implementation ("com.facebook.react:react-android:0.73.2") // From node_modules
    implementation ("com.facebook.react:hermes-android:0.73.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}
apply(from = file("$rootDir/baka3k-react-native/node_modules/@react-native-community/cli-platform-android/native_modules.gradle"))
val applyNativeModules: Closure<Any> = extra.get("applyNativeModulesAppBuildGradle") as Closure<Any>
applyNativeModules(project)
//apply from: file("$rootDir/baka3k/node_modules/@react-native-community/cli-platform-android/native_modules.gradle")
//applyNativeModulesAppBuildGradle(project)