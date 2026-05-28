import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

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

        lint {
            checkAllWarnings = true
            warningsAsErrors = true
            baseline = file("lint-baseline.xml")
        }

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
                implementation(libs.compose.ui.tooling.preview)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        jsMain {
            dependencies {
                implementation(libs.kotlin.wrappers.browser)
            }
        }
    }
}
