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

package com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.transaction

import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Transaction
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

internal interface TransactionRepository {
    suspend fun checkIfAccountIsUsedInTransactions(
        accountId: Int,
    ): Boolean

    suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean

    suspend fun checkIfTransactionForIsUsedInTransactions(
        transactionForId: Int,
    ): Boolean

    suspend fun deleteAllTransactions(): Boolean

    suspend fun deleteTransactionById(
        id: Int,
    ): Boolean

    suspend fun getAllTransactions(): ImmutableList<Transaction>

    suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): ImmutableList<String>

    suspend fun getTransactionById(
        id: Int,
    ): Transaction?

    suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): ImmutableList<Transaction>

    fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<ImmutableList<Transaction>>

    suspend fun insertTransaction(
        accountFrom: Account?,
        accountTo: Account?,
        transaction: Transaction,
        originalTransaction: Transaction?,
    ): Long

    suspend fun insertTransactions(
        vararg transactions: Transaction,
    ): ImmutableList<Long>

    suspend fun restoreData(
        categories: ImmutableList<Category>,
        accounts: ImmutableList<Account>,
        transactions: ImmutableList<Transaction>,
        transactionForValues: ImmutableList<TransactionFor>,
    ): Boolean

    suspend fun updateTransaction(
        transaction: Transaction,
    ): Boolean

    suspend fun updateTransactions(
        vararg transactions: Transaction,
    ): Boolean
}
