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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.transactions.view_model

import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.common.core.extensions.atEndOfDay
import com.makeappssimple.abhimanyu.common.core.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.common.core.extensions.isNull
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.extensions.orEmpty
import com.makeappssimple.abhimanyu.common.core.extensions.orMin
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
import com.makeappssimple.abhimanyu.common.core.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.UpdateTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature.areFiltersSelected
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature.orDefault
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature.orEmpty
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.transaction.toTransactionListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.transactions.bottom_sheet.TransactionsScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.transactions.state.TransactionsScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.transactions.state.TransactionsScreenUIStateEvents
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel
import java.lang.Long.min
import java.time.LocalDate

@KoinViewModel
internal class TransactionsScreenViewModel(
    navigationKit: NavigationKit,
    screenUIStateDelegate: ScreenUIStateDelegate,
    private val coroutineScope: CoroutineScope,
    private val dispatcherProvider: DispatcherProvider,
    private val dateTimeKit: DateTimeKit,
    private val getAllTransactionDataFlowUseCase: GetAllTransactionDataFlowUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val updateTransactionsUseCase: UpdateTransactionsUseCase,
    internal val logKit: LogKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    logKit = logKit,
    navigationKit = navigationKit,
    screenUIStateDelegate = screenUIStateDelegate,
) {
    // region initial data
    private var isInitialDataFetchCompleted = false
    // TODO(Abhi): Firebase tracking
    // private var performanceScreenInitTrace: Trace? = null

    private var categoriesMap: Map<TransactionType, MutableSet<Category>> =
        mapOf()
    private var accounts: MutableSet<Account> = mutableSetOf()
    private var oldestTransactionLocalDate: LocalDate? = null
    private var allTransactionForValues: ImmutableList<TransactionFor> =
        persistentListOf()

    private val transactionTypes: ImmutableList<TransactionType> =
        TransactionType.entries.toImmutableList()
    private val sortOptions: ImmutableList<SortOption> =
        SortOption.entries.toImmutableList()
    private val currentLocalDate: LocalDate = dateTimeKit.getCurrentLocalDate()
    // endregion

    // region initial data
    private val allTransactionData: MutableStateFlow<ImmutableList<TransactionData>> =
        MutableStateFlow(
            value = persistentListOf(),
        )
    private val selectedTransactionIndices: MutableStateFlow<ImmutableList<Int>> =
        MutableStateFlow(
            value = persistentListOf(),
        )
    private val transactionDetailsListItemViewData: MutableStateFlow<Map<String, ImmutableList<TransactionListItemData>>> =
        MutableStateFlow(
            value = mutableMapOf(),
        )
    // endregion

    // region UI state
    private val isInSelectionMode: MutableStateFlow<Boolean> =
        MutableStateFlow(
            value = false,
        )
    private val searchText: MutableStateFlow<String> = MutableStateFlow(
        value = "",
    )
    private val selectedFilter: MutableStateFlow<Filter> = MutableStateFlow(
        value = Filter(),
    )
    private val selectedSortOption: MutableStateFlow<SortOption> =
        MutableStateFlow(
            value = SortOption.LATEST_FIRST,
        )
    private val screenBottomSheetType: MutableStateFlow<TransactionsScreenBottomSheetType> =
        MutableStateFlow(
            value = TransactionsScreenBottomSheetType.None,
        )
    // endregion

    // region uiStateAndStateEvents
    private val _uiState: MutableStateFlow<TransactionsScreenUIState> =
        MutableStateFlow(
            value = TransactionsScreenUIState(),
        )
    internal val uiState: StateFlow<TransactionsScreenUIState> =
        _uiState.asStateFlow()
    internal val uiStateEvents: TransactionsScreenUIStateEvents =
        TransactionsScreenUIStateEvents(
            addToSelectedTransactions = ::addToSelectedTransactions,
            clearSelectedTransactions = ::clearSelectedTransactions,
            navigateToAddTransactionScreen = ::navigateToAddTransactionScreen,
            navigateToViewTransactionScreen = ::navigateToViewTransactionScreen,
            navigateUp = ::navigateUp,
            removeFromSelectedTransactions = ::removeFromSelectedTransactions,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            selectAllTransactions = ::selectAllTransactions,
            updateIsInSelectionMode = ::updateIsInSelectionMode,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
            updateSearchText = ::updateSearchText,
            updateSelectedFilter = ::updateSelectedFilter,
            updateSelectedSortOption = ::updateSelectedSortOption,
            updateTransactionForValuesInTransactions = ::updateTransactionForValuesInTransactions,
        )
    // endregion

    // region updateUiStateAndStateEvents
    override fun updateUiStateAndStateEvents() {
        if (!isInitialDataFetchCompleted && transactionDetailsListItemViewData.value.isNotEmpty()) {
            isInitialDataFetchCompleted = true
        }

        _uiState.update {
            TransactionsScreenUIState(
                isBackHandlerEnabled = searchText.value.isNotEmpty() ||
                        selectedFilter.value.orEmpty().areFiltersSelected() ||
                        isInSelectionMode.value,
                isBottomSheetVisible = screenBottomSheetType != TransactionsScreenBottomSheetType.None,
                isInSelectionMode = isInSelectionMode.value,
                isLoading = isLoading,
                isSearchSortAndFilterVisible = isInSelectionMode.value.not() && (
                        transactionDetailsListItemViewData.value.isNotEmpty() ||
                                searchText.value.isNotEmpty() ||
                                selectedFilter.value.orEmpty()
                                    .areFiltersSelected() ||
                                isLoading
                        ),
                selectedFilter = selectedFilter.value.orEmpty(),
                selectedTransactions = selectedTransactionIndices.value.toImmutableList(),
                sortOptions = sortOptions.orEmpty(),
                transactionForValues = allTransactionForValues.orEmpty(),
                accounts = accounts.toImmutableList(),
                expenseCategories = categoriesMap[TransactionType.EXPENSE].orEmpty()
                    .toImmutableList(),
                incomeCategories = categoriesMap[TransactionType.INCOME].orEmpty()
                    .toImmutableList(),
                investmentCategories = categoriesMap[TransactionType.INVESTMENT].orEmpty()
                    .toImmutableList(),
                transactionTypes = transactionTypes.orEmpty(),
                currentLocalDate = currentLocalDate.orMin(),
                oldestTransactionLocalDate = oldestTransactionLocalDate.orMin(),
                transactionDetailsListItemViewData = transactionDetailsListItemViewData.value,
                selectedSortOption = selectedSortOption.value.orDefault(),
                searchText = searchText.value,
                screenBottomSheetType = screenBottomSheetType.value,
            )
        }
    }
    // endregion

    // region fetchData
    override fun fetchData(): Job {
        return coroutineScope.launch {
            withLoadingSuspend {
                allTransactionForValues = getAllTransactionForValuesUseCase()
            }
        }
    }
    // endregion

    // region observeData
    override fun observeData() {
        observeForTransactionDetailsListItemViewData()
        observeForAllTransactionData()
    }
    // endregion

    // region state events
    private fun addToSelectedTransactions(
        transactionId: Int,
    ): Job {
        selectedTransactionIndices.update {
            it.toMutableList().apply {
                add(transactionId)
            }.toImmutableList()
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun clearSelectedTransactions(): Job {
        selectedTransactionIndices.update {
            persistentListOf()
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun removeFromSelectedTransactions(
        transactionId: Int,
    ): Job {
        selectedTransactionIndices.update {
            it.toMutableList().apply {
                remove(transactionId)
            }.toImmutableList()
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun selectAllTransactions(): Job {
        selectedTransactionIndices.update {
            transactionDetailsListItemViewData.value.values.flatMap {
                it.map { transactionListItemData ->
                    transactionListItemData.transactionId
                }
            }.toImmutableList()
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun resetScreenBottomSheetType(): Job {
        return updateScreenBottomSheetType(
            updatedTransactionsScreenBottomSheetType = TransactionsScreenBottomSheetType.None,
        )
    }

    private fun updateScreenBottomSheetType(
        updatedTransactionsScreenBottomSheetType: TransactionsScreenBottomSheetType,
    ): Job {
        screenBottomSheetType.update {
            updatedTransactionsScreenBottomSheetType
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateIsInSelectionMode(
        updatedIsInSelectionMode: Boolean,
    ): Job {
        isInSelectionMode.update {
            updatedIsInSelectionMode
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateSearchText(
        updatedSearchText: String,
    ): Job {
        searchText.update {
            updatedSearchText
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateSelectedFilter(
        updatedSelectedFilter: Filter,
    ): Job {
        selectedFilter.update {
            updatedSelectedFilter
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateSelectedSortOption(
        updatedSelectedSortOption: SortOption,
    ): Job {
        selectedSortOption.update {
            updatedSelectedSortOption
        }
        return refreshIfRequired(
            shouldRefresh = true,
        )
    }

    private fun updateTransactionForValuesInTransactions(
        transactionForId: Int,
    ): Job {
        val selectedTransactions: ImmutableList<Int> =
            selectedTransactionIndices.value
        return coroutineScope.launch {
            val updatedTransactions =
                allTransactionData.value.map { transactionData ->
                    transactionData.transaction
                }.filter {
                    it.transactionType == TransactionType.EXPENSE &&
                            selectedTransactions.contains(it.id)
                }.map {
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

    // region observeForTransactionDetailsListItemViewData
    private fun observeForTransactionDetailsListItemViewData() {
        coroutineScope.launch {
            combineAndCollectLatest(
                searchText,
                selectedFilter,
                selectedSortOption,
                allTransactionData,
            ) {
                    (
                        searchText,
                        selectedFilter,
                        selectedSortOption,
                        allTransactionData,
                    ),
                ->
                val updatedAllTransactionData = withContext(
                    context = dispatcherProvider.default,
                ) {
                    allTransactionData
                        .filter { transactionData ->
                            isAvailableAfterSearch(
                                searchTextValue = searchText,
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
                                listItem.toTransactionListItemData(
                                    dateTimeKit = dateTimeKit,
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
                transactionDetailsListItemViewData.update {
                    updatedAllTransactionData
                }
                if (allTransactionData.isNotEmpty()) {
                    completeLoading()
                }
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

    // region observeForAllTransactionData
    private fun observeForAllTransactionData() {
        coroutineScope.launch {
            getAllTransactionDataFlowUseCase()
                .flowOn(
                    context = dispatcherProvider.io,
                )
                .collectLatest { updatedAllTransactionData ->
                    val accountsInTransactions = mutableSetOf<Account>()
                    var oldestTransactionLocalDateValue = Long.MAX_VALUE
                    val categoriesInTransactionsMap =
                        mutableMapOf<TransactionType, MutableSet<Category>>()

                    updatedAllTransactionData.forEach { transactionData ->
                        transactionData.accountFrom?.let {
                            accountsInTransactions.add(it)
                        }
                        transactionData.accountTo?.let {
                            accountsInTransactions.add(it)
                        }
                        oldestTransactionLocalDateValue = min(
                            oldestTransactionLocalDateValue,
                            transactionData.transaction.transactionTimestamp
                        )
                        transactionData.category?.let {
                            categoriesInTransactionsMap[it.transactionType]?.add(
                                it
                            )
                        }
                    }

                    accounts = accountsInTransactions
                    oldestTransactionLocalDate = dateTimeKit.getLocalDate(
                        timestamp = oldestTransactionLocalDateValue.orZero(),
                    )
                    categoriesMap = categoriesInTransactionsMap.toMap()
                    allTransactionData.update {
                        updatedAllTransactionData
                    }
                }
        }
    }
    // endregion
}
