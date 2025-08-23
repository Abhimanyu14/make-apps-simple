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
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.bottom_sheet.EditCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.state.EditCategoryScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.test.TestDependencies
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

            assertThat(result.screenBottomSheetType).isEqualTo(
                EditCategoryScreenBottomSheetType.None
            )
            assertThat(result.isBottomSheetVisible).isFalse()
            assertThat(result.isCtaButtonEnabled).isFalse()
            assertThat(result.isLoading).isTrue()
            assertThat(result.isSupportingTextVisible).isFalse()
            assertThat(result.titleError).isEqualTo(EditCategoryScreenTitleError.None)
            assertThat(result.selectedTransactionTypeIndex).isNull()
            assertThat(result.transactionTypesChipUIData).isEmpty()
            assertThat(result.emoji).isEmpty()
            assertThat(result.emojiSearchText).isEmpty()
            assertThat(result.title.text).isEmpty()
        }
    }
    // endregion

    // region updateUiStateAndStateEvents
    @Test
    fun updateUiStateAndStateEvents_titleIsBlank_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.title.text).isEmpty()
                val fetchDataCompletedState = awaitItem()
                assertThat(fetchDataCompletedState.isLoading).isFalse()

                editCategoryScreenViewModel.uiStateEvents.updateTitle(
                    fetchDataCompletedState.title.copy(
                        text = "   ",
                    )
                )

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isFalse()
                assertThat(result.titleError).isEqualTo(
                    EditCategoryScreenTitleError.None
                )
            }
        }

    @Test
    fun updateUiStateAndStateEvents_titleIsValid_ctaIsEnabled() =
        testDependencies.runTestWithTimeout {
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.title.text).isEmpty()
                val fetchDataCompletedState = awaitItem()
                assertThat(fetchDataCompletedState.isLoading).isFalse()

                editCategoryScreenViewModel.uiStateEvents.updateTitle(
                    fetchDataCompletedState.title.copy(
                        text = "test-title",
                    )
                )

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isTrue()
                assertThat(result.titleError).isEqualTo(
                    EditCategoryScreenTitleError.None
                )
            }
        }

    @Test
    fun updateUiStateAndStateEvents_categoryAlreadyExists_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.title.text).isEmpty()
                assertThat(initialState.isLoading).isTrue()
                val fetchDataCompletedState = awaitItem()
                assertThat(fetchDataCompletedState.isLoading).isFalse()

                editCategoryScreenViewModel.uiStateEvents.updateTitle(
                    fetchDataCompletedState.title.copy(
                        text = testDependencies.testCategoryTitle2,
                    )
                )

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isFalse()
                assertThat(result.titleError).isEqualTo(
                    EditCategoryScreenTitleError.CategoryExists
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
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.selectedTransactionTypeIndex).isNull()

                val result = awaitItem()
                assertThat(result.isLoading).isFalse()
                assertThat(result.selectedTransactionTypeIndex).isEqualTo(1)
                assertThat(result.emoji).isEqualTo(testDependencies.testCategoryEntity1.emoji)
                assertThat(result.title.text).isEqualTo(testDependencies.testCategoryTitle1)
            }
        }
    // endregion

    // region state events
    @Test
    fun clearTitle_shouldClearTitle() = testDependencies.runTestWithTimeout {
        editCategoryScreenViewModel.uiState.test {
            val initialState = awaitItem()
            assertThat(initialState.isLoading).isTrue()
            assertThat(initialState.title.text).isEmpty()
            val fetchDataCompletedState = awaitItem()
            assertThat(fetchDataCompletedState.isLoading).isFalse()
            assertThat(fetchDataCompletedState.title.text).isEqualTo(
                testDependencies.testCategoryTitle1
            )

            editCategoryScreenViewModel.uiStateEvents.clearTitle()

            val result = awaitItem()
            assertThat(result.title.text).isEmpty()
        }
    }

    @Test
    fun resetScreenBottomSheetType_shouldSetBottomSheetTypeToNone() =
        testDependencies.runTestWithTimeout {
            val testScreenBottomSheetType =
                EditCategoryScreenBottomSheetType.SelectEmoji
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.screenBottomSheetType).isEqualTo(
                    EditCategoryScreenBottomSheetType.None
                )
                val fetchDataCompletedState = awaitItem()
                assertThat(fetchDataCompletedState.isLoading).isFalse()
                editCategoryScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    testScreenBottomSheetType
                )
                assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                    testScreenBottomSheetType
                )

                editCategoryScreenViewModel.uiStateEvents.resetScreenBottomSheetType()

                val result = awaitItem()
                assertThat(result.screenBottomSheetType).isEqualTo(
                    EditCategoryScreenBottomSheetType.None
                )
            }
        }

    @Test
    fun updateEmoji_shouldUpdateEmoji() = testDependencies.runTestWithTimeout {
        val testEmoji = "🤔"
        editCategoryScreenViewModel.uiState.test {
            val initialState = awaitItem()
            assertThat(initialState.isLoading).isTrue()
            assertThat(initialState.emoji).isEmpty()
            val fetchDataCompletedState = awaitItem()
            assertThat(fetchDataCompletedState.isLoading).isFalse()

            editCategoryScreenViewModel.uiStateEvents.updateEmoji(testEmoji)

            val result = awaitItem()
            assertThat(result.emoji).isEqualTo(testEmoji)
        }
    }

    @Test
    fun updateEmojiSearchText_shouldUpdateEmojiSearchText() =
        testDependencies.runTestWithTimeout {
            val testEmojiSearchText = "test-emoji-search-text"
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.emojiSearchText).isEmpty()
                val fetchDataCompletedState = awaitItem()
                assertThat(fetchDataCompletedState.isLoading).isFalse()

                editCategoryScreenViewModel.uiStateEvents.updateEmojiSearchText(
                    testEmojiSearchText
                )

                val result = awaitItem()
                assertThat(result.emojiSearchText).isEqualTo(testEmojiSearchText)
            }
        }

    @Test
    fun updateScreenBottomSheetType_shouldUpdateBottomSheetType() =
        testDependencies.runTestWithTimeout {
            val testScreenBottomSheetType =
                EditCategoryScreenBottomSheetType.SelectEmoji
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.screenBottomSheetType).isEqualTo(
                    EditCategoryScreenBottomSheetType.None
                )
                val fetchDataCompletedState = awaitItem()
                assertThat(fetchDataCompletedState.isLoading).isFalse()

                editCategoryScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    testScreenBottomSheetType
                )

                val result = awaitItem()
                assertThat(result.screenBottomSheetType).isEqualTo(
                    testScreenBottomSheetType
                )
            }
        }

    @Test
    fun updateSelectedTransactionTypeIndex_shouldUpdateSelectedTransactionTypeIndex() =
        testDependencies.runTestWithTimeout {
            val testSelectedTransactionTypeIndex = 2
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.selectedTransactionTypeIndex).isNull()
                val fetchDataCompletedState = awaitItem()
                assertThat(fetchDataCompletedState.isLoading).isFalse()
                assertThat(fetchDataCompletedState.selectedTransactionTypeIndex).isEqualTo(
                    1
                )

                editCategoryScreenViewModel.uiStateEvents.updateSelectedTransactionTypeIndex(
                    testSelectedTransactionTypeIndex,
                )

                val result = awaitItem()
                assertThat(result.selectedTransactionTypeIndex).isEqualTo(
                    testSelectedTransactionTypeIndex,
                )
            }
        }

    @Test
    fun updateTitle_shouldUpdateTitle() = testDependencies.runTestWithTimeout {
        val updatedTestTitle = "test-title"
        editCategoryScreenViewModel.uiState.test {
            val initialState = awaitItem()
            assertThat(initialState.isLoading).isTrue()
            assertThat(initialState.title.text).isEmpty()
            val fetchDataCompletedState = awaitItem()
            assertThat(fetchDataCompletedState.isLoading).isFalse()
            assertThat(fetchDataCompletedState.title.text).isEqualTo(
                testDependencies.testCategoryTitle1
            )

            editCategoryScreenViewModel.uiStateEvents.updateTitle(
                fetchDataCompletedState.title.copy(
                    text = updatedTestTitle,
                )
            )

            val result = awaitItem()
            assertThat(result.title.text).isEqualTo(updatedTestTitle)
        }
    }
    // endregion
}
