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

import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.AccountDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.TransactionDataDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.AccountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.TransactionDataEntity
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.TransactionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

/**
 * Fake implementation of [TransactionDataDao] for testing purposes,
 * using other fake DAO implementations.
 */
internal class FakeTransactionDataDaoImpl(
    private val accountDao: AccountDao,
    private val categoryDao: CategoryDao,
    private val transactionDao: TransactionDao,
    private val transactionForDao: TransactionForDao,
) : TransactionDataDao {
    override fun getAccountsInTransactionsFlow(): Flow<List<AccountEntity>> {
        return accountDao.getAllAccountsFlow()
    }

    override suspend fun getAllTransactionData(): List<TransactionDataEntity> {
        val transactions = transactionDao.getAllTransactions()
        return getSortedTransactionDataEntity(
            transactions = transactions,
        )
    }

    override fun getAllTransactionDataFlow(
        searchText: String,
        selectedAccountIds: List<Int>?,
    ): Flow<List<TransactionDataEntity>> {
        return transactionDao.getAllTransactionsFlow().map { transactions ->
            getSortedTransactionDataEntity(
                transactions = transactions,
            )
        }
    }

    override fun getCategoriesInTransactionsFlow(): Flow<List<CategoryEntity>> {
        return emptyFlow()
    }

    override fun getOldestTransactionTimestampFlow(): Flow<Long?> {
        return emptyFlow()
    }

    override fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<List<TransactionDataEntity>> {
        return getAllTransactionDataFlow(
            searchText = "",
            selectedAccountIds = null,
        ).map { transactionDataList ->
            transactionDataList.take(
                n = numberOfTransactions,
            )
        }
    }

    override suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionDataEntity> {
        val allTransactionData = getAllTransactionData()
        val lowercaseSearchText = searchText.lowercase()
        return allTransactionData.filter { transactionData ->
            val titleMatch = transactionData.transaction.title
                .lowercase()
                .contains(
                    other = lowercaseSearchText,
                )
            val amountMatch = transactionData.transaction.amount.value
                .toString()
                .lowercase()
                .contains(
                    other = lowercaseSearchText,
                )
            titleMatch || amountMatch
        }
    }

    override suspend fun getTransactionDataById(
        id: Int,
    ): TransactionDataEntity? {
        val transaction = transactionDao.getTransactionById(
            id = id,
        )
        return transaction?.let {
            mapToTransactionDataEntity(it)
        }
    }

    private suspend fun getSortedTransactionDataEntity(
        transactions: List<TransactionEntity>,
    ): List<TransactionDataEntity> {
        return transactions
            .map { transaction ->
                mapToTransactionDataEntity(
                    transaction = transaction,
                )
            }
            .sortedByDescending {
                it.transaction.transactionTimestamp
            }
    }

    private suspend fun mapToTransactionDataEntity(
        transaction: TransactionEntity,
    ): TransactionDataEntity {
        val category = transaction.categoryId?.let {
            categoryDao.getCategoryById(
                id = it,
            )
        }
        val accountFrom = transaction.accountFromId?.let {
            accountDao.getAccountById(
                id = it,
            )
        }
        val accountTo = transaction.accountToId?.let {
            accountDao.getAccountById(
                id = it,
            )
        }
        val transactionFor = transactionForDao.getTransactionForById(
            id = transaction.transactionForId,
        )
        checkNotNull(
            value = transactionFor,
            lazyMessage = {
                "TransactionForEntity with id ${transaction.transactionForId} not found for transaction ${transaction.id}"
            },
        )
        return TransactionDataEntity(
            transaction = transaction,
            category = category,
            accountFrom = accountFrom,
            accountTo = accountTo,
            transactionFor = transactionFor,
        )
    }
}
