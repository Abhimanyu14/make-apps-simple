package com.makeappssimple.abhimanyu.cosmos.design.system.catalog

private class WasmPlatform : Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

internal actual fun getPlatform(): Platform = WasmPlatform()
