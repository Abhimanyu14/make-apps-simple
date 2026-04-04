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

package com.makeappssimple.abhimanyu.barcodes.android.features.settings.ui.credits.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.makeappssimple.abhimanyu.barcodes.android.features.settings.presentation.credits.event.CreditsScreenUIEventHandler
import com.makeappssimple.abhimanyu.barcodes.android.features.settings.presentation.credits.view_model.CreditsScreenViewModel

@Composable
internal fun CreditsScreen(
    screenViewModel: CreditsScreenViewModel,
) {
    screenViewModel.logError(
        message = "Inside CreditsScreen",
    )

    val screenUIEventHandler = remember(
        key1 = screenViewModel,
    ) {
        CreditsScreenUIEventHandler(
            screenViewModel = screenViewModel,
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
