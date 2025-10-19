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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.transactions.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.common.constants.TestTags.SCREEN_CONTENT_TRANSACTIONS
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.common.constants.TestTags.SCREEN_TRANSACTIONS
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.VerticalSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.button.MyFloatingActionButton
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.button.MyIconButton
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.navigationBarsSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.theme.BottomSheetExpandedShape
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.theme.BottomSheetShape
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.feature.areFiltersSelected
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.common.BottomSheetHandler
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.action_button.ActionButton
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.action_button.ActionButtonData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.action_button.ActionButtonEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.bottom_sheet.transaction_for.SelectTransactionForBottomSheet
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.bottom_sheet.transaction_for.SelectTransactionForBottomSheetData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.bottom_sheet.transaction_for.SelectTransactionForBottomSheetEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.bottom_sheet.transactions.TransactionsFilterBottomSheet
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.bottom_sheet.transactions.TransactionsMenuBottomSheet
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.bottom_sheet.transactions.TransactionsMenuBottomSheetData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.bottom_sheet.transactions.TransactionsMenuBottomSheetEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.bottom_sheet.transactions.TransactionsSortBottomSheet
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.listitem.transaction.TransactionListItem
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.listitem.transaction.TransactionListItemEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.text_field.search_bar.MySearchBarDataV2
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.text_field.search_bar.MySearchBarEventV2
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.text_field.search_bar.MySearchBarV2
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.top_app_bar.MySelectionModeTopAppBar
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.transactions.bottom_sheet.TransactionsScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.transactions.event.TransactionsScreenUIEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.transactions.snackbar.TransactionsScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.transactions.state.TransactionsScreenUIState
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.coroutines.launch

private object TransactionsScreenUIConstants {
    val searchSortAndFilterBarMinHeight = 48.dp
    val searchSortAndFilterBarPaddingBottom = 2.dp
    val searchSortAndFilterBarPaddingEnd = 8.dp
    val searchSortAndFilterBarPaddingStart = 8.dp
    val searchSortAndFilterBarPaddingTop = 6.dp
    val stickyHeaderTextPaddingBottom = 4.dp
    val stickyHeaderTextPaddingEnd = 16.dp
    val stickyHeaderTextPaddingStart = 16.dp
    val stickyHeaderTextPaddingTop = 8.dp
    val contentBottomPadding = 72.dp
}

