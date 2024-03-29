plugins {
    alias(libs.plugins.hi.android.library)
    alias(libs.plugins.hi.android.library.jacoco)
    alias(libs.plugins.hi.android.hilt)
    alias(libs.plugins.hi.android.room)
}

android {
    namespace = "com.baka3k.core.database"
}

dependencies {
    implementation(project(":core_model"))
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
}