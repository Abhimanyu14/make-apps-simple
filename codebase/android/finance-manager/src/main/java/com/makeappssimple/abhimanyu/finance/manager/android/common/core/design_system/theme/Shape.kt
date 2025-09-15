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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

internal val Shapes = Shapes(
    extraSmall = RoundedCornerShape(
        size = 4.dp,
    ),
    small = RoundedCornerShape(
        size = 8.dp,
    ),
    medium = RoundedCornerShape(
        size = 16.dp,
    ),
    large = RoundedCornerShape(
        size = 24.dp,
    ),
    extraLarge = RoundedCornerShape(
        size = 32.dp,
    ),
)

public val BottomSheetExpandedShape: Shape = RectangleShape
public val BottomSheetShape: RoundedCornerShape = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 16.dp,
)
public val ExpandedListItemShape: RoundedCornerShape = RoundedCornerShape(
    topStart = 24.dp,
    topEnd = 24.dp,
)
