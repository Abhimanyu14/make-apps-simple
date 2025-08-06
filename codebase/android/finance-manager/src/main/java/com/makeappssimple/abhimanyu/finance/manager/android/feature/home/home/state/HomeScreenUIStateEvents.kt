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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.home.home.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.overview_card.OverviewCardAction
import com.makeappssimple.abhimanyu.finance.manager.android.feature.home.home.bottom_sheet.HomeScreenBottomSheetType

@Stable
internal class HomeScreenUIStateEvents(
    val handleOverviewCardAction: (overviewCardAction: OverviewCardAction) -> Unit = {},
    val navigateToAccountsScreen: () -> Unit = {},
    val navigateToAddTransactionScreen: () -> Unit = {},
    val navigateToAnalysisScreen: () -> Unit = {},
    val navigateToSettingsScreen: () -> Unit = {},
    val navigateToTransactionsScreen: () -> Unit = {},
    val navigateToViewTransactionScreen: (transactionId: Int) -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val updateIsBalanceVisible: (Boolean) -> Unit = {},
    val updateOverviewTabSelectionIndex: (updatedOverviewTabSelectionIndex: Int) -> Unit = {},
    val updateScreenBottomSheetType: (HomeScreenBottomSheetType) -> Unit = {},
) : ScreenUIStateEvents
