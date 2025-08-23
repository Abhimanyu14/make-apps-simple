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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.view_model

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.bottom_sheet.EditCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.state.EditCategoryScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
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
            screenUIStateDelegate = testDependencies.screenUIStateDelegate,
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
            val result = awaitItem()

            result.screenBottomSheetType.shouldBe(
                expected = EditCategoryScreenBottomSheetType.None,
            )
            result.isBottomSheetVisible.shouldBeFalse()
            result.isCtaButtonEnabled.shouldBeFalse()
            result.isLoading.shouldBeTrue()
            result.isSupportingTextVisible.shouldBeFalse()
            result.titleError.shouldBe(
                expected = EditCategoryScreenTitleError.None,
            )
            result.selectedTransactionTypeIndex.shouldBeNull()
            result.transactionTypesChipUIData.shouldBeEmpty()
            result.emoji.shouldBeEmpty()
            result.emojiSearchText.shouldBeEmpty()
            result.title.text.shouldBeEmpty()
        }
    }
    // endregion

    // region updateUiStateAndStateEvents
    @Test
    fun updateUiStateAndStateEvents_titleIsBlank_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.title.text.shouldBeEmpty()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()

                editCategoryScreenViewModel.uiStateEvents.updateTitle(
                    fetchDataCompletedState.title.copy(
                        text = "   ",
                    )
                )

                val result = awaitItem()
                result.isCtaButtonEnabled.shouldBeFalse()
                result.titleError.shouldBe(
                    expected = EditCategoryScreenTitleError.None,
                )
            }
        }

    @Test
    fun updateUiStateAndStateEvents_titleIsValid_ctaIsEnabled() =
        testDependencies.runTestWithTimeout {
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.title.text.shouldBeEmpty()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()

                editCategoryScreenViewModel.uiStateEvents.updateTitle(
                    fetchDataCompletedState.title.copy(
                        text = "test-title",
                    )
                )

                val result = awaitItem()
                result.isCtaButtonEnabled.shouldBeTrue()
                result.titleError.shouldBe(
                    expected = EditCategoryScreenTitleError.None,
                )
            }
        }

    @Test
    fun updateUiStateAndStateEvents_categoryAlreadyExists_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.title.text.shouldBeEmpty()
                initialState.isLoading.shouldBeTrue()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()

                editCategoryScreenViewModel.uiStateEvents.updateTitle(
                    fetchDataCompletedState.title.copy(
                        text = testDependencies.testCategoryTitle2,
                    )
                )

                val result = awaitItem()
                result.isCtaButtonEnabled.shouldBeFalse()
                result.titleError.shouldBe(
                    expected = EditCategoryScreenTitleError.CategoryExists,
                )
            }
        }
    // endregion

    // region fetchData
    @Test
    fun fetchData_shouldUpdateCurrentCategory() =
        testDependencies.runTestWithTimeout {
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                initialState.selectedTransactionTypeIndex.shouldBeNull()

                val result = awaitItem()
                result.isLoading.shouldBeFalse()
                result.selectedTransactionTypeIndex.shouldBe(
                    expected = 1,
                )
                result.emoji.shouldBe(
                    expected = testDependencies.testCategoryEntity1.emoji,
                )
                result.title.text.shouldBe(
                    expected = testDependencies.testCategoryTitle1,
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
            initialState.title.text.shouldBeEmpty()
            val fetchDataCompletedState = awaitItem()
            fetchDataCompletedState.isLoading.shouldBeFalse()
            fetchDataCompletedState.title.text.shouldBe(
                expected = testDependencies.testCategoryTitle1,
            )

            editCategoryScreenViewModel.uiStateEvents.clearTitle()

            val result = awaitItem()
            result.title.text.shouldBeEmpty()
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
    fun updateEmojiSearchText_shouldUpdateEmojiSearchText() =
        testDependencies.runTestWithTimeout {
            val testEmojiSearchText = "test-emoji-search-text"
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                initialState.isLoading.shouldBeTrue()
                initialState.emojiSearchText.shouldBeEmpty()
                val fetchDataCompletedState = awaitItem()
                fetchDataCompletedState.isLoading.shouldBeFalse()

                editCategoryScreenViewModel.uiStateEvents.updateEmojiSearchText(
                    testEmojiSearchText
                )

                val result = awaitItem()
                result.emojiSearchText.shouldBe(
                    expected = testEmojiSearchText,
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
    fun updateTitle_shouldUpdateTitle() = testDependencies.runTestWithTimeout {
        val updatedTestTitle = "test-title"
        editCategoryScreenViewModel.uiState.test {
            val initialState = awaitItem()
            initialState.isLoading.shouldBeTrue()
            initialState.title.text.shouldBeEmpty()
            val fetchDataCompletedState = awaitItem()
            fetchDataCompletedState.isLoading.shouldBeFalse()
            fetchDataCompletedState.title.text.shouldBe(
                expected = testDependencies.testCategoryTitle1,
            )

            editCategoryScreenViewModel.uiStateEvents.updateTitle(
                fetchDataCompletedState.title.copy(
                    text = updatedTestTitle,
                )
            )

            val result = awaitItem()
            result.title.text.shouldBe(
                expected = updatedTestTitle,
            )
        }
    }
    // endregion
}
