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

import io.gitlab.arturbosch.detekt.extensions.DetektExtension

plugins {
    alias(libs.plugins.plugin.android.application) apply false
    alias(libs.plugins.plugin.android.lint) apply false
    alias(libs.plugins.plugin.compose) apply false
    alias(libs.plugins.plugin.detekt) apply false
    alias(libs.plugins.plugin.kotlin.compose) apply false
    alias(libs.plugins.plugin.kotlin.jvm) apply false
    alias(libs.plugins.plugin.kotlin.multiplatform) apply false
    alias(libs.plugins.plugin.kotlin.multiplatform.library) apply false
    alias(libs.plugins.plugin.spotless) apply true
}

// region Spotless
spotless {
    kotlin {
        target("**/src/**/*.kt")
        targetExclude("**/build/**/*.kt")

        ktlint(libs.versions.ktlint.get()).editorConfigOverride(
            mapOf(
                "ktlint_standard_no-wildcard-imports" to "disabled",
                "ij_kotlin_allow_trailing_comma" to "true",
                "ktlint_standard_function-naming" to "disabled",
                "ktlint_standard_filename" to "disabled",
            ),
        )
        trimTrailingWhitespace()
        endWithNewline()
    }

    kotlinGradle {
        target(
            "*.gradle.kts",
            "**/src/**/*.gradle.kts",
        )
        ktlint(libs.versions.ktlint.get())
    }
}
// endregion

// region Detekt
subprojects {
    apply(
        plugin = "io.gitlab.arturbosch.detekt",
    )

    configure<DetektExtension> {
        config.setFrom(files("${rootProject.projectDir}/config/detekt/detekt.yml"))
        buildUponDefaultConfig = true
        allRules = true
    }
}
// endregion
