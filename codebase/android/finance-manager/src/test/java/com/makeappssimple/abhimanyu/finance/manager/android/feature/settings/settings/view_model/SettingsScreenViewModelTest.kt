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

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.settings.snackbar.SettingsScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class SettingsScreenViewModelTest {
    // region test setup
    private lateinit var settingsScreenViewModel: SettingsScreenViewModel
    private lateinit var testDependencies: TestDependencies

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        settingsScreenViewModel = SettingsScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            screenUIStateDelegate = testDependencies.screenUIStateDelegate,
            alarmKit = testDependencies.alarmKit,
            appVersionKit = testDependencies.appVersionKit,
            backupDataUseCase = testDependencies.backupDataUseCase,
            coroutineScope = testDependencies.testScope.backgroundScope,
            financeManagerPreferencesRepository = testDependencies.financeManagerPreferencesRepository,
            recalculateTotalUseCase = testDependencies.recalculateTotalUseCase,
            restoreDataUseCase = testDependencies.restoreDataUseCase,
            logKit = testDependencies.logKit,
        )
        settingsScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testDependencies.testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = testDependencies.runTestWithTimeout {
        settingsScreenViewModel.uiState.test {
            val result = awaitItem()

            assertThat(result.isLoading).isTrue()
            assertThat(result.isReminderEnabled).isNull()
            assertThat(result.screenSnackbarType).isEqualTo(
                SettingsScreenSnackbarType.None
            )
            assertThat(result.appVersion).isNull()
        }
    }
    // endregion

    // region updateUiStateAndStateEvents
    // endregion

    // region state events
    @Test
    fun resetScreenSnackbarType_shouldSetSnackbarTypeToNone() =
        testDependencies.runTestWithTimeout {
            settingsScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.screenSnackbarType).isEqualTo(
                    SettingsScreenSnackbarType.None
                )
                val fetchDataCompletedState = awaitItem()
                assertThat(fetchDataCompletedState.isLoading).isFalse()
                assertThat(fetchDataCompletedState.screenSnackbarType).isEqualTo(
                    SettingsScreenSnackbarType.None
                )
                settingsScreenViewModel.uiStateEvents.updateScreenSnackbarType(
                    SettingsScreenSnackbarType.CancelReminderSuccessful
                )
                assertThat(awaitItem().screenSnackbarType).isEqualTo(
                    SettingsScreenSnackbarType.CancelReminderSuccessful
                )

                settingsScreenViewModel.uiStateEvents.resetScreenSnackbarType()

                assertThat(awaitItem().screenSnackbarType).isEqualTo(
                    SettingsScreenSnackbarType.None
                )
            }
        }

    @Test
    fun updateScreenSnackbarType_shouldUpdateScreenSnackbarType() =
        testDependencies.runTestWithTimeout {
            settingsScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.screenSnackbarType).isEqualTo(
                    SettingsScreenSnackbarType.None
                )
                val fetchDataCompletedState = awaitItem()
                assertThat(fetchDataCompletedState.isLoading).isFalse()
                assertThat(fetchDataCompletedState.screenSnackbarType).isEqualTo(
                    SettingsScreenSnackbarType.None
                )

                settingsScreenViewModel.uiStateEvents.updateScreenSnackbarType(
                    SettingsScreenSnackbarType.CancelReminderSuccessful
                )

                val result = awaitItem()
                assertThat(result.screenSnackbarType).isEqualTo(
                    SettingsScreenSnackbarType.CancelReminderSuccessful
                )
            }
        }
    // endregion
}
