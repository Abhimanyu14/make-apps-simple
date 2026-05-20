package com.makeappssimple.abhimanyu.catfact

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return sayHello(platform.name)
    }
}
