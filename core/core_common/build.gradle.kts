plugins {
    alias(libs.plugins.hi.android.library)
}
android {
    namespace = "com.baka3k.core.common"
}
dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    kspTest(libs.hilt.compiler)
    kspAndroidTest(libs.hilt.compiler)
}
