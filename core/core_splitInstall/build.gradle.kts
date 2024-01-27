plugins {
    alias(libs.plugins.hi.android.library)
    alias(libs.plugins.hi.android.library.compose)
}

android {
    namespace = "hi.baka.splitinstall"
}

dependencies {
    api(platform(libs.androidx.compose.bom))
    api(libs.play.core)
    implementation(libs.androidx.compose.material3)
}