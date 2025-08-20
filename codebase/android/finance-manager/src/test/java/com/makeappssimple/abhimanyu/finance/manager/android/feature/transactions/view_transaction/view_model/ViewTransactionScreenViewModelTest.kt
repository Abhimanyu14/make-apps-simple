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
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.AmountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.view_transaction.bottom_sheet.ViewTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class ViewTransactionScreenViewModelTest {
    // region test setup
    private val savedStateHandle: SavedStateHandle = SavedStateHandle(
        initialState = mapOf(
            "transactionId" to "123",
        ),
    )

    private lateinit var testDependencies: TestDependencies
    private lateinit var viewTransactionScreenViewModel: ViewTransactionScreenViewModel

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        testDependencies.testScope.launch {
            testDependencies.fakeAccountDao.insertAccounts(
                AccountEntity(
                    balanceAmount = AmountEntity(
                        value = 1000,
                    ),
                    id = 1,
                    type = AccountType.E_WALLET,
                    name = "test-account",
                ),
            )
            testDependencies.fakeCategoryDao.insertCategories(
                CategoryEntity(
                    id = 1,
                    emoji = "💳",
                    title = "test-category",
                    transactionType = TransactionType.EXPENSE,
                ),
            )
            testDependencies.fakeTransactionForDao.insertTransactionForValues(
                TransactionForEntity(
                    id = 1,
                    title = "test-transaction-for",
                ),
            )
            testDependencies.fakeTransactionDao.insertTransaction(
                TransactionEntity(
                    amount = AmountEntity(
                        value = 100,
                    ),
                    categoryId = 1,
                    id = 123,
                    accountFromId = 1,
                    transactionForId = 1,
                    creationTimestamp = 100L,
                    transactionTimestamp = 100L,
                    title = "test-transaction",
                ),
            )
        }
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

            assertThat(result.isBottomSheetVisible).isFalse()
            assertThat(result.isLoading).isTrue()
            assertThat(result.refundTransactionsListItemData).isEmpty()
            assertThat(result.originalTransactionListItemData).isNull()
            assertThat(result.transactionListItemData).isNull()
            assertThat(result.screenBottomSheetType).isEqualTo(
                ViewTransactionScreenBottomSheetType.None
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
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.screenBottomSheetType).isEqualTo(
                    ViewTransactionScreenBottomSheetType.None
                )
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
                assertThat(postDataFetchCompletion.screenBottomSheetType).isEqualTo(
                    ViewTransactionScreenBottomSheetType.None
                )
                viewTransactionScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    ViewTransactionScreenBottomSheetType.DeleteConfirmation
                )
                assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                    ViewTransactionScreenBottomSheetType.DeleteConfirmation
                )

                viewTransactionScreenViewModel.uiStateEvents.resetScreenBottomSheetType()

                val result = awaitItem()
                assertThat(result.screenBottomSheetType).isEqualTo(
                    ViewTransactionScreenBottomSheetType.None
                )
            }
        }

    @Test
    fun updateScreenBottomSheetType_shouldUpdateScreenBottomSheetType() =
        testDependencies.runTestWithTimeout {
            viewTransactionScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.screenBottomSheetType).isEqualTo(
                    ViewTransactionScreenBottomSheetType.None
                )
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
                assertThat(postDataFetchCompletion.screenBottomSheetType).isEqualTo(
                    ViewTransactionScreenBottomSheetType.None
                )

                viewTransactionScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    ViewTransactionScreenBottomSheetType.DeleteConfirmation
                )

                val result = awaitItem()
                assertThat(result.screenBottomSheetType).isEqualTo(
                    ViewTransactionScreenBottomSheetType.DeleteConfirmation
                )
            }
        }
    // endregion
}
