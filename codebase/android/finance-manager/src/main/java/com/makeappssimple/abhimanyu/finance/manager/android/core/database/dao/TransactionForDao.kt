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

/**
 * Data Access Object for transaction_for_table.
 */
@Dao
public interface TransactionForDao {
    /**
     * Delete all transaction for values from the table.
     * @return Number of rows deleted
     */
    @Query(value = "DELETE FROM transaction_for_table")
    public suspend fun deleteAllTransactionForValues(): Int

    /**
     * Delete a transaction for value by id.
     * @param id Required transaction for id
     * @return Number of rows deleted (0 or 1)
     */
    @Query(value = "DELETE FROM transaction_for_table WHERE id = :id")
    public suspend fun deleteTransactionForById(
        id: Int,
    ): Int

    /**
     * Get all transaction for values as a list.
     * @return Returns all transaction for values ordered by [TransactionForEntity.id]
     */
    @Query(value = "SELECT * from transaction_for_table ORDER BY id ASC")
    public suspend fun getAllTransactionForValues(): List<TransactionForEntity>

    /**
     * Get all transaction for values as a Flow.
     * @return Flow emitting the list of all transaction for values ordered by [TransactionForEntity.id]
     */
    @Query(value = "SELECT * from transaction_for_table ORDER BY id ASC")
    public fun getAllTransactionForValuesFlow(): Flow<List<TransactionForEntity>>

    /**
     * Get a transaction for value by id.
     * @param id Required transaction for id
     * @return TransactionFor with given [id] or null if not found
     */
    @Query(value = "SELECT * from transaction_for_table WHERE id = :id")
    public suspend fun getTransactionForById(
        id: Int,
    ): TransactionForEntity?

    /**
     * Insert transaction for values into the table.
     * @param transactionForValues Transaction for values to insert
     * @return List of row ids for inserted values. -1 if a conflict occurred for that item.
     */
    // TODO(Abhi): Handle conflicts with error handling properly
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionForEntity,
    ): List<Long>

    /**
     * Update transaction for values in the table.
     * Only updates the existing rows using the primary key
     * @param transactionForValues Transaction for values to update
     * @return Number of rows updated
     */
    @Update
    public suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionForEntity,
    ): Int
}
