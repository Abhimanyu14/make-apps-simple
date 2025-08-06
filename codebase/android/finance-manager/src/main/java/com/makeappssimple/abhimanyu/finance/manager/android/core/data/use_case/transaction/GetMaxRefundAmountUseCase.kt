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

package com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction

import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Amount
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.minus
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.plus

public class GetMaxRefundAmountUseCase(
    private val getTransactionDataUseCase: GetTransactionDataUseCase,
) {
    public suspend operator fun invoke(
        id: Int,
    ): Amount? {
        val originalTransactionData: TransactionData =
            getTransactionDataUseCase(
                id = id,
            ) ?: return null

        val refundTransactionIds =
            originalTransactionData.transaction.refundTransactionIds
        if (refundTransactionIds.isNullOrEmpty()) {
            return originalTransactionData.transaction.amount
        }

        val refundedAmountCalculated: Amount? = calculateRefundedAmount(
            refundTransactionIds = refundTransactionIds,
        )
        return if (refundedAmountCalculated.isNotNull()) {
            originalTransactionData.transaction.amount.minus(
                refundedAmountCalculated
            )
        } else {
            originalTransactionData.transaction.amount
        }
    }

    private suspend fun calculateRefundedAmount(
        refundTransactionIds: List<Int>,
    ): Amount? {
        var refundedAmountCalculated: Amount? = null
        for (refundTransactionId in refundTransactionIds) {
            val refundTransactionData = getTransactionDataUseCase(
                id = refundTransactionId,
            ) ?: continue
            val refundTransactionAmount =
                refundTransactionData.transaction.amount
            refundedAmountCalculated =
                refundedAmountCalculated?.plus(refundTransactionAmount)
                    ?: refundTransactionAmount
        }
        return refundedAmountCalculated
    }
}
