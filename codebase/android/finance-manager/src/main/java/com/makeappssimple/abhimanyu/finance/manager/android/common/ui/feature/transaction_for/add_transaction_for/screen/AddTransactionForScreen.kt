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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.feature.transaction_for.add_transaction_for.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.add_transaction_for.event.AddTransactionForScreenUIEventHandler
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.add_transaction_for.state.AddTransactionForScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.add_transaction_for.state.AddTransactionForScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.add_transaction_for.view_model.AddTransactionForScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun AddTransactionForScreen(
    screenViewModel: AddTransactionForScreenViewModel = koinViewModel(),
) {
    screenViewModel.logKit.logError(
        message = "Inside AddTransactionForScreen",
    )

    val uiState: AddTransactionForScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: AddTransactionForScreenUIStateEvents =
        screenViewModel.uiStateEvents

    val screenUIEventHandler = remember(
        key1 = uiStateEvents,
    ) {
        AddTransactionForScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    AddTransactionForScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
