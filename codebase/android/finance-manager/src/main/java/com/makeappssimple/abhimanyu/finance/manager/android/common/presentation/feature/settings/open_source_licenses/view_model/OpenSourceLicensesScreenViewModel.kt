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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.settings.open_source_licenses.view_model

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.settings.open_source_licenses.state.OpenSourceLicensesScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.navigation.NavigationKit
import kotlinx.coroutines.CoroutineScope
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class OpenSourceLicensesScreenViewModel(
    coroutineScope: CoroutineScope,
    private val navigationKit: NavigationKit,
    internal val logKit: LogKit,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit {
    // region uiState
    internal val uiStateEvents: OpenSourceLicensesScreenUIStateEvents =
        OpenSourceLicensesScreenUIStateEvents(
            navigateUp = navigationKit::navigateUp,
        )
    // endregion
}
