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

package com.makeappssimple.abhimanyu.barcodes.android.features.barcode_details.ui.barcode_details.screen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.barcodes.android.core.domain.model.BarcodeSourceDomainModel
import com.makeappssimple.abhimanyu.barcodes.android.features.barcode_details.presentation.barcode_details.event.BarcodeDetailsScreenUIEventHandler
import com.makeappssimple.abhimanyu.barcodes.android.features.barcode_details.presentation.barcode_details.state.BarcodeDetailsScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.features.barcode_details.presentation.barcode_details.state.BarcodeDetailsScreenUIStateEvents
import com.makeappssimple.abhimanyu.barcodes.android.features.barcode_details.presentation.barcode_details.view_model.BarcodeDetailsScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.shared.ui.constants.BarcodesStrings
import com.makeappssimple.abhimanyu.cosmos.design.system.android.util.dpToPx
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.min

@Composable
internal fun BarcodeDetailsScreen(
    screenViewModel: BarcodeDetailsScreenViewModel = koinViewModel(),
) {
    screenViewModel.logError(
        message = "Inside BarcodeDetailsScreen",
    )

    val context = LocalContext.current
    val windowContainerSize = LocalWindowInfo.current.containerSize
    val screenHeight = windowContainerSize.height.dp.dpToPx()
    val screenWidth = windowContainerSize.width.dp.dpToPx()

    val uiState: BarcodeDetailsScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: BarcodeDetailsScreenUIStateEvents =
        screenViewModel.uiStateEvents

    val showBarcodeValueCopiedToastMessage: () -> Unit = {
        Toast.makeText(
            context,
            BarcodesStrings.barcodeDetailsBarcodeValueCopiedToastMessage(
                barcodeValue = uiState.barcodeValue,
            ),
            Toast.LENGTH_SHORT
        ).show()
    }
    val formattedTimestampLabel = remember(
        key1 = uiState.barcodeSource,
    ) {
        when (uiState.barcodeSource) {
            BarcodeSourceDomainModel.Created -> {
                BarcodesStrings.barcodeDetailsBarcodeTimestampCreated
            }

            BarcodeSourceDomainModel.Scanned -> {
                BarcodesStrings.barcodeDetailsBarcodeTimestampScanned
            }
        }
    }

    val screenUIEventHandler = remember(
        key1 = screenViewModel,
        key2 = showBarcodeValueCopiedToastMessage,
    ) {
        BarcodeDetailsScreenUIEventHandler(
            screenViewModel = screenViewModel,
            uiStateEvents = uiStateEvents,
            showBarcodeValueCopiedToastMessage = showBarcodeValueCopiedToastMessage,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
        uiStateEvents.updateBarcodeBitmapSize(
            min(
                screenWidth,
                screenHeight
            ).toInt()
        )
    }

    BarcodeDetailsScreenUI(
        uiState = uiState.copy(
            formattedTimestampLabel = formattedTimestampLabel,
        ),
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
