package com.makeappssimple.abhimanyu.barcodes.android.feature.scanbarcode.scanbarcode.screen

import androidx.annotation.Keep
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIData

@Keep
internal data class ScanBarcodeScreenUIData(
    val isCameraPermissionGranted: Boolean,
    val isDeeplink: Boolean,
) : ScreenUIData
