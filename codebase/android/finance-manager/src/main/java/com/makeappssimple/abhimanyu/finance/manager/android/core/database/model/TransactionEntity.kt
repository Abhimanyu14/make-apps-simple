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

package com.makeappssimple.abhimanyu.finance.manager.android.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.converters.IntListConverter
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "transaction_table")
@Serializable
public data class TransactionEntity(
    val amount: AmountEntity,

    @ColumnInfo(name = "category_id")
    @SerialName(value = "category_id")
    val categoryId: Int? = null,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "original_transaction_id")
    @SerialName(value = "original_transaction_id")
    val originalTransactionId: Int? = null,

    @ColumnInfo(name = "account_from_id")
    @SerialName(value = "account_from_id")
    val accountFromId: Int? = null,

    @ColumnInfo(name = "account_to_id")
    @SerialName(value = "account_to_id")
    val accountToId: Int? = null,

    @ColumnInfo(name = "transaction_for_id")
    @SerialName(value = "transaction_for_id")
    val transactionForId: Int = 1,

    @ColumnInfo(name = "refund_transaction_ids")
    @SerialName(value = "refund_transaction_ids")
    @TypeConverters(IntListConverter::class)
    val refundTransactionIds: List<Int>? = null,

    @ColumnInfo(name = "creation_timestamp")
    @SerialName(value = "creation_timestamp")
    val creationTimestamp: Long,

    @ColumnInfo(name = "transaction_timestamp")
    @SerialName(value = "transaction_timestamp")
    val transactionTimestamp: Long,

    val description: String = "",

    val title: String,

    @ColumnInfo(name = "transaction_type")
    @SerialName(value = "transaction_type")
    val transactionType: TransactionType = TransactionType.EXPENSE,
)

public fun TransactionEntity.asExternalModel(): Transaction {
    return Transaction(
        amount = amount.asExternalModel(),
        categoryId = categoryId,
        id = id,
        originalTransactionId = originalTransactionId,
        accountFromId = accountFromId,
        accountToId = accountToId,
        transactionForId = transactionForId,
        refundTransactionIds = refundTransactionIds,
        creationTimestamp = creationTimestamp,
        transactionTimestamp = transactionTimestamp,
        description = description,
        title = title,
        transactionType = transactionType,
    )
}
