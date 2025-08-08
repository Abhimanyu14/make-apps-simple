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
import kotlinx.coroutines.flow.emptyFlow

public class FakeTransactionForDaoImpl : TransactionForDao {
    override fun getAllTransactionForValuesFlow(): Flow<List<TransactionForEntity>> {
        return emptyFlow()
    }

    override suspend fun getAllTransactionForValues(): List<TransactionForEntity> {
        return emptyList()
    }

    override suspend fun getTransactionForValuesCount(): Int {
        return 0
    }

    override suspend fun getTransactionForById(id: Int): TransactionForEntity? {
        return null
    }

    override suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionForEntity,
    ): List<Long> {
        return emptyList()
    }

    override suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionForEntity,
    ): Int {
        return 0
    }

    override suspend fun deleteTransactionForById(
        id: Int,
    ): Int {
        return 0
    }

    override suspend fun deleteAllTransactionForValues(): Int {
        return 0
    }
}
