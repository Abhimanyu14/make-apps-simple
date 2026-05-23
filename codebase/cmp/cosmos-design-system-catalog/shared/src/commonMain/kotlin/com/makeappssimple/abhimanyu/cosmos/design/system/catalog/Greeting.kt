package com.makeappssimple.abhimanyu.cosmos.design.system.catalog

internal class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return sayHello(platform.name)
    }
}
