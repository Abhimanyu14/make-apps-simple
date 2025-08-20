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

@file:OptIn(ExperimentalCoroutinesApi::class)

package com.makeappssimple.abhimanyu.finance.manager.android.feature.home.home.view_model

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.finance.manager.android.core.chart.compose_pie.data.PieChartData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.overview_card.OverviewCardViewModelData
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class HomeScreenViewModelTest {
    // region test setup
    private lateinit var testDependencies: TestDependencies
    private lateinit var homeScreenViewModel: HomeScreenViewModel

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        homeScreenViewModel = HomeScreenViewModel(
            getAccountsTotalBalanceAmountValueUseCase = testDependencies.getAccountsTotalBalanceAmountValueUseCase,
            getAccountsTotalMinimumBalanceAmountValueUseCase = testDependencies.getAccountsTotalMinimumBalanceAmountValueUseCase,
            navigationKit = testDependencies.navigationKit,
            screenUIStateDelegate = testDependencies.screenUIStateDelegate,
            shouldShowBackupCardUseCase = testDependencies.shouldShowBackupCardUseCase,
            backupDataUseCase = testDependencies.backupDataUseCase,
            coroutineScope = testDependencies.testScope.backgroundScope,
            dateTimeKit = testDependencies.dateTimeKit,
            getRecentTransactionDataFlowUseCase = testDependencies.getRecentTransactionDataFlowUseCase,
            getTransactionByIdUseCase = testDependencies.getTransactionByIdUseCase,
            getTransactionsBetweenTimestampsUseCase = testDependencies.getTransactionsBetweenTimestampsUseCase,
            logKit = testDependencies.logKit,
        )
        homeScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testDependencies.testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = testDependencies.runTestWithTimeout {
        homeScreenViewModel.uiState.test {
            val result = awaitItem()

            assertThat(result.isBackupCardVisible).isFalse()
            assertThat(result.isBalanceVisible).isFalse()
            assertThat(result.isLoading).isTrue()
            assertThat(result.isRecentTransactionsTrailingTextVisible).isFalse()
            assertThat(result.overviewTabSelectionIndex).isEqualTo(0)
            assertThat(result.transactionListItemDataList).isEmpty()
            assertThat(result.accountsTotalBalanceAmountValue).isEqualTo(0)
            assertThat(result.accountsTotalMinimumBalanceAmountValue).isEqualTo(
                0
            )
            assertThat(result.overviewCardData).isEqualTo(
                OverviewCardViewModelData()
            )
            assertThat(result.pieChartData).isEqualTo(PieChartData())
        }
    }
    // endregion
}
