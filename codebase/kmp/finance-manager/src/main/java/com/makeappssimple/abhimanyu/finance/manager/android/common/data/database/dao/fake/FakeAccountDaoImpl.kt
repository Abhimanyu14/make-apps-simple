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

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.fake

import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.AccountDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.AccountEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * In-memory fake implementation of [AccountDao] for testing purposes.
 */
internal class FakeAccountDaoImpl : AccountDao {
    private val accounts = mutableListOf<AccountEntity>()
    private val accountsFlow: MutableStateFlow<List<AccountEntity>> =
        MutableStateFlow(
            value = emptyList(),
        )
    private var nextId = 1

    override suspend fun deleteAccountById(
        id: Int,
    ): Int {
        val removed = accounts.removeIf { accountEntity ->
            accountEntity.id == id
        }
        if (removed) {
            accountsFlow.value = accounts.sortedBy { accountEntity ->
                accountEntity.id
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

    override fun getAllAccountsFlow(): Flow<List<AccountEntity>> {
        return accountsFlow.asStateFlow()
    }

    override suspend fun getAccountById(
        id: Int,
    ): AccountEntity? {
        return accounts.find { accountEntity ->
            accountEntity.id == id
        }
    }

    override suspend fun getAccountsByIds(
        ids: List<Int>,
    ): List<AccountEntity> {
        return accounts.filter { accountEntity ->
            accountEntity.id in ids
        }
    }

    override suspend fun getAllAccounts(): List<AccountEntity> {
        return accounts.toList()
    }

    override suspend fun insertAccounts(
        vararg accounts: AccountEntity,
    ): List<Long> {
        val result = mutableListOf<Long>()
        for (account in accounts) {
            val id = if (account.id == 0) {
                nextId++
            } else {
                account.id
            }
            val exists = this@FakeAccountDaoImpl.accounts.any { accountEntity ->
                accountEntity.id == id
            }
            if (exists) {
                result.add(
                    element = -1L,
                )
            } else {
                val entity = account.copy(
                    id = id,
                )
                this@FakeAccountDaoImpl.accounts.add(
                    element = entity,
                )
                result.add(
                    element = id.toLong(),
                )
            }
        }
        accountsFlow.value = this@FakeAccountDaoImpl.accounts
            .sortedBy { accountEntity ->
                accountEntity.id
            }
        return result
    }

    override suspend fun updateAccounts(
        vararg accounts: AccountEntity,
    ): Int {
        var updatedCount = 0
        for (account in accounts) {
            val index =
                this@FakeAccountDaoImpl.accounts.indexOfFirst { accountEntity ->
                    accountEntity.id == account.id
                }
            if (index != -1) {
                this@FakeAccountDaoImpl.accounts[index] = account
                updatedCount++
            }
        }
        accountsFlow.value =
            this@FakeAccountDaoImpl.accounts.sortedBy { accountEntity ->
                accountEntity.id
            }
        return updatedCount
    }
}
