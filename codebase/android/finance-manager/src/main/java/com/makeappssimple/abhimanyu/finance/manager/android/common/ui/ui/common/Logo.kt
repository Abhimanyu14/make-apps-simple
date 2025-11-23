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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.common

/**
 * TODO(Abhi): Remove this hard-coded mapping once logo can be dynamically set
 */
internal fun getLogoUrl(
    name: String,
): String? {
    return when (name.trim()) {
        "Airtel Credit Card" -> {
            "https://makeappssimple.com/hosting/logos/airtel.svg"
        }

        "Amazon Credit Card" -> {
            "https://makeappssimple.com/hosting/logos/amazon.svg"
        }

        "Amazon Pay" -> {
            "https://makeappssimple.com/hosting/logos/amazon_pay.png"
        }

        "Amazon Prime" -> {
            "https://makeappssimple.com/hosting/logos/amazon.svg"
        }

        "Amazon Voucher" -> {
            "https://makeappssimple.com/hosting/logos/amazon.svg"
        }

        "Axis" -> {
            "https://makeappssimple.com/hosting/logos/axis.svg"
        }

        "Axis (Suja)" -> {
            "https://makeappssimple.com/hosting/logos/axis.svg"
        }

        "CUB" -> {
            "https://makeappssimple.com/hosting/logos/cub.png"
        }

        "FTH" -> {
            "https://makeappssimple.com/hosting/logos/fresh_to_home.png"
        }

        "HDFC" -> {
            "https://makeappssimple.com/hosting/logos/hdfc.svg"
        }

        "ICICI" -> {
            "https://makeappssimple.com/hosting/logos/icici.svg"
        }

        "IOB" -> {
            "https://makeappssimple.com/hosting/logos/iob.svg"
        }

        "Paytm" -> {
            "https://makeappssimple.com/hosting/logos/paytm.svg"
        }

        "Paytm UPI Lite" -> {
            "https://makeappssimple.com/hosting/logos/paytm.svg"
        }

        "PhonePe" -> {
            "https://makeappssimple.com/hosting/logos/phonepe.webp"
        }

        "PhonePe UPI Lite" -> {
            "https://makeappssimple.com/hosting/logos/phonepe.webp"
        }

        "RuPay Credit Card" -> {
            "https://makeappssimple.com/hosting/logos/icici.svg"
        }

        "R-Wallet" -> {
            "https://makeappssimple.com/hosting/logos/irctc.svg"
        }

        "Splitwise" -> {
            "https://makeappssimple.com/hosting/logos/splitwise.webp"
        }

        "Zerodha" -> {
            "https://makeappssimple.com/hosting/logos/kite.svg"
        }

        "Zomato" -> {
            "https://makeappssimple.com/hosting/logos/zomato.svg"
        }

        else -> null
    }
}
