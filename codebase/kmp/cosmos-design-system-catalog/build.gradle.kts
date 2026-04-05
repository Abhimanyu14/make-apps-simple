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

@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.plugin.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.plugin.kotlin.compose)
    alias(libs.plugins.plugin.ksp)
}

android {
    namespace = "com.makeappssimple.abhimanyu.library.cosmos.design.system.catalog.android"
    compileSdk = libs.versions.compile.sdk.get().toInt()
    ndkVersion = libs.versions.ndk.get()
    resourcePrefix = "cosmos"

    buildFeatures {
        buildConfig = true
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    lint {
        checkAllWarnings = true
        warningsAsErrors = true
        baseline = file("lint-baseline.xml")
        disable += "AndroidGradlePluginVersion"
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(platform(libs.koin.bom))

    ksp(libs.koin.ksp.compiler)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "CosmosDesignSystemCatalog"
            isStatic = true
        }
    }
    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    js {
        browser()
        binaries.executable()
    }
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":core:coroutines"))
                implementation(project(":cosmos-design-system"))

                implementation(libs.lifecycle.viewmodel)
                implementation(libs.lifecycle.viewmodel.compose)

                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)
                implementation(libs.compose.runtime)
                implementation(libs.compose.ui)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.test.kotlin)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(project(":common"))

                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.compose.foundation)
                implementation(libs.androidx.compose.material3)
                implementation(libs.androidx.compose.runtime)
                implementation(libs.androidx.compose.ui.tooling.preview)
                implementation(libs.bundles.koin)
                implementation(libs.navigation.compose)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutines.swing)
            }
        }
    }

    explicitApi()
}

ksp {
    arg(
        k = "KOIN_CONFIG_CHECK",
        v = "true",
    )
    arg(
        k = "KOIN_DEFAULT_MODULE",
        v = "false",
    )
}
