plugins {
//    id("hi.android.library")
//    id("hi.android.library.jacoco")
////    kotlin("kapt")
//    id("com.google.devtools.ksp")
//    alias(libs.plugins.hi.android.library)
//    alias(libs.plugins.hi.android.library.jacoco)

//    alias(libs.plugins.android.library)
//    alias(libs.plugins.jetbrainsKotlinAndroid)
//    alias(libs.plugins.hilt)
//    alias(libs.plugins.ksp)
        alias(libs.plugins.hi.android.library)
}
android {
    namespace = "com.baka3k.core.common"
//    compileSdk = 34
//    defaultConfig {
//        minSdk = 21
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles("consumer-rules.pro")
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }

}
//kotlin {
//    // For example:
//    jvmToolchain(17)
//}
dependencies {
//    implementation(libs.kotlinx.coroutines.android)
//    implementation(libs.hilt.android)
//    ksp(libs.hilt.compiler)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    kspTest(libs.hilt.compiler)
    kspAndroidTest(libs.hilt.compiler)
}
