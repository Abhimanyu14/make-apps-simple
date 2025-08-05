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
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.transactions.bottomsheet.TransactionsScreenBottomSheetType

@Stable
internal class TransactionsScreenUIStateEvents(
    val addToSelectedTransactions: (transactionId: Int) -> Unit = {},
    val clearSelectedTransactions: () -> Unit = {},
    val navigateToAddTransactionScreen: () -> Unit = {},
    val navigateToViewTransactionScreen: (transactionId: Int) -> Unit = {},
    val navigateUp: () -> Unit = {},
    val removeFromSelectedTransactions: (transactionId: Int) -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val selectAllTransactions: () -> Unit = {},
    val updateIsInSelectionMode: (Boolean) -> Unit = {},
    val updateScreenBottomSheetType: (TransactionsScreenBottomSheetType) -> Unit = {},
    val updateSearchText: (updatedSearchText: String) -> Unit = {},
    val updateSelectedFilter: (updatedSelectedFilter: Filter) -> Unit = {},
    val updateSelectedSortOption: (updatedSelectedSortOption: SortOption) -> Unit = {},
    val updateTransactionForValuesInTransactions: (transactionForId: Int) -> Unit = {},
) : ScreenUIStateEvents
