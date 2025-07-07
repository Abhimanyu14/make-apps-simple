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

package com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.barcodes.android.core.logger.LocalLogKit
import com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.event.WebViewScreenUIEventHandler
import com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.state.WebViewScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.state.WebViewScreenUIStateEvents
import com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.viewmodel.WebViewScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun WebViewScreen(
    screenViewModel: WebViewScreenViewModel = koinViewModel(),
) {
    val myLogger = LocalLogKit.current
    myLogger.logError(
        message = "Inside WebViewScreen",
    )

    val uiState: WebViewScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: WebViewScreenUIStateEvents =
        screenViewModel.uiStateEvents

    val screenUIEventHandler = remember(
        key1 = uiStateEvents,
    ) {
        WebViewScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    WebViewScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
