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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.extensions.fading_edge

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.common.core.extensions.isNotZero

private object FadingEdgeConstants {
    const val DEFAULT_PERCENTAGE = 0.1F
    val defaultStart = 8.dp
    val defaultEnd = 8.dp
}

internal fun Modifier.fadingEdge(
    start: Dp = FadingEdgeConstants.defaultStart,
    end: Dp = FadingEdgeConstants.defaultEnd,
): Modifier {
    return composed {
        val density = LocalDensity.current
        this
            .graphicsLayer(
                compositingStrategy = CompositingStrategy.Offscreen,
            )
            .drawWithContent {
                drawContent()
                val widthInDp = with(
                    receiver = density,
                ) {
                    size.width.toDp()
                }
                val startPercentage = if (size.width.isNotZero()) {
                    start / widthInDp
                } else {
                    FadingEdgeConstants.DEFAULT_PERCENTAGE
                }
                val endPercentage = if (size.width.isNotZero()) {
                    end / widthInDp
                } else {
                    FadingEdgeConstants.DEFAULT_PERCENTAGE
                }
                val brush = Brush.horizontalGradient(
                    0F to Color.Transparent,
                    startPercentage to Color.Black,
                    (1F - endPercentage) to Color.Black,
                    1F to Color.Transparent
                )
                drawRect(
                    brush = brush,
                    blendMode = BlendMode.DstIn,
                )
            }
    }
}
