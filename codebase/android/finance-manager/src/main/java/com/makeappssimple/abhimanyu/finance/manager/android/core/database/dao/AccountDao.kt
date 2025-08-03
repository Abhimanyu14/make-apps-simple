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

package com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
public interface AccountDao {
    @Query(value = "SELECT * from account_table ORDER BY id ASC")
    public fun getAllAccountsFlow(): Flow<List<AccountEntity>>

    @Query(value = "SELECT * from account_table ORDER BY id ASC")
    public suspend fun getAllAccounts(): List<AccountEntity>

    @Query(value = "SELECT COUNT(*) FROM account_table")
    public suspend fun getAllAccountsCount(): Int

    @Query(value = "SELECT * from account_table WHERE id = :id")
    public suspend fun getAccount(
        id: Int,
    ): AccountEntity?

    @Query(value = "SELECT * from account_table WHERE id IN (:ids)")
    public suspend fun getAccounts(
        ids: List<Int>,
    ): List<AccountEntity>

    // TODO(Abhi): Handle conflicts with error handling properly
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public suspend fun insertAccounts(
        vararg accounts: AccountEntity,
    ): List<Long>

    @Update
    public suspend fun updateAccounts(
        vararg accounts: AccountEntity,
    ): Int

    @Query(value = "DELETE FROM account_table WHERE id = :id")
    public suspend fun deleteAccount(
        id: Int,
    ): Int

    @Delete
    public suspend fun deleteAccounts(
        vararg accounts: AccountEntity,
    ): Int

    @Query(value = "DELETE FROM account_table")
    public suspend fun deleteAllAccounts(): Int
}
