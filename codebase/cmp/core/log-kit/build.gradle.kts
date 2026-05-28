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

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.plugin.kotlin.multiplatform)
    alias(libs.plugins.plugin.kotlin.multiplatform.library)
    alias(libs.plugins.plugin.android.lint)
}

kotlin {
    // region Platforms
    android {
        namespace = "com.makeappssimple.abhimanyu.core.log.kit"
        compileSdk {
            version = release(libs.versions.android.compile.sdk.get().toInt()) {
                minorApiLevel = 1
            }
        }
        minSdk = libs.versions.android.min.sdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.fromTarget(libs.versions.java.get())
        }

        lint {
            checkAllWarnings = true
            warningsAsErrors = true
            baseline = file("lint-baseline.xml")
        }

        withHostTestBuilder {}

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    jvm()

    js {
        browser()
    }

    listOf(
        iosArm64(),
        iosX64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "core:log-kit"
            isStatic = true
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    // endregion

    explicitApi()

    sourceSets {
        androidMain {
            dependencies {}
        }

        commonMain {
            dependencies {
                implementation(libs.koin.annotations)
                implementation(libs.koin.core)
                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlinx.coroutines.core)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.test.kotlin)
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.test.core)
                implementation(libs.androidx.test.ext.junit)
                implementation(libs.androidx.test.runner)
            }
        }

        iosMain {
            dependencies {}
        }
    }
}
