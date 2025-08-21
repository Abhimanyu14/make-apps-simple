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

package com.makeappssimple.abhimanyu.finance.manager.android.test

import com.makeappssimple.abhimanyu.common.core.app_version.AppVersionKit
import com.makeappssimple.abhimanyu.common.core.app_version.fake.FakeAppVersionKitImpl
import com.makeappssimple.abhimanyu.common.core.coroutines.test.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.common.core.json_reader.JsonReaderKit
import com.makeappssimple.abhimanyu.common.core.json_reader.fake.FakeJsonReaderKitImpl
import com.makeappssimple.abhimanyu.common.core.json_writer.JsonWriterKit
import com.makeappssimple.abhimanyu.common.core.json_writer.fake.FakeJsonWriterKitImpl
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.log_kit.fake.FakeLogKitImpl
import com.makeappssimple.abhimanyu.common.core.uri_decoder.UriDecoder
import com.makeappssimple.abhimanyu.common.core.uri_decoder.fake.FakeUriDecoderImpl
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
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.DeleteAccountByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAccountByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAllAccountsFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAllAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAllAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.InsertAccountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.UpdateAccountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.UpdateAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.DeleteCategoryByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.GetCategoryByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.InsertCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.SetDefaultCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.UpdateCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.UpdateCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.common.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.common.RestoreDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.common.ShouldShowBackupCardUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.CheckIfAccountIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.CheckIfCategoryIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.CheckIfTransactionForValuesAreUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.DeleteTransactionUseByIdCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetMaxRefundAmountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetRecentTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetTitleSuggestionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetTransactionByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetTransactionDataByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetTransactionsBetweenTimestampsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.UpdateTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.DeleteTransactionForByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.GetTransactionForByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.InsertTransactionForUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.UpdateTransactionForUseCase
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
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.use_case.GetAllAccountsListItemDataListUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.use_case.GetDefaultAccountIdFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.use_case.AddAccountScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.use_case.EditAccountScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.use_case.AddCategoryScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.use_case.EditCategoryScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.add_transaction_for.use_case.AddTransactionForScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.edit_transaction_for.use_case.EditTransactionForScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.use_case.AddTransactionScreenDataValidationUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlin.time.Duration.Companion.seconds

internal class TestDependencies {
    // region coroutines setup
    val testCoroutineDispatcher = StandardTestDispatcher()
    val testScope = TestScope(
        context = testCoroutineDispatcher + Job(),
    )
    val testDispatcherProvider = TestDispatcherProviderImpl(
        testDispatcher = testCoroutineDispatcher,
    )
    // endregion

    // region database
    val fakeAccountDao: AccountDao = FakeAccountDaoImpl()
    val fakeCategoryDao: CategoryDao = FakeCategoryDaoImpl()
    val fakeDatabaseTransactionProvider: DatabaseTransactionProvider =
        FakeDatabaseTransactionProviderImpl()
    val fakeTransactionDao: TransactionDao = FakeTransactionDaoImpl()
    val fakeTransactionForDao: TransactionForDao =
        FakeTransactionForDaoImpl()
    val fakeTransactionDataDao: TransactionDataDao =
        FakeTransactionDataDaoImpl(
            accountDao = fakeAccountDao,
            categoryDao = fakeCategoryDao,
            transactionDao = fakeTransactionDao,
            transactionForDao = fakeTransactionForDao,
        )
    val commonDataSource: CommonDataSource = CommonDataSourceImpl(
        accountDao = fakeAccountDao,
        categoryDao = fakeCategoryDao,
        databaseTransactionProvider = fakeDatabaseTransactionProvider,
        transactionDao = fakeTransactionDao,
        transactionDataDao = fakeTransactionDataDao,
        transactionForDao = fakeTransactionForDao,
    )
    // endregion

    // region datastore
    val fakeFinanceManagerPreferencesDataSource: FinanceManagerPreferencesDataSource =
        FakeFinanceManagerPreferencesDataSource()
    // endregion

