package com.makeappssimple.abhimanyu.barcodes.android.feature.scanbarcode.scanbarcode.event

import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIEvent

internal sealed class ScanBarcodeScreenUIEvent : ScreenUIEvent {
    data object OnTopAppBarNavigationButtonClick : ScanBarcodeScreenUIEvent()

    data class OnBarcodeScanned(
        val barcode: Barcode,
    ) : ScanBarcodeScreenUIEvent()
}
