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
import com.makeappssimple.abhimanyu.barcodes.android.R
import com.makeappssimple.abhimanyu.barcodes.android.core.common.clipboard.BARCODE_VALUE_CLIPBOARD_LABEL
import com.makeappssimple.abhimanyu.barcodes.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.util.dpToPx
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.event.BarcodeDetailsScreenUIEventHandler
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.state.rememberBarcodeDetailsScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.viewmodel.BarcodeDetailsScreenViewModel
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

    val screenUIData: MyResult<BarcodeDetailsScreenUIData>? by screenViewModel.screenUIData.collectAsStateWithLifecycle()
    val uiState = rememberBarcodeDetailsScreenUIState(
        data = screenUIData,
    )
    val copyBarcodeValueToClipboard: () -> Unit = {
        if (
            screenViewModel.copyToClipboard(
                label = BARCODE_VALUE_CLIPBOARD_LABEL,
                text = uiState.barcode?.value.orEmpty(),
            )
        ) {
            Toast.makeText(
                context,
                context.getString(
                    R.string.screen_barcode_details_barcode_value_copied_toast_message,
                    uiState.barcode?.value.orEmpty(),
                ),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    val screenUIEventHandler = remember(
        key1 = screenViewModel,
        key2 = copyBarcodeValueToClipboard,
    ) {
        BarcodeDetailsScreenUIEventHandler(
            screenViewModel = screenViewModel,
            copyBarcodeValueToClipboard = copyBarcodeValueToClipboard,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
        screenViewModel.updateBarcodeBitmapSize(
            size = min(screenWidth, screenHeight).toInt(),
        )
    }

    BarcodeDetailsScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
