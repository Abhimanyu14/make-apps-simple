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

import android.database.sqlite.SQLiteConstraintException
import androidx.sqlite.SQLiteException
import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.datasource.CommonDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

internal class TransactionRepositoryImpl(
    private val commonDataSource: CommonDataSource,
    private val dispatcherProvider: DispatcherProvider,
    private val transactionDao: TransactionDao,
) : TransactionRepository {
    override suspend fun checkIfAccountIsUsedInTransactions(
        accountId: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionDao.checkIfAccountIsUsedInTransactions(
                    accountId = accountId,
                )
            } catch (
                _: SQLiteException,
            ) {
                false
            }
        }
    }

    override suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionDao.checkIfCategoryIsUsedInTransactions(
                    categoryId = categoryId,
                )
            } catch (
                _: SQLiteException,
            ) {
                false
            }
        }
    }

    override suspend fun checkIfTransactionForIsUsedInTransactions(
        transactionForId: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionDao.checkIfTransactionForIsUsedInTransactions(
                    transactionForId = transactionForId,
                )
            } catch (
                _: SQLiteException,
            ) {
                false
            }
        }
    }

    override suspend fun deleteAllTransactions(): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionDao.deleteAllTransactions() > 0
            } catch (
                _: SQLiteException,
            ) {
                false
            }
        }
    }

    override suspend fun deleteTransactionById(
        id: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                commonDataSource.deleteTransactionById(
                    id = id,
                )
            } catch (
                _: SQLiteException,
            ) {
                false
            }
        }
    }

    override suspend fun getAllTransactions(): ImmutableList<Transaction> {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionDao.getAllTransactions().map(
                    transform = TransactionEntity::asExternalModel,
                )
            } catch (
                _: SQLiteException,
            ) {
                persistentListOf()
            }
        }
    }

    override suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): ImmutableList<String> {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionDao.getTitleSuggestions(
                    categoryId = categoryId,
                    numberOfSuggestions = numberOfSuggestions,
                    enteredTitle = enteredTitle,
                ).toImmutableList()
            } catch (
                _: SQLiteException,
            ) {
                persistentListOf()
            }
        }
    }

    override suspend fun getTransactionById(
        id: Int,
    ): Transaction? {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionDao.getTransactionById(
                    id = id,
                )?.asExternalModel()
            } catch (
                _: SQLiteException,
            ) {
                null
            }
        }
    }

    override suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): ImmutableList<Transaction> {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionDao.getTransactionsBetweenTimestamps(
                    startingTimestamp = startingTimestamp,
                    endingTimestamp = endingTimestamp,
                ).map(
                    transform = TransactionEntity::asExternalModel,
                )
            } catch (
                _: SQLiteException,
            ) {
                persistentListOf()
            }
        }
    }

    override fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<ImmutableList<Transaction>> {
        return try {
            transactionDao.getTransactionsBetweenTimestampsFlow(
                startingTimestamp = startingTimestamp,
                endingTimestamp = endingTimestamp,
            ).map {
                it.map(
                    transform = TransactionEntity::asExternalModel,
                )
            }
        } catch (
            _: SQLiteException,
        ) {
            emptyFlow()
        }
    }

    override suspend fun getTransactionsCount(): Int {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionDao.getTransactionsCount()
            } catch (
                _: SQLiteException,
            ) {
                0
            }
        }
    }

    override suspend fun insertTransaction(
        accountFrom: Account?,
        accountTo: Account?,
        transaction: Transaction,
        originalTransaction: Transaction?,
    ): Long {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                commonDataSource.insertTransaction(
                    accountFrom = accountFrom?.asEntity(),
                    accountTo = accountTo?.asEntity(),
                    transaction = transaction.asEntity(),
                    originalTransaction = originalTransaction?.asEntity(),
                )
            } catch (
                _: SQLiteConstraintException,
            ) {
                // TODO(Abhi): Check if this needs additional handling
                -1L
            } catch (
                _: SQLiteException,
            ) {
                -1L
            }
        }
    }

    override suspend fun insertTransactions(
        vararg transactions: Transaction,
    ): ImmutableList<Long> {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionDao.insertTransactions(
                    transactions = transactions.map(
                        transform = Transaction::asEntity,
                    ).toTypedArray(),
                ).toImmutableList()
            } catch (
                _: SQLiteConstraintException,
            ) {
                // TODO(Abhi): Check if this needs additional handling
                persistentListOf()
            } catch (
                _: SQLiteException,
            ) {
                persistentListOf()
            }
        }
    }

    override suspend fun restoreData(
        categories: ImmutableList<Category>,
        accounts: ImmutableList<Account>,
        transactions: ImmutableList<Transaction>,
        transactionForValues: ImmutableList<TransactionFor>,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                commonDataSource.restoreData(
                    categories = categories.map(
                        transform = Category::asEntity,
                    ).toTypedArray(),
                    accounts = accounts.map(
                        transform = Account::asEntity,
                    ).toTypedArray(),
                    transactions = transactions.map(
                        transform = Transaction::asEntity,
                    ).toTypedArray(),
                    transactionForValues = transactionForValues.map(
                        transform = TransactionFor::asEntity,
                    ).toTypedArray(),
                )
            } catch (
                _: SQLiteException,
            ) {
                false
            }
        }
    }

    override suspend fun updateTransaction(
        transaction: Transaction,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionDao.updateTransaction(
                    transaction = transaction.asEntity(),
                ) == 1
            } catch (
                _: SQLiteException,
            ) {
                false
            }
        }
    }

    override suspend fun updateTransactions(
        vararg transactions: Transaction,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionDao.updateTransactions(
                    transactions = transactions.map(
                        transform = Transaction::asEntity,
                    ).toTypedArray(),
                ) == transactions.size
            } catch (
                _: SQLiteException,
            ) {
                false
            }
        }
    }
}
