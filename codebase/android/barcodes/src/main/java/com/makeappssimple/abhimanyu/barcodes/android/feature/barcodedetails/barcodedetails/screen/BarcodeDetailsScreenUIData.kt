package com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.screen

import android.graphics.Bitmap
import androidx.annotation.Keep
import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIData

@Keep
internal data class BarcodeDetailsScreenUIData(
    val barcode: Barcode,
    val formattedTimestamp: String,
    val bitmap: Bitmap?,
) : ScreenUIData
