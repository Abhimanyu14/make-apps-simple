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
import app.cash.turbine.turbineScope
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.common.core.coroutines.test.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.common.core.extensions.toLongOrZero
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
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.FinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.fake.FakeFinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Amount
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.FinanceManagerNavigationDirections
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
    private val getAllAccountsUseCase = GetAllAccountsUseCase(
        accountRepository = accountRepository,
    )
    private val addAccountScreenDataValidationUseCase =
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
    private val insertAccountUseCase = InsertAccountUseCase(
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

    // region updateUiStateAndStateEvents
    @Test
    fun updateUiStateAndStateEvents_nameIsBlank_ctaIsDisabled() =
        runTestWithTimeout {
            val updatedName = "   "
            val updatedValue = TextFieldValue(
                text = updatedName,
            )
            addAccountScreenViewModel.uiState.test {
                assertThat(awaitItem().isCtaButtonEnabled).isFalse()

                addAccountScreenViewModel.uiStateEvents.updateName(updatedValue)

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isFalse()
                assertThat(result.nameError).isEqualTo(
                    AddAccountScreenNameError.None
                )
            }
        }

    @Test
    fun updateUiStateAndStateEvents_nameIsCash_ctaIsDisabled() =
        runTestWithTimeout {
            val updatedName = "Cash"
            val updatedValue = TextFieldValue(
                text = updatedName,
            )
            addAccountScreenViewModel.uiState.test {
                assertThat(awaitItem().isCtaButtonEnabled).isFalse()

                addAccountScreenViewModel.uiStateEvents.updateName(updatedValue)

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isFalse()
                assertThat(result.nameError).isEqualTo(
                    AddAccountScreenNameError.AccountExists
                )
            }
        }

    @Test
    fun updateUiStateAndStateEvents_nameIsCashWithSpaces_ctaIsDisabled() =
        runTestWithTimeout {
            val updatedName = "  Cash   "
            val updatedValue = TextFieldValue(
                text = updatedName,
            )
            addAccountScreenViewModel.uiState.test {
                assertThat(awaitItem().isCtaButtonEnabled).isFalse()

                addAccountScreenViewModel.uiStateEvents.updateName(updatedValue)

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isFalse()
                assertThat(result.nameError).isEqualTo(
                    AddAccountScreenNameError.AccountExists
                )
            }
        }

    @Test
    fun updateUiStateAndStateEvents_accountAlreadyExists_ctaIsEnabled() =
        runTestWithTimeout {
            val updatedName1 = "CUB"
            val updatedValue1 = TextFieldValue(
                text = updatedName1,
            )
            val updatedName2 = "IOB"
            val updatedValue2 = TextFieldValue(
                text = updatedName2,
            )
            addAccountScreenViewModel.uiState.test {
                assertThat(awaitItem().isCtaButtonEnabled).isFalse()
                addAccountScreenViewModel.uiStateEvents.updateName(updatedValue1)
                assertThat(awaitItem().nameTextFieldValue.text).isEqualTo(
                    updatedName1
                )
                addAccountScreenViewModel.uiStateEvents.insertAccount().join()
                addAccountScreenViewModel.uiStateEvents.updateName(updatedValue2)
                assertThat(awaitItem().nameTextFieldValue.text).isEqualTo(
                    updatedName2
                )

                addAccountScreenViewModel.uiStateEvents.updateName(updatedValue1)

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isFalse()
                assertThat(result.nameError).isEqualTo(
                    AddAccountScreenNameError.AccountExists
                )
            }
        }

    @Test
    fun updateUiStateAndStateEvents_accountIsValid_ctaIsEnabled() =
        runTestWithTimeout {
            val updatedName = "CUB"
            val updatedValue = TextFieldValue(
                text = updatedName,
            )
            addAccountScreenViewModel.uiState.test {
                assertThat(awaitItem().isCtaButtonEnabled).isFalse()

                addAccountScreenViewModel.uiStateEvents.updateName(updatedValue)

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isTrue()
                assertThat(result.nameError).isEqualTo(
                    AddAccountScreenNameError.None
                )
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

    @Test
    fun insertAccount_bank_shouldInsertAndNavigateUp() =
        runTestWithTimeout {
            turbineScope {
                val navigationCommandTurbine = navigationKit.command.testIn(
                    scope = backgroundScope,
                )
                val uiStateTurbine = addAccountScreenViewModel.uiState.testIn(
                    scope = backgroundScope,
                )
                val lastChangeTimestamp =
                    financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
                        ?: -1L

                val testAccountId = 1
                val testAccountName = TextFieldValue("test-bank")
                val testMinimumAccountBalanceAmount = TextFieldValue("1000")
                val testAccountType = AccountType.BANK
                val testAccount = Account(
                    id = testAccountId,
                    type = testAccountType,
                    minimumAccountBalanceAmount = Amount(
                        value = testMinimumAccountBalanceAmount.text.toLongOrZero(),
                    ),
                    name = testAccountName.text,
                )
                assertThat(uiStateTurbine.awaitItem().isLoading).isTrue()
                assertThat(uiStateTurbine.awaitItem().isLoading).isFalse()
                addAccountScreenViewModel.uiStateEvents.updateName(
                    testAccountName
                )
                assertThat(uiStateTurbine.awaitItem().isLoading).isFalse()
                addAccountScreenViewModel.uiStateEvents.updateMinimumAccountBalanceAmountValue(
                    testMinimumAccountBalanceAmount
                )
                assertThat(uiStateTurbine.awaitItem().isLoading).isFalse()

                addAccountScreenViewModel.uiStateEvents.insertAccount()

                assertThat(uiStateTurbine.awaitItem().isLoading).isTrue()
                assertThat(
                    fakeAccountDao.getAllAccounts().first().asExternalModel()
                ).isEqualTo(
                    testAccount
                )
                assertThat(
                    financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
                        ?: -1L
                ).isGreaterThan(lastChangeTimestamp)
                assertThat(navigationCommandTurbine.awaitItem()).isEqualTo(
                    FinanceManagerNavigationDirections.NavigateUp
                )
            }
        }

    @Test
    fun insertAccount_eWallet_shouldInsertAndNavigateUp() =
        runTestWithTimeout {
            turbineScope {
                val navigationCommandTurbine = navigationKit.command.testIn(
                    scope = backgroundScope,
                )
                val uiStateTurbine = addAccountScreenViewModel.uiState.testIn(
                    scope = backgroundScope,
                )
                val lastChangeTimestamp =
                    financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
                        ?: -1L

                val testAccountId = 1
                val testAccountName = TextFieldValue("test-wallet")
                val testMinimumAccountBalanceAmount = TextFieldValue("1000")
                val testAccountType = AccountType.E_WALLET
                val testSelectedAccountTypeIndex = 1
                val testAccount = Account(
                    id = testAccountId,
                    type = testAccountType,
                    minimumAccountBalanceAmount = null,
                    name = testAccountName.text,
                )
                assertThat(uiStateTurbine.awaitItem().isLoading).isTrue()
                assertThat(uiStateTurbine.awaitItem().isLoading).isFalse()
                addAccountScreenViewModel.uiStateEvents.updateName(
                    testAccountName
                )
                assertThat(uiStateTurbine.awaitItem().isLoading).isFalse()
                addAccountScreenViewModel.uiStateEvents.updateMinimumAccountBalanceAmountValue(
                    testMinimumAccountBalanceAmount
                )
                assertThat(uiStateTurbine.awaitItem().isLoading).isFalse()
                addAccountScreenViewModel.uiStateEvents.updateSelectedAccountTypeIndex(
                    testSelectedAccountTypeIndex
                )
                assertThat(uiStateTurbine.awaitItem().isLoading).isFalse()

                addAccountScreenViewModel.uiStateEvents.insertAccount()

                assertThat(uiStateTurbine.awaitItem().isLoading).isTrue()
                assertThat(
                    fakeAccountDao.getAllAccounts().first().asExternalModel()
                ).isEqualTo(
                    testAccount
                )
                assertThat(
                    financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
                        ?: -1L
                ).isGreaterThan(lastChangeTimestamp)
                assertThat(navigationCommandTurbine.awaitItem()).isEqualTo(
                    FinanceManagerNavigationDirections.NavigateUp
                )
            }
        }

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

    // region common
    private fun runTestWithTimeout(
        testBody: suspend TestScope.() -> Unit,
    ) {
        testScope.runTest(
            timeout = 3.seconds,
        ) {
            testBody()
        }
    }
    // endregion
}
