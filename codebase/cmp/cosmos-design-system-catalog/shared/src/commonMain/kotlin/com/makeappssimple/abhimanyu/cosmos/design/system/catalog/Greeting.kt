package com.makeappssimple.abhimanyu.cosmos.design.system.catalog

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return sayHello(platform.name)
    }
}
