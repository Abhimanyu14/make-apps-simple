package com.makeappssimple.abhimanyu.cosmos.design.system.catalog

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()
