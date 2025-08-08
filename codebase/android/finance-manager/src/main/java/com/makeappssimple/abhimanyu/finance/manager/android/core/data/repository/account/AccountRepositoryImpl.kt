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

package com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.account

import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.updateBalanceAmount
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class AccountRepositoryImpl(
    private val accountDao: AccountDao,
    private val dispatcherProvider: DispatcherProvider,
) : AccountRepository {
    override fun getAllAccountsFlow(): Flow<ImmutableList<Account>> {
        return accountDao.getAllAccountsFlow().map {
            it.map(
                transform = AccountEntity::asExternalModel,
            )
        }
    }

    override suspend fun getAllAccounts(): ImmutableList<Account> {
        return dispatcherProvider.executeOnIoDispatcher {
            accountDao.getAllAccounts().map(
                transform = AccountEntity::asExternalModel,
            )
        }
    }

    override suspend fun getAllAccountsCount(): Int {
        return dispatcherProvider.executeOnIoDispatcher {
            accountDao.getAllAccountsCount()
        }
    }

    override suspend fun getAccountById(
        id: Int,
    ): Account? {
        return dispatcherProvider.executeOnIoDispatcher {
            accountDao.getAccountById(
                id = id,
            )?.asExternalModel()
        }
    }

    override suspend fun getAccounts(
        ids: ImmutableList<Int>,
    ): ImmutableList<Account> {
        return dispatcherProvider.executeOnIoDispatcher {
            accountDao.getAccounts(
                ids = ids,
            ).map(
                transform = AccountEntity::asExternalModel,
            )
        }
    }

    override suspend fun insertAccounts(
        vararg accounts: Account,
    ): ImmutableList<Long> {
        return dispatcherProvider.executeOnIoDispatcher {
            accountDao.insertAccounts(
                accounts = accounts.map(
                    transform = Account::asEntity,
                ).toTypedArray(),
            ).toImmutableList()
        }
    }

    override suspend fun updateAccountBalanceAmount(
        accountsBalanceAmountChange: ImmutableList<Pair<Int, Long>>,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            val accountIds = accountsBalanceAmountChange.map {
                it.first
            }
            val accounts = getAccounts(
                ids = accountIds,
            )
            val updatedAccounts = accounts.mapIndexed { index, account ->
                account.updateBalanceAmount(
                    updatedBalanceAmount = account.balanceAmount.value + accountsBalanceAmountChange[index].second,
                )
            }
            updateAccounts(
                accounts = updatedAccounts.toTypedArray(),
            )
        }
    }

    override suspend fun updateAccounts(
        vararg accounts: Account,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            accountDao.updateAccounts(
                accounts = accounts.map(
                    transform = Account::asEntity,
                ).toTypedArray(),
            ) == accounts.size
        }
    }

    override suspend fun deleteAccountById(
        id: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            accountDao.deleteAccountById(
                id = id,
            ) == 1
        }
    }
}
