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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.view_model

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.bottom_sheet.AddTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.snackbar.AddTransactionScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.state.AccountFromText
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.state.AccountToText
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.ints.shouldBeZero
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

internal class AddTransactionScreenViewModelTest {
    // region test setup
    private lateinit var addTransactionScreenViewModel: AddTransactionScreenViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var testDependencies: TestDependencies

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        savedStateHandle = SavedStateHandle()
        addTransactionScreenViewModel = AddTransactionScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            savedStateHandle = savedStateHandle,
            uriDecoder = testDependencies.uriDecoder,
            addTransactionScreenDataValidationUseCase = testDependencies.addTransactionScreenDataValidationUseCase,
            coroutineScope = testDependencies.testScope.backgroundScope,
            dateTimeKit = testDependencies.dateTimeKit,
            financeManagerPreferencesRepository = testDependencies.financeManagerPreferencesRepository,
            getAllAccountsUseCase = testDependencies.getAllAccountsUseCase,
            getAllCategoriesUseCase = testDependencies.getAllCategoriesUseCase,
            getAllTransactionForValuesUseCase = testDependencies.getAllTransactionForValuesUseCase,
            getTitleSuggestionsUseCase = testDependencies.getTitleSuggestionsUseCase,
            getTransactionDataByIdUseCase = testDependencies.getTransactionDataByIdUseCase,
            getMaxRefundAmountUseCase = testDependencies.getMaxRefundAmountUseCase,
            insertTransactionUseCase = testDependencies.insertTransactionUseCase,
            logKit = testDependencies.logKit,
        )
        addTransactionScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testDependencies.testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = testDependencies.runTestWithTimeout {
        addTransactionScreenViewModel.uiState.test {
            val result = awaitItem()

            result.accountFrom.shouldBeNull()
            result.accountFromText.shouldBe(
                expected = AccountFromText.Account,
            )
            result.accountTo.shouldBeNull()
            result.accountToText.shouldBe(
                expected = AccountToText.Account,
            )
            result.screenBottomSheetType.shouldBe(
                expected = AddTransactionScreenBottomSheetType.None,
            )
            result.screenSnackbarType.shouldBe(
                expected = AddTransactionScreenSnackbarType.None,
            )
            result.uiVisibilityState.shouldBe(
                expected = AddTransactionScreenUiVisibilityState.Expense(),
            )
            result.isBottomSheetVisible.shouldBeFalse()
            result.isCtaButtonEnabled.shouldBeFalse()
            result.isLoading.shouldBeTrue()
            result.isTransactionDatePickerDialogVisible.shouldBeFalse()
            result.isTransactionTimePickerDialogVisible.shouldBeFalse()
            result.category.shouldBeNull()
            result.selectedTransactionForIndex.shouldBeZero()
            result.selectedTransactionTypeIndex.shouldBeNull()
            result.accounts.shouldBeEmpty()
            result.filteredCategories.shouldBeEmpty()
            result.titleSuggestionsChipUIData.shouldBeEmpty()
            result.transactionForValuesChipUIData.shouldBeEmpty()
            result.transactionTypesForNewTransactionChipUIData.shouldBeEmpty()
            result.titleSuggestions.shouldBeEmpty()
            result.currentLocalDate.shouldBe(
                expected = LocalDate.MIN,
            )
            result.transactionDate.shouldBe(
                expected = LocalDate.MIN,
            )
            result.transactionTime.shouldBe(
                expected = LocalTime.MIN,
            )
            result.amountErrorText.shouldBeNull()
            result.amountTextFieldState.text.toString().shouldBe(
                expected = "",
            )
            result.titleTextFieldState.text.toString().shouldBe(
                expected = "",
            )
        }
    }
    // endregion
}
