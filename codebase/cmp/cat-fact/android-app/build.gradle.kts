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

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.plugin.android.application)
    alias(libs.plugins.plugin.compose)
    alias(libs.plugins.plugin.kotlin.compose)
}

android {
    namespace = "com.makeappssimple.abhimanyu.cat.fact"
    compileSdk = libs.versions.android.compile.sdk.get().toInt()

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        val javaVersion = JavaVersion.toVersion(libs.versions.java.get())
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    defaultConfig {
        applicationId = "com.makeappssimple.abhimanyu.cat.fact"
        minSdk = libs.versions.android.min.sdk.get().toInt()
        targetSdk = libs.versions.android.target.sdk.get().toInt()
        versionCode = libs.versions.app.cat.fact.version.code.get().toInt()
        versionName = libs.versions.app.cat.fact.version.name.get()
    }

    lint {
        abortOnError = false
        checkAllWarnings = true
        htmlReport = true
        warningsAsErrors = true
        baseline = file("lint-baseline.xml")
        lintConfig = file("../config/lint/lint.xml")

        // Force-ignore rules that evaluate before lint.xml loads
        // Prefer adding to lint.xml first
        disable.addAll(
            elements = listOf(
                "AndroidGradlePluginVersion",
                "NewerVersionAvailable",
                "Registered",
            ),
        )
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)

    detektPlugins(libs.detekt.compose.rules)

    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.ui.tooling.preview)
    implementation(projects.catFact.shared)
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.fromTarget(libs.versions.java.get())
    }

    explicitApi()
}
