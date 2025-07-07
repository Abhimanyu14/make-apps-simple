package com.makeappssimple.abhimanyu.barcodes.android.core.model

public enum class BarcodeSource(
    public val title: String,
) {
    SCANNED(
        title = "Scanned",
    ),
    CREATED(
        title = "Created",
    ),
}
