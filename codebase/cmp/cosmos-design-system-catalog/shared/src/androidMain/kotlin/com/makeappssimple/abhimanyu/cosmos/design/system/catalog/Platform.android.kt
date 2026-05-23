package com.makeappssimple.abhimanyu.cosmos.design.system.catalog

import android.os.Build

private class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

internal actual fun getPlatform(): Platform = AndroidPlatform()
