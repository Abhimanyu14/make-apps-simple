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

@file:OptIn(ExperimentalSerializationApi::class)

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.converters.IntListConverter
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Transaction
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "transaction_table")
@Serializable
internal data class TransactionEntity(
    @ColumnInfo(name = "amount")
    @EncodeDefault
    @SerialName(value = "amount")
    val amount: AmountEntity,

    @ColumnInfo(name = "category_id")
    @EncodeDefault
    @SerialName(value = "category_id")
    val categoryId: Int? = null,

    @ColumnInfo(name = "id")
    @EncodeDefault
    @PrimaryKey(autoGenerate = true)
    @SerialName(value = "id")
    val id: Int = 0,

    @ColumnInfo(name = "original_transaction_id")
    @EncodeDefault
    @SerialName(value = "original_transaction_id")
    val originalTransactionId: Int? = null,

    @ColumnInfo(name = "account_from_id")
    @EncodeDefault
    @SerialName(value = "account_from_id")
    val accountFromId: Int? = null,

    @ColumnInfo(name = "account_to_id")
    @EncodeDefault
    @SerialName(value = "account_to_id")
    val accountToId: Int? = null,

    @ColumnInfo(name = "transaction_for_id")
    @EncodeDefault
    @SerialName(value = "transaction_for_id")
    val transactionForId: Int = 1,

    @ColumnInfo(name = "refund_transaction_ids")
    @EncodeDefault
    @SerialName(value = "refund_transaction_ids")
    @TypeConverters(IntListConverter::class)
    val refundTransactionIds: List<Int>? = null,

    @ColumnInfo(name = "creation_timestamp")
    @EncodeDefault
    @SerialName(value = "creation_timestamp")
    val creationTimestamp: Long,

    @ColumnInfo(name = "transaction_timestamp")
    @EncodeDefault
    @SerialName(value = "transaction_timestamp")
    val transactionTimestamp: Long,

    @ColumnInfo(name = "description")
    @EncodeDefault
    @SerialName(value = "description")
    val description: String = "",

    @ColumnInfo(name = "title")
    @EncodeDefault
    @SerialName(value = "title")
    val title: String,

    @ColumnInfo(name = "transaction_type")
    @EncodeDefault
    @SerialName(value = "transaction_type")
    val transactionType: TransactionType = TransactionType.EXPENSE,
)

internal fun TransactionEntity.asExternalModel(): Transaction {
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
