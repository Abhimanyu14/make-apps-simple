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

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.repository.transaction

import androidx.sqlite.SQLiteException
import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.model.asEntity
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.datasource.CommonDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.asExternalModel
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Transaction
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
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
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
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
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
            }
        }
    }

    override suspend fun deleteAllTransactions(): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionDao.deleteAllTransactions() > 0
            } catch (
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
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
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
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
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
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
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
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
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
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
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
            }
        }
    }

    override fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<ImmutableList<Transaction>> {
        return transactionDao
            .getTransactionsBetweenTimestampsFlow(
                startingTimestamp = startingTimestamp,
                endingTimestamp = endingTimestamp,
            )
            .catch { throwable: Throwable ->
                if (throwable is SQLiteException) {
                    error(
                        message = "Database Error: ${throwable.localizedMessage}",
                    )
                }
            }
            .map {
                it.map(
                    transform = TransactionEntity::asExternalModel,
                )
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
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
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
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
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
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
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
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
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
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
            }
        }
    }
}
