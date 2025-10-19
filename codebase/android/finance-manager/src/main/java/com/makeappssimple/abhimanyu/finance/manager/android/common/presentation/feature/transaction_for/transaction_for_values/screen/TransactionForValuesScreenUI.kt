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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.transaction_for_values.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.constants.TestTags.SCREEN_CONTENT_TRANSACTION_FOR_VALUES
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.constants.TestTags.SCREEN_TRANSACTION_FOR_VALUES
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.VerticalSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.button.MyFloatingActionButton
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.navigationBarsSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.transaction_for_values.bottom_sheet.TransactionForValuesScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.transaction_for_values.event.TransactionForValuesScreenUIEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.transaction_for_values.state.TransactionForValuesScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.bottom_sheet.transaction_for.TransactionForValuesDeleteConfirmationBottomSheet
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.bottom_sheet.transaction_for.TransactionForValuesDeleteConfirmationBottomSheetEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.bottom_sheet.transaction_for.TransactionForValuesMenuBottomSheet
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.transaction_for.TransactionForListItem
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.transaction_for.TransactionForListItemEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Composable
internal fun TransactionForValuesScreenUI(
    uiState: TransactionForValuesScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: TransactionForValuesScreenUIEvent) -> Unit = {},
) {
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
                tag = SCREEN_TRANSACTION_FOR_VALUES,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                is TransactionForValuesScreenBottomSheetType.DeleteConfirmation -> {
                    TransactionForValuesDeleteConfirmationBottomSheet(
                        handleEvent = { event ->
                            when (event) {
                                TransactionForValuesDeleteConfirmationBottomSheetEvent.OnNegativeButtonClick -> {
                                    handleUIEvent(
                                        TransactionForValuesScreenUIEvent.OnTransactionForValuesDeleteConfirmationBottomSheet.NegativeButtonClick
                                    )
                                }

                                TransactionForValuesDeleteConfirmationBottomSheetEvent.OnPositiveButtonClick -> {
                                    handleUIEvent(
                                        TransactionForValuesScreenUIEvent.OnTransactionForValuesDeleteConfirmationBottomSheet.PositiveButtonClick
                                    )
                                }
                            }
                        },
                    )
                }

                is TransactionForValuesScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }

                is TransactionForValuesScreenBottomSheetType.Menu -> {
                    val bottomSheetData = uiState.screenBottomSheetType
                    TransactionForValuesMenuBottomSheet(
                        isDeleteVisible = bottomSheetData.isDeleteVisible,
                        onEditClick = {
                            handleUIEvent(
                                TransactionForValuesScreenUIEvent.OnTransactionForValuesMenuBottomSheet.EditButtonClick(
                                    transactionForId = bottomSheetData.transactionForId,
                                )
                            )
                        },
                        onDeleteClick = {
                            handleUIEvent(
                                TransactionForValuesScreenUIEvent.OnTransactionForValuesMenuBottomSheet.DeleteButtonClick(
                                    transactionForId = bottomSheetData.transactionForId,
                                )
                            )
                        },
                    )
                }
            }
        },
        sheetState = state.modalBottomSheetState,
        snackbarHostState = state.snackbarHostState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.finance_manager_screen_transaction_for_values_appbar_title,
                onNavigationButtonClick = {
                    handleUIEvent(TransactionForValuesScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                modifier = Modifier
                    .navigationBarsSpacer(),
                iconImageVector = MyIcons.Add,
                contentDescription = stringResource(
                    id = R.string.finance_manager_screen_transaction_for_values_floating_action_button_content_description,
                ),
                onClick = {
                    handleUIEvent(TransactionForValuesScreenUIEvent.OnFloatingActionButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.isBottomSheetVisible,
        isBackHandlerEnabled = uiState.screenBottomSheetType != TransactionForValuesScreenBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBottomSheetDismiss = {
            handleUIEvent(TransactionForValuesScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_TRANSACTION_FOR_VALUES,
                )
                .navigationBarLandscapeSpacer(),
        ) {
            items(
                items = uiState.transactionForListItemDataList,
                key = { listItem ->
                    listItem.transactionForId
                },
            ) { listItem ->
                TransactionForListItem(
                    data = listItem,
                    handleEvent = { event ->
                        when (event) {
                            is TransactionForListItemEvent.OnClick -> {
                                handleUIEvent(
                                    TransactionForValuesScreenUIEvent.OnTransactionForListItem.Click(
                                        isDeleteVisible = listItem.isDeleteBottomSheetMenuItemVisible,
                                        transactionForId = listItem.transactionForId,
                                    )
                                )
                            }

                            is TransactionForListItemEvent.OnMoreOptionsIconButtonClick -> {
                                handleUIEvent(
                                    TransactionForValuesScreenUIEvent.OnTransactionForListItem.MoreOptionsIconButtonClick(
                                        isDeleteVisible = listItem.isDeleteBottomSheetMenuItemVisible,
                                        transactionForId = listItem.transactionForId
                                    )
                                )
                            }
                        }
                    },
                )
            }
            item {
                NavigationBarsAndImeSpacer()
            }
        }
    }
}
