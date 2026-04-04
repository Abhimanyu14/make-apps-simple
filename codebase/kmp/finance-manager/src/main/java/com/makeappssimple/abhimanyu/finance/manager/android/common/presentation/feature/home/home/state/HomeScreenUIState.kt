/*
 * Copyright 2025-2026 Abhimanyu
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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.home.home.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.overview_card.OverviewCardViewModelData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class HomeScreenUIState(
    val isBackupCardVisible: Boolean = false,
    val isBalanceVisible: Boolean = false,
    val isLoading: Boolean = true,
    val isRecentTransactionsTrailingTextVisible: Boolean = false,
    val overviewTabSelectionIndex: Int = 0,
    val transactionListItemDataList: ImmutableList<TransactionListItemData> = persistentListOf(),
    val accountsTotalBalanceAmountValue: Long = 0L,
    val allAccountsTotalMinimumBalanceAmountValue: Long = 0L,
    val overviewCardData: OverviewCardViewModelData = OverviewCardViewModelData(),
) : ScreenUIState
