package com.makeappssimple.abhimanyu.catfact

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Cat Fact",
    ) {
        App()
    }
}
