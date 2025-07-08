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

package com.makeappssimple.abhimanyu.barcodes.android.feature.settings.credits.viewmodel

import com.makeappssimple.abhimanyu.barcodes.android.core.analytics.AnalyticsKit
import com.makeappssimple.abhimanyu.barcodes.android.core.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.barcodes.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.feature.settings.credits.state.CreditsScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.feature.settings.credits.state.CreditsScreenUIStateEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class CreditsScreenViewModel(
    analyticsKit: AnalyticsKit,
    coroutineScope: CoroutineScope,
    private val navigationKit: NavigationKit,
    private val screenUICommonState: ScreenUICommonState,
    val logKit: LogKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
    analyticsKit = analyticsKit,
    screen = Screen.Credits,
    screenUICommonState = screenUICommonState,
), CreditsScreenUIStateDelegate by CreditsScreenUIStateDelegateImpl(
    navigationKit = navigationKit,
    screenUICommonState = screenUICommonState,
) {
    // region uiState and uiStateEvents
    private val _uiState: MutableStateFlow<CreditsScreenUIState> =
        MutableStateFlow(
            value = CreditsScreenUIState(),
        )
    val uiState: StateFlow<CreditsScreenUIState> = _uiState
    val uiStateEvents: CreditsScreenUIStateEvents = CreditsScreenUIStateEvents(
        navigateUp = ::navigateUp,
        navigateToWebViewScreen = ::navigateToWebViewScreen,
    )
    // endregion

    override fun updateUiStateAndStateEvents() {
        _uiState.update {
            CreditsScreenUIState()
        }
    }
}
