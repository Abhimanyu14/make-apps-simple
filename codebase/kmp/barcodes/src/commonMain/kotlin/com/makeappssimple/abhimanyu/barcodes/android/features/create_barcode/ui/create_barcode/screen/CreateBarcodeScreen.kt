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

package com.makeappssimple.abhimanyu.barcodes.android.features.create_barcode.ui.create_barcode.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import com.makeappssimple.abhimanyu.barcodes.android.features.create_barcode.presentation.create_barcode.event.CreateBarcodeScreenUIEventHandler
import com.makeappssimple.abhimanyu.barcodes.android.features.create_barcode.presentation.create_barcode.state.CreateBarcodeScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.features.create_barcode.presentation.create_barcode.state.CreateBarcodeScreenUIStateEvents
import com.makeappssimple.abhimanyu.barcodes.android.features.create_barcode.presentation.create_barcode.view_model.CreateBarcodeScreenViewModel

@Composable
internal fun CreateBarcodeScreen(
    screenViewModel: CreateBarcodeScreenViewModel,
    showBarcodeValueCopiedToastMessage: (barcodeValue: String) -> Unit,
    triggerInAppReview: ((onCompleted: () -> Unit) -> Unit),
) {
    screenViewModel.logError(
        message = "Inside CreateBarcodeScreen",
    )

    val uiState: CreateBarcodeScreenUIState by screenViewModel.uiState.collectAsState()
    val uiStateEvents: CreateBarcodeScreenUIStateEvents =
        screenViewModel.uiStateEvents
    val currentBarcodeValue by rememberUpdatedState(
        newValue = uiState.barcodeValue,
    )

    val screenUIEventHandler = remember(
        key1 = screenViewModel,
        key2 = showBarcodeValueCopiedToastMessage,
        key3 = triggerInAppReview,
    ) {
        CreateBarcodeScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
            screenViewModel = screenViewModel,
            showBarcodeValueCopiedToastMessage = {
                showBarcodeValueCopiedToastMessage(currentBarcodeValue)
            },
            triggerInAppReview = {
                triggerInAppReview(
                    screenViewModel::navigateUp,
                )
            },
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    CreateBarcodeScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
