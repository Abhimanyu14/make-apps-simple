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

package com.makeappssimple.abhimanyu.barcodes.android.features.web_view.presentation.web_view.view_model

import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.base.ScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation.BarcodesNavigationKit
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation.BarcodesScreen
import com.makeappssimple.abhimanyu.barcodes.android.features.web_view.presentation.navigation.WebViewScreenArgs
import com.makeappssimple.abhimanyu.barcodes.android.features.web_view.presentation.web_view.state.WebViewScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.features.web_view.presentation.web_view.state.WebViewScreenUIStateEvents
import com.makeappssimple.abhimanyu.barcodes.android.shared.ui.analytics.AnalyticsKit
import com.makeappssimple.abhimanyu.common.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.uri_decoder.UriDecoder
import com.makeappssimple.abhimanyu.common.util.defaultObjectStateIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

internal class WebViewScreenViewModel(
    analyticsKit: AnalyticsKit,
    barcodesNavigationKit: BarcodesNavigationKit,
    coroutineScope: CoroutineScope,
    logKit: LogKit,
    savedStateHandle: SavedStateHandle,
    uriDecoder: UriDecoder,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    analyticsKit = analyticsKit,
    barcodesNavigationKit = barcodesNavigationKit,
    barcodesScreen = BarcodesScreen.WebView,
    logKit = logKit,
) {
    // region screen args
    private val screenArgs = WebViewScreenArgs(
        savedStateHandle = savedStateHandle,
        uriDecoder = uriDecoder,
    )
    // endregion

    // region state
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

    // region state events
    private fun updateScreenTitle(
        updatedScreenTitle: String,
    ) {
        screenTitle.update {
            updatedScreenTitle
        }
    }
    // endregion
}
