package com.makeappssimple.abhimanyu.barcodes.android.feature.settings.settings.event

import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIEvent

internal sealed class SettingsScreenUIEvent : ScreenUIEvent {
    data object OnTopAppBarNavigationButtonClick : SettingsScreenUIEvent()

    sealed class OnListItem {
        data object CreditsButtonClick : SettingsScreenUIEvent()
        data object OpenSourceLicensesButtonClick : SettingsScreenUIEvent()
        data object PrivacyPolicyButtonClick : SettingsScreenUIEvent()
    }
}
