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

package com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.account

import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

internal interface AccountRepository {
    suspend fun deleteAccountById(
        id: Int,
    ): Int

    suspend fun getAccountById(
        id: Int,
    ): Account?

    suspend fun getAccountsByIds(
        ids: ImmutableList<Int>,
    ): ImmutableList<Account>

    suspend fun getAllAccounts(): ImmutableList<Account>

    fun getAllAccountsFlow(): Flow<ImmutableList<Account>>

    suspend fun insertAccounts(
        vararg accounts: Account,
    ): ImmutableList<Long>

    suspend fun updateAccountBalanceAmount(
        accountsBalanceAmountChange: ImmutableList<Pair<Int, Long>>,
    ): Int

    suspend fun updateAccounts(
        vararg accounts: Account,
    ): Int
}
