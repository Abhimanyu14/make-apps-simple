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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.chart.compose_pie.legend

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
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.spacer.CosmosHorizontalSpacer
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.spacer.CosmosNonFillingVerticalSpacer
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.chart.compose_pie.data.PieChartLegendItemData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.theme.composeColor

@Composable
internal fun PieChartLegend(
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
                    CosmosHorizontalSpacer(
                        width = 8.dp,
                    )
                    CosmosText(
                        stringResource = item.stringResource,
                        style = FinanceManagerAppTheme.typography.headlineLarge
                            .copy(
                                color = FinanceManagerAppTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center,
                            ),
                    )
                }

                if (index != items.lastIndex) {
                    CosmosNonFillingVerticalSpacer(
                        height = 4.dp,
                    )
                }
            }
        }
    }
}
