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
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionDataEntity
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
     */
    @Query(
        value = "SELECT EXISTS(SELECT * FROM transaction_table " +
                "WHERE account_from_id = :accountId OR account_to_id = :accountId)"
    )
    public suspend fun checkIfAccountIsUsedInTransactions(
        accountId: Int,
    ): Boolean

    /**
     * Check if a category is used in any transactions.
     * @param categoryId ID of the category to check
     * @return true if the category is used in any transactions, false otherwise
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
     */
    @Query(value = "DELETE FROM transaction_table")
    public suspend fun deleteAllTransactions(): Int

    /**
     * Delete a transaction by id.
     * @param id Required transaction id
     * @return Number of rows deleted (0 or 1)
     */
    @Query(
        value = "DELETE FROM transaction_table " +
                "WHERE id = :id"
    )
    public suspend fun deleteTransaction(
        id: Int,
    ): Int

    /**
     * Get all transaction data as a list, ordered by timestamp descending.
     * @return List of all transaction data
     */
    @Query(
        value = "SELECT * FROM transaction_table " +
                "ORDER BY transaction_timestamp DESC"
    )
    public suspend fun getAllTransactionData(): List<TransactionDataEntity>

    /**
     * Get all transaction data as a Flow, ordered by timestamp descending.
     * @return Flow emitting the list of all transaction data
     */
    @Query(
        value = "SELECT * FROM transaction_table " +
                "ORDER BY transaction_timestamp DESC"
    )
    public fun getAllTransactionDataFlow(): Flow<List<TransactionDataEntity>>

    /**
     * Get all transactions as a list, ordered by timestamp descending.
     * @return List of all transactions
     */
    @Query(
        value = "SELECT * from transaction_table " +
                "ORDER BY transaction_timestamp DESC"
    )
    public suspend fun getAllTransactions(): List<TransactionEntity>

    /**
     * Get all transactions as a Flow, ordered by timestamp descending.
     * @return Flow emitting the list of all transactions
     */
    @Query(
        value = "SELECT * from transaction_table " +
                "ORDER BY transaction_timestamp DESC"
    )
    public fun getAllTransactionsFlow(): Flow<List<TransactionEntity>>

    /**
     * Get recent transaction data as a Flow.
     * @param numberOfTransactions Number of recent transactions to retrieve
     * @return Flow emitting the specified number of most recent transactions
     */
    @Query(
        value = "SELECT * FROM transaction_table " +
                "ORDER BY transaction_timestamp DESC " +
                "LIMIT :numberOfTransactions"
    )
    public fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<List<TransactionDataEntity>>

    /**
     * Get searched transaction data.
     * TODO(Abhi): To search amount properly, JSON1 extension is required which is not available in Android.
     * For more info - https://stackoverflow.com/a/65104396/9636037
     *
     * The current code is a hacky solution, which does a simple text search of the JSON string.
     */
    @Query(
        value = "SELECT * FROM transaction_table " +
                "WHERE instr(lower(title), lower(:searchText)) > 0 OR instr(lower(amount), lower(:searchText)) > 0 " +
                "ORDER BY transaction_timestamp DESC"
    )
    public suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionDataEntity>

    /**
     * Get title suggestions for a category based on existing transactions.
     * @param categoryId ID of the category
     * @param numberOfSuggestions Maximum number of suggestions to return
     * @param enteredTitle Partial title to match against
     * @return List of suggested titles, ordered by frequency of use
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
     */
    @Query(
        value = "SELECT * FROM transaction_table " +
                "WHERE id = :id"
    )
    public suspend fun getTransaction(
        id: Int,
    ): TransactionEntity?

    /**
     * Get transaction data by id.
     * @param id Required transaction id
     * @return Transaction data with given [id] or null if not found
     */
    @Query(
        value = "SELECT * FROM transaction_table " +
                "WHERE id = :id"
    )
    public suspend fun getTransactionData(
        id: Int,
    ): TransactionDataEntity?

    /**
     * Get transactions between two timestamps as a list.
     * @param startingTimestamp Start timestamp (inclusive)
     * @param endingTimestamp End timestamp (inclusive)
     * @return List of transactions between the given timestamps
     */
    @Query(
        value = "SELECT * from transaction_table " +
                "WHERE transaction_timestamp BETWEEN :startingTimestamp AND :endingTimestamp " +
                "ORDER BY transaction_timestamp DESC"
    )
    public suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<TransactionEntity>

    /**
     * Get transactions between two timestamps as a Flow.
     * @param startingTimestamp Start timestamp (inclusive)
     * @param endingTimestamp End timestamp (inclusive)
     * @return Flow emitting transactions between the given timestamps
     */
    @Query(
        value = "SELECT * from transaction_table " +
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
     */
    @Query(value = "SELECT COUNT(*) FROM transaction_table")
    public suspend fun getTransactionsCount(): Int

    /**
     * Insert transactions into the table.
     * @param transactions Transactions to insert
     * @return List of row ids for inserted transactions. -1 if a conflict occurred for that item.
     */
    // TODO(Abhi): Handle conflicts with error handling properly
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public suspend fun insertTransactions(
        vararg transactions: TransactionEntity,
    ): List<Long>

    /**
     * Insert a single transaction into the table.
     * @param transaction Transaction to insert
     * @return Row id of inserted transaction. -1 if a conflict occurred.
     */
    // TODO(Abhi): Handle conflicts with error handling properly
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public suspend fun insertTransaction(
        transaction: TransactionEntity,
    ): Long

    /**
     * Update a single transaction in the table.
     * Only updates if the transaction exists using the primary key.
     * @param transaction Transaction to update
     * @return Number of rows updated (0 or 1)
     */
    @Update
    public suspend fun updateTransaction(
        transaction: TransactionEntity,
    ): Int

    /**
     * Update multiple transactions in the table.
     * Only updates the existing rows using the primary key.
     * @param transactions Transactions to update
     * @return Number of rows updated
     */
    @Update
    public suspend fun updateTransactions(
        vararg transactions: TransactionEntity,
    ): Int
}
