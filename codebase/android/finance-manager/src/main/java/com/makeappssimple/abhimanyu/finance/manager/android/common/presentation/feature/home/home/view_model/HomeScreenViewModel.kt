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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.home.home.view_model

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.common.core.coroutines.getCompletedJob
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Transaction
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.account.GetAllAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.account.GetAllAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.common.ShouldShowBackupCardUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.transaction.GetRecentTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.transaction.GetTransactionByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.use_case.transaction.GetTransactionsBetweenTimestampsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.home.home.state.HomeScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.home.home.state.HomeScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.listitem.transaction.toTransactionListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.overview_card.OverviewCardAction
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.overview_card.OverviewCardViewModelData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.overview_card.OverviewTabOption
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.overview_card.orDefault
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import kotlin.math.abs

private const val DEFAULT_OVERVIEW_TAB_SELECTION = 1

@KoinViewModel
internal class HomeScreenViewModel(
    private val backupDataUseCase: BackupDataUseCase,
    private val coroutineScope: CoroutineScope,
    private val getAllAccountsTotalBalanceAmountValueUseCase: GetAllAccountsTotalBalanceAmountValueUseCase,
    private val getAllAccountsTotalMinimumBalanceAmountValueUseCase: GetAllAccountsTotalMinimumBalanceAmountValueUseCase,
    private val getRecentTransactionDataFlowUseCase: GetRecentTransactionDataFlowUseCase,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val getTransactionsBetweenTimestampsUseCase: GetTransactionsBetweenTimestampsUseCase,
    private val navigationKit: NavigationKit,
    private val shouldShowBackupCardUseCase: ShouldShowBackupCardUseCase,
    internal val dateTimeKit: DateTimeKit,
    internal val logKit: LogKit,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit {
    // region initial data
    private var isLoading: Boolean = true
    private var isBackupCardVisible: Boolean = false
    private var allAccountsTotalBalanceAmountValue: Long = 0L
    private var allAccountsTotalMinimumBalanceAmountValue = 0L
    private var isBalanceVisible: Boolean = false
    private var homeListItemViewData: ImmutableList<TransactionListItemData> =
        persistentListOf()
    private var overviewTabSelectionIndex: Int = DEFAULT_OVERVIEW_TAB_SELECTION
    private var selectedTimestamp: Long = dateTimeKit.getCurrentTimeMillis()
    private var overviewCardData: OverviewCardViewModelData? = null
    // endregion

    // region uiState
    private val _uiState: MutableStateFlow<HomeScreenUIState> =
        MutableStateFlow(
            value = HomeScreenUIState(),
        )
    internal val uiState: StateFlow<HomeScreenUIState> = _uiState.asStateFlow()
    // endregion

    // region uiStateEvents
    internal val uiStateEvents: HomeScreenUIStateEvents =
        HomeScreenUIStateEvents(
            handleOverviewCardAction = ::handleOverviewCardAction,
            navigateToAccountsScreen = navigationKit::navigateToAccountsScreen,
            navigateToAddTransactionScreen = navigationKit::navigateToAddTransactionScreen,
            navigateToAnalysisScreen = navigationKit::navigateToAnalysisScreen,
            navigateToSettingsScreen = navigationKit::navigateToSettingsScreen,
            navigateToTransactionsScreen = navigationKit::navigateToTransactionsScreen,
            navigateToViewTransactionScreen = navigationKit::navigateToViewTransactionScreen,
            updateIsBalanceVisible = ::updateIsBalanceVisible,
            updateOverviewTabSelectionIndex = ::updateOverviewTabSelectionIndex,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        completeLoading()
        observeData()
    }
    // endregion

    // region refreshUiState
    private fun refreshUiState(): Job {
        return coroutineScope.launch {
            updateOverviewCardData()
            updateUiState()
        }
    }

    private fun updateUiState() {
        logError(
            tag = "Abhi",
            message = "HomeScreenViewModel: updateUiState",
        )
        _uiState.update {
            HomeScreenUIState(
                isBackupCardVisible = isBackupCardVisible,
                isBalanceVisible = isBalanceVisible,
                isLoading = isLoading,
                isRecentTransactionsTrailingTextVisible = homeListItemViewData
                    .isNotEmpty(),
                overviewTabSelectionIndex = overviewTabSelectionIndex.orZero(),
                transactionListItemDataList = homeListItemViewData,
                accountsTotalBalanceAmountValue = allAccountsTotalBalanceAmountValue,
                allAccountsTotalMinimumBalanceAmountValue = allAccountsTotalMinimumBalanceAmountValue,
                overviewCardData = overviewCardData.orDefault(),
            )
        }
    }
    // endregion

    // region observeData
    private fun observeData() {
        observeForHomeListItemViewData()
        observeIsBackupCardVisible()
        observeAllAccountsTotalBalanceAmountValue()
        observeAllAccountsTotalMinimumBalanceAmountValueUseCase()
    }

    private fun observeForHomeListItemViewData() {
        coroutineScope.launch {
            getRecentTransactionDataFlowUseCase().collectLatest { transactionDataList ->
                homeListItemViewData = transactionDataList
                    .map { transactionData: TransactionData ->
                        transactionData.toTransactionListItemData(
                            getReadableDateAndTime = dateTimeKit::getReadableDateAndTime,
                        )
                    }
                refreshUiState()
            }
        }
    }

    private fun observeIsBackupCardVisible() {
        coroutineScope.launch {
            shouldShowBackupCardUseCase().collectLatest {
                isBackupCardVisible = it
                refreshUiState()
            }
        }
    }

    private fun observeAllAccountsTotalBalanceAmountValue() {
        coroutineScope.launch {
            getAllAccountsTotalBalanceAmountValueUseCase().collectLatest {
                allAccountsTotalBalanceAmountValue = it
                refreshUiState()
            }
        }
    }

    private fun observeAllAccountsTotalMinimumBalanceAmountValueUseCase() {
        coroutineScope.launch {
            getAllAccountsTotalMinimumBalanceAmountValueUseCase().collectLatest {
                allAccountsTotalMinimumBalanceAmountValue = it
                refreshUiState()
            }
        }
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
                        selectedTimestamp = dateTimeKit.getNextDayTimestamp(
                            timestamp = selectedTimestamp,
                        )
                    }

                    OverviewTabOption.MONTH -> {
                        selectedTimestamp = dateTimeKit.getNextMonthTimestamp(
                            timestamp = selectedTimestamp,
                        )
                    }

                    OverviewTabOption.YEAR -> {
                        selectedTimestamp = dateTimeKit.getNextYearTimestamp(
                            timestamp = selectedTimestamp,
                        )
                    }
                }
            }

            OverviewCardAction.PREV -> {
                when (overviewTabOption) {
                    OverviewTabOption.DAY -> {
                        selectedTimestamp = dateTimeKit.getPreviousDayTimestamp(
                            timestamp = selectedTimestamp,
                        )
                    }

                    OverviewTabOption.MONTH -> {
                        selectedTimestamp =
                            dateTimeKit.getPreviousMonthTimestamp(
                                timestamp = selectedTimestamp,
                            )
                    }

                    OverviewTabOption.YEAR -> {
                        selectedTimestamp =
                            dateTimeKit.getPreviousYearTimestamp(
                                timestamp = selectedTimestamp,
                            )
                    }
                }
            }
        }
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateIsBalanceVisible(
        updatedIsBalanceVisible: Boolean,
        shouldRefresh: Boolean = true,
    ): Job {
        isBalanceVisible = updatedIsBalanceVisible
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateOverviewTabSelectionIndex(
        updatedOverviewTabSelectionIndex: Int,
        shouldRefresh: Boolean = true,
    ): Job {
        overviewTabSelectionIndex = updatedOverviewTabSelectionIndex
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
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
            navigationKit.navigateUp()
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

    // region loading
    private fun completeLoading() {
        isLoading = false
        refreshUiState()
    }

    private fun startLoading() {
        isLoading = true
        refreshUiState()
    }
    // endregion
}
