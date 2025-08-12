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

import android.database.sqlite.SQLiteConstraintException
import androidx.sqlite.SQLiteException
import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.updateBalanceAmount
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

internal class AccountRepositoryImpl(
    private val accountDao: AccountDao,
    private val dispatcherProvider: DispatcherProvider,
) : AccountRepository {
    override suspend fun deleteAccountById(
        id: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                accountDao.deleteAccountById(
                    id = id,
                ) == 1
            } catch (
                _: SQLiteException,
            ) {
                false
            }
        }
    }

    override suspend fun getAccountById(
        id: Int,
    ): Account? {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                accountDao.getAccountById(
                    id = id,
                )?.asExternalModel()
            } catch (
                _: SQLiteException,
            ) {
                null
            }
        }
    }

    override suspend fun getAccounts(
        ids: ImmutableList<Int>,
    ): ImmutableList<Account> {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                accountDao.getAccounts(
                    ids = ids,
                ).map(
                    transform = AccountEntity::asExternalModel,
                )
            } catch (
                _: SQLiteException,
            ) {
                persistentListOf()
            }
        }
    }

    override suspend fun getAllAccounts(): ImmutableList<Account> {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                accountDao.getAllAccounts().map(
                    transform = AccountEntity::asExternalModel,
                )
            } catch (
                _: SQLiteException,
            ) {
                persistentListOf()
            }
        }
    }

    override fun getAllAccountsFlow(): Flow<ImmutableList<Account>> {
        return try {
            accountDao.getAllAccountsFlow().map {
                it.map(
                    transform = AccountEntity::asExternalModel,
                )
            }
        } catch (
            _: SQLiteException,
        ) {
            emptyFlow()
        }
    }

    override suspend fun insertAccounts(
        vararg accounts: Account,
    ): ImmutableList<Long> {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                accountDao.insertAccounts(
                    accounts = accounts.map(
                        transform = Account::asEntity,
                    ).toTypedArray(),
                ).toImmutableList()
            } catch (
                _: SQLiteConstraintException,
            ) {
                // TODO(Abhi): Check if this can be handled better
                persistentListOf()
            } catch (
                _: SQLiteException,
            ) {
                persistentListOf()
            }
        }
    }

    // TODO(Abhi): Revisit this method, either remove it or implement it properly.
    //  This method is not used anywhere in the codebase.
    override suspend fun updateAccountBalanceAmount(
        accountsBalanceAmountChange: ImmutableList<Pair<Int, Long>>,
    ): Int {
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
    ): Int {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                accountDao.updateAccounts(
                    accounts = accounts.map(
                        transform = Account::asEntity,
                    ).toTypedArray(),
                )
            } catch (
                _: SQLiteException,
            ) {
                0
            }
        }
    }
}
