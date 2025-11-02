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

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.repository.transaction_data

import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Transaction
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionFilter
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionSortOption
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

internal interface TransactionDataRepository {
    fun getAccountsInTransactionsFlow(): Flow<List<Account>>

    suspend fun deleteTransactionById(
        id: Int,
    ): Boolean

    suspend fun getAllTransactionData(): ImmutableList<TransactionData>

    fun getAllTransactionDataFlow(
        transactionFilter: TransactionFilter,
        transactionSortOption: TransactionSortOption,
    ): Flow<ImmutableList<TransactionData>>

    fun getCategoriesInTransactionsFlow(): Flow<List<Category>>

    fun getOldestTransactionTimestampFlow(): Flow<Long?>

    fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<ImmutableList<TransactionData>>

    suspend fun getSearchedTransactionData(
        searchText: String,
    ): ImmutableList<TransactionData>

    suspend fun getTransactionDataById(
        id: Int,
    ): TransactionData?

    suspend fun insertTransaction(
        accountFrom: Account?,
        accountTo: Account?,
        transaction: Transaction,
        originalTransaction: Transaction?,
    ): Long

    suspend fun restoreData(
        categories: ImmutableList<Category>,
        accounts: ImmutableList<Account>,
        transactions: ImmutableList<Transaction>,
        transactionForValues: ImmutableList<TransactionFor>,
    ): Boolean
}
