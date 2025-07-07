package com.makeappssimple.abhimanyu.barcodes.android.feature.settings.settings.event

import com.makeappssimple.abhimanyu.barcodes.android.feature.settings.settings.viewmodel.SettingsScreenViewModel

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
