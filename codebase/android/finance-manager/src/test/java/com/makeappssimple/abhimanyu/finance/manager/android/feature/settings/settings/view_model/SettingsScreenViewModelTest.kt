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
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.AmountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.settings.snackbar.SettingsScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class SettingsScreenViewModelTest {
    // region test setup
    private lateinit var testDependencies: TestDependencies
    private lateinit var settingsScreenViewModel: SettingsScreenViewModel

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        testDependencies.testScope.launch {
            testDependencies.fakeAccountDao.insertAccounts(
                AccountEntity(
                    balanceAmount = AmountEntity(
                        value = 1000,
                    ),
                    id = 1,
                    type = AccountType.E_WALLET,
                    name = "test-account",
                ),
            )
            testDependencies.fakeCategoryDao.insertCategories(
                CategoryEntity(
                    id = 1,
                    emoji = "💳",
                    title = "test-category",
                    transactionType = TransactionType.EXPENSE,
                ),
            )
            testDependencies.fakeTransactionForDao.insertTransactionForValues(
                TransactionForEntity(
                    id = 1,
                    title = "test-transaction-for",
                ),
            )
            testDependencies.fakeTransactionDao.insertTransaction(
                TransactionEntity(
                    amount = AmountEntity(
                        value = 100,
                    ),
                    categoryId = 1,
                    id = 123,
                    accountFromId = 1,
                    transactionForId = 1,
                    creationTimestamp = 100L,
                    transactionTimestamp = 100L,
                    title = "test-transaction",
                ),
            )
        }
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
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
                assertThat(postDataFetchCompletion.screenSnackbarType).isEqualTo(
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
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
                assertThat(postDataFetchCompletion.screenSnackbarType).isEqualTo(
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
