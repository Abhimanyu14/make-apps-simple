package com.makeappssimple.abhimanyu.barcodes.android.core.logger.fake

import com.makeappssimple.abhimanyu.barcodes.android.core.logger.LogKit

internal class FakeLogKitImpl : LogKit {
    override fun logError(
        message: String,
        tag: String,
    ) {
        println("$tag: $message")
    }
}