    // region data
    val accountRepository: AccountRepository = AccountRepositoryImpl(
        accountDao = fakeAccountDao,
        dispatcherProvider = testDispatcherProvider,
    )
    val categoryRepository: CategoryRepository = CategoryRepositoryImpl(
        categoryDao = fakeCategoryDao,
        dispatcherProvider = testDispatcherProvider,
    )
    val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository =
        FinanceManagerPreferencesRepositoryImpl(
            dispatcherProvider = testDispatcherProvider,
            financeManagerPreferencesDataSource = fakeFinanceManagerPreferencesDataSource,
        )
    val transactionRepository: TransactionRepository =
        TransactionRepositoryImpl(
            commonDataSource = commonDataSource,
            dispatcherProvider = testDispatcherProvider,
            transactionDao = fakeTransactionDao,
        )
    val transactionDataRepository: TransactionDataRepository =
        TransactionDataRepositoryImpl(
            commonDataSource = commonDataSource,
            dispatcherProvider = testDispatcherProvider,
            transactionDataDao = fakeTransactionDataDao,
        )
    val transactionForRepository: TransactionForRepository =
        TransactionForRepositoryImpl(
            dispatcherProvider = testDispatcherProvider,
            transactionForDao = fakeTransactionForDao,
        )
    // endregion

    // region core dependencies
    val alarmKit: AlarmKit = FakeAlarmKitImpl()
    val appVersionKit: AppVersionKit = FakeAppVersionKitImpl()
    val dateTimeKit: DateTimeKit = DateTimeKitImpl()
    val jsonReaderKit: JsonReaderKit = FakeJsonReaderKitImpl()
    val jsonWriterKit: JsonWriterKit = FakeJsonWriterKitImpl()
    val logKit: LogKit = FakeLogKitImpl()
    val navigationKit: NavigationKit = NavigationKitImpl(
        coroutineScope = testScope.backgroundScope,
    )
    val screenUIStateDelegate: ScreenUIStateDelegate =
        ScreenUIStateDelegateImpl(
            coroutineScope = testScope.backgroundScope,
        )
    val uriDecoder: UriDecoder = FakeUriDecoderImpl()
    // endregion

    // region transaction data use cases
    val getRecentTransactionDataFlowUseCase =
        GetRecentTransactionDataFlowUseCase(
            transactionDataRepository = transactionDataRepository,
        )
    val getAllTransactionDataUseCase = GetAllTransactionDataUseCase(
        transactionDataRepository = transactionDataRepository,
    )
    val getTransactionDataByIdUseCase = GetTransactionDataByIdUseCase(
        transactionDataRepository = transactionDataRepository,
    )
    val getAllTransactionDataFlowUseCase = GetAllTransactionDataFlowUseCase(
        transactionDataRepository = transactionDataRepository,
    )
    // endregion

    // region transaction use cases
    val insertTransactionsUseCase = InsertTransactionsUseCase(
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        transactionRepository = transactionRepository,
    )
    val getAllTransactionsUseCase = GetAllTransactionsUseCase(
        transactionRepository = transactionRepository,
    )
    val getTransactionByIdUseCase = GetTransactionByIdUseCase(
        transactionRepository = transactionRepository,
    )
    val getTransactionsBetweenTimestampsUseCase =
        GetTransactionsBetweenTimestampsUseCase(
            transactionRepository = transactionRepository,
        )
    val deleteTransactionUseByIdCase = DeleteTransactionUseByIdCase(
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        transactionRepository = transactionRepository,
    )
    val getTitleSuggestionsUseCase = GetTitleSuggestionsUseCase(
        transactionRepository = transactionRepository,
    )
    val getMaxRefundAmountUseCase = GetMaxRefundAmountUseCase(
        getTransactionDataByIdUseCase = getTransactionDataByIdUseCase,
    )
    val insertTransactionUseCase = InsertTransactionUseCase(
        dateTimeKit = dateTimeKit,
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        transactionRepository = transactionRepository,
    )
    val updateTransactionsUseCase = UpdateTransactionsUseCase(
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        transactionRepository = transactionRepository,
    )
    // endregion

    // region account use cases
    val getAllAccountsUseCase = GetAllAccountsUseCase(
        accountRepository = accountRepository,
    )
    val addAccountScreenDataValidationUseCase =
        AddAccountScreenDataValidationUseCase(
            getAllAccountsUseCase = getAllAccountsUseCase,
        )
    val insertAccountUseCase = InsertAccountUseCase(
        accountRepository = accountRepository,
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
    )
    val getAllAccountsTotalBalanceAmountValueUseCase =
        GetAllAccountsTotalBalanceAmountValueUseCase(
            accountRepository = accountRepository,
        )
    val getAllAccountsTotalMinimumBalanceAmountValueUseCase =
        GetAllAccountsTotalMinimumBalanceAmountValueUseCase(
            accountRepository = accountRepository,
        )
    val updateAccountsUseCase = UpdateAccountsUseCase(
        accountRepository = accountRepository,
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
    )
    val editAccountScreenDataValidationUseCase =
        EditAccountScreenDataValidationUseCase(
            getAllAccountsUseCase = getAllAccountsUseCase,
        )
    val getAccountByIdUseCase = GetAccountByIdUseCase(
        accountRepository = accountRepository,
    )
    val updateAccountUseCase = UpdateAccountUseCase(
        dateTimeKit = dateTimeKit,
        insertTransactionsUseCase = insertTransactionsUseCase,
        updateAccountsUseCase = updateAccountsUseCase,
    )

