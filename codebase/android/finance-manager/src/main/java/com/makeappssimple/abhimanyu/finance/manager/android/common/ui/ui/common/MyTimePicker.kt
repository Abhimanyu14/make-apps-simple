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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.common

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button.CosmosIconButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button.CosmosTextButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme
import com.makeappssimple.abhimanyu.cosmos.design.system.android.typealiases.ComposableContent
import com.makeappssimple.abhimanyu.cosmos.design.system.android.date_time.MyLocalTime
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.theme.financeManagerDarkColorScheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.theme.financeManagerLightColorScheme
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

private object MyTimePickerConstants {
    const val MIN_SCREEN_HEIGHT_REQUIRED_FOR_TIMEPICKER = 400
}

@Immutable
internal data class MyTimePickerData(
    val isVisible: Boolean = false,
    val selectedLocalDate: MyLocalTime? = null,
)

@Immutable
internal sealed class MyTimePickerEvent {
    data object OnNegativeButtonClick : MyTimePickerEvent()
    internal data class OnPositiveButtonClick(
        val selectedTime: MyLocalTime,
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
            titleStringResource = CosmosStringResource.Id(
                id = if (showingPicker.value) {
                    R.string.finance_manager_time_picker_touch_input_title
                } else {
                    R.string.finance_manager_time_picker_text_input_title
                },
            ),
            onCancel = {
                handleEvent(MyTimePickerEvent.OnNegativeButtonClick)
            },
            onConfirm = {
                handleEvent(
                    MyTimePickerEvent.OnPositiveButtonClick(
                        selectedTime = MyLocalTime.of(
                            state.hour,
                            state.minute
                        ),
                    )
                )
            },
            toggle = {
                if (configuration.screenHeightDp > 400) {
                    CosmosIconButton(
                        onClickLabelStringResource = CosmosStringResource.Id(
                            id = if (showingPicker.value) {
                                R.string.finance_manager_time_picker_touch_input_switch_icon_button_content_description
                            } else {
                                R.string.finance_manager_time_picker_text_input_switch_icon_button_content_description
                            },
                        ),
                        onClick = {
                            showingPicker.value = !showingPicker.value
                        },
                    ) {
                        CosmosIcon(
                            iconResource = if (showingPicker.value) {
                                CosmosIcons.Keyboard
                            } else {
                                CosmosIcons.Schedule
                            },
                        )
                    }
                }
            }
        ) {
            val colors = TimePickerDefaults.colors(
                clockDialColor = CosmosAppTheme.colorScheme.background,
                periodSelectorBorderColor = CosmosAppTheme.colorScheme.onSurfaceVariant,

                periodSelectorSelectedContainerColor = CosmosAppTheme.colorScheme.primaryContainer,
                periodSelectorUnselectedContainerColor = CosmosAppTheme.colorScheme.surfaceVariant,
                periodSelectorSelectedContentColor = CosmosAppTheme.colorScheme.onPrimaryContainer,
                periodSelectorUnselectedContentColor = CosmosAppTheme.colorScheme.onSurfaceVariant,

                timeSelectorSelectedContainerColor = CosmosAppTheme.colorScheme.primaryContainer,
                timeSelectorUnselectedContainerColor = CosmosAppTheme.colorScheme.surfaceVariant,
                timeSelectorSelectedContentColor = CosmosAppTheme.colorScheme.onPrimaryContainer,
                timeSelectorUnselectedContentColor = CosmosAppTheme.colorScheme.onSurfaceVariant,
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
    titleStringResource: CosmosStringResource,
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
            shape = CosmosAppTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = modifier
                .width(
                    intrinsicSize = IntrinsicSize.Min,
                )
                .height(
                    intrinsicSize = IntrinsicSize.Min,
                )
                .background(
                    shape = CosmosAppTheme.shapes.extraLarge,
                    color = CosmosAppTheme.colorScheme.surface,
                ),
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        all = 24.dp,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CosmosText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = 20.dp,
                        ),
                    stringResource = titleStringResource,
                    style = CosmosAppTheme.typography.labelMedium
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
                    CosmosTextButton(
                        onClick = onCancel,
                    ) {
                        CosmosText(
                            stringResource = CosmosStringResource.Id(
                                id = R.string.finance_manager_time_picker_negative_button_text,
                            ),
                        )
                    }
                    CosmosTextButton(
                        onClick = onConfirm,
                    ) {
                        CosmosText(
                            stringResource = CosmosStringResource.Id(
                                id = R.string.finance_manager_time_picker_positive_button_text,
                            ),
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
    CosmosAppTheme(
        lightColorScheme = financeManagerLightColorScheme
            .copy(
                outline = CosmosAppTheme.colorScheme.onSurfaceVariant,
            ),
        darkColorScheme = financeManagerDarkColorScheme
            .copy(
                outline = CosmosAppTheme.colorScheme.onSurfaceVariant,
            ),
        content = content,
    )
}
