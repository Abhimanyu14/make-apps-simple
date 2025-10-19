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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.chart.pie.renderer

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.chart.pie.PieChartItemData

internal class SimpleSliceDrawer(private val sliceThickness: Float = 25f) :
    SliceDrawer {
    init {
        require(sliceThickness in 10F..100F) {
            "Thickness of $sliceThickness must be between 10-100"
        }
    }

    private val sectionPaint = Paint().apply {
        isAntiAlias = true
        style = PaintingStyle.Stroke
    }

    override fun drawSlice(
        drawScope: DrawScope,
        canvas: Canvas,
        area: Size,
        startAngle: Float,
        sweepAngle: Float,
        slice: PieChartItemData,
    ) {
        val sliceThickness = calculateSectorThickness(area = area)
        val drawableArea = calculateDrawableArea(area = area)

        canvas.drawArc(
            rect = drawableArea,
            paint = sectionPaint.apply {
                color = slice.color
                strokeWidth = sliceThickness
            },
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false
        )
    }

    private fun calculateSectorThickness(area: Size): Float {
        val minSize = minOf(area.width, area.height)

        return minSize * (sliceThickness / 200F)
    }

    private fun calculateDrawableArea(area: Size): Rect {
        val sliceThicknessOffset =
            calculateSectorThickness(area = area) / 2f
        val offsetHorizontally = (area.width - area.height) / 2f

        return Rect(
            left = sliceThicknessOffset + offsetHorizontally,
            top = sliceThicknessOffset,
            right = area.width - sliceThicknessOffset - offsetHorizontally,
            bottom = area.height - sliceThicknessOffset
        )
    }
}
