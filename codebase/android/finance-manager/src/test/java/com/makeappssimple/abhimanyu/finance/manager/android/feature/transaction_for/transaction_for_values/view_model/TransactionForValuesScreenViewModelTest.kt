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

@file:OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.transaction_for_values.view_model

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.transaction_for_values.bottom_sheet.TransactionForValuesScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
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
                screenUIStateDelegate = testDependencies.screenUIStateDelegate,
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
            val result = awaitItem()
            assertThat(result.isBottomSheetVisible).isFalse()
            assertThat(result.isLoading).isTrue()
            assertThat(result.transactionForListItemDataList).isEmpty()
            assertThat(result.screenBottomSheetType).isEqualTo(
                TransactionForValuesScreenBottomSheetType.None
            )
        }
    }
    // endregion

    // region uiStateAndStateEvents
    @Test
    fun updateUiStateAndStateEvents_screenBottomSheetTypeIsNone_isBottomSheetVisibleIsFalse() =
        testDependencies.runTestWithTimeout {
            transactionForValuesScreenViewModel.uiState.test {
                val result = awaitItem()
                assertThat(result.screenBottomSheetType).isEqualTo(
                    TransactionForValuesScreenBottomSheetType.None
                )
                assertThat(result.isBottomSheetVisible).isFalse()
            }
        }

    @Test
    fun updateUiStateAndStateEvents_screenBottomSheetTypeIsDeleteConfirmation_isBottomSheetVisibleIsTrue() =
        testDependencies.runTestWithTimeout {
            transactionForValuesScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.screenBottomSheetType).isEqualTo(
                    TransactionForValuesScreenBottomSheetType.None
                )

                transactionForValuesScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    TransactionForValuesScreenBottomSheetType.DeleteConfirmation
                )
                val result = awaitItem()
                assertThat(result.isLoading).isFalse()
                assertThat(result.isBottomSheetVisible).isTrue()
            }
        }
    // endregion

    // region state events
    @Test
    fun deleteTransactionFor_shouldDeleteAndResetId() =
        testDependencies.runTestWithTimeout {
            transactionForValuesScreenViewModel.uiState.test {
                assertThat(awaitItem().isLoading).isTrue()
                val fetchDataCompletedState = awaitItem()
                assertThat(fetchDataCompletedState.isLoading).isFalse()
                assertThat(fetchDataCompletedState.transactionForListItemDataList).isEmpty()
                val observeDataCompletedState = awaitItem()
                assertThat(fetchDataCompletedState.isLoading).isFalse()
                assertThat(observeDataCompletedState.transactionForListItemDataList.size).isEqualTo(
                    2
                )

                transactionForValuesScreenViewModel.uiStateEvents.updateTransactionForIdToDelete(
                    testDependencies.testTransactionForId1
                )
                transactionForValuesScreenViewModel.uiStateEvents.deleteTransactionFor()

                val result = awaitItem()
                assertThat(result.isLoading).isFalse()
                assertThat(result.transactionForListItemDataList.size).isEqualTo(
                    1
                )
            }
        }

    @Test
    fun resetScreenBottomSheetType_shouldResetValue() =
        testDependencies.runTestWithTimeout {
            transactionForValuesScreenViewModel.uiState.test {
                assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                    TransactionForValuesScreenBottomSheetType.None
                )
                transactionForValuesScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    TransactionForValuesScreenBottomSheetType.DeleteConfirmation
                )
                assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                    TransactionForValuesScreenBottomSheetType.DeleteConfirmation
                )

                transactionForValuesScreenViewModel.uiStateEvents.resetScreenBottomSheetType()

                assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                    TransactionForValuesScreenBottomSheetType.None
                )
            }
        }

    @Test
    fun updateScreenBottomSheetType_shouldUpdateValue() =
        testDependencies.runTestWithTimeout {
            transactionForValuesScreenViewModel.uiState.test {
                assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                    TransactionForValuesScreenBottomSheetType.None
                )

                transactionForValuesScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    TransactionForValuesScreenBottomSheetType.DeleteConfirmation
                )

                assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                    TransactionForValuesScreenBottomSheetType.DeleteConfirmation
                )
            }
        }
    // endregion

    // region observeData
    @Test
    fun observeData_shouldUpdateUiState() =
        testDependencies.runTestWithTimeout {
            transactionForValuesScreenViewModel.observeData()
            transactionForValuesScreenViewModel.uiState.test {
                val result = awaitItem()
                assertThat(result.transactionForListItemDataList).isEmpty()
            }
        }
    // endregion
}
