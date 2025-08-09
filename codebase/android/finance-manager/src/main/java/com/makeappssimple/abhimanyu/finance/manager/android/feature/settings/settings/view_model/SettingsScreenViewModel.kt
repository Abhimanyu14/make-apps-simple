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
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.common.core.app_version.AppVersionKit
import com.makeappssimple.abhimanyu.common.core.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.common.core.extensions.orFalse
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.alarm.AlarmKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.common.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.common.RestoreDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.settings.bottom_sheet.SettingsScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.settings.snackbar.SettingsScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.settings.state.SettingsScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.settings.state.SettingsScreenUIStateEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class SettingsScreenViewModel(
    private val alarmKit: AlarmKit,
    private val appVersionKit: AppVersionKit,
    private val backupDataUseCase: BackupDataUseCase,
    private val coroutineScope: CoroutineScope,
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    private val recalculateTotalUseCase: RecalculateTotalUseCase,
    private val restoreDataUseCase: RestoreDataUseCase,
    internal val logKit: LogKit,
    @VisibleForTesting internal val navigationKit: NavigationKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    logKit = logKit,
    navigationKit = navigationKit,
) {
    // region initial data
    private var appVersion: String = ""
    private val isReminderEnabled: MutableStateFlow<Boolean> = MutableStateFlow(
        value = false,
    )
    // endregion

    // region UI state
    val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    val screenBottomSheetType: MutableStateFlow<SettingsScreenBottomSheetType> =
        MutableStateFlow(
            value = SettingsScreenBottomSheetType.None,
        )
    val screenSnackbarType: MutableStateFlow<SettingsScreenSnackbarType> =
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
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            resetScreenSnackbarType = ::resetScreenSnackbarType,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
            updateScreenSnackbarType = ::updateScreenSnackbarType,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        observeData()
        fetchData()
    }

    private fun fetchData() {
        getAppVersion()
    }

    private fun observeData() {
        observeForUiStateAndStateEvents()
        observeForReminder()
    }
    // endregion

    // region loading
    fun startLoading() {
        isLoading.update {
            true
        }
    }

    fun completeLoading() {
        isLoading.update {
            false
        }
    }

    fun <T> withLoading(
        block: () -> T,
    ): T {
        startLoading()
        val result = block()
        completeLoading()
        return result
    }

    suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T {
        startLoading()
        try {
            return block()
        } finally {
            completeLoading()
        }
    }
    // endregion

    // region state events
    fun disableReminder() {
        coroutineScope.launch {
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

    fun enableReminder() {
        coroutineScope.launch {
            alarmKit.scheduleReminderAlarm()
        }
    }

    fun recalculateTotal() {
        coroutineScope.launch {
            startLoading()
            recalculateTotalUseCase()
            navigationKit.navigateUp()
        }
    }

    fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedSettingsScreenBottomSheetType = SettingsScreenBottomSheetType.None,
        )
    }

    fun resetScreenSnackbarType() {
        updateScreenSnackbarType(
            updatedSettingsScreenSnackbarType = SettingsScreenSnackbarType.None,
        )
    }

    fun updateScreenBottomSheetType(
        updatedSettingsScreenBottomSheetType: SettingsScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedSettingsScreenBottomSheetType
        }
    }

    fun updateScreenSnackbarType(
        updatedSettingsScreenSnackbarType: SettingsScreenSnackbarType,
    ) {
        screenSnackbarType.update {
            updatedSettingsScreenSnackbarType
        }
    }
    // endregion

    // region backupDataToDocument
    internal fun backupDataToDocument(
        uri: Uri,
    ) {
        viewModelScope.launch {
            startLoading()
            val isBackupSuccessful = backupDataUseCase(
                uri = uri,
            )
            if (isBackupSuccessful) {
                navigationKit.navigateUp()
            } else {
                // TODO(Abhi): use the result to show snackbar to the user
            }
        }
    }
    // endregion

    // region restoreDataFromDocument
    internal fun restoreDataFromDocument(
        uri: Uri,
    ) {
        viewModelScope.launch {
            startLoading()
            if (restoreDataUseCase(
                    uri = uri,
                )
            ) {
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

    // region getAppVersion
    private fun getAppVersion() {
        appVersion = appVersionKit.getAppVersion()?.versionName.orEmpty()
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                screenSnackbarType,
                isReminderEnabled,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        screenSnackbarType,
                        isReminderEnabled,
                    ),
                ->
                uiState.update {
                    SettingsScreenUIState(
                        isBottomSheetVisible = screenBottomSheetType != SettingsScreenBottomSheetType.None,
                        isLoading = isLoading,
                        isReminderEnabled = isReminderEnabled,
                        screenBottomSheetType = screenBottomSheetType,
                        screenSnackbarType = screenSnackbarType,
                        appVersion = appVersion,
                    )
                }
            }
        }
    }
    // endregion

    // region observeForReminder
    private fun observeForReminder() {
        viewModelScope.launch {
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
