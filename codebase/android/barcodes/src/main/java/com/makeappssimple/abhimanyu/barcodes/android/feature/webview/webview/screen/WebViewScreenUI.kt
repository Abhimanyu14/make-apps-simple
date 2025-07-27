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

package com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.makeappssimple.abhimanyu.barcodes.android.core.common.constants.TestTags.SCREEN_WEB_VIEW
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.progressindicator.MyCircularProgressIndicator
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.topappbar.MyTopAppBar
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.common.CommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.barcodes.android.core.webview.WebView
import com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.event.WebViewScreenUIEvent
import com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.state.WebViewScreenUIState

@Composable
internal fun WebViewScreenUI(
    uiState: WebViewScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: WebViewScreenUIEvent) -> Unit = {},
) {
    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_WEB_VIEW,
            )
            .fillMaxSize(),
        topBar = {
            MyTopAppBar(
                titleText = uiState.screenTitle,
                navigationAction = {
                    handleUIEvent(WebViewScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = {
            state.focusManager.clearFocus()
        },
        coroutineScope = state.coroutineScope,
    ) {
        if (uiState.url.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                MyCircularProgressIndicator()
            }
        } else {
            WebView(
                url = uiState.url,
                onPageLoadingCompleted = {
                    handleUIEvent(WebViewScreenUIEvent.OnPageLoadingCompleted(it))
                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.Red,
                    ),
            )
        }
    }
}
