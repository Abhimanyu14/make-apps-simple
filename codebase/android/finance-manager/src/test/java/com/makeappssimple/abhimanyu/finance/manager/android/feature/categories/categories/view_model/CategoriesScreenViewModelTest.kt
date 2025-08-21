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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.view_model

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.bottom_sheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.categories.snackbar.CategoriesScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
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
            screenUIStateDelegate = testDependencies.screenUIStateDelegate,
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
            assertThat(result.isBottomSheetVisible).isFalse()
            assertThat(result.isLoading).isTrue()
            assertThat(result.screenBottomSheetType).isEqualTo(
                CategoriesScreenBottomSheetType.None
            )
            assertThat(result.screenSnackbarType).isEqualTo(
                CategoriesScreenSnackbarType.None
            )
            assertThat(result.tabData).isEmpty()
            assertThat(result.validTransactionTypes).isEmpty()
            assertThat(result.categoriesGridItemDataMap).isEmpty()
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
                assertThat(initialState.isLoading).isTrue()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
                assertThat(postDataFetchCompletion.screenBottomSheetType).isEqualTo(
                    CategoriesScreenBottomSheetType.None
                )
                categoriesScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    CategoriesScreenBottomSheetType.DeleteConfirmation
                )
                assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                    CategoriesScreenBottomSheetType.DeleteConfirmation
                )

                categoriesScreenViewModel.uiStateEvents.resetScreenBottomSheetType()

                assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                    CategoriesScreenBottomSheetType.None
                )
            }
        }

    @Test
    fun resetScreenSnackbarType_shouldUpdateScreenSnackbarTypeToNone() =
        testDependencies.runTestWithTimeout {
            categoriesScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
                assertThat(postDataFetchCompletion.screenSnackbarType).isEqualTo(
                    CategoriesScreenSnackbarType.None
                )
                categoriesScreenViewModel.uiStateEvents.updateScreenSnackbarType(
                    CategoriesScreenSnackbarType.SetDefaultCategorySuccessful
                )
                assertThat(awaitItem().screenSnackbarType).isEqualTo(
                    CategoriesScreenSnackbarType.SetDefaultCategorySuccessful
                )

                categoriesScreenViewModel.uiStateEvents.resetScreenSnackbarType()

                assertThat(awaitItem().screenSnackbarType).isEqualTo(
                    CategoriesScreenSnackbarType.None
                )
            }
        }

    @Test
    fun updateScreenBottomSheetType_shouldUpdateScreenBottomSheetType() =
        testDependencies.runTestWithTimeout {
            categoriesScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
                assertThat(postDataFetchCompletion.screenBottomSheetType).isEqualTo(
                    CategoriesScreenBottomSheetType.None
                )

                categoriesScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    CategoriesScreenBottomSheetType.DeleteConfirmation
                )
                val result = awaitItem()
                assertThat(result.screenBottomSheetType).isEqualTo(
                    CategoriesScreenBottomSheetType.DeleteConfirmation
                )
            }
        }

    @Test
    fun updateScreenSnackbarType_shouldUpdateScreenSnackbarType() =
        testDependencies.runTestWithTimeout {
            categoriesScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
                assertThat(postDataFetchCompletion.screenSnackbarType).isEqualTo(
                    CategoriesScreenSnackbarType.None
                )

                categoriesScreenViewModel.uiStateEvents.updateScreenSnackbarType(
                    CategoriesScreenSnackbarType.SetDefaultCategorySuccessful
                )
                val result = awaitItem()
                assertThat(result.screenSnackbarType).isEqualTo(
                    CategoriesScreenSnackbarType.SetDefaultCategorySuccessful
                )
            }
        }
    // endregion
}
