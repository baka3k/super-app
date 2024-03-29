plugins {
    alias(libs.plugins.hi.android.feature)
    alias(libs.plugins.hi.android.hilt)
    alias(libs.plugins.hi.android.library.compose)
}
android {
    namespace = "hi.baka.feature.movie.detail"
}

dependencies {
    implementation(libs.androidx.constraintlayout.compose)
}