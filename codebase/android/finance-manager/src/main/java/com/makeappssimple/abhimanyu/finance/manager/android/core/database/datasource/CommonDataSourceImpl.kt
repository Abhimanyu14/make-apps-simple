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

package com.makeappssimple.abhimanyu.finance.manager.android.core.database.datasource

import androidx.room.withTransaction
import com.makeappssimple.abhimanyu.common.core.extensions.orEmpty
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.local.database.FinanceManagerRoomDatabase
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionDataEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.updateBalanceAmount
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType

public class CommonDataSourceImpl(
    private val financeManagerRoomDatabase: FinanceManagerRoomDatabase,
) : CommonDataSource {
    override suspend fun deleteTransactionById(
        id: Int,
    ): Boolean {
        return with(
            receiver = financeManagerRoomDatabase,
        ) {
            try {
                withTransaction {
                    val transactionData: TransactionDataEntity? =
                        transactionDataDao()
                            .getTransactionDataById(
                                id = id,
                            )
                    requireNotNull(
                        value = transactionData,
                    ) {
                        "Transaction data with ID $id not found."
                    }

                    val updatedAccounts: MutableList<AccountEntity> =
                        mutableListOf()
                    transactionData.accountFrom?.let { transactionAccountFrom ->
                        updatedAccounts.add(
                            transactionAccountFrom.updateBalanceAmount(
                                updatedBalanceAmount = transactionAccountFrom.balanceAmount.value + transactionData.transaction.amount.value,
                            )
                        )
                    }
                    transactionData.accountTo?.let { transactionAccountTo ->
                        updatedAccounts.add(
                            transactionAccountTo.updateBalanceAmount(
                                updatedBalanceAmount = transactionAccountTo.balanceAmount.value - transactionData.transaction.amount.value,
                            )
                        )
                    }

                    if (transactionData.transaction.transactionType == TransactionType.REFUND) {
                        val originalTransactionId =
                            transactionData.transaction.originalTransactionId
                        requireNotNull(
                            value = originalTransactionId,
                        ) {
                            "Original transaction ID must not be null for refund transactions."
                        }
                        val originalTransaction =
                            transactionDao().getTransactionById(
                                id = originalTransactionId,
                            )
                        requireNotNull(
                            value = originalTransaction,
                        ) {
                            "Original transaction with ID $originalTransactionId not found."
                        }
                        val originalTransactionRefundTransactionIds =
                            originalTransaction.refundTransactionIds?.toMutableList()
                        requireNotNull(
                            value = originalTransactionRefundTransactionIds,
                        ) {
                            "Original transaction refund transaction IDs must not be null."
                        }
                        originalTransactionRefundTransactionIds.remove(
                            element = id,
                        )
                        val updatedRefundTransactionIds =
                            if (originalTransactionRefundTransactionIds.isEmpty()) {
                                null
                            } else {
                                originalTransactionRefundTransactionIds
                            }
                        transactionDao().updateTransaction(
                            transaction = originalTransaction.copy(
                                refundTransactionIds = updatedRefundTransactionIds,
                            ),
                        )
                    }
                    transactionDao().deleteTransactionById(
                        id = id,
                    )
                    accountDao().updateAccounts(
                        accounts = updatedAccounts.toTypedArray(),
                    )
                }
                true
            } catch (
                _: Exception,
            ) {
                false
            }
        }
    }

    override suspend fun insertTransaction(
        accountFrom: AccountEntity?,
        accountTo: AccountEntity?,
        transaction: TransactionEntity,
        originalTransaction: TransactionEntity?,
    ): Long {
        return with(
            receiver = financeManagerRoomDatabase,
        ) {
            withTransaction {
                val insertedTransactionId = transactionDao().insertTransaction(
                    transaction = transaction,
                )
                val isTransactionInserted = insertedTransactionId != -1L
                if (isTransactionInserted) {
                    val updatedAccounts = mutableListOf<AccountEntity>()
                    accountFrom?.let { accountFromValue ->
                        updatedAccounts.add(
                            element = accountFromValue.updateBalanceAmount(
                                updatedBalanceAmount = accountFromValue.balanceAmount.value - transaction.amount.value,
                            ),
                        )
                    }
                    accountTo?.let { accountToValue ->
                        updatedAccounts.add(
                            element = accountToValue.updateBalanceAmount(
                                updatedBalanceAmount = accountToValue.balanceAmount.value + transaction.amount.value,
                            ),
                        )
                    }
                    accountDao().updateAccounts(
                        accounts = updatedAccounts.toTypedArray(),
                    )

                    if (originalTransaction != null) {
                        val refundTransactionIds = originalTransaction
                            .refundTransactionIds
                            .orEmpty()
                            .toMutableList()
                        refundTransactionIds.add(insertedTransactionId.toInt())
                        transactionDao().updateTransaction(
                            transaction = originalTransaction
                                .copy(
                                    refundTransactionIds = refundTransactionIds,
                                ),
                        )
                    }
                } else {
                    throw IllegalStateException("Failed to insert transaction.")
                }
                insertedTransactionId
            }
        }
    }

    override suspend fun restoreData(
        categories: Array<CategoryEntity>,
        accounts: Array<AccountEntity>,
        transactions: Array<TransactionEntity>,
        transactionForValues: Array<TransactionForEntity>,
    ): Boolean {
        return with(
            receiver = financeManagerRoomDatabase,
        ) {
            withTransaction {
                categoryDao().deleteAllCategories()
                accountDao().deleteAllAccounts()
                transactionDao().deleteAllTransactions()
                transactionForDao().deleteAllTransactionForValues()

                categoryDao().insertCategories(
                    categories = categories,
                )
                accountDao().insertAccounts(
                    accounts = accounts,
                )
                transactionDao().insertTransactions(
                    transactions = transactions,
                )
                transactionForDao().insertTransactionForValues(
                    transactionForValues = transactionForValues,
                )
            }
            true
        }
    }
}
