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

@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.plugin.android.library)
    alias(libs.plugins.plugin.kotlin.android)
    alias(libs.plugins.plugin.kotlin.compose)
    alias(libs.plugins.plugin.kotlin.serialization)
    alias(libs.plugins.plugin.kotlinx.kover)
    alias(libs.plugins.plugin.ksp)
    alias(libs.plugins.plugin.room)
    alias(libs.plugins.plugin.screenshot)
}

android {
    namespace = "com.makeappssimple.abhimanyu.common"
    compileSdk = libs.versions.compile.sdk.get().toInt()
    ndkVersion = libs.versions.ndk.get()

    // Screenshot testing
    experimentalProperties["android.experimental.enableScreenshotTest"] = true

    buildFeatures {
        buildConfig = true
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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

        // Generate native debug symbols to allow Google Play to symbolicate our native crashes
        ndk.debugSymbolLevel = "FULL"
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvm.get()

        // Room schema for testing
        sourceSets {
            // Adds exported schema location as test app assets.
            getByName("androidTest").assets.srcDir("$projectDir/schemas")
        }
    }

    lint {
        checkAllWarnings = true
        warningsAsErrors = true
        baseline = file("lint-baseline.xml")
        disable += "AndroidGradlePluginVersion"
    }
}

dependencies {
    implementation(project(":cosmos-design-system"))

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.bundles.test)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)

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
    // implementation(libs.material.icons.core)
    // implementation(libs.material.icons.extended)
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

kotlin {
    explicitApi()
}

kover {
    currentProject {
        instrumentation {
            disabledForTestTasks.add("testReleaseUnitTest")
        }
    }
    reports {
        filters {
            excludes {
                // exclusion rules - classes to exclude from report
                // classes(
                //      "com.example.Class2",
                // )

                packages(
                    // DI
                    "org.koin.ksp.generated",
                    "com.makeappssimple.abhimanyu.barcodes.android.di.*",

                    // UI
                    "com.makeappssimple.abhimanyu.barcodes.android.feature.*.*.screen",
                    "com.makeappssimple.abhimanyu.barcodes.android.core.design_system.*",
                )
            }
            includes {
                // inclusion rules - classes only those that will be present in reports
                // classes("com.example.Class1", "com.example.Class3")
            }
        }
    }
}

ksp {
    // Koin
    arg("KOIN_CONFIG_CHECK", "true")
    arg("KOIN_DEFAULT_MODULE", "false")
}

room {
    schemaDirectory(
        path = "$projectDir/schemas",
    )
}
