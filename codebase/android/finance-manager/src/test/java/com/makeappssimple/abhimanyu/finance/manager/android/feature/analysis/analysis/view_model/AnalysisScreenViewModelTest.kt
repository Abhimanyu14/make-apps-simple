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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.analysis.analysis.view_model

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.AmountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.finance.manager.android.feature.analysis.analysis.bottom_sheet.AnalysisScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.test.TestDependencies
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

internal class AnalysisScreenViewModelTest {
    // region test setup
    private lateinit var testDependencies: TestDependencies
    private lateinit var analysisScreenViewModel: AnalysisScreenViewModel

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
        analysisScreenViewModel = AnalysisScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            screenUIStateDelegate = testDependencies.screenUIStateDelegate,
            coroutineScope = testDependencies.testScope.backgroundScope,
            dateTimeKit = testDependencies.dateTimeKit,
            getAllTransactionDataUseCase = testDependencies.getAllTransactionDataUseCase,
            logKit = testDependencies.logKit,
        )
        analysisScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testDependencies.testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = testDependencies.runTestWithTimeout {
        analysisScreenViewModel.uiState.test {
            val result = awaitItem()

            assertThat(result.screenBottomSheetType).isEqualTo(
                AnalysisScreenBottomSheetType.None
            )
            assertThat(result.isBottomSheetVisible).isFalse()
            assertThat(result.isLoading).isTrue()
            assertThat(result.selectedFilter).isEqualTo(Filter())
            assertThat(result.analysisListItemData).isEmpty()
            assertThat(result.transactionTypesChipUIData).isEmpty()
            assertThat(result.selectedTransactionTypeIndex).isEqualTo(0)
            assertThat(result.defaultStartLocalDate).isEqualTo(LocalDate.MIN)
            assertThat(result.defaultEndLocalDate).isEqualTo(LocalDate.MIN)
            assertThat(result.startOfCurrentMonthLocalDate).isEqualTo(LocalDate.MIN)
            assertThat(result.startOfCurrentYearLocalDate).isEqualTo(LocalDate.MIN)
        }
    }
    // endregion
}
