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

package com.makeappssimple.abhimanyu.cosmos.design.system.android.components.chart.compose_pie

import androidx.compose.ui.geometry.Offset
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.chart.compose_pie.data.PieChartData
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.chart.compose_pie.data.PieChartLegendItemData
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

private const val DEG2RAD = Math.PI / 180.0
internal const val FDEG2RAD = Math.PI.toFloat() / 180F
internal val FLOAT_EPSILON = Float.fromBits(1)

internal fun PieChartData.createLegendEntries(): List<PieChartLegendItemData> {
    return items.map { item ->
        PieChartLegendItemData(
            stringResource = item.stringResource,
            color = item.color,
        )
    }
}

internal fun PieChartData.calculateFractions(): List<Float> {
    val total = items
        .sumOf {
            it.value.toDouble()
        }.toFloat()
    if (total == 0F) {
        return items
            .map {
                0F
            }
    }
    val fractions = items
        .map {
            (it.value / total) * 360F
        }
    return fractions
}

internal fun calculateMinimumRadiusForSpacedSlice(
    center: Offset,
    radius: Float,
    angle: Float,
    arcStartPointX: Float,
    arcStartPointY: Float,
    startAngle: Float,
    sweepAngle: Float,
): Float {
    val angleMiddle = startAngle + sweepAngle / 2F

    // Other point of the arc
    val arcEndPointX: Float =
        center.x + radius * cos((startAngle + sweepAngle) * FDEG2RAD)
    val arcEndPointY: Float =
        center.y + radius * sin((startAngle + sweepAngle) * FDEG2RAD)

    // Middle point on the arc
    val arcMidPointX: Float = center.x + radius * cos(angleMiddle * FDEG2RAD)
    val arcMidPointY: Float = center.y + radius * sin(angleMiddle * FDEG2RAD)

    // This is the base of the contained triangle
    val basePointsDistance = sqrt(
        (arcEndPointX - arcStartPointX).toDouble().pow(2.0) +
                (arcEndPointY - arcStartPointY).toDouble().pow(2.0)
    )

    // After reducing space from both sides of the "slice",
    //   the angle of the contained triangle should stay the same.
    // So let's find out the height of that triangle.
    val containedTriangleHeight =
        (basePointsDistance / 2.0 * tan((180.0 - angle) / 2.0 * DEG2RAD)).toFloat()

    // Now we subtract that from the radius
    var spacedRadius = radius - containedTriangleHeight

    // And now subtract the height of the arc that's between the triangle and the outer circle
    spacedRadius -= sqrt(
        (arcMidPointX - (arcEndPointX + arcStartPointX) / 2F).toDouble()
            .pow(2.0) +
                (arcMidPointY - (arcEndPointY + arcStartPointY) / 2F).toDouble()
                    .pow(2.0)
    ).toFloat()

    return spacedRadius
}
