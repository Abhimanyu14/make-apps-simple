plugins {
    alias(libs.plugins.plugin.android.lint)
    alias(libs.plugins.plugin.kotlin.multiplatform)
    alias(libs.plugins.plugin.kotlin.multiplatform.library)
}

kotlin {
    // Target declarations - add or remove as needed below. These define
    // which platforms this KMP module supports.
    // See: https://kotlinlang.org/docs/multiplatform-discover-project.html#targets
    android {
        namespace = "com.makeappssimple.abhimanyu.cosmos.design.system"

        compileSdk {
            version = release(libs.versions.android.compile.sdk.get().toInt()) {
                minorApiLevel = 1
            }
        }
        minSdk = libs.versions.android.min.sdk.get().toInt()

        lint {
            checkAllWarnings = true
            warningsAsErrors = true
            baseline = file("lint-baseline.xml")
        }

        withHostTestBuilder {}

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    // For iOS targets, this is also where you should
    // configure native binary output. For more information, see:
    // https://kotlinlang.org/docs/multiplatform-build-native-binaries.html#build-xcframeworks

    // A step-by-step guide on how to include this library in an XCode
    // project can be found here:
    // https://developer.android.com/kotlin/multiplatform/migrate
    listOf(
        iosArm64(),
        iosX64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "cosmos-design-system"
        }
    }

    explicitApi()

    // Source set declarations.
    // Declaring a target automatically creates a source set with the same name. By default, the
    // Kotlin Gradle Plugin creates additional source sets that depend on each other, since it is
    // common to share sources between related targets.
    // See: https://kotlinlang.org/docs/multiplatform-hierarchy.html
    sourceSets {
        androidMain {
            dependencies {}
        }

        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.test.core)
                implementation(libs.androidx.test.ext.junit)
                implementation(libs.androidx.test.runner)
            }
        }

        iosMain {
            dependencies {}
        }
    }
}
