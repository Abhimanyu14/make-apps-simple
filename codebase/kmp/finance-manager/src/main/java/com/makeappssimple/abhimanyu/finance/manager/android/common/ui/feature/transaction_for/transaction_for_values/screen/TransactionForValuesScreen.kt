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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.feature.transaction_for.transaction_for_values.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.transaction_for_values.event.TransactionForValuesScreenUIEventHandler
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.transaction_for_values.state.TransactionForValuesScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.transaction_for_values.state.TransactionForValuesScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.transaction_for_values.view_model.TransactionForValuesScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun TransactionForValuesScreen(
    screenViewModel: TransactionForValuesScreenViewModel = koinViewModel(),
) {
    screenViewModel.logKit.logError(
        message = "Inside TransactionForValuesScreen",
    )

    val uiState: TransactionForValuesScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: TransactionForValuesScreenUIStateEvents =
        screenViewModel.uiStateEvents

    val screenUIEventHandler = remember(
        key1 = uiStateEvents,
    ) {
        TransactionForValuesScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    TransactionForValuesScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
