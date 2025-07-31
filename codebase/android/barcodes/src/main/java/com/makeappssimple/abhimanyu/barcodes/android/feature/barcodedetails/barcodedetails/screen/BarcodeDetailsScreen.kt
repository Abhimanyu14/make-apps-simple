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

package com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.screen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.util.dpToPx
import com.makeappssimple.abhimanyu.barcodes.android.core.model.BarcodeSource
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.event.BarcodeDetailsScreenUIEventHandler
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.state.BarcodeDetailsScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.state.BarcodeDetailsScreenUIStateEvents
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.viewmodel.BarcodeDetailsScreenViewModel
import com.makeappssimple.abhimanyu.library.barcodes.android.R
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
            context.getString(
                R.string.barcodes_screen_barcode_details_barcode_value_copied_toast_message,
                uiState.barcodeValue,
            ),
            Toast.LENGTH_SHORT
        ).show()
    }
    val formattedTimestampLabelId = remember(
        key1 = uiState.barcodeSource,
    ) {
        when (uiState.barcodeSource) {
            BarcodeSource.CREATED -> {
                R.string.barcodes_screen_barcode_details_barcode_timestamp_created
            }

            BarcodeSource.SCANNED -> {
                R.string.barcodes_screen_barcode_details_barcode_timestamp_scanned
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
            min(screenWidth, screenHeight).toInt()
        )
    }

    BarcodeDetailsScreenUI(
        uiState = uiState.copy(
            formattedTimestampLabelId = formattedTimestampLabelId,
        ),
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
