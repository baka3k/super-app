
plugins {
    id("hi.android.library")
    id("hi.android.library.jacoco")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
}
dependencies {
    api(libs.androidx.hilt.navigation.compose)
    api(libs.androidx.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
android {
    namespace = "com.baka3k.core.navigation"
}
