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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.feature.categories.edit_category.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.common.core.extensions.orFalse
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.spacer.CosmosNavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.spacer.CosmosVerticalSpacer
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.spacer.cosmosNavigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.constants.TestTags.SCREEN_ADD_OR_EDIT_CATEGORY
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.constants.TestTags.SCREEN_CONTENT_ADD_OR_EDIT_CATEGORY
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.edit_category.bottom_sheet.EditCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.edit_category.event.EditCategoryScreenUIEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.edit_category.state.EditCategoryScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.edit_category.state.stringResourceId
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.category.EditCategorySelectEmojiBottomSheet
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.emoji_circle.EmojiCircleSize
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.emoji_circle.MyEmojiCircle
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.emoji_circle.MyEmojiCircleData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.emoji_circle.MyEmojiCircleEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.save_button.SaveButton
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.save_button.SaveButtonData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.save_button.SaveButtonEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.selection_group.MyRadioGroup
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.selection_group.MyRadioGroupData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.selection_group.MyRadioGroupEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.text_field.MyOutlinedTextFieldDataV2
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.text_field.MyOutlinedTextFieldEventV2
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.text_field.MyOutlinedTextFieldV2
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.coroutines.delay

@Composable
internal fun EditCategoryScreenUI(
    uiState: EditCategoryScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: EditCategoryScreenUIEvent) -> Unit = {},
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
                tag = SCREEN_ADD_OR_EDIT_CATEGORY,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                EditCategoryScreenBottomSheetType.None -> {
                    CosmosVerticalSpacer()
                }

                EditCategoryScreenBottomSheetType.SelectEmoji -> {
                    EditCategorySelectEmojiBottomSheet(
                        resetBottomSheetType = {
                            handleUIEvent(EditCategoryScreenUIEvent.OnBottomSheetDismissed)
                        },
                        updateEmoji = { updatedEmoji ->
                            handleUIEvent(
                                EditCategoryScreenUIEvent.OnEmojiUpdated(
                                    updatedEmoji = updatedEmoji,
                                )
                            )
                        },
                    )
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        snackbarHostState = state.snackbarHostState,
        sheetShape = when (uiState.screenBottomSheetType) {
            is EditCategoryScreenBottomSheetType.None -> {
                BottomSheetShape
            }

            is EditCategoryScreenBottomSheetType.SelectEmoji -> {
                BottomSheetExpandedShape
            }
        },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.finance_manager_screen_add_category_appbar_title,
                onNavigationButtonClick = {
                    handleUIEvent(EditCategoryScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.isBottomSheetVisible,
        isBackHandlerEnabled = uiState.screenBottomSheetType != EditCategoryScreenBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBottomSheetDismiss = {
            handleUIEvent(EditCategoryScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_ADD_OR_EDIT_CATEGORY,
                )
                .fillMaxSize()
                .cosmosNavigationBarLandscapeSpacer()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            MyRadioGroup(
                data = MyRadioGroupData(
                    isLoading = uiState.isLoading,
                    items = uiState.transactionTypesChipUIData,
                    selectedItemIndex = uiState.selectedTransactionTypeIndex,
                ),
                handleEvent = { event ->
                    when (event) {
                        is MyRadioGroupEvent.OnSelectionChange -> {
                            handleUIEvent(
                                EditCategoryScreenUIEvent.OnSelectedTransactionTypeIndexUpdated(
                                    updatedIndex = event.index,
                                )
                            )
                        }
                    }
                },
                modifier = Modifier
                    .padding(
                        horizontal = 8.dp,
                        vertical = 4.dp,
                    ),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                    )
                    .fillMaxWidth(),
            ) {
                MyEmojiCircle(
                    data = MyEmojiCircleData(
                        isClickable = true,
                        isLoading = uiState.isLoading,
                        emojiCircleSize = EmojiCircleSize.Normal,
                        emoji = uiState.emoji,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyEmojiCircleEvent.OnClick -> {
                                handleUIEvent(EditCategoryScreenUIEvent.OnEmojiCircleClick)
                            }
                        }
                    },
                )
                MyOutlinedTextFieldV2(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(
                            focusRequester = state.focusRequester,
                        )
                        .padding(
                            start = 8.dp,
                        ),
                    data = MyOutlinedTextFieldDataV2(
                        isLoading = uiState.isLoading,
                        textFieldState = uiState.titleTextFieldState,
                        labelTextStringResourceId = R.string.finance_manager_screen_add_or_edit_category_title,
                        trailingIconContentDescriptionTextStringResourceId = R.string.finance_manager_screen_add_or_edit_category_clear_title,
                        supportingText = if (uiState.isSupportingTextVisible) {
                            {
                                uiState.titleError.stringResourceId?.let { titleTextFieldErrorTextStringResourceId ->
                                    CosmosText(
                                        stringResource = CosmosStringResource.Id(
                                            id = titleTextFieldErrorTextStringResourceId,
                                        ),
                                        style = CosmosAppTheme.typography.bodySmall.copy(
                                            color = CosmosAppTheme.colorScheme.error,
                                        ),
                                    )
                                }
                            }
                        } else {
                            null
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
                                handleUIEvent(EditCategoryScreenUIEvent.OnClearTitleButtonClick)
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
                    isEnabled = uiState.isCtaButtonEnabled.orFalse(),
                    isLoading = uiState.isLoading,
                    stringResource = CosmosStringResource.Id(
                        id = R.string.finance_manager_screen_add_category_floating_action_button_content_description,
                    ),
                ),
                handleEvent = { event ->
                    when (event) {
                        is SaveButtonEvent.OnClick -> {
                            handleUIEvent(EditCategoryScreenUIEvent.OnCtaButtonClick)
                        }
                    }
                },
            )
            CosmosNavigationBarsAndImeSpacer()
        }
    }
}
