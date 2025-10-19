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

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.fake

import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.TransactionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

/**
 * In-memory fake implementation of [TransactionDao] for testing purposes.
 */
public class FakeTransactionDaoImpl : TransactionDao {
    private val transactions = mutableListOf<TransactionEntity>()
    private val transactionsFlow: MutableStateFlow<List<TransactionEntity>> =
        MutableStateFlow(
            value = emptyList(),
        )
    private var nextId = 1

    override suspend fun checkIfAccountIsUsedInTransactions(
        accountId: Int,
    ): Boolean {
        return transactions.any { transactionEntity ->
            transactionEntity.accountFromId == accountId || transactionEntity.accountToId == accountId
        }
    }

    override suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean {
        return transactions.any { transactionEntity ->
            transactionEntity.categoryId == categoryId
        }
    }

    override suspend fun checkIfTransactionForIsUsedInTransactions(
        transactionForId: Int,
    ): Boolean {
        return transactions.any { transactionEntity ->
            transactionEntity.transactionForId == transactionForId
        }
    }

    override suspend fun deleteAllTransactions(): Int {
        val count = transactions.size
        transactions.clear()
        transactionsFlow.value = emptyList()
        return count
    }

    override suspend fun deleteTransactionById(
        id: Int,
    ): Int {
        val removed = transactions.removeIf { transactionEntity ->
            transactionEntity.id == id
        }
        if (removed) {
            transactionsFlow.value =
                transactions.sortedBy { transactionEntity ->
                    transactionEntity.id
                }
            return 1
        }
        return 0
    }

    override suspend fun getAllTransactions(): List<TransactionEntity> {
        return transactions.toList()
    }

    override fun getAllTransactionsFlow(): Flow<List<TransactionEntity>> {
        return transactionsFlow.asStateFlow()
    }

    override suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): List<String> = emptyList()

    override suspend fun getTransactionById(
        id: Int,
    ): TransactionEntity? {
        return transactions.find { transactionEntity ->
            transactionEntity.id == id
        }
    }

    override suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<TransactionEntity> {
        return transactions.filter { transactionEntity ->
            transactionEntity.transactionTimestamp in startingTimestamp..endingTimestamp
        }
    }

    override fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<TransactionEntity>> {
        return flow {
            emit(
                value = transactions.filter { transactionEntity ->
                    transactionEntity.transactionTimestamp in startingTimestamp..endingTimestamp
                },
            )
        }
    }

    override suspend fun insertTransaction(
        transaction: TransactionEntity,
    ): Long {
        val id = if (transaction.id == 0) {
            nextId++
        } else {
            transaction.id
        }
        val exists = transactions.any { transactionEntity ->
            transactionEntity.id == id
        }
        return if (exists) {
            -1L
        } else {
            val entity = transaction.copy(
                id = id,
            )
            transactions.add(
                element = entity,
            )
            transactionsFlow.value =
                transactions.sortedBy { transactionEntity ->
                    transactionEntity.id
                }
            id.toLong()
        }
    }

    override suspend fun insertTransactions(
        vararg transactions: TransactionEntity,
    ): List<Long> {
        val result = mutableListOf<Long>()
        for (transaction in transactions) {
            result.add(
                element = insertTransaction(
                    transaction = transaction,
                ),
            )
        }
        return result
    }

    override suspend fun updateTransaction(
        transaction: TransactionEntity,
    ): Int {
        val index = transactions.indexOfFirst { transactionEntity ->
            transactionEntity.id == transaction.id
        }
        return if (index != -1) {
            transactions[index] = transaction
            transactionsFlow.value =
                transactions.sortedBy { transactionEntity ->
                    transactionEntity.id
                }
            1
        } else {
            0
        }
    }

    override suspend fun updateTransactions(
        vararg transactions: TransactionEntity,
    ): Int {
        var updatedCount = 0
        for (transaction in transactions) {
            updatedCount += updateTransaction(
                transaction = transaction,
            )
        }
        return updatedCount
    }
}
