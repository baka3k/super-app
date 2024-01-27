plugins {
//    id("hi.android.library")
//    id("hi.android.library.jacoco")
//    kotlin("kapt")
//    id("com.google.devtools.ksp")
//    id("kotlinx-serialization")
//    id("dagger.hilt.android.plugin")
//    id("hi.spotless")
    alias(libs.plugins.hi.android.library)
    alias(libs.plugins.hi.android.library.jacoco)
    alias(libs.plugins.hi.android.hilt)
    id("kotlinx-serialization")
}
dependencies {
    implementation(project(":core_common"))
    implementation(project(":core_model"))
    implementation(project(":core_database"))
//    implementation(project(":core_datastore"))
    implementation(project(":core_network"))


    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
}
android {
    namespace = "com.baka3k.core.data"
}
