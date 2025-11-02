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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.common

import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer

/**
 * Format amount input
 *
 * 1 => 1
 * 12 => 12
 * 123 => 123
 * 1234 => 1,234
 * 12345 => 12,345
 * 123456 => 1,23,456
 * 1234567 => 12,34,567
 * 12345678 => 1,23,45,678
 * 123456789 => 12,34,56,789
 * 1234567890 => 1,23,45,67,890
 *
 * TODO(Abhi): Extend for currencies other than INR
 * */
internal class AmountOutputTransformation : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        val formatted = originalText.toString().toIndianCurrencyFormat()
        if (originalText != formatted) {
            replace(
                start = 0,
                end = originalText.length,
                text = formatted,
            )
        }
    }
}

private fun String.toIndianCurrencyFormat(): String {
    return if (length > 3) {
        val regex = "(\\d+?)(?=(\\d\\d)+(\\d)(?!\\d))(\\.\\d+)?"
        replace(
            regex = Regex(
                pattern = regex,
            ),
            replacement = "$1,",
        )
    } else {
        this
    }
}
