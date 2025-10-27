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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.transactions.view_model

import app.cash.turbine.test
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.asExternalModel
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.feature.Filter
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.feature.SortOption
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.theme.MyColor
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.transactions.bottom_sheet.TransactionsScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import java.time.LocalDate

internal class TransactionsScreenViewModelTest {
    // region test setup
    private lateinit var testDependencies: TestDependencies
    private lateinit var transactionsScreenViewModel: TransactionsScreenViewModel

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        transactionsScreenViewModel = TransactionsScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            coroutineScope = testDependencies.testScope.backgroundScope,
            dispatcherProvider = testDependencies.testDispatcherProvider,
            dateTimeKit = testDependencies.dateTimeKit,
            duplicateTransactionUseCase = testDependencies.duplicateTransactionUseCase,
            getAllTransactionDataFlowUseCase = testDependencies.getAllTransactionDataFlowUseCase,
            getAccountsInTransactionsFlowUseCase = testDependencies.getAccountsInTransactionsFlowUseCase,
            getCategoriesInTransactionsFlowUseCase = testDependencies.getCategoriesInTransactionsFlowUseCase,
            getAllTransactionForValuesUseCase = testDependencies.getAllTransactionForValuesUseCase,
            getOldestTransactionTimestampUseCase = testDependencies.getOldestTransactionTimestampUseCase,
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

