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
    alias(libs.plugins.plugin.makeappssimple.android.kover)
    alias(libs.plugins.plugin.makeappssimple.android.ksp)
    alias(libs.plugins.plugin.kotlin.compose)
    alias(libs.plugins.plugin.kotlin.serialization)
    alias(libs.plugins.plugin.room)
    alias(libs.plugins.plugin.screenshot)
}

android {
    namespace = "com.makeappssimple.abhimanyu.common"

    // Screenshot testing
    experimentalProperties["android.experimental.enableScreenshotTest"] = true

    buildFeatures {
        buildConfig = true
        compose = true
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

}

dependencies {
    implementation(project(":core:coroutines"))
    implementation(project(":core:kotlin"))
    implementation(project(":core-date-time"))
    implementation(project(":cosmos-design-system"))

    androidTestImplementation(libs.test.compose.ui.junit4)
    androidTestImplementation(libs.test.room)
    androidTestImplementation(libs.bundles.test)

    debugImplementation(libs.test.compose.ui.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)

    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.camera)
    implementation(libs.bundles.coil)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.room)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.navigation.compose)
    implementation(libs.play.app.update)
    implementation(libs.play.review)
    implementation(libs.play.services.mlkit.barcode.scanning)
    implementation(libs.play.services.oss.licenses)
    implementation(libs.play.services.vision)
    implementation(libs.zxing.core)

    implementation(platform(libs.androidx.compose.bom))
    implementation(platform(libs.firebase.bom))
    implementation(platform(libs.koin.bom))

    ksp(libs.androidx.room.compiler)
    ksp(libs.koin.ksp.compiler)

    screenshotTestImplementation(libs.androidx.compose.ui.tooling)
    screenshotTestImplementation(libs.screenshot.validation.api)

    testImplementation(libs.bundles.test)
}

kover {
    reports {
        filters {
            excludes {
                packages(
                    "com.makeappssimple.abhimanyu.barcodes.android.di.*",

                    // UI
                    "com.makeappssimple.abhimanyu.barcodes.android.feature.*.*.screen",
                    "com.makeappssimple.abhimanyu.barcodes.android.core.design_system.*",
                )
            }
        }
    }
}

room {
    schemaDirectory(
        path = "$projectDir/schemas",
    )
}
