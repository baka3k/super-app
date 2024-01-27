/*
 * Copyright 2022 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import baka3k.hi.configureFlavors
import baka3k.hi.configureGradleManagedDevices
import baka3k.hi.configureKotlinAndroid
import baka3k.hi.configurePrintApksTask
import baka3k.hi.disableUnnecessaryAndroidTests
import baka3k.hi.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
//                apply("com.android.library")
//                apply("org.jetbrains.kotlin.android")
//                apply("hi.android.lint")

                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
//                alias(libs.plugins.android.library)
//                alias(libs.plugins.jetbrainsKotlinAndroid)
//                alias(libs.plugins.hilt)
//                alias(libs.plugins.ksp)
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 34
//                configureFlavors(this)
//                configureGradleManagedDevices(this)
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                configurePrintApksTask(this)
                disableUnnecessaryAndroidTests(target)
            }
            dependencies {
                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
                add("implementation", libs.findLibrary("hilt.android").get())
                add("ksp", libs.findLibrary("hilt.compiler").get())
            }
        }
    }
}