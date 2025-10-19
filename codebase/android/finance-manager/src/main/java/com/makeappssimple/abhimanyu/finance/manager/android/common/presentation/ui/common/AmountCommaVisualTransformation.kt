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

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.makeappssimple.abhimanyu.common.core.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.currency.formattedCurrencyValue

/**
 * Format long to comma separated string.
 *
 * Example: 1234567899 => 123,456,789,9
 * */
public class AmountCommaVisualTransformation : VisualTransformation {
    override fun filter(
        text: AnnotatedString,
    ): TransformedText {
        return TransformedText(
            text = AnnotatedString(
                formattedCurrencyValue(
                    value = text.text.toLongOrZero(),
                ),
            ),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(
                    offset: Int,
                ): Int {
                    return formattedCurrencyValue(
                        value = text.text.toLongOrZero(),
                    ).length
                }

                override fun transformedToOriginal(
                    offset: Int,
                ): Int {
                    return text.length
                }
            }
        )
    }
}
