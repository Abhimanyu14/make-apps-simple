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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.transactions.state

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.MyLocalDate
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionFilter
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionSortOption
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.transactions.bottom_sheet.TransactionsScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.transactions.snackbar.TransactionsScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.transaction.TransactionListItemData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class TransactionsScreenUIState(
    val isBackHandlerEnabled: Boolean = false,
    val isBottomSheetVisible: Boolean = false,
    val isDuplicateTransactionMenuOptionVisible: Boolean = false,
    val isInSelectionMode: Boolean = false,
    val isLoading: Boolean = true,
    val isSearchSortAndFilterVisible: Boolean = false,
    val selectedTransactions: ImmutableList<Int> = persistentListOf(),
    val transactionForValues: ImmutableList<TransactionFor> = persistentListOf(),
    val transactionSortOptions: ImmutableList<TransactionSortOption> = persistentListOf(),
    val accounts: ImmutableList<Account> = persistentListOf(),
    val expenseCategories: ImmutableList<Category> = persistentListOf(),
    val incomeCategories: ImmutableList<Category> = persistentListOf(),
    val investmentCategories: ImmutableList<Category> = persistentListOf(),
    val transactionTypes: ImmutableList<TransactionType> = persistentListOf(),
    val currentLocalDate: MyLocalDate = MyLocalDate.MIN,
    val oldestTransactionLocalDate: MyLocalDate = MyLocalDate.MIN,
    val transactionDetailsListItemViewData: Map<String, ImmutableList<TransactionListItemData>> = emptyMap(),
    val searchTextFieldState: TextFieldState = TextFieldState(),
    val selectedTransactionSortOption: TransactionSortOption = TransactionSortOption.LATEST_FIRST,
    val selectedTransactionFilter: TransactionFilter = TransactionFilter(),
    val screenBottomSheetType: TransactionsScreenBottomSheetType = TransactionsScreenBottomSheetType.None,
    val screenSnackbarType: TransactionsScreenSnackbarType = TransactionsScreenSnackbarType.None,
) : ScreenUIState
