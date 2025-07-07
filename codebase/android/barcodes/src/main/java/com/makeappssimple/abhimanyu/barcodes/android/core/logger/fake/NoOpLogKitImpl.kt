package com.makeappssimple.abhimanyu.barcodes.android.core.logger.fake

import com.makeappssimple.abhimanyu.barcodes.android.core.logger.LogKit

internal class NoOpLogKitImpl : LogKit {
    override fun logError(
        message: String,
        tag: String,
    ) {
    }
}
