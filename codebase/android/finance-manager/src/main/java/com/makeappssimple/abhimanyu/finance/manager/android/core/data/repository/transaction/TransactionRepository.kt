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

package com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction

import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

public interface TransactionRepository {
    public suspend fun getAllTransactions(): ImmutableList<Transaction>

    public fun getAllTransactionDataFlow(): Flow<ImmutableList<TransactionData>>

    public suspend fun getAllTransactionData(): ImmutableList<TransactionData>

    public suspend fun getSearchedTransactionData(
        searchText: String,
    ): ImmutableList<TransactionData>

    public fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<ImmutableList<TransactionData>>

    public fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<ImmutableList<Transaction>>

    public suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): ImmutableList<Transaction>

    public suspend fun getTransactionsCount(): Int

    public suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): ImmutableList<String>

    public suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean

    public suspend fun checkIfAccountIsUsedInTransactions(
        accountId: Int,
    ): Boolean

    public suspend fun checkIfTransactionForIsUsedInTransactions(
        transactionForId: Int,
    ): Boolean

    public suspend fun getTransaction(
        id: Int,
    ): Transaction?

    public suspend fun getTransactionData(
        id: Int,
    ): TransactionData?

    public suspend fun insertTransaction(
        accountFrom: Account?,
        accountTo: Account?,
        transaction: Transaction,
        originalTransaction: Transaction?,
    ): Long

    public suspend fun insertTransactions(
        vararg transactions: Transaction,
    ): ImmutableList<Long>

    public suspend fun updateTransaction(
        transaction: Transaction,
    ): Boolean

    public suspend fun updateTransactions(
        vararg transactions: Transaction,
    ): Boolean

    public suspend fun deleteTransaction(
        id: Int,
    ): Boolean

    public suspend fun deleteAllTransactions(): Boolean

    public suspend fun restoreData(
        categories: ImmutableList<Category>,
        accounts: ImmutableList<Account>,
        transactions: ImmutableList<Transaction>,
        transactionForValues: ImmutableList<TransactionFor>,
    ): Boolean
}
