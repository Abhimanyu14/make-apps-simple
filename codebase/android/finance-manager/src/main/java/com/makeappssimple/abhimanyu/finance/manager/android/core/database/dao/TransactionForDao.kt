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
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionForEntity
import kotlinx.coroutines.flow.Flow

@Dao
public interface TransactionForDao {
    @Query(value = "SELECT * from transaction_for_table ORDER BY id ASC")
    public fun getAllTransactionForValuesFlow(): Flow<List<TransactionForEntity>>

    @Query(value = "SELECT * from transaction_for_table ORDER BY id ASC")
    public suspend fun getAllTransactionForValues(): List<TransactionForEntity>

    @Query(value = "SELECT COUNT(*) FROM transaction_for_table")
    public suspend fun getTransactionForValuesCount(): Int

    @Query(value = "SELECT * from transaction_for_table WHERE id = :id")
    public suspend fun getTransactionFor(
        id: Int,
    ): TransactionForEntity?

    // TODO(Abhi): Handle conflicts with error handling properly
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionForEntity,
    ): List<Long>

    @Update
    public suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionForEntity,
    ): Int

    @Query(value = "DELETE FROM transaction_for_table WHERE id = :id")
    public suspend fun deleteTransactionFor(
        id: Int,
    ): Int

    @Query(value = "DELETE FROM transaction_for_table")
    public suspend fun deleteAllTransactionForValues(): Int
}
