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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.overview_card

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.chart.pie.PieChart
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.chart.pie.PieChartData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.chart.pie.renderer.SimpleSliceDrawer

private object PieChartConstants {
    const val SLICE_THICKNESS = 30F
}
/*
public val pieChartData = PieChartData(
    items = listOf(
        PieChartItemData(
            value = 10F,
            color = Green700,
        ),
        PieChartItemData(
            value = 10F,
            color = Red,
        ),
    )
)
*/

@Composable
public fun PieChart(
    modifier: Modifier = Modifier,
    pieChartData: PieChartData,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(
                vertical = 8.dp,
            )
    ) {
        PieChart(
            pieChartData = pieChartData,
            sliceDrawer = SimpleSliceDrawer(
                sliceThickness = PieChartConstants.SLICE_THICKNESS,
            ),
        )
    }
}
