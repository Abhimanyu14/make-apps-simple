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

import android.database.sqlite.SQLiteException
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.AccountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.TransactionDataEntity
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for TransactionDataEntity combining multiple tables.
 */
@Dao
internal interface TransactionDataDao {
    /**
     * Get a flow of all accounts that are used in transactions.
     *
     * @return A flow emitting a list of account entities.
     * @throws SQLiteException if there is a general SQLite error.
     */
    @Query(
        value = """
            SELECT *
            FROM account_table
            WHERE id IN (
                SELECT DISTINCT account_from_id
                FROM transaction_table
                WHERE account_from_id IS NOT NULL
                
                UNION
                
                SELECT DISTINCT account_to_id
                FROM transaction_table
                WHERE account_to_id IS NOT NULL
            )
        """
    )
    fun getAccountsInTransactionsFlow(): Flow<List<AccountEntity>>

    /**
     * Get all transaction data as a list, ordered by timestamp descending.
     * @return List of all transaction data
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(
        value = """
            SELECT *
            FROM transaction_table
            ORDER BY transaction_timestamp DESC
        """
    )
    @Transaction
    suspend fun getAllTransactionData(): List<TransactionDataEntity>

    /**
     * Get all transaction data as a Flow, ordered by timestamp descending.
     * @return Flow emitting the list of all transaction data
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(
        value = """
            SELECT *
            FROM transaction_table
            WHERE
            (
                NOT :areAccountFiltersSelected
                OR
                account_from_id IN (:selectedAccountIds)
                OR
                account_to_id IN (:selectedAccountIds)
            )
            AND
            (
                lower(title) LIKE '%' || lower(:searchText) || '%' 
                OR lower(CAST(amount AS TEXT)) LIKE '%' || lower(:searchText) || '%'
            )
            AND
            (
                NOT :areTransactionForFiltersSelected
                OR
                transaction_for_id IN (:selectedTransactionForValueIds)
            )
            AND
            (
                NOT :areTransactionTypeFiltersSelected
                OR
                transaction_type IN (:selectedTransactionTypes)
            )
            AND
            (
                NOT :areCategoryFiltersSelected
                OR
                category_id IN (:selectedCategoryIds)
            )
            ORDER BY transaction_timestamp DESC
        """
    )
    @Transaction
    fun getAllTransactionDataFlow(
        areAccountFiltersSelected: Boolean = false,
        areCategoryFiltersSelected: Boolean = false,
        areTransactionForFiltersSelected: Boolean = false,
        areTransactionTypeFiltersSelected: Boolean = false,
        selectedAccountIds: List<Int> = emptyList(),
        selectedCategoryIds: List<Int> = emptyList(),
        selectedTransactionForValueIds: List<Int> = emptyList(),
        selectedTransactionTypes: List<TransactionType> = emptyList(),
        searchText: String = "",
    ): Flow<List<TransactionDataEntity>>

    /**
     * Get a flow of all categories that are used in transactions.
     *
     * @return A flow emitting a list of category entities.
     * @throws SQLiteException if there is a general SQLite error.
     */
    @Query(
        value = """
            SELECT DISTINCT category_from_all_categories.*
            FROM category_table category_from_all_categories
            INNER JOIN transaction_table transaction_from_all_transactions
            ON category_from_all_categories.id = transaction_from_all_transactions.category_id
        """
    )
    fun getCategoriesInTransactionsFlow(): Flow<List<CategoryEntity>>

    /**
     * Get the timestamp of the oldest transaction.
     *
     * @return The timestamp of the oldest transaction, or null if there are no transactions.
     * @throws SQLiteException if there is a general SQLite error.
     */
    @Query(
        value = """
            SELECT MIN(transaction_timestamp)
            FROM transaction_table
        """
    )
    fun getOldestTransactionTimestampFlow(): Flow<Long?>

    /**
     * Get recent transaction data as a Flow.
     * @param numberOfTransactions Number of recent transactions to retrieve
     * @return Flow emitting the specified number of most recent transactions
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(
        value = """
            SELECT *
            FROM transaction_table
            ORDER BY transaction_timestamp DESC
            LIMIT :numberOfTransactions
        """
    )
    @Transaction
    fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<List<TransactionDataEntity>>

    /**
     * Get searched transaction data.
     * TODO(Abhi): To search amount properly, JSON1 extension is required which is not available in Android.
     * For more info - https://stackoverflow.com/a/65104396/9636037
     *
     * The current code is a hacky solution, which does a simple text search of the JSON string.
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(
        value = """
            SELECT *
            FROM transaction_table
            WHERE instr(lower(title), lower(:searchText)) > 0
                OR instr(lower(amount), lower(:searchText)) > 0
            ORDER BY transaction_timestamp DESC
        """
    )
    @Transaction
    suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionDataEntity>

    /**
     * Get transaction data by id.
     * @param id Required transaction id
     * @return Transaction data with given [id] or null if not found
     * @throws SQLiteException if there is a general SQLite error
     */
    @Query(
        value = """
            SELECT *
            FROM transaction_table
            WHERE id = :id
        """
    )
    @Transaction
    suspend fun getTransactionDataById(
        id: Int,
    ): TransactionDataEntity?
}
