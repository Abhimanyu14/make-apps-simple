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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

public class FakeAccountDaoImpl : AccountDao {
    private val accounts = mutableListOf<AccountEntity>()
    private val accountsFlow = MutableStateFlow<List<AccountEntity>>(
        value = emptyList(),
    )
    private var nextId = 1

    override suspend fun deleteAccountById(
        id: Int,
    ): Int {
        val removed = accounts.removeIf {
            it.id == id
        }
        if (removed) {
            accountsFlow.value = accounts.sortedBy {
                it.id
            }
            return 1
        }
        return 0
    }

    override suspend fun deleteAllAccounts(): Int {
        val count = accounts.size
        accounts.clear()
        accountsFlow.value = emptyList()
        return count
    }

    override suspend fun getAccountById(
        id: Int,
    ): AccountEntity? {
        return accounts.find {
            it.id == id
        }
    }

    override suspend fun getAccounts(
        ids: List<Int>,
    ): List<AccountEntity> {
        return accounts.filter {
            it.id in ids
        }
    }

    override suspend fun getAllAccounts(): List<AccountEntity> {
        return accounts.toList()
    }

    override suspend fun getAllAccountsCount(): Int {
        return accounts.size
    }

    override fun getAllAccountsFlow(): Flow<List<AccountEntity>> {
        return accountsFlow.asStateFlow()
    }

    override suspend fun insertAccounts(
        vararg newAccounts: AccountEntity,
    ): List<Long> {
        val result = mutableListOf<Long>()
        for (account in newAccounts) {
            // Simulate auto-increment if id is 0
            val id = if (account.id == 0) {
                nextId++
            } else {
                account.id
            }
            val accountExists = accounts.any {
                it.id == id
            }
            if (accountExists) {
                result.add(
                    element = -1L,
                )
            } else {
                val entity = account.copy(
                    id = id,
                )
                accounts.add(
                    element = entity,
                )
                result.add(
                    element = id.toLong(),
                )
            }
        }
        accountsFlow.value = accounts.sortedBy {
            it.id
        }
        return result
    }

    override suspend fun updateAccounts(
        vararg updatedAccounts: AccountEntity,
    ): Int {
        var updatedCount = 0
        for (account in updatedAccounts) {
            val index = accounts.indexOfFirst {
                it.id == account.id
            }
            if (index != -1) {
                accounts[index] = account
                updatedCount++
            }
        }
        accountsFlow.value = accounts.sortedBy {
            it.id
        }
        return updatedCount
    }
}
