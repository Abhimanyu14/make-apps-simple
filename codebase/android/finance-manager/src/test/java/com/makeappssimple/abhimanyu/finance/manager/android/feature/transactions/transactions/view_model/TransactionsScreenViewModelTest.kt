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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.transactions.view_model

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.transactions.bottom_sheet.TransactionsScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

internal class TransactionsScreenViewModelTest {
    // region test setup
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var testDependencies: TestDependencies
    private lateinit var transactionsScreenViewModel: TransactionsScreenViewModel

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        savedStateHandle = SavedStateHandle()
        transactionsScreenViewModel = TransactionsScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            screenUIStateDelegate = testDependencies.screenUIStateDelegate,
            coroutineScope = testDependencies.testScope.backgroundScope,
            dispatcherProvider = testDependencies.testDispatcherProvider,
            dateTimeKit = testDependencies.dateTimeKit,
            getAllTransactionDataFlowUseCase = testDependencies.getAllTransactionDataFlowUseCase,
            getAllTransactionForValuesUseCase = testDependencies.getAllTransactionForValuesUseCase,
            updateTransactionsUseCase = testDependencies.updateTransactionsUseCase,
            logKit = testDependencies.logKit,
        )
        transactionsScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testDependencies.testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = testDependencies.runTestWithTimeout {
        transactionsScreenViewModel.uiState.test {
            val result = awaitItem()

            assertThat(result.isBackHandlerEnabled).isFalse()
            assertThat(result.isBottomSheetVisible).isFalse()
            assertThat(result.isInSelectionMode).isFalse()
            assertThat(result.isLoading).isTrue()
            assertThat(result.isSearchSortAndFilterVisible).isFalse()
            assertThat(result.selectedFilter).isEqualTo(Filter())
            assertThat(result.selectedTransactions).isEmpty()
            assertThat(result.sortOptions).isEmpty()
            assertThat(result.transactionForValues).isEmpty()
            assertThat(result.accounts).isEmpty()
            assertThat(result.expenseCategories).isEmpty()
            assertThat(result.incomeCategories).isEmpty()
            assertThat(result.investmentCategories).isEmpty()
            assertThat(result.transactionTypes).isEmpty()
            assertThat(result.currentLocalDate).isEqualTo(LocalDate.MIN)
            assertThat(result.oldestTransactionLocalDate).isEqualTo(LocalDate.MIN)
            assertThat(result.transactionDetailsListItemViewData).isEmpty()
            assertThat(result.selectedSortOption).isEqualTo(SortOption.LATEST_FIRST)
            assertThat(result.searchText).isEmpty()
            assertThat(result.screenBottomSheetType).isEqualTo(
                TransactionsScreenBottomSheetType.None
            )
        }
    }
    // endregion

    // region fetchData
    @Test
    fun fetchData_initialState_dataFetched() =
        testDependencies.runTestWithTimeout {
            transactionsScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()

                val result = awaitItem()
                assertThat(result.isLoading).isFalse()
                assertThat(result.transactionForValues.size).isEqualTo(2)
                assertThat(result.transactionForValues).isEqualTo(
                    listOf(
                        testDependencies.testTransactionForEntity1.asExternalModel(),
                        testDependencies.testTransactionForEntity2.asExternalModel(),
                    ),
                )
            }
        }
    // endregion

    // region observeData
    @Test
    fun observeData_initialState_dataObserved() =
        testDependencies.runTestWithTimeout {
            transactionsScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                val fetchDataCompletedState = awaitItem()
                assertThat(fetchDataCompletedState.isLoading).isFalse()

                val result = awaitItem()
                assertThat(result.accounts).isEqualTo(
                    listOf(
                        testDependencies.testAccountEntity2.asExternalModel(),
                        testDependencies.testAccountEntity1.asExternalModel(),
                    ),
                )
                assertThat(result.expenseCategories).isEmpty()
                assertThat(result.incomeCategories).isEmpty()
                assertThat(result.investmentCategories).isEmpty()
                assertThat(result.oldestTransactionLocalDate).isEqualTo(
                    LocalDate.of(2024, 5, 20)
                )
                assertThat(result.transactionDetailsListItemViewData.size).isEqualTo(
                    2
                )
                assertThat(result.transactionDetailsListItemViewData["23 Aug, 2025 (Saturday)"]?.size).isEqualTo(
                    1
                )
                assertThat(result.transactionDetailsListItemViewData["20 May, 2024 (Monday)"]?.size).isEqualTo(
                    1
                )
//                assertThat(result.transactionDetailsListItemViewData).isEqualTo(
//                    mapOf(
//                        "23 Aug, 2025 (Saturday)" to listOf(
//                            TransactionListItemData(
//                                isDeleteButtonEnabled = false,
//                                isDeleteButtonVisible = true,
//                                isEditButtonVisible = false,
//                                isExpanded = false,
//                                isInSelectionMode = false,
//                                isLoading = false,
//                                isRefundButtonVisible = false,
//                                isSelected = false,
//                                transactionId = 101,
//                                amountColor = MyColor.ERROR,
//                                amountText = "- ₹1,000",
//                                dateAndTimeText = "01 Jan, 1970 at 05:46 AM",
//                                emoji = "💳",
//                                accountFromName = "test-account-101",
//                                accountToName = null,
//                                title = "test-transaction-101",
//                                transactionForText = "Test-transaction-for-101",
//                            ),
//                        ),
//                        "20 May, 2024 (Monday)" to listOf(
//                            TransactionListItemData(
//                                isDeleteButtonEnabled = false,
//                                isDeleteButtonVisible = true,
//                                isEditButtonVisible = false,
//                                isExpanded = false,
//                                isInSelectionMode = false,
//                                isLoading = false,
//                                isRefundButtonVisible = false,
//                                isSelected = false,
//                                transactionId = 102,
//                                amountColor = MyColor.ERROR,
//                                amountText = "- ₹1,000",
//                                dateAndTimeText = "01 Jan, 1970 at 05:46 AM",
//                                emoji = "💰",
//                                accountFromName = "test-account-102",
//                                accountToName = null,
//                                title = "test-transaction-102",
//                                transactionForText = "Test-transaction-for-102",
//                            ),
//                        )
//                    ),
//                )
            }
        }
    // endregion
}
