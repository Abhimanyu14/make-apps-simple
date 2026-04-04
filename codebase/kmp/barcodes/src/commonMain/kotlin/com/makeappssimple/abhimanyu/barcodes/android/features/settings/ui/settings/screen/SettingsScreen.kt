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

package com.makeappssimple.abhimanyu.barcodes.android.features.settings.ui.settings.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.makeappssimple.abhimanyu.barcodes.android.features.settings.presentation.settings.event.SettingsScreenUIEventHandler
import com.makeappssimple.abhimanyu.barcodes.android.features.settings.presentation.settings.view_model.SettingsScreenViewModel

@Composable
internal fun SettingsScreen(
    screenViewModel: SettingsScreenViewModel,
    navigateToOpenSourceLicensesScreen: () -> Unit,
) {
    screenViewModel.logError(
        message = "Inside SettingsScreen",
    )

    val screenUIEventHandler = remember(
        key1 = screenViewModel,
        key2 = navigateToOpenSourceLicensesScreen,
    ) {
        SettingsScreenUIEventHandler(
            screenViewModel = screenViewModel,
            navigateToOpenSourceLicensesScreen = navigateToOpenSourceLicensesScreen,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    SettingsScreenUI(
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
