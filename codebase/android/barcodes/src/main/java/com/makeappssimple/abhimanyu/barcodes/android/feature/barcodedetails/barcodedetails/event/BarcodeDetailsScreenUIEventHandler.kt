package com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.event

import com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.viewmodel.BarcodeDetailsScreenViewModel

internal class BarcodeDetailsScreenUIEventHandler internal constructor(
    private val screenViewModel: BarcodeDetailsScreenViewModel,
    private val copyBarcodeValueToClipboard: () -> Unit,
) {
    fun handleUIEvent(
        uiEvent: BarcodeDetailsScreenUIEvent,
    ) {
        when (uiEvent) {
            is BarcodeDetailsScreenUIEvent.OnCopyBarcodeValueButtonClick -> {
                copyBarcodeValueToClipboard()
            }

            is BarcodeDetailsScreenUIEvent.OnBarcodeDetailsDeleteBarcodeDialog.ConfirmButtonClick -> {
                screenViewModel.deleteBarcode()
            }

            is BarcodeDetailsScreenUIEvent.OnBarcodeDetailsTopAppBar.EditBarcodeButtonClick -> {
                screenViewModel.navigateToCreateBarcodeScreen()
            }

            is BarcodeDetailsScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                screenViewModel.navigateUp()
            }
        }
    }
}