    val deleteAccountByIdUseCase = DeleteAccountByIdUseCase(
        accountRepository = accountRepository,
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
    )
    val getAllAccountsFlowUseCase = GetAllAccountsFlowUseCase(
        accountRepository = accountRepository,
    )
    val checkIfAccountIsUsedInTransactionsUseCase =
        CheckIfAccountIsUsedInTransactionsUseCase(
            transactionRepository = transactionRepository,
        )
    val getAllAccountsListItemDataListUseCase =
        GetAllAccountsListItemDataListUseCase(
            checkIfAccountIsUsedInTransactionsUseCase = checkIfAccountIsUsedInTransactionsUseCase,
        )
    val getDefaultAccountIdFlowUseCase = GetDefaultAccountIdFlowUseCase(
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
    )
    // endregion

    // region category use cases
    val getAllCategoriesUseCase = GetAllCategoriesUseCase(
        categoryRepository = categoryRepository,
    )
    val addCategoryScreenDataValidationUseCase =
        AddCategoryScreenDataValidationUseCase(
            getAllCategoriesUseCase = getAllCategoriesUseCase,
        )
    val insertCategoriesUseCase = InsertCategoriesUseCase(
        categoryRepository = categoryRepository,
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
    )
    val insertCategoryUseCase = InsertCategoryUseCase(
        insertCategoriesUseCase = insertCategoriesUseCase,
    )
    val editCategoryScreenDataValidationUseCase =
        EditCategoryScreenDataValidationUseCase(
            getAllCategoriesUseCase = getAllCategoriesUseCase,
        )
    val getCategoryByIdUseCase = GetCategoryByIdUseCase(
        categoryRepository = categoryRepository,
    )
    val updateCategoriesUseCase = UpdateCategoriesUseCase(
        categoryRepository = categoryRepository,
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
    )
    val updateCategoryUseCase = UpdateCategoryUseCase(
        updateCategoriesUseCase = updateCategoriesUseCase,
    )
    val checkIfCategoryIsUsedInTransactionsUseCase =
        CheckIfCategoryIsUsedInTransactionsUseCase(
            transactionRepository = transactionRepository,
        )
    val deleteCategoryByIdUseCase = DeleteCategoryByIdUseCase(
        categoryRepository = categoryRepository,
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
    )
    val getAllCategoriesFlowUseCase = GetAllCategoriesFlowUseCase(
        categoryRepository = categoryRepository,
    )
    val setDefaultCategoryUseCase = SetDefaultCategoryUseCase(
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
    )
    // endregion

