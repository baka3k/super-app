plugins {
//    alias(libs.plugins.hi.android.library)
//     dynamic feature
    alias(libs.plugins.androidDynamicFeature)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}
android {
    namespace = "com.baka3k.architecture.feature.person"
    compileSdk = 34
//    configurations {
//        androidJdkImage {
//            // Delete the configuration.
//            delete()
//        }
//    }
//    compileSdkVersion 31
    defaultConfig {
        minSdk = 31
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}
kotlin {
    // For example:
    jvmToolchain(17)
}
hilt {
    enableExperimentalClasspathAggregation = true
}
dependencies {
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.kotlinx.datetime)
    implementation(project(":app"))
    //    add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    //    add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
//    add("implementation", libs.findLibrary("hilt.android").get())
    implementation("com.google.dagger:hilt-android:2.48.1")
    ksp("com.google.dagger:hilt-android-compiler:2.48.1")

//    add("implementation", libs.findLibrary("coil.kt").get())
//    add("implementation", libs.findLibrary("coil.kt.compose").get())
    implementation("io.coil-kt:coil:2.4.0")
    implementation("io.coil-kt:coil-compose:2.4.0")

//    add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
//    add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
//    add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
//    add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
    implementation(project(":core_model"))
    implementation(project(":core_common"))
    implementation(project(":core_data"))
    implementation(project(":core_ui"))
    implementation(project(":core_navigation"))
//    add("implementation", project(":core_model"))
//    add("implementation", project(":core_common"))
//    add("implementation", project(":core_data"))
//    add("implementation", project(":core_ui"))
//    add("implementation", project(":core_navigation"))
//    "implementation"(libs.findLibrary("hilt.android").get())
//    "ksp"(libs.findLibrary("hilt.compiler").get())
//    "kspAndroidTest"(libs.findLibrary("hilt.compiler").get())
//    "kspTest"(libs.findLibrary("hilt.compiler").get())
}


//    id("hi.android.library")
//    id("hi.android.feature")
//    id("hi.android.library.compose")
//    id("dagger.hilt.android.plugin")
//    alias(libs.plugins.hi.android.feature)
//    alias(libs.plugins.hi.android.hilt)
//    alias(libs.plugins.hi.android.library.compose)

///
//    apply("com.android.library")
//    apply("org.jetbrains.kotlin.android")
//    apply("com.google.dagger.hilt.android")
//    apply("com.google.devtools.ksp")
//    apply("com.google.devtools.ksp")
//    apply("dagger.hilt.android.plugin")

//    id("com.android.library")
//    id("com.google.dagger.hilt.android")
//    id("com.google.devtools.ksp")
//    id("dagger.hilt.android.plugin")
