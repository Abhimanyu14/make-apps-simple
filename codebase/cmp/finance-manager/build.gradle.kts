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
    alias(libs.plugins.plugin.about.libraries)
    alias(libs.plugins.plugin.kotlin.android)
    alias(libs.plugins.plugin.kotlin.compose)
    alias(libs.plugins.plugin.kotlin.serialization)
    alias(libs.plugins.plugin.ksp)
    alias(libs.plugins.plugin.room)
}

android {
    namespace = "com.makeappssimple.abhimanyu.library.finance.manager.android"
    resourcePrefix = "finance_manager"
    compileSdk = libs.versions.android.compile.sdk.get().toInt()

    buildFeatures {
        buildConfig = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

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
        minSdk = libs.versions.android.min.sdk.get().toInt()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core:app-version"))
    implementation(project(":core:build-config"))
    implementation(project(":core:clipboard"))
    implementation(project(":core:coroutines"))
    implementation(project(":core:date-time"))
    implementation(project(":core:json"))
    implementation(project(":core:kotlin"))
    implementation(project(":core:log-kit"))
    implementation(project(":core:uri"))
    implementation(project(":cosmos-design-system"))

    androidTestImplementation(libs.test.compose.ui.junit4)
    androidTestImplementation(libs.test.room)
    androidTestImplementation(libs.bundles.test)

    androidTestImplementation(platform(libs.koin.bom))

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.tooling.preview)

    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.work.runtime)
    implementation(libs.bundles.coil)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.room)
    implementation(libs.compose.emoji.picker)
    implementation(libs.about.libraries.compose)
    implementation(libs.about.libraries.compose.core)
    implementation(libs.firebase.config)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.navigation.compose)

    implementation(platform(libs.firebase.bom))
    implementation(platform(libs.koin.bom))

    ksp(libs.androidx.room.compiler)
    ksp(libs.koin.ksp.compiler)

    testImplementation(libs.bundles.test)
}

configurations.configureEach {
    exclude(group = "com.intellij", module = "annotations")
}

room {
    schemaDirectory(
        path = "$projectDir/schemas",
    )
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}
