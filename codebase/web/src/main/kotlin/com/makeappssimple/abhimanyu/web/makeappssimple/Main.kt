package com.makeappssimple.abhimanyu.web.makeappssimple

import com.makeappssimple.abhimanyu.web.makeappssimple.components.Layout
import com.makeappssimple.abhimanyu.web.makeappssimple.components.MainContentLayout
import com.makeappssimple.abhimanyu.web.makeappssimple.content.Header
import com.makeappssimple.abhimanyu.web.makeappssimple.content.PageFooter
import com.makeappssimple.abhimanyu.web.makeappssimple.style.AppStylesheet
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(
        rootElementId = "root",
    ) {
        Style(
            styleSheet = AppStylesheet,
        )
        Layout {
            Header()
//            MainContentLayout {
//                 Intro()
//                 ComposeWebLibraries()
//                 GetStarted()
//                 CodeSamples()
//                 JoinUs()
//            }
            PageFooter()
        }
    }
}
