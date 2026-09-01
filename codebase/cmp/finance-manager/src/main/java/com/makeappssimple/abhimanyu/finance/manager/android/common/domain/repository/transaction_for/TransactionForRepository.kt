/*
 * Copyright 2025-2026 Abhimanyu
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

package com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.transaction_for

import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

internal interface TransactionForRepository {
    suspend fun deleteTransactionForById(
        id: Int,
    ): Boolean

    suspend fun getAllTransactionForValues(): ImmutableList<TransactionFor>

    fun getAllTransactionForValuesFlow(): Flow<ImmutableList<TransactionFor>>

    suspend fun getTransactionForById(
        id: Int,
    ): TransactionFor?

    suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): ImmutableList<Long>

    suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): Boolean
}
