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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.constants.TestTags.SCREEN_ADD_OR_EDIT_ACCOUNT
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.constants.TestTags.SCREEN_CONTENT_ADD_OR_EDIT_ACCOUNT
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.common.AmountCommaVisualTransformation
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.save_button.SaveButton
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.save_button.SaveButtonData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.save_button.SaveButtonEvent
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.selection_group.MyRadioGroup
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.selection_group.MyRadioGroupData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.selection_group.MyRadioGroupEvent
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.text_field.MyOutlinedTextField
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.text_field.MyOutlinedTextFieldData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.text_field.MyOutlinedTextFieldEvent
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.event.EditAccountScreenUIEvent
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.state.EditAccountScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.state.stringResourceId
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Immutable
internal data class EditAccountScreenUIVisibilityData(
    val balanceAmountTextField: Boolean = false,
    val minimumBalanceAmountTextField: Boolean = false,
    val nameTextField: Boolean = false,
    val nameTextFieldErrorText: Boolean = false,
    val accountTypesRadioGroup: Boolean = false,
)

@Composable
internal fun EditAccountScreenUI(
    uiState: EditAccountScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: EditAccountScreenUIEvent) -> Unit = {},
) {
    val nameTextFieldFocusRequester = remember {
        FocusRequester()
    }
    val balanceAmountTextFieldFocusRequester = remember {
        FocusRequester()
    }

    if (!uiState.isLoading) {
        LaunchedEffect(
            key1 = uiState.visibilityData.balanceAmountTextField,
            key2 = uiState.visibilityData.nameTextField,
        ) {
            if (uiState.visibilityData.balanceAmountTextField) {
                balanceAmountTextFieldFocusRequester.requestFocus()
            } else if (uiState.visibilityData.nameTextField) {
                nameTextFieldFocusRequester.requestFocus()
            }
            state.keyboardController?.show()
        }
    }

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_ADD_OR_EDIT_ACCOUNT,
            )
            .fillMaxSize(),
        sheetState = state.modalBottomSheetState,
        snackbarHostState = state.snackbarHostState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.finance_manager_screen_edit_account_appbar_title,
                navigationAction = {
                    handleUIEvent(EditAccountScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        coroutineScope = state.coroutineScope,
        onBottomSheetDismiss = {
            handleUIEvent(EditAccountScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_ADD_OR_EDIT_ACCOUNT,
                )
                .fillMaxSize()
                .navigationBarLandscapeSpacer()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            if (uiState.visibilityData.accountTypesRadioGroup) {
                MyRadioGroup(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        ),
                    data = MyRadioGroupData(
                        isLoading = uiState.isLoading,
                        items = uiState.accountTypesChipUIDataList,
                        selectedItemIndex = uiState.selectedAccountTypeIndex,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyRadioGroupEvent.OnSelectionChange -> {
                                handleUIEvent(
                                    EditAccountScreenUIEvent.OnSelectedAccountTypeIndexUpdated(
                                        updatedIndex = event.index,
                                    )
                                )
                            }
                        }
                    },
                )
            }
            if (uiState.visibilityData.nameTextField) {
                MyOutlinedTextField(
                    modifier = Modifier
                        .focusRequester(
                            focusRequester = nameTextFieldFocusRequester,
                        )
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                    data = MyOutlinedTextFieldData(
                        isError = uiState.visibilityData.nameTextFieldErrorText,
                        isLoading = uiState.isLoading,
                        textFieldValue = uiState.name,
                        labelTextStringResourceId = R.string.finance_manager_screen_add_or_edit_account_name,
                        trailingIconContentDescriptionTextStringResourceId = R.string.finance_manager_screen_add_or_edit_account_clear_name,
                        supportingText = if (uiState.visibilityData.nameTextFieldErrorText) {
                            {
                                uiState.nameError.stringResourceId?.let { nameTextFieldErrorTextStringResourceId ->
                                    MyText(
                                        text = stringResource(
                                            id = nameTextFieldErrorTextStringResourceId,
                                        ),
                                        style = MaterialTheme.typography.bodySmall
                                            .copy(
                                                color = MaterialTheme.colorScheme.error,
                                            ),
                                    )
                                }
                            }
                        } else {
                            null
                        },
                        keyboardActions = KeyboardActions(
                            onNext = {
                                state.focusManager.moveFocus(
                                    focusDirection = FocusDirection.Down,
                                )
                            },
                            onDone = {
                                state.focusManager.clearFocus()
                            },
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = if (uiState.visibilityData.balanceAmountTextField) {
                                ImeAction.Next
                            } else {
                                ImeAction.Done
                            },
                        ),
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyOutlinedTextFieldEvent.OnClickTrailingIcon -> {
                                handleUIEvent(EditAccountScreenUIEvent.OnClearNameButtonClick)
                            }

                            is MyOutlinedTextFieldEvent.OnValueChange -> {
                                handleUIEvent(
                                    EditAccountScreenUIEvent.OnNameUpdated(
                                        updatedName = event.updatedValue,
                                    )
                                )
                            }
                        }
                    },
                )
            }
            if (uiState.visibilityData.balanceAmountTextField) {
                MyOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(
                            focusRequester = balanceAmountTextFieldFocusRequester,
                        )
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                    data = MyOutlinedTextFieldData(
                        isLoading = uiState.isLoading,
                        textFieldValue = uiState.balanceAmountValue,
                        labelTextStringResourceId = R.string.finance_manager_screen_add_or_edit_account_balance_amount_value,
                        trailingIconContentDescriptionTextStringResourceId = R.string.finance_manager_screen_add_or_edit_account_clear_balance_amount_value,
                        visualTransformation = AmountCommaVisualTransformation(),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                state.focusManager.moveFocus(
                                    focusDirection = FocusDirection.Down,
                                )
                            },
                            onDone = {
                                state.focusManager.clearFocus()
                            },
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Done,
                        ),
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyOutlinedTextFieldEvent.OnClickTrailingIcon -> {
                                handleUIEvent(EditAccountScreenUIEvent.OnClearBalanceAmountValueButtonClick)
                            }

                            is MyOutlinedTextFieldEvent.OnValueChange -> {
                                handleUIEvent(
                                    EditAccountScreenUIEvent.OnBalanceAmountValueUpdated(
                                        updatedBalanceAmountValue = event.updatedValue,
                                    )
                                )
                            }
                        }
                    },
                )
            }
            if (uiState.visibilityData.minimumBalanceAmountTextField) {
                MyOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                    data = MyOutlinedTextFieldData(
                        isLoading = uiState.isLoading,
                        textFieldValue = uiState.minimumBalanceAmountValue,
                        labelTextStringResourceId = R.string.finance_manager_screen_add_or_edit_account_minimum_account_balance_amount_value,
                        trailingIconContentDescriptionTextStringResourceId = R.string.finance_manager_screen_add_or_edit_account_clear_minimum_account_balance_amount_value,
                        visualTransformation = AmountCommaVisualTransformation(),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                state.focusManager.clearFocus()
                            },
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Done,
                        ),
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyOutlinedTextFieldEvent.OnClickTrailingIcon -> {
                                handleUIEvent(EditAccountScreenUIEvent.OnClearMinimumAccountBalanceAmountValueButtonClick)
                            }

                            is MyOutlinedTextFieldEvent.OnValueChange -> {
                                handleUIEvent(
                                    EditAccountScreenUIEvent.OnMinimumAccountBalanceAmountValueUpdated(
                                        updatedMinimumAccountBalanceAmountValue = event.updatedValue,
                                    )
                                )
                            }
                        }
                    },
                )
            }
            SaveButton(
                modifier = Modifier
                    .padding(
                        all = 8.dp,
                    ),
                data = SaveButtonData(
                    isEnabled = uiState.isCtaButtonEnabled,
                    isLoading = uiState.isLoading,
                    textStringResourceId = R.string.finance_manager_screen_edit_account_floating_action_button_content_description,
                ),
                handleEvent = { event ->
                    when (event) {
                        is SaveButtonEvent.OnClick -> {
                            handleUIEvent(EditAccountScreenUIEvent.OnCtaButtonClick)
                        }
                    }
                },
            )
        }
    }
}
