package com.makeappssimple.abhimanyu.cat.fact

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return sayHello(platform.name)
    }
}
