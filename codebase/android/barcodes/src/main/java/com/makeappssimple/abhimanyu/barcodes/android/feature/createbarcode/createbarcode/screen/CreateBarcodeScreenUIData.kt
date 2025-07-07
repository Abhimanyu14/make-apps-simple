package com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.createbarcode.screen

import androidx.annotation.Keep
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIData

@Keep
internal data class CreateBarcodeScreenUIData(
    val isBarcodeValueEditable: Boolean,
    val barcodeName: String,
    val barcodeValue: String,
) : ScreenUIData
