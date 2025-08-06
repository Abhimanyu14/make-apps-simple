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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.view_transaction.event.ViewTransactionScreenUIEventHandler
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.view_transaction.state.ViewTransactionScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.view_transaction.state.ViewTransactionScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.view_transaction.view_model.ViewTransactionScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ViewTransactionScreen(
    screenViewModel: ViewTransactionScreenViewModel = koinViewModel(),
) {
    screenViewModel.logKit.logError(
        message = "Inside ViewTransactionScreen",
    )

    val uiState: ViewTransactionScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: ViewTransactionScreenUIStateEvents =
        screenViewModel.uiStateEvents

    val screenUIEventHandler = remember(
        key1 = uiStateEvents,
    ) {
        ViewTransactionScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    ViewTransactionScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
