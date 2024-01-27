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

import com.android.build.gradle.LibraryExtension
import baka3k.hi.configureGradleManagedDevices
import baka3k.hi.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("hi.android.library")
                apply("hi.android.hilt")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner =
                        "baka3k.hi.core.testing.HiTestRunner"
                }
                configureGradleManagedDevices(this)
            }

            dependencies {

                add("implementation", libs.findLibrary("coil.kt").get())
                add("implementation", libs.findLibrary("coil.kt.compose").get())
                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())

                add("implementation", project(":core_model"))
                add("implementation", project(":core_common"))
                add("implementation", project(":core_data"))
                add("implementation", project(":core_ui"))
                add("implementation", project(":core_navigation"))
            }
        }
    }
}