    // region transaction for use cases
    val getAllTransactionForValuesUseCase = GetAllTransactionForValuesUseCase(
        transactionForRepository = transactionForRepository,
    )
    val addTransactionForScreenDataValidationUseCase =
        AddTransactionForScreenDataValidationUseCase(
            getAllTransactionForValuesUseCase = getAllTransactionForValuesUseCase,
        )
    val insertTransactionForUseCase = InsertTransactionForUseCase(
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        transactionForRepository = transactionForRepository,
    )
    val editTransactionForScreenDataValidationUseCase =
        EditTransactionForScreenDataValidationUseCase(
            getAllTransactionForValuesUseCase = getAllTransactionForValuesUseCase,
        )
    val getTransactionForByIdUseCase =
        GetTransactionForByIdUseCase(
            transactionForRepository = transactionForRepository,
        )
    val updateTransactionForUseCase =
        UpdateTransactionForUseCase(
            transactionForRepository = transactionForRepository,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    val getAllTransactionForValuesFlowUseCase =
        GetAllTransactionForValuesFlowUseCase(
            transactionForRepository = transactionForRepository,
        )
    val checkIfTransactionForValuesAreUsedInTransactionsUseCase =
        CheckIfTransactionForValuesAreUsedInTransactionsUseCase(
            transactionRepository = transactionRepository,
        )
    val deleteTransactionForByIdUseCase = DeleteTransactionForByIdUseCase(
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        transactionForRepository = transactionForRepository,
    )
    // endregion

    // region common use cases
    val shouldShowBackupCardUseCase = ShouldShowBackupCardUseCase(
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
    )
    val restoreDataUseCase = RestoreDataUseCase(
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        jsonReaderKit = jsonReaderKit,
        logKit = logKit,
        transactionRepository = transactionRepository,
    )
    val backupDataUseCase = BackupDataUseCase(
        dateTimeKit = dateTimeKit,
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        getAllCategoriesUseCase = getAllCategoriesUseCase,
        getAllAccountsUseCase = getAllAccountsUseCase,
        getAllTransactionForValuesUseCase = getAllTransactionForValuesUseCase,
        getAllTransactionsUseCase = getAllTransactionsUseCase,
        jsonWriterKit = jsonWriterKit,
    )
    val recalculateTotalUseCase = RecalculateTotalUseCase(
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        getAllAccountsUseCase = getAllAccountsUseCase,
        getAllTransactionDataUseCase = getAllTransactionDataUseCase,
        updateAccountsUseCase = updateAccountsUseCase,
    )
    // endregion

    // region feature use cases
    val addTransactionScreenDataValidationUseCase =
        AddTransactionScreenDataValidationUseCase()
    // endregion

    // region pre-populate test data
    val testAccountId1 = 101
    val testAccountName1 = "test-account-101"
    val testAccountEntity1 = AccountEntity(
        balanceAmount = AmountEntity(
            value = 100000,
        ),
        id = testAccountId1,
        type = AccountType.E_WALLET,
        name = testAccountName1,
    )
    val testAccountId2 = 102
    val testAccountName2 = "test-account-102"
    val testAccountEntity2 = AccountEntity(
        balanceAmount = AmountEntity(
            value = 100000,
        ),
        id = testAccountId2,
        type = AccountType.BANK,
        name = testAccountName2,
    )
    val testCategoryId1 = 101
    val testCategoryTitle1 = "test-category-101"
    val testCategoryEntity1 = CategoryEntity(
        id = testCategoryId1,
        emoji = "💳",
        title = testCategoryTitle1,
        transactionType = TransactionType.EXPENSE,
    )
    val testCategoryId2 = 102
    val testCategoryTitle2 = "test-category-102"
    val testCategoryEntity2 = CategoryEntity(
        id = testCategoryId2,
        emoji = "💳",
        title = testCategoryTitle2,
        transactionType = TransactionType.INCOME,
    )
    val testTransactionForId1 = 101
    val testTransactionForTitle1 = "test-transaction-for-101"
    val testTransactionForEntity1 = TransactionForEntity(
        id = testTransactionForId1,
        title = testTransactionForTitle1,
    )
    val testTransactionForId2 = 102
    val testTransactionForTitle2 = "test-transaction-for-102"
    val testTransactionForEntity2 = TransactionForEntity(
        id = testTransactionForId2,
        title = testTransactionForTitle2,
    )
    val testTransactionId1 = 101
    val testTransactionTitle1 = "test-transaction-101"
    val testTransactionEntity1 = TransactionEntity(
        amount = AmountEntity(
            value = 1000,
        ),
        categoryId = testCategoryId1,
        id = testTransactionId1,
        accountFromId = testAccountId1,
        transactionForId = testTransactionForId1,
        creationTimestamp = 1000000L,
        transactionTimestamp = 1000000L,
        title = testTransactionTitle1,
    )
    val testTransactionId2 = 102
    val testTransactionTitle2 = "test-transaction-102"
    val testTransactionEntity2 = TransactionEntity(
        amount = AmountEntity(
            value = 1000,
        ),
        categoryId = testCategoryId2,
        id = testTransactionId2,
        accountFromId = testAccountId2,
        transactionForId = testTransactionForId2,
        creationTimestamp = 1000000L,
        transactionTimestamp = 1000000L,
        title = testTransactionTitle2,
    )

    init {
        testScope.launch {
            fakeAccountDao.insertAccounts(
                testAccountEntity1,
                testAccountEntity2,
            )
            fakeCategoryDao.insertCategories(
                testCategoryEntity1,
                testCategoryEntity2,
            )
            fakeTransactionForDao.insertTransactionForValues(
                testTransactionForEntity1,
                testTransactionForEntity2,
            )
            fakeTransactionDao.insertTransactions(
                testTransactionEntity1,
                testTransactionEntity2,
            )
        }
    }
    // endregion

    // region runTestWithTimeout
    fun runTestWithTimeout(
        testBody: suspend TestScope.() -> Unit,
    ) {
        testScope.runTest(
            timeout = 10.seconds,
        ) {
            testBody()
        }
    }
    // endregion
}
