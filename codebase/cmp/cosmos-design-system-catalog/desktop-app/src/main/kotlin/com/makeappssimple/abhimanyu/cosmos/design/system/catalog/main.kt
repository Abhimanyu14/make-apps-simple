package com.makeappssimple.abhimanyu.cosmos.design.system.catalog

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

internal fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Cosmos Design System Catalog",
    ) {
        App()
    }
}
