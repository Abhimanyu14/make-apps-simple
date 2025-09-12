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

@file:OptIn(ExperimentalMaterial3Api::class)

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.date_picker

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.common.core.extensions.orMin
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.constants.FinanceManagerAppConstants
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.date_time.getLocalDate
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.date_time.getTimestamp
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.component.button.MyTextButton
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import java.time.LocalDate
import java.time.ZoneId

@Composable
public fun MyDatePicker(
    modifier: Modifier = Modifier,
    data: MyDatePickerData,
    handleEvent: (event: MyDatePickerEvent) -> Unit = {},
) {
    if (data.isVisible) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = getTimestamp(
                localDate = data.selectedLocalDate ?: data.endLocalDate.orMin(),
                zoneId = ZoneId.of(FinanceManagerAppConstants.ZONE_ID_GMT),
            ),
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(
                    utcTimeMillis: Long,
                ): Boolean {
                    val localDate: LocalDate = getLocalDate(
                        timestamp = utcTimeMillis,
                    )
                    return if (data.startLocalDate.isNotNull() && data.endLocalDate.isNotNull()) {
                        localDate >= data.startLocalDate && localDate <= data.endLocalDate
                    } else if (data.startLocalDate.isNotNull()) {
                        localDate >= data.startLocalDate
                    } else if (data.endLocalDate.isNotNull()) {
                        localDate <= data.endLocalDate
                    } else {
                        super.isSelectableDate(utcTimeMillis)
                    }
                }

                override fun isSelectableYear(
                    year: Int,
                ): Boolean {
                    return if (data.startLocalDate.isNotNull() && data.endLocalDate.isNotNull()) {
                        year >= data.startLocalDate.year && year <= data.endLocalDate.year
                    } else if (data.startLocalDate.isNotNull()) {
                        year >= data.startLocalDate.year
                    } else {
                        super.isSelectableYear(year)
                    }
                }
            }
        )
        val confirmEnabled = remember {
            derivedStateOf {
                datePickerState.selectedDateMillis.isNotNull()
            }
        }
        DatePickerDialog(
            modifier = modifier,
            onDismissRequest = {
                handleEvent(MyDatePickerEvent.OnNegativeButtonClick)
            },
            confirmButton = {
                MyTextButton(
                    onClick = {
                        val startOfDayTimestamp: LocalDate = getLocalDate(
                            timestamp = datePickerState.selectedDateMillis.orZero(),
                        )
                        handleEvent(
                            MyDatePickerEvent.OnPositiveButtonClick(
                                selectedDate = startOfDayTimestamp,
                            )
                        )
                    },
                    enabled = confirmEnabled.value,
                ) {
                    MyText(
                        textStringResourceId = R.string.finance_manager_date_picker_positive_button,
                    )
                }
            },
            dismissButton = {
                MyTextButton(
                    onClick = {
                        handleEvent(MyDatePickerEvent.OnNegativeButtonClick)
                    },
                ) {
                    MyText(
                        textStringResourceId = R.string.finance_manager_date_picker_negative_button,
                    )
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = FinanceManagerAppTheme.colorScheme.background,
            ),
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = FinanceManagerAppTheme.colorScheme.background,
                ),
            )
        }
    }
}
