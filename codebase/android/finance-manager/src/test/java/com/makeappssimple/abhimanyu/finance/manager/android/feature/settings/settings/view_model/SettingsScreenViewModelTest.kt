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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.settings.view_model

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.common.core.app_version.AppVersionKit
import com.makeappssimple.abhimanyu.common.core.app_version.fake.FakeAppVersionKitImpl
import com.makeappssimple.abhimanyu.common.core.coroutines.test.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.common.core.json_reader.JsonReaderKit
import com.makeappssimple.abhimanyu.common.core.json_reader.fake.FakeJsonReaderKitImpl
import com.makeappssimple.abhimanyu.common.core.json_writer.JsonWriterKit
import com.makeappssimple.abhimanyu.common.core.json_writer.fake.FakeJsonWriterKitImpl
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.log_kit.fake.FakeLogKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.alarm.AlarmKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.alarm.fake.FakeAlarmKitImpl
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
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.UpdateAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.common.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.common.RestoreDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetAllTransactionsUseCase
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
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.AmountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.transaction_provider.DatabaseTransactionProvider
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.transaction_provider.fake.FakeDatabaseTransactionProviderImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.FinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.fake.FakeFinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegateImpl
import com.makeappssimple.abhimanyu.finance.manager.android.feature.settings.settings.snackbar.SettingsScreenSnackbarType
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

class SettingsScreenViewModelTest {
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
    private val alarmKit: AlarmKit = FakeAlarmKitImpl()
    private val appVersionKit: AppVersionKit = FakeAppVersionKitImpl()
    private val dateTimeKit: DateTimeKit = DateTimeKitImpl()
    private val financeManagerPreferencesDataSource: FinanceManagerPreferencesDataSource =
        FakeFinanceManagerPreferencesDataSource()
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository =
        FinanceManagerPreferencesRepositoryImpl(
            dispatcherProvider = testDispatcherProvider,
            financeManagerPreferencesDataSource = financeManagerPreferencesDataSource,
        )
    private val fakeCategoryDao: CategoryDao = FakeCategoryDaoImpl()
    private val categoryRepository: CategoryRepository = CategoryRepositoryImpl(
        categoryDao = fakeCategoryDao,
        dispatcherProvider = testDispatcherProvider,
    )
    private val getAllCategoriesUseCase = GetAllCategoriesUseCase(
        categoryRepository = categoryRepository,
    )
    private val fakeAccountDao: AccountDao = FakeAccountDaoImpl()
    private val accountRepository: AccountRepository =
        AccountRepositoryImpl(
            accountDao = fakeAccountDao,
            dispatcherProvider = testDispatcherProvider,
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
    private val fakeDatabaseTransactionProvider: DatabaseTransactionProvider =
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
        databaseTransactionProvider = fakeDatabaseTransactionProvider,
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
    private val backupDataUseCase = BackupDataUseCase(
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
    private val getAllTransactionDataUseCase = GetAllTransactionDataUseCase(
        transactionDataRepository = transactionDataRepository,
    )
    private val updateAccountsUseCase = UpdateAccountsUseCase(
        accountRepository = accountRepository,
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
    )
    private val recalculateTotalUseCase = RecalculateTotalUseCase(
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        getAllAccountsUseCase = getAllAccountsUseCase,
        getAllTransactionDataUseCase = getAllTransactionDataUseCase,
        updateAccountsUseCase = updateAccountsUseCase,
    )
    private val jsonReaderKit: JsonReaderKit = FakeJsonReaderKitImpl()
    private val logKit: LogKit = FakeLogKitImpl()
    private val restoreDataUseCase = RestoreDataUseCase(
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        jsonReaderKit = jsonReaderKit,
        logKit = logKit,
        transactionRepository = transactionRepository,
    )

    private lateinit var settingsScreenViewModel: SettingsScreenViewModel

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
        settingsScreenViewModel = SettingsScreenViewModel(
            navigationKit = navigationKit,
            screenUIStateDelegate = screenUIStateDelegate,
            alarmKit = alarmKit,
            appVersionKit = appVersionKit,
            backupDataUseCase = backupDataUseCase,
            coroutineScope = testScope.backgroundScope,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            recalculateTotalUseCase = recalculateTotalUseCase,
            restoreDataUseCase = restoreDataUseCase,
            logKit = logKit,
        )
        settingsScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = runTestWithTimeout {
        settingsScreenViewModel.uiState.test {
            val result = awaitItem()

            assertThat(result.isLoading).isTrue()
            assertThat(result.isReminderEnabled).isNull()
            assertThat(result.screenSnackbarType).isEqualTo(
                SettingsScreenSnackbarType.None
            )
            assertThat(result.appVersion).isNull()
        }
    }
    // endregion

    // region updateUiStateAndStateEvents
    // endregion

    // region state events
    @Test
    fun resetScreenSnackbarType_shouldSetSnackbarTypeToNone() =
        runTestWithTimeout {
            settingsScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.screenSnackbarType).isEqualTo(
                    SettingsScreenSnackbarType.None
                )
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
                assertThat(postDataFetchCompletion.screenSnackbarType).isEqualTo(
                    SettingsScreenSnackbarType.None
                )
                settingsScreenViewModel.uiStateEvents.updateScreenSnackbarType(
                    SettingsScreenSnackbarType.CancelReminderSuccessful
                )
                assertThat(awaitItem().screenSnackbarType).isEqualTo(
                    SettingsScreenSnackbarType.CancelReminderSuccessful
                )

                settingsScreenViewModel.uiStateEvents.resetScreenSnackbarType()

                assertThat(awaitItem().screenSnackbarType).isEqualTo(
                    SettingsScreenSnackbarType.None
                )
            }
        }

    @Test
    fun updateScreenSnackbarType_shouldUpdateScreenSnackbarType() =
        runTestWithTimeout {
            settingsScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.screenSnackbarType).isEqualTo(
                    SettingsScreenSnackbarType.None
                )
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
                assertThat(postDataFetchCompletion.screenSnackbarType).isEqualTo(
                    SettingsScreenSnackbarType.None
                )

                settingsScreenViewModel.uiStateEvents.updateScreenSnackbarType(
                    SettingsScreenSnackbarType.CancelReminderSuccessful
                )

                val result = awaitItem()
                assertThat(result.screenSnackbarType).isEqualTo(
                    SettingsScreenSnackbarType.CancelReminderSuccessful
                )
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
