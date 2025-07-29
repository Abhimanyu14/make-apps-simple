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

package com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.event.HomeScreenUIEventHandler
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.state.HomeScreenUIState
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.state.HomeScreenUIStateEvents
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.viewmodel.HomeScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeScreen(
    screenViewModel: HomeScreenViewModel = koinViewModel(),
) {
    screenViewModel.logError(
        message = "Inside HomeScreen",
    )

    val uiState: HomeScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: HomeScreenUIStateEvents = screenViewModel.uiStateEvents

    val screenUIEventHandler = remember(
        key1 = screenViewModel,
        key2 = uiStateEvents,
    ) {
        HomeScreenUIEventHandler(
            screenViewModel = screenViewModel,
            uiStateEvents = uiStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    HomeScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
