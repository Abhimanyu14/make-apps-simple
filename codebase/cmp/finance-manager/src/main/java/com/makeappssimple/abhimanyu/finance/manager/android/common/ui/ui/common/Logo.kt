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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.common

private const val HOSTING_URL = "https://abhimanyu14.github.io/hosting"

/**
 * TODO(Abhi): Remove this hard-coded mapping once logo can be dynamically set
 */
internal fun getLogoUrl(
    name: String,
): String? {
    val logoUrl = mapOf(
        "Airtel Credit Card" to "${HOSTING_URL}/finance_manager/airtel.svg",
        "Amazon Credit Card" to "${HOSTING_URL}/finance_manager/amazon.svg",
        "Amazon Pay" to "${HOSTING_URL}/finance_manager/amazon_pay.png",
        "Amazon Prime" to "${HOSTING_URL}/finance_manager/amazon.svg",
        "Amazon Voucher" to "${HOSTING_URL}/finance_manager/amazon.svg",
        "Axis" to "${HOSTING_URL}/finance_manager/axis.svg",
        "Axis (Suja)" to "${HOSTING_URL}/finance_manager/axis.svg",
        "Big Basket" to "${HOSTING_URL}/finance_manager/big_basket.webp",
        "CUB" to "${HOSTING_URL}/finance_manager/cub.png",
        "FTH" to "${HOSTING_URL}/finance_manager/fresh_to_home.png",
        "HDFC" to "${HOSTING_URL}/finance_manager/hdfc.svg",
        "ICICI" to "${HOSTING_URL}/finance_manager/icici.svg",
        "IOB" to "${HOSTING_URL}/finance_manager/iob.svg",
        "Kiwi Credit Card" to "${HOSTING_URL}/finance_manager/kiwi_credit_card.webp",
        "Paytm" to "${HOSTING_URL}/finance_manager/paytm.svg",
        "Paytm UPI Lite" to "${HOSTING_URL}/finance_manager/paytm.svg",
        "PhonePe" to "${HOSTING_URL}/finance_manager/phonepe.webp",
        "PhonePe UPI Lite" to "${HOSTING_URL}/finance_manager/phonepe.webp",
        "RuPay Credit Card" to "${HOSTING_URL}/finance_manager/icici.svg",
        "R-Wallet" to "${HOSTING_URL}/finance_manager/irctc.svg",
        "Splitwise" to "${HOSTING_URL}/finance_manager/splitwise.webp",
        "Swiggy" to "${HOSTING_URL}/finance_manager/swiggy.webp",
        "Uber" to "${HOSTING_URL}/finance_manager/uber.webp",
        "Zerodha" to "${HOSTING_URL}/finance_manager/kite.svg",
        "Zomato" to "${HOSTING_URL}/finance_manager/zomato.svg",
    )
    return logoUrl.entries
        .firstOrNull {
            it.key.equals(
                name.trim(),
                ignoreCase = true
            )
        }
        ?.value
}
