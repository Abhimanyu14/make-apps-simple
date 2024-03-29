rootProject.name = "KotlinProject"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven(
            url = "https://maven.pkg.jetbrains.space/public/p/compose/dev",
        )
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven(
            url = "https://maven.pkg.jetbrains.space/public/p/compose/dev",
        )
    }
}

include(":composeApp")
