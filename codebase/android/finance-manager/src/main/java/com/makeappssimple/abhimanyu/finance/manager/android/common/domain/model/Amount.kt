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

package com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model

import com.makeappssimple.abhimanyu.common.core.extensions.isNull
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.constants.CurrencyCodeConstants
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.serializer.CurrencySerializer
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.currency.formattedCurrencyValue
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import java.util.Currency
import kotlin.math.abs

@Serializable
public data class Amount(
    @EncodeDefault
    @Serializable(CurrencySerializer::class)
    public val currency: Currency = Currency.getInstance(CurrencyCodeConstants.INR),

    @EncodeDefault
    public val value: Long = 0,
) : Comparable<Amount> {
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

    override fun compareTo(
        other: Amount,
    ): Int {
        return (value - other.value).toInt()
    }
}

public fun Amount.toNonSignedString(): String {
    return toSignedString(
        isPositive = false,
        isNegative = false
    )
}

public fun Amount.toSignedString(
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

public operator fun Amount.plus(
    amount: Amount,
): Amount {
    return Amount(
        currency = currency,
        value = value + amount.value,
    )
}

public operator fun Amount.minus(
    amount: Amount,
): Amount {
    return Amount(
        currency = currency,
        value = value - amount.value,
    )
}

public fun Amount?.orEmpty(): Amount {
    return if (this.isNull()) {
        Amount()
    } else {
        this
    }
}
