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
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.AccountType
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO(Abhi): Clean up deprecated class
@Deprecated(
    message = "Maintained only for migration",
    replaceWith = ReplaceWith(
        expression = "AccountEntity",
    ),
)
@Entity(tableName = "source_table")
@Serializable
public data class SourceEntity(
    @EncodeDefault
    @ColumnInfo(name = "balance_amount")
    @SerialName(value = "balance_amount")
    val balanceAmount: AmountEntity = AmountEntity(
        value = 0,
    ),

    @EncodeDefault
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @EncodeDefault
    val type: AccountType = AccountType.CASH,

    val name: String,
)
