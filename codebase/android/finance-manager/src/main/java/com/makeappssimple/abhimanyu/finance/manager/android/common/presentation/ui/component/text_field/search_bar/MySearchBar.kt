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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.text_field.search_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.button.MyTextButton
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.extensions.shimmer.shimmer
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

private object MySearchBarConstants {
    val borderWidth = 1.dp
    val leadingIconPaddingBottom = 2.dp
    val leadingIconPaddingEnd = 2.dp
    val leadingIconPaddingStart = 12.dp
    val leadingIconPaddingTop = 2.dp
    const val leadingIconScale = 0.75F
    val loadingUIHeight = 40.dp
    val loadingUIPadding = 4.dp
    val loadingUIWidth = 80.dp
    val padding = 4.dp
    val shape = CircleShape
    val textPaddingBottom = 6.dp
    val textPaddingEnd = 16.dp
    val textPaddingStart = 16.dp
    val textPaddingStartWithIcon = 0.dp
    val textPaddingTop = 6.dp
}

@Composable
internal fun MySearchBar(
    modifier: Modifier = Modifier,
    data: MySearchBarData,
    handleEvent: (event: MySearchBarEvent) -> Unit = {},
) {
    if (data.isLoading) {
        MySearchBarLoadingUI()
    } else {
        MySearchBarUI(
            modifier = modifier,
            data = data,
            handleEvent = handleEvent,
        )
    }
}

@Composable
private fun MySearchBarUI(
    modifier: Modifier = Modifier,
    data: MySearchBarData,
    handleEvent: (event: MySearchBarEvent) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val focusRequester: FocusRequester = remember {
        FocusRequester()
    }

    if (data.autoFocus) {
        LaunchedEffect(
            key1 = Unit,
        ) {
            focusRequester.requestFocus()
        }
    }

    BasicTextField(
        value = data.searchText,
        onValueChange = {
            handleEvent(
                MySearchBarEvent.OnSearchTextChange(
                    updatedSearchText = it,
                )
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .height(
                height = 40.dp,
            )
            .focusRequester(
                focusRequester = focusRequester,
            ),
        textStyle = FinanceManagerAppTheme.typography.bodyLarge
            .copy(
                color = FinanceManagerAppTheme.colorScheme.onPrimaryContainer,
            ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                handleEvent(MySearchBarEvent.OnSearch)
            },
        ),
        singleLine = true,
        cursorBrush = SolidColor(FinanceManagerAppTheme.colorScheme.primary),
        decorationBox = {
            TextFieldDefaults.DecorationBox(
                value = data.searchText,
                innerTextField = it,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                placeholder = {
                    MyText(
                        text = data.placeholderText,
                        style = FinanceManagerAppTheme.typography.headlineLarge
                            .copy(
                                color = FinanceManagerAppTheme.colorScheme.onPrimaryContainer,
                            ),
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = MyIcons.Search,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(
                                all = 0.dp,
                            )
                            .size(
                                size = 20.dp,
                            ),
                    )
                },
                trailingIcon = if (data.searchText.isNotBlank()) {
                    {
                        Icon(
                            imageVector = MyIcons.Close,
                            contentDescription = null,
                            modifier = Modifier
                                .clip(
                                    shape = CircleShape,
                                )
                                .clickable {
                                    focusManager.clearFocus()
                                    handleEvent(
                                        MySearchBarEvent.OnSearchTextChange(
                                            updatedSearchText = "",
                                        )
                                    )
                                }
                                .padding(
                                    all = 6.dp,
                                )
                                .size(
                                    size = 20.dp,
                                ),
                        )
                    }
                } else {
                    null
                },
                shape = CircleShape,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = FinanceManagerAppTheme.colorScheme.primaryContainer,
                    unfocusedContainerColor = FinanceManagerAppTheme.colorScheme.primaryContainer,
                    disabledContainerColor = FinanceManagerAppTheme.colorScheme.primaryContainer,
                    focusedIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent,
                ),
                contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                    start = 0.dp,
                    top = 0.dp,
                    end = 0.dp,
                    bottom = 0.dp,
                ),
                container = {
                    Box(
                        modifier = Modifier
                            .requiredHeight(
                                height = 40.dp,
                            )
                            .clip(
                                shape = CircleShape,
                            )
                            .background(
                                color = FinanceManagerAppTheme.colorScheme.primaryContainer,
                            )
                    )
                },
            )
        },
    )
}

