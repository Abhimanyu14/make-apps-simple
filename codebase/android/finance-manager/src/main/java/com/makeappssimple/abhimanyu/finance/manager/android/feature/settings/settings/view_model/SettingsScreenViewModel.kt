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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.settings.view_model

import android.net.Uri
import com.makeappssimple.abhimanyu.common.core.app_version.AppVersionKit
import com.makeappssimple.abhimanyu.common.core.extensions.orFalse
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.alarm.AlarmKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.common.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.common.RestoreDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.settings.snackbar.SettingsScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.settings.state.SettingsScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.settings.state.SettingsScreenUIStateEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class SettingsScreenViewModel(
    navigationKit: NavigationKit,
    screenUIStateDelegate: ScreenUIStateDelegate,
    private val alarmKit: AlarmKit,
    private val appVersionKit: AppVersionKit,
    private val backupDataUseCase: BackupDataUseCase,
    private val coroutineScope: CoroutineScope,
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    private val recalculateTotalUseCase: RecalculateTotalUseCase,
    private val restoreDataUseCase: RestoreDataUseCase,
    internal val logKit: LogKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    logKit = logKit,
    navigationKit = navigationKit,
    screenUIStateDelegate = screenUIStateDelegate,
) {
    // region initial data
    private var appVersion: String = ""
    private val isReminderEnabled: MutableStateFlow<Boolean> = MutableStateFlow(
        value = false,
    )
    // endregion

    // region UI state
    private val screenSnackbarType: MutableStateFlow<SettingsScreenSnackbarType> =
        MutableStateFlow(
            value = SettingsScreenSnackbarType.None,
        )
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<SettingsScreenUIState> =
        MutableStateFlow(
            value = SettingsScreenUIState(),
        )
    internal val uiStateEvents: SettingsScreenUIStateEvents =
        SettingsScreenUIStateEvents(
            disableReminder = ::disableReminder,
            enableReminder = ::enableReminder,
            navigateToAccountsScreen = ::navigateToAccountsScreen,
            navigateToCategoriesScreen = ::navigateToCategoriesScreen,
            navigateToOpenSourceLicensesScreen = ::navigateToOpenSourceLicensesScreen,
            navigateToTransactionForValuesScreen = ::navigateToTransactionForValuesScreen,
            navigateUp = ::navigateUp,
            recalculateTotal = ::recalculateTotal,
            resetScreenSnackbarType = ::resetScreenSnackbarType,
            updateScreenSnackbarType = ::updateScreenSnackbarType,
        )
    // endregion

    // region updateUiStateAndStateEvents
    override fun updateUiStateAndStateEvents() {
        // TODO(Abhi): Fix isLoading, isReminderEnabled and screenSnackbarType
        uiState.update {
            SettingsScreenUIState(
                isLoading = isLoading,
                isReminderEnabled = isReminderEnabled.value,
                screenSnackbarType = screenSnackbarType.value,
                appVersion = appVersion,
            )
        }
    }
    // endregion

    // region fetchData
    override fun fetchData(): Job {
        return coroutineScope.launch {
            getAppVersion()
        }
    }
    // endregion

    // region observeData
    override fun observeData() {
        observeForReminder()
    }
    // endregion

    // region backupDataToDocument
    internal fun backupDataToDocument(
        uri: Uri,
    ): Job {
        return coroutineScope.launch {
            startLoading()
            val isBackupSuccessful = backupDataUseCase(
                uri = uri,
            )
            if (isBackupSuccessful) {
                navigateUp()
            } else {
                // TODO(Abhi): use the result to show snackbar to the user
            }
        }
    }
    // endregion

    // region restoreDataFromDocument
    internal fun restoreDataFromDocument(
        uri: Uri,
    ): Job {
        return coroutineScope.launch {
            startLoading()
            if (restoreDataUseCase(
                    uri = uri,
                )
            ) {
                navigateUp()
            } else {
                completeLoading()
                updateScreenSnackbarType(
                    updatedSettingsScreenSnackbarType = SettingsScreenSnackbarType.RestoreDataFailed,
                )
            }
        }
    }
    // endregion

    // region state events
    private fun disableReminder(): Job {
        return coroutineScope.launch {
            if (alarmKit.cancelReminderAlarm()) {
                updateScreenSnackbarType(
                    updatedSettingsScreenSnackbarType = SettingsScreenSnackbarType.CancelReminderSuccessful,
                )
            } else {
                updateScreenSnackbarType(
                    updatedSettingsScreenSnackbarType = SettingsScreenSnackbarType.CancelReminderFailed,
                )
            }
        }
    }

    private fun enableReminder(): Job {
        return coroutineScope.launch {
            alarmKit.scheduleReminderAlarm()
        }
    }

    private fun recalculateTotal(): Job {
        return coroutineScope.launch {
            startLoading()
            recalculateTotalUseCase()
            navigateUp()
        }
    }

    private fun resetScreenSnackbarType(): Job {
        return updateScreenSnackbarType(
            updatedSettingsScreenSnackbarType = SettingsScreenSnackbarType.None,
        )
    }

    private fun updateScreenSnackbarType(
        updatedSettingsScreenSnackbarType: SettingsScreenSnackbarType,
    ): Job {
        screenSnackbarType.update {
            updatedSettingsScreenSnackbarType
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }
    // endregion

    // region getAppVersion
    private fun getAppVersion() {
        appVersion = appVersionKit.getAppVersion()?.versionName.orEmpty()
    }
    // endregion

    // region observeForReminder
    private fun observeForReminder() {
        coroutineScope.launch {
            financeManagerPreferencesRepository.getReminderFlow()
                .collectLatest { updatedReminder ->
                    startLoading()
                    isReminderEnabled.update {
                        updatedReminder?.isEnabled.orFalse()
                    }
                    completeLoading()
                }
        }
    }
    // endregion
}
