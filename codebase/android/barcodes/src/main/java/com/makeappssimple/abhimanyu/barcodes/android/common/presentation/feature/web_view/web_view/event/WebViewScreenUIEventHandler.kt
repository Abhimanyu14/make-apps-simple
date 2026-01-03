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

package com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.web_view.web_view.event

import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.web_view.web_view.state.WebViewScreenUIStateEvents
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.web_view.web_view.view_model.WebViewScreenViewModel

internal class WebViewScreenUIEventHandler internal constructor(
    private val uiStateEvents: WebViewScreenUIStateEvents,
    private val screenViewModel: WebViewScreenViewModel,
) {
    fun handleUIEvent(
        uiEvent: WebViewScreenUIEvent,
    ) {
        when (uiEvent) {
            is WebViewScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                screenViewModel.navigateUp()
            }

            is WebViewScreenUIEvent.OnPageLoadingCompleted -> {
                uiStateEvents.updateScreenTitle(uiEvent.screenTitle)
            }
        }
    }
}
