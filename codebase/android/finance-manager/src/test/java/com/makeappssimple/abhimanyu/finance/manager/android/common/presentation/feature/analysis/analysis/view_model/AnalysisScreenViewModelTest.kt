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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.analysis.analysis.view_model

import app.cash.turbine.test
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.MyLocalDate
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.analysis.analysis.bottom_sheet.AnalysisScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.ints.shouldBeZero
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class AnalysisScreenViewModelTest {
    // region test setup
    private lateinit var analysisScreenViewModel: AnalysisScreenViewModel
    private lateinit var testDependencies: TestDependencies

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        analysisScreenViewModel = AnalysisScreenViewModel(
            navigationKit = testDependencies.navigationKit,
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

            result.screenBottomSheetType.shouldBe(
                expected = AnalysisScreenBottomSheetType.None,
            )
            result.isBottomSheetVisible.shouldBeFalse()
            result.isLoading.shouldBeTrue()
            result.selectedFilter.shouldBe(
                expected = Filter(),
            )
            result.analysisListItemData.shouldBeEmpty()
            result.transactionTypesChipUIData.shouldBeEmpty()
            result.selectedTransactionTypeIndex.shouldBeZero()
            result.defaultStartLocalDate.shouldBe(
                expected = MyLocalDate.MIN,
            )
            result.defaultEndLocalDate.shouldBe(
                expected = MyLocalDate.MIN,
            )
            result.startOfCurrentMonthLocalDate.shouldBe(
                expected = MyLocalDate.MIN,
            )
            result.startOfCurrentYearLocalDate.shouldBe(
                expected = MyLocalDate.MIN,
            )
        }
    }
    // endregion
}
