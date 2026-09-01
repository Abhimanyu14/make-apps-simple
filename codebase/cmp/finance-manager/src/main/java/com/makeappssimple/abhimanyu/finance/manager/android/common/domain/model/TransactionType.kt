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

package com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model

import kotlinx.serialization.SerialName

internal enum class TransactionType(
    val title: String,
) {
    @SerialName(value = "INCOME")
    Income(
        title = "Income",
    ),

    @SerialName(value = "EXPENSE")
    Expense(
        title = "Expense",
    ),

    @SerialName(value = "TRANSFER")
    Transfer(
        title = "Transfer",
    ),

    @SerialName(value = "ADJUSTMENT")
    Adjustment(
        title = "Adjustment",
    ),

    @SerialName(value = "INVESTMENT")
    Investment(
        title = "Investment",
    ),

    @SerialName(value = "REFUND")
    Refund(
        title = "Refund",
    ),
    /*
    // TODO(Abhi): Add Loans later
    LOAN(
        title = "Loan",
    ),
    REPAYMENT(
        title = "Repayment",
    ),
    */
}
