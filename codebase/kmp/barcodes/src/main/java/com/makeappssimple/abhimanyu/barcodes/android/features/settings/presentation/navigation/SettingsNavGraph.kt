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

package com.makeappssimple.abhimanyu.barcodes.android.features.settings.presentation.navigation

import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation.BarcodesScreen
import com.makeappssimple.abhimanyu.barcodes.android.features.settings.presentation.credits.view_model.CreditsScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.features.settings.presentation.settings.view_model.SettingsScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.features.settings.ui.credits.screen.CreditsScreen
import com.makeappssimple.abhimanyu.barcodes.android.features.settings.ui.settings.screen.SettingsScreen
import com.makeappssimple.abhimanyu.barcodes.android.shared.ui.constants.BarcodesStrings
import org.koin.compose.viewmodel.koinViewModel

internal fun NavGraphBuilder.settingsNavGraph() {
    composable(
        route = BarcodesScreen.Credits.route,
    ) {
        CreditsScreen(
            screenViewModel = koinViewModel<CreditsScreenViewModel>(),
        )
    }

    composable(
        route = BarcodesScreen.Settings.route,
    ) {
        val context = LocalContext.current

        SettingsScreen(
            screenViewModel = koinViewModel<SettingsScreenViewModel>(),
            navigateToOpenSourceLicensesScreen = {
                OssLicensesMenuActivity.setActivityTitle(
                    BarcodesStrings.settingsOpenSourceLicenses,
                )
                context.startActivity(
                    Intent(
                        context,
                        OssLicensesMenuActivity::class.java,
                    ),
                )
            },
        )
    }
}
