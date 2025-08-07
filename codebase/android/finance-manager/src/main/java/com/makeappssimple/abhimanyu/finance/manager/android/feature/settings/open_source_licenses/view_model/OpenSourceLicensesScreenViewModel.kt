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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.open_source_licenses.view_model

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.common.core.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.open_source_licenses.bottom_sheet.OpenSourceLicensesScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.open_source_licenses.state.OpenSourceLicensesScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.open_source_licenses.state.OpenSourceLicensesScreenUIStateEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class OpenSourceLicensesScreenViewModel(
    coroutineScope: CoroutineScope,
    internal val logKit: LogKit,
    @VisibleForTesting internal val navigationKit: NavigationKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
),
    OpenSourceLicensesScreenUIStateDelegate by OpenSourceLicensesScreenUIStateDelegateImpl(
        navigationKit = navigationKit,
    ) {
    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<OpenSourceLicensesScreenUIState> =
        MutableStateFlow(
            value = OpenSourceLicensesScreenUIState(),
        )
    internal val uiStateEvents: OpenSourceLicensesScreenUIStateEvents =
        OpenSourceLicensesScreenUIStateEvents(
            navigateUp = ::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        observeData()
        fetchData()
    }

    private fun fetchData() {}

    private fun observeData() {
        observeForUiStateAndStateEvents()
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                    ),
                ->
                uiState.update {
                    OpenSourceLicensesScreenUIState(
                        isBottomSheetVisible = screenBottomSheetType != OpenSourceLicensesScreenBottomSheetType.None,
                        isLoading = isLoading,
                        screenBottomSheetType = screenBottomSheetType,
                    )
                }
            }
        }
    }
    // endregion
}
