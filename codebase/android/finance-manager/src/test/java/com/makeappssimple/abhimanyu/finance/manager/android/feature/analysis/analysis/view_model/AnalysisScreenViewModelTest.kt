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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.analysis.analysis.view_model

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.finance.manager.android.feature.analysis.analysis.bottom_sheet.AnalysisScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

internal class AnalysisScreenViewModelTest {
    // region test setup
    private lateinit var analysisScreenViewModel: AnalysisScreenViewModel
    private lateinit var testDependencies: TestDependencies

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        analysisScreenViewModel = AnalysisScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            screenUIStateDelegate = testDependencies.screenUIStateDelegate,
            coroutineScope = testDependencies.testScope.backgroundScope,
            dateTimeKit = testDependencies.dateTimeKit,
            getAllTransactionDataUseCase = testDependencies.getAllTransactionDataUseCase,
            logKit = testDependencies.logKit,
        )
        analysisScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testDependencies.testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = testDependencies.runTestWithTimeout {
        analysisScreenViewModel.uiState.test {
            val result = awaitItem()

            assertThat(result.screenBottomSheetType).isEqualTo(
                AnalysisScreenBottomSheetType.None
            )
            assertThat(result.isBottomSheetVisible).isFalse()
            assertThat(result.isLoading).isTrue()
            assertThat(result.selectedFilter).isEqualTo(Filter())
            assertThat(result.analysisListItemData).isEmpty()
            assertThat(result.transactionTypesChipUIData).isEmpty()
            assertThat(result.selectedTransactionTypeIndex).isEqualTo(0)
            assertThat(result.defaultStartLocalDate).isEqualTo(LocalDate.MIN)
            assertThat(result.defaultEndLocalDate).isEqualTo(LocalDate.MIN)
            assertThat(result.startOfCurrentMonthLocalDate).isEqualTo(LocalDate.MIN)
            assertThat(result.startOfCurrentYearLocalDate).isEqualTo(LocalDate.MIN)
        }
    }
    // endregion
}
