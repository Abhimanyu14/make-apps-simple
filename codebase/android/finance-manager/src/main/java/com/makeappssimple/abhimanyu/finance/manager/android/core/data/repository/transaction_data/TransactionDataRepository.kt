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

package com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction_data

import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

public interface TransactionDataRepository {
    public suspend fun deleteTransactionById(
        id: Int,
    ): Boolean

    public suspend fun getAllTransactionData(): ImmutableList<TransactionData>

    public fun getAllTransactionDataFlow(): Flow<ImmutableList<TransactionData>>

    public fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<ImmutableList<TransactionData>>

    public suspend fun getSearchedTransactionData(
        searchText: String,
    ): ImmutableList<TransactionData>

    public suspend fun getTransactionDataById(
        id: Int,
    ): TransactionData?

    public suspend fun insertTransaction(
        accountFrom: Account?,
        accountTo: Account?,
        transaction: Transaction,
        originalTransaction: Transaction?,
    ): Long

    public suspend fun restoreData(
        categories: ImmutableList<Category>,
        accounts: ImmutableList<Account>,
        transactions: ImmutableList<Transaction>,
        transactionForValues: ImmutableList<TransactionFor>,
    ): Boolean
}
