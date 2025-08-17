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
import com.makeappssimple.abhimanyu.common.core.coroutines.test.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.log_kit.fake.FakeLogKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.category.CategoryRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.GetCategoryByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.UpdateCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.UpdateCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake.FakeCategoryDaoImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.FinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.fake.FakeFinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKitImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegateImpl
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.bottom_sheet.EditCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.state.EditCategoryScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.use_case.EditCategoryScreenDataValidationUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

internal class EditCategoryScreenViewModelTest {
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
    private val testCategoryId1 = 1
    private val testEmoji1 = "💰"
    private val testTitle1 = "test-title-1"
    private val testTransactionType1 = TransactionType.EXPENSE
    private val testCategory1 = CategoryEntity(
        id = testCategoryId1,
        emoji = testEmoji1,
        title = testTitle1,
        transactionType = testTransactionType1,
    )
    private val testCategoryId2 = 2
    private val testEmoji2 = "💳"
    private val testTitle2 = "test-title-2"
    private val testTransactionType2 = TransactionType.INCOME
    private val testCategory2 = CategoryEntity(
        id = testCategoryId2,
        emoji = testEmoji2,
        title = testTitle2,
        transactionType = testTransactionType2,
    )
    private val savedStateHandle: SavedStateHandle = SavedStateHandle(
        initialState = mapOf(
            "categoryId" to testCategoryId1,
        ),
    )
    private val screenUIStateDelegate: ScreenUIStateDelegate =
        ScreenUIStateDelegateImpl(
            coroutineScope = testScope.backgroundScope,
        )
    private val fakeCategoryDao: CategoryDao = FakeCategoryDaoImpl()
    private val categoryRepository: CategoryRepository = CategoryRepositoryImpl(
        categoryDao = fakeCategoryDao,
        dispatcherProvider = testDispatcherProvider,
    )
    private val getAllCategoriesUseCase = GetAllCategoriesUseCase(
        categoryRepository = categoryRepository,
    )
    private val editCategoryScreenDataValidationUseCase =
        EditCategoryScreenDataValidationUseCase(
            getAllCategoriesUseCase = getAllCategoriesUseCase,
        )
    private val getCategoryByIdUseCase = GetCategoryByIdUseCase(
        categoryRepository = categoryRepository,
    )
    private val financeManagerPreferencesDataSource: FinanceManagerPreferencesDataSource =
        FakeFinanceManagerPreferencesDataSource()
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository =
        FinanceManagerPreferencesRepositoryImpl(
            dispatcherProvider = testDispatcherProvider,
            financeManagerPreferencesDataSource = financeManagerPreferencesDataSource,
        )
    private val updateCategoriesUseCase = UpdateCategoriesUseCase(
        categoryRepository = categoryRepository,
        financeManagerPreferencesRepository = financeManagerPreferencesRepository,
    )
    private val updateCategoryUseCase = UpdateCategoryUseCase(
        updateCategoriesUseCase = updateCategoriesUseCase,
    )
    private val logKit: LogKit = FakeLogKitImpl()

    private lateinit var editCategoryScreenViewModel: EditCategoryScreenViewModel

    @Before
    fun setUp() {
        testScope.launch {
            fakeCategoryDao.insertCategories(
                testCategory1,
                testCategory2,
            )
        }
        editCategoryScreenViewModel = EditCategoryScreenViewModel(
            navigationKit = navigationKit,
            savedStateHandle = savedStateHandle,
            screenUIStateDelegate = screenUIStateDelegate,
            coroutineScope = testScope.backgroundScope,
            editCategoryScreenDataValidationUseCase = editCategoryScreenDataValidationUseCase,
            getCategoryByIdUseCase = getCategoryByIdUseCase,
            updateCategoryUseCase = updateCategoryUseCase,
            logKit = logKit,
        )
        editCategoryScreenViewModel.initViewModel()
    }

    @After
    fun tearDown() {
        testScope.cancel()
    }
    // endregion

