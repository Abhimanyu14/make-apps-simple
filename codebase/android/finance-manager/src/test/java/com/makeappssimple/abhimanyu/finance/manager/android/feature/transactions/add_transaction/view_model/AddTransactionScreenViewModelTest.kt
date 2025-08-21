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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.view_model

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.bottom_sheet.AddTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.snackbar.AddTransactionScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.state.AccountFromText
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.state.AccountToText
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
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
            screenUIStateDelegate = testDependencies.screenUIStateDelegate,
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

            assertThat(result.accountFrom).isNull()
            assertThat(result.accountFromText).isEqualTo(AccountFromText.Account)
            assertThat(result.accountTo).isNull()
            assertThat(result.accountToText).isEqualTo(AccountToText.Account)
            assertThat(result.screenBottomSheetType).isEqualTo(
                AddTransactionScreenBottomSheetType.None
            )
            assertThat(result.screenSnackbarType).isEqualTo(
                AddTransactionScreenSnackbarType.None
            )
            assertThat(result.uiVisibilityState).isEqualTo(
                AddTransactionScreenUiVisibilityState.Expense
            )
            assertThat(result.isBottomSheetVisible).isFalse()
            assertThat(result.isCtaButtonEnabled).isFalse()
            assertThat(result.isLoading).isTrue()
            assertThat(result.isTransactionDatePickerDialogVisible).isFalse()
            assertThat(result.isTransactionTimePickerDialogVisible).isFalse()
            assertThat(result.category).isNull()
            assertThat(result.selectedTransactionForIndex).isEqualTo(0)
            assertThat(result.selectedTransactionTypeIndex).isNull()
            assertThat(result.accounts).isEmpty()
            assertThat(result.filteredCategories).isEmpty()
            assertThat(result.titleSuggestionsChipUIData).isEmpty()
            assertThat(result.transactionForValuesChipUIData).isEmpty()
            assertThat(result.transactionTypesForNewTransactionChipUIData).isEmpty()
            assertThat(result.titleSuggestions).isEmpty()
            assertThat(result.currentLocalDate).isEqualTo(LocalDate.MIN)
            assertThat(result.transactionDate).isEqualTo(LocalDate.MIN)
            assertThat(result.transactionTime).isEqualTo(LocalTime.MIN)
            assertThat(result.amountErrorText).isNull()
            assertThat(result.amount).isEqualTo(TextFieldValue())
            assertThat(result.title).isEqualTo(TextFieldValue())
        }
    }
    // endregion
}
