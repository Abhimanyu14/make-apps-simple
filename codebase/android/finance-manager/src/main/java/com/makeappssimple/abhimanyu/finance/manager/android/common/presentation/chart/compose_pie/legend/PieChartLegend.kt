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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.chart.compose_pie.legend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.HorizontalSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.NonFillingVerticalSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.theme.composeColor
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.chart.compose_pie.data.PieChartLegendItemData

@Composable
public fun PieChartLegend(
    modifier: Modifier = Modifier,
    items: List<PieChartLegendItemData>,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        items.fastForEachIndexed { index, item ->
            key("${index}_${item.hashCode()}") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Dot(
                        color = item.color.composeColor,
                    )
                    HorizontalSpacer(
                        width = 8.dp,
                    )
                    MyText(
                        text = item.text,
                        style = FinanceManagerAppTheme.typography.headlineLarge
                            .copy(
                                color = FinanceManagerAppTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center,
                            ),
                    )
                }

                if (index != items.lastIndex) {
                    NonFillingVerticalSpacer(
                        height = 4.dp,
                    )
                }
            }
        }
    }
}
