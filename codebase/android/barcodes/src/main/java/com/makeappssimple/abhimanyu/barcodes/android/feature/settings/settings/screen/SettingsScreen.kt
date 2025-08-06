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

package com.makeappssimple.abhimanyu.barcodes.android.feature.settings.settings.screen

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.makeappssimple.abhimanyu.barcodes.android.feature.settings.settings.event.SettingsScreenUIEventHandler
import com.makeappssimple.abhimanyu.barcodes.android.feature.settings.settings.view_model.SettingsScreenViewModel
import com.makeappssimple.abhimanyu.library.barcodes.android.R
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SettingsScreen(
    screenViewModel: SettingsScreenViewModel = koinViewModel(),
) {
    screenViewModel.logError(
        message = "Inside SettingsScreen",
    )

    // region navigateToOpenSourceLicensesScreen
    val context = LocalContext.current
    val openSourceLicensesScreenTitle = stringResource(
        id = R.string.barcodes_screen_settings_open_source_licenses,
    )
    val navigateToOpenSourceLicensesScreen: () -> Unit = {
        OssLicensesMenuActivity.setActivityTitle(openSourceLicensesScreenTitle)
        context.startActivity(
            Intent(
                context,
                OssLicensesMenuActivity::class.java
            )
        )
    }
    // endregion

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
