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

package com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.createbarcode.screen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.barcodes.android.R
import com.makeappssimple.abhimanyu.barcodes.android.core.playstorereview.PlayStoreReviewHandler
import com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.createbarcode.event.CreateBarcodeScreenUIEventHandler
import com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.createbarcode.state.CreateBarcodeScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.createbarcode.state.CreateBarcodeScreenUIStateEvents
import com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.createbarcode.viewmodel.CreateBarcodeScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CreateBarcodeScreen(
    screenViewModel: CreateBarcodeScreenViewModel = koinViewModel(),
) {
    screenViewModel.logError(
        message = "Inside CreateBarcodeScreen",
    )

    val context = LocalContext.current
    val playStoreReviewHandler = remember {
        PlayStoreReviewHandler(context)
    }

    val uiState: CreateBarcodeScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: CreateBarcodeScreenUIStateEvents =
        screenViewModel.uiStateEvents

    val showBarcodeValueCopiedToastMessage: () -> Unit = {
        Toast.makeText(
            context,
            context.getString(
                R.string.screen_create_barcode_barcode_value_copied_toast_message,
                uiState.barcodeValue,
            ),
            Toast.LENGTH_SHORT
        ).show()
    }
    val triggerInAppReview: () -> Unit = {
        playStoreReviewHandler.triggerInAppReview {
            screenViewModel.navigateUp()
        }
    }

    val screenUIEventHandler = remember(
        key1 = screenViewModel,
        key2 = showBarcodeValueCopiedToastMessage,
        key3 = triggerInAppReview,
    ) {
        CreateBarcodeScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
            screenViewModel = screenViewModel,
            showBarcodeValueCopiedToastMessage = showBarcodeValueCopiedToastMessage,
            triggerInAppReview = triggerInAppReview,
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
