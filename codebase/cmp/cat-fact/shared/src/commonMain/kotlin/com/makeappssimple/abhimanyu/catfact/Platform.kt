package com.makeappssimple.abhimanyu.catfact

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
