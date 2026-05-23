package com.makeappssimple.abhimanyu.cat.fact

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
