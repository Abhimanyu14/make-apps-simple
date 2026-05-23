package com.makeappssimple.abhimanyu.cosmos.design.system.catalog

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
