package com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.event

import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIEvent

internal sealed class BarcodeDetailsScreenUIEvent : ScreenUIEvent {
    data object OnCopyBarcodeValueButtonClick : BarcodeDetailsScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : BarcodeDetailsScreenUIEvent()

    sealed class OnBarcodeDetailsTopAppBar {
        data object EditBarcodeButtonClick : BarcodeDetailsScreenUIEvent()
    }

    sealed class OnBarcodeDetailsDeleteBarcodeDialog {
        data object ConfirmButtonClick : BarcodeDetailsScreenUIEvent()
    }
}
