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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.transactions.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.transactions.bottom_sheet.TransactionsScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

@Stable
internal data class TransactionsScreenUIState(
    val isBackHandlerEnabled: Boolean = false,
    val isBottomSheetVisible: Boolean = false,
    val isDuplicateTransactionMenuOptionVisible: Boolean = false,
    val isInSelectionMode: Boolean = false,
    val isLoading: Boolean = true,
    val isSearchSortAndFilterVisible: Boolean = false,
    val selectedFilter: Filter = Filter(),
    val selectedTransactions: ImmutableList<Int> = persistentListOf(),
    val sortOptions: ImmutableList<SortOption> = persistentListOf(),
    val transactionForValues: ImmutableList<TransactionFor> = persistentListOf(),
    val accounts: ImmutableList<Account> = persistentListOf(),
    val expenseCategories: ImmutableList<Category> = persistentListOf(),
    val incomeCategories: ImmutableList<Category> = persistentListOf(),
    val investmentCategories: ImmutableList<Category> = persistentListOf(),
    val transactionTypes: ImmutableList<TransactionType> = persistentListOf(),
    val currentLocalDate: LocalDate = LocalDate.MIN,
    val oldestTransactionLocalDate: LocalDate = LocalDate.MIN,
    val transactionDetailsListItemViewData: Map<String, ImmutableList<TransactionListItemData>> = emptyMap(),
    val selectedSortOption: SortOption = SortOption.LATEST_FIRST,
    val searchText: String = "",
    val screenBottomSheetType: TransactionsScreenBottomSheetType = TransactionsScreenBottomSheetType.None,
) : ScreenUIState
