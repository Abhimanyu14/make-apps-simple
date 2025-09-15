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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.chart.compose_pie

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.chart.compose_pie.data.PieChartData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.chart.compose_pie.legend.PieChartLegend

/**
 * Source - https://github.com/humawork/compose-charts
 */
@Composable
public fun ComposePieChart(
    data: PieChartData,
) {
    val fractions = remember(
        key1 = data,
    ) {
        data.calculateFractions()
    }
    val legendEntries = remember(
        key1 = data,
    ) {
        data.createLegendEntries()
    }
    val chartSize: Dp = 100.dp
    val chartSizePx = with(
        receiver = LocalDensity.current,
    ) {
        chartSize.toPx()
    }
    val sliceWidthPx = with(
        receiver = LocalDensity.current,
    ) {
        16.dp.toPx()
    }
    val sliceSpacingPx = with(
        receiver = LocalDensity.current,
    ) {
        2.dp.toPx()
    }
    val entryColors = data.items.map {
        it.color
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
    ) {
        PieChartRenderer(
            modifier = Modifier
                .requiredSize(
                    size = chartSize,
                ),
            chartSizePx = chartSizePx,
            sliceWidthPx = sliceWidthPx,
            sliceSpacingPx = sliceSpacingPx,
            fractions = fractions,
            entryColors = entryColors,
            animate = true,
        )
        PieChartLegend(
            items = legendEntries,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                ),
        )
    }
}
