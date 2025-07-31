/*
 * Copyright 2025-2025 Abhimanyu
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

plugins {
    alias(libs.plugins.plugin.android.application)
    alias(libs.plugins.plugin.kotlin.android)
    alias(libs.plugins.plugin.kotlin.compose)
}

kotlin {
    explicitApi()
}

android {
    namespace =
        "com.makeappssimple.abhimanyu.cosmos.design.system.catalog.android"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        applicationId =
            "com.makeappssimple.abhimanyu.cosmos.design.system.catalog.android"
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode =
            libs.versions.app.cosmos.design.system.catalog.version.code.get()
                .toInt()
        versionName =
            libs.versions.app.cosmos.design.system.catalog.version.name.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    lint {
        checkAllWarnings = true
        warningsAsErrors = true
        baseline = file("lint-baseline.xml")
        disable += "AndroidGradlePluginVersion"
    }
}

dependencies {
    implementation(project(":cosmos-design-system-catalog"))
}
