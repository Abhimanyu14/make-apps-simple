package com.makeappssimple.abhimanyu.cat.fact

import web.navigator.navigator

private class JsPlatform : Platform {
    private val userAgent = navigator.userAgent
    private val browserList = listOf("Chrome", "Firefox", "Safari", "Edge")

    override val name: String = userAgent.findAnyOf(browserList, ignoreCase = true)
        ?.let { (startIndex) ->
            userAgent.substring(startIndex).substringBefore(" ")
        }
        ?: "Unknown"
}

internal actual fun getPlatform(): Platform = JsPlatform()
