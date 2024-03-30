package com.makeappssimple.abhimanyu.web.makeappssimple

import com.makeappssimple.abhimanyu.web.makeappssimple.components.Layout
import com.makeappssimple.abhimanyu.web.makeappssimple.components.MainContentLayout
import com.makeappssimple.abhimanyu.web.makeappssimple.content.CodeSamples
import com.makeappssimple.abhimanyu.web.makeappssimple.content.ComposeWebLibraries
import com.makeappssimple.abhimanyu.web.makeappssimple.content.GetStarted
import com.makeappssimple.abhimanyu.web.makeappssimple.content.Header
import com.makeappssimple.abhimanyu.web.makeappssimple.content.Intro
import com.makeappssimple.abhimanyu.web.makeappssimple.content.JoinUs
import com.makeappssimple.abhimanyu.web.makeappssimple.content.PageFooter
import com.makeappssimple.abhimanyu.web.makeappssimple.style.AppStylesheet
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(
        rootElementId = "root",
    ) {
        Style(AppStylesheet)

        Layout {
            Header()
            MainContentLayout {
                // Intro()
                // ComposeWebLibraries()
                // GetStarted()
                // CodeSamples()
                // JoinUs()
            }
            PageFooter()
        }
    }
}
