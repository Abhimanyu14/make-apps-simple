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

package com.makeappssimple.abhimanyu.barcodes.android.feature.settings.credits.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.makeappssimple.abhimanyu.barcodes.android.feature.settings.credits.event.CreditsScreenUIEventHandler
import com.makeappssimple.abhimanyu.barcodes.android.feature.settings.credits.state.CreditsScreenUIStateEvents
import com.makeappssimple.abhimanyu.barcodes.android.feature.settings.credits.viewmodel.CreditsScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CreditsScreen(
    screenViewModel: CreditsScreenViewModel = koinViewModel(),
) {
    screenViewModel.logKit.logError(
        message = "Inside CreditsScreen",
    )

    val uiStateEvents: CreditsScreenUIStateEvents =
        screenViewModel.uiStateEvents

    val screenUIEventHandler = remember(
        key1 = uiStateEvents,
    ) {
        CreditsScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    CreditsScreenUI(
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
