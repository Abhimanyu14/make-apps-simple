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

import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.AccountEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

public class FakeAccountDaoImpl : AccountDao {
    override fun getAllAccountsFlow(): Flow<List<AccountEntity>> {
        return emptyFlow()
    }

    override suspend fun getAllAccounts(): List<AccountEntity> {
        return emptyList()
    }

    override suspend fun getAllAccountsCount(): Int {
        return 0
    }

    override suspend fun getAccount(
        id: Int,
    ): AccountEntity? {
        return null
    }

    override suspend fun getAccounts(
        ids: List<Int>,
    ): List<AccountEntity> {
        return emptyList()
    }

    override suspend fun insertAccounts(
        vararg accounts: AccountEntity,
    ): List<Long> {
        return emptyList()
    }

    override suspend fun updateAccounts(
        vararg accounts: AccountEntity,
    ): Int {
        return 0
    }

    override suspend fun deleteAccountById(
        id: Int,
    ): Int {
        return 0
    }

    override suspend fun deleteAccounts(
        vararg accounts: AccountEntity,
    ): Int {
        return 0
    }

    override suspend fun deleteAllAccounts(): Int {
        return 0
    }
}
