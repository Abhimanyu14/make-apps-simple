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

@file:OptIn(
    ExperimentalCoroutinesApi::class,
    FlowPreview::class
)

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.transactions.view_model

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.common.core.coroutines.getCompletedJob
import com.makeappssimple.abhimanyu.common.core.extensions.atEndOfDay
import com.makeappssimple.abhimanyu.common.core.extensions.isNull
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.extensions.orMin
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
import com.makeappssimple.abhimanyu.common.core.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.DuplicateTransactionUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetAccountsInTransactionsFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetCategoriesInTransactionsFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetOldestTransactionTimestampUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.UpdateTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction_for.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionFilter
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.feature.Filter
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.feature.SortOption
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.feature.areFiltersSelected
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.feature.orDefault
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.transactions.bottom_sheet.TransactionsScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.transactions.snackbar.TransactionsScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.transactions.state.TransactionsScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.transactions.state.TransactionsScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.transaction.toTransactionListItemData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel
import java.time.LocalDate

@KoinViewModel
internal class TransactionsScreenViewModel(
    private val coroutineScope: CoroutineScope,
    private val dispatcherProvider: DispatcherProvider,
    private val dateTimeKit: DateTimeKit,
    private val duplicateTransactionUseCase: DuplicateTransactionUseCase,
    private val getAllTransactionDataFlowUseCase: GetAllTransactionDataFlowUseCase,
    private val getAccountsInTransactionsFlowUseCase: GetAccountsInTransactionsFlowUseCase,
    private val getCategoriesInTransactionsFlowUseCase: GetCategoriesInTransactionsFlowUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getOldestTransactionTimestampUseCase: GetOldestTransactionTimestampUseCase,
    private val navigationKit: NavigationKit,
    private val updateTransactionsUseCase: UpdateTransactionsUseCase,
    internal val logKit: LogKit,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit {
    // region data
    private var isInSelectionMode: Boolean = false
    private var isLoading: Boolean = true
    private var selectedFilter = Filter()
    private val transactionFilter: MutableStateFlow<TransactionFilter> =
        MutableStateFlow(
            value = TransactionFilter(),
        )
    private var allTransactionData: ImmutableList<TransactionData> =
        persistentListOf()
    private var allTransactionForValues: ImmutableList<TransactionFor> =
        persistentListOf()
    private var selectedTransactionIndices: ImmutableList<Int> =
        persistentListOf()
    private val sortOptions: ImmutableList<SortOption> =
        SortOption.entries.toImmutableList()
    private val transactionTypes: ImmutableList<TransactionType> =
        TransactionType.entries.toImmutableList()
    private val currentLocalDate: LocalDate = dateTimeKit.getCurrentLocalDate()
    private var oldestTransactionLocalDate: LocalDate? = null
    private var categoriesMap: Map<TransactionType, List<Category>> =
        mapOf()
    private var transactionDetailsListItemViewData: Map<String, ImmutableList<TransactionListItemData>> =
        mutableMapOf()
    private var accounts: ImmutableList<Account> = persistentListOf()
    private var selectedSortOption: SortOption = SortOption.LATEST_FIRST
    private var searchTextFieldState: TextFieldState = TextFieldState()
    private var screenBottomSheetType: TransactionsScreenBottomSheetType =
        TransactionsScreenBottomSheetType.None
    private var screenSnackbarType: TransactionsScreenSnackbarType =
        TransactionsScreenSnackbarType.None
    // endregion

    // region uiState
    private val _uiState: MutableStateFlow<TransactionsScreenUIState> =
        MutableStateFlow(
            value = TransactionsScreenUIState(),
        )
    internal val uiState: StateFlow<TransactionsScreenUIState> =
        _uiState.asStateFlow()
    // endregion

    // region uiStateEvents
    internal val uiStateEvents: TransactionsScreenUIStateEvents =
        TransactionsScreenUIStateEvents(
            addToSelectedTransactions = ::addToSelectedTransactions,
            clearSelectedTransactions = ::clearSelectedTransactions,
            duplicateTransaction = ::duplicateTransaction,
            navigateToAddTransactionScreen = navigationKit::navigateToAddTransactionScreen,
            navigateToViewTransactionScreen = navigationKit::navigateToViewTransactionScreen,
            navigateUp = navigationKit::navigateUp,
            removeFromSelectedTransactions = ::removeFromSelectedTransactions,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            resetScreenSnackbarType = ::resetScreenSnackbarType,
            selectAllTransactions = ::selectAllTransactions,
            updateIsInSelectionMode = ::updateIsInSelectionMode,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
            updateSearchText = ::updateSearchText,
            updateSelectedFilter = ::updateSelectedFilter,
            updateSelectedSortOption = ::updateSelectedSortOption,
            updateTransactionForValuesInTransactions = ::updateTransactionForValuesInTransactions,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        coroutineScope.launch {
            fetchData()
            completeLoading()
        }
        observeData()
    }
    // endregion

    // region refreshUiState
    private suspend fun refreshUiState() {
        updateTransactionDetailsListItemViewData()
        updateUiState()
    }

    private fun updateUiState() {
        _uiState.update {
            TransactionsScreenUIState(
                isBackHandlerEnabled = searchTextFieldState.text.isNotEmpty() ||
                        selectedFilter.areFiltersSelected() ||
                        isInSelectionMode,
                isBottomSheetVisible = screenBottomSheetType != TransactionsScreenBottomSheetType.None,
                isDuplicateTransactionMenuOptionVisible = selectedTransactionIndices.size == 1,
                isInSelectionMode = isInSelectionMode,
                isLoading = isLoading,
                isSearchSortAndFilterVisible = isInSelectionMode.not() && (
                        transactionDetailsListItemViewData.isNotEmpty() ||
                                searchTextFieldState.text.isNotEmpty() ||
                                selectedFilter.areFiltersSelected() ||
                                isLoading
                        ),
                selectedFilter = selectedFilter,
                selectedTransactions = selectedTransactionIndices.toImmutableList(),
                sortOptions = sortOptions,
                transactionForValues = allTransactionForValues,
                accounts = accounts.toImmutableList(),
                expenseCategories = categoriesMap[TransactionType.EXPENSE].orEmpty()
                    .toImmutableList(),
                incomeCategories = categoriesMap[TransactionType.INCOME].orEmpty()
                    .toImmutableList(),
                investmentCategories = categoriesMap[TransactionType.INVESTMENT].orEmpty()
                    .toImmutableList(),
                transactionTypes = transactionTypes,
                currentLocalDate = currentLocalDate.orMin(),
                oldestTransactionLocalDate = oldestTransactionLocalDate.orMin(),
                transactionDetailsListItemViewData = transactionDetailsListItemViewData,
                selectedSortOption = selectedSortOption.orDefault(),
                searchTextFieldState = searchTextFieldState,
                screenBottomSheetType = screenBottomSheetType,
                screenSnackbarType = screenSnackbarType,
            )
        }
    }

    private suspend fun updateTransactionDetailsListItemViewData() {
        withContext(
            context = dispatcherProvider.default,
        ) {
            val updatedAllTransactionData = allTransactionData
                .filter { transactionData ->
                    isAvailableAfterSearch(
                        searchTextValue = searchTextFieldState.text.toString(),
                        transactionData = transactionData,
                    ) && isAvailableAfterDateFilter(
                        fromDate = selectedFilter.fromDate,
                        toDate = selectedFilter.toDate,
                        transactionData = transactionData,
                    ) && isAvailableAfterAccountFilter(
                        selectedAccountsIds = selectedFilter.selectedAccountIds,
                        transactionData = transactionData,
                    ) && isAvailableAfterCategoryFilter(
                        selectedExpenseCategoryIds = selectedFilter.selectedExpenseCategoryIds,
                        selectedIncomeCategoryIds = selectedFilter.selectedIncomeCategoryIds,
                        selectedInvestmentCategoryIds = selectedFilter.selectedInvestmentCategoryIds,
                        transactionData = transactionData,
                    )
                }
                .sortedWith(
                    comparator = compareBy { transactionData ->
                        when (selectedSortOption) {
                            SortOption.AMOUNT_ASC -> {
                                transactionData.transaction.amount.value
                            }

                            SortOption.AMOUNT_DESC -> {
                                -1 * transactionData.transaction.amount.value
                            }

                            SortOption.LATEST_FIRST -> {
                                -1 * transactionData.transaction.transactionTimestamp
                            }

                            SortOption.OLDEST_FIRST -> {
                                transactionData.transaction.transactionTimestamp
                            }
                        }
                    },
                )
                .groupBy {
                    if (selectedSortOption == SortOption.LATEST_FIRST ||
                        selectedSortOption == SortOption.OLDEST_FIRST
                    ) {
                        dateTimeKit.getFormattedDateWithDayOfWeek(
                            timestamp = it.transaction.transactionTimestamp,
                        )
                    } else {
                        ""
                    }
                }
                .mapValues {
                    it.value.map { listItem ->
                        listItem
                            .toTransactionListItemData(
                                getReadableDateAndTime = dateTimeKit::getReadableDateAndTime,
                            )
                            .copy(
                                isDeleteButtonEnabled = false,
                                isDeleteButtonVisible = true,
                                isEditButtonVisible = false,
                                isExpanded = false,
                                isRefundButtonVisible = false,
                            )
                    }
                }
            transactionDetailsListItemViewData = updatedAllTransactionData
            if (allTransactionData.isNotEmpty() && isLoading) {
                completeLoading()
            }
        }
    }

    private fun isAvailableAfterSearch(
        searchTextValue: String,
        transactionData: TransactionData,
    ): Boolean {
        if (searchTextValue.isBlank()) {
            return true
        }
        return transactionData.transaction.title.contains(
            other = searchTextValue,
            ignoreCase = true,
        ) || transactionData.transaction.amount.value.toString().contains(
            other = searchTextValue,
            ignoreCase = true,
        )
    }

    private fun isAvailableAfterDateFilter(
        fromDate: LocalDate?,
        toDate: LocalDate?,
        transactionData: TransactionData,
    ): Boolean {
        if (fromDate.isNull() || toDate.isNull()) {
            return true
        }
        val fromDateStartOfDayTimestamp = fromDate
            .atStartOfDay()
            .toEpochMilli()
        val toDateStartOfDayTimestamp = toDate
            .atEndOfDay()
            .toEpochMilli()
        return transactionData.transaction.transactionTimestamp in (fromDateStartOfDayTimestamp) until toDateStartOfDayTimestamp
    }

    private fun isAvailableAfterAccountFilter(
        selectedAccountsIds: ImmutableList<Int>,
        transactionData: TransactionData,
    ): Boolean {
        if (selectedAccountsIds.isEmpty()) {
            return true
        }
        return selectedAccountsIds.contains(
            element = transactionData.accountFrom?.id,
        ) || selectedAccountsIds.contains(
            element = transactionData.accountTo?.id,
        )
    }

    private fun isAvailableAfterCategoryFilter(
        selectedExpenseCategoryIds: ImmutableList<Int>,
        selectedIncomeCategoryIds: ImmutableList<Int>,
        selectedInvestmentCategoryIds: ImmutableList<Int>,
        transactionData: TransactionData,
    ): Boolean {
        if (selectedExpenseCategoryIds.isEmpty() &&
            selectedIncomeCategoryIds.isEmpty() &&
            selectedInvestmentCategoryIds.isEmpty()
        ) {
            return true
        }
        val transactionCategoryId = transactionData.category?.id ?: return false
        return selectedExpenseCategoryIds.contains(
            element = transactionCategoryId,
        ) || selectedIncomeCategoryIds.contains(
            element = transactionCategoryId,
        ) || selectedInvestmentCategoryIds.contains(
            element = transactionCategoryId,
        )
    }
    // endregion

    // region fetchData
    private suspend fun fetchData() {
        allTransactionForValues = getAllTransactionForValuesUseCase()
    }
    // endregion

    // region observeData
    private fun observeData() {
        observeForAccountsInTransactions()
        observeForAllTransactionData()
        observeForCategoriesInTransactions()
        observeForOldestTransactionTimestamp()
        observeForSearchText()
    }

    private fun observeForAccountsInTransactions() {
        coroutineScope.launch {
            getAccountsInTransactionsFlowUseCase()
                .collectLatest { accountsInTransactions ->
                    accounts = accountsInTransactions.toImmutableList()
                    refreshUiState()
                }
        }
    }

    private fun observeForAllTransactionData() {
        coroutineScope.launch(
            context = dispatcherProvider.default,
        ) {
            transactionFilter
                .flatMapLatest { updatedTransactionFilter ->
                    getAllTransactionDataFlowUseCase(
                        transactionFilter = updatedTransactionFilter,
                    )
                }
                .collectLatest { updatedAllTransactionData ->
                    allTransactionData = updatedAllTransactionData
                    refreshUiState()
                }
        }
    }

    private fun observeForCategoriesInTransactions() {
        coroutineScope.launch(
            context = dispatcherProvider.default,
        ) {
            getCategoriesInTransactionsFlowUseCase()
                .collectLatest { categoriesInTransactions: List<Category> ->
                    categoriesMap = categoriesInTransactions.groupBy {
                        it.transactionType
                    }
                    refreshUiState()
                }
        }
    }

    private fun observeForOldestTransactionTimestamp() {
        coroutineScope.launch(
            context = dispatcherProvider.default,
        ) {
            getOldestTransactionTimestampUseCase()
                .collectLatest { oldestTransactionTimestamp: Long? ->
                    oldestTransactionLocalDate = dateTimeKit.getLocalDate(
                        timestamp = oldestTransactionTimestamp.orZero(),
                    )
                    refreshUiState()
                }
        }
    }

    private fun observeForSearchText() {
        coroutineScope.launch(
            context = dispatcherProvider.default,
        ) {
            snapshotFlow {
                searchTextFieldState.text.toString()
            }
                .debounce(
                    timeoutMillis = 300L,
                )
                .distinctUntilChanged()
                .collect { updatedSearchText ->
                    transactionFilter.update {
                        it.copy(
                            searchText = updatedSearchText,
                        )
                    }
                }
        }
    }
    // endregion

    // region state events
    private fun addToSelectedTransactions(
        transactionId: Int,
        shouldRefresh: Boolean = true,
    ): Job {
        selectedTransactionIndices = selectedTransactionIndices
            .toMutableList()
            .apply {
                add(
                    element = transactionId,
                )
            }
            .toImmutableList()
        return if (shouldRefresh) {
            coroutineScope.launch {
                refreshUiState()
            }
        } else {
            getCompletedJob()
        }
    }

    private fun clearSelectedTransactions(
        shouldRefresh: Boolean = true,
    ): Job {
        selectedTransactionIndices = persistentListOf()
        return if (shouldRefresh) {
            coroutineScope.launch {
                refreshUiState()
            }
        } else {
            getCompletedJob()
        }
    }

    private fun duplicateTransaction(): Job {
        val transactionId = selectedTransactionIndices.first()
        checkNotNull(
            value = transactionId,
            lazyMessage = {
                "transaction id must not be null"
            },
        )
        return coroutineScope.launch(
            context = dispatcherProvider.io,
        ) {
            duplicateTransactionUseCase(
                transactionId = transactionId,
            )
            updateScreenSnackbarType(
                updatedTransactionsScreenSnackbarType = TransactionsScreenSnackbarType.DuplicateTransactionSuccessful,
            )
        }
    }

    private fun removeFromSelectedTransactions(
        transactionId: Int,
        shouldRefresh: Boolean = true,
    ): Job {
        selectedTransactionIndices = selectedTransactionIndices
            .toMutableList()
            .apply {
                remove(
                    element = transactionId,
                )
            }
            .toImmutableList()
        return if (shouldRefresh) {
            coroutineScope.launch {
                refreshUiState()
            }
        } else {
            getCompletedJob()
        }
    }

    private fun selectAllTransactions(
        shouldRefresh: Boolean = true,
    ): Job {
        selectedTransactionIndices = transactionDetailsListItemViewData.values
            .flatMap {
                it.map { transactionListItemData ->
                    transactionListItemData.transactionId
                }
            }
            .toImmutableList()
        return if (shouldRefresh) {
            coroutineScope.launch {
                refreshUiState()
            }
        } else {
            getCompletedJob()
        }
    }

    private fun resetScreenBottomSheetType(): Job {
        return updateScreenBottomSheetType(
            updatedTransactionsScreenBottomSheetType = TransactionsScreenBottomSheetType.None,
        )
    }

    private fun resetScreenSnackbarType(): Job {
        return updateScreenSnackbarType(
            updatedTransactionsScreenSnackbarType = TransactionsScreenSnackbarType.None,
        )
    }

    private fun updateScreenBottomSheetType(
        updatedTransactionsScreenBottomSheetType: TransactionsScreenBottomSheetType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenBottomSheetType = updatedTransactionsScreenBottomSheetType
        return if (shouldRefresh) {
            coroutineScope.launch {
                refreshUiState()
            }
        } else {
            getCompletedJob()
        }
    }

    private fun updateScreenSnackbarType(
        updatedTransactionsScreenSnackbarType: TransactionsScreenSnackbarType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenSnackbarType = updatedTransactionsScreenSnackbarType
        return if (shouldRefresh) {
            coroutineScope.launch {
                refreshUiState()
            }
        } else {
            getCompletedJob()
        }
    }

    private fun updateIsInSelectionMode(
        updatedIsInSelectionMode: Boolean,
        shouldRefresh: Boolean = true,
    ): Job {
        isInSelectionMode = updatedIsInSelectionMode
        return if (shouldRefresh) {
            coroutineScope.launch {
                refreshUiState()
            }
        } else {
            getCompletedJob()
        }
    }

    private fun updateSearchText(
        updatedSearchText: String,
        shouldRefresh: Boolean = true,
    ): Job {
        searchTextFieldState.setTextAndPlaceCursorAtEnd(
            text = updatedSearchText,
        )
        return if (shouldRefresh) {
            coroutineScope.launch {
                refreshUiState()
            }
        } else {
            getCompletedJob()
        }
    }

    private fun updateSelectedFilter(
        updatedSelectedFilter: Filter,
        shouldRefresh: Boolean = true,
    ): Job {
        selectedFilter = updatedSelectedFilter
        transactionFilter.update { currentTransactionFilter ->
            currentTransactionFilter.copy(
                selectedAccountIds = updatedSelectedFilter.selectedAccountIds,
                selectedExpenseCategoryIds = updatedSelectedFilter.selectedExpenseCategoryIds,
                selectedIncomeCategoryIds = updatedSelectedFilter.selectedIncomeCategoryIds,
                selectedInvestmentCategoryIds = updatedSelectedFilter.selectedInvestmentCategoryIds,
                selectedTransactionForIds = updatedSelectedFilter.selectedTransactionForIds,
                selectedTransactionTypes = updatedSelectedFilter.selectedTransactionTypes,
                fromDate = updatedSelectedFilter.fromDate,
                toDate = updatedSelectedFilter.toDate,
            )
        }
        return if (shouldRefresh) {
            coroutineScope.launch {
                refreshUiState()
            }
        } else {
            getCompletedJob()
        }
    }

    private fun updateSelectedSortOption(
        updatedSelectedSortOption: SortOption,
        shouldRefresh: Boolean = true,
    ): Job {
        selectedSortOption = updatedSelectedSortOption
        return if (shouldRefresh) {
            coroutineScope.launch {
                refreshUiState()
            }
        } else {
            getCompletedJob()
        }
    }

    private fun updateTransactionForValuesInTransactions(
        transactionForId: Int,
    ): Job {
        val selectedTransactions: ImmutableList<Int> =
            selectedTransactionIndices
        return coroutineScope.launch {
            val updatedTransactions = allTransactionData
                .map { transactionData ->
                    transactionData.transaction
                }
                .filter {
                    it.transactionType == TransactionType.EXPENSE &&
                            selectedTransactions.contains(it.id)
                }
                .map {
                    it
                        .copy(
                            transactionForId = transactionForId,
                        )
                }
            updateTransactionsUseCase(
                transactions = updatedTransactions.toTypedArray(),
            )
        }
    }
    // endregion

    // region loading
    private fun completeLoading(): Job {
        isLoading = false
        return coroutineScope.launch {
            refreshUiState()
        }
    }

    private fun startLoading(): Job {
        isLoading = true
        return coroutineScope.launch {
            refreshUiState()
        }
    }
    // endregion
}
