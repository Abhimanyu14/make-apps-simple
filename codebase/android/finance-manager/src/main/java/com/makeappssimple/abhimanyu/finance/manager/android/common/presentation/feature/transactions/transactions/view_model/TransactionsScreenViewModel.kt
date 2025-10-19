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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.transactions.view_model

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
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
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.UpdateTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction_for.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionData
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel
import java.lang.Long.min
import java.time.LocalDate

@KoinViewModel
internal class TransactionsScreenViewModel(
    private val coroutineScope: CoroutineScope,
    private val dispatcherProvider: DispatcherProvider,
    private val dateTimeKit: DateTimeKit,
    private val duplicateTransactionUseCase: DuplicateTransactionUseCase,
    private val getAllTransactionDataFlowUseCase: GetAllTransactionDataFlowUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
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
    private var categoriesMap: Map<TransactionType, MutableSet<Category>> =
        mapOf()
    private var transactionDetailsListItemViewData: Map<String, ImmutableList<TransactionListItemData>> =
        mutableMapOf()
    private var accounts: MutableSet<Account> = mutableSetOf()
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
    private fun refreshUiState(): Job {
        return coroutineScope.launch {
            updateTransactionDetailsListItemViewData()
            updateUiState()
        }
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
        val updatedAllTransactionData = withContext(
            context = dispatcherProvider.default,
        ) {
            allTransactionData
                .filter { transactionData ->
                    isAvailableAfterSearch(
                        searchTextValue = searchTextFieldState.text.toString(),
                        transactionData = transactionData,
                    ) && isAvailableAfterDateFilter(
                        fromDate = selectedFilter.fromDate,
                        toDate = selectedFilter.toDate,
                        transactionData = transactionData,
                    ) && isAvailableAfterTransactionForFilter(
                        selectedTransactionForValuesIndices = selectedFilter.selectedTransactionForValuesIndices,
                        transactionData = transactionData,
                        transactionForValuesValue = allTransactionForValues,
                    ) && isAvailableAfterTransactionTypeFilter(
                        transactionTypes = transactionTypes,
                        selectedTransactionTypesIndicesValue = selectedFilter.selectedTransactionTypeIndices,
                        transactionData = transactionData,
                    ) && isAvailableAfterAccountFilter(
                        selectedAccountsIndicesValue = selectedFilter.selectedAccountsIndices,
                        accountsValue = accounts.toImmutableList(),
                        transactionData = transactionData,
                    ) && isAvailableAfterCategoryFilter(
                        selectedExpenseCategoryIndicesValue = selectedFilter.selectedExpenseCategoryIndices,
                        selectedIncomeCategoryIndicesValue = selectedFilter.selectedIncomeCategoryIndices,
                        selectedInvestmentCategoryIndicesValue = selectedFilter.selectedInvestmentCategoryIndices,
                        expenseCategoriesValue = categoriesMap[TransactionType.EXPENSE].orEmpty()
                            .toImmutableList(),
                        transactionData = transactionData,
                        incomeCategoriesValue = categoriesMap[TransactionType.INCOME].orEmpty()
                            .toImmutableList(),
                        investmentCategoriesValue = categoriesMap[TransactionType.INVESTMENT].orEmpty()
                            .toImmutableList(),
                    )
                }
                .sortedWith(compareBy {
                    when (selectedSortOption) {
                        SortOption.AMOUNT_ASC -> {
                            it.transaction.amount.value
                        }

                        SortOption.AMOUNT_DESC -> {
                            -1 * it.transaction.amount.value
                        }

                        SortOption.LATEST_FIRST -> {
                            -1 * it.transaction.transactionTimestamp
                        }

                        SortOption.OLDEST_FIRST -> {
                            it.transaction.transactionTimestamp
                        }
                    }
                })
                .groupBy {
                    if (selectedSortOption == SortOption.LATEST_FIRST ||
                        selectedSortOption == SortOption.OLDEST_FIRST
                    ) {
                        val dateTextBuilder = StringBuilder()
                        dateTextBuilder.append(
                            dateTimeKit.getFormattedDate(
                                timestamp = it.transaction.transactionTimestamp,
                            )
                        )
                        dateTextBuilder.append(" (")
                        dateTextBuilder.append(
                            dateTimeKit.getFormattedDayOfWeek(
                                timestamp = it.transaction.transactionTimestamp,
                            )
                        )
                        dateTextBuilder.append(")")
                        dateTextBuilder.toString()
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
        }
        transactionDetailsListItemViewData = updatedAllTransactionData
        if (allTransactionData.isNotEmpty()) {
            completeLoading()
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

    private fun isAvailableAfterTransactionForFilter(
        selectedTransactionForValuesIndices: ImmutableList<Int>,
        transactionData: TransactionData,
        transactionForValuesValue: ImmutableList<TransactionFor>,
    ): Boolean {
        if (selectedTransactionForValuesIndices.isEmpty()) {
            return true
        }
        return selectedTransactionForValuesIndices.contains(
            element = transactionForValuesValue.indexOf(
                element = transactionData.transactionFor,
            ),
        )
    }

    private fun isAvailableAfterTransactionTypeFilter(
        transactionTypes: ImmutableList<TransactionType>,
        selectedTransactionTypesIndicesValue: ImmutableList<Int>,
        transactionData: TransactionData,
    ): Boolean {
        if (selectedTransactionTypesIndicesValue.isEmpty()) {
            return true
        }
        return selectedTransactionTypesIndicesValue.contains(
            element = transactionTypes.indexOf(
                element = transactionData.transaction.transactionType,
            ),
        )
    }

    private fun isAvailableAfterAccountFilter(
        selectedAccountsIndicesValue: ImmutableList<Int>,
        accountsValue: ImmutableList<Account>,
        transactionData: TransactionData,
    ): Boolean {
        if (selectedAccountsIndicesValue.isEmpty()) {
            return true
        }
        return selectedAccountsIndicesValue.contains(
            element = accountsValue.indexOf(
                element = transactionData.accountFrom,
            ),
        ) || selectedAccountsIndicesValue.contains(
            element = accountsValue.indexOf(
                element = transactionData.accountTo,
            ),
        )
    }

    private fun isAvailableAfterCategoryFilter(
        expenseCategoriesValue: ImmutableList<Category>,
        incomeCategoriesValue: ImmutableList<Category>,
        investmentCategoriesValue: ImmutableList<Category>,
        selectedExpenseCategoryIndicesValue: ImmutableList<Int>,
        selectedIncomeCategoryIndicesValue: ImmutableList<Int>,
        selectedInvestmentCategoryIndicesValue: ImmutableList<Int>,
        transactionData: TransactionData,
    ): Boolean {
        if (selectedExpenseCategoryIndicesValue.isEmpty() &&
            selectedIncomeCategoryIndicesValue.isEmpty() &&
            selectedInvestmentCategoryIndicesValue.isEmpty()
        ) {
            return true
        }
        return selectedExpenseCategoryIndicesValue.contains(
            element = expenseCategoriesValue.indexOf(
                element = transactionData.category,
            ),
        ) || selectedIncomeCategoryIndicesValue.contains(
            element = incomeCategoriesValue.indexOf(
                element = transactionData.category,
            ),
        ) || selectedInvestmentCategoryIndicesValue.contains(
            element = investmentCategoriesValue.indexOf(
                element = transactionData.category,
            ),
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
        observeForAllTransactionData()
    }

    private fun observeForAllTransactionData() {
        coroutineScope.launch {
            getAllTransactionDataFlowUseCase()
                .collectLatest { updatedAllTransactionData ->
                    val accountsInTransactions = mutableSetOf<Account>()
                    var oldestTransactionLocalDateValue = Long.MAX_VALUE
                    val categoriesInTransactionsMap =
                        mutableMapOf<TransactionType, MutableSet<Category>>()
                    updatedAllTransactionData.forEach { transactionData ->
                        transactionData.accountFrom?.let {
                            accountsInTransactions.add(
                                element = it,
                            )
                        }
                        transactionData.accountTo?.let {
                            accountsInTransactions.add(
                                element = it,
                            )
                        }
                        oldestTransactionLocalDateValue = min(
                            oldestTransactionLocalDateValue,
                            transactionData.transaction.transactionTimestamp,
                        )
                        transactionData.category?.let {
                            categoriesInTransactionsMap.computeIfAbsent(
                                it.transactionType,
                            ) {
                                mutableSetOf()
                            }.add(
                                element = it,
                            )
                        }
                    }
                    accounts = accountsInTransactions
                    oldestTransactionLocalDate = dateTimeKit.getLocalDate(
                        timestamp = oldestTransactionLocalDateValue.orZero(),
                    )
                    categoriesMap = categoriesInTransactionsMap.toMap()
                    allTransactionData = updatedAllTransactionData
                    refreshUiState()
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
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun clearSelectedTransactions(
        shouldRefresh: Boolean = true,
    ): Job {
        selectedTransactionIndices = persistentListOf()
        return if (shouldRefresh) {
            refreshUiState()
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
            refreshUiState()
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
            refreshUiState()
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
            refreshUiState()
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
            refreshUiState()
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
            refreshUiState()
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
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateSelectedFilter(
        updatedSelectedFilter: Filter,
        shouldRefresh: Boolean = true,
    ): Job {
        selectedFilter = updatedSelectedFilter
        return if (shouldRefresh) {
            refreshUiState()
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
            refreshUiState()
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
