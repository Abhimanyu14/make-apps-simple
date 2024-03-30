package com.makeappssimple.abhimanyu.web.makeappssimple

import org.w3c.dom.HTMLElement

@JsName("hljs")
@JsModule("highlight.js")
@JsNonModule
external class HighlightJs {
    companion object {
        fun highlightElement(block: HTMLElement)
    }
}
