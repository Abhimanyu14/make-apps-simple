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
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.extensions.orZero
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.DuplicateTransactionUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetAccountsInTransactionsFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetCategoriesInTransactionsFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.GetOldestTransactionTimestampUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction.UpdateTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.use_case.transaction_for.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.MyLocalDate
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.orMin
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionFilter
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionSortOption
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.areFiltersSelected
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.annotation.KoinViewModel

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
    private var selectedTransactionFilter = TransactionFilter()
    private var allTransactionData: ImmutableList<TransactionData> =
        persistentListOf()
    private var allTransactionForValues: ImmutableList<TransactionFor> =
        persistentListOf()
    private var selectedTransactionIndices: ImmutableList<Int> =
        persistentListOf()
    private val transactionSortOptions: ImmutableList<TransactionSortOption> =
        TransactionSortOption.entries.toImmutableList()
    private val transactionTypes: ImmutableList<TransactionType> =
        TransactionType.entries.toImmutableList()
    private val currentLocalDate: MyLocalDate =
        dateTimeKit.getCurrentLocalDate()
    private var oldestTransactionLocalDate: MyLocalDate? = null
    private var categoriesMap: Map<TransactionType, List<Category>> =
        mapOf()
    private var transactionDetailsListItemViewData: Map<String, ImmutableList<TransactionListItemData>> =
        mutableMapOf()
    private var accounts: ImmutableList<Account> = persistentListOf()
    private var selectedTransactionSortOption: TransactionSortOption =
        TransactionSortOption.LATEST_FIRST
    private var searchTextFieldState: TextFieldState = TextFieldState()
    private var screenBottomSheetType: TransactionsScreenBottomSheetType =
        TransactionsScreenBottomSheetType.None
    private var screenSnackbarType: TransactionsScreenSnackbarType =
        TransactionsScreenSnackbarType.None
    private var allTransactionDataObserverJob: Job? = null
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
            updateSelectedTransactionFilter = ::updateSelectedTransactionFilter,
            updateSelectedTransactionSortOption = ::updateSelectedTransactionSortOption,
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
        logError(
            tag = "Abhi",
            message = "TransactionsScreenViewModel: updateUiState",
        )
        _uiState.update {
            TransactionsScreenUIState(
                isBackHandlerEnabled = searchTextFieldState.text.isNotEmpty() ||
                        selectedTransactionFilter.areFiltersSelected() ||
                        isInSelectionMode,
                isBottomSheetVisible = screenBottomSheetType != TransactionsScreenBottomSheetType.None,
                isDuplicateTransactionMenuOptionVisible = selectedTransactionIndices.size == 1,
                isInSelectionMode = isInSelectionMode,
                isLoading = isLoading,
                isSearchSortAndFilterVisible = isInSelectionMode.not() && (
                        transactionDetailsListItemViewData.isNotEmpty() ||
                                searchTextFieldState.text.isNotEmpty() ||
                                selectedTransactionFilter.areFiltersSelected() ||
                                isLoading
                        ),
                selectedTransactionFilter = selectedTransactionFilter,
                selectedTransactions = selectedTransactionIndices.toImmutableList(),
                transactionForValues = allTransactionForValues,
                transactionSortOptions = transactionSortOptions,
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
                searchTextFieldState = searchTextFieldState,
                selectedTransactionSortOption = selectedTransactionSortOption,
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
                    )
                }
                .groupBy {
                    if (selectedTransactionSortOption == TransactionSortOption.LATEST_FIRST ||
                        selectedTransactionSortOption == TransactionSortOption.OLDEST_FIRST
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
        allTransactionDataObserverJob?.cancel()
        allTransactionDataObserverJob = coroutineScope.launch(
            context = dispatcherProvider.default,
        ) {
            getAllTransactionDataFlowUseCase(
                transactionFilter = selectedTransactionFilter,
                transactionSortOption = selectedTransactionSortOption,
            ).collectLatest { updatedAllTransactionData ->
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
                    updateSelectedTransactionFilter(
                        updatedSelectedTransactionFilter = selectedTransactionFilter.copy(
                            searchText = updatedSearchText,
                        ),
                    )
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

    private fun updateSelectedTransactionFilter(
        updatedSelectedTransactionFilter: TransactionFilter,
        shouldRefresh: Boolean = true,
    ): Job {
        selectedTransactionFilter = updatedSelectedTransactionFilter
        observeForAllTransactionData()
        return if (shouldRefresh) {
            coroutineScope.launch {
                refreshUiState()
            }
        } else {
            getCompletedJob()
        }
    }

    private fun updateSelectedTransactionSortOption(
        updatedSelectedTransactionSortOption: TransactionSortOption,
        shouldRefresh: Boolean = true,
    ): Job {
        selectedTransactionSortOption = updatedSelectedTransactionSortOption
        observeForAllTransactionData()
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
