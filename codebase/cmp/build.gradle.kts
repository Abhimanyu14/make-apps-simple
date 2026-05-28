import io.gitlab.arturbosch.detekt.extensions.DetektExtension

plugins {
    alias(libs.plugins.plugin.android.application) apply false
    alias(libs.plugins.plugin.kotlin.multiplatform.library) apply false
    alias(libs.plugins.plugin.compose) apply false
    alias(libs.plugins.plugin.kotlin.compose) apply false
    alias(libs.plugins.plugin.kotlin.jvm) apply false
    alias(libs.plugins.plugin.kotlin.multiplatform) apply false
    alias(libs.plugins.plugin.spotless) apply true
    alias(libs.plugins.plugin.detekt) apply false
}

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