            result.isBackHandlerEnabled.shouldBeFalse()
            result.isBottomSheetVisible.shouldBeFalse()
            result.isInSelectionMode.shouldBeFalse()
            result.isLoading.shouldBeTrue()
            result.isSearchSortAndFilterVisible.shouldBeFalse()
            result.selectedFilter.shouldBe(
                expected = Filter(),
            )
            result.selectedTransactions.shouldBeEmpty()
            result.sortOptions.shouldBeEmpty()
            result.transactionForValues.shouldBeEmpty()
            result.accounts.shouldBeEmpty()
            result.expenseCategories.shouldBeEmpty()
            result.incomeCategories.shouldBeEmpty()
            result.investmentCategories.shouldBeEmpty()
            result.transactionTypes.shouldBeEmpty()
            result.currentLocalDate.shouldBe(
                expected = LocalDate.MIN,
            )
            result.oldestTransactionLocalDate.shouldBe(
                expected = LocalDate.MIN,
            )
            result.transactionDetailsListItemViewData.shouldBeEmpty()
            result.selectedSortOption.shouldBe(
                expected = SortOption.LATEST_FIRST,
            )
            result.searchTextFieldState.text.toString().shouldBeEmpty()
            result.screenBottomSheetType.shouldBe(
                expected = TransactionsScreenBottomSheetType.None,
            )
        }
    }
    // endregion

    // region fetchData
    @Test
    @Ignore("To Fix")
    fun fetchData_initialState_dataFetched() =
        testDependencies.runTestWithTimeout {
            transactionsScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()

                val result = awaitItem()
                result.isLoading.shouldBeFalse()
                result.transactionForValues.size.shouldBe(
                    expected = 2,
                )
                result.transactionForValues.shouldBe(
                    expected = listOf(
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
                initialState.isLoading.shouldBeTrue()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()

                val result = awaitItem()
                result.accounts.shouldBe(
                    expected = listOf(
                        testDependencies.testAccountEntity2.asExternalModel(),
                        testDependencies.testAccountEntity1.asExternalModel(),
                    ),
                )
                result.expenseCategories.shouldBe(
                    expected = listOf(
                        testDependencies.testCategoryEntity1.asExternalModel(),
                    ),
                )
                result.incomeCategories.shouldBe(
                    expected = listOf(
                        testDependencies.testCategoryEntity2.asExternalModel(),
                    ),
                )
                result.investmentCategories.shouldBeEmpty()
                result.oldestTransactionLocalDate.shouldBe(
                    expected = LocalDate.of(
                        2024,
                        5,
                        20
                    ),
                )
                result.transactionDetailsListItemViewData.size.shouldBe(
                    expected = 2,
                )
                result.transactionDetailsListItemViewData["23 Aug, 2025 (Saturday)"]?.size.shouldBe(
                    expected = 1,
                )
                result.transactionDetailsListItemViewData["20 May, 2024 (Monday)"]?.size.shouldBe(
                    expected = 1,
                )
                result.transactionDetailsListItemViewData.shouldBe(
                    expected = mapOf(
                        "23 Aug, 2025 (Saturday)" to listOf(
                            TransactionListItemData(
                                isDeleteButtonEnabled = false,
                                isDeleteButtonVisible = true,
                                isEditButtonVisible = false,
                                isExpanded = false,
                                isInSelectionMode = false,
                                isLoading = false,
                                isRefundButtonVisible = false,
                                isSelected = false,
                                transactionId = 102,
                                amountColor = MyColor.ERROR,
                                amountText = "- ₹1,000",
                                dateAndTimeText = "23 Aug, 2025 at 08:29 AM",
                                emoji = "💰",
                                accountFromName = "test-account-102",
                                accountToName = null,
                                title = "test-transaction-102",
                                transactionForText = "test-transaction-for-102",
                            ),
                        ),
                        "20 May, 2024 (Monday)" to listOf(
                            TransactionListItemData(
                                isDeleteButtonEnabled = false,
                                isDeleteButtonVisible = true,
                                isEditButtonVisible = false,
                                isExpanded = false,
                                isInSelectionMode = false,
                                isLoading = false,
                                isRefundButtonVisible = false,
                                isSelected = false,
                                transactionId = 101,
                                amountColor = MyColor.ERROR,
                                amountText = "- ₹1,000",
                                dateAndTimeText = "20 May, 2024 at 08:29 AM",
                                emoji = "💳",
                                accountFromName = "test-account-101",
                                accountToName = null,
                                title = "test-transaction-101",
                                transactionForText = "test-transaction-for-101",
                            ),
                        ),
                    ),
                )
            }
        }
    // endregion

    // region state events
    @Test
    fun duplicateTransaction() = testDependencies.runTestWithTimeout {
        transactionsScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.isLoading.shouldBeTrue()
            val fetchDataCompletedState = awaitItem()
            fetchDataCompletedState.isLoading.shouldBeFalse()
            val observeDataCompletedState = awaitItem()
            observeDataCompletedState.transactionDetailsListItemViewData.shouldBe(
                expected = mapOf(
                    "23 Aug, 2025 (Saturday)" to listOf(
                        TransactionListItemData(
                            isDeleteButtonEnabled = false,
                            isDeleteButtonVisible = true,
                            isEditButtonVisible = false,
                            isExpanded = false,
                            isInSelectionMode = false,
                            isLoading = false,
                            isRefundButtonVisible = false,
                            isSelected = false,
                            transactionId = 102,
                            amountColor = MyColor.ERROR,
                            amountText = "- ₹1,000",
                            dateAndTimeText = "23 Aug, 2025 at 08:29 AM",
                            emoji = "💰",
                            accountFromName = "test-account-102",
                            accountToName = null,
                            title = "test-transaction-102",
                            transactionForText = "test-transaction-for-102",
                        ),
                    ),
                    "20 May, 2024 (Monday)" to listOf(
                        TransactionListItemData(
                            isDeleteButtonEnabled = false,
                            isDeleteButtonVisible = true,
                            isEditButtonVisible = false,
                            isExpanded = false,
                            isInSelectionMode = false,
                            isLoading = false,
                            isRefundButtonVisible = false,
                            isSelected = false,
                            transactionId = 101,
                            amountColor = MyColor.ERROR,
                            amountText = "- ₹1,000",
                            dateAndTimeText = "20 May, 2024 at 08:29 AM",
                            emoji = "💳",
                            accountFromName = "test-account-101",
                            accountToName = null,
                            title = "test-transaction-101",
                            transactionForText = "test-transaction-for-101",
                        ),
                    ),
                ),
            )

            transactionsScreenViewModel.uiStateEvents.addToSelectedTransactions(
                testDependencies.testTransactionId1,
            )
            transactionsScreenViewModel.uiStateEvents.duplicateTransaction()

            val updateScreenBottomSheetTypeCompletedState = awaitItem()
            val updateScreenSnackbarTypeCompletedState = awaitItem()
            val result = awaitItem()
            result.accounts.shouldBe(
                expected = listOf(
                    testDependencies.testAccountEntity2.asExternalModel(),
                    testDependencies.testAccountEntity1.asExternalModel(),
                ),
            )
            result.expenseCategories.shouldBe(
                expected = listOf(
                    testDependencies.testCategoryEntity1.asExternalModel(),
                ),
            )
            result.incomeCategories.shouldBe(
                expected = listOf(
                    testDependencies.testCategoryEntity2.asExternalModel(),
                ),
            )
            result.investmentCategories.shouldBeEmpty()
            result.oldestTransactionLocalDate.shouldBe(
                expected = LocalDate.of(
                    2024,
                    5,
                    20
                ),
            )
            result.transactionDetailsListItemViewData.size.shouldBe(
                expected = 2,
            )
            result.transactionDetailsListItemViewData["23 Aug, 2025 (Saturday)"]?.size.shouldBe(
                expected = 1,
            )
            result.transactionDetailsListItemViewData["20 May, 2024 (Monday)"]?.size.shouldBe(
                expected = 2,
            )
            result.transactionDetailsListItemViewData.shouldBe(
                expected = mapOf(
                    "23 Aug, 2025 (Saturday)" to listOf(
                        TransactionListItemData(
                            isDeleteButtonEnabled = false,
                            isDeleteButtonVisible = true,
                            isEditButtonVisible = false,
                            isExpanded = false,
                            isInSelectionMode = false,
                            isLoading = false,
                            isRefundButtonVisible = false,
                            isSelected = false,
                            transactionId = 102,
                            amountColor = MyColor.ERROR,
                            amountText = "- ₹1,000",
                            dateAndTimeText = "23 Aug, 2025 at 08:29 AM",
                            emoji = "💰",
                            accountFromName = "test-account-102",
                            accountToName = null,
                            title = "test-transaction-102",
                            transactionForText = "test-transaction-for-102",
                        ),
                    ),
                    "20 May, 2024 (Monday)" to listOf(
                        TransactionListItemData(
                            isDeleteButtonEnabled = false,
                            isDeleteButtonVisible = true,
                            isEditButtonVisible = false,
                            isExpanded = false,
                            isInSelectionMode = false,
                            isLoading = false,
                            isRefundButtonVisible = false,
                            isSelected = false,
                            transactionId = 1,
                            amountColor = MyColor.ERROR,
                            amountText = "- ₹1,000",
                            dateAndTimeText = "20 May, 2024 at 08:29 AM",
                            emoji = "💳",
                            accountFromName = "test-account-101",
                            accountToName = null,
                            title = "test-transaction-101",
                            transactionForText = "test-transaction-for-101",
                        ),
                        TransactionListItemData(
                            isDeleteButtonEnabled = false,
                            isDeleteButtonVisible = true,
                            isEditButtonVisible = false,
                            isExpanded = false,
                            isInSelectionMode = false,
                            isLoading = false,
                            isRefundButtonVisible = false,
                            isSelected = false,
                            transactionId = 101,
                            amountColor = MyColor.ERROR,
                            amountText = "- ₹1,000",
                            dateAndTimeText = "20 May, 2024 at 08:29 AM",
                            emoji = "💳",
                            accountFromName = "test-account-101",
                            accountToName = null,
                            title = "test-transaction-101",
                            transactionForText = "test-transaction-for-101",
                        ),
                    ),
                ),
            )
        }
    }
    // endregion
}
