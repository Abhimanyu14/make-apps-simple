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

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for transaction_table.
 */
@Dao
public interface TransactionDao {
    /**
     * Check if an account is used in any transactions.
     * @param accountId ID of the account to check
     * @return true if the account is used in any transactions as source or destination, false otherwise
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(
        value = """
            SELECT EXISTS(
                SELECT *
                FROM transaction_table 
                WHERE account_from_id = :accountId 
                    OR account_to_id = :accountId
            )
        """
    )
    public suspend fun checkIfAccountIsUsedInTransactions(
        accountId: Int,
    ): Boolean

    /**
     * Check if a category is used in any transactions.
     * @param categoryId ID of the category to check
     * @return true if the category is used in any transactions, false otherwise
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(
        value = "SELECT EXISTS(SELECT * FROM transaction_table " +
                "WHERE category_id = :categoryId)"
    )
    public suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean

    /**
     * Check if a transaction for value is used in any transactions.
     * @param transactionForId ID of the transaction for value to check
     * @return true if the transaction for value is used in any transactions, false otherwise
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(
        value = "SELECT EXISTS(SELECT * FROM transaction_table " +
                "WHERE transaction_for_id = :transactionForId)"
    )
    public suspend fun checkIfTransactionForIsUsedInTransactions(
        transactionForId: Int,
    ): Boolean

    /**
     * Delete all transactions from the table.
     * @return Number of rows deleted
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(value = "DELETE FROM transaction_table")
    public suspend fun deleteAllTransactions(): Int

    /**
     * Delete a transaction by id.
     * @param id Required transaction id
     * @return Number of rows deleted (0 or 1)
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(
        value = "DELETE FROM transaction_table " +
                "WHERE id = :id"
    )
    public suspend fun deleteTransactionById(
        id: Int,
    ): Int

    /**
     * Get all transactions as a list, ordered by timestamp descending.
     * @return List of all transactions
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(
        value = "SELECT * from transaction_table " +
                "ORDER BY transaction_timestamp DESC"
    )
    public suspend fun getAllTransactions(): List<TransactionEntity>

    /**
     * Get all transactions as a Flow, ordered by timestamp descending.
     * @return Flow emitting the list of all transactions
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(
        value = "SELECT * from transaction_table " +
                "ORDER BY transaction_timestamp DESC"
    )
    public fun getAllTransactionsFlow(): Flow<List<TransactionEntity>>

    /**
     * Get title suggestions for a category based on existing transactions.
     * @param categoryId ID of the category
     * @param numberOfSuggestions Maximum number of suggestions to return
     * @param enteredTitle Partial title to match against
     * @return List of suggested titles, ordered by frequency of use
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(
        value = "SELECT title from transaction_table " +
                "WHERE category_id = :categoryId " +
                "AND title LIKE '%' || :enteredTitle || '%' " +
                "GROUP BY title " +
                "ORDER BY COUNT(title) DESC " +
                "LIMIT :numberOfSuggestions"
    )
    public suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
        enteredTitle: String,
    ): List<String>

    /**
     * Get a transaction by id.
     * @param id Required transaction id
     * @return Transaction with given [id] or null if not found
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(
        value = "SELECT * FROM transaction_table " +
                "WHERE id = :id"
    )
    public suspend fun getTransactionById(
        id: Int,
    ): TransactionEntity?

    /**
     * Get transactions between timestamps as a list.
     * @param startingTimestamp Start timestamp (inclusive)
     * @param endingTimestamp End timestamp (inclusive)
     * @return List of transactions in the given range
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(
        value = "SELECT * FROM transaction_table " +
                "WHERE transaction_timestamp BETWEEN :startingTimestamp AND :endingTimestamp " +
                "ORDER BY transaction_timestamp DESC"
    )
    public suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<TransactionEntity>

    /**
     * Get transactions between timestamps as a Flow.
     * @param startingTimestamp Start timestamp (inclusive)
     * @param endingTimestamp End timestamp (inclusive)
     * @return Flow emitting the list of transactions in the given range
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(
        value = "SELECT * FROM transaction_table " +
                "WHERE transaction_timestamp BETWEEN :startingTimestamp AND :endingTimestamp " +
                "ORDER BY transaction_timestamp DESC"
    )
    public fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<TransactionEntity>>

    /**
     * Get the count of all transactions.
     * @return Number of transactions in the table
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(value = "SELECT COUNT(*) FROM transaction_table")
    public suspend fun getTransactionsCount(): Int

    /**
     * Insert a transaction into the table.
     * @param transaction Transaction to insert
     * @return Row id for inserted transaction. -1 if a conflict occurred.
     * @throws SQLiteConstraintException if a constraint is violated, such as a unique constraint
     * @throws SQLiteException if there is a general SQLite error
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    public suspend fun insertTransaction(
        transaction: TransactionEntity,
    ): Long

    /**
     * Insert transactions into the table.
     * @param transactions Transactions to insert
     * @return List of row ids for inserted transactions. -1 if a conflict occurred for that item.
     * @throws SQLiteConstraintException if a constraint is violated, such as a unique constraint
     * @throws SQLiteException if there is a general SQLite error
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    public suspend fun insertTransactions(
        vararg transactions: TransactionEntity,
    ): List<Long>

    /**
     * Update a transaction in the table.
     * Only updates the existing row using the primary key
     * @param transaction Transaction to update
     * @return Number of rows updated
     * @throws SQLiteException if there is a general SQLite error
     */
    @Update
    public suspend fun updateTransaction(
        transaction: TransactionEntity,
    ): Int

    /**
     * Update transactions in the table.
     * Only updates the existing rows using the primary key
     * @param transactions Transactions to update
     * @return Number of rows updated
     * @throws SQLiteException if there is a general SQLite error
     */
    @Update
    public suspend fun updateTransactions(
        vararg transactions: TransactionEntity,
    ): Int
}
