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

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.plugin.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.plugin.kotlin.compose)
    alias(libs.plugins.plugin.makeappssimple.android.kover)
    alias(libs.plugins.plugin.makeappssimple.android.ksp)
}

android {
    namespace = "com.makeappssimple.abhimanyu.core.date.time"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    lint {
        checkAllWarnings = true
        warningsAsErrors = true
        baseline = file("lint-baseline.xml")
        disable += "AndroidGradlePluginVersion"
    }
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    js(IR) {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.datetime)
                implementation(libs.koin.core)
                implementation(libs.koin.annotations)
                implementation(libs.compose.runtime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.test.kotlin)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.core.ktx)
            }
        }
    }

    explicitApi()
}

dependencies {
    add("kspAndroid", libs.koin.ksp.compiler)
    add("kspJvm", libs.koin.ksp.compiler)
    add("kspIosX64", libs.koin.ksp.compiler)
    add("kspIosArm64", libs.koin.ksp.compiler)
    add("kspIosSimulatorArm64", libs.koin.ksp.compiler)
    add("kspJs", libs.koin.ksp.compiler)
}

kover {
    reports {
        filters {
            excludes {
                packages(
                    // UI
                    "com.makeappssimple.abhimanyu.barcodes.android.feature.*.*.screen",
                    "com.makeappssimple.abhimanyu.barcodes.android.core.design_system.*",
                )
            }
        }
    }
}
