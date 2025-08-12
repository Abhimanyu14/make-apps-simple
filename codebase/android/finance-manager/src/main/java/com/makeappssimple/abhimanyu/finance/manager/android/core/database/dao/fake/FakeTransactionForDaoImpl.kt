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

import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionForEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * In-memory fake implementation of [TransactionForDao] for testing purposes.
 */
public class FakeTransactionForDaoImpl : TransactionForDao {
    private val transactionForValues = mutableListOf<TransactionForEntity>()
    private val transactionForValuesFlow: MutableStateFlow<List<TransactionForEntity>> =
        MutableStateFlow(
            value = emptyList(),
        )
    private var nextId = 1

    override suspend fun deleteAllTransactionForValues(): Int {
        val count = transactionForValues.size
        transactionForValues.clear()
        transactionForValuesFlow.value = emptyList()
        return count
    }

    override suspend fun deleteTransactionForById(
        id: Int,
    ): Int {
        val removed = transactionForValues.removeIf { transactionForEntity ->
            transactionForEntity.id == id
        }
        if (removed) {
            transactionForValuesFlow.value =
                transactionForValues.sortedBy { transactionForEntity ->
                    transactionForEntity.id
                }
            return 1
        }
        return 0
    }

    override fun getAllTransactionForValuesFlow(): Flow<List<TransactionForEntity>> {
        return transactionForValuesFlow.asStateFlow()
    }

    override suspend fun getAllTransactionForValues(): List<TransactionForEntity> {
        return transactionForValues.toList()
    }

    override suspend fun getTransactionForById(
        id: Int,
    ): TransactionForEntity? {
        return transactionForValues.find {
            it.id == id
        }
    }

    override suspend fun getTransactionForValuesCount(): Int {
        return transactionForValues.size
    }

    override suspend fun insertTransactionForValues(
        vararg newValues: TransactionForEntity,
    ): List<Long> {
        val result = mutableListOf<Long>()
        for (value in newValues) {
            val id = if (value.id == 0) {
                nextId++
            } else {
                value.id
            }
            val exists = transactionForValues.any { transactionForEntity ->
                transactionForEntity.id == id
            }
            if (exists) {
                result.add(
                    element = -1L,
                )
            } else {
                val entity = value.copy(
                    id = id,
                )
                transactionForValues.add(
                    element = entity,
                )
                result.add(
                    element = id.toLong(),
                )
            }
        }
        transactionForValuesFlow.value =
            transactionForValues.sortedBy { transactionForEntity ->
                transactionForEntity.id
            }
        return result
    }

    override suspend fun updateTransactionForValues(
        vararg updatedValues: TransactionForEntity,
    ): Int {
        var updatedCount = 0
        for (value in updatedValues) {
            val index =
                transactionForValues.indexOfFirst { transactionForEntity ->
                    transactionForEntity.id == value.id
                }
            if (index != -1) {
                transactionForValues[index] = value
                updatedCount++
            }
        }
        transactionForValuesFlow.value =
            transactionForValues.sortedBy { transactionForEntity ->
                transactionForEntity.id
            }
        return updatedCount
    }
}
