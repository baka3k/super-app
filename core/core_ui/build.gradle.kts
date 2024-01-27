plugins {
    id("hi.android.library")
    id("hi.android.library.compose")
    id("hi.android.library.jacoco")
}
android {
    buildFeatures {
        viewBinding = true
    }
    namespace = "com.baka3k.architecture.core.ui"
}
dependencies {
//    implementation(project(":core:core-model"))
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.window.manager)
    implementation(libs.androidx.core.ktx)
    api(libs.coil.kt)
    api(libs.coil.kt.compose)
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.appcompat)

    implementation(libs.androidx.recyclerview)

    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    debugApi(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.runtime.livedata)

    api(libs.androidx.fragment)
    api(libs.androidx.fragment.ktx)
    api(libs.androidx.compose.material3.windowSizeClass)
}