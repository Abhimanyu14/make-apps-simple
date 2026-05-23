package com.makeappssimple.abhimanyu.cat.fact

private class WasmPlatform : Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

internal actual fun getPlatform(): Platform = WasmPlatform()
