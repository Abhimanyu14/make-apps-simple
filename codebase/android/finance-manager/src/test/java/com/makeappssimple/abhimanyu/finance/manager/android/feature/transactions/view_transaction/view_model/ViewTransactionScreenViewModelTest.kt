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
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.view_transaction.bottom_sheet.ViewTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
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
                "transactionId" to testDependencies.testTransactionForId1.toString(),
            ),
        )
        viewTransactionScreenViewModel = ViewTransactionScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            screenUIStateDelegate = testDependencies.screenUIStateDelegate,
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

    // region updateUiStateAndStateEvents
//    @Test
//    fun updateUiStateAndStateEvents_nameIsBlank_ctaIsDisabled() =
//        testDependencies.runTestWithTimeout {
//            val updatedName = "   "
//            val updatedValue = TextFieldValue(
//                text = updatedName,
//            )
//            viewTransactionScreenViewModel.uiState.test {
//                assertThat(awaitItem().isCtaButtonEnabled).isFalse()
//
//                viewTransactionScreenViewModel.uiStateEvents.updateName(
//                    updatedValue
//                )
//
//                val result = awaitItem()
//                assertThat(result.isCtaButtonEnabled).isFalse()
//                assertThat(result.nameError).isEqualTo(
//                    AddAccountScreenNameError.None
//                )
//            }
//        }
    // endregion

    // region state events
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
