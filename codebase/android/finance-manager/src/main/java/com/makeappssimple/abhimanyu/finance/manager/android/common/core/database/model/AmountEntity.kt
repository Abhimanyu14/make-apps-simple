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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.database.model

import androidx.room.ColumnInfo
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.common.constants.CurrencyCodeConstants
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.common.util.currency.formattedCurrencyValue
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Amount
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.serializer.CurrencySerializer
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Currency
import kotlin.math.abs

@Serializable
public data class AmountEntity(
    @ColumnInfo(name = "currency")
    @EncodeDefault
    @Serializable(CurrencySerializer::class)
    @SerialName(value = "currency")
    public val currency: Currency = Currency.getInstance(CurrencyCodeConstants.INR),

    @ColumnInfo(name = "value")
    @EncodeDefault
    @SerialName(value = "value")
    public val value: Long = 0,
) {
    override fun toString(): String {
        val formattedValue = formattedCurrencyValue(
            value = abs(
                n = value,
            ),
        )
        return if (value >= 0) {
            "${currency.symbol}$formattedValue"
        } else {
            "- ${currency.symbol}$formattedValue"
        }
    }
}

public fun AmountEntity.toNonSignedString(): String {
    return toSignedString(
        isPositive = false,
        isNegative = false
    )
}

public fun AmountEntity.toSignedString(
    isPositive: Boolean = false,
    isNegative: Boolean = false,
): String {
    val formattedValue = formattedCurrencyValue(
        value = abs(
            n = value,
        ),
    )
    if (isPositive) {
        return "+ ${currency.symbol}$formattedValue"
    }
    if (isNegative) {
        return "- ${currency.symbol}$formattedValue"
    }
    return "${currency.symbol}$formattedValue"
}

public operator fun AmountEntity.plus(
    amount: AmountEntity,
): AmountEntity {
    return AmountEntity(
        currency = currency,
        value = value + amount.value,
    )
}

public operator fun AmountEntity.minus(
    amount: AmountEntity,
): AmountEntity {
    return AmountEntity(
        currency = currency,
        value = value - amount.value,
    )
}

public fun AmountEntity.asExternalModel(): Amount {
    return Amount(
        currency = currency,
        value = value,
    )
}
