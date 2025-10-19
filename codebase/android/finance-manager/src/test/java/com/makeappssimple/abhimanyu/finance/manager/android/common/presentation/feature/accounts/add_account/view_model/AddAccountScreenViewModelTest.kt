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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.add_account.view_model

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.makeappssimple.abhimanyu.common.core.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Amount
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.navigation.FinanceManagerNavigationDirections
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.add_account.state.AddAccountScreenNameError
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.add_account.state.AddAccountScreenUIVisibilityData
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeZero
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

            result.selectedAccountType.shouldBe(
                expected = AccountType.BANK,
            )
            result.nameError.shouldBe(
                expected = AddAccountScreenNameError.None,
            )
            result.visibilityData.shouldBe(
                expected = AddAccountScreenUIVisibilityData(
                    minimumBalanceAmountTextField = true,
                    nameTextFieldErrorText = false,
                ),
            )
            result.isCtaButtonEnabled.shouldBeFalse()
            result.isLoading.shouldBeFalse()
            result.selectedAccountTypeIndex.shouldBeZero()
            result.accountTypesChipUIDataList.shouldBe(
                expected = listOf(
                    ChipUIData(
                        text = "Bank",
                        icon = MyIcons.AccountBalance,
                    ),
                    ChipUIData(
                        text = "E-Wallet",
                        icon = MyIcons.AccountBalanceWallet,
                    ),
                ),
            )
            result.minimumAccountBalanceTextFieldState.text.toString()
                .shouldBeEmpty()
            result.nameTextFieldState.text.toString().shouldBeEmpty()
        }
    }
    // endregion

    // region state events
    @Test
    fun clearMinimumAccountBalanceAmountValue_shouldClearText() =
        testDependencies.runTestWithTimeout {
            val updatedMinimumAccountBalanceAmountValue = "1000"
            addAccountScreenViewModel.uiState.test {
                val previousResult = awaitItem()
                previousResult.isLoading.shouldBeFalse()
                previousResult.minimumAccountBalanceTextFieldState.text.toString()
                    .shouldBeEmpty()
                addAccountScreenViewModel.uiStateEvents.updateMinimumAccountBalanceAmountValue(
                    updatedMinimumAccountBalanceAmountValue,
                )
                previousResult.minimumAccountBalanceTextFieldState.text.toString()
                    .shouldBe(
                        expected = updatedMinimumAccountBalanceAmountValue,
                    )

                addAccountScreenViewModel.uiStateEvents.clearMinimumAccountBalanceAmountValue()

                previousResult.minimumAccountBalanceTextFieldState.text.toString()
                    .shouldBeEmpty()
            }
        }

    @Test
    fun clearName_shouldClearText() = testDependencies.runTestWithTimeout {
        val updatedName = "test-account"
        addAccountScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.isLoading.shouldBeFalse()
            initialState.nameTextFieldState.text.toString()
                .shouldBeEmpty()
            addAccountScreenViewModel.uiStateEvents.updateName(updatedName)
            awaitItem().nameTextFieldState.text.toString().shouldBe(
                expected = updatedName,
            )

            addAccountScreenViewModel.uiStateEvents.clearName()
            val result = awaitItem()
            result.nameTextFieldState.text.toString().shouldBeEmpty()
        }
    }

    @Test
    fun insertAccount_selectedAccountIsBank_shouldInsertAndNavigateUp() =
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
                val testAccountName = "test-bank"
                val testMinimumAccountBalanceAmount = "1000"
                val testAccountType = AccountType.BANK
                val testAccount = Account(
                    id = testAccountId,
                    type = testAccountType,
                    minimumAccountBalanceAmount = Amount(
                        value = testMinimumAccountBalanceAmount.toLongOrZero(),
                    ),
                    name = testAccountName,
                )
                val initialState = uiStateTurbine.awaitItem()
                initialState.isLoading.shouldBeFalse()
                addAccountScreenViewModel.uiStateEvents.updateName(
                    testAccountName,
                )
                val nameUpdatedState = uiStateTurbine.awaitItem()
                nameUpdatedState.nameTextFieldState.text.toString()
                    .shouldBe(
                        expected = testAccountName,
                    )
                addAccountScreenViewModel.uiStateEvents.updateMinimumAccountBalanceAmountValue(
                    testMinimumAccountBalanceAmount,
                )
                nameUpdatedState.minimumAccountBalanceTextFieldState.text.toString()
                    .shouldBe(
                        expected = testMinimumAccountBalanceAmount,
                    )

                addAccountScreenViewModel.uiStateEvents.insertAccount()

                uiStateTurbine.awaitItem().isLoading.shouldBeTrue()
                testDependencies.fakeAccountDao.getAllAccounts()
                    .find {
                        it.id == testAccountId
                    }
                    ?.asExternalModel()
                    .shouldBe(
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
    fun insertAccount_selectedAccountIsEWallet_shouldInsertAndNavigateUp() =
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
                val testAccountName = "test-wallet"
                val testMinimumAccountBalanceAmount = "1000"
                val testAccountType = AccountType.E_WALLET
                val testSelectedAccountTypeIndex = 1
                val testAccount = Account(
                    id = testAccountId,
                    type = testAccountType,
                    minimumAccountBalanceAmount = Amount(
                        value = testMinimumAccountBalanceAmount.toLongOrZero(),
                    ),
                    name = testAccountName,
                )
                val initialState = uiStateTurbine.awaitItem()
                initialState.isLoading.shouldBeFalse()
                addAccountScreenViewModel.uiStateEvents.updateName(
                    testAccountName,
                )
                val nameUpdatedState = uiStateTurbine.awaitItem()
                nameUpdatedState.nameTextFieldState.text.toString()
                    .shouldBe(
                        expected = testAccountName,
                    )
                addAccountScreenViewModel.uiStateEvents.updateMinimumAccountBalanceAmountValue(
                    testMinimumAccountBalanceAmount,
                )
                nameUpdatedState.minimumAccountBalanceTextFieldState.text.toString()
                    .shouldBe(
                        expected = testMinimumAccountBalanceAmount,
                    )
                addAccountScreenViewModel.uiStateEvents.updateSelectedAccountTypeIndex(
                    testSelectedAccountTypeIndex
                )
                val selectedAccountTypeIndexUpdatedState =
                    uiStateTurbine.awaitItem()
                selectedAccountTypeIndexUpdatedState.selectedAccountTypeIndex.shouldBe(
                    expected = testSelectedAccountTypeIndex,
                )

                addAccountScreenViewModel.uiStateEvents.insertAccount()

                uiStateTurbine.awaitItem().isLoading.shouldBeTrue()
                testDependencies.fakeAccountDao.getAllAccounts()
                    .find {
                        it.id == 1
                    }
                    ?.asExternalModel()
                    .shouldBe(
                        expected = Account(
                            id = testAccountId,
                            type = testAccountType,
                            minimumAccountBalanceAmount = null,
                            name = testAccountName,
                        ),
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
    fun updateMinimumAccountBalanceAmountValue_shouldUpdateValue() =
        testDependencies.runTestWithTimeout {
            val updatedMinimumAccountBalanceAmountValue = "1000"
            addAccountScreenViewModel.uiState.test {
                val previousResult = awaitItem()
                previousResult.isLoading.shouldBeFalse()
                previousResult.minimumAccountBalanceTextFieldState.text.toString()
                    .shouldBeEmpty()

                addAccountScreenViewModel.uiStateEvents.updateMinimumAccountBalanceAmountValue(
                    updatedMinimumAccountBalanceAmountValue,
                )

                previousResult.minimumAccountBalanceTextFieldState.text.toString()
                    .shouldBe(
                        expected = updatedMinimumAccountBalanceAmountValue,
                    )
            }
        }

    @Test
    fun updateName_nameIsBlank_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            val updatedName = "  "
            addAccountScreenViewModel.uiState.test {
                val previousResult = awaitItem()
                previousResult.isLoading.shouldBeFalse()
                previousResult.nameTextFieldState.text.toString()
                    .shouldBeEmpty()

                addAccountScreenViewModel.uiStateEvents.updateName(updatedName)

                previousResult.nameError.shouldBe(
                    expected = AddAccountScreenNameError.None,
                )
                previousResult.visibilityData.shouldBe(
                    expected = AddAccountScreenUIVisibilityData(
                        minimumBalanceAmountTextField = true,
                        nameTextFieldErrorText = false,
                    ),
                )
                previousResult.isCtaButtonEnabled.shouldBeFalse()
                previousResult.nameTextFieldState.text.toString().shouldBe(
                    expected = updatedName,
                )
            }
        }

    @Test
    fun updateName_nameIsCash_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            val updatedName = "Cash"
            addAccountScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeFalse()
                initialState.nameTextFieldState.text.toString()
                    .shouldBeEmpty()

                addAccountScreenViewModel.uiStateEvents.updateName(updatedName)

                val result = awaitItem()
                result.nameError.shouldBe(
                    expected = AddAccountScreenNameError.AccountExists,
                )
                result.visibilityData.shouldBe(
                    expected = AddAccountScreenUIVisibilityData(
                        minimumBalanceAmountTextField = true,
                        nameTextFieldErrorText = true,
                    ),
                )
                result.isCtaButtonEnabled.shouldBeFalse()
                result.nameTextFieldState.text.toString().shouldBe(
                    expected = updatedName,
                )
            }
        }

    @Test
    fun updateName_nameIsCashInSmall_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            val updatedName = "cash"
            addAccountScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeFalse()
                initialState.nameTextFieldState.text.toString()
                    .shouldBeEmpty()

                addAccountScreenViewModel.uiStateEvents.updateName(updatedName)

                val result = awaitItem()
                result.nameError.shouldBe(
                    expected = AddAccountScreenNameError.AccountExists,
                )
                result.visibilityData.shouldBe(
                    expected = AddAccountScreenUIVisibilityData(
                        minimumBalanceAmountTextField = true,
                        nameTextFieldErrorText = true,
                    ),
                )
                result.isCtaButtonEnabled.shouldBeFalse()
                result.nameTextFieldState.text.toString().shouldBe(
                    expected = updatedName,
                )
            }
        }

    @Test
    fun updateName_nameIsCashWithSpaces_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            val updatedName = "  Cash   "
            addAccountScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeFalse()
                initialState.nameTextFieldState.text.toString()
                    .shouldBeEmpty()

                addAccountScreenViewModel.uiStateEvents.updateName(updatedName)

                val result = awaitItem()
                result.nameError.shouldBe(
                    expected = AddAccountScreenNameError.AccountExists,
                )
                result.visibilityData.shouldBe(
                    expected = AddAccountScreenUIVisibilityData(
                        minimumBalanceAmountTextField = true,
                        nameTextFieldErrorText = true,
                    ),
                )
                result.isCtaButtonEnabled.shouldBeFalse()
                result.nameTextFieldState.text.toString().shouldBe(
                    expected = updatedName,
                )
            }
        }

    @Test
    fun updateName_accountExists_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            val updatedName = testDependencies.testAccountName1
            addAccountScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeFalse()
                initialState.nameTextFieldState.text.toString()
                    .shouldBeEmpty()

                addAccountScreenViewModel.uiStateEvents.updateName(updatedName)

                val result = awaitItem()
                result.nameError.shouldBe(
                    expected = AddAccountScreenNameError.AccountExists,
                )
                result.visibilityData.shouldBe(
                    expected = AddAccountScreenUIVisibilityData(
                        minimumBalanceAmountTextField = true,
                        nameTextFieldErrorText = true,
                    ),
                )
                result.isCtaButtonEnabled.shouldBeFalse()
                result.nameTextFieldState.text.toString().shouldBe(
                    expected = updatedName,
                )
            }
        }

    @Test
    fun updateName_accountIsValid_ctaIsEnabled() =
        testDependencies.runTestWithTimeout {
            val updatedName = "test-account"
            addAccountScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeFalse()
                initialState.nameTextFieldState.text.toString()
                    .shouldBeEmpty()

                addAccountScreenViewModel.uiStateEvents.updateName(updatedName)

                val result = awaitItem()
                result.nameError.shouldBe(
                    expected = AddAccountScreenNameError.None,
                )
                result.visibilityData.shouldBe(
                    expected = AddAccountScreenUIVisibilityData(
                        minimumBalanceAmountTextField = true,
                        nameTextFieldErrorText = false,
                    ),
                )
                result.isCtaButtonEnabled.shouldBeTrue()
                result.nameTextFieldState.text.toString().shouldBe(
                    expected = updatedName,
                )
            }
        }

    @Test
    fun updateSelectedAccountTypeIndex_shouldUpdateIndex() =
        testDependencies.runTestWithTimeout {
            val updatedIndex = 1
            addAccountScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeFalse()
                initialState.selectedAccountTypeIndex.shouldBe(
                    expected = 0,
                )

                addAccountScreenViewModel.uiStateEvents.updateSelectedAccountTypeIndex(
                    updatedIndex,
                )

                val result = awaitItem()
                result.selectedAccountTypeIndex.shouldBe(
                    expected = updatedIndex,
                )
            }
        }
    // endregion
}
