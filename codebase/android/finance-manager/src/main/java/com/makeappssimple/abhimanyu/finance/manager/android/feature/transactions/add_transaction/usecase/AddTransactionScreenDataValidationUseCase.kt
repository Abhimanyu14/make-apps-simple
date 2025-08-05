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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.usecase

import com.makeappssimple.abhimanyu.common.core.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.common.core.extensions.isNotZero
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
import com.makeappssimple.abhimanyu.common.core.extensions.toIntOrZero
import com.makeappssimple.abhimanyu.common.core.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Amount
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenDataValidationState
import javax.inject.Inject

public class AddTransactionScreenDataValidationUseCase() {
    public operator fun invoke(
        accountFrom: Account?,
        accountTo: Account?,
        maxRefundAmount: Amount?,
        amount: String,
        title: String,
        selectedTransactionType: TransactionType?,
    ): AddTransactionScreenDataValidationState {
        val state = AddTransactionScreenDataValidationState()
        if (selectedTransactionType == null) {
            return state
        }
        var amountErrorText: String? = null
        val isCtaButtonEnabled = when (selectedTransactionType) {
            TransactionType.INCOME -> {
                title.isNotNullOrBlank() && amount.toIntOrZero().isNotZero()
            }

            TransactionType.EXPENSE -> {
                title.isNotNullOrBlank() && amount.toIntOrZero().isNotZero()
            }

            TransactionType.TRANSFER -> {
                accountFrom?.id != accountTo?.id && amount.toIntOrZero()
                    .isNotZero()
            }

            TransactionType.ADJUSTMENT -> {
                false
            }

            TransactionType.INVESTMENT -> {
                title.isNotNullOrBlank() && amount.toIntOrZero().isNotZero()
            }

            TransactionType.REFUND -> {
                val maxRefundAmountValue = maxRefundAmount?.value.orZero()
                val enteredAmountValue = amount.toLongOrZero()
                if (enteredAmountValue > maxRefundAmountValue) {
                    amountErrorText = maxRefundAmount?.toString()
                    false
                } else {
                    amount.toIntOrZero().isNotZero()
                }
            }
        }

        return state
            .copy(
                isCtaButtonEnabled = isCtaButtonEnabled,
                amountErrorText = amountErrorText,
            )
    }
}
