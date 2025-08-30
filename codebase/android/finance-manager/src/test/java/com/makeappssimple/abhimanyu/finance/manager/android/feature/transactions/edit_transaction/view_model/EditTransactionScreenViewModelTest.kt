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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.edit_transaction.view_model

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.edit_transaction.bottom_sheet.EditTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.edit_transaction.snackbar.EditTransactionScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.edit_transaction.state.AccountFromText
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.edit_transaction.state.AccountToText
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.ints.shouldBeZero
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

internal class EditTransactionScreenViewModelTest {
    // region test setup
    private lateinit var editTransactionScreenViewModel: EditTransactionScreenViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var testDependencies: TestDependencies

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        savedStateHandle = SavedStateHandle(
            initialState = mapOf(
                "transactionId" to testDependencies.testTransactionId1.toString(),
            ),
        )
        editTransactionScreenViewModel = EditTransactionScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            screenUIStateDelegate = testDependencies.screenUIStateDelegate,
            savedStateHandle = savedStateHandle,
            uriDecoder = testDependencies.uriDecoder,
            coroutineScope = testDependencies.testScope.backgroundScope,
            dateTimeKit = testDependencies.dateTimeKit,
            editTransactionScreenDataValidationUseCase = testDependencies.editTransactionScreenDataValidationUseCase,
            financeManagerPreferencesRepository = testDependencies.financeManagerPreferencesRepository,
            getAllCategoriesUseCase = testDependencies.getAllCategoriesUseCase,
            getAllAccountsUseCase = testDependencies.getAllAccountsUseCase,
            getAllTransactionForValuesUseCase = testDependencies.getAllTransactionForValuesUseCase,
            getTitleSuggestionsUseCase = testDependencies.getTitleSuggestionsUseCase,
            getTransactionDataByIdUseCase = testDependencies.getTransactionDataByIdUseCase,
            getMaxRefundAmountUseCase = testDependencies.getMaxRefundAmountUseCase,
            updateTransactionUseCase = testDependencies.updateTransactionUseCase,
            logKit = testDependencies.logKit,
        )
        editTransactionScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testDependencies.testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = testDependencies.runTestWithTimeout {
        editTransactionScreenViewModel.uiState.test {
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
                expected = EditTransactionScreenBottomSheetType.None,
            )
            result.screenSnackbarType.shouldBe(
                expected = EditTransactionScreenSnackbarType.None,
            )
            result.uiVisibilityState.shouldBe(
                expected = EditTransactionScreenUiVisibilityState.Expense,
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
            result.amount.text.shouldBeEmpty()
            result.title.text.shouldBeEmpty()
            result.selectedTransactionType.shouldBe(
                expected = TransactionType.EXPENSE,
            )
        }
    }
    // endregion

    // region fetchData
    @Test
    fun fetchData_shouldUpdateUiState() = testDependencies.runTestWithTimeout {
        editTransactionScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.accountFrom.shouldBeNull()
            initialState.accountFromText.shouldBe(
                expected = AccountFromText.Account,
            )
            initialState.accountTo.shouldBeNull()
            initialState.accountToText.shouldBe(
                expected = AccountToText.Account,
            )
            initialState.screenBottomSheetType.shouldBe(
                expected = EditTransactionScreenBottomSheetType.None,
            )
            initialState.screenSnackbarType.shouldBe(
                expected = EditTransactionScreenSnackbarType.None,
            )
            initialState.uiVisibilityState.shouldBe(
                expected = EditTransactionScreenUiVisibilityState.Expense,
            )
            initialState.isBottomSheetVisible.shouldBeFalse()
            initialState.isCtaButtonEnabled.shouldBeFalse()
            initialState.isLoading.shouldBeTrue()
            initialState.isTransactionDatePickerDialogVisible.shouldBeFalse()
            initialState.isTransactionTimePickerDialogVisible.shouldBeFalse()
            initialState.category.shouldBeNull()
            initialState.selectedTransactionForIndex.shouldBeZero()
            initialState.selectedTransactionTypeIndex.shouldBeNull()
            initialState.accounts.shouldBeEmpty()
            initialState.filteredCategories.shouldBeEmpty()
            initialState.titleSuggestionsChipUIData.shouldBeEmpty()
            initialState.transactionForValuesChipUIData.shouldBeEmpty()
            initialState.transactionTypesForNewTransactionChipUIData.shouldBeEmpty()
            initialState.titleSuggestions.shouldBeEmpty()
            initialState.currentLocalDate.shouldBe(
                expected = LocalDate.MIN,
            )
            initialState.transactionDate.shouldBe(
                expected = LocalDate.MIN,
            )
            initialState.transactionTime.shouldBe(
                expected = LocalTime.MIN,
            )
            initialState.amountErrorText.shouldBeNull()
            initialState.amount.text.shouldBeEmpty()
            initialState.title.text.shouldBeEmpty()
            initialState.selectedTransactionType.shouldBe(
                expected = TransactionType.EXPENSE,
            )

            val fetchDataCompletedState = awaitItem()
            fetchDataCompletedState.accountFrom.shouldBeNull()
            fetchDataCompletedState.accountFromText.shouldBe(
                expected = AccountFromText.Account,
            )
            fetchDataCompletedState.accountTo.shouldBeNull()
            fetchDataCompletedState.accountToText.shouldBe(
                expected = AccountToText.Account,
            )
            fetchDataCompletedState.screenBottomSheetType.shouldBe(
                expected = EditTransactionScreenBottomSheetType.None,
            )
            fetchDataCompletedState.screenSnackbarType.shouldBe(
                expected = EditTransactionScreenSnackbarType.None,
            )
            fetchDataCompletedState.uiVisibilityState.shouldBe(
                expected = EditTransactionScreenUiVisibilityState.Expense,
            )
            fetchDataCompletedState.isBottomSheetVisible.shouldBeFalse()
            fetchDataCompletedState.isCtaButtonEnabled.shouldBeFalse()
            fetchDataCompletedState.isLoading.shouldBeFalse()
            fetchDataCompletedState.isTransactionDatePickerDialogVisible.shouldBeFalse()
            fetchDataCompletedState.isTransactionTimePickerDialogVisible.shouldBeFalse()
            fetchDataCompletedState.category.shouldBeNull()
            fetchDataCompletedState.selectedTransactionForIndex.shouldBeZero()
            fetchDataCompletedState.selectedTransactionTypeIndex.shouldBe(
                expected = 1,
            )
            fetchDataCompletedState.accounts.shouldBe(
                expected = listOf(
                    testDependencies.testAccountEntity2.asExternalModel(),
                    testDependencies.testAccountEntity1.asExternalModel(),
                )
            )
            fetchDataCompletedState.filteredCategories.shouldBeEmpty()
            fetchDataCompletedState.titleSuggestionsChipUIData.shouldBeEmpty()
            fetchDataCompletedState.transactionForValuesChipUIData.shouldBeEmpty()
            fetchDataCompletedState.transactionTypesForNewTransactionChipUIData.shouldBeEmpty()
            fetchDataCompletedState.titleSuggestions.shouldBeEmpty()
            fetchDataCompletedState.currentLocalDate.shouldBe(
                expected = LocalDate.MIN,
            )
            fetchDataCompletedState.transactionDate.shouldBe(
                expected = LocalDate.MIN,
            )
            fetchDataCompletedState.transactionTime.shouldBe(
                expected = LocalTime.MIN,
            )
            fetchDataCompletedState.amountErrorText.shouldBeNull()
            fetchDataCompletedState.amount.text.shouldBeEmpty()
            fetchDataCompletedState.title.text.shouldBeEmpty()
            fetchDataCompletedState.selectedTransactionType.shouldBe(
                expected = TransactionType.EXPENSE,
            )
        }
    }
    // endregion
}
