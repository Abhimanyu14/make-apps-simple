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

package com.makeappssimple.abhimanyu.finance.manager.android.common.feature.accounts.edit_account.view_model

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.accounts.edit_account.screen.EditAccountScreenUIVisibilityData
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.accounts.edit_account.state.EditAccountScreenNameError
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.ExpectedException
import kotlin.test.Test
import kotlin.test.assertFailsWith

internal class EditAccountScreenViewModelTest {
    // region test setup
    private lateinit var editAccountScreenViewModel: EditAccountScreenViewModel
    private lateinit var testSavedStateHandle: SavedStateHandle
    private lateinit var testDependencies: TestDependencies

    @Rule
    @JvmField
    val expectedException: ExpectedException = ExpectedException.none()

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        testSavedStateHandle = SavedStateHandle(
            initialState = mapOf(
                "accountId" to testDependencies.testAccountId2,
            ),
        )
    }

    @After
    fun tearDown() {
        testDependencies.testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_accountIdIsInvalid_throwsException() =
        testDependencies.runTestWithTimeout {
            val testAccountId = 100
            testSavedStateHandle = SavedStateHandle(
                initialState = mapOf(
                    "accountId" to testAccountId,
                ),
            )
            setUpViewModel()
            expectedException.expect(IllegalArgumentException::class.java)
            expectedException.expectMessage("account with id $testAccountId not found.")

            editAccountScreenViewModel.initViewModel().join()

            // TODO(Abhi): This does not work
            /*
            val exception = assertFailsWith(
                exceptionClass = IllegalArgumentException::class,
            ) {
                editAccountScreenViewModel.initViewModel().join()
            }
            exception.message.shouldBe(
                expected = "account with id $testAccountId not found.",
            )
            */
        }

    @Test
    fun uiState_initialState_eWallet() = testDependencies.runTestWithTimeout {
        testSavedStateHandle = SavedStateHandle(
            initialState = mapOf(
                "accountId" to testDependencies.testAccountId1,
            ),
        )
        setUpViewModel()
        editAccountScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.isLoading.shouldBeTrue()
            editAccountScreenViewModel.initViewModel().join()

            val result = awaitItem()
            result.visibilityData.shouldBe(
                expected = EditAccountScreenUIVisibilityData(
                    accountTypesRadioGroup = true,
                    balanceAmountTextField = true,
                    minimumBalanceAmountTextField = false,
                    nameTextField = true,
                    nameTextFieldErrorText = false,
                ),
            )
            result.isCtaButtonEnabled.shouldBeTrue()
            result.isLoading.shouldBeFalse()
            result.nameError.shouldBe(
                expected = EditAccountScreenNameError.None,
            )
            result.selectedAccountTypeIndex.shouldBe(
                expected = 1,
            )
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
            result.balanceAmountValueTextFieldState.text.toString().shouldBe(
                expected = testDependencies.testAccountEntity1.balanceAmount.value.toString(),
            )
            result.minimumBalanceAmountValueTextFieldState.text.toString()
                .shouldBeEmpty()
            result.nameTextFieldState.text.toString().shouldBe(
                expected = testDependencies.testAccountName1,
            )
        }
    }

    @Test
    fun uiState_initialState_bank() = testDependencies.runTestWithTimeout {
        testSavedStateHandle = SavedStateHandle(
            initialState = mapOf(
                "accountId" to testDependencies.testAccountId2,
            ),
        )
        setUpViewModel()
        editAccountScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.isLoading.shouldBeTrue()
            editAccountScreenViewModel.initViewModel().join()

            val result = awaitItem()
            result.visibilityData.shouldBe(
                expected = EditAccountScreenUIVisibilityData(
                    accountTypesRadioGroup = true,
                    balanceAmountTextField = true,
                    minimumBalanceAmountTextField = true,
                    nameTextField = true,
                    nameTextFieldErrorText = false,
                ),
            )
            result.isCtaButtonEnabled.shouldBeTrue()
            result.isLoading.shouldBeFalse()
            result.nameError.shouldBe(
                expected = EditAccountScreenNameError.None,
            )
            result.selectedAccountTypeIndex.shouldBe(
                expected = 0,
            )
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
            result.balanceAmountValueTextFieldState.text.toString().shouldBe(
                expected = testDependencies.testAccountEntity2.balanceAmount.value.toString(),
            )
            result.minimumBalanceAmountValueTextFieldState.text.toString()
                .shouldBe(
                    expected = testDependencies.testAccountEntity2.minimumAccountBalanceAmount?.value?.toString()
                        .orEmpty(),
                )
            result.nameTextFieldState.text.toString().shouldBe(
                expected = testDependencies.testAccountName2,
            )
        }
    }
    // endregion

    // region state events
    @Test
    fun clearBalanceAmountValue() = testDependencies.runTestWithTimeout {
        setUpViewModel()
        editAccountScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.isLoading.shouldBeTrue()
            editAccountScreenViewModel.initViewModel().join()
            val previousResult = awaitItem()
            previousResult.isLoading.shouldBeFalse()

            editAccountScreenViewModel.uiStateEvents.clearBalanceAmountValue()
            previousResult.balanceAmountValueTextFieldState.text.toString()
                .shouldBeEmpty()
        }
    }

    @Test
    fun clearMinimumAccountBalanceAmountValue() =
        testDependencies.runTestWithTimeout {
            setUpViewModel()
            editAccountScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                editAccountScreenViewModel.initViewModel().join()
                val previousResult = awaitItem()
                previousResult.isLoading.shouldBeFalse()

                editAccountScreenViewModel.uiStateEvents.clearMinimumAccountBalanceAmountValue()

                previousResult.minimumBalanceAmountValueTextFieldState.text.toString()
                    .shouldBeEmpty()
            }
        }

    @Test
    fun clearName() =
        testDependencies.runTestWithTimeout {
            setUpViewModel()
            editAccountScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                editAccountScreenViewModel.initViewModel().join()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()

                editAccountScreenViewModel.uiStateEvents.clearName()
                fetchDataCompletedState.nameTextFieldState.text.toString()
                    .shouldBeEmpty()

                val result = awaitItem()
                result.isCtaButtonEnabled.shouldBeFalse()
                result.nameError.shouldBeEqual(
                    expected = EditAccountScreenNameError.None,
                )
                result.nameTextFieldState.text.toString().shouldBeEmpty()
            }
        }

    @Test
    fun updateBalanceAmountValue() = testDependencies.runTestWithTimeout {
        setUpViewModel()
        val updatedBalanceAmountValue = "2000"
        editAccountScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.isLoading.shouldBeTrue()
            editAccountScreenViewModel.initViewModel().join()
            val previousResult = awaitItem()
            previousResult.isLoading.shouldBeFalse()

            editAccountScreenViewModel.uiStateEvents.updateBalanceAmountValue(
                updatedBalanceAmountValue,
            )
            previousResult.balanceAmountValueTextFieldState.text.toString()
                .shouldBe(
                    expected = updatedBalanceAmountValue,
                )
        }
    }

    @Test
    fun updateMinimumAccountBalanceAmountValue() =
        testDependencies.runTestWithTimeout {
            setUpViewModel()
            val updatedMinimumAccountBalanceAmountValue = "2000"
            editAccountScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                editAccountScreenViewModel.initViewModel().join()
                val previousResult = awaitItem()
                previousResult.isLoading.shouldBeFalse()

                editAccountScreenViewModel.uiStateEvents.updateMinimumAccountBalanceAmountValue(
                    updatedMinimumAccountBalanceAmountValue,
                )

                previousResult.minimumBalanceAmountValueTextFieldState.text.toString()
                    .shouldBe(
                        expected = updatedMinimumAccountBalanceAmountValue,
                    )
            }
        }

    @Test
    fun updateName_accountExists_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            setUpViewModel()
            val updatedName = testDependencies.testAccountName1
            editAccountScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                editAccountScreenViewModel.initViewModel().join()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()

                editAccountScreenViewModel.uiStateEvents.updateName(
                    updatedName,
                )
                fetchDataCompletedState.nameTextFieldState.text.toString()
                    .shouldBe(
                        expected = updatedName,
                    )

                val result = awaitItem()
                result.isCtaButtonEnabled.shouldBeFalse()
                result.nameError.shouldBeEqual(
                    expected = EditAccountScreenNameError.AccountExists,
                )
                result.nameTextFieldState.text.toString().shouldBe(
                    expected = updatedName,
                )
            }
        }

    @Test
    fun updateName_nameIsBlank_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            setUpViewModel()
            val updatedName = "    "
            editAccountScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                editAccountScreenViewModel.initViewModel().join()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()

                editAccountScreenViewModel.uiStateEvents.updateName(
                    updatedName,
                )
                fetchDataCompletedState.nameTextFieldState.text.toString()
                    .shouldBe(
                        expected = updatedName,
                    )

                val result = awaitItem()
                result.isCtaButtonEnabled.shouldBeFalse()
                result.nameError.shouldBeEqual(
                    expected = EditAccountScreenNameError.None,
                )
                result.nameTextFieldState.text.toString().shouldBe(
                    expected = updatedName,
                )
            }
        }

    @Test
    fun updateName_nameIsValid_ctaIsEnabled() =
        testDependencies.runTestWithTimeout {
            setUpViewModel()
            val updatedName = "test-account-name"
            editAccountScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                editAccountScreenViewModel.initViewModel().join()
                val previousResult = awaitItem()
                previousResult.isLoading.shouldBeFalse()

                editAccountScreenViewModel.uiStateEvents.updateName(
                    updatedName,
                )

                previousResult.isCtaButtonEnabled.shouldBeTrue()
                previousResult.nameError.shouldBeEqual(
                    expected = EditAccountScreenNameError.None,
                )
                previousResult.nameTextFieldState.text.toString().shouldBe(
                    expected = updatedName,
                )
            }
        }

    @Test
    fun updateSelectedAccountTypeIndex() =
        testDependencies.runTestWithTimeout {
            setUpViewModel()
            val updatedSelectedAccountTypeIndex = 1
            editAccountScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                editAccountScreenViewModel.initViewModel().join()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()
                fetchDataCompletedState.selectedAccountTypeIndex.shouldBe(
                    expected = 0,
                )

                editAccountScreenViewModel.uiStateEvents.updateSelectedAccountTypeIndex(
                    updatedSelectedAccountTypeIndex,
                )

                val result = awaitItem()
                result.selectedAccountTypeIndex.shouldBe(
                    expected = updatedSelectedAccountTypeIndex,
                )
            }
        }

    @Test
    fun updateSelectedAccountTypeIndex_invalidIndex_throwsException() =
        testDependencies.runTestWithTimeout {
            setUpViewModel()
            val updatedSelectedAccountTypeIndex = 100
            editAccountScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                editAccountScreenViewModel.initViewModel().join()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()
                fetchDataCompletedState.selectedAccountTypeIndex.shouldBe(
                    expected = 0,
                )

                val exception = assertFailsWith(
                    exceptionClass = IllegalStateException::class,
                ) {
                    editAccountScreenViewModel.uiStateEvents.updateSelectedAccountTypeIndex(
                        updatedSelectedAccountTypeIndex,
                    )
                }

                exception.message.shouldBe(
                    expected = "No account type found for index $updatedSelectedAccountTypeIndex",
                )
            }
        }
    // endregion

    // region common
    private fun setUpViewModel(
        savedStateHandle: SavedStateHandle = testSavedStateHandle,
    ) {
        editAccountScreenViewModel = EditAccountScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            savedStateHandle = savedStateHandle,
            coroutineScope = testDependencies.testScope.backgroundScope,
            editAccountScreenDataValidationUseCase = testDependencies.editAccountScreenDataValidationUseCase,
            getAccountByIdUseCase = testDependencies.getAccountByIdUseCase,
            updateAccountUseCase = testDependencies.updateAccountUseCase,
            logKit = testDependencies.logKit,
        )
    }
    // endregion
}
