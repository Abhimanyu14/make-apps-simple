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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.event.CategoriesScreenUIEventHandler
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.state.CategoriesScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.state.CategoriesScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.view_model.CategoriesScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CategoriesScreen(
    screenViewModel: CategoriesScreenViewModel = koinViewModel(),
) {
    screenViewModel.logKit.logError(
        message = "Inside CategoriesScreen",
    )

    val uiState: CategoriesScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: CategoriesScreenUIStateEvents =
        screenViewModel.uiStateEvents

    val screenUIEventHandler = remember(
        key1 = uiStateEvents,
    ) {
        CategoriesScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    CategoriesScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
