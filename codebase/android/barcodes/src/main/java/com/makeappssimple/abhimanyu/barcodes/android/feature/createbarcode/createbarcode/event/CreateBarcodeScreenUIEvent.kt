package com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.createbarcode.event

import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIEvent

internal sealed class CreateBarcodeScreenUIEvent : ScreenUIEvent {
    data object OnCopyBarcodeValueButtonClick : CreateBarcodeScreenUIEvent()
    data object OnSaveButtonClick : CreateBarcodeScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : CreateBarcodeScreenUIEvent()

    data class OnBarcodeNameUpdated(
        val updatedBarcodeName: String,
    ) : CreateBarcodeScreenUIEvent()

    data class OnBarcodeValueUpdated(
        val updatedBarcodeValue: String,
    ) : CreateBarcodeScreenUIEvent()
}
