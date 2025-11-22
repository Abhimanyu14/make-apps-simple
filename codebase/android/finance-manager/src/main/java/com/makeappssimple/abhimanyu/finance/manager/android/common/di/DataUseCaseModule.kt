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

package com.makeappssimple.abhimanyu.finance.manager.android.common.di

import com.makeappssimple.abhimanyu.common.core.json_reader.JsonReaderKit
import com.makeappssimple.abhimanyu.common.core.json_writer.JsonWriterKit
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.transaction_data.TransactionDataRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.transaction_for.TransactionForRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.account.DeleteAccountByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.account.GetAccountByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.account.GetAllAccountsFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.account.GetAllAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.account.GetAllAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.account.GetIsAccountsUsedInTransactionFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.account.InsertAccountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.account.InsertAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.account.UpdateAccountBalanceAmountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.account.UpdateAccountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.account.UpdateAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.category.DeleteCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.category.DeleteCategoryByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.category.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.category.GetCategoryByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.category.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.category.InsertCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.category.SetDefaultCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.category.UpdateCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.category.UpdateCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.common.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.common.RestoreDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.common.ShouldShowBackupCardUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.CheckIfAccountIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.CheckIfCategoryIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.CheckIfTransactionForValuesAreUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.DeleteAllTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.DeleteTransactionUseByIdCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.DuplicateTransactionUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetAccountsInTransactionsFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetCategoriesInTransactionsFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetMaxRefundAmountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetOldestTransactionTimestampUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetRecentTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetSearchedTransactionDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetTitleSuggestionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetTransactionByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetTransactionDataByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetTransactionDataMappedByCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetTransactionsBetweenTimestampsFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetTransactionsBetweenTimestampsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.UpdateTransactionUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.UpdateTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction_for.DeleteTransactionForByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction_for.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction_for.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction_for.GetTransactionForByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction_for.InsertTransactionForUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction_for.UpdateTransactionForUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction_for.UpdateTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.DateTimeKit
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
internal class DataUseCaseModule {
    // region account
    @Single
    internal fun providesDeleteAccountUseCase(
        accountRepository: AccountRepository,
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    ): DeleteAccountByIdUseCase {
        return DeleteAccountByIdUseCase(
            accountRepository = accountRepository,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    }

    @Single
    internal fun providesGetAllAccountsTotalBalanceAmountValueUseCase(
        accountRepository: AccountRepository,
    ): GetAllAccountsTotalBalanceAmountValueUseCase {
        return GetAllAccountsTotalBalanceAmountValueUseCase(
            accountRepository = accountRepository,
        )
    }

    @Single
    internal fun providesGetAllAccountsTotalMinimumBalanceAmountValueUseCase(
        accountRepository: AccountRepository,
    ): GetAllAccountsTotalMinimumBalanceAmountValueUseCase {
        return GetAllAccountsTotalMinimumBalanceAmountValueUseCase(
            accountRepository = accountRepository,
        )
    }

    @Single
    internal fun providesGetAccountByIdUseCase(
        accountRepository: AccountRepository,
    ): GetAccountByIdUseCase {
        return GetAccountByIdUseCase(
            accountRepository = accountRepository,
        )
    }

    @Single
    internal fun providesGetAllAccountsFlowUseCase(
        accountRepository: AccountRepository,
    ): GetAllAccountsFlowUseCase {
        return GetAllAccountsFlowUseCase(
            accountRepository = accountRepository,
        )
    }

    @Single
    internal fun providesGetAllAccountsUseCase(
        accountRepository: AccountRepository,
    ): GetAllAccountsUseCase {
        return GetAllAccountsUseCase(
            accountRepository = accountRepository,
        )
    }

    @Single
    internal fun providesGetIsAccountsUsedInTransactionFlowUseCase(
        getAllAccountsFlowUseCase: GetAllAccountsFlowUseCase,
        checkIfAccountIsUsedInTransactionsUseCase: CheckIfAccountIsUsedInTransactionsUseCase,
    ): GetIsAccountsUsedInTransactionFlowUseCase {
        return GetIsAccountsUsedInTransactionFlowUseCase(
            getAllAccountsFlowUseCase = getAllAccountsFlowUseCase,
            checkIfAccountIsUsedInTransactionsUseCase = checkIfAccountIsUsedInTransactionsUseCase,
        )
    }

    @Single
    internal fun providesInsertAccountsUseCase(
        accountRepository: AccountRepository,
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    ): InsertAccountsUseCase {
        return InsertAccountsUseCase(
            accountRepository = accountRepository,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    }

    @Single
    internal fun providesInsertAccountUseCase(
        accountRepository: AccountRepository,
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    ): InsertAccountUseCase {
        return InsertAccountUseCase(
            accountRepository = accountRepository,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    }

    @Single
    internal fun providesUpdateAccountBalanceAmountUseCase(
        accountRepository: AccountRepository,
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    ): UpdateAccountBalanceAmountUseCase {
        return UpdateAccountBalanceAmountUseCase(
            accountRepository = accountRepository,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    }

    @Single
    internal fun providesUpdateAccountsUseCase(
        accountRepository: AccountRepository,
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    ): UpdateAccountsUseCase {
        return UpdateAccountsUseCase(
            accountRepository = accountRepository,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    }

    @Single
    internal fun providesUpdateAccountUseCase(
        dateTimeKit: DateTimeKit,
        insertTransactionsUseCase: InsertTransactionsUseCase,
        updateAccountsUseCase: UpdateAccountsUseCase,
    ): UpdateAccountUseCase {
        return UpdateAccountUseCase(
            dateTimeKit = dateTimeKit,
            insertTransactionsUseCase = insertTransactionsUseCase,
            updateAccountsUseCase = updateAccountsUseCase,
        )
    }
    // endregion

    // region category
    @Single
    internal fun providesDeleteCategoriesUseCase(
        categoryRepository: CategoryRepository,
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    ): DeleteCategoriesUseCase {
        return DeleteCategoriesUseCase(
            categoryRepository = categoryRepository,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    }

    @Single
    internal fun providesDeleteCategoryByIdUseCase(
        categoryRepository: CategoryRepository,
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    ): DeleteCategoryByIdUseCase {
        return DeleteCategoryByIdUseCase(
            categoryRepository = categoryRepository,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    }

    @Single
    internal fun providesGetAllCategoriesFlowUseCase(
        categoryRepository: CategoryRepository,
    ): GetAllCategoriesFlowUseCase {
        return GetAllCategoriesFlowUseCase(
            categoryRepository = categoryRepository,
        )
    }

    @Single
    internal fun providesGetAllCategoriesUseCase(
        categoryRepository: CategoryRepository,
    ): GetAllCategoriesUseCase {
        return GetAllCategoriesUseCase(
            categoryRepository = categoryRepository,
        )
    }

    @Single
    internal fun providesGetCategoryByIdUseCase(
        categoryRepository: CategoryRepository,
    ): GetCategoryByIdUseCase {
        return GetCategoryByIdUseCase(
            categoryRepository = categoryRepository,
        )
    }

    @Single
    internal fun providesInsertCategoriesUseCase(
        categoryRepository: CategoryRepository,
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    ): InsertCategoriesUseCase {
        return InsertCategoriesUseCase(
            categoryRepository = categoryRepository,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    }

    @Single
    internal fun providesInsertCategoryUseCase(
        insertCategoriesUseCase: InsertCategoriesUseCase,
    ): InsertCategoryUseCase {
        return InsertCategoryUseCase(
            insertCategoriesUseCase = insertCategoriesUseCase,
        )
    }

    @Single
    internal fun providesSetDefaultCategoryUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    ): SetDefaultCategoryUseCase {
        return SetDefaultCategoryUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    }

    @Single
    internal fun providesUpdateCategoriesUseCase(
        categoryRepository: CategoryRepository,
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    ): UpdateCategoriesUseCase {
        return UpdateCategoriesUseCase(
            categoryRepository = categoryRepository,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    }

    @Single
    internal fun providesUpdateCategoryUseCase(
        updateCategoriesUseCase: UpdateCategoriesUseCase,
    ): UpdateCategoryUseCase {
        return UpdateCategoryUseCase(
            updateCategoriesUseCase = updateCategoriesUseCase,
        )
    }
    // endregion

    // region common
    @Single
    internal fun providesBackupDataUseCase(
        dateTimeKit: DateTimeKit,
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        getAllCategoriesUseCase: GetAllCategoriesUseCase,
        getAllAccountsUseCase: GetAllAccountsUseCase,
        getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
        getAllTransactionsUseCase: GetAllTransactionsUseCase,
        jsonWriterKit: JsonWriterKit,
    ): BackupDataUseCase {
        return BackupDataUseCase(
            dateTimeKit = dateTimeKit,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            getAllCategoriesUseCase = getAllCategoriesUseCase,
            getAllAccountsUseCase = getAllAccountsUseCase,
            getAllTransactionForValuesUseCase = getAllTransactionForValuesUseCase,
            getAllTransactionsUseCase = getAllTransactionsUseCase,
            jsonWriterKit = jsonWriterKit,
        )
    }

    @Single
    internal fun providesRecalculateTotalUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        getAllAccountsUseCase: GetAllAccountsUseCase,
        getAllTransactionDataUseCase: GetAllTransactionDataUseCase,
        updateAccountsUseCase: UpdateAccountsUseCase,
    ): RecalculateTotalUseCase {
        return RecalculateTotalUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            getAllAccountsUseCase = getAllAccountsUseCase,
            getAllTransactionDataUseCase = getAllTransactionDataUseCase,
            updateAccountsUseCase = updateAccountsUseCase,
        )
    }

    @Single
    internal fun providesRestoreDataUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        jsonReaderKit: JsonReaderKit,
        logKit: LogKit,
        transactionRepository: TransactionRepository,
    ): RestoreDataUseCase {
        return RestoreDataUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            jsonReaderKit = jsonReaderKit,
            logKit = logKit,
            transactionRepository = transactionRepository,
        )
    }

    @Single
    internal fun providesShouldShowBackupCardUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    ): ShouldShowBackupCardUseCase {
        return ShouldShowBackupCardUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    }
    // endregion

    // region transaction
    @Single
    internal fun providesCheckIfAccountIsUsedInTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): CheckIfAccountIsUsedInTransactionsUseCase {
        return CheckIfAccountIsUsedInTransactionsUseCase(
            transactionRepository = transactionRepository,
        )
    }

    @Single
    internal fun providesCheckIfCategoryIsUsedInTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): CheckIfCategoryIsUsedInTransactionsUseCase {
        return CheckIfCategoryIsUsedInTransactionsUseCase(
            transactionRepository = transactionRepository,
        )
    }

    @Single
    internal fun providesCheckIfTransactionForValuesAreUsedInTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): CheckIfTransactionForValuesAreUsedInTransactionsUseCase {
        return CheckIfTransactionForValuesAreUsedInTransactionsUseCase(
            transactionRepository = transactionRepository,
        )
    }