@Composable
internal fun TransactionsScreenUI(
    uiState: TransactionsScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: TransactionsScreenUIEvent) -> Unit = {},
) {
    val duplicateTransactionSuccessfulSnackbarText = stringResource(
        id = R.string.finance_manager_screen_transactions_duplicate_transaction_successful,
    )

    BottomSheetHandler(
        isBottomSheetVisible = uiState.isBottomSheetVisible,
        screenBottomSheetType = uiState.screenBottomSheetType,
        coroutineScope = state.coroutineScope,
        modalBottomSheetState = state.modalBottomSheetState,
        keyboardController = state.keyboardController,
    )

    LaunchedEffect(
        key1 = uiState.screenSnackbarType,
        key2 = handleUIEvent,
    ) {
        when (uiState.screenSnackbarType) {
            TransactionsScreenSnackbarType.DuplicateTransactionSuccessful -> {
                launch {
                    val result = state.snackbarHostState.showSnackbar(
                        message = duplicateTransactionSuccessfulSnackbarText,
                    )
                    when (result) {
                        SnackbarResult.ActionPerformed -> {}
                        SnackbarResult.Dismissed -> {
                            handleUIEvent(TransactionsScreenUIEvent.OnSnackbarDismissed)
                        }
                    }
                }
            }

            TransactionsScreenSnackbarType.None -> {}
        }
    }

    BackHandler(
        enabled = uiState.isBackHandlerEnabled,
    ) {
        handleUIEvent(TransactionsScreenUIEvent.OnNavigationBackButtonClick)
    }

    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_TRANSACTIONS,
            )
            .fillMaxSize(),
        sheetContent = {
            when (uiState.screenBottomSheetType) {
                TransactionsScreenBottomSheetType.Filters -> {
                    TransactionsFilterBottomSheet(
                        expenseCategories = uiState.expenseCategories,
                        incomeCategories = uiState.incomeCategories,
                        investmentCategories = uiState.investmentCategories,
                        accounts = uiState.accounts,
                        transactionForValues = uiState.transactionForValues,
                        transactionTypes = uiState.transactionTypes,
                        defaultMinDate = uiState.oldestTransactionLocalDate,
                        defaultMaxDate = uiState.currentLocalDate,
                        selectedFilter = uiState.selectedFilter,
                        updateSelectedFilter = { updatedSelectedFilter ->
                            handleUIEvent(
                                TransactionsScreenUIEvent.OnSelectedFilterUpdated(
                                    updatedSelectedFilter = updatedSelectedFilter,
                                )
                            )
                        },
                        resetBottomSheetType = {
                            handleUIEvent(TransactionsScreenUIEvent.OnBottomSheetDismissed)
                        },
                    )
                }

                TransactionsScreenBottomSheetType.Menu -> {
                    TransactionsMenuBottomSheet(
                        data = TransactionsMenuBottomSheetData(
                            isDuplicateTransactionMenuOptionVisible = uiState.isDuplicateTransactionMenuOptionVisible,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is TransactionsMenuBottomSheetEvent.OnDuplicateTransactionClick -> {
                                    handleUIEvent(TransactionsScreenUIEvent.OnTransactionsMenuBottomSheet.DuplicateTransactionButtonClick)
                                }

                                is TransactionsMenuBottomSheetEvent.OnSelectAllTransactionsClick -> {
                                    handleUIEvent(TransactionsScreenUIEvent.OnTransactionsMenuBottomSheet.SelectAllTransactionsButtonClick)
                                }

                                is TransactionsMenuBottomSheetEvent.OnUpdateTransactionForClick -> {
                                    handleUIEvent(TransactionsScreenUIEvent.OnTransactionsMenuBottomSheet.UpdateTransactionForButtonClick)
                                }
                            }
                        },
                    )
                }

                TransactionsScreenBottomSheetType.None -> {
                    VerticalSpacer()
                }

                TransactionsScreenBottomSheetType.SelectTransactionFor -> {
                    SelectTransactionForBottomSheet(
                        data = SelectTransactionForBottomSheetData(
                            transactionForValues = uiState.transactionForValues,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is SelectTransactionForBottomSheetEvent.OnItemClick -> {
                                    handleUIEvent(
                                        TransactionsScreenUIEvent.OnSelectTransactionForBottomSheet.ItemClick(
                                            updatedTransactionForValues = event.selectedTransactionFor.id,
                                        )
                                    )
                                }
                            }
                        },
                    )
                }

                TransactionsScreenBottomSheetType.Sort -> {
                    TransactionsSortBottomSheet(
                        selectedSortOptionIndex = uiState.sortOptions.indexOf(
                            uiState.selectedSortOption
                        ),
                        sortOptions = uiState.sortOptions,
                        resetBottomSheetType = {
                            handleUIEvent(TransactionsScreenUIEvent.OnBottomSheetDismissed)
                        },
                        updateSelectedSortOption = { index ->
                            handleUIEvent(
                                TransactionsScreenUIEvent.OnSelectedSortOptionUpdated(
                                    updatedSelectedSortOption = uiState.sortOptions[index],
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
            is TransactionsScreenBottomSheetType.Menu,
            is TransactionsScreenBottomSheetType.None,
            is TransactionsScreenBottomSheetType.SelectTransactionFor,
            is TransactionsScreenBottomSheetType.Sort,
                -> {
                BottomSheetShape
            }

            is TransactionsScreenBottomSheetType.Filters -> {
                BottomSheetExpandedShape
            }
        },
        topBar = {
            if (uiState.isInSelectionMode) {
                MySelectionModeTopAppBar(
                    appBarActions = {
                        MyIconButton(
                            tint = FinanceManagerAppTheme.colorScheme.onBackground,
                            imageVector = MyIcons.MoreVert,
                            contentDescriptionStringResourceId = R.string.finance_manager_screen_transactions_selection_mode_appbar_menu_more_options,
                            onClick = {
                                handleUIEvent(TransactionsScreenUIEvent.OnSelectionModeTopAppBarMoreOptionsButtonClick)
                            },
                        )
                    },
                    onNavigationButtonClick = {
                        handleUIEvent(TransactionsScreenUIEvent.OnSelectionModeTopAppBarNavigationButtonClick)
                    },
                    title = {
                        MyText(
                            text = stringResource(
                                id = R.string.finance_manager_screen_transactions_selection_mode_appbar_title,
                                uiState.selectedTransactions.size,
                            ),
                            style = FinanceManagerAppTheme.typography.titleLarge
                                .copy(
                                    color = FinanceManagerAppTheme.colorScheme.onBackground,
                                ),
                        )
                    }
                )
            } else {
                MyTopAppBar(
                    titleTextStringResourceId = R.string.finance_manager_screen_transactions_appbar_title,
                    onNavigationButtonClick = {
                        handleUIEvent(TransactionsScreenUIEvent.OnTopAppBarNavigationButtonClick)
                    },
                )
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = uiState.isInSelectionMode.not(),
                enter = scaleIn(),
                exit = scaleOut(),
            ) {
                MyFloatingActionButton(
                    modifier = Modifier
                        .navigationBarsSpacer(),
                    iconImageVector = MyIcons.Add,
                    contentDescription = stringResource(
                        id = R.string.finance_manager_screen_transactions_floating_action_button_content_description,
                    ),
                    onClick = {
                        handleUIEvent(TransactionsScreenUIEvent.OnFloatingActionButtonClick)
                    },
                )
            }
        },
        onClick = state.focusManager::clearFocus,
        isModalBottomSheetVisible = uiState.isBottomSheetVisible,
        isBackHandlerEnabled = uiState.screenBottomSheetType != TransactionsScreenBottomSheetType.None,
        coroutineScope = state.coroutineScope,
        onBottomSheetDismiss = {
            handleUIEvent(TransactionsScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        Column(
            modifier = Modifier
                .testTag(
                    tag = SCREEN_CONTENT_TRANSACTIONS,
                )
                .fillMaxSize()
                .navigationBarLandscapeSpacer(),
        ) {
            SearchSortAndFilterBar(
                uiState = uiState,
                state = state,
                handleUIEvent = handleUIEvent,
            )
            LazyColumn(
                contentPadding = PaddingValues(
                    bottom = TransactionsScreenUIConstants.contentBottomPadding,
                ),
            ) {
                if (uiState.isLoading) {
                    items(
                        count = 10,
                    ) {
                        TransactionListItem(
                            data = TransactionListItemData(
                                isLoading = true,
                            ),
                        )
                    }
                } else {
                    uiState.transactionDetailsListItemViewData.forEach { (stickyHeaderText, listItemData) ->
                        if (stickyHeaderText.isNotBlank()) {
                            stickyHeader {
                                StickyHeaderText(
                                    text = stickyHeaderText,
                                )
                            }
                        }
                        itemsIndexed(
                            items = listItemData,
                            key = { _, listItem ->
                                listItem.transactionId
                            },
                        ) { _, listItem ->
                            val isSelected =
                                uiState.selectedTransactions.contains(
                                    element = listItem.transactionId,
                                )
                            TransactionListItem(
                                data = listItem
                                    .copy(
                                        isInSelectionMode = uiState.isInSelectionMode,
                                        isSelected = isSelected,
                                    ),
                                handleEvent = { event ->
                                    when (event) {
                                        is TransactionListItemEvent.OnClick -> {
                                            handleUIEvent(
                                                TransactionsScreenUIEvent.OnTransactionListItem.Click(
                                                    isInSelectionMode = uiState.isInSelectionMode,
                                                    isSelected = isSelected,
                                                    transactionId = listItem.transactionId,
                                                )
                                            )
                                        }

                                        is TransactionListItemEvent.OnDeleteButtonClick -> {}

                                        is TransactionListItemEvent.OnEditButtonClick -> {}

                                        is TransactionListItemEvent.OnLongClick -> {
                                            handleUIEvent(
                                                TransactionsScreenUIEvent.OnTransactionListItem.LongClick(
                                                    isInSelectionMode = uiState.isInSelectionMode,
                                                    isSelected = isSelected,
                                                    transactionId = listItem.transactionId,
                                                )
                                            )
                                        }

                                        is TransactionListItemEvent.OnRefundButtonClick -> {}
                                    }
                                },
                            )
                        }
                    }
                }
                item {
                    NavigationBarsAndImeSpacer()
                }
            }
        }
    }
}

@Composable
private fun SearchSortAndFilterBar(
    uiState: TransactionsScreenUIState,
    state: CommonScreenUIState,
    handleUIEvent: (uiEvent: TransactionsScreenUIEvent) -> Unit,
) {
    AnimatedVisibility(
        visible = uiState.isSearchSortAndFilterVisible,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(
                    minHeight = TransactionsScreenUIConstants.searchSortAndFilterBarMinHeight,
                )
                .padding(
                    bottom = TransactionsScreenUIConstants.searchSortAndFilterBarPaddingBottom,
                    end = TransactionsScreenUIConstants.searchSortAndFilterBarPaddingEnd,
                    start = TransactionsScreenUIConstants.searchSortAndFilterBarPaddingStart,
                    top = TransactionsScreenUIConstants.searchSortAndFilterBarPaddingTop,
                ),
        ) {
            Box(
                modifier = Modifier
                    .weight(
                        weight = 1F,
                    )
            ) {
                MySearchBarV2(
                    data = MySearchBarDataV2(
                        autoFocus = false,
                        isLoading = uiState.isLoading,
                        placeholderText = stringResource(
                            id = R.string.finance_manager_screen_transactions_searchbar_placeholder,
                        ),
                        searchTextFieldState = uiState.searchTextFieldState,
                    ),
                    handleEvent = { events ->
                        when (events) {
                            is MySearchBarEventV2.OnSearch -> {
                                state.focusManager.clearFocus()
                            }
                        }
                    },
                )
            }
            ActionButton(
                data = ActionButtonData(
                    isLoading = uiState.isLoading,
                    imageVector = MyIcons.SwapVert,
                    contentDescriptionStringResourceId = R.string.finance_manager_screen_transactions_sort_button_content_description,
                ),
                handleEvent = { event ->
                    when (event) {
                        is ActionButtonEvent.OnClick -> {
                            handleUIEvent(TransactionsScreenUIEvent.OnSortActionButtonClick)
                        }
                    }
                },
            )
            ActionButton(
                data = ActionButtonData(
                    isIndicatorVisible = uiState.selectedFilter.areFiltersSelected(),
                    isLoading = uiState.isLoading,
                    imageVector = MyIcons.FilterAlt,
                    contentDescriptionStringResourceId = R.string.finance_manager_screen_transactions_filter_button_content_description,
                ),
                handleEvent = { event ->
                    when (event) {
                        is ActionButtonEvent.OnClick -> {
                            handleUIEvent(TransactionsScreenUIEvent.OnFilterActionButtonClick)
                        }
                    }
                },
            )
        }
    }
}

@Composable
private fun StickyHeaderText(
    text: String,
) {
    MyText(
        modifier = Modifier
            .background(
                color = FinanceManagerAppTheme.colorScheme.background,
            )
            .fillMaxWidth()
            .padding(
                bottom = TransactionsScreenUIConstants.stickyHeaderTextPaddingBottom,
                end = TransactionsScreenUIConstants.stickyHeaderTextPaddingEnd,
                start = TransactionsScreenUIConstants.stickyHeaderTextPaddingStart,
                top = TransactionsScreenUIConstants.stickyHeaderTextPaddingTop,
            ),
        text = text,
        style = FinanceManagerAppTheme.typography.headlineSmall
            .copy(
                color = FinanceManagerAppTheme.colorScheme.onBackground,
            ),
    )
}
