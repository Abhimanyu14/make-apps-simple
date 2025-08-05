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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.home.home.viewmodel

import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.overview_card.OverviewCardAction
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.overview_card.OverviewCardViewModelData
import com.makeappssimple.abhimanyu.finance.manager.android.feature.home.home.bottomsheet.HomeScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableStateFlow

internal interface HomeScreenUIStateDelegate {
    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val isBalanceVisible: MutableStateFlow<Boolean>
    val screenBottomSheetType: MutableStateFlow<HomeScreenBottomSheetType>
    val homeListItemViewData: MutableStateFlow<ImmutableList<TransactionListItemData>>
    val overviewTabSelectionIndex: MutableStateFlow<Int>
    val selectedTimestamp: MutableStateFlow<Long>
    val overviewCardData: MutableStateFlow<OverviewCardViewModelData?>
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
    fun handleOverviewCardAction(
        overviewCardAction: OverviewCardAction,
    )

    fun navigateToAccountsScreen()

    fun navigateToAddTransactionScreen()

    fun navigateToAnalysisScreen()

    fun navigateToSettingsScreen()

    fun navigateToTransactionsScreen()

    fun navigateToViewTransactionScreen(
        transactionId: Int,
    )

    fun resetScreenBottomSheetType()

    fun updateIsBalanceVisible(
        updatedIsBalanceVisible: Boolean,
    )

    fun updateOverviewTabSelectionIndex(
        updatedOverviewTabSelectionIndex: Int,
    )

    fun updateScreenBottomSheetType(
        updatedHomeScreenBottomSheetType: HomeScreenBottomSheetType,
    )
    // endregion
}
