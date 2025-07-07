package com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.event

import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.bottomsheet.HomeMenuBottomSheetEvent
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.viewmodel.HomeScreenViewModel

internal class HomeScreenUIEventHandler internal constructor(
    private val screenViewModel: HomeScreenViewModel,
    private val resetScreenBottomSheetType: () -> Unit,
) {
    fun handleUIEvent(
        uiEvent: HomeScreenUIEvent,
    ) {
        when (uiEvent) {
            is HomeScreenUIEvent.OnHomeMenuBottomSheetEvent -> {
                when (uiEvent.event) {
                    HomeMenuBottomSheetEvent.OnCreateBarcodeButtonClick -> {
                        screenViewModel.navigateToCreateBarcodeScreen()
                        resetScreenBottomSheetType()
                    }

                    HomeMenuBottomSheetEvent.OnScanBarcodeButtonClick -> {
                        screenViewModel.navigateToScanBarcodeScreen()
                        resetScreenBottomSheetType()
                    }
                }
            }

            is HomeScreenUIEvent.OnBarcodeDeletedSnackbar.ActionButtonClick -> {
                screenViewModel.saveBarcode(
                    barcode = uiEvent.barcode,
                )
            }

            is HomeScreenUIEvent.OnHomeDeleteBarcodeDialog.ConfirmButtonClick -> {
                screenViewModel.deleteBarcodes(
                    barcodes = uiEvent.barcodes,
                )
            }

            is HomeScreenUIEvent.OnListItem.Click -> {
                screenViewModel.navigateToBarcodeDetailsScreen(
                    barcodeId = uiEvent.barcode.id,
                )
            }

            is HomeScreenUIEvent.OnListItem.SwipeToEnd -> {
                screenViewModel.deleteBarcodes(
                    barcodes = uiEvent.barcodes,
                )
            }

            is HomeScreenUIEvent.OnTopAppBar.SettingsButtonClick -> {
                screenViewModel.navigateToSettingsScreen()
            }
        }
    }
}
