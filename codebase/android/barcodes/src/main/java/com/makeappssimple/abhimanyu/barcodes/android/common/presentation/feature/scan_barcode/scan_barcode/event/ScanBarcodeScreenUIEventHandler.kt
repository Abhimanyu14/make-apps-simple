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

package com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.scan_barcode.scan_barcode.event

import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.scan_barcode.scan_barcode.view_model.ScanBarcodeScreenViewModel

internal class ScanBarcodeScreenUIEventHandler(
    private val screenViewModel: ScanBarcodeScreenViewModel,
    private val onCameraPermissionRequestButtonClick: () -> Unit,
    private val onPermissionPermanentlyDeniedDialogConfirmButtonClick: () -> Unit,
    private val onPermissionPermanentlyDeniedDialogDismissButtonClick: () -> Unit,
    private val onTopAppBarNavigationButtonClick: () -> Unit?,
) {
    fun handleUIEvent(
        uiEvent: ScanBarcodeScreenUIEvent,
    ) {
        when (uiEvent) {
            is ScanBarcodeScreenUIEvent.OnCameraPermissionRequestButtonClick -> {
                onCameraPermissionRequestButtonClick()
            }

            is ScanBarcodeScreenUIEvent.OnPermissionPermanentlyDeniedDialog.ConfirmButtonClick -> {
                onPermissionPermanentlyDeniedDialogConfirmButtonClick()
            }

            is ScanBarcodeScreenUIEvent.OnPermissionPermanentlyDeniedDialog.DismissButtonClick -> {
                onPermissionPermanentlyDeniedDialogDismissButtonClick()
            }

            is ScanBarcodeScreenUIEvent.OnSnackbarDismissed -> {
                screenViewModel.onSnackbarDismissed()
            }

            is ScanBarcodeScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                onTopAppBarNavigationButtonClick()
            }
        }
    }
}
