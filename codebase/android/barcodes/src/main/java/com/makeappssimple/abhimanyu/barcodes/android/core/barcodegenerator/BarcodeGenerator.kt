package com.makeappssimple.abhimanyu.barcodes.android.core.barcodegenerator

import android.graphics.Bitmap
import android.graphics.Color

private object BarcodeDefault {
    const val WIDTH: Int = 500
    const val HEIGHT: Int = 500
    const val BARCODE_COLOR: Int = Color.BLACK
    const val BACKGROUND_COLOR: Int = Color.WHITE
}

public interface BarcodeGenerator {
    public suspend fun generateBarcode(
        data: String,
        visionBarcodeFormat: Int,
        width: Int = BarcodeDefault.WIDTH,
        height: Int = BarcodeDefault.HEIGHT,
        barcodeColor: Int = BarcodeDefault.BARCODE_COLOR,
        backgroundColor: Int = BarcodeDefault.BACKGROUND_COLOR,
    ): Bitmap?
}
