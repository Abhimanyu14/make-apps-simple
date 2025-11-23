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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.edit_transaction_for.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.common.core.extensions.orFalse
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.constants.TestTags.SCREEN_ADD_OR_EDIT_TRANSACTION_FOR
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.constants.TestTags.SCREEN_CONTENT_ADD_OR_EDIT_TRANSACTION_FOR
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.edit_transaction_for.event.EditTransactionForScreenUIEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.edit_transaction_for.state.EditTransactionForScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.edit_transaction_for.state.stringResourceId
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.save_button.SaveButton
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.save_button.SaveButtonData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.save_button.SaveButtonEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.text_field.MyOutlinedTextFieldDataV2
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.text_field.MyOutlinedTextFieldEventV2
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.text_field.MyOutlinedTextFieldV2
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.coroutines.delay

@Composable
internal fun EditTransactionForScreenUI(
    uiState: EditTransactionForScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: EditTransactionForScreenUIEvent) -> Unit = {},
) {
    if (!uiState.isLoading) {
        LaunchedEffect(
            key1 = Unit,
        ) {
            delay(
                timeMillis = 300,
            ) // Source - https://stackoverflow.com/a/72783456/9636037
            state.focusRequester.requestFocus()
        }
    }

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_ADD_OR_EDIT_TRANSACTION_FOR,
            )
            .fillMaxSize(),
        snackbarHostState = state.snackbarHostState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.finance_manager_screen_edit_transaction_for_appbar_title,
                onNavigationButtonClick = {
                    handleUIEvent(EditTransactionForScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        coroutineScope = state.coroutineScope,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_ADD_OR_EDIT_TRANSACTION_FOR,
                )
                .fillMaxSize()
                .navigationBarLandscapeSpacer()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            MyOutlinedTextFieldV2(
                data = MyOutlinedTextFieldDataV2(
                    isLoading = uiState.isLoading,
                    textFieldState = uiState.titleTextFieldState,
                    labelTextStringResourceId = R.string.finance_manager_screen_add_or_edit_transaction_for_title,
                    trailingIconContentDescriptionTextStringResourceId = R.string.finance_manager_screen_add_or_edit_transaction_for_clear_title,
                    supportingText = {
                        uiState.titleError.stringResourceId?.let { titleTextFieldErrorTextStringResourceId ->
                            MyText(
                                text = stringResource(
                                    id = titleTextFieldErrorTextStringResourceId,
                                ),
                                style = FinanceManagerAppTheme.typography.bodySmall
                                    .copy(
                                        color = FinanceManagerAppTheme.colorScheme.error,
                                    ),
                            )
                        }
                    },
                    keyboardActions = {
                        state.focusManager.clearFocus()
                        handleUIEvent(EditTransactionForScreenUIEvent.OnCtaButtonClick)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                ),
                handleEvent = { event ->
                    when (event) {
                        is MyOutlinedTextFieldEventV2.OnClickTrailingIcon -> {
                            handleUIEvent(EditTransactionForScreenUIEvent.OnClearTitleButtonClick)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(
                        focusRequester = state.focusRequester,
                    )
                    .padding(
                        all = 16.dp,
                    ),
            )
            SaveButton(
                modifier = Modifier,
                data = SaveButtonData(
                    isEnabled = uiState.isCtaButtonEnabled.orFalse(),
                    isLoading = uiState.isLoading,
                    textStringResourceId = R.string.finance_manager_screen_edit_transaction_for_floating_action_button_content_description,
                ),
                handleEvent = { event ->
                    when (event) {
                        is SaveButtonEvent.OnClick -> {
                            handleUIEvent(EditTransactionForScreenUIEvent.OnCtaButtonClick)
                        }
                    }
                },
            )
        }
    }
}
