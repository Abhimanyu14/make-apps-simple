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

package com.makeappssimple.abhimanyu.finance.manager.android.core.database.transaction_provider

import androidx.room.withTransaction
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.local.database.FinanceManagerRoomDatabase

/**
 * An implementation of [DatabaseTransactionProvider] that uses Room's `withTransaction`.
 */
internal class DatabaseTransactionProviderImpl(
    private val financeManagerRoomDatabase: FinanceManagerRoomDatabase,
) : DatabaseTransactionProvider {
    /**
     * Executes the given [block] of code within a database transaction.
     *
     * If the [block] completes successfully, the transaction is committed.
     * If an exception occurs within the [block],
     * the transaction is rolled back, and the exception is re-thrown.
     *
     * @param R The return type of the [block].
     * @param block The suspendable block of code to be executed transactionally.
     * @return The result of the [block] execution.
     * @throws Exception if the [block] throws an exception.
     */
    override suspend fun <R> runAsTransaction(
        block: suspend () -> R,
    ): R {
        return with(
            receiver = financeManagerRoomDatabase,
        ) {
            try {
                withTransaction {
                    block()
                }
            } catch (
                exception: Exception,
            ) {
                // Re-throw the exception to ensure transaction rollback and propagate the error.
                throw exception
            }
        }
    }
}
