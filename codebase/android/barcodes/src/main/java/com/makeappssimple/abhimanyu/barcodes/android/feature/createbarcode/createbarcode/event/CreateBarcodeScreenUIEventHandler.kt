package com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.createbarcode.event

import com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.createbarcode.viewmodel.CreateBarcodeScreenViewModel

internal class CreateBarcodeScreenUIEventHandler internal constructor(
    private val screenViewModel: CreateBarcodeScreenViewModel,
    private val copyBarcodeValueToClipboard: () -> Unit,
    private val triggerInAppReview: () -> Unit,
) {
    fun handleUIEvent(
        uiEvent: CreateBarcodeScreenUIEvent,
    ) {
        when (uiEvent) {
            is CreateBarcodeScreenUIEvent.OnBarcodeNameUpdated -> {
                screenViewModel.updateBarcodeName(
                    updatedBarcodeName = uiEvent.updatedBarcodeName,
                )
            }

            is CreateBarcodeScreenUIEvent.OnBarcodeValueUpdated -> {
                screenViewModel.updateBarcodeValue(
                    updatedBarcodeValue = uiEvent.updatedBarcodeValue,
                )
            }

            is CreateBarcodeScreenUIEvent.OnCopyBarcodeValueButtonClick -> {
                copyBarcodeValueToClipboard()
            }

            is CreateBarcodeScreenUIEvent.OnSaveButtonClick -> {
                screenViewModel.saveBarcode()
                triggerInAppReview()
            }

            is CreateBarcodeScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                screenViewModel.navigateUp()
            }
        }
    }
}
