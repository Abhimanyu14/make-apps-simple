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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.action_button.ActionButton
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.action_button.ActionButtonData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.action_button.ActionButtonEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.selection_group.MyHorizontalScrollingRadioGroup
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.selection_group.MyHorizontalScrollingRadioGroupData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.selection_group.MyHorizontalScrollingRadioGroupEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.analysis.analysis.event.AnalysisScreenUIEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.analysis.analysis.state.AnalysisScreenUIState
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Composable
internal fun AnalysisScreenHeader(
    uiState: AnalysisScreenUIState,
    handleUIEvent: (uiEvent: AnalysisScreenUIEvent) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                color = FinanceManagerAppTheme.colorScheme.background,
            ),
    ) {
        MyHorizontalScrollingRadioGroup(
            modifier = Modifier
                .weight(
                    weight = 1F,
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 4.dp,
                ),
            data = MyHorizontalScrollingRadioGroupData(
                horizontalArrangement = Arrangement.Start,
                isLoading = uiState.isLoading,
                items = uiState.transactionTypesChipUIData,
                selectedItemIndex = uiState.selectedTransactionTypeIndex,
            ),
            handleEvent = { event ->
                when (event) {
                    is MyHorizontalScrollingRadioGroupEvent.OnSelectionChange -> {
                        handleUIEvent(
                            AnalysisScreenUIEvent.OnTransactionTypeChange(
                                updatedSelectedTransactionTypeIndex = event.index,
                            )
                        )
                    }
                }
            },
        )
        ActionButton(
            data = ActionButtonData(
                isIndicatorVisible = uiState.selectedFilter.areFiltersSelected(),
                isLoading = uiState.isLoading,
                imageVector = MyIcons.FilterAlt,
                contentDescriptionStringResourceId = R.string.finance_manager_screen_analysis_filter_button_content_description,
            ),
            handleEvent = { event ->
                when (event) {
                    is ActionButtonEvent.OnClick -> {
                        handleUIEvent(AnalysisScreenUIEvent.OnFilterActionButtonClick)
                    }
                }
            },
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                ),
        )
    }
}
