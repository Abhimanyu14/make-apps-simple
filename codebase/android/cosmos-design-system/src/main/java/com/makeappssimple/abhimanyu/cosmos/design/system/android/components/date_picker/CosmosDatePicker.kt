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

@file:OptIn(ExperimentalMaterial3Api::class)

package com.makeappssimple.abhimanyu.cosmos.design.system.android.components.date_picker

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
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button.CosmosTextButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.date_time.MyLocalDate
import com.makeappssimple.abhimanyu.cosmos.design.system.android.extensions.isNotNull
import com.makeappssimple.abhimanyu.cosmos.design.system.android.extensions.orZero
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme
import com.makeappssimple.abhimanyu.library.cosmos.design.system.android.R

@Composable
public fun CosmosDatePicker(
    modifier: Modifier = Modifier,
    data: CosmosDatePickerData,
    handleEvent: (event: CosmosDatePickerEvent) -> Unit = {},
) {
    if (data.isVisible) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = (
                    data.selectedLocalDate
                        ?: data.endLocalDate
                        ?: MyLocalDate.MIN
                    )
                .toStartOfDayEpochMilli(),
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(
                    utcTimeMillis: Long,
                ): Boolean {
                    val localDate = MyLocalDate(
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
                handleEvent(CosmosDatePickerEvent.OnNegativeButtonClick)
            },
            confirmButton = {
                CosmosTextButton(
                    onClick = {
                        val startOfDayTimestamp = MyLocalDate(
                            timestamp = datePickerState.selectedDateMillis.orZero(),
                        )
                        handleEvent(
                            CosmosDatePickerEvent.OnPositiveButtonClick(
                                selectedDate = startOfDayTimestamp,
                            )
                        )
                    },
                    enabled = confirmEnabled.value,
                ) {
                    CosmosText(
                        stringResource = CosmosStringResource.Id(
                            id = R.string.cosmos_date_picker_positive_button,
                        ),
                    )
                }
            },
            dismissButton = {
                CosmosTextButton(
                    onClick = {
                        handleEvent(CosmosDatePickerEvent.OnNegativeButtonClick)
                    },
                ) {
                    CosmosText(
                        stringResource = CosmosStringResource.Id(
                            id = R.string.cosmos_date_picker_negative_button,
                        ),
                    )
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = CosmosAppTheme.colorScheme.background,
            ),
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = CosmosAppTheme.colorScheme.background,
                ),
            )
        }
    }
}
