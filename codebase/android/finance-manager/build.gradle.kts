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

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.plugin.android.library)
    alias(libs.plugins.plugin.about.libraries)
    alias(libs.plugins.plugin.kotlin.android)
    alias(libs.plugins.plugin.kotlin.compose)
    alias(libs.plugins.plugin.kotlin.serialization)
    alias(libs.plugins.plugin.kotlinx.kover)
    alias(libs.plugins.plugin.ksp)
    alias(libs.plugins.plugin.room)
    alias(libs.plugins.plugin.screenshot)
}

android {
    namespace = "com.makeappssimple.abhimanyu.library.finance.manager.android"
    compileSdk = libs.versions.compile.sdk.get().toInt()
    ndkVersion = libs.versions.ndk.get()
    resourcePrefix = "finance_manager"

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

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Generate native debug symbols to allow Google Play to symbolicate our native crashes
        ndk.debugSymbolLevel = "FULL"
    }

    kotlinOptions {
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
    implementation(project(":common"))
    implementation(project(":cosmos-design-system"))

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.bundles.test)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)

    implementation(libs.about.libraries.compose)
    // implementation(libs.about.libraries.core)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.work.runtime)
    implementation(libs.bundles.camera)
    implementation(libs.bundles.coil)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.room)
    implementation(libs.compose.emoji.picker)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.config)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.perf)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.material.icons.core)
    implementation(libs.material.icons.extended)
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

    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
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
                classes(
                    // Room generated files
                    "com.makeappssimple.abhimanyu.finance.manager.android.common.*Dao_Impl",
                )
                packages(
                    // Android
                    "com.makeappssimple.abhimanyu.finance.manager.android.platform.*",

                    // DI
                    "org.koin.ksp.generated",
                    "com.makeappssimple.abhimanyu.finance.manager.android.common.di",

                    // Fake
                    "com.makeappssimple.abhimanyu.finance.manager.android.common.*.fake",

                    // Room
                    "com.makeappssimple.abhimanyu.finance.manager.android.common.core.database.dao",
                    "com.makeappssimple.abhimanyu.finance.manager.android.common.core.database.local.database",

                    // UI
                    "com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system*",
                    "com.makeappssimple.abhimanyu.finance.manager.android.common.core.chart*",
                    "com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component*",
                    "com.makeappssimple.abhimanyu.finance.manager.android.common.feature.*.screen",
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
