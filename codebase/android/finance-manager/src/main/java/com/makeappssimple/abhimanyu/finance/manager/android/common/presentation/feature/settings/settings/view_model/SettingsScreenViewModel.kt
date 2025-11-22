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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.settings.settings.view_model

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.common.core.app_version.AppVersionKit
import com.makeappssimple.abhimanyu.common.core.coroutines.getCompletedJob
import com.makeappssimple.abhimanyu.common.core.extensions.orFalse
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.alarm.AlarmKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.common.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.common.RestoreDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.settings.settings.snackbar.SettingsScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.settings.settings.state.SettingsScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.settings.settings.state.SettingsScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.navigation.NavigationKit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class SettingsScreenViewModel(
    private val alarmKit: AlarmKit,
    private val appVersionKit: AppVersionKit,
    private val backupDataUseCase: BackupDataUseCase,
    private val coroutineScope: CoroutineScope,
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    private val navigationKit: NavigationKit,
    private val recalculateTotalUseCase: RecalculateTotalUseCase,
    private val restoreDataUseCase: RestoreDataUseCase,
    internal val dateTimeKit: DateTimeKit,
    internal val logKit: LogKit,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit {
    // region initial data
    private var isLoading: Boolean = true
    private var appVersion: String = ""
    private var isReminderEnabled: Boolean = false
    private var screenSnackbarType: SettingsScreenSnackbarType =
        SettingsScreenSnackbarType.None
    // endregion

    // region uiState
    private val _uiState: MutableStateFlow<SettingsScreenUIState> =
        MutableStateFlow(
            value = SettingsScreenUIState(),
        )
    internal val uiState: StateFlow<SettingsScreenUIState> =
        _uiState.asStateFlow()
    // endregion

    // region uiStateEvents
    internal val uiStateEvents: SettingsScreenUIStateEvents =
        SettingsScreenUIStateEvents(
            disableReminder = ::disableReminder,
            enableReminder = ::enableReminder,
            navigateToAccountsScreen = navigationKit::navigateToAccountsScreen,
            navigateToCategoriesScreen = navigationKit::navigateToCategoriesScreen,
            navigateToOpenSourceLicensesScreen = navigationKit::navigateToOpenSourceLicensesScreen,
            navigateToTransactionForValuesScreen = navigationKit::navigateToTransactionForValuesScreen,
            navigateUp = navigationKit::navigateUp,
            recalculateTotal = ::recalculateTotal,
            resetScreenSnackbarType = ::resetScreenSnackbarType,
            updateScreenSnackbarType = ::updateScreenSnackbarType,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        coroutineScope.launch {
            fetchData()
            completeLoading()
        }
        observeData()
    }
    // endregion

    // region refreshUiState
    private fun refreshUiState(): Job {
        return coroutineScope.launch {
            updateUiState()
        }
    }

    private fun updateUiState() {
        logError(
            tag = "Abhi",
            message = "SettingsScreenViewModel: updateUiState",
        )
        _uiState.update {
            SettingsScreenUIState(
                isLoading = isLoading,
                isReminderEnabled = isReminderEnabled,
                screenSnackbarType = screenSnackbarType,
                appVersion = appVersion,
            )
        }
    }
    // endregion

    // region fetchData
    private fun fetchData() {
        getAppVersion()
    }

    private fun getAppVersion() {
        appVersion = appVersionKit.getAppVersion()?.versionName.orEmpty()
    }
    // endregion

    // region observeData
    private fun observeData() {
        observeForReminder()
    }

    private fun observeForReminder() {
        coroutineScope.launch {
            financeManagerPreferencesRepository.getReminderFlow()
                .collectLatest { updatedReminder ->
                    isReminderEnabled = updatedReminder?.isEnabled.orFalse()
                    refreshUiState()
                }
        }
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
                navigationKit.navigateUp()
            } else {
                completeLoading()
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
            val isDataRestored = restoreDataUseCase(
                uri = uri,
            )
            if (isDataRestored) {
                navigationKit.navigateUp()
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
            navigationKit.navigateUp()
        }
    }

    private fun resetScreenSnackbarType(): Job {
        return updateScreenSnackbarType(
            updatedSettingsScreenSnackbarType = SettingsScreenSnackbarType.None,
        )
    }

    private fun updateScreenSnackbarType(
        updatedSettingsScreenSnackbarType: SettingsScreenSnackbarType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenSnackbarType = updatedSettingsScreenSnackbarType
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }
    // endregion

    // region loading
    private fun completeLoading() {
        isLoading = false
        refreshUiState()
    }

    private fun startLoading() {
        isLoading = true
        refreshUiState()
    }
    // endregion
}