    @Single
    internal fun providesDeleteAllTransactionsUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        transactionRepository: TransactionRepository,
    ): DeleteAllTransactionsUseCase {
        return DeleteAllTransactionsUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            transactionRepository = transactionRepository,
        )
    }

    @Single
    internal fun providesDeleteTransactionUseByIdCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        transactionRepository: TransactionRepository,
    ): DeleteTransactionUseByIdCase {
        return DeleteTransactionUseByIdCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            transactionRepository = transactionRepository,
        )
    }

    @Single
    internal fun providesDuplicateTransactionUseCase(
        dateTimeKit: DateTimeKit,
        getTransactionByIdUseCase: GetTransactionByIdUseCase,
        insertTransactionsUseCase: InsertTransactionsUseCase,
    ): DuplicateTransactionUseCase {
        return DuplicateTransactionUseCase(
            dateTimeKit = dateTimeKit,
            getTransactionByIdUseCase = getTransactionByIdUseCase,
            insertTransactionsUseCase = insertTransactionsUseCase,
        )
    }

    @Single
    internal fun providesGetAccountsInTransactionsFlowUseCase(
        transactionDataRepository: TransactionDataRepository,
    ): GetAccountsInTransactionsFlowUseCase {
        return GetAccountsInTransactionsFlowUseCase(
            transactionDataRepository = transactionDataRepository,
        )
    }

    @Single
    internal fun providesGetAllTransactionDataFlowUseCase(
        transactionDataRepository: TransactionDataRepository,
    ): GetAllTransactionDataFlowUseCase {
        return GetAllTransactionDataFlowUseCase(
            transactionDataRepository = transactionDataRepository,
        )
    }

    @Single
    internal fun providesGetAllTransactionDataUseCase(
        transactionDataRepository: TransactionDataRepository,
    ): GetAllTransactionDataUseCase {
        return GetAllTransactionDataUseCase(
            transactionDataRepository = transactionDataRepository,
        )
    }

    @Single
    internal fun providesGetAllTransactionsUseCase(
        transactionRepository: TransactionRepository,
    ): GetAllTransactionsUseCase {
        return GetAllTransactionsUseCase(
            transactionRepository = transactionRepository,
        )
    }

    @Single
    internal fun providesGetCategoriesInTransactionsFlowUseCase(
        transactionDataRepository: TransactionDataRepository,
    ): GetCategoriesInTransactionsFlowUseCase {
        return GetCategoriesInTransactionsFlowUseCase(
            transactionDataRepository = transactionDataRepository,
        )
    }

    @Single
    internal fun providesGetMaxRefundAmountUseCase(
        getTransactionDataByIdUseCase: GetTransactionDataByIdUseCase,
    ): GetMaxRefundAmountUseCase {
        return GetMaxRefundAmountUseCase(
            getTransactionDataByIdUseCase = getTransactionDataByIdUseCase,
        )
    }

    @Single
    internal fun providesGetOldestTransactionTimestampUseCase(
        transactionDataRepository: TransactionDataRepository,
    ): GetOldestTransactionTimestampUseCase {
        return GetOldestTransactionTimestampUseCase(
            transactionDataRepository = transactionDataRepository,
        )
    }

    @Single
    internal fun providesGetRecentTransactionDataFlowUseCase(
        transactionDataRepository: TransactionDataRepository,
    ): GetRecentTransactionDataFlowUseCase {
        return GetRecentTransactionDataFlowUseCase(
            transactionDataRepository = transactionDataRepository,
        )
    }

    @Single
    internal fun providesGetSearchedTransactionDataUseCase(
        transactionDataRepository: TransactionDataRepository,
    ): GetSearchedTransactionDataUseCase {
        return GetSearchedTransactionDataUseCase(
            transactionDataRepository = transactionDataRepository,
        )
    }

    @Single
    internal fun providesGetTitleSuggestionsUseCase(
        transactionRepository: TransactionRepository,
    ): GetTitleSuggestionsUseCase {
        return GetTitleSuggestionsUseCase(
            transactionRepository = transactionRepository,
        )
    }

    @Single
    internal fun providesGetTransactionDataMappedByCategoryUseCase(
        transactionDataRepository: TransactionDataRepository,
    ): GetTransactionDataMappedByCategoryUseCase {
        return GetTransactionDataMappedByCategoryUseCase(
            transactionDataRepository = transactionDataRepository,
        )
    }

    @Single
    internal fun providesGetTransactionDataByIdUseCase(
        transactionDataRepository: TransactionDataRepository,
    ): GetTransactionDataByIdUseCase {
        return GetTransactionDataByIdUseCase(
            transactionDataRepository = transactionDataRepository,
        )
    }

    @Single
    internal fun providesGetTransactionsBetweenTimestampsFlowUseCase(
        transactionRepository: TransactionRepository,
    ): GetTransactionsBetweenTimestampsFlowUseCase {
        return GetTransactionsBetweenTimestampsFlowUseCase(
            transactionRepository = transactionRepository,
        )
    }

    @Single
    internal fun providesGetTransactionsBetweenTimestampsUseCase(
        transactionRepository: TransactionRepository,
    ): GetTransactionsBetweenTimestampsUseCase {
        return GetTransactionsBetweenTimestampsUseCase(
            transactionRepository = transactionRepository,
        )
    }

    @Single
    internal fun providesGetTransactionByIdUseCase(
        transactionRepository: TransactionRepository,
    ): GetTransactionByIdUseCase {
        return GetTransactionByIdUseCase(
            transactionRepository = transactionRepository,
        )
    }

    @Single
    internal fun providesInsertTransactionUseCase(
        dateTimeKit: DateTimeKit,
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        transactionRepository: TransactionRepository,
    ): InsertTransactionUseCase {
        return InsertTransactionUseCase(
            dateTimeKit = dateTimeKit,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            transactionRepository = transactionRepository,
        )
    }

    @Single
    internal fun providesInsertTransactionsUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        transactionRepository: TransactionRepository,
    ): InsertTransactionsUseCase {
        return InsertTransactionsUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            transactionRepository = transactionRepository,
        )
    }

    @Single
    internal fun providesUpdateTransactionUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        transactionRepository: TransactionRepository,
    ): UpdateTransactionUseCase {
        return UpdateTransactionUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            transactionRepository = transactionRepository,
        )
    }

    @Single
    internal fun providesUpdateTransactionsUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        transactionRepository: TransactionRepository,
    ): UpdateTransactionsUseCase {
        return UpdateTransactionsUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            transactionRepository = transactionRepository,
        )
    }
    // endregion

    // region transaction for
    @Single
    internal fun providesDeleteTransactionForByIdUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        transactionForRepository: TransactionForRepository,
    ): DeleteTransactionForByIdUseCase {
        return DeleteTransactionForByIdUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            transactionForRepository = transactionForRepository,
        )
    }

    @Single
    internal fun providesGetAllTransactionForValuesFlowUseCase(
        transactionForRepository: TransactionForRepository,
    ): GetAllTransactionForValuesFlowUseCase {
        return GetAllTransactionForValuesFlowUseCase(
            transactionForRepository = transactionForRepository,
        )
    }

    @Single
    internal fun providesGetAllTransactionForValuesUseCase(
        transactionForRepository: TransactionForRepository,
    ): GetAllTransactionForValuesUseCase {
        return GetAllTransactionForValuesUseCase(
            transactionForRepository = transactionForRepository,
        )
    }

    @Single
    internal fun providesGetTransactionForByIdUseCase(
        transactionForRepository: TransactionForRepository,
    ): GetTransactionForByIdUseCase {
        return GetTransactionForByIdUseCase(
            transactionForRepository = transactionForRepository,
        )
    }

    @Single
    internal fun providesInsertTransactionForUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        transactionForRepository: TransactionForRepository,
    ): InsertTransactionForUseCase {
        return InsertTransactionForUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            transactionForRepository = transactionForRepository,
        )
    }

    @Single
    internal fun providesUpdateTransactionForUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        transactionForRepository: TransactionForRepository,
    ): UpdateTransactionForUseCase {
        return UpdateTransactionForUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            transactionForRepository = transactionForRepository,
        )
    }

    @Single
    internal fun providesUpdateTransactionForValuesUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        transactionForRepository: TransactionForRepository,
    ): UpdateTransactionForValuesUseCase {
        return UpdateTransactionForValuesUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            transactionForRepository = transactionForRepository,
        )
    }
    // endregion
}