    // region initial state
    @Test
    fun uiState_initialState() = runTestWithTimeout {
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
        runTestWithTimeout {
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.title.text).isEmpty()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()

                editCategoryScreenViewModel.uiStateEvents.updateTitle(
                    postDataFetchCompletion.title.copy(
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
        runTestWithTimeout {
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.title.text).isEmpty()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()

                editCategoryScreenViewModel.uiStateEvents.updateTitle(
                    postDataFetchCompletion.title.copy(
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
        runTestWithTimeout {
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.title.text).isEmpty()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()

                editCategoryScreenViewModel.uiStateEvents.updateTitle(
                    postDataFetchCompletion.title.copy(
                        text = testTitle2,
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
        runTestWithTimeout {
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.selectedTransactionTypeIndex).isNull()

                val result = awaitItem()
                assertThat(result.isLoading).isFalse()
                assertThat(result.selectedTransactionTypeIndex).isEqualTo(1)
                assertThat(result.emoji).isEqualTo(testEmoji1)
                assertThat(result.title.text).isEqualTo(testTitle1)
            }
        }
    // endregion

    // region state events
    @Test
    fun clearTitle_shouldClearTitle() = runTestWithTimeout {
        editCategoryScreenViewModel.uiState.test {
            val initialState = awaitItem()
            assertThat(initialState.isLoading).isTrue()
            assertThat(initialState.title.text).isEmpty()
            val postDataFetchCompletion = awaitItem()
            assertThat(postDataFetchCompletion.isLoading).isFalse()
            assertThat(postDataFetchCompletion.title.text).isEqualTo(testTitle1)

            editCategoryScreenViewModel.uiStateEvents.clearTitle()

            val result = awaitItem()
            assertThat(result.title.text).isEmpty()
        }
    }

    @Test
    fun resetScreenBottomSheetType_shouldSetBottomSheetTypeToNone() =
        runTestWithTimeout {
            val testScreenBottomSheetType =
                EditCategoryScreenBottomSheetType.SelectEmoji
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.screenBottomSheetType).isEqualTo(
                    EditCategoryScreenBottomSheetType.None
                )
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
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
    fun updateEmoji_shouldUpdateEmoji() = runTestWithTimeout {
        val testEmoji = "🤔"
        editCategoryScreenViewModel.uiState.test {
            val initialState = awaitItem()
            assertThat(initialState.isLoading).isTrue()
            assertThat(initialState.emoji).isEmpty()
            val postDataFetchCompletion = awaitItem()
            assertThat(postDataFetchCompletion.isLoading).isFalse()

            editCategoryScreenViewModel.uiStateEvents.updateEmoji(testEmoji)

            val result = awaitItem()
            assertThat(result.emoji).isEqualTo(testEmoji)
        }
    }

    @Test
    fun updateEmojiSearchText_shouldUpdateEmojiSearchText() =
        runTestWithTimeout {
            val testEmojiSearchText = "test-emoji-search-text"
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.emojiSearchText).isEmpty()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()

                editCategoryScreenViewModel.uiStateEvents.updateEmojiSearchText(
                    testEmojiSearchText
                )

                val result = awaitItem()
                assertThat(result.emojiSearchText).isEqualTo(testEmojiSearchText)
            }
        }

    @Test
    fun updateScreenBottomSheetType_shouldUpdateBottomSheetType() =
        runTestWithTimeout {
            val testScreenBottomSheetType =
                EditCategoryScreenBottomSheetType.SelectEmoji
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.screenBottomSheetType).isEqualTo(
                    EditCategoryScreenBottomSheetType.None
                )
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()

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
        runTestWithTimeout {
            val testSelectedTransactionTypeIndex = 2
            editCategoryScreenViewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.selectedTransactionTypeIndex).isNull()
                val postDataFetchCompletion = awaitItem()
                assertThat(postDataFetchCompletion.isLoading).isFalse()
                assertThat(postDataFetchCompletion.selectedTransactionTypeIndex).isEqualTo(
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
    fun updateTitle_shouldUpdateTitle() = runTestWithTimeout {
        val updatedTestTitle = "test-title"
        editCategoryScreenViewModel.uiState.test {
            val initialState = awaitItem()
            assertThat(initialState.isLoading).isTrue()
            assertThat(initialState.title.text).isEmpty()
            val postDataFetchCompletion = awaitItem()
            assertThat(postDataFetchCompletion.isLoading).isFalse()
            assertThat(postDataFetchCompletion.title.text).isEqualTo(testTitle1)

            editCategoryScreenViewModel.uiStateEvents.updateTitle(
                postDataFetchCompletion.title.copy(
                    text = updatedTestTitle,
                )
            )

            val result = awaitItem()
            assertThat(result.title.text).isEqualTo(updatedTestTitle)
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
