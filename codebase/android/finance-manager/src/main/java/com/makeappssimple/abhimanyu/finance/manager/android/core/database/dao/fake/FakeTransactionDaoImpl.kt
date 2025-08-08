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

package com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake

import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionDataEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

public class FakeTransactionDaoImpl : TransactionDao {
    override fun getAllTransactionsFlow(): Flow<List<TransactionEntity>> {
        return emptyFlow()
    }

    override suspend fun getAllTransactions(): List<TransactionEntity> {
        return emptyList()
    }

    override fun getAllTransactionDataFlow(): Flow<List<TransactionDataEntity>> {
        return emptyFlow()
    }

    override suspend fun getAllTransactionData(): List<TransactionDataEntity> {
        return emptyList()
    }

    override suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionDataEntity> {
        return emptyList()
    }

    override fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<TransactionEntity>> {
        return emptyFlow()
    }

    override suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<TransactionEntity> {
        return emptyList()
    }

    override fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<List<TransactionDataEntity>> {
        return emptyFlow()
    }

    override suspend fun getTransactionsCount(): Int {
        return 0
    }

    override suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): List<String> {
        return emptyList()
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

    override suspend fun getTransactionById(
        id: Int,
    ): TransactionEntity? {
        return null
    }

    override suspend fun getTransactionDataById(
        id: Int,
    ): TransactionDataEntity? {
        return null
    }

    override suspend fun insertTransactions(
        vararg transactions: TransactionEntity,
    ): List<Long> {
        return emptyList()
    }

    override suspend fun deleteAllTransactions(): Int {
        return 0
    }

    override suspend fun insertTransaction(
        transaction: TransactionEntity,
    ): Long {
        return 0L
    }

    override suspend fun updateTransaction(
        transaction: TransactionEntity,
    ): Int {
        return 0
    }

    override suspend fun updateTransactions(
        vararg transactions: TransactionEntity,
    ): Int {
        return 0
    }

    override suspend fun deleteTransactionById(
        id: Int,
    ): Int {
        return 0
    }
}
