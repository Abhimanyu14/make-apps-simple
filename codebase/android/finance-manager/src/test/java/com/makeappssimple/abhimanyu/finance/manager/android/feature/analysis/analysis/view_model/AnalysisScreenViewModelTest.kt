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
import com.makeappssimple.abhimanyu.common.core.coroutines.test.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.log_kit.fake.FakeLogKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.date_time.DateTimeKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction_data.TransactionDataRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction_data.TransactionDataRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.TransactionDataDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeAccountDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeCategoryDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeTransactionDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeTransactionDataDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeTransactionForDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.datasource.CommonDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.datasource.CommonDataSourceImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.AmountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.transaction_provider.DatabaseTransactionProvider
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.transaction_provider.fake.FakeDatabaseTransactionProviderImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegateImpl
import com.makeappssimple.abhimanyu.finance.manager.android.feature.analysis.analysis.bottom_sheet.AnalysisScreenBottomSheetType
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.time.Duration.Companion.seconds

internal class AnalysisScreenViewModelTest {
    // region coroutines setup
    private val testCoroutineDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(
        context = testCoroutineDispatcher + Job(),
    )
    private val testDispatcherProvider = TestDispatcherProviderImpl(
        testDispatcher = testCoroutineDispatcher,
    )
    // endregion

    // region test setup
    private val navigationKit: NavigationKit = NavigationKitImpl(
        coroutineScope = testScope.backgroundScope,
    )
    private val screenUIStateDelegate: ScreenUIStateDelegate =
        ScreenUIStateDelegateImpl(
            coroutineScope = testScope.backgroundScope,
        )
    private val dateTimeKit: DateTimeKit = DateTimeKitImpl()
    private val fakeAccountDao: AccountDao = FakeAccountDaoImpl()
    private val fakeCategoryDao: CategoryDao = FakeCategoryDaoImpl()
    private val fakeDatabaseTransactionProvider: DatabaseTransactionProvider =
        FakeDatabaseTransactionProviderImpl()
    private val fakeTransactionDao: TransactionDao = FakeTransactionDaoImpl()
    private val fakeTransactionForDao: TransactionForDao =
        FakeTransactionForDaoImpl()
    private val fakeTransactionDataDao: TransactionDataDao =
        FakeTransactionDataDaoImpl(
            accountDao = fakeAccountDao,
            categoryDao = fakeCategoryDao,
            transactionDao = fakeTransactionDao,
            transactionForDao = fakeTransactionForDao,
        )
    private val commonDataSource: CommonDataSource = CommonDataSourceImpl(
        accountDao = fakeAccountDao,
        categoryDao = fakeCategoryDao,
        databaseTransactionProvider = fakeDatabaseTransactionProvider,
        transactionDao = fakeTransactionDao,
        transactionDataDao = fakeTransactionDataDao,
        transactionForDao = fakeTransactionForDao,
    )
    private val transactionDataRepository: TransactionDataRepository =
        TransactionDataRepositoryImpl(
            commonDataSource = commonDataSource,
            dispatcherProvider = testDispatcherProvider,
            transactionDataDao = fakeTransactionDataDao,
        )
    private val getAllTransactionDataUseCase = GetAllTransactionDataUseCase(
        transactionDataRepository = transactionDataRepository,
    )
    private val logKit: LogKit = FakeLogKitImpl()

    private lateinit var analysisScreenViewModel: AnalysisScreenViewModel

    @Before
    fun setUp() {
        testScope.launch {
            fakeAccountDao.insertAccounts(
                AccountEntity(
                    balanceAmount = AmountEntity(
                        value = 1000,
                    ),
                    id = 1,
                    type = AccountType.E_WALLET,
                    name = "test-account",
                ),
            )
            fakeCategoryDao.insertCategories(
                CategoryEntity(
                    id = 1,
                    emoji = "💳",
                    title = "test-category",
                    transactionType = TransactionType.EXPENSE,
                ),
            )
            fakeTransactionForDao.insertTransactionForValues(
                TransactionForEntity(
                    id = 1,
                    title = "test-transaction-for",
                ),
            )
            fakeTransactionDao.insertTransaction(
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
            navigationKit = navigationKit,
            screenUIStateDelegate = screenUIStateDelegate,
            coroutineScope = testScope.backgroundScope,
            dateTimeKit = dateTimeKit,
            getAllTransactionDataUseCase = getAllTransactionDataUseCase,
            logKit = logKit,
        )
        analysisScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = runTestWithTimeout {
        analysisScreenViewModel.uiState.test {
            val result = awaitItem()

            assertThat(result.screenBottomSheetType).isEqualTo(
                AnalysisScreenBottomSheetType.None
            )
            assertThat(result.isBottomSheetVisible).isFalse()
            assertThat(result.isLoading).isTrue()
            assertThat(result.selectedFilter as Filter).isEqualTo(Filter())
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

    // region common
    private fun runTestWithTimeout(
        testBody: suspend TestScope.() -> Unit,
    ) {
        testScope.runTest(
            timeout = 3.seconds,
        ) {
            testBody()
        }
    }
    // endregion
}
