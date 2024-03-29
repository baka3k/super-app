plugins {
    alias(libs.plugins.hi.android.feature)
    alias(libs.plugins.hi.android.hilt)
    alias(libs.plugins.hi.android.library.compose)
}
android {
    namespace = "hi.baka.superapp.movie.list"
}
dependencies {

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.accompanist.pager)
    implementation(libs.androidx.constraintlayout.compose)

    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.hilt.navigation.compose)

}