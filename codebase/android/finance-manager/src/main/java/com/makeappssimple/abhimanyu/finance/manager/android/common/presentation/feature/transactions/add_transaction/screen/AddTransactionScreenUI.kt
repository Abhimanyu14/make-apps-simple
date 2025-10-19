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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarResult
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
import com.makeappssimple.abhimanyu.common.core.extensions.formattedDate
import com.makeappssimple.abhimanyu.common.core.extensions.formattedTime
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.common.constants.TestTags.SCREEN_ADD_OR_EDIT_TRANSACTION
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.common.constants.TestTags.SCREEN_CONTENT_ADD_OR_EDIT_TRANSACTION
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.VerticalSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.common.AmountCommaVisualTransformation
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.common.MyTimePicker
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.common.MyTimePickerData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.common.MyTimePickerEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.bottom_sheet.account.SelectAccountBottomSheet
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.bottom_sheet.account.SelectAccountBottomSheetData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.bottom_sheet.account.SelectAccountBottomSheetEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.bottom_sheet.category.SelectCategoryBottomSheet
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.bottom_sheet.category.SelectCategoryBottomSheetData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.bottom_sheet.category.SelectCategoryBottomSheetEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.date_picker.MyDatePicker
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.date_picker.MyDatePickerData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.date_picker.MyDatePickerEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.save_button.SaveButton
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.save_button.SaveButtonData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.save_button.SaveButtonEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.selection_group.MyHorizontalScrollingRadioGroup
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.selection_group.MyHorizontalScrollingRadioGroupData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.selection_group.MyHorizontalScrollingRadioGroupEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroup
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroupData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.selection_group.MyHorizontalScrollingSelectionGroupEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.text_field.MyOutlinedTextFieldDataV2
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.text_field.MyOutlinedTextFieldEventV2
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.text_field.MyOutlinedTextFieldV2
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.text_field.MyReadOnlyTextField
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.text_field.MyReadOnlyTextFieldData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.text_field.MyReadOnlyTextFieldEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.bottom_sheet.AddTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.event.AddTransactionScreenUIEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.snackbar.AddTransactionScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.state.AddTransactionScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.state.stringResourceId
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun AddTransactionScreenUI(
    uiState: AddTransactionScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: AddTransactionScreenUIEvent) -> Unit = {},
) {
    val clearFocus = {
        state.focusManager.clearFocus()
    }
    val addTransactionFailedSnackbarText = stringResource(
        id = R.string.finance_manager_screen_add_or_edit_transaction_add_transaction_failed,
    )
    val addTransactionSuccessfulSnackbarText = stringResource(
        id = R.string.finance_manager_screen_add_or_edit_transaction_add_transaction_successful,
    )

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

    LaunchedEffect(
        key1 = uiState.screenSnackbarType,
        key2 = handleUIEvent,
    ) {
        when (uiState.screenSnackbarType) {
            AddTransactionScreenSnackbarType.AddTransactionFailed -> {
                launch {
                    val result = state.snackbarHostState.showSnackbar(
                        message = addTransactionFailedSnackbarText,
                    )
                    when (result) {
                        SnackbarResult.ActionPerformed -> {}
                        SnackbarResult.Dismissed -> {
                            handleUIEvent(AddTransactionScreenUIEvent.OnSnackbarDismissed)
                        }
                    }
                }
            }

            AddTransactionScreenSnackbarType.AddTransactionSuccessful -> {
                launch {
                    val result = state.snackbarHostState.showSnackbar(
                        message = addTransactionSuccessfulSnackbarText,
                    )
                    when (result) {
                        SnackbarResult.ActionPerformed -> {}
                        SnackbarResult.Dismissed -> {
                            handleUIEvent(AddTransactionScreenUIEvent.OnSnackbarDismissed)
                        }
                    }
                }
            }

            AddTransactionScreenSnackbarType.None -> {}
        }
    }

    BottomSheetHandler(
        isBottomSheetVisible = uiState.isBottomSheetVisible,
        screenBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
        keyboardController = state.keyboardController,
    )

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_ADD_OR_EDIT_TRANSACTION,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                is AddTransactionScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }

                is AddTransactionScreenBottomSheetType.SelectCategory -> {
                    SelectCategoryBottomSheet(
                        data = SelectCategoryBottomSheetData(
                            filteredCategories = uiState.filteredCategories,
                            selectedCategoryId = uiState.category?.id,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is SelectCategoryBottomSheetEvent.ResetBottomSheetType -> {
                                    handleUIEvent(AddTransactionScreenUIEvent.OnBottomSheetDismissed)
                                }

                                is SelectCategoryBottomSheetEvent.UpdateCategory -> {
                                    handleUIEvent(
                                        AddTransactionScreenUIEvent.OnCategoryUpdated(
                                            updatedCategory = event.updatedCategory,
                                        )
                                    )
                                }
                            }
                        },
                    )
                }

                is AddTransactionScreenBottomSheetType.SelectAccountFrom -> {
                    SelectAccountBottomSheet(
                        data = SelectAccountBottomSheetData(
                            accounts = uiState.accounts,
                            selectedAccountId = uiState.accountFrom?.id,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is SelectAccountBottomSheetEvent.ResetBottomSheetType -> {
                                    handleUIEvent(AddTransactionScreenUIEvent.OnBottomSheetDismissed)
                                }

                                is SelectAccountBottomSheetEvent.UpdateAccount -> {
                                    handleUIEvent(
                                        AddTransactionScreenUIEvent.OnAccountFromUpdated(
                                            updatedAccountFrom = event.updatedAccount,
                                        )
                                    )
                                }
                            }
                        },
                    )
                }

                is AddTransactionScreenBottomSheetType.SelectAccountTo -> {
                    SelectAccountBottomSheet(
                        data = SelectAccountBottomSheetData(
                            accounts = uiState.accounts,
                            selectedAccountId = uiState.accountTo?.id,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is SelectAccountBottomSheetEvent.ResetBottomSheetType -> {
                                    handleUIEvent(AddTransactionScreenUIEvent.OnBottomSheetDismissed)
                                }

                                is SelectAccountBottomSheetEvent.UpdateAccount -> {
                                    handleUIEvent(
                                        AddTransactionScreenUIEvent.OnAccountToUpdated(
                                            updatedAccountTo = event.updatedAccount,
                                        )
                                    )
                                }
                            }
                        },
                    )
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        snackbarHostState = state.snackbarHostState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.finance_manager_screen_add_transaction_appbar_title,
                onNavigationButtonClick = {
                    handleUIEvent(AddTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.isBottomSheetVisible,
        isBackHandlerEnabled = uiState.screenBottomSheetType != AddTransactionScreenBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBottomSheetDismiss = {
            handleUIEvent(AddTransactionScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        MyDatePicker(
            data = MyDatePickerData(
                isVisible = uiState.isTransactionDatePickerDialogVisible,
                endLocalDate = uiState.currentLocalDate,
                selectedLocalDate = uiState.transactionDate,
            ),
            handleEvent = { event ->
                when (event) {
                    is MyDatePickerEvent.OnNegativeButtonClick -> {
                        handleUIEvent(AddTransactionScreenUIEvent.OnTransactionDatePickerDismissed)
                    }

                    is MyDatePickerEvent.OnPositiveButtonClick -> {
                        handleUIEvent(
                            AddTransactionScreenUIEvent.OnTransactionDateUpdated(
                                updatedTransactionDate = event.selectedDate,
                            )
                        )
                    }
                }
            },
        )
        MyTimePicker(
            data = MyTimePickerData(
                isVisible = uiState.isTransactionTimePickerDialogVisible,
                selectedLocalDate = uiState.transactionTime,
            ),
            handleEvent = { event ->
                when (event) {
                    is MyTimePickerEvent.OnNegativeButtonClick -> {
                        handleUIEvent(AddTransactionScreenUIEvent.OnTransactionTimePickerDismissed)
                    }

                    is MyTimePickerEvent.OnPositiveButtonClick -> {
                        handleUIEvent(
                            AddTransactionScreenUIEvent.OnTransactionTimeUpdated(
                                updatedTransactionTime = event.selectedTime,
                            )
                        )
                    }
                }
            },
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_ADD_OR_EDIT_TRANSACTION,
                )
                .fillMaxSize()
                .navigationBarLandscapeSpacer()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isTransactionTypesRadioGroupVisible,
            ) {
                MyHorizontalScrollingRadioGroup(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                        ),
                    data = MyHorizontalScrollingRadioGroupData(
                        isLoading = uiState.isLoading,
                        loadingItemSize = 5,
                        items = uiState.transactionTypesForNewTransactionChipUIData,
                        selectedItemIndex = uiState.selectedTransactionTypeIndex,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyHorizontalScrollingRadioGroupEvent.OnSelectionChange -> {
                                handleUIEvent(
                                    AddTransactionScreenUIEvent.OnSelectedTransactionTypeIndexUpdated(
                                        updatedSelectedTransactionTypeIndex = event.index,
                                    )
                                )
                            }
                        }
                    },
                )
            }
            MyOutlinedTextFieldV2(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(
                        focusRequester = state.focusRequester,
                    )
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
                data = MyOutlinedTextFieldDataV2(
                    isLoading = uiState.isLoading,
                    textFieldState = uiState.amountTextFieldState,
                    labelTextStringResourceId = R.string.finance_manager_screen_add_or_edit_transaction_amount,
                    trailingIconContentDescriptionTextStringResourceId = R.string.finance_manager_screen_add_or_edit_transaction_clear_amount,
                    supportingText = if (uiState.amountErrorText.isNotNullOrBlank()) {
                        {
                            AnimatedVisibility(
                                uiState.amountErrorText.isNotNullOrBlank(),
                            ) {
                                MyText(
                                    text = stringResource(
                                        id = R.string.finance_manager_screen_add_or_edit_transaction_amount_error_text,
                                        uiState.amountErrorText,
                                    ),
                                    style = FinanceManagerAppTheme.typography.bodySmall.copy(
                                        color = FinanceManagerAppTheme.colorScheme.error,
                                    ),
                                )
                            }
                        }
                    } else {
                        null
                    },
                    isError = uiState.amountErrorText.isNotNull(),
                    visualTransformation = AmountCommaVisualTransformation(),
                    keyboardActions = {
                        clearFocus()
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Done,
                    ),
                ),
                handleEvent = { event ->
                    when (event) {
                        is MyOutlinedTextFieldEventV2.OnClickTrailingIcon -> {
                            handleUIEvent(AddTransactionScreenUIEvent.OnClearAmountButtonClick)
                        }
                    }
                },
            )
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isCategoryTextFieldVisible,
            ) {
                MyReadOnlyTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                    data = MyReadOnlyTextFieldData(
                        isLoading = uiState.isLoading,
                        value = uiState.category?.title.orEmpty(),
                        labelTextStringResourceId = R.string.finance_manager_screen_add_or_edit_transaction_category,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyReadOnlyTextFieldEvent.OnClick -> {
                                clearFocus()
                                handleUIEvent(AddTransactionScreenUIEvent.OnCategoryTextFieldClick)
                            }
                        }
                    },
                )
            }
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isTitleTextFieldVisible,
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
                        textFieldState = uiState.titleTextFieldState,
                        labelTextStringResourceId = R.string.finance_manager_screen_add_or_edit_transaction_title,
                        trailingIconContentDescriptionTextStringResourceId = R.string.finance_manager_screen_add_or_edit_transaction_clear_title,
                        keyboardActions = {
                            clearFocus()
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done,
                        ),
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyOutlinedTextFieldEventV2.OnClickTrailingIcon -> {
                                handleUIEvent(AddTransactionScreenUIEvent.OnClearTitleButtonClick)
                            }
                        }
                    },
                )
            }
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isTitleSuggestionsVisible,
            ) {
                MyHorizontalScrollingSelectionGroup(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 4.dp,
                        ),
                    data = MyHorizontalScrollingSelectionGroupData(
                        isLoading = uiState.isLoading,
                        items = uiState.titleSuggestionsChipUIData,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyHorizontalScrollingSelectionGroupEvent.OnSelectionChange -> {
                                clearFocus()
                                handleUIEvent(
                                    AddTransactionScreenUIEvent.OnTitleUpdated(
                                        updatedTitle = uiState.titleSuggestions[event.index],
                                    ),
                                )
                            }
                        }
                    },
                )
            }
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isTransactionForRadioGroupVisible,
            ) {
                MyHorizontalScrollingRadioGroup(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            top = 4.dp,
                            end = 16.dp,
                        ),
                    data = MyHorizontalScrollingRadioGroupData(
                        isLoading = uiState.isLoading,
                        loadingItemSize = 5,
                        items = uiState.transactionForValuesChipUIData,
                        selectedItemIndex = uiState.selectedTransactionForIndex,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyHorizontalScrollingRadioGroupEvent.OnSelectionChange -> {
                                clearFocus()
                                handleUIEvent(
                                    AddTransactionScreenUIEvent.OnSelectedTransactionForIndexUpdated(
                                        updatedSelectedTransactionForIndex = event.index,
                                    )
                                )
                            }
                        }
                    },
                )
            }
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isAccountFromTextFieldVisible,
            ) {
                MyReadOnlyTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                    data = MyReadOnlyTextFieldData(
                        isLoading = uiState.isLoading,
                        value = uiState.accountFrom?.name.orEmpty(),
                        labelTextStringResourceId = uiState.accountFromText.stringResourceId,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyReadOnlyTextFieldEvent.OnClick -> {
                                clearFocus()
                                handleUIEvent(AddTransactionScreenUIEvent.OnAccountFromTextFieldClick)
                            }
                        }
                    },
                )
            }
            AnimatedVisibility(
                visible = uiState.uiVisibilityState.isAccountToTextFieldVisible,
            ) {
                MyReadOnlyTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 4.dp,
                        ),
                    data = MyReadOnlyTextFieldData(
                        isLoading = uiState.isLoading,
                        value = uiState.accountTo?.name.orEmpty(),
                        labelTextStringResourceId = uiState.accountToText.stringResourceId,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyReadOnlyTextFieldEvent.OnClick -> {
                                clearFocus()
                                handleUIEvent(AddTransactionScreenUIEvent.OnAccountToTextFieldClick)
                            }
                        }
                    },
                )
            }
            MyReadOnlyTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
                data = MyReadOnlyTextFieldData(
                    isLoading = uiState.isLoading,
                    value = uiState.transactionDate.formattedDate(),
                    labelTextStringResourceId = R.string.finance_manager_screen_add_or_edit_transaction_transaction_date,
                ),
                handleEvent = { event ->
                    when (event) {
                        is MyReadOnlyTextFieldEvent.OnClick -> {
                            clearFocus()
                            handleUIEvent(AddTransactionScreenUIEvent.OnTransactionDateTextFieldClick)
                        }
                    }
                },
            )
            MyReadOnlyTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
                data = MyReadOnlyTextFieldData(
                    isLoading = uiState.isLoading,
                    value = uiState.transactionTime.formattedTime(),
                    labelTextStringResourceId = R.string.finance_manager_screen_add_or_edit_transaction_transaction_time,
                ),
                handleEvent = { event ->
                    when (event) {
                        MyReadOnlyTextFieldEvent.OnClick -> {
                            clearFocus()
                            handleUIEvent(AddTransactionScreenUIEvent.OnTransactionTimeTextFieldClick)
                        }
                    }
                },
            )
            SaveButton(
                modifier = Modifier
                    .padding(
                        all = 8.dp,
                    ),
                data = SaveButtonData(
                    isEnabled = uiState.isCtaButtonEnabled,
                    isLoading = uiState.isLoading,
                    textStringResourceId = R.string.finance_manager_screen_add_transaction_floating_action_button_content_description,
                ),
                handleEvent = { event ->
                    when (event) {
                        is SaveButtonEvent.OnClick -> {
                            handleUIEvent(AddTransactionScreenUIEvent.OnCtaButtonClick)
                        }
                    }
                },
            )
            NavigationBarsAndImeSpacer()
        }
    }
}
