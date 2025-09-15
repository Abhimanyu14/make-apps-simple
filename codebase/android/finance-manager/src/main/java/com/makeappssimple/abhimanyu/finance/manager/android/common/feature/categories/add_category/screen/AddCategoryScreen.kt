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

package com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.add_category.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.add_category.event.AddCategoryScreenUIEventHandler
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.add_category.state.AddCategoryScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.add_category.state.AddCategoryScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.add_category.view_model.AddCategoryScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun AddCategoryScreen(
    screenViewModel: AddCategoryScreenViewModel = koinViewModel(),
) {
    screenViewModel.logKit.logError(
        message = "Inside AddCategoryScreen",
    )

    val uiState: AddCategoryScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: AddCategoryScreenUIStateEvents =
        screenViewModel.uiStateEvents

    val screenUIEventHandler = remember(
        key1 = uiStateEvents,
    ) {
        AddCategoryScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    AddCategoryScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
