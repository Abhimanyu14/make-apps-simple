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

package com.makeappssimple.abhimanyu.cosmos.design.system.android.text

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.library.cosmos.design.system.android.R

/**
 * @param text The text to display.
 * @param softWrap Whether the text should wrap or not.
 * @param maxLines The maximum number of lines for the text to span, or Int.MAX_VALUE for no limit.
 * @param minLines The minimum number of lines for the text to span, at least 1.
 * @param style The style of the text.
 */
@Composable
public fun CosmosText(
    modifier: Modifier = Modifier,
    text: String,
    softWrap: Boolean = true,
    style: CosmosTextStyle = CosmosTextStyle.Body2,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    textAlign: TextAlign = TextAlign.Unspecified,
) {
    BasicText(
        text = text,
        modifier = modifier,
        style = TextStyle.Default.merge(
            fontFamily = FontFamily(
                Font(
                    resId = R.font.lexend,
                )
            ),
            fontSize = style.fontSize,
            fontWeight = style.fontWeight,
            textAlign = textAlign,
        ),
        overflow = TextOverflow.Ellipsis,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
    )
}

private val CosmosTextStyle.fontSize: TextUnit
    get() = when (this) {
        CosmosTextStyle.Heading1 -> {
            28.sp
        }

        CosmosTextStyle.Heading2 -> {
            26.sp
        }

        CosmosTextStyle.Heading3 -> {
            22.sp
        }

        CosmosTextStyle.Body1 -> {
            16.sp
        }

        CosmosTextStyle.Body2 -> {
            16.sp
        }

        CosmosTextStyle.Body3 -> {
            14.sp
        }

        CosmosTextStyle.Body4 -> {
            14.sp
        }

        CosmosTextStyle.Footnote -> {
            11.sp
        }

        CosmosTextStyle.Caption -> {
            11.sp
        }

        CosmosTextStyle.Small1 -> {
            12.sp
        }

        CosmosTextStyle.Small2 -> {
            12.sp
        }
    }

private val CosmosTextStyle.fontWeight: FontWeight
    get() = when (this) {
        CosmosTextStyle.Heading1 -> {
            FontWeight.SemiBold
        }

        CosmosTextStyle.Heading2 -> {
            FontWeight.SemiBold
        }

        CosmosTextStyle.Heading3 -> {
            FontWeight.SemiBold
        }

        CosmosTextStyle.Body1 -> {
            FontWeight.SemiBold
        }

        CosmosTextStyle.Body2 -> {
            FontWeight.Normal
        }

        CosmosTextStyle.Body3 -> {
            FontWeight.Medium
        }

        CosmosTextStyle.Body4 -> {
            FontWeight.Normal
        }

        CosmosTextStyle.Footnote -> {
            FontWeight.Normal
        }

        CosmosTextStyle.Caption -> {
            FontWeight.Light
        }

        CosmosTextStyle.Small1 -> {
            FontWeight.Medium
        }

        CosmosTextStyle.Small2 -> {
            FontWeight.Normal
        }
    }
