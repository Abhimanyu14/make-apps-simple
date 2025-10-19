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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.accounts.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.common.constants.TestTags.SCREEN_ACCOUNTS
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.common.constants.TestTags.SCREEN_CONTENT_ACCOUNTS
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.VerticalSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.button.MyFloatingActionButton
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.navigationBarsSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.bottom_sheet.account.AccountsDeleteConfirmationBottomSheet
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.bottom_sheet.account.AccountsDeleteConfirmationBottomSheetEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.bottom_sheet.account.AccountsMenuBottomSheet
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.bottom_sheet.account.AccountsSetAsDefaultConfirmationBottomSheet
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.bottom_sheet.account.AccountsSetAsDefaultConfirmationBottomSheetEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.accounts.AccountsListItemContent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.accounts.AccountsListItemContentData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.accounts.AccountsListItemContentEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.accounts.AccountsListItemContentLoadingUI
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.accounts.AccountsListItemHeader
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.accounts.AccountsListItemHeaderData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.accounts.AccountsListItemHeaderLoadingUI
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.total_balance_card.TotalBalanceCard
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.total_balance_card.TotalBalanceCardData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.accounts.bottom_sheet.AccountsScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.accounts.event.AccountsScreenUIEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.accounts.accounts.state.AccountsScreenUIState
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Composable
internal fun AccountsScreenUI(
    uiState: AccountsScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: AccountsScreenUIEvent) -> Unit = {},
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
                tag = SCREEN_ACCOUNTS,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                is AccountsScreenBottomSheetType.DeleteConfirmation -> {
                    AccountsDeleteConfirmationBottomSheet(
                        handleEvent = { event ->
                            when (event) {
                                AccountsDeleteConfirmationBottomSheetEvent.OnNegativeButtonClick -> {
                                    handleUIEvent(AccountsScreenUIEvent.OnAccountsDeleteConfirmationBottomSheet.NegativeButtonClick)
                                }

                                AccountsDeleteConfirmationBottomSheetEvent.OnPositiveButtonClick -> {
                                    handleUIEvent(AccountsScreenUIEvent.OnAccountsDeleteConfirmationBottomSheet.PositiveButtonClick)
                                }
                            }
                        },
                    )
                }

                is AccountsScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }

                is AccountsScreenBottomSheetType.SetAsDefaultConfirmation -> {
                    AccountsSetAsDefaultConfirmationBottomSheet(
                        handleEvent = { event ->
                            when (event) {
                                AccountsSetAsDefaultConfirmationBottomSheetEvent.OnNegativeButtonClick -> {
                                    handleUIEvent(AccountsScreenUIEvent.OnAccountsSetAsDefaultConfirmationBottomSheet.NegativeButtonClick)
                                }

                                AccountsSetAsDefaultConfirmationBottomSheetEvent.OnPositiveButtonClick -> {
                                    handleUIEvent(AccountsScreenUIEvent.OnAccountsSetAsDefaultConfirmationBottomSheet.PositiveButtonClick)
                                }
                            }
                        },
                    )
                }

                is AccountsScreenBottomSheetType.Menu -> {
                    val bottomSheetData = uiState.screenBottomSheetType

                    AccountsMenuBottomSheet(
                        isDeleteVisible = bottomSheetData.isDeleteVisible,
                        isEditVisible = bottomSheetData.isEditVisible,
                        isSetAsDefaultVisible = bottomSheetData.isSetAsDefaultVisible,
                        onDeleteClick = {
                            handleUIEvent(
                                AccountsScreenUIEvent.OnAccountsMenuBottomSheet.DeleteButtonClick(
                                    accountId = bottomSheetData.accountId,
                                )
                            )
                        },
                        onEditClick = {
                            handleUIEvent(
                                AccountsScreenUIEvent.OnAccountsMenuBottomSheet.EditButtonClick(
                                    accountId = bottomSheetData.accountId,
                                )
                            )
                        },
                        onSetAsDefaultClick = {
                            handleUIEvent(
                                AccountsScreenUIEvent.OnAccountsMenuBottomSheet.SetAsDefaultButtonClick(
                                    accountId = bottomSheetData.accountId,
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
                titleTextStringResourceId = R.string.finance_manager_screen_accounts_appbar_title,
                onNavigationButtonClick = {
                    handleUIEvent(AccountsScreenUIEvent.OnTopAppBarNavigationButtonClick)
                },
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                modifier = Modifier
                    .navigationBarsSpacer(),
                iconImageVector = MyIcons.Add,
                contentDescription = stringResource(
                    id = R.string.finance_manager_screen_accounts_floating_action_button_content_description,
                ),
                onClick = {
                    handleUIEvent(AccountsScreenUIEvent.OnFloatingActionButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.isBottomSheetVisible,
        isBackHandlerEnabled = uiState.screenBottomSheetType != AccountsScreenBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBottomSheetDismiss = {
            handleUIEvent(AccountsScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_ACCOUNTS,
                )
                .navigationBarLandscapeSpacer(),
            contentPadding = PaddingValues(
                bottom = 80.dp,
            ),
        ) {
            item {
                TotalBalanceCard(
                    data = TotalBalanceCardData(
                        isBalanceVisible = true,
                        isLoading = uiState.isLoading,
                        totalBalanceAmount = uiState.accountsTotalBalanceAmountValue,
                        totalMinimumBalanceAmount = uiState.allAccountsTotalMinimumBalanceAmountValue,
                    ),
                )
            }
            accountsList(
                uiState = uiState,
                handleUIEvent = handleUIEvent,
            )
            item {
                NavigationBarsAndImeSpacer()
            }
        }
    }
}

private fun LazyListScope.accountsList(
    uiState: AccountsScreenUIState,
    handleUIEvent: (uiEvent: AccountsScreenUIEvent) -> Unit,
) {
    if (uiState.isLoading) {
        accountsListLoadingUI()
    } else {
        accountsListUI(
            uiState = uiState,
            handleUIEvent = handleUIEvent,
        )
    }
}

private fun LazyListScope.accountsListLoadingUI() {
    repeat(3) {
        item {
            AccountsListItemHeaderLoadingUI()
        }
        repeat(5) {
            item {
                AccountsListItemContentLoadingUI()
            }
        }
        item {
            VerticalSpacer(
                height = 16.dp,
            )
        }
    }
}

private fun LazyListScope.accountsListUI(
    uiState: AccountsScreenUIState,
    handleUIEvent: (uiEvent: AccountsScreenUIEvent) -> Unit,
) {
    itemsIndexed(
        items = uiState.accountsListItemDataList,
        key = { _, listItem ->
            listItem.hashCode()
        },
    ) { _, listItem ->
        when (listItem) {
            is AccountsListItemContentData -> {
                AccountsListItemContent(
                    data = listItem,
                    handleEvent = { event ->
                        when (event) {
                            is AccountsListItemContentEvent.OnClick -> {
                                handleUIEvent(
                                    AccountsScreenUIEvent.OnAccountsListItemContent.Click(
                                        isDeleteEnabled = listItem.isDeleteEnabled,
                                        isDefault = listItem.isDefault,
                                        accountId = listItem.accountId,
                                    )
                                )
                            }
                        }
                    },
                )
            }

            is AccountsListItemHeaderData -> {
                AccountsListItemHeader(
                    data = listItem,
                )
            }
        }
    }
}
