import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.plugin.kotlin.jvm)
    alias(libs.plugins.plugin.compose)
    alias(libs.plugins.plugin.kotlin.compose)
}

dependencies {
    detektPlugins(libs.detekt.compose.rules)

    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutines.swing)
    implementation(libs.compose.ui.tooling.preview)
    implementation(projects.cosmosDesignSystemCatalog.shared)
}

compose.desktop {
    application {
        mainClass = "com.makeappssimple.abhimanyu.cosmos.design.system.catalog.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.makeappssimple.abhimanyu.cosmos.design.system.catalog"
            packageVersion = "1.0.0"
        }
    }
}

kotlin {
    explicitApi()
}
