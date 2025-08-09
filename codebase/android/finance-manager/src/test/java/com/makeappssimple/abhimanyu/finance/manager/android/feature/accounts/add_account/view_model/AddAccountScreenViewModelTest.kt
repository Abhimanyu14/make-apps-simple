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

@file:OptIn(ExperimentalCoroutinesApi::class)

package com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.view_model

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.common.core.coroutines.test.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.log_kit.fake.FakeLogKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.account.AccountRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.InsertAccountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeAccountDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.FinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.fake.FakeFinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegateImpl
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.snackbar.AddAccountScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.state.AddAccountScreenNameError
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.use_case.AddAccountScreenDataValidationUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

internal class AddAccountScreenViewModelTest {
    // region coroutines setup
    private val testCoroutineDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(
        context = testCoroutineDispatcher + Job(),
    )
    private val testDispatcherProvider = TestDispatcherProviderImpl(
        testDispatcher = testCoroutineDispatcher,
    )
    // endregion

    // region test setup
    private val navigationKit: NavigationKit = NavigationKitImpl(
        coroutineScope = testScope.backgroundScope,
    )
    private val screenUIStateDelegate: ScreenUIStateDelegate =
        ScreenUIStateDelegateImpl(
            coroutineScope = testScope.backgroundScope,
        )
    private val fakeAccountDao: AccountDao = FakeAccountDaoImpl()
    private val accountRepository: AccountRepository = AccountRepositoryImpl(
        accountDao = fakeAccountDao,
        dispatcherProvider = testDispatcherProvider,
    )
    private val getAllAccountsUseCase: GetAllAccountsUseCase =
        GetAllAccountsUseCase(
            accountRepository = accountRepository,
        )
    private val addAccountScreenDataValidationUseCase: AddAccountScreenDataValidationUseCase =
        AddAccountScreenDataValidationUseCase(
            getAllAccountsUseCase = getAllAccountsUseCase,
        )
    private val financeManagerPreferencesDataSource: FinanceManagerPreferencesDataSource =
        FakeFinanceManagerPreferencesDataSource()
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository =
        FinanceManagerPreferencesRepositoryImpl(
            dispatcherProvider = testDispatcherProvider,
            financeManagerPreferencesDataSource = financeManagerPreferencesDataSource,
        )
    private val insertAccountUseCase: InsertAccountUseCase =
        InsertAccountUseCase(
            accountRepository = accountRepository,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    private val logKit: LogKit = FakeLogKitImpl()
    private lateinit var addAccountScreenViewModel: AddAccountScreenViewModel

    @Before
    fun setUp() {
        addAccountScreenViewModel = AddAccountScreenViewModel(
            navigationKit = navigationKit,
            screenUIStateDelegate = screenUIStateDelegate,
            addAccountScreenDataValidationUseCase = addAccountScreenDataValidationUseCase,
            coroutineScope = testScope.backgroundScope,
            insertAccountUseCase = insertAccountUseCase,
            logKit = logKit,
        )

        addAccountScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = runTestWithTimeout {
        addAccountScreenViewModel.uiState.test {
            val result = awaitItem()

            assertThat(result.selectedAccountType).isNull()
            assertThat(result.nameError).isEqualTo(AddAccountScreenNameError.None)
            assertThat(result.screenSnackbarType).isEqualTo(
                AddAccountScreenSnackbarType.None
            )
            assertThat(result.visibilityData.minimumBalanceAmountTextField).isFalse()
            assertThat(result.visibilityData.nameTextFieldErrorText).isFalse()
            assertThat(result.isCtaButtonEnabled).isFalse()
            assertThat(result.isLoading).isTrue()
            assertThat(result.selectedAccountTypeIndex).isEqualTo(0)
            assertThat(result.accountTypesChipUIDataList).isEmpty()
            assertThat(result.minimumAccountBalanceTextFieldValue.text).isEmpty()
            assertThat(result.nameTextFieldValue.text).isEmpty()
        }
    }
    // endregion

    // region state events
    @Test
    fun clearMinimumAccountBalanceAmountValue_shouldClearText() =
        runTestWithTimeout {
            val updatedMinimumAccountBalanceAmountValue = "1000"
            val updatedValue = TextFieldValue(
                text = updatedMinimumAccountBalanceAmountValue,
            )
            addAccountScreenViewModel.uiState.test {
                assertThat(awaitItem().minimumAccountBalanceTextFieldValue.text).isEmpty()
                addAccountScreenViewModel.uiStateEvents.updateMinimumAccountBalanceAmountValue(
                    updatedValue
                )
                assertThat(awaitItem().minimumAccountBalanceTextFieldValue.text).isEqualTo(
                    updatedMinimumAccountBalanceAmountValue
                )

                addAccountScreenViewModel.uiStateEvents.clearMinimumAccountBalanceAmountValue()

                assertThat(awaitItem().minimumAccountBalanceTextFieldValue.text).isEmpty()
            }
        }

    @Test
    fun clearName_shouldClearText() = runTestWithTimeout {
        val updatedName = "test-account"
        val updatedValue = TextFieldValue(
            text = updatedName,
        )
        addAccountScreenViewModel.uiState.test {
            assertThat(awaitItem().nameTextFieldValue.text).isEmpty()
            addAccountScreenViewModel.uiStateEvents.updateName(updatedValue)
            assertThat(awaitItem().nameTextFieldValue.text).isEqualTo(
                updatedName
            )

            addAccountScreenViewModel.uiStateEvents.clearName()

            assertThat(awaitItem().nameTextFieldValue.text).isEmpty()
        }
    }

//    @Test
//    fun insertAccount_withValidData_shouldInsertAndNavigateUp() =
//        runTestWithTimeout {
//            // Given
//            val accountName = TextFieldValue("Test Account")
//            val minimumBalance = TextFieldValue("1000")
//            addAccountScreenViewModel.uiStateEvents.updateName(accountName)
//            addAccountScreenViewModel.uiStateEvents.updateMinimumAccountBalanceAmountValue(
//                minimumBalance
//            )
//
//            addAccountScreenViewModel.uiStateEvents.insertAccount()
//
//            // Navigation verification would go here if we had access to navigation events
//            // For now, we can verify the account was attempted to be inserted by checking loading state
//            val result = addAccountScreenViewModel.uiState.first()
//            assertThat(result.isLoading).isFalse()
//        }
//
//    @Test
//    fun insertAccount_withInvalidData_shouldShowError() = runTestWithTimeout {
//        // Given
//        val accountName = TextFieldValue("")  // Invalid empty name
//        addAccountScreenViewModel.uiStateEvents.updateName(accountName)
//
//
//        addAccountScreenViewModel.uiStateEvents.insertAccount()
//
//
//        val result = addAccountScreenViewModel.uiState.first()
//        assertThat(result.isLoading).isFalse()
//        assertThat(result.screenSnackbarType).isEqualTo(
//            AddAccountScreenSnackbarType.SaveError
//        )
//    }
//
//    @Test
//    fun insertAccount_withValidData_shouldUpdateLoadingStates() =
//        runTestWithTimeout {
//            // Given
//            val accountName = TextFieldValue("Test Account")
//            addAccountScreenViewModel.uiStateEvents.updateName(accountName)
//
//            -Get initial loading state
//            val initialState = addAccountScreenViewModel.uiState.first()
//            assertThat(initialState.isLoading).isTrue()
//
//            -Insert account
//                    addAccountScreenViewModel.uiStateEvents.insertAccount()
//
//            -Check final loading state
//            val finalState = addAccountScreenViewModel.uiState.first()
//            assertThat(finalState.isLoading).isFalse()
//        }

    @Test
    fun resetScreenSnackbarType_shouldResetToNone() = runTestWithTimeout {
        addAccountScreenViewModel.uiState.test {
            assertThat(awaitItem().screenSnackbarType).isEqualTo(
                AddAccountScreenSnackbarType.None
            )

            addAccountScreenViewModel.uiStateEvents.resetScreenSnackbarType()

            assertThat(awaitItem().screenSnackbarType).isEqualTo(
                AddAccountScreenSnackbarType.None
            )
        }
    }

    @Test
    fun updateMinimumAccountBalanceAmountValue_shouldUpdateValue() =
        runTestWithTimeout {
            val updatedMinimumAccountBalanceAmountValue = "1000"
            val updatedValue = TextFieldValue(
                text = updatedMinimumAccountBalanceAmountValue,
            )
            addAccountScreenViewModel.uiState.test {
                assertThat(awaitItem().minimumAccountBalanceTextFieldValue.text).isEmpty()

                addAccountScreenViewModel.uiStateEvents.updateMinimumAccountBalanceAmountValue(
                    updatedValue
                )

                assertThat(awaitItem().minimumAccountBalanceTextFieldValue.text).isEqualTo(
                    updatedMinimumAccountBalanceAmountValue
                )
            }
        }

    @Test
    fun updateName_shouldUpdateValue() = runTestWithTimeout {
        val updatedName = "test-account"
        val updatedValue = TextFieldValue(
            text = updatedName,
        )
        addAccountScreenViewModel.uiState.test {
            assertThat(awaitItem().nameTextFieldValue.text).isEmpty()

            addAccountScreenViewModel.uiStateEvents.updateName(updatedValue)

            assertThat(awaitItem().nameTextFieldValue.text).isEqualTo(
                updatedName
            )
        }
    }

    @Test
    fun updateScreenSnackbarType_shouldUpdateType() = runTestWithTimeout {
        val updatedType = AddAccountScreenSnackbarType.None
        addAccountScreenViewModel.uiState.test {
            assertThat(awaitItem().screenSnackbarType).isEqualTo(
                AddAccountScreenSnackbarType.None
            )

            addAccountScreenViewModel.uiStateEvents.updateScreenSnackbarType(
                updatedType
            )

            assertThat(awaitItem().screenSnackbarType).isEqualTo(
                updatedType
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateSelectedAccountTypeIndex_shouldUpdateIndex() =
        runTestWithTimeout {
            val updatedIndex = 1
            addAccountScreenViewModel.uiState.test {
                assertThat(awaitItem().selectedAccountTypeIndex).isEqualTo(0)

                addAccountScreenViewModel.uiStateEvents.updateSelectedAccountTypeIndex(
                    updatedIndex
                )

                assertThat(awaitItem().selectedAccountTypeIndex).isEqualTo(
                    updatedIndex
                )
            }
        }
    // endregion

    private fun runTestWithTimeout(
        testBody: suspend TestScope.() -> Unit,
    ) {
        testScope.runTest(
            timeout = 3.seconds,
        ) {
            testBody()
        }
    }
}
