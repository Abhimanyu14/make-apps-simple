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
    alias(libs.plugins.plugin.android.library)
    alias(libs.plugins.plugin.kotlin.android)
    alias(libs.plugins.plugin.kotlin.compose)
    alias(libs.plugins.plugin.kotlin.serialization)
    alias(libs.plugins.plugin.kotlinx.kover)
    alias(libs.plugins.plugin.ksp)
    alias(libs.plugins.plugin.screenshot)
}

kotlin {
    explicitApi()
}

android {
    namespace = "com.makeappssimple.abhimanyu.common"
    compileSdk = libs.versions.compile.sdk.get().toInt()
    ndkVersion = libs.versions.ndk.get()

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Room schema
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas".toString())
            }
        }

        // Generate native debug symbols to allow Google Play to symbolicate our native crashes
        ndk.debugSymbolLevel = "FULL"
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

    // Screenshot testing
    experimentalProperties["android.experimental.enableScreenshotTest"] = true
}

dependencies {
    implementation(project(":cosmos-design-system"))

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.bundles.test)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)

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
                    "com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.*",
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

    // Room
    arg("room.schemaLocation", "$projectDir/schemas")
}
