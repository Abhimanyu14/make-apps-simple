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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.home.home.view_model

import android.net.Uri
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
import com.makeappssimple.abhimanyu.common.core.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.common.core.extensions.toZonedDateTime
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.chart.compose_pie.data.PieChartData
import com.makeappssimple.abhimanyu.finance.manager.android.core.chart.compose_pie.data.PieChartItemData
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAllAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAllAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.common.ShouldShowBackupCardUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetRecentTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetTransactionByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetTransactionsBetweenTimestampsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.theme.MyColor
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Amount
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.toNonSignedString
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.transaction.toTransactionListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.overview_card.OverviewCardAction
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.overview_card.OverviewCardViewModelData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.overview_card.OverviewTabOption
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.overview_card.orDefault
import com.makeappssimple.abhimanyu.finance.manager.android.feature.home.home.state.HomeScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.home.home.state.HomeScreenUIStateEvents
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import java.time.Instant
import kotlin.math.abs

private const val DEFAULT_OVERVIEW_TAB_SELECTION = 1

@KoinViewModel
internal class HomeScreenViewModel(
    getAllAccountsTotalBalanceAmountValueUseCase: GetAllAccountsTotalBalanceAmountValueUseCase,
    getAllAccountsTotalMinimumBalanceAmountValueUseCase: GetAllAccountsTotalMinimumBalanceAmountValueUseCase,
    navigationKit: NavigationKit,
    screenUIStateDelegate: ScreenUIStateDelegate,
    shouldShowBackupCardUseCase: ShouldShowBackupCardUseCase,
    private val backupDataUseCase: BackupDataUseCase,
    private val coroutineScope: CoroutineScope,
    private val dateTimeKit: DateTimeKit,
    private val getRecentTransactionDataFlowUseCase: GetRecentTransactionDataFlowUseCase,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val getTransactionsBetweenTimestampsUseCase: GetTransactionsBetweenTimestampsUseCase,
    internal val logKit: LogKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    logKit = logKit,
    navigationKit = navigationKit,
    screenUIStateDelegate = screenUIStateDelegate,
) {
    // region initial data
    private val isBackupCardVisible: Flow<Boolean> =
        shouldShowBackupCardUseCase()
    private val accountsTotalBalanceAmountValue: Flow<Long> =
        getAllAccountsTotalBalanceAmountValueUseCase()
    private val allAccountsTotalMinimumBalanceAmountValue: Flow<Long> =
        getAllAccountsTotalMinimumBalanceAmountValueUseCase()
    // endregion

    // region UI state
    private var isBalanceVisible: Boolean = false
    private var homeListItemViewData: ImmutableList<TransactionListItemData> =
        persistentListOf()
    private var overviewTabSelectionIndex: Int = DEFAULT_OVERVIEW_TAB_SELECTION
    private var selectedTimestamp: Long = dateTimeKit.getCurrentTimeMillis()
    private var overviewCardData: OverviewCardViewModelData? = null
    // endregion

    // region uiStateAndStateEvents
    private val _uiState: MutableStateFlow<HomeScreenUIState> =
        MutableStateFlow(
            value = HomeScreenUIState(),
        )
    internal val uiState: StateFlow<HomeScreenUIState> = _uiState.asStateFlow()
    internal val uiStateEvents: HomeScreenUIStateEvents =
        HomeScreenUIStateEvents(
            handleOverviewCardAction = ::handleOverviewCardAction,
            navigateToAccountsScreen = ::navigateToAccountsScreen,
            navigateToAddTransactionScreen = ::navigateToAddTransactionScreen,
            navigateToAnalysisScreen = ::navigateToAnalysisScreen,
            navigateToSettingsScreen = ::navigateToSettingsScreen,
            navigateToTransactionsScreen = ::navigateToTransactionsScreen,
            navigateToViewTransactionScreen = ::navigateToViewTransactionScreen,
            updateIsBalanceVisible = ::updateIsBalanceVisible,
            updateOverviewTabSelectionIndex = ::updateOverviewTabSelectionIndex,
        )
    // endregion

    // region updateUiStateAndStateEvents
    override fun updateUiStateAndStateEvents() {
        val totalIncomeAmount = Amount(
            value = overviewCardData?.income?.toLong().orZero(),
        )
        val totalExpenseAmount = Amount(
            value = overviewCardData?.expense?.toLong().orZero(),
        )
        coroutineScope.launch {
            updateOverviewCardData()
            _uiState.update {
                HomeScreenUIState(
                    isBackupCardVisible = isBackupCardVisible.first(),
                    isBalanceVisible = isBalanceVisible,
                    isLoading = isLoading,
                    isRecentTransactionsTrailingTextVisible = homeListItemViewData
                        .isNotEmpty(),
                    overviewTabSelectionIndex = overviewTabSelectionIndex.orZero(),
                    transactionListItemDataList = homeListItemViewData,
                    accountsTotalBalanceAmountValue = accountsTotalBalanceAmountValue.first()
                        .orZero(),
                    allAccountsTotalMinimumBalanceAmountValue = allAccountsTotalMinimumBalanceAmountValue.first()
                        .orZero(),
                    overviewCardData = overviewCardData.orDefault(),
                    pieChartData = PieChartData(
                        items = persistentListOf(
                            PieChartItemData(
                                value = overviewCardData?.income.orZero(),
                                text = "Income : $totalIncomeAmount", // TODO(Abhi): Move to string resources
                                color = MyColor.TERTIARY,
                            ),
                            PieChartItemData(
                                value = overviewCardData?.expense.orZero(),
                                text = "Expense : ${totalExpenseAmount.toNonSignedString()}", // TODO(Abhi): Move to string resources
                                color = MyColor.ERROR,
                            ),
                        ),
                    ),
                )
            }
        }
    }
    // endregion

    // region observeData
    override fun observeData() {
        observeForHomeListItemViewData()
    }
    // endregion

    // region state events
    private fun handleOverviewCardAction(
        overviewCardAction: OverviewCardAction,
        shouldRefresh: Boolean = true,
    ): Job {
        val overviewTabOption =
            OverviewTabOption.entries[overviewTabSelectionIndex]
        when (overviewCardAction) {
            OverviewCardAction.NEXT -> {
                when (overviewTabOption) {
                    OverviewTabOption.DAY -> {
                        selectedTimestamp =
                            Instant.ofEpochMilli(selectedTimestamp)
                                .toZonedDateTime()
                                .plusDays(1)
                                .toEpochMilli()
                    }

                    OverviewTabOption.MONTH -> {
                        selectedTimestamp =
                            Instant.ofEpochMilli(selectedTimestamp)
                                .toZonedDateTime()
                                .plusMonths(1)
                                .toEpochMilli()
                    }

                    OverviewTabOption.YEAR -> {
                        selectedTimestamp =
                            Instant.ofEpochMilli(selectedTimestamp)
                                .toZonedDateTime()
                                .plusYears(1)
                                .toEpochMilli()
                    }
                }
            }

            OverviewCardAction.PREV -> {
                when (overviewTabOption) {
                    OverviewTabOption.DAY -> {
                        selectedTimestamp =
                            Instant.ofEpochMilli(selectedTimestamp)
                                .toZonedDateTime()
                                .minusDays(1)
                                .toEpochMilli()
                    }

                    OverviewTabOption.MONTH -> {
                        selectedTimestamp =
                            Instant.ofEpochMilli(selectedTimestamp)
                                .toZonedDateTime()
                                .minusMonths(1)
                                .toEpochMilli()
                    }

                    OverviewTabOption.YEAR -> {
                        selectedTimestamp =
                            Instant.ofEpochMilli(selectedTimestamp)
                                .toZonedDateTime()
                                .minusYears(1)
                                .toEpochMilli()
                    }
                }
            }
        }
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateIsBalanceVisible(
        updatedIsBalanceVisible: Boolean,
        shouldRefresh: Boolean = true,
    ): Job {
        isBalanceVisible = updatedIsBalanceVisible
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateOverviewTabSelectionIndex(
        updatedOverviewTabSelectionIndex: Int,
        shouldRefresh: Boolean = true,
    ): Job {
        overviewTabSelectionIndex = updatedOverviewTabSelectionIndex
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }
    // endregion

    // region backupDataToDocument
    internal fun backupDataToDocument(
        uri: Uri,
    ) {
        coroutineScope.launch {
            // TODO(Abhi): Change this logic to ensure that this coroutine lives till the backup is completed.
            launch {
                backupDataUseCase(
                    uri = uri,
                )
            }
            navigateUp()
        }
    }
    // endregion

    // region observeForHomeListItemViewData
    private fun observeForHomeListItemViewData() {
        coroutineScope.launch {
            getRecentTransactionDataFlowUseCase().collectLatest { transactionDataList ->
                startLoading()
                homeListItemViewData = transactionDataList
                    .map { transactionData: TransactionData ->
                        transactionData.toTransactionListItemData(
                            getReadableDateAndTime = dateTimeKit::getReadableDateAndTime,
                        )
                    }
                completeLoading()
            }
        }
    }
    // endregion

    // region updateOverviewCardData
    private suspend fun updateOverviewCardData() {
        val overviewTabOption =
            OverviewTabOption.entries[overviewTabSelectionIndex]
        val transactionsInSelectedTimeRange =
            getTransactionsInSelectedTimeRange(
                overviewTabOption = overviewTabOption,
                timestamp = selectedTimestamp,
            )

        overviewCardData = OverviewCardViewModelData(
            income = getIncomeAmount(
                transactionsInSelectedTimeRange = transactionsInSelectedTimeRange,
            ),
            expense = getExpenseAmount(
                transactionsInSelectedTimeRange = transactionsInSelectedTimeRange,
            ),
            title = getTitle(
                overviewTabOption = overviewTabOption,
                timestamp = selectedTimestamp
            ),
        )
    }

    private suspend fun getTransactionsInSelectedTimeRange(
        overviewTabOption: OverviewTabOption,
        timestamp: Long,
    ): ImmutableList<Transaction> {
        return getTransactionsBetweenTimestampsUseCase(
            startingTimestamp = when (overviewTabOption) {
                OverviewTabOption.DAY -> {
                    dateTimeKit.getStartOfDayTimestamp(
                        timestamp = timestamp,
                    )
                }

                OverviewTabOption.MONTH -> {
                    dateTimeKit.getStartOfMonthTimestamp(
                        timestamp = timestamp,
                    )
                }

                OverviewTabOption.YEAR -> {
                    dateTimeKit.getStartOfYearTimestamp(
                        timestamp = timestamp,
                    )
                }
            },
            endingTimestamp = when (overviewTabOption) {
                OverviewTabOption.DAY -> {
                    dateTimeKit.getEndOfDayTimestamp(
                        timestamp = timestamp,
                    )
                }

                OverviewTabOption.MONTH -> {
                    dateTimeKit.getEndOfMonthTimestamp(
                        timestamp = timestamp,
                    )
                }

                OverviewTabOption.YEAR -> {
                    dateTimeKit.getEndOfYearTimestamp(
                        timestamp = timestamp,
                    )
                }
            },
        )
    }

    private fun getIncomeAmount(
        transactionsInSelectedTimeRange: ImmutableList<Transaction>,
    ): Float {
        return transactionsInSelectedTimeRange.filter {
            it.transactionType == TransactionType.INCOME
        }.sumOf {
            it.amount.value
        }.toFloat()
    }

    private suspend fun getExpenseAmount(
        transactionsInSelectedTimeRange: ImmutableList<Transaction>,
    ): Float {
        val expenseTransactions = transactionsInSelectedTimeRange.filter {
            it.transactionType == TransactionType.EXPENSE
        }
        val expenseTransactionsWithRefund = mutableListOf<Transaction>()
        expenseTransactions.forEach { expenseTransaction ->
            expenseTransactionsWithRefund.add(expenseTransaction)
            expenseTransaction.refundTransactionIds?.let { refundTransactionIds ->
                refundTransactionIds.forEach { id ->
                    getTransactionByIdUseCase(id)?.let {
                        expenseTransactionsWithRefund.add(it)
                    }
                }
            }
        }
        val expenseAmount = expenseTransactionsWithRefund.sumOf { transaction ->
            if (transaction.transactionType == TransactionType.EXPENSE) {
                abs(
                    n = transaction.amount.value,
                )
            } else {
                -abs(
                    n = transaction.amount.value,
                )
            }
        }.toFloat()
        return expenseAmount
    }

    private fun getTitle(
        overviewTabOption: OverviewTabOption,
        timestamp: Long,
    ): String {
        return when (overviewTabOption) {
            OverviewTabOption.DAY -> {
                dateTimeKit.getFormattedDate(timestamp).uppercase()
            }

            OverviewTabOption.MONTH -> {
                dateTimeKit.getFormattedMonth(timestamp).uppercase()
            }

            OverviewTabOption.YEAR -> {
                dateTimeKit.getFormattedYear(timestamp).uppercase()
            }
        }
    }
    // endregion
}
