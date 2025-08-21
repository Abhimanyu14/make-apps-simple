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
import com.makeappssimple.abhimanyu.common.core.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Amount
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.FinanceManagerNavigationDirections
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.snackbar.AddAccountScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.state.AddAccountScreenNameError
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class AddAccountScreenViewModelTest {
    // region test setup
    private lateinit var addAccountScreenViewModel: AddAccountScreenViewModel
    private lateinit var testDependencies: TestDependencies

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        addAccountScreenViewModel = AddAccountScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            screenUIStateDelegate = testDependencies.screenUIStateDelegate,
            addAccountScreenDataValidationUseCase = testDependencies.addAccountScreenDataValidationUseCase,
            coroutineScope = testDependencies.testScope.backgroundScope,
            insertAccountUseCase = testDependencies.insertAccountUseCase,
            logKit = testDependencies.logKit,
        )
        addAccountScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testDependencies.testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = testDependencies.runTestWithTimeout {
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
        testDependencies.runTestWithTimeout {
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
        testDependencies.runTestWithTimeout {
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
        testDependencies.runTestWithTimeout {
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
        testDependencies.runTestWithTimeout {
            addAccountScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()

                addAccountScreenViewModel.uiStateEvents.updateName(
                    addAccountScreenViewModel.uiState.value.nameTextFieldValue.copy(
                        text = testDependencies.testAccountName1,
                    )
                )

                val result = awaitItem()
                assertThat(result.nameTextFieldValue.text).isEqualTo(
                    testDependencies.testAccountName1
                )
                assertThat(result.isCtaButtonEnabled).isFalse()
                assertThat(result.nameError).isEqualTo(
                    AddAccountScreenNameError.AccountExists
                )
            }
        }

    @Test
    fun updateUiStateAndStateEvents_accountIsValid_ctaIsEnabled() =
        testDependencies.runTestWithTimeout {
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
        testDependencies.runTestWithTimeout {
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
    fun clearName_shouldClearText() = testDependencies.runTestWithTimeout {
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
        testDependencies.runTestWithTimeout {
            turbineScope {
                val navigationCommandTurbine =
                    testDependencies.navigationKit.command.testIn(
                        scope = backgroundScope,
                    )
                val uiStateTurbine = addAccountScreenViewModel.uiState.testIn(
                    scope = backgroundScope,
                )
                val lastChangeTimestamp =
                    testDependencies.financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
                        ?: -1L

                val testAccountId = 1
                val testAccountName = TextFieldValue(
                    text = "test-bank",
                )
                val testMinimumAccountBalanceAmount = TextFieldValue(
                    text = "1000",
                )
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
                    testDependencies.fakeAccountDao.getAllAccounts()
                        .find {
                            it.id == testAccountId
                        }
                        ?.asExternalModel()
                ).isEqualTo(
                    testAccount
                )
                assertThat(
                    testDependencies.financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
                        ?: -1L
                ).isGreaterThan(lastChangeTimestamp)
                assertThat(navigationCommandTurbine.awaitItem()).isEqualTo(
                    FinanceManagerNavigationDirections.NavigateUp
                )
            }
        }

    @Test
    fun insertAccount_eWallet_shouldInsertAndNavigateUp() =
        testDependencies.runTestWithTimeout {
            turbineScope {
                val navigationCommandTurbine =
                    testDependencies.navigationKit.command.testIn(
                        scope = backgroundScope,
                    )
                val uiStateTurbine = addAccountScreenViewModel.uiState.testIn(
                    scope = backgroundScope,
                )
                val lastChangeTimestamp =
                    testDependencies.financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
                        ?: -1L

                val testAccountId = 1
                val testAccountName = TextFieldValue(
                    text = "test-wallet",
                )
                val testMinimumAccountBalanceAmount = TextFieldValue(
                    text = "1000",
                )
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
                    testDependencies.fakeAccountDao.getAllAccounts()
                        .find {
                            it.id == 1
                        }
                        ?.asExternalModel()
                ).isEqualTo(
                    testAccount
                )
                assertThat(
                    testDependencies.financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
                        ?: -1L
                ).isGreaterThan(lastChangeTimestamp)
                assertThat(navigationCommandTurbine.awaitItem()).isEqualTo(
                    FinanceManagerNavigationDirections.NavigateUp
                )
            }
        }

    @Test
    fun resetScreenSnackbarType_shouldResetToNone() =
        testDependencies.runTestWithTimeout {
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
        testDependencies.runTestWithTimeout {
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
    fun updateName_shouldUpdateValue() = testDependencies.runTestWithTimeout {
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
    fun updateScreenSnackbarType_shouldUpdateType() =
        testDependencies.runTestWithTimeout {
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
        testDependencies.runTestWithTimeout {
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
}
