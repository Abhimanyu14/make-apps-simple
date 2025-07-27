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

package com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.barcodes.android.core.analytics.AnalyticsKit
import com.makeappssimple.abhimanyu.barcodes.android.core.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.barcodes.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.barcodes.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.barcodes.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.feature.webview.navigation.WebViewScreenArgs
import com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.state.WebViewScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.state.WebViewScreenUIStateEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class WebViewScreenViewModel(
    analyticsKit: AnalyticsKit,
    coroutineScope: CoroutineScope,
    logKit: LogKit,
    navigationKit: NavigationKit,
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val screenUICommonState: ScreenUICommonState,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    analyticsKit = analyticsKit,
    logKit = logKit,
    navigationKit = navigationKit,
    screen = Screen.WebView,
    screenUICommonState = screenUICommonState,
) {
    // region screen args
    private val screenArgs = WebViewScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )
    // endregion

    // region UI state
    private val screenTitle = MutableStateFlow(
        value = "",
    )
    // endregion

    // region uiState and uiStateEvents
    val uiState: StateFlow<WebViewScreenUIState> =
        screenTitle.map { screenTitle ->
            WebViewScreenUIState(
                url = screenArgs.url.orEmpty(),
                screenTitle = screenTitle,
            )
        }.defaultObjectStateIn(
            scope = coroutineScope,
            initialValue = WebViewScreenUIState(
                url = screenArgs.url.orEmpty(),
                screenTitle = "",
            ),
        )
    val uiStateEvents: WebViewScreenUIStateEvents = WebViewScreenUIStateEvents(
        updateScreenTitle = ::updateScreenTitle,
    )
    // endregion

    override fun updateUiStateAndStateEvents() {
    }

    // region state events
    fun updateScreenTitle(
        updatedScreenTitle: String,
    ) {
        screenTitle.update {
            updatedScreenTitle
        }
    }
    // endregion
}
