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

package com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.library.cosmos.design.system.android.R

private val cosmosFontFamily: FontFamily = FontFamily(
    Font(
        resId = R.font.lexend,
    )
)
public val Typography: Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = cosmosFontFamily,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.6.sp,
        lineHeight = 40.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = cosmosFontFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.sp,
        lineHeight = 32.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = cosmosFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.sp,
        lineHeight = 24.sp,
    ),

    headlineLarge = TextStyle(
        fontFamily = cosmosFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.sp,
        lineHeight = 24.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = cosmosFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.sp,
        lineHeight = 22.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = cosmosFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.sp,
        lineHeight = 20.sp,
    ),

    /**
     * Usage - CenterAlignedTopAppBar
     */
    titleLarge = TextStyle(
        fontFamily = cosmosFontFamily,
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = cosmosFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.2.sp,
        lineHeight = 22.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = cosmosFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.1.sp,
        lineHeight = 20.sp,
    ),

    /**
     * Usage - OutlinedTextField
     */
    bodyLarge = TextStyle(
        fontFamily = cosmosFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.5.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = cosmosFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.2.sp,
        lineHeight = 22.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = cosmosFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.4.sp,
        lineHeight = 20.sp,
    ),

    labelLarge = TextStyle(
        fontFamily = cosmosFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp,
        lineHeight = 22.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = cosmosFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.5.sp,
        lineHeight = 22.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = cosmosFontFamily,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.2.sp,
        lineHeight = 18.sp,
    ),
)
