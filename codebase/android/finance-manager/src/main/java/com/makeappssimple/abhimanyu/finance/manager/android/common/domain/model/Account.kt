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

package com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Account(
    @EncodeDefault
    @SerialName(value = "balance_amount")
    val balanceAmount: Amount = Amount(
        value = 0,
    ),

    @EncodeDefault
    val id: Int = 0,

    @EncodeDefault
    val type: AccountType = AccountType.CASH,

    @EncodeDefault
    @SerialName(value = "minimum_account_balance_amount")
    val minimumAccountBalanceAmount: Amount? = null,

    val name: String,
)

internal fun Account.updateBalanceAmount(
    updatedBalanceAmount: Long,
): Account {
    return this
        .copy(
            balanceAmount = this.balanceAmount
                .copy(
                    value = updatedBalanceAmount,
                )
        )
}
