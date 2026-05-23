package com.makeappssimple.abhimanyu.cosmos.design.system.catalog

internal interface Platform {
    val name: String
}

internal expect fun getPlatform(): Platform
