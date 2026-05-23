import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.plugin.kotlin.jvm)
    alias(libs.plugins.plugin.compose)
    alias(libs.plugins.plugin.kotlin.compose)
}

dependencies {
    implementation(projects.cosmosDesignSystemCatalog.shared)

    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutines.swing)

    implementation(libs.compose.ui.tooling.preview)
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
