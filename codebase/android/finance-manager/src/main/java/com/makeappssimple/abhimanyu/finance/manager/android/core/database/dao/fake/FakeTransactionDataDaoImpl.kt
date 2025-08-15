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

import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.TransactionDataDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionDataEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

/**
 * In-memory fake implementation of [TransactionDataDao] for testing purposes.
 */
public class FakeTransactionDataDaoImpl : TransactionDataDao {
    private val transactionDataListInternal =
        mutableListOf<TransactionDataEntity>()
    private val transactionDataListFlow: MutableStateFlow<List<TransactionDataEntity>> =
        MutableStateFlow(
            value = emptyList(),
        )

    public fun setData(
        data: List<TransactionDataEntity>,
    ) {
        transactionDataListInternal.clear()
        transactionDataListInternal.addAll(data)
        transactionDataListFlow.value = transactionDataListInternal
            .sortedByDescending {
                it.transaction.transactionTimestamp
            }
            .toList()
    }

    override suspend fun getAllTransactionData(): List<TransactionDataEntity> {
        return transactionDataListInternal
            .sortedByDescending {
                it.transaction.transactionTimestamp
            }
            .toList()
    }

    override fun getAllTransactionDataFlow(): Flow<List<TransactionDataEntity>> {
        return transactionDataListFlow
    }

    override fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<List<TransactionDataEntity>> {
        return transactionDataListFlow.map {
            it.take(
                n = numberOfTransactions,
            )
        }
    }

    override suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionDataEntity> {
        val lowerSearchText = searchText.lowercase()
        return transactionDataListInternal
            .filter {
                it.transaction.title
                    .lowercase()
                    .contains(
                        other = lowerSearchText,
                    ) || it.transaction.amount.value
                    .toString()
                    .lowercase()
                    .contains(
                        other = lowerSearchText,
                    )
            }.sortedByDescending {
                it.transaction.transactionTimestamp
            }.toList()
    }

    override suspend fun getTransactionDataById(
        id: Int,
    ): TransactionDataEntity? {
        return transactionDataListInternal.find {
            it.transaction.id == id
        }
    }
}
