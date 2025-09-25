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

package com.makeappssimple.abhimanyu.finance.manager.android.common.feature.transactions.transactions.event

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.feature.Filter
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.base.ScreenUIEvent

@Immutable
internal sealed class TransactionsScreenUIEvent : ScreenUIEvent {
    data object OnBottomSheetDismissed : TransactionsScreenUIEvent()
    data object OnSelectionModeTopAppBarNavigationButtonClick :
        TransactionsScreenUIEvent()

    data object OnSelectionModeTopAppBarMoreOptionsButtonClick :
        TransactionsScreenUIEvent()

    data object OnFloatingActionButtonClick : TransactionsScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : TransactionsScreenUIEvent()
    data object OnNavigationBackButtonClick : TransactionsScreenUIEvent()
    data object OnSnackbarDismissed : TransactionsScreenUIEvent()
    data object OnSortActionButtonClick : TransactionsScreenUIEvent()
    data object OnFilterActionButtonClick : TransactionsScreenUIEvent()

    data class OnSelectedFilterUpdated(
        val updatedSelectedFilter: Filter,
    ) : TransactionsScreenUIEvent()

    data class OnSelectedSortOptionUpdated(
        val updatedSelectedSortOption: SortOption,
    ) : TransactionsScreenUIEvent()

    sealed class OnSelectTransactionForBottomSheet {
        data class ItemClick(
            val updatedTransactionForValues: Int,
        ) : TransactionsScreenUIEvent()
    }

    sealed class OnTransactionListItem {
        data class Click(
            val isInSelectionMode: Boolean,
            val isSelected: Boolean,
            val transactionId: Int,
        ) : TransactionsScreenUIEvent()

        data class LongClick(
            val isInSelectionMode: Boolean,
            val isSelected: Boolean,
            val transactionId: Int,
        ) : TransactionsScreenUIEvent()
    }

    sealed class OnTransactionsMenuBottomSheet {
        data object DuplicateTransactionButtonClick :
            TransactionsScreenUIEvent()

        data object SelectAllTransactionsButtonClick :
            TransactionsScreenUIEvent()

        data object UpdateTransactionForButtonClick :
            TransactionsScreenUIEvent()
    }
}
