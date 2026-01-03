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

@file:OptIn(ExperimentalCoroutinesApi::class)

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.categories.view_model

import app.cash.turbine.test
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.categories.bottom_sheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.categories.snackbar.CategoriesScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.component.tab_row.MyTabData
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class CategoriesScreenViewModelTest {
    // region test setup
    private lateinit var categoriesScreenViewModel: CategoriesScreenViewModel
    private lateinit var testDependencies: TestDependencies

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        categoriesScreenViewModel = CategoriesScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            checkIfCategoryIsUsedInTransactionsUseCase = testDependencies.checkIfCategoryIsUsedInTransactionsUseCase,
            coroutineScope = testDependencies.testScope.backgroundScope,
            deleteCategoryByIdUseCase = testDependencies.deleteCategoryByIdUseCase,
            financeManagerPreferencesRepository = testDependencies.financeManagerPreferencesRepository,
            getAllCategoriesFlowUseCase = testDependencies.getAllCategoriesFlowUseCase,
            setDefaultCategoryUseCase = testDependencies.setDefaultCategoryUseCase,
            logKit = testDependencies.logKit,
        )
        categoriesScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testDependencies.testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = testDependencies.runTestWithTimeout {
        categoriesScreenViewModel.uiState.test {
            val result = awaitItem()
            result.isBottomSheetVisible.shouldBeFalse()
            result.isLoading.shouldBeFalse()
            result.screenBottomSheetType.shouldBe(
                expected = CategoriesScreenBottomSheetType.None,
            )
            result.screenSnackbarType.shouldBe(
                expected = CategoriesScreenSnackbarType.None,
            )
            result.tabData.shouldBe(
                expected = listOf(
                    MyTabData(
                        title = "Expense",
                    ),
                    MyTabData(
                        title = "Income",
                    ),
                    MyTabData(
                        title = "Investment",
                    ),
                ),
            )
            result.transactionTypeTabs.shouldBe(
                expected = listOf(
                    TransactionType.EXPENSE,
                    TransactionType.INCOME,
                    TransactionType.INVESTMENT,
                ),
            )
            result.categoriesGridItemDataMap.shouldBeEmpty()
        }
    }
    // endregion

    // region observeData
    // endregion

    // region state events
    @Test
    fun resetScreenBottomSheetType_shouldUpdateScreenBottomSheetTypeToNone() =
        testDependencies.runTestWithTimeout {
            categoriesScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeFalse()
                initialState.screenBottomSheetType.shouldBe(
                    expected = CategoriesScreenBottomSheetType.None,
                )
                categoriesScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    CategoriesScreenBottomSheetType.DeleteConfirmation
                )
                awaitItem().screenBottomSheetType.shouldBe(
                    expected = CategoriesScreenBottomSheetType.DeleteConfirmation,
                )

                categoriesScreenViewModel.uiStateEvents.resetScreenBottomSheetType()

                awaitItem().screenBottomSheetType.shouldBe(
                    expected = CategoriesScreenBottomSheetType.None,
                )
            }
        }

    @Test
    fun resetScreenSnackbarType_shouldUpdateScreenSnackbarTypeToNone() =
        testDependencies.runTestWithTimeout {
            categoriesScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeFalse()
                initialState.screenSnackbarType.shouldBe(
                    expected = CategoriesScreenSnackbarType.None,
                )
                categoriesScreenViewModel.uiStateEvents.updateScreenSnackbarType(
                    CategoriesScreenSnackbarType.SetDefaultCategorySuccessful
                )
                awaitItem().screenSnackbarType.shouldBe(
                    expected = CategoriesScreenSnackbarType.SetDefaultCategorySuccessful,
                )

                categoriesScreenViewModel.uiStateEvents.resetScreenSnackbarType()

                awaitItem().screenSnackbarType.shouldBe(
                    expected = CategoriesScreenSnackbarType.None,
                )
            }
        }

    @Test
    fun updateScreenBottomSheetType_shouldUpdateScreenBottomSheetType() =
        testDependencies.runTestWithTimeout {
            categoriesScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeFalse()
                initialState.screenBottomSheetType.shouldBe(
                    expected = CategoriesScreenBottomSheetType.None,
                )

                categoriesScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    CategoriesScreenBottomSheetType.DeleteConfirmation
                )
                val result = awaitItem()
                result.screenBottomSheetType.shouldBe(
                    expected = CategoriesScreenBottomSheetType.DeleteConfirmation,
                )
            }
        }

    @Test
    fun updateScreenSnackbarType_shouldUpdateScreenSnackbarType() =
        testDependencies.runTestWithTimeout {
            categoriesScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeFalse()
                initialState.screenSnackbarType.shouldBe(
                    expected = CategoriesScreenSnackbarType.None,
                )

                categoriesScreenViewModel.uiStateEvents.updateScreenSnackbarType(
                    CategoriesScreenSnackbarType.SetDefaultCategorySuccessful
                )
                val result = awaitItem()
                result.screenSnackbarType.shouldBe(
                    expected = CategoriesScreenSnackbarType.SetDefaultCategorySuccessful,
                )
            }
        }
    // endregion
}
