package com.makeappssimple.abhimanyu.cosmos.design.system.catalog

private class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

internal actual fun getPlatform(): Platform = JVMPlatform()
