/*
 * Copyright 2025-2026 Abhimanyu
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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.extensions

import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosColor
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Transaction
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType

internal fun Transaction.getAmountTextColor(): CosmosColor {
    return this.transactionType.getAmountTextColor(
        isBalanceReduced = this.accountFromId.isNotNull(),
    )
}

private fun TransactionType.getAmountTextColor(
    isBalanceReduced: Boolean,
): CosmosColor {
    return when (this) {
        TransactionType.INCOME -> {
            CosmosColor.ON_TERTIARY_CONTAINER
        }

        TransactionType.EXPENSE -> {
            CosmosColor.ERROR
        }

        TransactionType.TRANSFER -> {
            CosmosColor.ON_BACKGROUND
        }

        TransactionType.ADJUSTMENT -> {
            if (isBalanceReduced) {
                CosmosColor.ERROR
            } else {
                CosmosColor.ON_TERTIARY_CONTAINER
            }
        }

        TransactionType.INVESTMENT -> {
            CosmosColor.PRIMARY
        }

        TransactionType.REFUND -> {
            CosmosColor.ON_TERTIARY_CONTAINER
        }
    }
}
