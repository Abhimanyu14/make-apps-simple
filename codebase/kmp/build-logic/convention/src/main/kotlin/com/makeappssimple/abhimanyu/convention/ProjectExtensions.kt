/*
 * Copyright 2025-2026 Abhimanyu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.makeappssimple.abhimanyu.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = libs.findVersion("compile-sdk").get().toString().toInt()

        defaultConfig {
            minSdk = libs.findVersion("min-sdk").get().toString().toInt()
            testInstrumentationRunner =
                "androidx.test.runner.AndroidJUnitRunner"

            // Generate native debug symbols to allow Google Play to symbolicate our native crashes
            ndk.debugSymbolLevel = "FULL"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        lint {
            checkAllWarnings = true
            warningsAsErrors = true
            baseline = file("lint-baseline.xml")
            disable += "AndroidGradlePluginVersion"
        }
    }

    configure<org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension> {
        explicitApi()
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }
}
