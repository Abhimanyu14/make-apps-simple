package com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.screen

import androidx.annotation.Keep
import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIData

@Keep
internal data class HomeScreenUIData(
    val allBarcodes: List<Barcode>,
    val barcodeFormattedTimestamps: List<String>,
) : ScreenUIData
