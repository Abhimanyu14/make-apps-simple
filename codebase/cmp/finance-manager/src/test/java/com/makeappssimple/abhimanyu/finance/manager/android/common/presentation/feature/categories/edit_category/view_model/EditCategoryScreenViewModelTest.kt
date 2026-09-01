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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.edit_category.view_model

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.edit_category.bottom_sheet.EditCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.edit_category.state.EditCategoryScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

internal class EditCategoryScreenViewModelTest {
    // region test setup
    private lateinit var editCategoryScreenViewModel: EditCategoryScreenViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var testDependencies: TestDependencies

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        savedStateHandle = SavedStateHandle(
            initialState = mapOf(
                "categoryId" to testDependencies.testCategoryId1,
            ),
        )
        editCategoryScreenViewModel = EditCategoryScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            savedStateHandle = savedStateHandle,
            coroutineScope = testDependencies.testScope.backgroundScope,
            editCategoryScreenDataValidationUseCase = testDependencies.editCategoryScreenDataValidationUseCase,
            getCategoryByIdUseCase = testDependencies.getCategoryByIdUseCase,
            updateCategoryUseCase = testDependencies.updateCategoryUseCase,
            logKit = testDependencies.logKit,
        )
        editCategoryScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testDependencies.testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = testDependencies.runTestWithTimeout {
        editCategoryScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.isLoading.shouldBeTrue()

            val result = awaitItem()
            result.screenBottomSheetType.shouldBe(
                expected = EditCategoryScreenBottomSheetType.None,
            )
            result.isBottomSheetVisible.shouldBeFalse()
            result.isCtaButtonEnabled.shouldBeTrue()
            result.isLoading.shouldBeFalse()
            result.isSupportingTextVisible.shouldBeFalse()
            result.titleError.shouldBe(
                expected = EditCategoryScreenTitleError.None,
            )
            result.selectedTransactionTypeIndex.shouldBe(
                expected = 1,
            )
            result.transactionTypesChipUIData.shouldBe(
                expected = listOf(
                    ChipUIData(
                        stringResource = CosmosStringResource.Text(
                            text = "Income",
                        ),
                    ),
                    ChipUIData(
                        stringResource = CosmosStringResource.Text(
                            text = "Expense",
                        ),
                    ),
                    ChipUIData(
                        stringResource = CosmosStringResource.Text(
                            text = "Investment",
                        ),
                    ),
                ),
            )
            result.emoji.shouldBe(
                expected = testDependencies.testCategoryEntity1.emoji,
            )
            result.titleTextFieldState.text.toString().shouldBe(
                expected = testDependencies.testCategoryTitle1,
            )
        }
    }
    // endregion

    // region updateUiStateAndStateEvents
    @Test
    @Ignore("To Fix")
    fun refreshUiState_titleIsBlank_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            val updatedTitle = "   "
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.titleTextFieldState.text.toString().shouldBeEmpty()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()

                editCategoryScreenViewModel.uiStateEvents.updateTitle(
                    updatedTitle
                )
                val textFieldStateUpdate = awaitItem()
                textFieldStateUpdate.titleTextFieldState.text.toString()
                    .shouldBe(
                        expected = updatedTitle,
                    )

                val result = awaitItem()
                result.isCtaButtonEnabled.shouldBeFalse()
                result.titleError.shouldBe(
                    expected = EditCategoryScreenTitleError.None,
                )
            }
        }

    @Test
    @Ignore("To Fix")
    fun refreshUiState_titleIsValid_ctaIsEnabled() =
        testDependencies.runTestWithTimeout {
            val updatedTitle = "test-title"
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.titleTextFieldState.text.toString().shouldBeEmpty()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()

                editCategoryScreenViewModel.uiStateEvents.updateTitle(
                    updatedTitle
                )
                val result = awaitItem()
                result.titleTextFieldState.shouldBe(
                    expected = updatedTitle,
                )
                result.isCtaButtonEnabled.shouldBeTrue()
                result.titleError.shouldBe(
                    expected = EditCategoryScreenTitleError.None,
                )
            }
        }

    @Test
    @Ignore("To Fix")
    fun refreshUiState_categoryExists_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            val updatedTitle = testDependencies.testCategoryTitle2
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.titleTextFieldState.text.toString().shouldBeEmpty()
                initialState.isLoading.shouldBeTrue()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()

                editCategoryScreenViewModel.uiStateEvents.updateTitle(
                    updatedTitle,
                )
                val textFieldStateUpdate = awaitItem()
                textFieldStateUpdate.titleTextFieldState.text.toString()
                    .shouldBe(
                        expected = updatedTitle,
                    )

                val result = awaitItem()
                result.isCtaButtonEnabled.shouldBeFalse()
                result.titleError.shouldBe(
                    expected = EditCategoryScreenTitleError.CategoryExists,
                )
            }
        }
    // endregion

    // region state events
    @Test
    fun clearTitle_shouldClearTitle() = testDependencies.runTestWithTimeout {
        editCategoryScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.isLoading.shouldBeTrue()
            initialState.titleTextFieldState.text.toString().shouldBeEmpty()
            val fetchDataCompletedState = awaitItem()
            fetchDataCompletedState.isLoading.shouldBeFalse()
            fetchDataCompletedState.titleTextFieldState.text.toString()
                .shouldBe(
                    expected = testDependencies.testCategoryTitle1,
                )

            editCategoryScreenViewModel.uiStateEvents.clearTitle()

            val result = awaitItem()
            result.titleTextFieldState.text.toString().shouldBeEmpty()
        }
    }

    @Test
    fun resetScreenBottomSheetType_shouldSetBottomSheetTypeToNone() =
        testDependencies.runTestWithTimeout {
            val testScreenBottomSheetType =
                EditCategoryScreenBottomSheetType.SelectEmoji
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                initialState.screenBottomSheetType.shouldBe(
                    expected = EditCategoryScreenBottomSheetType.None,
                )
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()
                editCategoryScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    testScreenBottomSheetType
                )
                awaitItem().screenBottomSheetType.shouldBe(
                    expected = testScreenBottomSheetType,
                )

                editCategoryScreenViewModel.uiStateEvents.resetScreenBottomSheetType()

                val result = awaitItem()
                result.screenBottomSheetType.shouldBe(
                    expected = EditCategoryScreenBottomSheetType.None,
                )
            }
        }

    @Test
    fun updateEmoji_shouldUpdateEmoji() = testDependencies.runTestWithTimeout {
        val testEmoji = "🤔"
        editCategoryScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.isLoading.shouldBeTrue()
            initialState.emoji.shouldBeEmpty()
            val fetchDataCompletedState = awaitItem()
            fetchDataCompletedState.isLoading.shouldBeFalse()

            editCategoryScreenViewModel.uiStateEvents.updateEmoji(testEmoji)

            val result = awaitItem()
            result.emoji.shouldBe(
                expected = testEmoji,
            )
        }
    }

    @Test
    fun updateScreenBottomSheetType_shouldUpdateBottomSheetType() =
        testDependencies.runTestWithTimeout {
            val testScreenBottomSheetType =
                EditCategoryScreenBottomSheetType.SelectEmoji
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                initialState.screenBottomSheetType.shouldBe(
                    expected = EditCategoryScreenBottomSheetType.None,
                )
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()

                editCategoryScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    testScreenBottomSheetType
                )

                val result = awaitItem()
                result.screenBottomSheetType.shouldBe(
                    expected = testScreenBottomSheetType,
                )
            }
        }

    @Test
    fun updateSelectedTransactionTypeIndex_shouldUpdateSelectedTransactionTypeIndex() =
        testDependencies.runTestWithTimeout {
            val testSelectedTransactionTypeIndex = 2
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                initialState.selectedTransactionTypeIndex.shouldBeNull()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()
                fetchDataCompletedState.selectedTransactionTypeIndex.shouldBe(
                    expected = 1,
                )

                editCategoryScreenViewModel.uiStateEvents.updateSelectedTransactionTypeIndex(
                    testSelectedTransactionTypeIndex,
                )

                val result = awaitItem()
                result.selectedTransactionTypeIndex.shouldBe(
                    expected = testSelectedTransactionTypeIndex,
                )
            }
        }

    @Test
    @Ignore("To Fix")
    fun updateTitle_shouldUpdateTitle() = testDependencies.runTestWithTimeout {
        val updatedTestTitle = "test-title"
        editCategoryScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.isLoading.shouldBeTrue()
            initialState.titleTextFieldState.text.toString().shouldBeEmpty()
            val fetchDataCompletedState = awaitItem()
            fetchDataCompletedState.isLoading.shouldBeFalse()
            fetchDataCompletedState.titleTextFieldState.text.toString()
                .shouldBe(
                    expected = testDependencies.testCategoryTitle1,
                )

            editCategoryScreenViewModel.uiStateEvents.updateTitle(
                updatedTestTitle
            )

            val result = awaitItem()
            result.titleTextFieldState.shouldBe(
                expected = updatedTestTitle,
            )
        }
    }
    // endregion
}
