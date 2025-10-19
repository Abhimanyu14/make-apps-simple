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

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.AccountEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for account_table.
 */
@Dao
public interface AccountDao {
    /**
     * Delete an account by id.
     * @param id Account id
     * @return Number of rows deleted
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(value = "DELETE FROM account_table WHERE id = :id")
    public suspend fun deleteAccountById(
        id: Int,
    ): Int

    /**
     * Delete all accounts from the table.
     * @return Number of rows deleted
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(value = "DELETE FROM account_table")
    public suspend fun deleteAllAccounts(): Int

    /**
     * Get an account by id.
     * @param id Account id
     * @return Account with given [id] or null if not found
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(value = "SELECT * from account_table WHERE id = :id")
    public suspend fun getAccountById(
        id: Int,
    ): AccountEntity?

    /**
     * Get accounts by a list of ids.
     * @param ids List of account ids
     * @return List of accounts with the given ids
     */
    @Query(value = "SELECT * from account_table WHERE id IN (:ids)")
    public suspend fun getAccountsByIds(
        ids: List<Int>,
    ): List<AccountEntity>

    /**
     * Get all accounts as a list.
     * @return Returns all accounts ordered by [AccountEntity.id]
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(value = "SELECT * from account_table ORDER BY id ASC")
    public suspend fun getAllAccounts(): List<AccountEntity>

    /**
     * Get all accounts as a Flow.
     * @return Flow emitting the list of all accounts ordered by [AccountEntity.id]
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(value = "SELECT * from account_table ORDER BY id ASC")
    public fun getAllAccountsFlow(): Flow<List<AccountEntity>>

    /**
     * Insert accounts into the table.
     * @param accounts Accounts to insert
     * @return List of row ids for inserted accounts. -1 if a conflict occurred for that item.
     * @throws SQLiteConstraintException if a constraint is violated, such as a unique constraint
     * @throws SQLiteException if there is a general SQLite error
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    public suspend fun insertAccounts(
        vararg accounts: AccountEntity,
    ): List<Long>

    /**
     * Update accounts in the table.
     * Only updates the existing rows using the primary key
     * @param accounts Accounts to update
     * @return Number of rows updated
     * @throws SQLiteException if there is a general SQLite error
     */
    @Update
    public suspend fun updateAccounts(
        vararg accounts: AccountEntity,
    ): Int
}
