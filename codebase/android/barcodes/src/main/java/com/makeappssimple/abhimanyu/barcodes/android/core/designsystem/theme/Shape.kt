package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

internal val Shapes = Shapes(
    extraSmall = RoundedCornerShape(
        size = 4.dp,
    ),
    small = RoundedCornerShape(
        size = 8.dp,
    ),
    medium = RoundedCornerShape(
        size = 12.dp,
    ),
    large = RoundedCornerShape(
        size = 16.dp,
    ),
    extraLarge = RoundedCornerShape(
        size = 32.dp,
    ),
)

internal val BottomSheetExpandedShape = RectangleShape
internal val BottomSheetShape = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 16.dp,
)
internal val ExpandedListItemShape = RoundedCornerShape(
    topStart = 24.dp,
    topEnd = 24.dp,
)
