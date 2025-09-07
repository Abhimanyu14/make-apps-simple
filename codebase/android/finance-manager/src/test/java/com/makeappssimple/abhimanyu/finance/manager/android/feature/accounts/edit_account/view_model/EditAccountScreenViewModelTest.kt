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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.view_model

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.screen.EditAccountScreenUIVisibilityData
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.state.EditAccountScreenNameError
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import kotlin.test.Test

internal class EditAccountScreenViewModelTest {
    // region test setup
    private lateinit var editAccountScreenViewModel: EditAccountScreenViewModel
    private lateinit var testSavedStateHandle: SavedStateHandle
    private lateinit var testDependencies: TestDependencies

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        testSavedStateHandle = SavedStateHandle(
            initialState = mapOf(
                "accountId" to testDependencies.testAccountId1,
            ),
        )
    }

    @After
    fun tearDown() {
        testDependencies.testScope.cancel()
    }
    // endregion

    @Test
    fun uiState_initialState_eWallet() = testDependencies.runTestWithTimeout {
        setUpViewModel()
        editAccountScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.isLoading.shouldBeTrue()
            editAccountScreenViewModel.initViewModel()

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
            editAccountScreenViewModel.initViewModel()

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
