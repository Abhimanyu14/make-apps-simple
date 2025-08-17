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
import com.makeappssimple.abhimanyu.common.core.coroutines.test.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.log_kit.fake.FakeLogKitImpl
import com.makeappssimple.abhimanyu.common.core.uri_decoder.UriDecoder
import com.makeappssimple.abhimanyu.common.core.uri_decoder.fake.FakeUriDecoderImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.category.CategoryRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.InsertCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeCategoryDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.FinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.fake.FakeFinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegateImpl
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.bottom_sheet.AddCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.state.AddCategoryScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.use_case.AddCategoryScreenDataValidationUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

internal class AddCategoryScreenViewModelTest {
    // region coroutines setup
    private val testCoroutineDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(
        context = testCoroutineDispatcher + Job(),
    )
    private val testDispatcherProvider = TestDispatcherProviderImpl(
        testDispatcher = testCoroutineDispatcher,
    )
    // endregion

    // region test setup
    private val navigationKit: NavigationKit = NavigationKitImpl(
        coroutineScope = testScope.backgroundScope,
    )
    private val savedStateHandle: SavedStateHandle = SavedStateHandle(
        initialState = mapOf(
            "transactionType" to TransactionType.EXPENSE.title,
        ),
    )
    private val screenUIStateDelegate: ScreenUIStateDelegate =
        ScreenUIStateDelegateImpl(
            coroutineScope = testScope.backgroundScope,
        )
    private val uriDecoder: UriDecoder = FakeUriDecoderImpl()
    private val fakeCategoryDao: CategoryDao = FakeCategoryDaoImpl()
    private val categoryRepository: CategoryRepository = CategoryRepositoryImpl(
        categoryDao = fakeCategoryDao,
        dispatcherProvider = testDispatcherProvider,
    )
    private val getAllCategoriesUseCase = GetAllCategoriesUseCase(
        categoryRepository = categoryRepository,
    )
    private val addCategoryScreenDataValidationUseCase =
        AddCategoryScreenDataValidationUseCase(
            getAllCategoriesUseCase = getAllCategoriesUseCase,
        )
    private val financeManagerPreferencesDataSource: FinanceManagerPreferencesDataSource =
        FakeFinanceManagerPreferencesDataSource()
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository =
        FinanceManagerPreferencesRepositoryImpl(
            dispatcherProvider = testDispatcherProvider,
            financeManagerPreferencesDataSource = financeManagerPreferencesDataSource,
        )
    private val insertCategoriesUseCase: InsertCategoriesUseCase =
        InsertCategoriesUseCase(
            categoryRepository = categoryRepository,
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    private val insertCategoryUseCase = InsertCategoryUseCase(
        insertCategoriesUseCase = insertCategoriesUseCase,
    )
    private val logKit: LogKit = FakeLogKitImpl()

    private lateinit var addCategoryScreenViewModel: AddCategoryScreenViewModel

    @Before
    fun setUp() {
        addCategoryScreenViewModel = AddCategoryScreenViewModel(
            navigationKit = navigationKit,
            savedStateHandle = savedStateHandle,
            screenUIStateDelegate = screenUIStateDelegate,
            uriDecoder = uriDecoder,
            addCategoryScreenDataValidationUseCase = addCategoryScreenDataValidationUseCase,
            coroutineScope = testScope.backgroundScope,
            insertCategoryUseCase = insertCategoryUseCase,
            logKit = logKit,
        )
        addCategoryScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = runTestWithTimeout {
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
        runTestWithTimeout {
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
        runTestWithTimeout {
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
        runTestWithTimeout {
            val testTitle = "test-title"
            insertCategoryUseCase(
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
        runTestWithTimeout {
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
    fun clearEmojiSearchText_shouldClearEmojiSearchText() = runTestWithTimeout {
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
    fun clearTitle_shouldClearTitle() = runTestWithTimeout {
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
        runTestWithTimeout {
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
    fun updateEmoji_shouldUpdateEmoji() = runTestWithTimeout {
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
    fun updateScreenBottomSheetType_shouldUpdateBottomSheetType() =
        runTestWithTimeout {
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
    fun updateEmojiSearchText_shouldUpdateEmojiSearchText() =
        runTestWithTimeout {
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
    fun updateSelectedTransactionTypeIndex_shouldUpdateSelectedTransactionTypeIndex() =
        runTestWithTimeout {
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
    fun updateTitle_shouldUpdateTitle() = runTestWithTimeout {
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

    // region common
    private fun runTestWithTimeout(
        testBody: suspend TestScope.() -> Unit,
    ) {
        testScope.runTest(
            timeout = 3.seconds,
        ) {
            testBody()
        }
    }
    // endregion
}
