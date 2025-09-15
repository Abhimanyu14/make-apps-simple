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

package com.makeappssimple.abhimanyu.finance.manager.android.common.feature.transactions.transactions.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.feature.Filter
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.transactions.transactions.bottom_sheet.TransactionsScreenBottomSheetType
import kotlinx.coroutines.Job

@Stable
internal class TransactionsScreenUIStateEvents(
    val addToSelectedTransactions: (transactionId: Int) -> Job,
    val clearSelectedTransactions: () -> Job,
    val duplicateTransaction: () -> Job,
    val navigateToAddTransactionScreen: () -> Job,
    val navigateToViewTransactionScreen: (transactionId: Int) -> Job,
    val navigateUp: () -> Job,
    val removeFromSelectedTransactions: (transactionId: Int) -> Job,
    val resetScreenBottomSheetType: () -> Job,
    val selectAllTransactions: () -> Job,
    val updateIsInSelectionMode: (Boolean) -> Job,
    val updateScreenBottomSheetType: (TransactionsScreenBottomSheetType) -> Job,
    val updateSearchText: (updatedSearchText: String) -> Job,
    val updateSelectedFilter: (updatedSelectedFilter: Filter) -> Job,
    val updateSelectedSortOption: (updatedSelectedSortOption: SortOption) -> Job,
    val updateTransactionForValuesInTransactions: (transactionForId: Int) -> Job,
) : ScreenUIStateEvents
