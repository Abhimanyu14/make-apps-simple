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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.feature.home.home.screen

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
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button.CosmosCircularFloatingActionButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button.CosmosIconButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.chart.compose_pie.data.PieChartData
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.chart.compose_pie.data.PieChartItemData
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.spacer.cosmosNavigationBarLandscapeSpacer
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.spacer.cosmosNavigationBarsSpacer
import com.makeappssimple.abhimanyu.cosmos.design.system.android.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosColor
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Amount
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.constants.TestTags.SCREEN_CONTENT_HOME
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.constants.TestTags.SCREEN_HOME
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.home.home.event.HomeScreenUIEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.home.home.state.HomeScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.model.toDefaultString
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.model.toUnsignedString
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.common.state.CommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.common.state.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.backup_card.BackupCard
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.backup_card.BackupCardData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.backup_card.BackupCardEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.home_recent_transactions.HomeRecentTransactions
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.home_recent_transactions.HomeRecentTransactionsData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.home_recent_transactions.HomeRecentTransactionsEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.listitem.transaction.TransactionListItem
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.listitem.transaction.TransactionListItemEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.overview_card.OverviewCard
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.overview_card.OverviewCardData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.overview_card.OverviewCardEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.top_app_bar.MyTopAppBar
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
                    CosmosIconButton(
                        onClickLabelStringResource = CosmosStringResource.Id(
                            id = R.string.finance_manager_screen_home_appbar_settings,
                        ),
                        onClick = {
                            handleUIEvent(HomeScreenUIEvent.OnTopAppBarSettingsButtonClick)
                        },
                    ) {
                        CosmosIcon(
                            iconResource = CosmosIcons.Settings,
                            tint = CosmosAppTheme.colorScheme.onBackground,
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            CosmosCircularFloatingActionButton(
                modifier = Modifier
                    .cosmosNavigationBarsSpacer(),
                iconResource = CosmosIcons.Add,
                contentDescriptionStringResource = CosmosStringResource.Id(
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
                .cosmosNavigationBarLandscapeSpacer()
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
                                stringResource = CosmosStringResource.Id(
                                    id = R.string.finance_manager_screen_home_overview_card_income,
                                    args = listOf(
                                        Amount(
                                            value = uiState.overviewCardData.income.toLong(),
                                        ).toDefaultString(),
                                    ),
                                ),
                                color = CosmosColor.TERTIARY,
                            ),
                            PieChartItemData(
                                value = uiState.overviewCardData.expense,
                                stringResource = CosmosStringResource.Id(
                                    id = R.string.finance_manager_screen_home_overview_card_expense,
                                    args = listOf(
                                        Amount(
                                            value = uiState.overviewCardData.expense.toLong(),
                                        ).toUnsignedString(),
                                    ),
                                ),
                                color = CosmosColor.ERROR,
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
