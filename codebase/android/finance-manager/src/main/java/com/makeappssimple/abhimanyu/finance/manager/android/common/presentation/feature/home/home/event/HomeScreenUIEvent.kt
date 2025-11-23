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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.home.home.event

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.base.ScreenUIEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.overview_card.OverviewCardAction

@Immutable
internal sealed class HomeScreenUIEvent : ScreenUIEvent {
    data object OnBackupCardClick : HomeScreenUIEvent()
    data object OnFloatingActionButtonClick : HomeScreenUIEvent()
    data object OnHomeRecentTransactionsClick : HomeScreenUIEvent()
    data object OnNavigationBackButtonClick : HomeScreenUIEvent()
    data object OnTopAppBarSettingsButtonClick : HomeScreenUIEvent()
    data object OnTotalBalanceCardClick : HomeScreenUIEvent()
    data object OnTotalBalanceCardViewBalanceClick : HomeScreenUIEvent()

    data class OnTransactionListItemClick(
        val transactionId: Int,
    ) : HomeScreenUIEvent()

    sealed class OnOverviewCard {
        data object Click : HomeScreenUIEvent()

        data class Action(
            val overviewCardAction: OverviewCardAction,
        ) : HomeScreenUIEvent()

        data class TabClick(
            val index: Int,
        ) : HomeScreenUIEvent()
    }
}
