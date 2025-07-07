package com.makeappssimple.abhimanyu.barcodes.android.core.logger

private const val DEFAULT_LOGGER_TAG = "Abhi"

public interface LogKit {
    public fun logError(
        message: String,
        tag: String = DEFAULT_LOGGER_TAG,
    )
}
