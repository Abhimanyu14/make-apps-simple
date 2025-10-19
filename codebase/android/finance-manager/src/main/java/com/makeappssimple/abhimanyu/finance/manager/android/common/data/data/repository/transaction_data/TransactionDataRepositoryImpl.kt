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

import androidx.sqlite.SQLiteException
import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.model.asEntity
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.TransactionDataDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.datasource.CommonDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.TransactionDataEntity
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.asExternalModel
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Transaction
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class TransactionDataRepositoryImpl(
    private val commonDataSource: CommonDataSource,
    private val dispatcherProvider: DispatcherProvider,
    private val transactionDataDao: TransactionDataDao,
) : TransactionDataRepository {
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

    override suspend fun getAllTransactionData(): ImmutableList<TransactionData> {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionDataDao.getAllTransactionData().map(
                    transform = TransactionDataEntity::asExternalModel,
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

    override fun getAllTransactionDataFlow(): Flow<ImmutableList<TransactionData>> {
        return try {
            transactionDataDao.getAllTransactionDataFlow().map {
                it.map(
                    transform = TransactionDataEntity::asExternalModel,
                )
            }
        } catch (
            sqliteException: SQLiteException,
        ) {
            error(
                message = "Database Error: ${sqliteException.localizedMessage}",
            )
        }
    }

    override fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<ImmutableList<TransactionData>> {
        return try {
            transactionDataDao.getRecentTransactionDataFlow(
                numberOfTransactions = numberOfTransactions,
            ).map {
                it.map(
                    transform = TransactionDataEntity::asExternalModel,
                )
            }
        } catch (
            sqliteException: SQLiteException,
        ) {
            error(
                message = "Database Error: ${sqliteException.localizedMessage}",
            )
        }
    }

    override suspend fun getSearchedTransactionData(
        searchText: String,
    ): ImmutableList<TransactionData> {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionDataDao.getSearchedTransactionData(
                    searchText = searchText,
                ).map(
                    transform = TransactionDataEntity::asExternalModel,
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

    override suspend fun getTransactionDataById(
        id: Int,
    ): TransactionData? {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionDataDao.getTransactionDataById(
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
}
