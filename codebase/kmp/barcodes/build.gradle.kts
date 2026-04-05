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
    alias(libs.plugins.plugin.detekt)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.plugin.kotlin.compose)
    alias(libs.plugins.plugin.kotlin.serialization)
    alias(libs.plugins.plugin.kotlinx.kover)
    alias(libs.plugins.plugin.ksp)
    alias(libs.plugins.plugin.room)
    alias(libs.plugins.plugin.screenshot)
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    metricsDestination = layout.buildDirectory.dir("compose_compiler")
}

android {
    namespace = "com.makeappssimple.abhimanyu.library.barcodes.android"
    compileSdk = libs.versions.compile.sdk.get().toInt()
    ndkVersion = libs.versions.ndk.get()
    resourcePrefix = "barcodes"

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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()

        testInstrumentationRunner =
            "com.makeappssimple.abhimanyu.barcodes.android.test.InstrumentationTestRunner"

        // Generate native debug symbols to allow Google Play to symbolicate our native crashes
        ndk.debugSymbolLevel = "FULL"
    }

    sourceSets {
        getByName("main").java.setSrcDirs(emptyList<String>())
        // Adds exported schema location as test app assets.
        getByName("androidTest").assets.srcDir("$projectDir/schemas")
    }
    lint {
        checkAllWarnings = true
        warningsAsErrors = true
        baseline = file("lint-baseline.xml")
        disable += "AndroidGradlePluginVersion"
    }

    /**
     * Reference: https://stackoverflow.com/a/76462186/9636037
     */
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {
    androidTestImplementation(libs.test.compose.ui.junit4)
    androidTestImplementation(libs.test.room)
    androidTestImplementation(libs.bundles.koin.test)
    androidTestImplementation(libs.bundles.test)

    debugImplementation(libs.test.compose.ui.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)

    detektPlugins(libs.bundles.detekt)

    ksp(libs.androidx.room.compiler)
    ksp(libs.koin.ksp.compiler)

    screenshotTestImplementation(libs.androidx.compose.ui.tooling)
    screenshotTestImplementation(libs.screenshot.validation.api)

    testImplementation(libs.bundles.test)
}

kotlin {
    explicitApi()

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    sourceSets {
        val commonMain by getting {
            kotlin.setSrcDirs(
                listOf(
                    "src/commonMain/kotlin",
                ),
            )
            dependencies {
                implementation(project(":core:coroutines"))
                implementation(project(":core:kotlin"))

                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)
                implementation(libs.compose.runtime)
                implementation(libs.compose.ui)
                implementation(libs.lifecycle.viewmodel)
                implementation(libs.koin.annotations)
                implementation(libs.koin.core)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        val androidMain by getting {
            kotlin.setSrcDirs(
                listOf(
                    "src/main/java",
                ),
            )
            dependencies {
                implementation(project(":barcode-generator"))
                implementation(project(":common"))
                implementation(project(":core-date-time"))
                implementation(project(":cosmos-design-system"))

                implementation(libs.androidx.compose.ui.tooling.preview)
                implementation(libs.androidx.core.ktx)
                implementation(libs.androidx.paging.compose)
                implementation(libs.androidx.paging.runtime)
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
            }
        }
    }
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
    arg(
        k = "KOIN_CONFIG_CHECK",
        v = "true",
    )
    arg(
        k = "KOIN_DEFAULT_MODULE",
        v = "false",
    )
}

room {
    schemaDirectory(
        path = "$projectDir/schemas",
    )
}
