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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.button.MyIconButton
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.button.MyTextButton
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.theme.myDarkColorScheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.theme.myLightColorScheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.typealiases.ComposableContent
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import java.time.LocalTime

private object MyTimePickerConstants {
    const val MIN_SCREEN_HEIGHT_REQUIRED_FOR_TIMEPICKER = 400
}

@Immutable
internal data class MyTimePickerData(
    val isVisible: Boolean = false,
    val selectedLocalDate: LocalTime? = null,
)

@Immutable
internal sealed class MyTimePickerEvent {
    data object OnNegativeButtonClick : MyTimePickerEvent()
    internal data class OnPositiveButtonClick(
        val selectedTime: LocalTime,
    ) : MyTimePickerEvent()
}

@Composable
internal fun MyTimePicker(
    modifier: Modifier = Modifier,
    data: MyTimePickerData,
    handleEvent: (event: MyTimePickerEvent) -> Unit = {},
) {
    val configuration = LocalConfiguration.current
    val state = rememberTimePickerState(
        is24Hour = false,
    )
    val showingPicker = remember {
        mutableStateOf(true)
    }

    if (data.isVisible) {
        TimePickerDialog(
            modifier = modifier,
            title = stringResource(
                id = if (showingPicker.value) {
                    R.string.finance_manager_time_picker_touch_input_title
                } else {
                    R.string.finance_manager_time_picker_text_input_title
                }
            ),
            onCancel = {
                handleEvent(MyTimePickerEvent.OnNegativeButtonClick)
            },
            onConfirm = {
                handleEvent(
                    MyTimePickerEvent.OnPositiveButtonClick(
                        selectedTime = LocalTime.of(state.hour, state.minute),
                    )
                )
            },
            toggle = {
                if (configuration.screenHeightDp > 400) {
                    MyIconButton(
                        imageVector = if (showingPicker.value) {
                            MyIcons.Keyboard
                        } else {
                            MyIcons.Schedule
                        },
                        contentDescriptionStringResourceId = if (showingPicker.value) {
                            R.string.finance_manager_time_picker_touch_input_switch_icon_button_content_description
                        } else {
                            R.string.finance_manager_time_picker_text_input_switch_icon_button_content_description
                        },
                        onClick = {
                            showingPicker.value = !showingPicker.value
                        }
                    )
                }
            }
        ) {
            val colors = TimePickerDefaults.colors(
                clockDialColor = FinanceManagerAppTheme.colorScheme.background,
                periodSelectorBorderColor = FinanceManagerAppTheme.colorScheme.onSurfaceVariant,

                periodSelectorSelectedContainerColor = FinanceManagerAppTheme.colorScheme.primaryContainer,
                periodSelectorUnselectedContainerColor = FinanceManagerAppTheme.colorScheme.surfaceVariant,
                periodSelectorSelectedContentColor = FinanceManagerAppTheme.colorScheme.onPrimaryContainer,
                periodSelectorUnselectedContentColor = FinanceManagerAppTheme.colorScheme.onSurfaceVariant,

                timeSelectorSelectedContainerColor = FinanceManagerAppTheme.colorScheme.primaryContainer,
                timeSelectorUnselectedContainerColor = FinanceManagerAppTheme.colorScheme.surfaceVariant,
                timeSelectorSelectedContentColor = FinanceManagerAppTheme.colorScheme.onPrimaryContainer,
                timeSelectorUnselectedContentColor = FinanceManagerAppTheme.colorScheme.onSurfaceVariant,
            )

            TimePickerTheme {
                if (showingPicker.value && configuration.screenHeightDp > MyTimePickerConstants.MIN_SCREEN_HEIGHT_REQUIRED_FOR_TIMEPICKER) {
                    TimePicker(
                        state = state,
                        colors = colors,
                    )
                } else {
                    TimeInput(
                        state = state,
                        colors = colors,
                    )
                }
            }
        }
    }
}

@Composable
private fun TimePickerDialog(
    modifier: Modifier = Modifier,
    title: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: ComposableContent = {},
    content: ComposableContent,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
    ) {
        Surface(
            shape = FinanceManagerAppTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = modifier
                .width(
                    intrinsicSize = IntrinsicSize.Min,
                )
                .height(
                    intrinsicSize = IntrinsicSize.Min,
                )
                .background(
                    shape = FinanceManagerAppTheme.shapes.extraLarge,
                    color = FinanceManagerAppTheme.colorScheme.surface,
                ),
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        all = 24.dp,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                MyText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = 20.dp,
                        ),
                    text = title,
                    style = FinanceManagerAppTheme.typography.labelMedium
                        .copy(
                            fontWeight = FontWeight.Bold,
                        ),
                )
                content()
                Row(
                    modifier = Modifier
                        .height(
                            height = 40.dp,
                        )
                        .fillMaxWidth(),
                ) {
                    toggle()
                    Spacer(
                        modifier = Modifier
                            .weight(
                                weight = 1F,
                            ),
                    )
                    MyTextButton(
                        onClick = onCancel,
                    ) {
                        MyText(
                            textStringResourceId = R.string.finance_manager_time_picker_negative_button_text,
                        )
                    }
                    MyTextButton(
                        onClick = onConfirm,
                    ) {
                        MyText(
                            textStringResourceId = R.string.finance_manager_time_picker_positive_button_text,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TimePickerTheme(
    content: ComposableContent,
) {
    FinanceManagerAppTheme(
        lightColorScheme = myLightColorScheme
            .copy(
                outline = FinanceManagerAppTheme.colorScheme.onSurfaceVariant,
            ),
        darkColorScheme = myDarkColorScheme
            .copy(
                outline = FinanceManagerAppTheme.colorScheme.onSurfaceVariant,
            ),
        content = content,
    )
}
