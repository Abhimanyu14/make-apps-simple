package com.makeappssimple.abhimanyu.cosmos.design.system.catalog

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.app.CosmosDesignSystemCatalogApp

public fun main() {
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Cosmos Design System Catalog",
        ) {
            CosmosDesignSystemCatalogApp()
        }
    }
}
