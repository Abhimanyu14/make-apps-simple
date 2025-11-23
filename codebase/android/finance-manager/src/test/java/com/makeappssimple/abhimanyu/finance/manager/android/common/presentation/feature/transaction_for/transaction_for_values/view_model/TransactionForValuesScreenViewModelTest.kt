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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.transaction_for_values.view_model

import app.cash.turbine.test
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.transaction_for_values.bottom_sheet.TransactionForValuesScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.listitem.transaction_for.TransactionForListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import kotlin.test.Test

internal class TransactionForValuesScreenViewModelTest {
    // region test setup
    private lateinit var testDependencies: TestDependencies
    private lateinit var transactionForValuesScreenViewModel: TransactionForValuesScreenViewModel

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        transactionForValuesScreenViewModel =
            TransactionForValuesScreenViewModel(
                navigationKit = testDependencies.navigationKit,
                coroutineScope = testDependencies.testScope.backgroundScope,
                getAllTransactionForValuesFlowUseCase = testDependencies.getAllTransactionForValuesFlowUseCase,
                checkIfTransactionForValuesAreUsedInTransactionsUseCase = testDependencies.checkIfTransactionForValuesAreUsedInTransactionsUseCase,
                deleteTransactionForByIdUseCase = testDependencies.deleteTransactionForByIdUseCase,
                logKit = testDependencies.logKit,
            )
        transactionForValuesScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testDependencies.testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = testDependencies.runTestWithTimeout {
        transactionForValuesScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.isLoading.shouldBeFalse()

            val result = awaitItem()
            result.isBottomSheetVisible.shouldBeFalse()
            result.isLoading.shouldBeFalse()
            result.transactionForListItemDataList.shouldBe(
                expected = listOf(
                    TransactionForListItemData(
                        transactionForId = 101,
                        isMoreOptionsIconButtonVisible = true,
                        isDeleteBottomSheetMenuItemVisible = false,
                        title = "Test-transaction-for-101",
                    ),
                    TransactionForListItemData(
                        transactionForId = 102,
                        isMoreOptionsIconButtonVisible = true,
                        isDeleteBottomSheetMenuItemVisible = false,
                        title = "Test-transaction-for-102",
                    ),
                ),
            )
            result.screenBottomSheetType.shouldBe(
                expected = TransactionForValuesScreenBottomSheetType.None,
            )
        }
    }
    // endregion

    // region uiStateAndStateEvents
    @Test
    fun refreshUiState_screenBottomSheetTypeIsNone_isBottomSheetVisibleIsFalse() =
        testDependencies.runTestWithTimeout {
            transactionForValuesScreenViewModel.uiState.test {
                val result = awaitItem()
                result.screenBottomSheetType.shouldBe(
                    expected = TransactionForValuesScreenBottomSheetType.None,
                )
                result.isBottomSheetVisible.shouldBeFalse()
            }
        }

    @Test
    @Ignore("To Fix")
    fun refreshUiState_screenBottomSheetTypeIsDeleteConfirmation_isBottomSheetVisibleIsTrue() =
        testDependencies.runTestWithTimeout {
            transactionForValuesScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.screenBottomSheetType.shouldBe(
                    expected = TransactionForValuesScreenBottomSheetType.None,
                )

                transactionForValuesScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    TransactionForValuesScreenBottomSheetType.DeleteConfirmation
                )
                val result = awaitItem()
                result.isLoading.shouldBeFalse()
                result.isBottomSheetVisible.shouldBeTrue()
            }
        }
    // endregion

    // region state events
    @Test
    fun deleteTransactionFor_shouldDeleteAndResetId() =
        testDependencies.runTestWithTimeout {
            transactionForValuesScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeFalse()
                val observeDataCompletedState = awaitItem()
                observeDataCompletedState.isLoading.shouldBeFalse()
                observeDataCompletedState.transactionForListItemDataList.size.shouldBe(
                    expected = 2,
                )
                observeDataCompletedState.transactionForListItemDataList.shouldBe(
                    expected = listOf(
                        TransactionForListItemData(
                            transactionForId = 101,
                            isMoreOptionsIconButtonVisible = true,
                            isDeleteBottomSheetMenuItemVisible = false,
                            title = "Test-transaction-for-101",
                        ),
                        TransactionForListItemData(
                            transactionForId = 102,
                            isMoreOptionsIconButtonVisible = true,
                            isDeleteBottomSheetMenuItemVisible = false,
                            title = "Test-transaction-for-102",
                        ),
                    ),
                )

                transactionForValuesScreenViewModel.uiStateEvents.updateTransactionForIdToDelete(
                    testDependencies.testTransactionForId1
                )
                transactionForValuesScreenViewModel.uiStateEvents.deleteTransactionFor()

                val result = awaitItem()
                result.isLoading.shouldBeFalse()
                result.transactionForListItemDataList.size.shouldBe(
                    expected = 1,
                )
            }
        }

    @Test
    @Ignore("To Fix")
    fun resetScreenBottomSheetType_shouldResetValue() =
        testDependencies.runTestWithTimeout {
            transactionForValuesScreenViewModel.uiState.test {
                awaitItem().screenBottomSheetType.shouldBe(
                    expected = TransactionForValuesScreenBottomSheetType.None,
                )
                transactionForValuesScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    TransactionForValuesScreenBottomSheetType.DeleteConfirmation
                )
                awaitItem().screenBottomSheetType.shouldBe(
                    expected = TransactionForValuesScreenBottomSheetType.DeleteConfirmation,
                )

                transactionForValuesScreenViewModel.uiStateEvents.resetScreenBottomSheetType()

                awaitItem().screenBottomSheetType.shouldBe(
                    expected = TransactionForValuesScreenBottomSheetType.None,
                )
            }
        }

    @Test
    @Ignore("To Fix")
    fun updateScreenBottomSheetType_shouldUpdateValue() =
        testDependencies.runTestWithTimeout {
            transactionForValuesScreenViewModel.uiState.test {
                awaitItem().screenBottomSheetType.shouldBe(
                    expected = TransactionForValuesScreenBottomSheetType.None,
                )

                transactionForValuesScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    TransactionForValuesScreenBottomSheetType.DeleteConfirmation
                )

                awaitItem().screenBottomSheetType.shouldBe(
                    expected = TransactionForValuesScreenBottomSheetType.DeleteConfirmation,
                )
            }
        }
    // endregion

    // region observeData
    @Test
    fun observeData_shouldUpdateUiState() =
        testDependencies.runTestWithTimeout {
            transactionForValuesScreenViewModel.uiState.test {
                val result = awaitItem()
                result.transactionForListItemDataList.shouldBeEmpty()
            }
        }
    // endregion
}
