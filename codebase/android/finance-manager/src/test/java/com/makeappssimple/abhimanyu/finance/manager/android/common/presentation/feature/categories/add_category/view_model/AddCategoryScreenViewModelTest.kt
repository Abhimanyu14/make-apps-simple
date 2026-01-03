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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.add_category.view_model

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.add_category.bottom_sheet.AddCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.add_category.state.AddCategoryScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.navigation.FinanceManagerNavigationDirections
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class AddCategoryScreenViewModelTest {
    // region test setup
    private lateinit var addCategoryScreenViewModel: AddCategoryScreenViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var testDependencies: TestDependencies

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        savedStateHandle = SavedStateHandle(
            initialState = mapOf(
                "transactionType" to TransactionType.EXPENSE.title,
            ),
        )
        addCategoryScreenViewModel = AddCategoryScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            savedStateHandle = savedStateHandle,
            uriDecoder = testDependencies.uriDecoder,
            addCategoryScreenDataValidationUseCase = testDependencies.addCategoryScreenDataValidationUseCase,
            coroutineScope = testDependencies.testScope.backgroundScope,
            insertCategoryUseCase = testDependencies.insertCategoryUseCase,
            logKit = testDependencies.logKit,
        )
        addCategoryScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testDependencies.testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = testDependencies.runTestWithTimeout {
        addCategoryScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.isLoading.shouldBeTrue()
            initialState.selectedTransactionTypeIndex.shouldBeNull()

            val result = awaitItem()

            result.screenBottomSheetType.shouldBe(
                expected = AddCategoryScreenBottomSheetType.None,
            )
            result.isBottomSheetVisible.shouldBeFalse()
            result.isCtaButtonEnabled.shouldBeFalse()
            result.isLoading.shouldBeFalse()
            result.isSupportingTextVisible.shouldBeFalse()
            result.titleError.shouldBe(
                expected = AddCategoryScreenTitleError.None,
            )
            result.selectedTransactionTypeIndex.shouldBe(
                expected = 1,
            )
            result.transactionTypesChipUIData.shouldBe(
                expected = listOf(
                    ChipUIData(
                        text = "Income",
                    ),
                    ChipUIData(
                        text = "Expense",
                    ),
                    ChipUIData(
                        text = "Investment",
                    ),
                )
            )
            result.emoji.shouldBe(
                expected = "😃",
            )
            result.titleTextFieldState.text.toString().shouldBeEmpty()
        }
    }
    // endregion

    // region state events
    @Test
    fun clearTitle_shouldClearTitle() = testDependencies.runTestWithTimeout {
        val testTitle = "test-title"
        addCategoryScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.isLoading.shouldBeTrue()
            initialState.titleTextFieldState.text.toString().shouldBeEmpty()
            val fetchDataCompletedState = awaitItem()
            fetchDataCompletedState.isLoading.shouldBeFalse()
            addCategoryScreenViewModel.uiStateEvents.updateTitle(testTitle)
            awaitItem().titleTextFieldState.text.toString().shouldBe(
                expected = testTitle,
            )

            addCategoryScreenViewModel.uiStateEvents.clearTitle()

            val result = awaitItem()
            result.titleTextFieldState.text.toString().shouldBeEmpty()
        }
    }

    @Test
    fun insertCategory_shouldInsertCategoryAndNavigateUp() =
        testDependencies.runTestWithTimeout {
            val testEmoji = "🎁"
            val testTitle = "Gift"
            val testTransactionTypeIndex = 0 // Corresponds to INCOME
            val expectedTransactionType = TransactionType.INCOME
            turbineScope {
                val navigationCommandTurbine =
                    testDependencies.navigationKit.command.testIn(
                        scope = backgroundScope,
                    )
                val uiStateTurbine = addCategoryScreenViewModel.uiState.testIn(
                    scope = backgroundScope,
                )
                val initialState = uiStateTurbine.awaitItem()
                initialState.isLoading.shouldBeTrue()
                val fetchDataCompletedState = uiStateTurbine.awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()
                addCategoryScreenViewModel.uiStateEvents.updateEmoji(testEmoji)
                val updateEmojiCompletedState = uiStateTurbine.awaitItem()
                updateEmojiCompletedState.emoji.shouldBe(
                    expected = testEmoji,
                )
                addCategoryScreenViewModel.uiStateEvents.updateTitle(testTitle)
                val updateTitleCompletedState = uiStateTurbine.awaitItem()
                updateTitleCompletedState.titleTextFieldState.text.toString()
                    .shouldBe(
                        expected = testTitle,
                    )
                addCategoryScreenViewModel.uiStateEvents.updateSelectedTransactionTypeIndex(
                    testTransactionTypeIndex
                )
                val updateSelectedTransactionTypeIndexCompletedState =
                    uiStateTurbine.awaitItem()
                updateSelectedTransactionTypeIndexCompletedState.selectedTransactionTypeIndex.shouldBe(
                    expected = testTransactionTypeIndex,
                )

                addCategoryScreenViewModel.uiStateEvents.insertCategory().join()

                val insertCategoryCompletedState = uiStateTurbine.awaitItem()
                insertCategoryCompletedState.isLoading.shouldBeTrue()
                testDependencies.fakeCategoryDao.getAllCategories().find {
                    it.emoji == testEmoji &&
                            it.title == testTitle &&
                            it.transactionType == expectedTransactionType
                }.shouldNotBeNull()
                navigationCommandTurbine.awaitItem().shouldBe(
                    expected = FinanceManagerNavigationDirections.NavigateUp,
                )
            }
        }

    @Test
    fun resetScreenBottomSheetType_shouldSetBottomSheetTypeToNone() =
        testDependencies.runTestWithTimeout {
            val testScreenBottomSheetType =
                AddCategoryScreenBottomSheetType.SelectEmoji
            addCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                initialState.screenBottomSheetType.shouldBe(
                    expected = AddCategoryScreenBottomSheetType.None,
                )
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()
                addCategoryScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    testScreenBottomSheetType
                )
                awaitItem().screenBottomSheetType.shouldBe(
                    expected = testScreenBottomSheetType,
                )

                addCategoryScreenViewModel.uiStateEvents.resetScreenBottomSheetType()

                val result = awaitItem()
                result.screenBottomSheetType.shouldBe(
                    expected = AddCategoryScreenBottomSheetType.None,
                )
            }
        }

    @Test
    fun updateEmoji_shouldUpdateEmoji() = testDependencies.runTestWithTimeout {
        val testEmoji = "🤔"
        addCategoryScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.isLoading.shouldBeTrue()
            initialState.emoji.shouldBeEmpty()
            val fetchDataCompletedState = awaitItem()
            fetchDataCompletedState.isLoading.shouldBeFalse()

            addCategoryScreenViewModel.uiStateEvents.updateEmoji(testEmoji)

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
                AddCategoryScreenBottomSheetType.SelectEmoji
            addCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                initialState.screenBottomSheetType.shouldBe(
                    expected = AddCategoryScreenBottomSheetType.None,
                )
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()

                addCategoryScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
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
            addCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                initialState.selectedTransactionTypeIndex.shouldBeNull()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()
                fetchDataCompletedState.selectedTransactionTypeIndex.shouldBe(
                    expected = 1,
                )

                addCategoryScreenViewModel.uiStateEvents.updateSelectedTransactionTypeIndex(
                    testSelectedTransactionTypeIndex,
                )

                val result = awaitItem()
                result.selectedTransactionTypeIndex.shouldBe(
                    expected = testSelectedTransactionTypeIndex,
                )
            }
        }

    @Test
    fun updateTitle_shouldUpdateTitle() = testDependencies.runTestWithTimeout {
        val testTitle = "test-title"
        addCategoryScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.isLoading.shouldBeTrue()
            initialState.titleTextFieldState.text.toString().shouldBeEmpty()
            val fetchDataCompletedState = awaitItem()
            fetchDataCompletedState.isLoading.shouldBeFalse()

            addCategoryScreenViewModel.uiStateEvents.updateTitle(testTitle)

            val result = awaitItem()
            result.titleTextFieldState.text.toString().shouldBe(
                expected = testTitle,
            )
        }
    }

    @Test
    fun updateTitle_titleIsBlank_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            val updatedTitle = "   "
            addCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.titleTextFieldState.text.toString().shouldBeEmpty()

                addCategoryScreenViewModel.uiStateEvents.updateTitle(
                    updatedTitle
                )
                val result = awaitItem()
                result.titleTextFieldState.text.toString()
                    .shouldBe(
                        expected = updatedTitle,
                    )
                result.isCtaButtonEnabled.shouldBeFalse()
                result.titleError.shouldBe(
                    expected = AddCategoryScreenTitleError.None,
                )
            }
        }

    @Test
    fun updateTitle_titleIsValid_ctaIsEnabled() =
        testDependencies.runTestWithTimeout {
            val updatedTitle = "test-title"
            addCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.titleTextFieldState.text.toString().shouldBeEmpty()

                addCategoryScreenViewModel.uiStateEvents.updateTitle(
                    updatedTitle
                )
                val result = awaitItem()
                result.titleTextFieldState.text.toString()
                    .shouldBe(
                        expected = updatedTitle,
                    )
                result.isCtaButtonEnabled.shouldBeTrue()
                result.titleError.shouldBe(
                    expected = AddCategoryScreenTitleError.None,
                )
            }
        }

    @Test
    fun updateTitle_categoryExists_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            val updatedTitle = "test-title"
            testDependencies.insertCategoryUseCase(
                emoji = "💰",
                title = updatedTitle,
                transactionType = TransactionType.INCOME,
            )

            addCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.titleTextFieldState.text.toString().shouldBeEmpty()

                addCategoryScreenViewModel.uiStateEvents.updateTitle(
                    updatedTitle
                )
                val result = awaitItem()
                result.titleTextFieldState.text.toString()
                    .shouldBe(
                        expected = updatedTitle,
                    )
                result.isCtaButtonEnabled.shouldBeFalse()
                result.titleError.shouldBe(
                    expected = AddCategoryScreenTitleError.CategoryExists,
                )
            }
        }
    // endregion
}
