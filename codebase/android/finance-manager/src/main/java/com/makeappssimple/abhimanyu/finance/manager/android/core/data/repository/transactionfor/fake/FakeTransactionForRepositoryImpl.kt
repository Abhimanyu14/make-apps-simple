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

package com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transactionfor.fake

import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public class FakeTransactionForRepositoryImpl : TransactionForRepository {
    override fun getAllTransactionForValuesFlow(): Flow<ImmutableList<TransactionFor>> {
        return flow {
            emptyList<TransactionFor>()
        }
    }

    override suspend fun getAllTransactionForValues(): ImmutableList<TransactionFor> {
        return persistentListOf()
    }

    override suspend fun getTransactionFor(
        id: Int,
    ): TransactionFor? {
        return null
    }

    override suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): ImmutableList<Long> {
        return persistentListOf()
    }

    override suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): Boolean {
        return false
    }

    override suspend fun deleteTransactionFor(
        id: Int,
    ): Boolean {
        return false
    }
}
