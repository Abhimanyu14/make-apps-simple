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

package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.theme

import androidx.annotation.ColorInt
import androidx.compose.ui.graphics.Color

internal enum class MyColor(
    @ColorInt val color: Int,
    val composeColor: Color = Color(
        color = color,
    ),
) {
    PRIMARY(
        color = Blue900,
    ),
    ON_PRIMARY(
        color = White,
    ),
    PRIMARY_CONTAINER(
        color = Blue50,
    ),
    ON_PRIMARY_CONTAINER(
        color = DarkGray,
    ),
    INVERSE_PRIMARY(
        color = Blue900,
    ),

    SECONDARY(
        color = Brown900,
    ),
    ON_SECONDARY(
        color = White,
    ),
    SECONDARY_CONTAINER(
        color = Brown50,
    ),
    ON_SECONDARY_CONTAINER(
        color = Brown1000,
    ),

    TERTIARY(
        color = Green900,
    ),
    ON_TERTIARY(
        color = White,
    ),
    TERTIARY_CONTAINER(
        color = Green100,
    ),
    ON_TERTIARY_CONTAINER(
        color = Green1000,
    ),

    BACKGROUND(
        color = White,
    ),
    ON_BACKGROUND(
        color = Black,
    ),

    SURFACE(
        color = White,
    ),
    ON_SURFACE(
        color = DarkGray,
    ),

    SURFACE_VARIANT(
        color = LightGray,
    ),
    ON_SURFACE_VARIANT(
        color = DarkGray,
    ),

    INVERSE_SURFACE(
        color = DarkGray,
    ),
    INVERSE_ON_SURFACE(
        color = White,
    ),

    ERROR(
        color = Red,
    ),
    ON_ERROR(
        color = White,
    ),

    ERROR_CONTAINER(
        color = Error90,
    ),
    ON_ERROR_CONTAINER(
        color = Error10,
    ),

    OUTLINE(
        color = LightGray,
    ),
}
