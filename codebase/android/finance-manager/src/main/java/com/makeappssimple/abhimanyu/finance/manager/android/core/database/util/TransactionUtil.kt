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

package com.makeappssimple.abhimanyu.finance.manager.android.core.database.util

import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import kotlinx.collections.immutable.ImmutableList
import kotlin.math.abs

public fun sanitizeTransactions(
    transactions: ImmutableList<TransactionEntity>,
): ImmutableList<TransactionEntity> {
    return transactions.map { transaction ->
        when (transaction.transactionType) {
            TransactionType.INCOME -> {
                transaction.copy(
                    accountFromId = null,
                )
            }

            TransactionType.EXPENSE -> {
                transaction.copy(
                    accountToId = null,
                    amount = transaction.amount.copy(
                        value = abs(transaction.amount.value),
                    ),
                )
            }

            TransactionType.TRANSFER -> {
                transaction.copy(
                    categoryId = null,
                )
            }

            TransactionType.ADJUSTMENT -> {
                val accountId = transaction.accountToId
                transaction.copy(
                    categoryId = null,
                    accountFromId = if (transaction.amount.value < 0) {
                        accountId
                    } else {
                        transaction.accountFromId
                    },
                    accountToId = if (transaction.amount.value < 0) {
                        null
                    } else {
                        transaction.accountToId
                    },
                    amount = transaction.amount.copy(
                        value = abs(transaction.amount.value),
                    ),
                )
            }

            TransactionType.INVESTMENT -> {
                transaction.copy(
                    accountToId = null,
                )
            }

            TransactionType.REFUND -> {
                transaction.copy(
                    accountFromId = null,
                )
            }
        }
    }
}
