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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.account

import com.makeappssimple.abhimanyu.common.core.extensions.toIntOrZero
import com.makeappssimple.abhimanyu.common.core.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.transaction.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Amount
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Transaction
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType
import kotlinx.collections.immutable.ImmutableList
import kotlin.math.abs

public class UpdateAccountUseCase(
    private val dateTimeKit: DateTimeKit,
    private val insertTransactionsUseCase: InsertTransactionsUseCase,
    private val updateAccountsUseCase: UpdateAccountsUseCase,
) {
    public suspend operator fun invoke(
        currentAccount: Account,
        validAccountTypesForNewAccount: ImmutableList<AccountType>,
        selectedAccountTypeIndex: Int,
        balanceAmountValue: String,
        minimumAccountBalanceAmountValue: String,
        name: String,
    ): Boolean {
        val amountChangeValue =
            balanceAmountValue.toIntOrZero() - currentAccount.balanceAmount.value
        val accountType = if (currentAccount.type != AccountType.CASH) {
            validAccountTypesForNewAccount[selectedAccountTypeIndex]
        } else {
            currentAccount.type
        }
        val minimumAccountBalanceAmount = if (accountType == AccountType.BANK) {
            (currentAccount.minimumAccountBalanceAmount ?: Amount(
                value = 0L,
            ))
                .copy(
                    value = minimumAccountBalanceAmountValue.toLongOrZero(),
                )
        } else {
            null
        }
        val updatedAccount = currentAccount
            .copy(
                balanceAmount = currentAccount.balanceAmount
                    .copy(
                        value = balanceAmountValue.toLongOrZero(),
                    ),
                type = accountType,
                minimumAccountBalanceAmount = minimumAccountBalanceAmount,
                name = name.ifBlank {
                    currentAccount.name
                },
            )
        val accountFromId = if (amountChangeValue < 0L) {
            updatedAccount.id
        } else {
            null
        }
        val accountToId = if (amountChangeValue < 0L) {
            null
        } else {
            updatedAccount.id
        }

        if (amountChangeValue != 0L) {
            insertTransactionsUseCase(
                Transaction(
                    amount = Amount(
                        value = abs(
                            n = amountChangeValue,
                        ),
                    ),
                    categoryId = null,
                    accountFromId = accountFromId,
                    accountToId = accountToId,
                    description = "",
                    title = TransactionType.ADJUSTMENT.title,
                    creationTimestamp = dateTimeKit.getCurrentTimeMillis(),
                    transactionTimestamp = dateTimeKit.getCurrentTimeMillis(),
                    transactionType = TransactionType.ADJUSTMENT,
                ),
            )
        }
        updateAccountsUseCase(updatedAccount)
        return true
    }
}
