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

import android.net.Uri
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.home.home.state.HomeScreenUIStateEvents

internal class HomeScreenUIEventHandler internal constructor(
    private val uiStateEvents: HomeScreenUIStateEvents,
    private val createDocument: ((uri: Uri?) -> Unit) -> Unit,
) : ScreenUIEventHandler<HomeScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: HomeScreenUIEvent,
    ) {
        when (uiEvent) {
            is HomeScreenUIEvent.OnBackupCardClick -> {
                createDocument {}
            }

            is HomeScreenUIEvent.OnTotalBalanceCardClick -> {
                uiStateEvents.navigateToAccountsScreen()
            }

            is HomeScreenUIEvent.OnTotalBalanceCardViewBalanceClick -> {
                uiStateEvents.updateIsBalanceVisible(true)
            }

            is HomeScreenUIEvent.OnFloatingActionButtonClick -> {
                uiStateEvents.navigateToAddTransactionScreen()
            }

            is HomeScreenUIEvent.OnOverviewCard.Click -> {
                uiStateEvents.navigateToAnalysisScreen()
            }

            is HomeScreenUIEvent.OnTopAppBarSettingsButtonClick -> {
                uiStateEvents.navigateToSettingsScreen()
            }

            is HomeScreenUIEvent.OnHomeRecentTransactionsClick -> {
                uiStateEvents.navigateToTransactionsScreen()
            }

            is HomeScreenUIEvent.OnNavigationBackButtonClick -> {}

            is HomeScreenUIEvent.OnTransactionListItemClick -> {
                uiStateEvents.navigateToViewTransactionScreen(uiEvent.transactionId)
            }

            is HomeScreenUIEvent.OnOverviewCard.Action -> {
                uiStateEvents.handleOverviewCardAction(uiEvent.overviewCardAction)
            }

            is HomeScreenUIEvent.OnOverviewCard.TabClick -> {
                uiStateEvents.updateOverviewTabSelectionIndex(uiEvent.index)
            }
        }
    }
}
