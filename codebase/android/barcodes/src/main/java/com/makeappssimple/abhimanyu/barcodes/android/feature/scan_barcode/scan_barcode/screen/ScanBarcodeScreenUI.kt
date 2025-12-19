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

package com.makeappssimple.abhimanyu.barcodes.android.feature.scan_barcode.scan_barcode.screen

import androidx.camera.core.SurfaceRequest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.makeappssimple.abhimanyu.barcodes.android.core.barcode_scanner.camera.BarcodeScannerPreview
import com.makeappssimple.abhimanyu.barcodes.android.core.constants.TestTags.SCREEN_CONTENT_SCAN_BARCODE
import com.makeappssimple.abhimanyu.barcodes.android.core.constants.TestTags.SCREEN_SCAN_BARCODE
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.barcodes.android.feature.scan_barcode.scan_barcode.event.ScanBarcodeScreenUIEvent
import com.makeappssimple.abhimanyu.barcodes.android.feature.scan_barcode.scan_barcode.state.ScanBarcodeScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.resource.StringResource
import com.makeappssimple.abhimanyu.library.barcodes.android.R

@Composable
internal fun ScanBarcodeScreenUI(
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    uiState: ScanBarcodeScreenUIState = ScanBarcodeScreenUIState(),
    surfaceRequest: SurfaceRequest?,
    handleUIEvent: (uiEvent: ScanBarcodeScreenUIEvent) -> Unit = {},
) {
    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_SCAN_BARCODE,
            )
            .fillMaxSize(),
        topBar = {
            MyTopAppBar(
                titleStringResource = StringResource.Id(
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
    }
}
