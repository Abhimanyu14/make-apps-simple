package com.makeappssimple.abhimanyu.kmp

class JsPlatform : Platform {
    override val name: String = "Web with Kotlin/JS"
}

actual fun getPlatform(): Platform {
    return JsPlatform()
}
