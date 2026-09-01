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
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.constants.CurrencyCodeConstants
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Amount
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Currency

@Serializable
internal data class AmountEntity(
    @ColumnInfo(name = "currency")
    @EncodeDefault
    @SerialName(value = "currency")
    val currency: String = CurrencyCodeConstants.INR,

    @ColumnInfo(name = "value")
    @EncodeDefault
    @SerialName(value = "value")
    val value: Long = 0,
)

internal operator fun AmountEntity.plus(
    amount: AmountEntity,
): AmountEntity {
    return AmountEntity(
        currency = currency,
        value = value + amount.value,
    )
}

internal operator fun AmountEntity.minus(
    amount: AmountEntity,
): AmountEntity {
    return AmountEntity(
        currency = currency,
        value = value - amount.value,
    )
}

internal fun AmountEntity.asExternalModel(): Amount {
    return Amount(
        currency = Currency.getInstance(currency),
        value = value,
    )
}
