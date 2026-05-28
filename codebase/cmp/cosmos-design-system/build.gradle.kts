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
    alias(libs.plugins.plugin.android.lint)
    alias(libs.plugins.plugin.compose)
    alias(libs.plugins.plugin.dokka)
    alias(libs.plugins.plugin.kotlin.compose)
    alias(libs.plugins.plugin.kotlin.multiplatform)
    alias(libs.plugins.plugin.kotlin.multiplatform.library)
    alias(libs.plugins.plugin.kotlinx.binary.compatibility.validator)
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    metricsDestination = layout.buildDirectory.dir("compose_compiler")
}

compose.resources {
    publicResClass = true
    packageOfResClass =
        "com.makeappssimple.abhimanyu.cosmos.design.system.resources"
}

kotlin {
    // region Platforms
    // Target declarations - add or remove as needed below. These define
    // which platforms this KMP module supports.
    // See: https://kotlinlang.org/docs/multiplatform-discover-project.html#targets
    android {
        namespace = "com.makeappssimple.abhimanyu.cosmos.design.system"

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

    // For iOS targets, this is also where you should
    // configure native binary output. For more information, see:
    // https://kotlinlang.org/docs/multiplatform-build-native-binaries.html#build-xcframeworks

    // A step-by-step guide on how to include this library in an XCode
    // project can be found here:
    // https://developer.android.com/kotlin/multiplatform/migrate
    listOf(
        iosArm64(),
        iosX64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "cosmos-design-system"
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    // endregion

    explicitApi()

    // Source set declarations.
    // Declaring a target automatically creates a source set with the same name. By default, the
    // Kotlin Gradle Plugin creates additional source sets that depend on each other, since it is
    // common to share sources between related targets.
    // See: https://kotlinlang.org/docs/multiplatform-hierarchy.html
    sourceSets {
        androidMain {
            dependencies {}
        }

        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.compose.components.resources)
                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)
                implementation(libs.compose.runtime)
                implementation(libs.compose.ui)
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.kotlinx.collections.immutable)

                implementation(project(":core:date-time"))
                implementation(project(":core:kotlin"))
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
