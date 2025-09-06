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

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.open_source_licenses.state.OpenSourceLicensesScreenUIStateEvents
import kotlinx.coroutines.CoroutineScope
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class OpenSourceLicensesScreenViewModel(
    coroutineScope: CoroutineScope,
    navigationKit: NavigationKit,
    internal val logKit: LogKit,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit,
    NavigationKit by navigationKit {
    // region uiState
    internal val uiStateEvents: OpenSourceLicensesScreenUIStateEvents =
        OpenSourceLicensesScreenUIStateEvents(
            navigateUp = ::navigateUp,
        )
    // endregion
}
