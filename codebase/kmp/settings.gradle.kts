rootProject.name = "make-apps-simple"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

includeBuild("build-logic")

include(":androidApp")
include(":composeApp")
include(":app-barcodes")
include(":app-cosmos-design-system-catalog")
include(":app-finance-manager")
include(":app-make-apps-simple")
include(":barcode-generator")
include(":barcodes")
include(":common")
include(":core:coroutines")
include(":core:kotlin")
include(":core-date-time")
include(":cosmos-design-system")
include(":cosmos-design-system-catalog")
include(":finance-manager")
