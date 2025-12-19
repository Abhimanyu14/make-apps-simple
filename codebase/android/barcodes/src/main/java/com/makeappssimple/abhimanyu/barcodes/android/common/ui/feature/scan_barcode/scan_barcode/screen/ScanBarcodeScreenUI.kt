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

@file:OptIn(ExperimentalMaterial3Api::class)

package com.makeappssimple.abhimanyu.barcodes.android.common.ui.feature.scan_barcode.scan_barcode.screen

import androidx.camera.core.SurfaceRequest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.scan_barcode.scan_barcode.event.ScanBarcodeScreenUIEvent
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.scan_barcode.scan_barcode.state.ScanBarcodeScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.barcode_scanner.camera.BarcodeScannerPreview
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.components.CameraPermissionPermanentlyDeniedDialog
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.constants.TestTags.SCREEN_CONTENT_SCAN_BARCODE
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.constants.TestTags.SCREEN_SCAN_BARCODE
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button.CosmosElevatedButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.scaffold.CosmosScaffold
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.top_app_bar.CosmosTopAppBar
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme
import com.makeappssimple.abhimanyu.library.barcodes.android.R

@Composable
internal fun ScanBarcodeScreenUI(
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    uiState: ScanBarcodeScreenUIState = ScanBarcodeScreenUIState(),
    surfaceRequest: SurfaceRequest?,
    handleUIEvent: (uiEvent: ScanBarcodeScreenUIEvent) -> Unit = {},
) {
    CosmosScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_SCAN_BARCODE,
            )
            .fillMaxSize(),
        topBar = {
            CosmosTopAppBar(
                titleStringResource = CosmosStringResource.Id(
                    id = R.string.barcodes_screen_scan_barcode,
                ),
                navigationAction = {
                    handleUIEvent(ScanBarcodeScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        coroutineScope = state.coroutineScope,
    ) {
        AnimatedVisibility(
            visible = uiState.showPermissionPermanentlyDeniedDialog,
        ) {
            CameraPermissionPermanentlyDeniedDialog(
                onConfirmButtonClick = {
                    handleUIEvent(ScanBarcodeScreenUIEvent.OnPermissionPermanentlyDeniedDialog.ConfirmButtonClick)
                },
                onDismissButtonClick = {
                    handleUIEvent(ScanBarcodeScreenUIEvent.OnPermissionPermanentlyDeniedDialog.DismissButtonClick)
                },
            )
        }
        AnimatedVisibility(
            visible = uiState.isCameraPermissionGranted,
        ) {
            surfaceRequest?.let {
                BarcodeScannerPreview(
                    surfaceRequest = surfaceRequest,
                    modifier = Modifier
                        .testTag(
                            tag = SCREEN_CONTENT_SCAN_BARCODE,
                        ),
                )
            }
        }
        AnimatedVisibility(
            visible = !uiState.isCameraPermissionGranted,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        all = 16.dp,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CosmosText(
                        modifier = Modifier,
                        stringResource = CosmosStringResource.Id(
                            id = R.string.barcodes_screen_scan_barcode_camera_permission_rationale,
                        ),
                        style = CosmosAppTheme.typography.bodyMedium,
                    )
                    CosmosElevatedButton(
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                            ),
                        stringResource = CosmosStringResource.Id(
                            id = R.string.barcodes_screen_scan_barcode_camera_permission_action_label,
                        ),
                        onClick = {
                            handleUIEvent(ScanBarcodeScreenUIEvent.OnCameraPermissionRequestButtonClick)
                        },
                    )
                }
            }
        }
    }
}
