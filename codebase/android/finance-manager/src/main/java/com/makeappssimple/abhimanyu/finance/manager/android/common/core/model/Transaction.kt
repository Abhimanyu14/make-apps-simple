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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Transaction(
    val amount: Amount,

    @SerialName(value = "category_id")
    val categoryId: Int? = null,

    val id: Int = 0,

    // The transaction id of the transaction that was refunded.
    @SerialName(value = "original_transaction_id")
    val originalTransactionId: Int? = null,

    @SerialName(value = "account_from_id")
    val accountFromId: Int? = null,

    @SerialName(value = "account_to_id")
    val accountToId: Int? = null,

    @SerialName(value = "transaction_for_id")
    val transactionForId: Int = 1,

    @SerialName(value = "refund_transaction_ids")
    val refundTransactionIds: List<Int>? = null,

    @SerialName(value = "creation_timestamp")
    val creationTimestamp: Long,

    @SerialName(value = "transaction_timestamp")
    val transactionTimestamp: Long,

    val description: String = "",

    val title: String,

    @SerialName(value = "transaction_type")
    val transactionType: TransactionType = TransactionType.EXPENSE,
)
