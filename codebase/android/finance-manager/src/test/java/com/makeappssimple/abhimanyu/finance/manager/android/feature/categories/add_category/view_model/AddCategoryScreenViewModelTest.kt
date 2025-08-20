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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.view_model

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.bottom_sheet.AddCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.state.AddCategoryScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.feature.test.TestDependencies
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class AddCategoryScreenViewModelTest {
    // region test setup
    private val savedStateHandle: SavedStateHandle = SavedStateHandle(
        initialState = mapOf(
            "transactionType" to TransactionType.EXPENSE.title,
        ),
    )

    private lateinit var testDependencies: TestDependencies
    private lateinit var addCategoryScreenViewModel: AddCategoryScreenViewModel

    @Before
    fun setUp() {
        testDependencies = TestDependencies()
        addCategoryScreenViewModel = AddCategoryScreenViewModel(
            navigationKit = testDependencies.navigationKit,
            savedStateHandle = savedStateHandle,
            screenUIStateDelegate = testDependencies.screenUIStateDelegate,
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
            val result = awaitItem()

            assertThat(result.screenBottomSheetType).isEqualTo(
                AddCategoryScreenBottomSheetType.None
            )
            assertThat(result.isBottomSheetVisible).isFalse()
            assertThat(result.isCtaButtonEnabled).isFalse()
            assertThat(result.isLoading).isTrue()
            assertThat(result.isSupportingTextVisible).isFalse()
            assertThat(result.titleError).isEqualTo(AddCategoryScreenTitleError.None)
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
            addCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.title.text).isEmpty()

                addCategoryScreenViewModel.uiStateEvents.updateTitle(
                    TextFieldValue(
                        text = "   ",
                    )
                )

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isFalse()
                assertThat(result.titleError).isEqualTo(
                    AddCategoryScreenTitleError.None
                )
            }
        }

    @Test
    fun updateUiStateAndStateEvents_titleIsValid_ctaIsEnabled() =
        testDependencies.runTestWithTimeout {
            addCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.title.text).isEmpty()

                addCategoryScreenViewModel.uiStateEvents.updateTitle(
                    TextFieldValue(
                        text = "test-title",
                    )
                )

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isTrue()
                assertThat(result.titleError).isEqualTo(
                    AddCategoryScreenTitleError.None
                )
            }
        }

    @Test
    fun updateUiStateAndStateEvents_categoryAlreadyExists_ctaIsDisabled() =
        testDependencies.runTestWithTimeout {
            val testTitle = "test-title"
            testDependencies.insertCategoryUseCase(
                emoji = "💰",
                title = testTitle,
                transactionType = TransactionType.INCOME,
            )

            addCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.title.text).isEmpty()

                addCategoryScreenViewModel.uiStateEvents.updateTitle(
                    TextFieldValue(
                        text = testTitle,
                    )
                )

                val result = awaitItem()
                assertThat(result.isCtaButtonEnabled).isFalse()
                assertThat(result.titleError).isEqualTo(
                    AddCategoryScreenTitleError.CategoryExists
                )
            }
        }
    // endregion

    // region fetchData
    @Test
    fun fetchData_shouldUpdateSelectedTransactionTypeIndex() =
        testDependencies.runTestWithTimeout {
            addCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.selectedTransactionTypeIndex).isNull()

                val result = awaitItem()
                assertThat(result.isLoading).isFalse()
                assertThat(result.selectedTransactionTypeIndex).isEqualTo(
                    1
                )
            }
        }
    // endregion

    // region state events
    @Test
    fun clearEmojiSearchText_shouldClearEmojiSearchText() =
        testDependencies.runTestWithTimeout {
            val testEmojiSearchText = "test-emoji-search-text"
            addCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.emojiSearchText).isEmpty()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
                addCategoryScreenViewModel.uiStateEvents.updateEmojiSearchText(
                    testEmojiSearchText
                )
                assertThat(awaitItem().emojiSearchText).isEqualTo(
                    testEmojiSearchText
                )

                addCategoryScreenViewModel.uiStateEvents.clearEmojiSearchText()

                val result = awaitItem()
                assertThat(result.emojiSearchText).isEmpty()
            }
        }

    @Test
    fun clearTitle_shouldClearTitle() = testDependencies.runTestWithTimeout {
        val testTitle = "test-title"
        addCategoryScreenViewModel.uiState.test {
            val initialState = awaitItem()
            assertThat(initialState.isLoading).isTrue()
            assertThat(initialState.title.text).isEmpty()
            val postDataFetchCompletion = awaitItem()
            assertThat(postDataFetchCompletion.isLoading).isFalse()
            addCategoryScreenViewModel.uiStateEvents.updateTitle(
                TextFieldValue(
                    text = testTitle,
                )
            )
            assertThat(awaitItem().title.text).isEqualTo(testTitle)

            addCategoryScreenViewModel.uiStateEvents.clearTitle()

            val result = awaitItem()
            assertThat(result.title.text).isEmpty()
        }
    }

    @Test
    fun resetScreenBottomSheetType_shouldSetBottomSheetTypeToNone() =
        testDependencies.runTestWithTimeout {
            val testScreenBottomSheetType =
                AddCategoryScreenBottomSheetType.SelectEmoji
            addCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.screenBottomSheetType).isEqualTo(
                    AddCategoryScreenBottomSheetType.None
                )
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
                addCategoryScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
                    testScreenBottomSheetType
                )
                assertThat(awaitItem().screenBottomSheetType).isEqualTo(
                    testScreenBottomSheetType
                )

                addCategoryScreenViewModel.uiStateEvents.resetScreenBottomSheetType()

                val result = awaitItem()
                assertThat(result.screenBottomSheetType).isEqualTo(
                    AddCategoryScreenBottomSheetType.None
                )
            }
        }

    @Test
    fun updateEmoji_shouldUpdateEmoji() = testDependencies.runTestWithTimeout {
        val testEmoji = "🤔"
        addCategoryScreenViewModel.uiState.test {
            val initialState = awaitItem()
            assertThat(initialState.isLoading).isTrue()
            assertThat(initialState.emoji).isEmpty()
            val postDataFetchCompletion = awaitItem()
            assertThat(postDataFetchCompletion.isLoading).isFalse()

            addCategoryScreenViewModel.uiStateEvents.updateEmoji(testEmoji)

            val result = awaitItem()
            assertThat(result.emoji).isEqualTo(testEmoji)
        }
    }

    @Test
    fun updateEmojiSearchText_shouldUpdateEmojiSearchText() =
        testDependencies.runTestWithTimeout {
            val testEmojiSearchText = "test-emoji-search-text"
            addCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.emojiSearchText).isEmpty()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()

                addCategoryScreenViewModel.uiStateEvents.updateEmojiSearchText(
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
                AddCategoryScreenBottomSheetType.SelectEmoji
            addCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.screenBottomSheetType).isEqualTo(
                    AddCategoryScreenBottomSheetType.None
                )
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()

                addCategoryScreenViewModel.uiStateEvents.updateScreenBottomSheetType(
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
            addCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.selectedTransactionTypeIndex).isNull()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
                assertThat(postDataFetchCompletion.selectedTransactionTypeIndex).isEqualTo(
                    1
                )

                addCategoryScreenViewModel.uiStateEvents.updateSelectedTransactionTypeIndex(
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
        val testTitle = "test-title"
        addCategoryScreenViewModel.uiState.test {
            val initialState = awaitItem()
            assertThat(initialState.isLoading).isTrue()
            assertThat(initialState.title.text).isEmpty()
            val postDataFetchCompletion = awaitItem()
            assertThat(postDataFetchCompletion.isLoading).isFalse()

            addCategoryScreenViewModel.uiStateEvents.updateTitle(
                TextFieldValue(
                    text = testTitle,
                )
            )

            val result = awaitItem()
            assertThat(result.title.text).isEqualTo(testTitle)
        }
    }
    // endregion
}
