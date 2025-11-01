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

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction

import com.makeappssimple.abhimanyu.common.core.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.MyLocalDate
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.MyLocalTime
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Amount
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Transaction
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType

internal class InsertTransactionUseCase(
    private val dateTimeKit: DateTimeKit,
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    private val transactionRepository: TransactionRepository,
) {
    suspend operator fun invoke(
        selectedAccountFrom: Account?,
        selectedAccountTo: Account?,
        selectedCategoryId: Int?,
        selectedTransactionForId: Int,
        selectedTransactionDate: MyLocalDate,
        selectedTransactionTime: MyLocalTime,
        enteredAmountValue: Long,
        enteredTitle: String,
        selectedTransactionType: TransactionType,
        originalTransaction: Transaction?,
    ): Boolean {
        val amount = Amount(
            value = enteredAmountValue,
        )
        val categoryId = getCategoryId(
            selectedTransactionType = selectedTransactionType,
            selectedCategoryId = selectedCategoryId,
        )
        val accountFromId = getAccountFromId(
            selectedTransactionType = selectedTransactionType,
            selectedAccountFrom = selectedAccountFrom,
        )
        val accountToId = getAccountToId(
            selectedTransactionType = selectedTransactionType,
            selectedAccountTo = selectedAccountTo,
        )
        val title = getTitle(
            selectedTransactionType = selectedTransactionType,
            enteredTitle = enteredTitle,
        )
        val transactionTimestamp = dateTimeKit.getTimestamp(
            date = selectedTransactionDate,
            time = selectedTransactionTime,
        )
        val transactionForId: Int = getTransactionForId(
            selectedTransactionType = selectedTransactionType,
            selectedTransactionForId = selectedTransactionForId,
        )
        val accountFrom = getAccount(
            accountId = accountFromId,
            selectedAccount = selectedAccountFrom,
        )
        val accountTo = getAccount(
            accountId = accountToId,
            selectedAccount = selectedAccountTo,
        )
        val transaction = Transaction(
            amount = amount,
            categoryId = categoryId,
            originalTransactionId = originalTransaction?.id,
            accountFromId = accountFromId,
            accountToId = accountToId,
            title = title,
            creationTimestamp = dateTimeKit.getCurrentTimeMillis(),
            transactionTimestamp = transactionTimestamp,
            transactionForId = transactionForId,
            transactionType = selectedTransactionType,
        )
        val id = transactionRepository.insertTransaction(
            accountFrom = accountFrom,
            accountTo = accountTo,
            transaction = transaction,
            originalTransaction = originalTransaction,
        )
        val isTransactionInserted = id != -1L
        if (isTransactionInserted) {
            financeManagerPreferencesRepository.updateLastDataChangeTimestamp()
        }
        return isTransactionInserted
    }

    private fun getCategoryId(
        selectedTransactionType: TransactionType,
        selectedCategoryId: Int?,
    ): Int? {
        return when (selectedTransactionType) {
            TransactionType.INCOME -> {
                selectedCategoryId
            }

            TransactionType.EXPENSE -> {
                selectedCategoryId
            }

            TransactionType.TRANSFER -> {
                null
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                selectedCategoryId
            }

            TransactionType.REFUND -> {
                selectedCategoryId
            }
        }
    }

    private fun getAccountFromId(
        selectedTransactionType: TransactionType,
        selectedAccountFrom: Account?,
    ): Int? {
        return when (selectedTransactionType) {
            TransactionType.INCOME -> {
                null
            }

            TransactionType.EXPENSE -> {
                selectedAccountFrom?.id
            }

            TransactionType.TRANSFER -> {
                selectedAccountFrom?.id
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                selectedAccountFrom?.id
            }

            TransactionType.REFUND -> {
                null
            }
        }
    }

    private fun getAccountToId(
        selectedTransactionType: TransactionType,
        selectedAccountTo: Account?,
    ): Int? {
        return when (selectedTransactionType) {
            TransactionType.INCOME -> {
                selectedAccountTo?.id
            }

            TransactionType.EXPENSE -> {
                null
            }

            TransactionType.TRANSFER -> {
                selectedAccountTo?.id
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                null
            }

            TransactionType.REFUND -> {
                selectedAccountTo?.id
            }
        }
    }

    private fun getTitle(
        selectedTransactionType: TransactionType,
        enteredTitle: String,
    ): String {
        return when (selectedTransactionType) {
            TransactionType.TRANSFER -> {
                TransactionType.TRANSFER.title
            }

            TransactionType.REFUND -> {
                TransactionType.REFUND.title
            }

            else -> {
                enteredTitle.capitalizeWords()
            }
        }
    }

    private fun getTransactionForId(
        selectedTransactionType: TransactionType,
        selectedTransactionForId: Int,
    ): Int {
        return when (selectedTransactionType) {
            TransactionType.INCOME -> {
                1
            }

            TransactionType.EXPENSE -> {
                selectedTransactionForId
            }

            TransactionType.TRANSFER -> {
                1
            }

            TransactionType.ADJUSTMENT -> {
                1
            }

            TransactionType.INVESTMENT -> {
                1
            }

            TransactionType.REFUND -> {
                1
            }
        }
    }

    private fun getAccount(
        accountId: Int?,
        selectedAccount: Account?,
    ): Account? {
        return if (accountId.isNotNull()) {
            selectedAccount
        } else {
            null
        }
    }
}
