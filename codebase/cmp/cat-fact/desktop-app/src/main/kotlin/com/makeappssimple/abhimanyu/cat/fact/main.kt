package com.makeappssimple.abhimanyu.cat.fact

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

internal fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Cat Fact",
    ) {
        App()
    }
}
