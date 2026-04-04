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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.feature.accounts.add_account.screen

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.save_button.CosmosSaveButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.save_button.CosmosSaveButtonData
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.save_button.CosmosSaveButtonEvent
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.spacer.cosmosNavigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text_field.MyOutlinedTextFieldDataV2
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text_field.MyOutlinedTextFieldEventV2
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text_field.MyOutlinedTextFieldV2
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.constants.TestTags.SCREEN_ADD_OR_EDIT_ACCOUNT
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.constants.TestTags.SCREEN_CONTENT_ADD_OR_EDIT_ACCOUNT
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.add_account.event.AddAccountScreenUIEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.add_account.state.AddAccountScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.add_account.state.stringResourceId
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.common.AmountInputTransformation
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.common.AmountOutputTransformation
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.selection_group.MyRadioGroup
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.selection_group.MyRadioGroupData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.selection_group.MyRadioGroupEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Composable
internal fun AddAccountScreenUI(
    uiState: AddAccountScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: AddAccountScreenUIEvent) -> Unit = {},
) {
    val nameTextFieldFocusRequester = remember {
        FocusRequester()
    }

    if (!uiState.isLoading) {
        LaunchedEffect(
            key1 = Unit,
        ) {
            val isFocusRequested = nameTextFieldFocusRequester.requestFocus()
            if (isFocusRequested) {
                state.keyboardController?.show()
            }
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
                titleTextStringResourceId = R.string.finance_manager_screen_add_account_appbar_title,
                onNavigationButtonClick = {
                    handleUIEvent(AddAccountScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        coroutineScope = state.coroutineScope,
        onBottomSheetDismiss = {
            handleUIEvent(AddAccountScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_ADD_OR_EDIT_ACCOUNT,
                )
                .fillMaxSize()
                .cosmosNavigationBarLandscapeSpacer()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
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
                                AddAccountScreenUIEvent.OnSelectedAccountTypeIndexUpdated(
                                    updatedIndex = event.index,
                                )
                            )
                        }
                    }
                },
            )
            MyOutlinedTextFieldV2(
                modifier = Modifier
                    .focusRequester(
                        focusRequester = nameTextFieldFocusRequester,
                    )
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
                data = MyOutlinedTextFieldDataV2(
                    isError = uiState.visibilityData.nameTextFieldErrorText,
                    isLoading = uiState.isLoading,
                    textFieldState = uiState.nameTextFieldState,
                    labelTextStringResourceId = R.string.finance_manager_screen_add_or_edit_account_name,
                    trailingIconContentDescriptionTextStringResourceId = R.string.finance_manager_screen_add_or_edit_account_clear_name,
                    supportingText = {
                        uiState.nameError.stringResourceId?.let { nameTextFieldErrorTextStringResourceId ->
                            CosmosText(
                                stringResource = CosmosStringResource.Id(
                                    nameTextFieldErrorTextStringResourceId,
                                ),
                                style = CosmosAppTheme.typography.bodySmall.copy(
                                    color = CosmosAppTheme.colorScheme.error,
                                ),
                            )
                        }
                    },
                    keyboardActions = {
                        state.focusManager.clearFocus()
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                ),
                handleEvent = { event ->
                    when (event) {
                        is MyOutlinedTextFieldEventV2.OnClickTrailingIcon -> {
                            handleUIEvent(AddAccountScreenUIEvent.OnClearNameButtonClick)
                        }
                    }
                },
            )
            AnimatedVisibility(
                visible = uiState.visibilityData.minimumBalanceAmountTextField,
            ) {
                MyOutlinedTextFieldV2(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                    data = MyOutlinedTextFieldDataV2(
                        isLoading = uiState.isLoading,
                        textFieldState = uiState.minimumAccountBalanceTextFieldState,
                        labelTextStringResourceId = R.string.finance_manager_screen_add_or_edit_account_minimum_account_balance_amount_value,
                        trailingIconContentDescriptionTextStringResourceId = R.string.finance_manager_screen_add_or_edit_account_clear_minimum_account_balance_amount_value,
                        inputTransformation = AmountInputTransformation(),
                        outputTransformation = AmountOutputTransformation(),
                        keyboardActions = {
                            state.focusManager.clearFocus()
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Done,
                        ),
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyOutlinedTextFieldEventV2.OnClickTrailingIcon -> {
                                handleUIEvent(AddAccountScreenUIEvent.OnClearMinimumAccountBalanceAmountValueButtonClick)
                            }
                        }
                    },
                )
            }
            CosmosSaveButton(
                modifier = Modifier
                    .padding(
                        all = 8.dp,
                    ),
                data = CosmosSaveButtonData(
                    isEnabled = uiState.isCtaButtonEnabled,
                    isLoading = uiState.isLoading,
                    stringResource = CosmosStringResource.Id(
                        id = R.string.finance_manager_screen_add_account_floating_action_button_content_description,
                    ),
                ),
                handleEvent = { event ->
                    when (event) {
                        is CosmosSaveButtonEvent.OnClick -> {
                            handleUIEvent(AddAccountScreenUIEvent.OnCtaButtonClick)
                        }
                    }
                },
            )
        }
    }
}
