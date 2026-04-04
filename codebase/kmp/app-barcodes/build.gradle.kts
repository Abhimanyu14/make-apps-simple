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

import java.io.FileInputStream
import java.util.Properties

val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(rootProject.file("key.properties")))

plugins {
    alias(libs.plugins.plugin.makeappssimple.android.application)
    alias(libs.plugins.plugin.kotlin.compose)
}

android {
    namespace = "com.makeappssimple.abhimanyu.barcodes.android"
    resourcePrefix = "cosmos"

    signingConfigs {
        create("release") {
            storeFile = file(keystoreProperties.getProperty("keyStoreFile"))
            storePassword = keystoreProperties.getProperty("keyStorePassword")
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    defaultConfig {
        applicationId = "com.makeappssimple.abhimanyu.barcodes.android"
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = libs.versions.app.barcodes.version.code.get().toInt()
        versionName = libs.versions.app.barcodes.version.name.get()
    }

}

dependencies {
    implementation(project(":barcodes"))
}
