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

package com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction_for

import androidx.sqlite.SQLiteException
import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class TransactionForRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val transactionForDao: TransactionForDao,
) : TransactionForRepository {
    override suspend fun deleteTransactionForById(
        id: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionForDao.deleteTransactionForById(
                    id = id,
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

    override suspend fun getAllTransactionForValues(): ImmutableList<TransactionFor> {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionForDao.getAllTransactionForValues().map(
                    transform = TransactionForEntity::asExternalModel,
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

    override fun getAllTransactionForValuesFlow(): Flow<ImmutableList<TransactionFor>> {
        return try {
            transactionForDao.getAllTransactionForValuesFlow().map {
                it.map(
                    transform = TransactionForEntity::asExternalModel,
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

    override suspend fun getTransactionForById(
        id: Int,
    ): TransactionFor? {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionForDao.getTransactionForById(
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

    override suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): ImmutableList<Long> {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionForDao.insertTransactionForValues(
                    transactionForValues = transactionForValues.map(
                        transform = TransactionFor::asEntity,
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

    override suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                transactionForDao.updateTransactionForValues(
                    transactionForValues = transactionForValues.map(
                        transform = TransactionFor::asEntity,
                    ).toTypedArray(),
                ) == transactionForValues.size
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
