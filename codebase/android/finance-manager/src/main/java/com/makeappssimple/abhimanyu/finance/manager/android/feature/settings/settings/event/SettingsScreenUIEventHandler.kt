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

import android.net.Uri
import android.os.Build
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.settings.state.SettingsScreenUIStateEvents

internal class SettingsScreenUIEventHandler internal constructor(
    private val hasNotificationPermission: Boolean,
    private val uiStateEvents: SettingsScreenUIStateEvents,
    private val createDocument: ((uri: Uri?) -> Unit) -> Unit,
    private val openDocument: () -> Unit,
    private val requestNotificationsPermission: () -> Unit,
) : ScreenUIEventHandler<SettingsScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: SettingsScreenUIEvent,
    ) {
        when (uiEvent) {
            is SettingsScreenUIEvent.OnAccountsListItemClick -> {
                uiStateEvents.navigateToAccountsScreen()
            }

            is SettingsScreenUIEvent.OnBackupDataListItemClick -> {
                createDocument {

                }
            }

            is SettingsScreenUIEvent.OnCategoriesListItemClick -> {
                uiStateEvents.navigateToCategoriesScreen()
            }

            is SettingsScreenUIEvent.OnNavigationBackButtonClick -> {}

            is SettingsScreenUIEvent.OnOpenSourceLicensesListItemClick -> {
                uiStateEvents.navigateToOpenSourceLicensesScreen()
            }

            is SettingsScreenUIEvent.OnRecalculateTotalListItemClick -> {
                uiStateEvents.recalculateTotal()
            }

            is SettingsScreenUIEvent.OnRestoreDataListItemClick -> {
                openDocument()
            }

            is SettingsScreenUIEvent.OnSnackbarDismissed -> {
                uiStateEvents.resetScreenSnackbarType()
            }

            is SettingsScreenUIEvent.OnReminderEnabled -> {
                uiStateEvents.disableReminder()
            }

            is SettingsScreenUIEvent.OnReminderDisabled -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotificationPermission) {
                    requestNotificationsPermission()
                } else {
                    uiStateEvents.enableReminder()
                }
            }

            is SettingsScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is SettingsScreenUIEvent.OnTransactionForListItemClick -> {
                uiStateEvents.navigateToTransactionForValuesScreen()
            }
        }
    }
}
