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

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.bottom_sheet.analysis

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.date_picker.MyDatePicker
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.date_picker.MyDatePickerData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.date_picker.MyDatePickerEvent
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroup
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroupData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroupEvent
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.text_field.MyReadOnlyTextField
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.text_field.MyReadOnlyTextFieldData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.text_field.MyReadOnlyTextFieldEvent
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import java.time.LocalDate

@Composable
public fun AnalysisFilterBottomSheetUI(
    modifier: Modifier = Modifier,
    data: AnalysisFilterBottomSheetUIData,
    onClearButtonClick: () -> Unit,
    onDateRangeOptionClick: (dateRangeOption: DateRangeOptions) -> Unit,
    onFromDateSelected: (LocalDate) -> Unit,
    onFromDateTextFieldClick: () -> Unit,
    onNegativeButtonClick: () -> Unit,
    onPositiveButtonClick: () -> Unit,
    onToDateSelected: (LocalDate) -> Unit,
    onToDateTextFieldClick: () -> Unit,
    setFromDatePickerDialogVisible: (Boolean) -> Unit,
    setToDatePickerDialogVisible: (Boolean) -> Unit,
) {
    MyDatePicker(
        data = MyDatePickerData(
            isVisible = data.isFromDatePickerDialogVisible,
            endLocalDate = data.fromDatePickerEndLocalDate,
            selectedLocalDate = data.fromDatePickerSelectedLocalDate,
            startLocalDate = data.fromDatePickerStartLocalDate,
        ),
        handleEvent = { event ->
            when (event) {
                is MyDatePickerEvent.OnNegativeButtonClick -> {
                    setFromDatePickerDialogVisible(false)
                }

                is MyDatePickerEvent.OnPositiveButtonClick -> {
                    onFromDateSelected(event.selectedDate)
                    setFromDatePickerDialogVisible(false)
                }
            }
        },
    )
    MyDatePicker(
        data = MyDatePickerData(
            isVisible = data.isToDatePickerDialogVisible,
            endLocalDate = data.toDatePickerEndLocalDate,
            selectedLocalDate = data.toDatePickerSelectedLocalDate,
            startLocalDate = data.toDatePickerStartLocalDate,
        ),
        handleEvent = { event ->
            when (event) {
                is MyDatePickerEvent.OnNegativeButtonClick -> {
                    setToDatePickerDialogVisible(false)
                }

                is MyDatePickerEvent.OnPositiveButtonClick -> {
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
            MyText(
                modifier = Modifier
                    .weight(
                        weight = 1F,
                    ),
                textStringResourceId = data.headingTextStringResourceId,
                style = FinanceManagerAppTheme.typography.headlineLarge
                    .copy(
                        color = FinanceManagerAppTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Start,
                    ),
            )
            TextButton(
                onClick = onClearButtonClick,
                modifier = Modifier,
            ) {
                MyText(
                    textStringResourceId = R.string.finance_manager_bottom_sheet_analysis_filter_clear,
                    style = FinanceManagerAppTheme.typography.labelLarge,
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
                data = MyReadOnlyTextFieldData(
                    value = data.fromDateText,
                    labelTextStringResourceId = R.string.finance_manager_bottom_sheet_analysis_filter_from_date,
                ),
                handleEvent = { event ->
                    when (event) {
                        is MyReadOnlyTextFieldEvent.OnClick -> {
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
                data = MyReadOnlyTextFieldData(
                    value = data.toDateText,
                    labelTextStringResourceId = R.string.finance_manager_bottom_sheet_analysis_filter_to_date,
                ),
                handleEvent = { event ->
                    when (event) {
                        is MyReadOnlyTextFieldEvent.OnClick -> {
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
                        text = it.title,
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
                MyText(
                    textStringResourceId = R.string.finance_manager_bottom_sheet_analysis_filter_reset,
                    style = FinanceManagerAppTheme.typography.labelLarge,
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
                MyText(
                    textStringResourceId = R.string.finance_manager_bottom_sheet_analysis_filter_apply,
                    style = FinanceManagerAppTheme.typography.labelLarge,
                )
            }
        }
        NavigationBarsAndImeSpacer()
    }
}
