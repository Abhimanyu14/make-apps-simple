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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.home.home.view_model

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.common.core.coroutines.test.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.common.core.json_writer.JsonWriterKit
import com.makeappssimple.abhimanyu.common.core.json_writer.fake.FakeJsonWriterKitImpl
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.log_kit.fake.FakeLogKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.chart.compose_pie.data.PieChartData
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.date_time.DateTimeKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.account.AccountRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.category.CategoryRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction.TransactionRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction_data.TransactionDataRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction_data.TransactionDataRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction_for.TransactionForRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction_for.TransactionForRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.common.ShouldShowBackupCardUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetRecentTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetTransactionByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetTransactionsBetweenTimestampsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.GetAllTransactionForValuesUseCase
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
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.transaction_provider.DatabaseTransactionProvider
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.transaction_provider.fake.FakeDatabaseTransactionProviderImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.FinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.fake.FakeFinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegateImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.overview_card.OverviewCardViewModelData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

internal class HomeScreenViewModelTest {
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
    private val fakeAccountDao: AccountDao = FakeAccountDaoImpl()
    private val accountRepository: AccountRepository = AccountRepositoryImpl(
        accountDao = fakeAccountDao,
        dispatcherProvider = testDispatcherProvider,
    )
    private val getAccountsTotalBalanceAmountValueUseCase =
        GetAccountsTotalBalanceAmountValueUseCase(
            accountRepository = accountRepository,
        )
    private val getAccountsTotalMinimumBalanceAmountValueUseCase =
        GetAccountsTotalMinimumBalanceAmountValueUseCase(
            accountRepository = accountRepository,
        )
    private val navigationKit: NavigationKit = NavigationKitImpl(
        coroutineScope = testScope.backgroundScope,
    )
    private val screenUIStateDelegate: ScreenUIStateDelegate =
        ScreenUIStateDelegateImpl(
            coroutineScope = testScope.backgroundScope,
        )
    private val financeManagerPreferencesDataSource: FinanceManagerPreferencesDataSource =
        FakeFinanceManagerPreferencesDataSource()
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository =
        FinanceManagerPreferencesRepositoryImpl(
            dispatcherProvider = testDispatcherProvider,
            financeManagerPreferencesDataSource = financeManagerPreferencesDataSource,
        )
    private val shouldShowBackupCardUseCase: ShouldShowBackupCardUseCase =
        ShouldShowBackupCardUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    private val dateTimeKit: DateTimeKit = DateTimeKitImpl()
    private val fakeCategoryDao: CategoryDao = FakeCategoryDaoImpl()
    private val categoryRepository: CategoryRepository = CategoryRepositoryImpl(
        categoryDao = fakeCategoryDao,
        dispatcherProvider = testDispatcherProvider,
    )
    private val getAllCategoriesUseCase = GetAllCategoriesUseCase(
        categoryRepository = categoryRepository,
    )
    private val getAllAccountsUseCase = GetAllAccountsUseCase(
        accountRepository = accountRepository,
    )
    private val fakeTransactionForDao: TransactionForDao =
        FakeTransactionForDaoImpl()
    private val transactionForRepository: TransactionForRepository =
        TransactionForRepositoryImpl(
            dispatcherProvider = testDispatcherProvider,
            transactionForDao = fakeTransactionForDao,
        )
    private val getAllTransactionForValuesUseCase =
        GetAllTransactionForValuesUseCase(
            transactionForRepository = transactionForRepository,
        )
    private val databaseTransactionProvider: DatabaseTransactionProvider =
        FakeDatabaseTransactionProviderImpl()
    private val fakeTransactionDao: TransactionDao = FakeTransactionDaoImpl()
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
        databaseTransactionProvider = databaseTransactionProvider,
        transactionDao = fakeTransactionDao,
        transactionDataDao = fakeTransactionDataDao,
        transactionForDao = fakeTransactionForDao,
    )
    private val transactionRepository: TransactionRepository =
        TransactionRepositoryImpl(
            commonDataSource = commonDataSource,
            dispatcherProvider = testDispatcherProvider,
            transactionDao = fakeTransactionDao,
        )
    private val getAllTransactionsUseCase = GetAllTransactionsUseCase(
        transactionRepository = transactionRepository,
    )
    private val jsonWriterKit: JsonWriterKit = FakeJsonWriterKitImpl()
    private val backupDataUseCase: BackupDataUseCase = BackupDataUseCase(
        dateTimeKit = dateTimeKit,
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        getAllCategoriesUseCase = getAllCategoriesUseCase,
        getAllAccountsUseCase = getAllAccountsUseCase,
        getAllTransactionForValuesUseCase = getAllTransactionForValuesUseCase,
        getAllTransactionsUseCase = getAllTransactionsUseCase,
        jsonWriterKit = jsonWriterKit,
    )
    private val transactionDataRepository: TransactionDataRepository =
        TransactionDataRepositoryImpl(
            commonDataSource = commonDataSource,
            dispatcherProvider = testDispatcherProvider,
            transactionDataDao = fakeTransactionDataDao,
        )
    private val getRecentTransactionDataFlowUseCase: GetRecentTransactionDataFlowUseCase =
        GetRecentTransactionDataFlowUseCase(
            transactionDataRepository = transactionDataRepository,
        )
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase =
        GetTransactionByIdUseCase(
            transactionRepository = transactionRepository,
        )
    private val getTransactionsBetweenTimestampsUseCase =
        GetTransactionsBetweenTimestampsUseCase(
            transactionRepository = transactionRepository,
        )
    private val logKit: LogKit = FakeLogKitImpl()

    private lateinit var homeScreenViewModel: HomeScreenViewModel

    @Before
    fun setUp() {
        homeScreenViewModel = HomeScreenViewModel(
            getAccountsTotalBalanceAmountValueUseCase = getAccountsTotalBalanceAmountValueUseCase,
            getAccountsTotalMinimumBalanceAmountValueUseCase = getAccountsTotalMinimumBalanceAmountValueUseCase,
            navigationKit = navigationKit,
            screenUIStateDelegate = screenUIStateDelegate,
            shouldShowBackupCardUseCase = shouldShowBackupCardUseCase,
            backupDataUseCase = backupDataUseCase,
            coroutineScope = testScope.backgroundScope,
            dateTimeKit = dateTimeKit,
            getRecentTransactionDataFlowUseCase = getRecentTransactionDataFlowUseCase,
            getTransactionByIdUseCase = getTransactionByIdUseCase,
            getTransactionsBetweenTimestampsUseCase = getTransactionsBetweenTimestampsUseCase,
            logKit = logKit,
        )
        homeScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = runTestWithTimeout {
        homeScreenViewModel.uiState.test {
            val result = awaitItem()

            assertThat(result.isBackupCardVisible).isFalse()
            assertThat(result.isBalanceVisible).isFalse()
            assertThat(result.isLoading).isTrue()
            assertThat(result.isRecentTransactionsTrailingTextVisible).isFalse()
            assertThat(result.overviewTabSelectionIndex).isEqualTo(0)
            assertThat(result.transactionListItemDataList).isEmpty()
            assertThat(result.accountsTotalBalanceAmountValue).isEqualTo(0)
            assertThat(result.accountsTotalMinimumBalanceAmountValue).isEqualTo(
                0
            )
            assertThat(result.overviewCardData).isEqualTo(
                OverviewCardViewModelData()
            )
            assertThat(result.pieChartData).isEqualTo(PieChartData())
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
