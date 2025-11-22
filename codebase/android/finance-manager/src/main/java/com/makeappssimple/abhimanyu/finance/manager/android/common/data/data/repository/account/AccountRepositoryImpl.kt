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

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.repository.account

import androidx.sqlite.SQLiteException
import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.model.asEntity
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.AccountDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.AccountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.asExternalModel
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.updateBalanceAmount
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.account.AccountRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

internal class AccountRepositoryImpl(
    private val accountDao: AccountDao,
    private val dispatcherProvider: DispatcherProvider,
) : AccountRepository {
    override suspend fun deleteAccountById(
        id: Int,
    ): Int {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                accountDao.deleteAccountById(
                    id = id,
                )
            } catch (
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
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
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
            }
        }
    }

    override suspend fun getAccountsByIds(
        ids: ImmutableList<Int>,
    ): ImmutableList<Account> {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                accountDao.getAccountsByIds(
                    ids = ids,
                ).map(
                    transform = AccountEntity::asExternalModel,
                )
            } catch (
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
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
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
            }
        }
    }

    override fun getAllAccountsFlow(): Flow<ImmutableList<Account>> {
        return accountDao
            .getAllAccountsFlow()
            .catch { throwable: Throwable ->
                if (throwable is SQLiteException) {
                    error(
                        message = "Database Error: ${throwable.localizedMessage}",
                    )
                }
            }
            .map {
                it.map(
                    transform = AccountEntity::asExternalModel,
                )
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
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
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
            val accounts = getAccountsByIds(
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
                sqliteException: SQLiteException,
            ) {
                error(
                    message = "Database Error: ${sqliteException.localizedMessage}",
                )
            }
        }
    }
}
