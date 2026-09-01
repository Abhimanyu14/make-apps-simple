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

/**
 * TODO(Abhi): Remove this hard-coded mapping once logo can be dynamically set
 */
internal fun getLogoUrl(
    name: String,
): String? {
    val logoUrl = mapOf(
        "Airtel Credit Card" to "https://abhimanyu14.github.io/hosting/finance_manager/airtel.svg",
        "Amazon Credit Card" to "https://abhimanyu14.github.io/hosting/finance_manager/amazon.svg",
        "Amazon Pay" to "https://abhimanyu14.github.io/hosting/finance_manager/amazon_pay.png",
        "Amazon Prime" to "https://abhimanyu14.github.io/hosting/finance_manager/amazon.svg",
        "Amazon Voucher" to "https://abhimanyu14.github.io/hosting/finance_manager/amazon.svg",
        "Axis" to "https://abhimanyu14.github.io/hosting/finance_manager/axis.svg",
        "Axis (Suja)" to "https://abhimanyu14.github.io/hosting/finance_manager/axis.svg",
        "CUB" to "https://abhimanyu14.github.io/hosting/finance_manager/cub.png",
        "FTH" to "https://abhimanyu14.github.io/hosting/finance_manager/fresh_to_home.png",
        "HDFC" to "https://abhimanyu14.github.io/hosting/finance_manager/hdfc.svg",
        "ICICI" to "https://abhimanyu14.github.io/hosting/finance_manager/icici.svg",
        "IOB" to "https://abhimanyu14.github.io/hosting/finance_manager/iob.svg",
        "Paytm" to "https://abhimanyu14.github.io/hosting/finance_manager/paytm.svg",
        "Paytm UPI Lite" to "https://abhimanyu14.github.io/hosting/finance_manager/paytm.svg",
        "PhonePe" to "https://abhimanyu14.github.io/hosting/finance_manager/phonepe.webp",
        "PhonePe UPI Lite" to "https://abhimanyu14.github.io/hosting/finance_manager/phonepe.webp",
        "RuPay Credit Card" to "https://abhimanyu14.github.io/hosting/finance_manager/icici.svg",
        "R-Wallet" to "https://abhimanyu14.github.io/hosting/finance_manager/irctc.svg",
        "Splitwise" to "https://abhimanyu14.github.io/hosting/finance_manager/splitwise.webp",
        "Zerodha" to "https://abhimanyu14.github.io/hosting/finance_manager/kite.svg",
        "Zomato" to "https://abhimanyu14.github.io/hosting/finance_manager/zomato.svg",
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
