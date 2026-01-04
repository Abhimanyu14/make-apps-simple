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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.analysis

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button.CosmosTextButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.chip.ChipUIData
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.date_picker.CosmosDatePicker
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.date_picker.CosmosDatePickerData
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.date_picker.CosmosDatePickerEvent
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.spacer.CosmosNavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text_field.CosmosReadOnlyTextFieldData
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text_field.CosmosReadOnlyTextFieldEvent
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text_field.MyReadOnlyTextField
import com.makeappssimple.abhimanyu.cosmos.design.system.android.date_time.MyLocalDate
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.selection_group.MyHorizontalScrollingSelectionGroup
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.selection_group.MyHorizontalScrollingSelectionGroupData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.selection_group.MyHorizontalScrollingSelectionGroupEvent
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Composable
internal fun AnalysisFilterBottomSheetUI(
    modifier: Modifier = Modifier,
    data: AnalysisFilterBottomSheetUIData,
    onClearButtonClick: () -> Unit,
    onDateRangeOptionClick: (dateRangeOption: DateRangeOptions) -> Unit,
    onFromDateSelected: (MyLocalDate) -> Unit,
    onFromDateTextFieldClick: () -> Unit,
    onNegativeButtonClick: () -> Unit,
    onPositiveButtonClick: () -> Unit,
    onToDateSelected: (MyLocalDate) -> Unit,
    onToDateTextFieldClick: () -> Unit,
    setFromDatePickerDialogVisible: (Boolean) -> Unit,
    setToDatePickerDialogVisible: (Boolean) -> Unit,
) {
    CosmosDatePicker(
        data = CosmosDatePickerData(
            isVisible = data.isFromDatePickerDialogVisible,
            endLocalDate = data.fromDatePickerEndLocalDate,
            selectedLocalDate = data.fromDatePickerSelectedLocalDate,
            startLocalDate = data.fromDatePickerStartLocalDate,
        ),
        handleEvent = { event ->
            when (event) {
                is CosmosDatePickerEvent.OnNegativeButtonClick -> {
                    setFromDatePickerDialogVisible(false)
                }

                is CosmosDatePickerEvent.OnPositiveButtonClick -> {
                    onFromDateSelected(event.selectedDate)
                    setFromDatePickerDialogVisible(false)
                }
            }
        },
    )
    CosmosDatePicker(
        data = CosmosDatePickerData(
            isVisible = data.isToDatePickerDialogVisible,
            endLocalDate = data.toDatePickerEndLocalDate,
            selectedLocalDate = data.toDatePickerSelectedLocalDate,
            startLocalDate = data.toDatePickerStartLocalDate,
        ),
        handleEvent = { event ->
            when (event) {
                is CosmosDatePickerEvent.OnNegativeButtonClick -> {
                    setToDatePickerDialogVisible(false)
                }

                is CosmosDatePickerEvent.OnPositiveButtonClick -> {
                    onToDateSelected(event.selectedDate)
                    setToDatePickerDialogVisible(false)
                }
            }
        },
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 16.dp,
                )
                .fillMaxWidth(),
        ) {
            CosmosText(
                modifier = Modifier
                    .weight(
                        weight = 1F,
                    ),
                stringResource = CosmosStringResource.Id(
                    id = data.headingTextStringResourceId,
                ),
                style = CosmosAppTheme.typography.headlineLarge
                    .copy(
                        color = CosmosAppTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Start,
                    ),
            )
            CosmosTextButton(
                onClick = onClearButtonClick,
            ) {
                CosmosText(
                    stringResource = CosmosStringResource.Id(
                        id = R.string.finance_manager_bottom_sheet_analysis_filter_clear,
                    ),
                    style = CosmosAppTheme.typography.labelLarge,
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 4.dp,
                    bottom = 16.dp,
                ),
        ) {
            MyReadOnlyTextField(
                modifier = Modifier
                    .weight(
                        weight = 1F,
                    )
                    .padding(
                        horizontal = 8.dp,
                    ),
                data = CosmosReadOnlyTextFieldData(
                    value = data.fromDateText,
                    labelTextStringResourceId = R.string.finance_manager_bottom_sheet_analysis_filter_from_date,
                ),
                handleEvent = { event ->
                    when (event) {
                        is CosmosReadOnlyTextFieldEvent.OnClick -> {
                            onFromDateTextFieldClick()
                        }
                    }
                },
            )
            MyReadOnlyTextField(
                modifier = Modifier
                    .weight(
                        weight = 1F,
                    )
                    .padding(
                        horizontal = 8.dp,
                    ),
                data = CosmosReadOnlyTextFieldData(
                    value = data.toDateText,
                    labelTextStringResourceId = R.string.finance_manager_bottom_sheet_analysis_filter_to_date,
                ),
                handleEvent = { event ->
                    when (event) {
                        is CosmosReadOnlyTextFieldEvent.OnClick -> {
                            onToDateTextFieldClick()
                        }
                    }
                },
            )
        }
        MyHorizontalScrollingSelectionGroup(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 4.dp,
                ),
            data = MyHorizontalScrollingSelectionGroupData(
                items = DateRangeOptions.entries.map {
                    ChipUIData(
                        stringResource = CosmosStringResource.Text(
                            text = it.title,
                        ),
                    )
                },
            ),
            handleEvent = { event ->
                when (event) {
                    is MyHorizontalScrollingSelectionGroupEvent.OnSelectionChange -> {
                        onDateRangeOptionClick(DateRangeOptions.entries[event.index])
                    }
                }
            },
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(
                modifier = Modifier
                    .padding(
                        all = 16.dp,
                    )
                    .weight(
                        weight = 1F,
                    ),
                onClick = onNegativeButtonClick,
            ) {
                CosmosText(
                    stringResource = CosmosStringResource.Id(
                        id = R.string.finance_manager_bottom_sheet_analysis_filter_reset,
                    ),
                    style = CosmosAppTheme.typography.labelLarge,
                )
            }
            Button(
                modifier = Modifier
                    .padding(
                        all = 16.dp,
                    )
                    .weight(
                        weight = 1F,
                    ),
                onClick = onPositiveButtonClick,
            ) {
                CosmosText(
                    stringResource = CosmosStringResource.Id(
                        id = R.string.finance_manager_bottom_sheet_analysis_filter_apply,
                    ),
                    style = CosmosAppTheme.typography.labelLarge,
                )
            }
        }
        CosmosNavigationBarsAndImeSpacer()
    }
}
