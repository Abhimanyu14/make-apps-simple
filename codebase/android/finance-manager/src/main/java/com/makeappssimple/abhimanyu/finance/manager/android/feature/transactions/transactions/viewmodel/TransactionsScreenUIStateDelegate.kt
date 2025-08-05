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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.transactions.viewmodel

import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.transactions.bottomsheet.TransactionsScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableStateFlow

internal interface TransactionsScreenUIStateDelegate {
    // region initial data
    val allTransactionData: MutableStateFlow<ImmutableList<TransactionData>>
    val selectedTransactionIndices: MutableStateFlow<ImmutableList<Int>>
    val transactionDetailsListItemViewData: MutableStateFlow<Map<String, ImmutableList<TransactionListItemData>>>
    // endregion

    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val isInSelectionMode: MutableStateFlow<Boolean>
    val searchText: MutableStateFlow<String>
    val selectedFilter: MutableStateFlow<Filter>
    val selectedSortOption: MutableStateFlow<SortOption>
    val screenBottomSheetType: MutableStateFlow<TransactionsScreenBottomSheetType>
    // endregion

    // region loading
    fun startLoading()

    fun completeLoading()

    fun <T> withLoading(
        block: () -> T,
    ): T

    suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T
    // endregion

    // region state events
    fun addToSelectedTransactions(
        transactionId: Int,
    )

    fun clearSelectedTransactions()

    fun navigateUp()

    fun removeFromSelectedTransactions(
        transactionId: Int,
    )

    fun navigateToAddTransactionScreen()

    fun navigateToViewTransactionScreen(
        transactionId: Int,
    )

    fun selectAllTransactions()

    fun resetScreenBottomSheetType()

    fun updateScreenBottomSheetType(
        updatedTransactionsScreenBottomSheetType: TransactionsScreenBottomSheetType,
    )

    fun updateIsInSelectionMode(
        updatedIsInSelectionMode: Boolean,
    )

    fun updateSearchText(
        updatedSearchText: String,
    )

    fun updateSelectedFilter(
        updatedSelectedFilter: Filter,
    )

    fun updateSelectedSortOption(
        updatedSelectedSortOption: SortOption,
    )

    fun updateTransactionForValuesInTransactions(
        transactionForId: Int,
    )
    // endregion
}
