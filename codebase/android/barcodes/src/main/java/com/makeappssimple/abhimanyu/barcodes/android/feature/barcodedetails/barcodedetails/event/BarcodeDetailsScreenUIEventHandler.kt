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

package com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.event

import com.makeappssimple.abhimanyu.barcodes.android.core.common.clipboard.BARCODE_VALUE_CLIPBOARD_LABEL
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.state.BarcodeDetailsScreenUIStateEvents
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.viewmodel.BarcodeDetailsScreenViewModel

internal class BarcodeDetailsScreenUIEventHandler internal constructor(
    private val uiStateEvents: BarcodeDetailsScreenUIStateEvents,
    private val screenViewModel: BarcodeDetailsScreenViewModel,
    private val showBarcodeValueCopiedToastMessage: () -> Unit
) {
    fun handleUIEvent(
        uiEvent: BarcodeDetailsScreenUIEvent,
    ) {
        when (uiEvent) {
            is BarcodeDetailsScreenUIEvent.OnBarcodeDetailsDeleteBarcodeDialog.ConfirmButtonClick -> {
                screenViewModel.deleteBarcode()
                uiStateEvents.updateIsDeleteBarcodeDialogVisible(false)
            }

            is BarcodeDetailsScreenUIEvent.OnBarcodeDetailsDeleteBarcodeDialog.Dismiss -> {
                uiStateEvents.updateIsDeleteBarcodeDialogVisible(false)
            }

            is BarcodeDetailsScreenUIEvent.OnBarcodeDetailsDeleteBarcodeDialog.DismissButtonClick -> {
                uiStateEvents.updateIsDeleteBarcodeDialogVisible(false)
            }

            is BarcodeDetailsScreenUIEvent.OnBarcodeDetailsTopAppBar.DeleteBarcodeButtonClick -> {
                uiStateEvents.updateIsDeleteBarcodeDialogVisible(true)
            }

            is BarcodeDetailsScreenUIEvent.OnBarcodeDetailsTopAppBar.EditBarcodeButtonClick -> {
                screenViewModel.navigateToCreateBarcodeScreen(
                    barcodeId = uiEvent.barcodeId,
                )
            }

            is BarcodeDetailsScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                screenViewModel.navigateUp()
            }

            is BarcodeDetailsScreenUIEvent.OnCopyBarcodeValueButtonClick -> {
                if (
                    screenViewModel.copyToClipboard(
                        label = BARCODE_VALUE_CLIPBOARD_LABEL,
                        text = uiEvent.barcodeValue,
                    ) && screenViewModel.shouldShowCopiedToClipboardToastMessage()
                ) {
                    showBarcodeValueCopiedToastMessage()
                }
            }
        }
    }
}
