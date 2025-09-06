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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.view_transaction.view_model

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.FinanceManagerNavigationDirections
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.view_transaction.bottom_sheet.ViewTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class ViewTransactionScreenViewModelTest {
    // region test setup
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var testDependencies: TestDependencies
    private lateinit var viewTransactionScreenViewModel: ViewTransactionScreenViewModel

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        savedStateHandle = SavedStateHandle(
            initialState = mapOf(
                "transactionId" to testDependencies.testTransactionId1.toString(),
            ),
        )
        viewTransactionScreenViewModel = ViewTransactionScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            savedStateHandle = savedStateHandle,
            uriDecoder = testDependencies.uriDecoder,
            coroutineScope = testDependencies.testScope.backgroundScope,
            dateTimeKit = testDependencies.dateTimeKit,
            deleteTransactionUseByIdCase = testDependencies.deleteTransactionUseByIdCase,
            getTransactionDataByIdUseCase = testDependencies.getTransactionDataByIdUseCase,
            logKit = testDependencies.logKit,
        )
        viewTransactionScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testDependencies.testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = testDependencies.runTestWithTimeout {
        viewTransactionScreenViewModel.uiState.test {
            val result = awaitItem()

            result.isBottomSheetVisible.shouldBeFalse()
            result.isLoading.shouldBeTrue()
            result.refundTransactionsListItemData.shouldBeEmpty()
            result.originalTransactionListItemData.shouldBeNull()
            result.transactionListItemData.shouldBeNull()
            result.screenBottomSheetType.shouldBe(
                expected = ViewTransactionScreenBottomSheetType.None,
            )
        }
    }
    // endregion

    // region state events
    @Test
    fun deleteTransaction_transactionIdToDeleteIsNull_shouldThrowException() =
        testDependencies.runTestWithTimeout {
            viewTransactionScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()

                val exception = shouldThrow<IllegalStateException> {
                    viewTransactionScreenViewModel.uiStateEvents.deleteTransaction()
                }
                exception.message.shouldBe(
                    expected = "Transaction ID to delete must not be null.",
                )
            }
        }

    @Test
    fun deleteTransaction_shouldDeleteTransaction() =
        testDependencies.runTestWithTimeout {
            turbineScope {
                val navigationCommandTurbine =
                    testDependencies.navigationKit.command.testIn(
                        scope = backgroundScope,
                    )
                val uiStateTurbine =
                    viewTransactionScreenViewModel.uiState.testIn(
                        scope = backgroundScope,
                    )
                val initialState = uiStateTurbine.awaitItem()
                initialState.isLoading.shouldBeTrue()
                val fetchDataCompletedState = uiStateTurbine.awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()
                viewTransactionScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    ViewTransactionScreenBottomSheetType.DeleteConfirmation
                )
                uiStateTurbine.awaitItem().screenBottomSheetType.shouldBe(
                    expected = ViewTransactionScreenBottomSheetType.DeleteConfirmation,
                )
                viewTransactionScreenViewModel.uiStateEvents.updateTransactionIdToDelete(
                    testDependencies.testTransactionId1,
                )

                viewTransactionScreenViewModel.uiStateEvents.deleteTransaction()

                uiStateTurbine.awaitItem().isLoading.shouldBeTrue()
                navigationCommandTurbine.awaitItem().shouldBe(
                    expected = FinanceManagerNavigationDirections.NavigateUp,
                )
            }
        }

    @Test
    fun resetScreenBottomSheetType_shouldSetBottomSheetTypeToNone() =
        testDependencies.runTestWithTimeout {
            viewTransactionScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                initialState.screenBottomSheetType.shouldBe(
                    expected = ViewTransactionScreenBottomSheetType.None,
                )
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()
                fetchDataCompletedState.screenBottomSheetType.shouldBe(
                    expected = ViewTransactionScreenBottomSheetType.None,
                )
                viewTransactionScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    ViewTransactionScreenBottomSheetType.DeleteConfirmation
                )
                awaitItem().screenBottomSheetType.shouldBe(
                    expected = ViewTransactionScreenBottomSheetType.DeleteConfirmation,
                )

                viewTransactionScreenViewModel.uiStateEvents.resetScreenBottomSheetType()

                val result = awaitItem()
                result.screenBottomSheetType.shouldBe(
                    expected = ViewTransactionScreenBottomSheetType.None,
                )
            }
        }

    @Test
    fun updateScreenBottomSheetType_shouldUpdateScreenBottomSheetType() =
        testDependencies.runTestWithTimeout {
            viewTransactionScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                initialState.screenBottomSheetType.shouldBe(
                    expected = ViewTransactionScreenBottomSheetType.None,
                )
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()
                fetchDataCompletedState.screenBottomSheetType.shouldBe(
                    expected = ViewTransactionScreenBottomSheetType.None,
                )

                viewTransactionScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    ViewTransactionScreenBottomSheetType.DeleteConfirmation
                )

                val result = awaitItem()
                result.screenBottomSheetType.shouldBe(
                    expected = ViewTransactionScreenBottomSheetType.DeleteConfirmation,
                )
            }
        }
    // endregion
}
