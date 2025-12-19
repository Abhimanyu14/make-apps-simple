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

package com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.settings.settings.event

import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.settings.settings.view_model.SettingsScreenViewModel

private object SettingsScreenUIEventHandlerConstants {
    const val PRIVACY_POLICY_URL =
        "https://makeappssimple.com/hosting/barcodes/privacy_policy.html"
}

internal class SettingsScreenUIEventHandler internal constructor(
    private val screenViewModel: SettingsScreenViewModel,
    private val navigateToOpenSourceLicensesScreen: () -> Unit,
) {
    fun handleUIEvent(
        uiEvent: SettingsScreenUIEvent,
    ) {
        when (uiEvent) {
            is SettingsScreenUIEvent.OnListItem.CreditsButtonClick -> {
                screenViewModel.navigateToCreditsScreen()
            }

            is SettingsScreenUIEvent.OnListItem.OpenSourceLicensesButtonClick -> {
                navigateToOpenSourceLicensesScreen()
            }

            is SettingsScreenUIEvent.OnListItem.PrivacyPolicyButtonClick -> {
                screenViewModel.navigateToWebViewScreen(
                    url = SettingsScreenUIEventHandlerConstants.PRIVACY_POLICY_URL,
                )
            }

            is SettingsScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                screenViewModel.navigateUp()
            }
        }
    }
}
