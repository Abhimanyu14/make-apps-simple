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

import com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.viewmodel.BarcodeDetailsScreenViewModel

internal class BarcodeDetailsScreenUIEventHandler internal constructor(
    private val screenViewModel: BarcodeDetailsScreenViewModel,
    private val copyBarcodeValueToClipboard: () -> Unit,
    private val setIsDeleteBarcodeDialogVisible: (Boolean) -> Unit
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
                setIsDeleteBarcodeDialogVisible(false)
            }

            is BarcodeDetailsScreenUIEvent.OnBarcodeDetailsDeleteBarcodeDialog.Dismiss -> {
                setIsDeleteBarcodeDialogVisible(false)
            }

            is BarcodeDetailsScreenUIEvent.OnBarcodeDetailsDeleteBarcodeDialog.DismissButtonClick -> {
                setIsDeleteBarcodeDialogVisible(false)
            }

            is BarcodeDetailsScreenUIEvent.OnBarcodeDetailsTopAppBar.DeleteBarcodeButtonClick -> {
                setIsDeleteBarcodeDialogVisible(true)
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
