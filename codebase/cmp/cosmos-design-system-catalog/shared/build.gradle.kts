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

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

// region Lint Config
val lintConfig: com.android.build.api.dsl.Lint.() -> Unit = {
    abortOnError = false
    checkAllWarnings = true
    htmlReport = true
    warningsAsErrors = true
    baseline = file(
        path = "lint-baseline.xml",
    )
    lintConfig = rootProject.file("config/lint/lint.xml")

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
// endregion

plugins {
    alias(libs.plugins.plugin.compose)
    alias(libs.plugins.plugin.kotlin.compose)
    alias(libs.plugins.plugin.kotlin.multiplatform)
    alias(libs.plugins.plugin.kotlin.multiplatform.library)
}

dependencies {
    androidRuntimeClasspath(libs.compose.ui.tooling)

    detektPlugins(libs.detekt.compose.rules)
}

kotlin {
    // region Platforms
    android {
        namespace =
            "com.makeappssimple.abhimanyu.cosmos.design.system.catalog.shared"
        compileSdk = libs.versions.android.compile.sdk.get().toInt()
        minSdk = libs.versions.android.min.sdk.get().toInt()

        androidResources {
            enable = true
        }

        compilerOptions {
            jvmTarget = JvmTarget.fromTarget(libs.versions.java.get())
        }

        lint(
            action = lintConfig,
        )

        withHostTest {
            isIncludeAndroidResources = true
        }
    }

    jvm()

    js {
        browser()
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    // endregion

    explicitApi()

    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.compose.ui.tooling.preview)
            }
        }

        commonMain {
            dependencies {
                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation(libs.androidx.lifecycle.viewmodel.compose)
                implementation(libs.compose.components.resources)
                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)
                implementation(libs.compose.runtime)
                implementation(libs.compose.ui)
                implementation(libs.compose.ui.backhandler)
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
                implementation(libs.koin.compose.viewmodel.navigation)
                implementation(libs.koin.core)

                implementation(project(":core:coroutines"))
                implementation(project(":core:log-kit"))
                implementation(project(":cosmos-design-system"))

                implementation(project.dependencies.platform(libs.koin.bom))
            }
        }

        commonTest {
            dependencies {
                implementation(libs.test.kotlin)
            }
        }

        jsMain {
            dependencies {
                implementation(libs.kotlin.wrappers.browser)
            }
        }
    }
}

// region JVM Lint
// Apply lint to the JVM/iOS verification targets (Fixes the :updateLintBaselineJvm crash)
plugins.withId("com.android.lint") {
    extensions.configure<com.android.build.api.dsl.Lint> {
        lintConfig()
    }
}
// endregion
