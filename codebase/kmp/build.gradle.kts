import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

plugins {
    alias(libs.plugins.compose.hot.reload) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false

    alias(libs.plugins.plugin.android.application) apply false
    alias(libs.plugins.plugin.android.library) apply false
    alias(libs.plugins.plugin.detekt)
    alias(libs.plugins.plugin.dokka)
    alias(libs.plugins.plugin.firebase.crashlytics) apply false
    alias(libs.plugins.plugin.google.services) apply false
    alias(libs.plugins.plugin.kotlin.android) apply false
    alias(libs.plugins.plugin.kotlin.compose) apply false
    alias(libs.plugins.plugin.kotlinx.kover)
    alias(libs.plugins.plugin.ksp) apply false
    alias(libs.plugins.plugin.maven.publish) apply false
}

// region Detekt
detekt {
    toolVersion = "1.23.8"
    config.setFrom("$projectDir/config/detekt/detekt.yml")
    buildUponDefaultConfig = true
    allRules = false
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(false)
        txt.required.set(true)
        sarif.required.set(false)
        md.required.set(true)
    }
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "1.8"
}

tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}
// endregion

kover {
    currentProject {
        instrumentation {
            disabledForTestTasks.add("testReleaseUnitTest")
        }
    }
}

allprojects {
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "com.google.guava" && requested.name == "guava") {
                useVersion("33.2.0-jre") // Force a stable, recent version
                because("Align Guava to prevent runtime errors.")
            }
        }
    }
}
