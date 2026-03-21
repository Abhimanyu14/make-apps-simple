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

package com.makeappssimple.abhimanyu.cosmos.design.system.android.theme

import androidx.annotation.ColorInt
import androidx.compose.ui.graphics.Color

public enum class CosmosColor(
    @ColorInt public val color: Int,
    public val composeColor: Color = Color(
        color = color,
    ),
) {
    Primary(
        color = Blue900,
    ),
    OnPrimary(
        color = White,
    ),
    PrimaryContainer(
        color = Blue50,
    ),
    OnPrimaryContainer(
        color = DarkGray,
    ),
    InversePrimary(
        color = Blue900,
    ),

    Secondary(
        color = Brown900,
    ),
    OnSecondary(
        color = White,
    ),
    SecondaryContainer(
        color = Brown50,
    ),
    OnSecondaryContainer(
        color = Brown1000,
    ),

    Tertiary(
        color = Green900,
    ),
    OnTertiary(
        color = White,
    ),
    TertiaryContainer(
        color = Green100,
    ),
    OnTertiaryContainer(
        color = Green1000,
    ),

    Background(
        color = White,
    ),
    OnBackground(
        color = Black,
    ),

    Surface(
        color = White,
    ),
    OnSurface(
        color = DarkGray,
    ),

    SurfaceVariant(
        color = LightGray,
    ),
    OnSurfaceVariant(
        color = DarkGray,
    ),

    InverseSurface(
        color = DarkGray,
    ),
    OnInverseSurface(
        color = White,
    ),

    Error(
        color = Red,
    ),
    OnError(
        color = White,
    ),

    ErrorContainer(
        color = Error90,
    ),
    OnErrorContainer(
        color = Error10,
    ),

    Outline(
        color = LightGray,
    ),
}
