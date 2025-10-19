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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.analysis.analysis.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.rememberTextMeasurer
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.listitem.analysis.AnalysisListItem
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.analysis.analysis.state.AnalysisScreenUIState

@Composable
internal fun AnalysisScreenList(
    uiState: AnalysisScreenUIState,
) {
    val textMeasurer: TextMeasurer = rememberTextMeasurer()
    val maxAmountTextWidth = remember(
        uiState.analysisListItemData
    ) {
        if (uiState.analysisListItemData.isEmpty()) {
            0
        } else {
            uiState.analysisListItemData.maxOf {
                textMeasurer.measure(it.amountText).size.width
            }
        }
    }

    LazyColumn(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .navigationBarLandscapeSpacer(),
    ) {
        items(
            items = uiState.analysisListItemData,
            key = { listItem ->
                listItem.hashCode()
            },
        ) { listItem ->
            AnalysisListItem(
                data = listItem.copy(
                    maxEndTextWidth = maxAmountTextWidth,
                ),
            )
        }
        item {
            NavigationBarsAndImeSpacer()
        }
    }
}
