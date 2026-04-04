/*
 * Copyright 2025-2026 Abhimanyu
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

package com.makeappssimple.abhimanyu.barcodes.android.features.scan_barcode.ui.scan_barcode.screen

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import com.makeappssimple.abhimanyu.barcodes.android.core.domain.model.BarcodeFormatDomainModel
import com.makeappssimple.abhimanyu.barcodes.android.features.scan_barcode.presentation.scan_barcode.event.ScanBarcodeScreenUIEventHandler
import com.makeappssimple.abhimanyu.barcodes.android.features.scan_barcode.presentation.scan_barcode.state.ScanBarcodeScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.features.scan_barcode.presentation.scan_barcode.view_model.ScanBarcodeScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.shared.ui.permissions.PermissionRequest

@Composable
internal fun ScanBarcodeScreen(
    screenViewModel: ScanBarcodeScreenViewModel,
    cameraPermissionRequest: PermissionRequest,
    handleActivityResult: (barcodeFormat: Int, barcodeValue: String) -> Unit,
    handleActivityResultCanceled: () -> Unit,
    openAppSettings: () -> Unit,
    cameraContent: @Composable (isScanning: Boolean, onBarcodeScanned: (BarcodeFormatDomainModel, String) -> Unit) -> Unit,
) {
    screenViewModel.logError(
        message = "Inside ScanBarcodeScreen",
    )

    val uiState: ScanBarcodeScreenUIState by screenViewModel.uiState.collectAsState()
    val currentIsDeeplink by rememberUpdatedState(
        newValue = uiState.isDeeplink,
    )

    val onBarcodeScanned = remember(
        key1 = screenViewModel,
        key2 = currentIsDeeplink,
    ) {
        { barcodeFormat: BarcodeFormatDomainModel, barcodeValue: String ->
            if (currentIsDeeplink) {
                handleActivityResult(
                    barcodeFormat.value,
                    barcodeValue,
                )
            } else {
                screenViewModel.saveBarcode(
                    barcodeValue = barcodeValue,
                    barcodeFormat = barcodeFormat.value,
                )
            }
        }
    }

    val onTopAppBarNavigationButtonClick: () -> Unit? = {
        if (currentIsDeeplink) {
            handleActivityResultCanceled()
        } else {
            screenViewModel.navigateUp()
            Unit
        }
    }
    val onCameraPermissionRequestButtonClick: () -> Unit = {
        when (cameraPermissionRequest.permissionStatus) {
            com.makeappssimple.abhimanyu.barcodes.android.shared.ui.permissions.PermissionStatus.Unknown -> {
                cameraPermissionRequest.requestPermission()
            }

            com.makeappssimple.abhimanyu.barcodes.android.shared.ui.permissions.PermissionStatus.Granted -> {}

            com.makeappssimple.abhimanyu.barcodes.android.shared.ui.permissions.PermissionStatus.Denied -> {
                cameraPermissionRequest.requestPermission()
            }

            com.makeappssimple.abhimanyu.barcodes.android.shared.ui.permissions.PermissionStatus.PermanentlyDenied -> {
                screenViewModel.setPermissionPermanentlyDeniedDialogVisible(
                    isVisible = true,
                )
            }
        }
    }
    val onPermissionPermanentlyDeniedDialogConfirmButtonClick: () -> Unit = {
        screenViewModel.setPermissionPermanentlyDeniedDialogVisible(
            isVisible = false,
        )
        openAppSettings()
    }
    val onPermissionPermanentlyDeniedDialogDismissButtonClick: () -> Unit = {
        screenViewModel.setPermissionPermanentlyDeniedDialogVisible(
            isVisible = false,
        )
    }

    val screenUIEventHandler = remember(
        key1 = screenViewModel,
        key2 = cameraPermissionRequest,
    ) {
        ScanBarcodeScreenUIEventHandler(
            screenViewModel = screenViewModel,
            onCameraPermissionRequestButtonClick = onCameraPermissionRequestButtonClick,
            onPermissionPermanentlyDeniedDialogConfirmButtonClick = onPermissionPermanentlyDeniedDialogConfirmButtonClick,
            onPermissionPermanentlyDeniedDialogDismissButtonClick = onPermissionPermanentlyDeniedDialogDismissButtonClick,
            onTopAppBarNavigationButtonClick = onTopAppBarNavigationButtonClick,
        )
    }

    LaunchedEffect(
        key1 = cameraPermissionRequest.permissionStatus,
    ) {
        screenViewModel.onCameraPermissionChanged(
            isCameraPermissionGranted = cameraPermissionRequest.permissionStatus == com.makeappssimple.abhimanyu.barcodes.android.shared.ui.permissions.PermissionStatus.Granted,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    BackHandler(
        enabled = uiState.isDeeplink,
    ) {
        handleActivityResultCanceled()
    }

    ScanBarcodeScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
        cameraContent = cameraContent,
        onBarcodeScanned = onBarcodeScanned,
    )
}
