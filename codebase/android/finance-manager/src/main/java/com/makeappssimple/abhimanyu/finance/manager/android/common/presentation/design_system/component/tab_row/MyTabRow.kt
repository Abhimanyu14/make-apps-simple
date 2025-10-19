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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.tab_row

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.theme.FinanceManagerAppTheme
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun MyTabRow(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    tabDataList: ImmutableList<MyTabData>,
    updateSelectedTabIndex: (updatedSelectedTabIndex: Int) -> Unit,
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = FinanceManagerAppTheme.colorScheme.background,
        contentColor = FinanceManagerAppTheme.colorScheme.primary,
    ) {
        tabDataList.mapIndexed { index, tabData ->
            val isSelected = selectedTabIndex == index
            Tab(
                text = {
                    MyTabText(
                        title = tabData.title,
                        isSelected = isSelected,
                    )
                },
                selected = isSelected,
                onClick = {
                    updateSelectedTabIndex(index)
                },
                selectedContentColor = FinanceManagerAppTheme.colorScheme.primary,
                unselectedContentColor = FinanceManagerAppTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
private fun MyTabText(
    title: String,
    isSelected: Boolean,
) {
    MyText(
        text = title,
        style = FinanceManagerAppTheme.typography.headlineLarge
            .copy(
                color = if (isSelected) {
                    FinanceManagerAppTheme.colorScheme.primary
                } else {
                    FinanceManagerAppTheme.colorScheme.onBackground
                },
            ),
    )
}
