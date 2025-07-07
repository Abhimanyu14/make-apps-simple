package com.makeappssimple.abhimanyu.barcodes.android.core.logger

import android.util.Log
import com.makeappssimple.abhimanyu.barcodes.android.core.common.buildconfig.BuildConfigKit

internal class LogKitImpl(
    private val buildConfigKit: BuildConfigKit,
) : LogKit {
    override fun logError(
        message: String,
        tag: String,
    ) {
        if (buildConfigKit.isDebugBuild()) {
            Log.e(tag, message)
        }
    }
}
