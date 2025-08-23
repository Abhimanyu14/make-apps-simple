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
import com.makeappssimple.abhimanyu.common.core.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Amount
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.FinanceManagerNavigationDirections
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.snackbar.AddAccountScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.state.AddAccountScreenNameError
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeZero
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
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

            result.selectedAccountType.shouldBeNull()
            result.nameError.shouldBe(
                expected = AddAccountScreenNameError.None,
            )
            result.screenSnackbarType.shouldBe(
                expected = AddAccountScreenSnackbarType.None,
            )
            result.visibilityData.minimumBalanceAmountTextField.shouldBeFalse()
            result.visibilityData.nameTextFieldErrorText.shouldBeFalse()
            result.isCtaButtonEnabled.shouldBeFalse()
            result.isLoading.shouldBeTrue()
            result.selectedAccountTypeIndex.shouldBeZero()
            result.accountTypesChipUIDataList.shouldBeEmpty()
            result.minimumAccountBalanceTextFieldValue.text.shouldBeEmpty()
            result.nameTextFieldValue.text.shouldBeEmpty()
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
                awaitItem().isCtaButtonEnabled.shouldBeFalse()

                addAccountScreenViewModel.uiStateEvents.updateName(updatedValue)

                val result = awaitItem()
                result.isCtaButtonEnabled.shouldBeFalse()
                result.nameError.shouldBe(
                    expected = AddAccountScreenNameError.None,
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
                awaitItem().isCtaButtonEnabled.shouldBeFalse()

                addAccountScreenViewModel.uiStateEvents.updateName(updatedValue)

                val result = awaitItem()
                result.isCtaButtonEnabled.shouldBeFalse()
                result.nameError.shouldBe(
                    expected = AddAccountScreenNameError.AccountExists,
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
                awaitItem().isCtaButtonEnabled.shouldBeFalse()

                addAccountScreenViewModel.uiStateEvents.updateName(updatedValue)

                val result = awaitItem()
                result.isCtaButtonEnabled.shouldBeFalse()
                result.nameError.shouldBe(
                    expected = AddAccountScreenNameError.AccountExists,
                )
            }
        }

    @Test
    fun updateUiStateAndStateEvents_accountAlreadyExists_ctaIsEnabled() =
        testDependencies.runTestWithTimeout {
            addAccountScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()

                addAccountScreenViewModel.uiStateEvents.updateName(
                    addAccountScreenViewModel.uiState.value.nameTextFieldValue.copy(
                        text = testDependencies.testAccountName1,
                    )
                )

                val result = awaitItem()
                result.nameTextFieldValue.text.shouldBe(
                    expected = testDependencies.testAccountName1,
                )
                result.isCtaButtonEnabled.shouldBeFalse()
                result.nameError.shouldBe(
                    expected = AddAccountScreenNameError.AccountExists,
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
                awaitItem().isCtaButtonEnabled.shouldBeFalse()

                addAccountScreenViewModel.uiStateEvents.updateName(updatedValue)

                val result = awaitItem()
                result.isCtaButtonEnabled.shouldBeTrue()
                result.nameError.shouldBe(
                    expected = AddAccountScreenNameError.None,
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
                awaitItem().minimumAccountBalanceTextFieldValue.text.shouldBeEmpty()
                addAccountScreenViewModel.uiStateEvents.updateMinimumAccountBalanceAmountValue(
                    updatedValue
                )
                awaitItem().minimumAccountBalanceTextFieldValue.text.shouldBe(
                    expected = updatedMinimumAccountBalanceAmountValue,
                )

                addAccountScreenViewModel.uiStateEvents.clearMinimumAccountBalanceAmountValue()

                awaitItem().minimumAccountBalanceTextFieldValue.text.shouldBeEmpty()
            }
        }

    @Test
    fun clearName_shouldClearText() = testDependencies.runTestWithTimeout {
        val updatedName = "test-account"
        val updatedValue = TextFieldValue(
            text = updatedName,
        )
        addAccountScreenViewModel.uiState.test {
            awaitItem().nameTextFieldValue.text.shouldBeEmpty()
            addAccountScreenViewModel.uiStateEvents.updateName(updatedValue)
            awaitItem().nameTextFieldValue.text.shouldBe(
                expected = updatedName,
            )

            addAccountScreenViewModel.uiStateEvents.clearName()

            awaitItem().nameTextFieldValue.text.shouldBeEmpty()
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
                uiStateTurbine.awaitItem().isLoading.shouldBeTrue()
                uiStateTurbine.awaitItem().isLoading.shouldBeFalse()
                addAccountScreenViewModel.uiStateEvents.updateName(
                    testAccountName
                )
                uiStateTurbine.awaitItem().isLoading.shouldBeFalse()
                addAccountScreenViewModel.uiStateEvents.updateMinimumAccountBalanceAmountValue(
                    testMinimumAccountBalanceAmount
                )
                uiStateTurbine.awaitItem().isLoading.shouldBeFalse()

                addAccountScreenViewModel.uiStateEvents.insertAccount()

                uiStateTurbine.awaitItem().isLoading.shouldBeTrue()
                testDependencies.fakeAccountDao.getAllAccounts()
                    .find {
                        it.id == testAccountId
                    }
                    ?.asExternalModel().shouldBe(
                        expected = testAccount,
                    )
                (testDependencies.financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
                    ?: -1L).shouldBeGreaterThan(
                    other = lastChangeTimestamp,
                )
                navigationCommandTurbine.awaitItem().shouldBe(
                    expected = FinanceManagerNavigationDirections.NavigateUp,
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
                uiStateTurbine.awaitItem().isLoading.shouldBeTrue()
                uiStateTurbine.awaitItem().isLoading.shouldBeFalse()
                addAccountScreenViewModel.uiStateEvents.updateName(
                    testAccountName
                )
                uiStateTurbine.awaitItem().isLoading.shouldBeFalse()
                addAccountScreenViewModel.uiStateEvents.updateMinimumAccountBalanceAmountValue(
                    testMinimumAccountBalanceAmount
                )
                uiStateTurbine.awaitItem().isLoading.shouldBeFalse()
                addAccountScreenViewModel.uiStateEvents.updateSelectedAccountTypeIndex(
                    testSelectedAccountTypeIndex
                )
                uiStateTurbine.awaitItem().isLoading.shouldBeFalse()

                addAccountScreenViewModel.uiStateEvents.insertAccount()

                uiStateTurbine.awaitItem().isLoading.shouldBeTrue()
                testDependencies.fakeAccountDao.getAllAccounts()
                    .find {
                        it.id == 1
                    }
                    ?.asExternalModel().shouldBe(
                        expected = testAccount,
                    )
                (testDependencies.financeManagerPreferencesRepository.getDataTimestamp()?.lastChange
                    ?: -1L).shouldBeGreaterThan(
                    other = lastChangeTimestamp,
                )
                navigationCommandTurbine.awaitItem().shouldBe(
                    expected = FinanceManagerNavigationDirections.NavigateUp,
                )
            }
        }

    @Test
    fun resetScreenSnackbarType_shouldResetToNone() =
        testDependencies.runTestWithTimeout {
            addAccountScreenViewModel.uiState.test {
                awaitItem().screenSnackbarType.shouldBe(
                    expected = AddAccountScreenSnackbarType.None,
                )

                addAccountScreenViewModel.uiStateEvents.resetScreenSnackbarType()

                awaitItem().screenSnackbarType.shouldBe(
                    expected = AddAccountScreenSnackbarType.None,
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
                awaitItem().minimumAccountBalanceTextFieldValue.text.shouldBeEmpty()

                addAccountScreenViewModel.uiStateEvents.updateMinimumAccountBalanceAmountValue(
                    updatedValue
                )

                awaitItem().minimumAccountBalanceTextFieldValue.text.shouldBe(
                    expected = updatedMinimumAccountBalanceAmountValue,
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
            awaitItem().nameTextFieldValue.text.shouldBeEmpty()

            addAccountScreenViewModel.uiStateEvents.updateName(updatedValue)

            awaitItem().nameTextFieldValue.text.shouldBe(
                expected = updatedName,
            )
        }
    }

    @Test
    fun updateScreenSnackbarType_shouldUpdateType() =
        testDependencies.runTestWithTimeout {
            val updatedType = AddAccountScreenSnackbarType.None
            addAccountScreenViewModel.uiState.test {
                awaitItem().screenSnackbarType.shouldBe(
                    expected = AddAccountScreenSnackbarType.None,
                )

                addAccountScreenViewModel.uiStateEvents.updateScreenSnackbarType(
                    updatedType
                )

                awaitItem().screenSnackbarType.shouldBe(
                    expected = updatedType,
                )
            }
        }

    @Test
    fun updateSelectedAccountTypeIndex_shouldUpdateIndex() =
        testDependencies.runTestWithTimeout {
            val updatedIndex = 1
            addAccountScreenViewModel.uiState.test {
                awaitItem().selectedAccountTypeIndex.shouldBe(
                    expected = 0,
                )

                addAccountScreenViewModel.uiStateEvents.updateSelectedAccountTypeIndex(
                    updatedIndex
                )

                awaitItem().selectedAccountTypeIndex.shouldBe(
                    expected = updatedIndex,
                )
            }
        }
    // endregion
}
