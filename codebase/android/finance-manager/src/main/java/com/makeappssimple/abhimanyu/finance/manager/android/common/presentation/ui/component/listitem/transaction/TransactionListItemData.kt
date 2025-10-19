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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.transaction

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.constants.EmojiConstants
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.theme.MyColor
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.model.toDefaultString
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.extensions.getAmountTextColor

@Immutable
internal data class TransactionListItemData(
    val isDeleteButtonEnabled: Boolean = false,
    val isDeleteButtonVisible: Boolean = false,
    val isEditButtonVisible: Boolean = false,
    val isExpanded: Boolean = false,
    val isInSelectionMode: Boolean = false,
    val isLoading: Boolean = false,
    val isRefundButtonVisible: Boolean = false,
    val isSelected: Boolean = false,
    val transactionId: Int = 0,
    val amountColor: MyColor = MyColor.ON_BACKGROUND,
    val amountText: String = "",
    val dateAndTimeText: String = "",
    val emoji: String = "",
    val accountFromName: String? = null,
    val accountToName: String? = null,
    val title: String = "",
    val transactionForText: String = "",
)

internal fun TransactionData.toTransactionListItemData(
    getReadableDateAndTime: (
        timestamp: Long
    ) -> String,
): TransactionListItemData {
    val amountText: String = when (transaction.transactionType) {
        TransactionType.INCOME,
        TransactionType.EXPENSE,
        TransactionType.ADJUSTMENT,
        TransactionType.REFUND,
            -> {
            transaction.amount.toSignedString(
                isPositive = accountTo.isNotNull(),
                isNegative = accountFrom.isNotNull(),
            )
        }

        else -> {
            transaction.amount.toDefaultString()
        }
    }
    val dateAndTimeText: String = getReadableDateAndTime(
        transaction.transactionTimestamp,
    )
    val emoji: String = when (transaction.transactionType) {
        TransactionType.TRANSFER -> {
            EmojiConstants.LEFT_RIGHT_ARROW
        }

        TransactionType.ADJUSTMENT -> {
            EmojiConstants.EXPRESSIONLESS_FACE
        }

        else -> {
            category?.emoji.orEmpty()
        }
    }
    return TransactionListItemData(
        transactionId = transaction.id,
        amountColor = transaction.getAmountTextColor(),
        amountText = amountText,
        dateAndTimeText = dateAndTimeText,
        emoji = emoji,
        accountFromName = accountFrom?.name,
        accountToName = accountTo?.name,
        title = transaction.title,
        transactionForText = transactionFor.title,
    )
}
