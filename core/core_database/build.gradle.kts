plugins {
//    id("hi.android.library")
//    id("hi.android.library.jacoco")
////    kotlin("kapt")
//    id("com.google.devtools.ksp")
//    id("dagger.hilt.android.plugin")
//    alias(libs.plugins.ksp)
////    id("hi.spotless")
    alias(libs.plugins.hi.android.library)
    alias(libs.plugins.hi.android.library.jacoco)
    alias(libs.plugins.hi.android.hilt)
    alias(libs.plugins.hi.android.room)
}

android {
//    defaultConfig {
//        // The schemas directory contains a schema file for each version of the Room database.
//        // This is required to enable Room auto migrations.
//        // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
//        ksp {
//            arg("room.schemaLocation", "$projectDir/schemas")
//        }
//
////        testInstrumentationRunner = "com.google.samples.apps.nowinandroid.core.testing.NiaTestRunner"
//    }
    namespace = "com.baka3k.core.database"
}

dependencies {
    implementation(project(":core_model"))
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

//    implementation(libs.room.runtime)
//    implementation(libs.room.ktx)
//    ksp(libs.room.compiler)
//
//    implementation(libs.kotlinx.coroutines.android)
//    implementation(libs.kotlinx.datetime)
//
//    implementation(libs.hilt.android)
//    kapt(libs.hilt.compiler)

//    androidTestImplementation(project(":core-testing"))
}