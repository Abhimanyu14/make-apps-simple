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
import com.makeappssimple.abhimanyu.common.core.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
import com.makeappssimple.abhimanyu.common.core.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.common.core.extensions.toZonedDateTime
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.chart.compose_pie.data.PieChartData
import com.makeappssimple.abhimanyu.finance.manager.android.core.chart.compose_pie.data.PieChartItemData
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAccountsTotalMinimumBalanceAmountValueUseCase
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
    getAccountsTotalBalanceAmountValueUseCase: GetAccountsTotalBalanceAmountValueUseCase,
    getAccountsTotalMinimumBalanceAmountValueUseCase: GetAccountsTotalMinimumBalanceAmountValueUseCase,
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
        getAccountsTotalBalanceAmountValueUseCase()
    private val accountsTotalMinimumBalanceAmountValue: Flow<Long> =
        getAccountsTotalMinimumBalanceAmountValueUseCase()
    // endregion

    // region UI state
    private val isBalanceVisible: MutableStateFlow<Boolean> = MutableStateFlow(
        value = false,
    )
    private val homeListItemViewData: MutableStateFlow<ImmutableList<TransactionListItemData>> =
        MutableStateFlow(
            value = persistentListOf(),
        )
    private val overviewTabSelectionIndex: MutableStateFlow<Int> =
        MutableStateFlow(
            value = DEFAULT_OVERVIEW_TAB_SELECTION,
        )
    private val selectedTimestamp: MutableStateFlow<Long> = MutableStateFlow(
        value = dateTimeKit.getCurrentTimeMillis(),
    )
    private val overviewCardData: MutableStateFlow<OverviewCardViewModelData?> =
        MutableStateFlow(
            value = null,
        )
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<HomeScreenUIState> =
        MutableStateFlow(
            value = HomeScreenUIState(),
        )
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
            value = overviewCardData.value?.income?.toLong().orZero(),
        )
        val totalExpenseAmount = Amount(
            value = overviewCardData.value?.expense?.toLong().orZero(),
        )

        coroutineScope.launch {
            uiState.update {
                HomeScreenUIState(
                    isBackupCardVisible = isBackupCardVisible.first(),
                    isBalanceVisible = isBalanceVisible.value,
                    isLoading = isLoading,
                    isRecentTransactionsTrailingTextVisible = homeListItemViewData.value
                        .isNotEmpty(),
                    overviewTabSelectionIndex = overviewTabSelectionIndex.value.orZero(),
                    transactionListItemDataList = homeListItemViewData.value,
                    accountsTotalBalanceAmountValue = accountsTotalBalanceAmountValue.first()
                        .orZero(),
                    accountsTotalMinimumBalanceAmountValue = accountsTotalMinimumBalanceAmountValue.first()
                        .orZero(),
                    overviewCardData = overviewCardData.value.orDefault(),
                    pieChartData = PieChartData(
                        items = persistentListOf(
                            PieChartItemData(
                                value = overviewCardData.value?.income.orZero(),
                                text = "Income : $totalIncomeAmount", // TODO(Abhi): Move to string resources
                                color = MyColor.TERTIARY,
                            ),
                            PieChartItemData(
                                value = overviewCardData.value?.expense.orZero(),
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
        observeForOverviewCardData()
    }
    // endregion

    // region state events
    private fun handleOverviewCardAction(
        overviewCardAction: OverviewCardAction,
    ): Job {
        val overviewTabOption =
            OverviewTabOption.entries[overviewTabSelectionIndex.value]
        when (overviewCardAction) {
            OverviewCardAction.NEXT -> {
                when (overviewTabOption) {
                    OverviewTabOption.DAY -> {
                        selectedTimestamp.value =
                            Instant.ofEpochMilli(selectedTimestamp.value)
                                .toZonedDateTime()
                                .plusDays(1)
                                .toEpochMilli()
                    }

                    OverviewTabOption.MONTH -> {
                        selectedTimestamp.value =
                            Instant.ofEpochMilli(selectedTimestamp.value)
                                .toZonedDateTime()
                                .plusMonths(1)
                                .toEpochMilli()
                    }

                    OverviewTabOption.YEAR -> {
                        selectedTimestamp.value =
                            Instant.ofEpochMilli(selectedTimestamp.value)
                                .toZonedDateTime()
                                .plusYears(1)
                                .toEpochMilli()
                    }
                }
            }

            OverviewCardAction.PREV -> {
                when (overviewTabOption) {
                    OverviewTabOption.DAY -> {
                        selectedTimestamp.value =
                            Instant.ofEpochMilli(selectedTimestamp.value)
                                .toZonedDateTime()
                                .minusDays(1)
                                .toEpochMilli()
                    }

                    OverviewTabOption.MONTH -> {
                        selectedTimestamp.value =
                            Instant.ofEpochMilli(selectedTimestamp.value)
                                .toZonedDateTime()
                                .minusMonths(1)
                                .toEpochMilli()
                    }

                    OverviewTabOption.YEAR -> {
                        selectedTimestamp.value =
                            Instant.ofEpochMilli(selectedTimestamp.value)
                                .toZonedDateTime()
                                .minusYears(1)
                                .toEpochMilli()
                    }
                }
            }
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateIsBalanceVisible(
        updatedIsBalanceVisible: Boolean,
    ): Job {
        isBalanceVisible.update {
            updatedIsBalanceVisible
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateOverviewTabSelectionIndex(
        updatedOverviewTabSelectionIndex: Int,
    ): Job {
        overviewTabSelectionIndex.value = updatedOverviewTabSelectionIndex
        return refreshIfRequired(
            shouldRefresh = true,
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
                homeListItemViewData.update {
                    transactionDataList.map { transactionData: TransactionData ->
                        transactionData.toTransactionListItemData(
                            dateTimeKit = dateTimeKit,
                        )
                    }
                }
                completeLoading()
            }
        }
    }
    // endregion

    // region observeForOverviewCardData
    private fun observeForOverviewCardData() {
        coroutineScope.launch {
            combineAndCollectLatest(
                overviewTabSelectionIndex,
                selectedTimestamp,
            ) { (overviewTabSelectionIndex, timestamp) ->
                val overviewTabOption =
                    OverviewTabOption.entries[overviewTabSelectionIndex]
                val transactionsInSelectedTimeRange =
                    getTransactionsInSelectedTimeRange(
                        overviewTabOption = overviewTabOption,
                        timestamp = timestamp,
                    )

                overviewCardData.update {
                    OverviewCardViewModelData(
                        income = getIncomeAmount(
                            transactionsInSelectedTimeRange = transactionsInSelectedTimeRange,
                        ),
                        expense = getExpenseAmount(
                            transactionsInSelectedTimeRange = transactionsInSelectedTimeRange,
                        ),
                        title = getTitle(
                            overviewTabOption = overviewTabOption,
                            timestamp = timestamp
                        ),
                    )
                }
            }
        }
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
                abs(transaction.amount.value)
            } else {
                -abs(transaction.amount.value)
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