@Composable
internal fun MySearchBarV2(
    modifier: Modifier = Modifier,
    data: MySearchBarDataV2,
    handleEvent: (event: MySearchBarEventV2) -> Unit = {},
) {
    if (data.isLoading) {
        MySearchBarLoadingUI()
    } else {
        MySearchBarUIV2(
            modifier = modifier,
            data = data,
            handleEvent = handleEvent,
        )
    }
}

@Composable
private fun MySearchBarUIV2(
    modifier: Modifier = Modifier,
    data: MySearchBarDataV2,
    handleEvent: (event: MySearchBarEventV2) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val focusRequester: FocusRequester = remember {
        FocusRequester()
    }
    var keyboardType by remember {
        mutableStateOf(
            value = KeyboardType.Text,
        )
    }

    if (data.autoFocus) {
        LaunchedEffect(
            key1 = Unit,
        ) {
            focusRequester.requestFocus()
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(
                height = 40.dp,
            )
            .clip(
                shape = CircleShape,
            )
            .background(
                color = FinanceManagerAppTheme.colorScheme.primaryContainer,
            )
            .padding(
                start = 16.dp,
                end = 8.dp,
            ),
    ) {
        Icon(
            imageVector = MyIcons.Search,
            contentDescription = null,
            modifier = Modifier
                .padding(
                    all = 0.dp,
                )
                .size(
                    size = 20.dp,
                ),
        )
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .weight(
                    weight = 1F,
                ),
        ) {
            BasicTextField(
                state = data.searchTextFieldState,
                modifier = modifier
                    .fillMaxWidth()
                    .focusRequester(
                        focusRequester = focusRequester,
                    )
                    .padding(
                        horizontal = 8.dp,
                    ),
                textStyle = FinanceManagerAppTheme.typography.bodyLarge
                    .copy(
                        color = FinanceManagerAppTheme.colorScheme.onPrimaryContainer,
                    ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    keyboardType = keyboardType,
                ),
                lineLimits = TextFieldLineLimits.SingleLine,
                onKeyboardAction = {
                    handleEvent(MySearchBarEventV2.OnSearch)
                },
                cursorBrush = SolidColor(
                    value = FinanceManagerAppTheme.colorScheme.primary,
                ),
            )
            if (data.searchTextFieldState.text.isEmpty()) {
                MyText(
                    text = data.placeholderText,
                    style = FinanceManagerAppTheme.typography.bodyLarge
                        .copy(
                            color = FinanceManagerAppTheme.colorScheme.onPrimaryContainer,
                        ),
                    modifier = Modifier
                        .padding(
                            horizontal = 6.dp,
                        ),
                )
            }
        }
        if (data.searchTextFieldState.text.isEmpty()) {
            VerticalDivider(
                modifier = Modifier
                    .padding(
                        top = 2.dp,
                        bottom = 2.dp,
                        start = 2.dp,
                        end = 2.dp
                    ),
            )
            MyTextButton(
                onClick = {
                    keyboardType = if (keyboardType == KeyboardType.Text) {
                        KeyboardType.Number
                    } else {
                        KeyboardType.Text
                    }
                    focusRequester.requestFocus()
                },
            ) {
                MyText(
                    textStringResourceId = if (keyboardType == KeyboardType.Text) {
                        R.string.finance_manager_search_bar_keyboard_type_number
                    } else {
                        R.string.finance_manager_search_bar_keyboard_type_text
                    },
                )
            }
        } else {
            Icon(
                imageVector = MyIcons.Close,
                contentDescription = null,
                modifier = Modifier
                    .clip(
                        shape = CircleShape,
                    )
                    .clickable {
                        focusManager.clearFocus()
                        data.searchTextFieldState.clearText()
                    }
                    .padding(
                        all = 6.dp,
                    )
                    .size(
                        size = 20.dp,
                    ),
            )
        }
    }
}

@Composable
private fun MySearchBarLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(
                MySearchBarConstants.loadingUIHeight,
            )
            .fillMaxWidth()
            .clip(
                shape = MySearchBarConstants.shape,
            )
            .shimmer(),
    )
}
