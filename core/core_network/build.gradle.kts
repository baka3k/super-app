
plugins {
    alias(libs.plugins.hi.android.library)
    alias(libs.plugins.hi.android.library.jacoco)
    alias(libs.plugins.hi.android.hilt)
    id("kotlinx-serialization")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}
android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.baka3k.core.network"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}
secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}
dependencies {
    implementation(project(":core_common"))
    implementation(project(":core_model"))

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

}
android {
    namespace = "com.baka3k.core.network"
}
