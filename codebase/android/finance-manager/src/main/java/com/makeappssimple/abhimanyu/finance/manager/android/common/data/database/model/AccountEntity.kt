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

@file:OptIn(ExperimentalSerializationApi::class)

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.AccountType
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "account_table")
@Serializable
internal data class AccountEntity(
    @ColumnInfo(name = "balance_amount")
    @EncodeDefault
    @SerialName(value = "balance_amount")
    val balanceAmount: AmountEntity = AmountEntity(
        value = 0,
    ),

    @ColumnInfo(name = "is_archived", defaultValue = "0")
    @EncodeDefault
    @SerialName(value = "is_archived")
    val isArchived: Boolean = false,

    @ColumnInfo(name = "id")
    @EncodeDefault
    @PrimaryKey(autoGenerate = true)
    @SerialName(value = "id")
    val id: Int = 0,

    @ColumnInfo(name = "type")
    @EncodeDefault
    @SerialName(value = "type")
    val type: AccountType = AccountType.CASH,

    @ColumnInfo(name = "minimum_account_balance_amount")
    @EncodeDefault
    @SerialName(value = "minimum_account_balance_amount")
    val minimumAccountBalanceAmount: AmountEntity? = null,

    @ColumnInfo(name = "name")
    @EncodeDefault
    @SerialName(value = "name")
    val name: String,
)

internal fun AccountEntity.updateBalanceAmount(
    updatedBalanceAmount: Long,
): AccountEntity {
    return this.copy(
        balanceAmount = this.balanceAmount.copy(
            value = updatedBalanceAmount,
        )
    )
}

internal fun AccountEntity.asExternalModel(): Account {
    return Account(
        balanceAmount = balanceAmount.asExternalModel(),
        id = id,
        type = type,
        minimumAccountBalanceAmount = minimumAccountBalanceAmount?.asExternalModel(),
        name = name,
    )
}
