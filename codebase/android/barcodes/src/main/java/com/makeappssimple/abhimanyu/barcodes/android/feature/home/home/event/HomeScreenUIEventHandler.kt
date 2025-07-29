/*
 * Copyright 2025-2025 Abhimanyu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.event

import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.bottomsheet.HomeMenuBottomSheetEvent
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.bottomsheet.HomeScreenBottomSheetType
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.state.HomeScreenUIStateEvents
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.viewmodel.HomeScreenViewModel

internal class HomeScreenUIEventHandler internal constructor(
    private val uiStateEvents: HomeScreenUIStateEvents,
    private val screenViewModel: HomeScreenViewModel,
) {
    fun handleUIEvent(
        uiEvent: HomeScreenUIEvent,
    ) {
        when (uiEvent) {
            is HomeScreenUIEvent.OnAddFloatingActionButtonClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    HomeScreenBottomSheetType.Menu
                )
            }

            is HomeScreenUIEvent.OnBottomSheetDismiss -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is HomeScreenUIEvent.OnHomeMenuBottomSheetEvent -> {
                when (uiEvent.event) {
                    HomeMenuBottomSheetEvent.OnCreateBarcodeButtonClick -> {
                        screenViewModel.navigateToCreateBarcodeScreen()
                        uiStateEvents.resetScreenBottomSheetType()
                    }

                    HomeMenuBottomSheetEvent.OnScanBarcodeButtonClick -> {
                        screenViewModel.navigateToScanBarcodeScreen()
                        uiStateEvents.resetScreenBottomSheetType()
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
                uiStateEvents.updateIsDeleteBarcodeDialogVisible(false)
            }

            is HomeScreenUIEvent.OnHomeDeleteBarcodeDialog.Dismiss -> {
                uiStateEvents.updateIsDeleteBarcodeDialogVisible(false)
            }

            is HomeScreenUIEvent.OnHomeDeleteBarcodeDialog.DismissButtonClick -> {
                uiStateEvents.updateIsDeleteBarcodeDialogVisible(false)
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

            is HomeScreenUIEvent.OnTopAppBar.DeleteBarcodeButtonClick -> {
                uiStateEvents.updateIsDeleteBarcodeDialogVisible(true)
            }

            is HomeScreenUIEvent.OnTopAppBar.SettingsButtonClick -> {
                screenViewModel.navigateToSettingsScreen()
            }
        }
    }
}
