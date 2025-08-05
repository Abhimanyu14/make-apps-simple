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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.settings.event

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIEvent

@Immutable
internal sealed class SettingsScreenUIEvent : ScreenUIEvent {
    data object OnAccountsListItemClick : SettingsScreenUIEvent()
    data object OnBackupDataListItemClick : SettingsScreenUIEvent()
    data object OnCategoriesListItemClick : SettingsScreenUIEvent()
    data object OnNavigationBackButtonClick : SettingsScreenUIEvent()
    data object OnOpenSourceLicensesListItemClick : SettingsScreenUIEvent()
    data object OnRecalculateTotalListItemClick : SettingsScreenUIEvent()
    data object OnReminderEnabled : SettingsScreenUIEvent()
    data object OnReminderDisabled : SettingsScreenUIEvent()
    data object OnRestoreDataListItemClick : SettingsScreenUIEvent()
    data object OnSnackbarDismissed : SettingsScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : SettingsScreenUIEvent()
    data object OnTransactionForListItemClick : SettingsScreenUIEvent()
}
