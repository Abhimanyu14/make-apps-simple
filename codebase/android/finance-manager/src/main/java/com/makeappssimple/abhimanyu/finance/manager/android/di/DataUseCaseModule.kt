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

package com.makeappssimple.abhimanyu.finance.manager.android.di

import com.makeappssimple.abhimanyu.common.core.jsonreader.JsonReaderKit
import com.makeappssimple.abhimanyu.common.core.jsonwriter.JsonWriterKit
import com.makeappssimple.abhimanyu.common.logger.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.datetime.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.account.DeleteAccountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.account.DeleteAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.account.GetAccountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.account.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.account.GetAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.account.GetAllAccountsCountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.account.GetAllAccountsFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.account.GetIsAccountsUsedInTransactionFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.account.InsertAccountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.account.InsertAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.account.UpdateAccountBalanceAmountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.account.UpdateAccountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.account.UpdateAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.category.DeleteCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.category.DeleteCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.category.GetAllCategoriesCountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.category.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.category.GetCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.category.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.category.SetDefaultCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.category.UpdateCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.common.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.common.RestoreDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.common.ShouldShowBackupCardUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.CheckIfAccountIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.CheckIfCategoryIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.CheckIfTransactionForValuesAreUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.DeleteAllTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.GetMaxRefundAmountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.GetRecentTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.GetSearchedTransactionDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.GetTitleSuggestionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.GetTransactionDataMappedByCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.GetTransactionUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.GetTransactionsBetweenTimestampsFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.GetTransactionsBetweenTimestampsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.UpdateTransactionUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.UpdateTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transactionfor.DeleteTransactionForUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transactionfor.GetTransactionForUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transactionfor.InsertTransactionForUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transactionfor.InsertTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transactionfor.UpdateTransactionForUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transactionfor.UpdateTransactionForValuesUseCase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
public class DataUseCaseModule {
    // region account
    @Single
    internal fun providesDeleteAccountsUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        accountRepository: AccountRepository,
    ): DeleteAccountsUseCase {
        return DeleteAccountsUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            accountRepository = accountRepository,
        )
    }

    @Single
    internal fun providesDeleteAccountUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        accountRepository: AccountRepository,
    ): DeleteAccountUseCase {
        return DeleteAccountUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            accountRepository = accountRepository,
        )
    }

    @Single
    internal fun providesGetAccountsTotalBalanceAmountValueUseCase(
        accountRepository: AccountRepository,
    ): GetAccountsTotalBalanceAmountValueUseCase {
        return GetAccountsTotalBalanceAmountValueUseCase(
            accountRepository = accountRepository,
        )
    }

    @Single
    internal fun providesGetAccountsTotalMinimumBalanceAmountValueUseCase(
        accountRepository: AccountRepository,
    ): GetAccountsTotalMinimumBalanceAmountValueUseCase {
        return GetAccountsTotalMinimumBalanceAmountValueUseCase(
            accountRepository = accountRepository,
        )
    }

    @Single
    internal fun providesGetAccountUseCase(
        accountRepository: AccountRepository,
    ): GetAccountUseCase {
        return GetAccountUseCase(
            accountRepository = accountRepository,
        )
    }

    @Single
    internal fun providesGetAllAccountsCountUseCase(
        accountRepository: AccountRepository,
    ): GetAllAccountsCountUseCase {
        return GetAllAccountsCountUseCase(
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
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        accountRepository: AccountRepository,
    ): InsertAccountsUseCase {
        return InsertAccountsUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            accountRepository = accountRepository,
        )
    }

    @Single
    internal fun providesInsertAccountUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        accountRepository: AccountRepository,
    ): InsertAccountUseCase {
        return InsertAccountUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            accountRepository = accountRepository,
        )
    }

    @Single
    internal fun providesUpdateAccountBalanceAmountUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        accountRepository: AccountRepository,
    ): UpdateAccountBalanceAmountUseCase {
        return UpdateAccountBalanceAmountUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            accountRepository = accountRepository,
        )
    }

    @Single
    internal fun providesUpdateAccountsUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        accountRepository: AccountRepository,
    ): UpdateAccountsUseCase {
        return UpdateAccountsUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            accountRepository = accountRepository,
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
    internal fun providesDeleteCategoryUseCase(
        categoryRepository: CategoryRepository,
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    ): DeleteCategoryUseCase {
        return DeleteCategoryUseCase(
            categoryRepository = categoryRepository,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    }

    @Single
    internal fun providesGetAllCategoriesCountUseCase(
        categoryRepository: CategoryRepository,
    ): GetAllCategoriesCountUseCase {
        return GetAllCategoriesCountUseCase(
            categoryRepository = categoryRepository,
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
    internal fun providesGetCategoryUseCase(
        categoryRepository: CategoryRepository,
    ): GetCategoryUseCase {
        return GetCategoryUseCase(
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
    internal fun providesDeleteTransactionUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        transactionRepository: TransactionRepository,
    ): DeleteTransactionUseCase {
        return DeleteTransactionUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
            transactionRepository = transactionRepository,
        )
    }

    @Single
    internal fun providesGetAllTransactionDataFlowUseCase(
        transactionRepository: TransactionRepository,
    ): GetAllTransactionDataFlowUseCase {
        return GetAllTransactionDataFlowUseCase(
            transactionRepository = transactionRepository,
        )
    }

    @Single
    internal fun providesGetAllTransactionDataUseCase(
        transactionRepository: TransactionRepository,
    ): GetAllTransactionDataUseCase {
        return GetAllTransactionDataUseCase(
            transactionRepository = transactionRepository,
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
    internal fun providesGetMaxRefundAmountUseCase(
        getTransactionDataUseCase: GetTransactionDataUseCase,
    ): GetMaxRefundAmountUseCase {
        return GetMaxRefundAmountUseCase(
            getTransactionDataUseCase = getTransactionDataUseCase,
        )
    }

    @Single
    internal fun providesGetRecentTransactionDataFlowUseCase(
        transactionRepository: TransactionRepository,
    ): GetRecentTransactionDataFlowUseCase {
        return GetRecentTransactionDataFlowUseCase(
            transactionRepository = transactionRepository,
        )
    }

    @Single
    internal fun providesGetSearchedTransactionDataUseCase(
        transactionRepository: TransactionRepository,
    ): GetSearchedTransactionDataUseCase {
        return GetSearchedTransactionDataUseCase(
            transactionRepository = transactionRepository,
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
        transactionRepository: TransactionRepository,
    ): GetTransactionDataMappedByCategoryUseCase {
        return GetTransactionDataMappedByCategoryUseCase(
            transactionRepository = transactionRepository,
        )
    }

    @Single
    internal fun providesGetTransactionDataUseCase(
        transactionRepository: TransactionRepository,
    ): GetTransactionDataUseCase {
        return GetTransactionDataUseCase(
            transactionRepository = transactionRepository,
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
    internal fun providesGetTransactionUseCase(
        transactionRepository: TransactionRepository,
    ): GetTransactionUseCase {
        return GetTransactionUseCase(
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
    internal fun providesDeleteTransactionForUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        transactionForRepository: TransactionForRepository,
    ): DeleteTransactionForUseCase {
        return DeleteTransactionForUseCase(
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
    internal fun providesGetTransactionForUseCase(
        transactionForRepository: TransactionForRepository,
    ): GetTransactionForUseCase {
        return GetTransactionForUseCase(
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
    internal fun providesInsertTransactionForValuesUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
        transactionForRepository: TransactionForRepository,
    ): InsertTransactionForValuesUseCase {
        return InsertTransactionForValuesUseCase(
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
