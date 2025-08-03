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

package com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.account.fake

import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public class FakeAccountRepositoryImpl : AccountRepository {
    override fun getAllAccountsFlow(): Flow<ImmutableList<Account>> {
        return flow {
            persistentListOf<Account>()
        }
    }

    override suspend fun getAllAccounts(): ImmutableList<Account> {
        return persistentListOf()
    }

    override suspend fun getAllAccountsCount(): Int {
        return 0
    }

    override suspend fun getAccount(
        id: Int,
    ): Account? {
        return null
    }

    override suspend fun getAccounts(
        ids: ImmutableList<Int>,
    ): ImmutableList<Account> {
        return persistentListOf()
    }

    override suspend fun insertAccounts(
        vararg accounts: Account,
    ): ImmutableList<Long> {
        return persistentListOf()
    }

    @androidx.room.Transaction
    override suspend fun updateAccountBalanceAmount(
        accountsBalanceAmountChange: ImmutableList<Pair<Int, Long>>,
    ): Boolean {
        return false
    }

    override suspend fun updateAccounts(
        vararg accounts: Account,
    ): Boolean {
        return false
    }

    override suspend fun deleteAccount(
        id: Int,
    ): Boolean {
        return false
    }

    override suspend fun deleteAccounts(
        vararg accounts: Account,
    ): Boolean {
        return false
    }
}
