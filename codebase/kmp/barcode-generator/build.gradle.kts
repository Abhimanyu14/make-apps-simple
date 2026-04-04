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

plugins {
    alias(libs.plugins.plugin.makeappssimple.android.library)
    alias(libs.plugins.plugin.makeappssimple.android.ksp)
}

android {
    namespace = "com.makeappssimple.abhimanyu.barcode.generator.android"
    resourcePrefix = "barcode_generator"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    defaultConfig {
        testInstrumentationRunner =
            "com.makeappssimple.abhimanyu.barcodes.android.test.InstrumentationTestRunner"
    }
}

dependencies {
    implementation(project(":common"))

    implementation(libs.bundles.compose)
    implementation(libs.bundles.koin)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.zxing.core)

    implementation(platform(libs.androidx.compose.bom))
    implementation(platform(libs.koin.bom))

    ksp(libs.koin.ksp.compiler)
}
