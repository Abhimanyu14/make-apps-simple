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

package com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction.fake

import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public class FakeTransactionRepositoryImpl : TransactionRepository {
    override suspend fun getAllTransactions(): ImmutableList<Transaction> {
        return persistentListOf()
    }

    override fun getAllTransactionDataFlow(): Flow<ImmutableList<TransactionData>> {
        return flow {
            persistentListOf<TransactionData>()
        }
    }

    override suspend fun getAllTransactionData(): ImmutableList<TransactionData> {
        return persistentListOf()
    }

    override suspend fun getSearchedTransactionData(
        searchText: String,
    ): ImmutableList<TransactionData> {
        return persistentListOf()
    }

    override fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<ImmutableList<TransactionData>> {
        return flow {
            persistentListOf<TransactionData>()
        }
    }

    override fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<ImmutableList<Transaction>> {
        return flow {
            persistentListOf<TransactionData>()
        }
    }

    override suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): ImmutableList<Transaction> {
        return persistentListOf()
    }

    override suspend fun getTransactionsCount(): Int {
        return 0
    }

    override suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): ImmutableList<String> {
        return persistentListOf()
    }

    override suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean {
        return false
    }

    override suspend fun checkIfAccountIsUsedInTransactions(
        accountId: Int,
    ): Boolean {
        return false
    }

    override suspend fun checkIfTransactionForIsUsedInTransactions(
        transactionForId: Int,
    ): Boolean {
        return false
    }

    override suspend fun getTransaction(
        id: Int,
    ): Transaction? {
        return null
    }

    override suspend fun getTransactionData(
        id: Int,
    ): TransactionData? {
        return null
    }

    override suspend fun insertTransaction(
        accountFrom: Account?,
        accountTo: Account?,
        transaction: Transaction,
        originalTransaction: Transaction?,
    ): Long {
        return 0L
    }

    override suspend fun insertTransactions(
        vararg transactions: Transaction,
    ): ImmutableList<Long> {
        return persistentListOf()
    }

    override suspend fun updateTransaction(
        transaction: Transaction,
    ): Boolean {
        return false
    }

    override suspend fun updateTransactions(
        vararg transactions: Transaction,
    ): Boolean {
        return false
    }

    override suspend fun deleteTransaction(
        id: Int,
    ): Boolean {
        return false
    }

    override suspend fun deleteAllTransactions(): Boolean {
        return false
    }

    override suspend fun restoreData(
        categories: ImmutableList<Category>,
        accounts: ImmutableList<Account>,
        transactions: ImmutableList<Transaction>,
        transactionForValues: ImmutableList<TransactionFor>,
    ): Boolean {
        return false
    }
}
