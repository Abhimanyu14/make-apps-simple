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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.home.home.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Amount
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.chart.compose_pie.data.PieChartData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.chart.compose_pie.data.PieChartItemData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.constants.TestTags.SCREEN_CONTENT_HOME
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.constants.TestTags.SCREEN_HOME
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.button.MyFloatingActionButton
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.button.MyIconButton
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.navigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.navigationBarsSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.theme.MyColor
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.home.home.event.HomeScreenUIEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.home.home.state.HomeScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.model.toNonSignedString
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.backup_card.BackupCard
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.backup_card.BackupCardData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.backup_card.BackupCardEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.home_recent_transactions.HomeRecentTransactions
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.home_recent_transactions.HomeRecentTransactionsData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.home_recent_transactions.HomeRecentTransactionsEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.transaction.TransactionListItem
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.transaction.TransactionListItemEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.overview_card.OverviewCard
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.overview_card.OverviewCardData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.overview_card.OverviewCardEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.collections.immutable.persistentListOf

private val bottomContentPadding = 100.dp

@Composable
internal fun HomeScreenUI(
    uiState: HomeScreenUIState,
    state: CommonScreenUIState = rememberCommonScreenUIState(),
    handleUIEvent: (uiEvent: HomeScreenUIEvent) -> Unit = {},
) {
    MyScaffold(
        modifier = Modifier
            .testTag(
                tag = SCREEN_HOME,
            )
            .fillMaxSize(),
        sheetState = state.modalBottomSheetState,
        snackbarHostState = state.snackbarHostState,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.finance_manager_screen_home_appbar_title,
                appBarActions = {
                    MyIconButton(
                        tint = FinanceManagerAppTheme.colorScheme.onBackground,
                        imageVector = MyIcons.Settings,
                        contentDescriptionStringResourceId = R.string.finance_manager_screen_home_appbar_settings,
                        onClick = {
                            handleUIEvent(HomeScreenUIEvent.OnTopAppBarSettingsButtonClick)
                        },
                    )
                },
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                modifier = Modifier
                    .navigationBarsSpacer(),
                iconImageVector = MyIcons.Add,
                contentDescription = stringResource(
                    id = R.string.finance_manager_screen_home_floating_action_button_content_description,
                ),
                onClick = {
                    handleUIEvent(HomeScreenUIEvent.OnFloatingActionButtonClick)
                },
            )
        },
        onClick = state.focusManager::clearFocus,
        contentTestTag = SCREEN_CONTENT_HOME,
        coroutineScope = state.coroutineScope,
        onBottomSheetDismiss = {
            handleUIEvent(HomeScreenUIEvent.OnNavigationBackButtonClick)
        },
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(
                    state = rememberScrollState(),
                )
                .navigationBarLandscapeSpacer()
                .padding(
                    bottom = bottomContentPadding,
                ),
        ) {
            // TODO(Abhi): View Balance Feature
            /*
            TotalBalanceCard(
                data = TotalBalanceCardData(
                    isBalanceVisible = uiState.isBalanceVisible,
                    isClickable = true,
                    isLoading = uiState.isLoading,
                    totalBalanceAmount = uiState.accountsTotalBalanceAmountValue,
                    totalMinimumBalanceAmount = uiState.allAccountsTotalMinimumBalanceAmountValue,
                ),
                handleEvent = { event ->
                    when (event) {
                        is TotalBalanceCardEvent.OnClick -> {
                            handleUIEvent(HomeScreenUIEvent.OnTotalBalanceCardClick)
                        }

                        is TotalBalanceCardEvent.OnViewBalanceClick -> {
                            handleUIEvent(HomeScreenUIEvent.OnTotalBalanceCardViewBalanceClick)
                        }
                    }
                },
            )
            */
            AnimatedVisibility(
                visible = uiState.isBackupCardVisible,
            ) {
                BackupCard(
                    data = BackupCardData(
                        isLoading = uiState.isLoading,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is BackupCardEvent.OnClick -> {
                                handleUIEvent(HomeScreenUIEvent.OnBackupCardClick)
                            }
                        }
                    },
                )
            }
            OverviewCard(
                data = OverviewCardData(
                    isLoading = uiState.isLoading,
                    overviewTabSelectionIndex = uiState.overviewTabSelectionIndex,
                    pieChartData = PieChartData(
                        items = persistentListOf(
                            PieChartItemData(
                                value = uiState.overviewCardData.income,
                                text = stringResource(
                                    id = R.string.finance_manager_screen_home_overview_card_income,
                                    Amount(
                                        value = uiState.overviewCardData.income.toLong(),
                                    ),
                                ),
                                color = MyColor.TERTIARY,
                            ),
                            PieChartItemData(
                                value = uiState.overviewCardData.expense,
                                text = stringResource(
                                    id = R.string.finance_manager_screen_home_overview_card_expense,
                                    Amount(
                                        value = uiState.overviewCardData.expense.toLong(),
                                    ).toNonSignedString(),
                                ),
                                color = MyColor.ERROR,
                            ),
                        ),
                    ),
                    title = uiState.overviewCardData.title,
                ),
                handleEvent = { event ->
                    when (event) {
                        is OverviewCardEvent.OnClick -> {
                            handleUIEvent(HomeScreenUIEvent.OnOverviewCard.Click)
                        }

                        is OverviewCardEvent.OnOverviewCardAction -> {
                            handleUIEvent(
                                HomeScreenUIEvent.OnOverviewCard.Action(
                                    overviewCardAction = event.overviewCardAction,
                                )
                            )
                        }

                        is OverviewCardEvent.OnOverviewTabClick -> {
                            handleUIEvent(
                                HomeScreenUIEvent.OnOverviewCard.TabClick(
                                    index = event.index,
                                )
                            )
                        }
                    }
                },
            )
            HomeRecentTransactions(
                data = HomeRecentTransactionsData(
                    isTrailingTextVisible = uiState.isRecentTransactionsTrailingTextVisible,
                ),
                handleEvent = { event ->
                    when (event) {
                        is HomeRecentTransactionsEvent.OnClick -> {
                            if (uiState.isRecentTransactionsTrailingTextVisible) {
                                handleUIEvent(HomeScreenUIEvent.OnHomeRecentTransactionsClick)
                            }
                        }
                    }
                },
            )
            uiState.transactionListItemDataList.map { listItem ->
                TransactionListItem(
                    data = listItem,
                    handleEvent = { event ->
                        when (event) {
                            is TransactionListItemEvent.OnClick -> {
                                handleUIEvent(
                                    HomeScreenUIEvent.OnTransactionListItemClick(
                                        transactionId = listItem.transactionId,
                                    )
                                )
                            }

                            is TransactionListItemEvent.OnDeleteButtonClick -> {}

                            is TransactionListItemEvent.OnEditButtonClick -> {}

                            is TransactionListItemEvent.OnLongClick -> {}

                            is TransactionListItemEvent.OnRefundButtonClick -> {}
                        }
                    },
                )
            }
        }
    }
}
