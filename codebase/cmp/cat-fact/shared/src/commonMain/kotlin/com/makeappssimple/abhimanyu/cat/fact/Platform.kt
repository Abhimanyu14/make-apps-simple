package com.makeappssimple.abhimanyu.cat.fact

internal interface Platform {
    val name: String
}

internal expect fun getPlatform(): Platform
