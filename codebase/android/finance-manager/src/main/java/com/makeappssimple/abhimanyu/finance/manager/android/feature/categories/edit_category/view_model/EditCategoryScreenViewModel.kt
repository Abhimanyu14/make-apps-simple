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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.view_model

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.common.core.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.common.core.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.uri_decoder.UriDecoder
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.GetCategoryByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.UpdateCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.bottom_sheet.EditCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.state.EditCategoryScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.state.EditCategoryScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.state.EditCategoryScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.use_case.EditCategoryScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.navigation.EditCategoryScreenArgs
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class EditCategoryScreenViewModel(
    navigationKit: NavigationKit,
    savedStateHandle: SavedStateHandle,
    uriDecoder: UriDecoder,
    private val coroutineScope: CoroutineScope,
    private val editCategoryScreenDataValidationUseCase: EditCategoryScreenDataValidationUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val updateCategoriesUseCase: UpdateCategoriesUseCase,
    internal val logKit: LogKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    logKit = logKit,
    navigationKit = navigationKit,
) {
    // region screen args
    private val screenArgs = EditCategoryScreenArgs(
        savedStateHandle = savedStateHandle,
        uriDecoder = uriDecoder,
    )
    // endregion

    // region initial data
    private val categories: MutableStateFlow<ImmutableList<Category>> =
        MutableStateFlow(
            value = persistentListOf(),
        )
    private val transactionType: String? = screenArgs.transactionType
    // endregion

    // region initial data
    private val category: MutableStateFlow<Category?> = MutableStateFlow(
        value = null,
    )
    private val validTransactionTypes: ImmutableList<TransactionType> =
        persistentListOf(
            TransactionType.INCOME,
            TransactionType.EXPENSE,
            TransactionType.INVESTMENT,
        )
    // endregion

    // region UI state
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val title: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(),
    )
    private val searchText: MutableStateFlow<String> = MutableStateFlow(
        value = "",
    )
    private val emoji: MutableStateFlow<String> = MutableStateFlow(
        value = EmojiConstants.GRINNING_FACE_WITH_BIG_EYES,
    )
    private val selectedTransactionTypeIndex: MutableStateFlow<Int> =
        MutableStateFlow(
            value = validTransactionTypes.indexOf(
                element = TransactionType.EXPENSE,
            ),
        )
    private val screenBottomSheetType: MutableStateFlow<EditCategoryScreenBottomSheetType> =
        MutableStateFlow(
            value = EditCategoryScreenBottomSheetType.None,
        )
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<EditCategoryScreenUIState> =
        MutableStateFlow(
            value = EditCategoryScreenUIState(),
        )
    internal val uiStateEvents: EditCategoryScreenUIStateEvents =
        EditCategoryScreenUIStateEvents(
            clearTitle = ::clearTitle,
            navigateUp = ::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            updateCategory = ::updateCategory,
            updateEmoji = ::updateEmoji,
            updateTitle = ::updateTitle,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
            updateSearchText = ::updateSearchText,
            updateSelectedTransactionTypeIndex = ::updateSelectedTransactionTypeIndex,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        observeData()
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            withLoadingSuspend {
                getAllCategories()
                getOriginalCategory()
                transactionType?.let { originalTransactionType ->
                    updateSelectedTransactionTypeIndex(
                        validTransactionTypes.indexOf(
                            element = TransactionType.entries.find { transactionType ->
                                transactionType.title == originalTransactionType
                            },
                        )
                    )
                }
            }
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEvents()
    }
    // endregion

    // region loading
    fun startLoading() {
        isLoading.update {
            true
        }
    }

    fun completeLoading() {
        isLoading.update {
            false
        }
    }

    fun <T> withLoading(
        block: () -> T,
    ): T {
        startLoading()
        val result = block()
        completeLoading()
        return result
    }

    suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T {
        startLoading()
        try {
            return block()
        } finally {
            completeLoading()
        }
    }
    // endregion

    // region state events
    private fun clearTitle() {
        title.update {
            title.value.copy(
                text = "",
            )
        }
    }

    private fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedEditCategoryScreenBottomSheetType = EditCategoryScreenBottomSheetType.None,
        )
    }

    private fun updateEmoji(
        updatedEmoji: String,
    ) {
        emoji.update {
            updatedEmoji
        }
    }

    private fun updateTitle(
        updatedTitle: TextFieldValue,
    ) {
        title.update {
            updatedTitle
        }
    }

    private fun updateScreenBottomSheetType(
        updatedEditCategoryScreenBottomSheetType: EditCategoryScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedEditCategoryScreenBottomSheetType
        }
    }

    private fun updateSearchText(
        updatedSearchText: String,
    ) {
        searchText.update {
            updatedSearchText
        }
    }

    private fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        selectedTransactionTypeIndex.update {
            updatedSelectedTransactionTypeIndex
        }
    }

    private fun updateCategory() {
        category.value?.copy(
            emoji = emoji.value,
            title = title.value.text,
            transactionType = validTransactionTypes[selectedTransactionTypeIndex.value],
        )?.let { category ->
            coroutineScope.launch {
                updateCategoriesUseCase(category)
                navigateUp()
            }
        }
    }
    // endregion

    // region getAllCategories
    private fun getAllCategories() {
        viewModelScope.launch {
            categories.update {
                getAllCategoriesUseCase()
            }
        }
    }
    // endregion

    // region getOriginalCategory
    private fun getOriginalCategory() {
        screenArgs.categoryId?.let { id ->
            viewModelScope.launch {
                category.update {
                    getCategoryByIdUseCase(
                        id = id,
                    )
                }

                category.value.let { category ->
                    updateSelectedTransactionTypeIndex(
                        validTransactionTypes.indexOf(
                            element = category?.transactionType,
                        )
                    )
                    updateTitle(
                        title.value.copy(
                            text = category?.title.orEmpty(),
                            selection = TextRange(category?.title.orEmpty().length),
                        )
                    )
                    updateEmoji(category?.emoji.orEmpty())
                }
            }
        }
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                title,
                categories,
                selectedTransactionTypeIndex,
                searchText,
                emoji,
                category,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        title,
                        categories,
                        selectedTransactionTypeIndex,
                        searchText,
                        emoji,
                        category,
                    ),
                ->
                val validationState = editCategoryScreenDataValidationUseCase(
                    categories = categories,
                    enteredTitle = title.text.trim(),
                    currentCategory = category,
                )
                var titleError: EditCategoryScreenTitleError =
                    EditCategoryScreenTitleError.None
                val isCtaButtonEnabled = if (title.text.isBlank()) {
                    false
                } else if (isDefaultIncomeCategory(
                        category = title.text.trim(),
                    ) || isDefaultExpenseCategory(
                        category = title.text.trim(),
                    ) || isDefaultInvestmentCategory(
                        category = title.text.trim(),
                    ) || (title.text.trim() != category?.title?.trim() && categories.find {
                        it.title.equalsIgnoringCase(
                            other = title.text.trim(),
                        )
                    }.isNotNull())
                ) {
                    titleError = EditCategoryScreenTitleError.CategoryExists
                    false
                } else {
                    true
                }

                uiState.update {
                    EditCategoryScreenUIState(
                        screenBottomSheetType = screenBottomSheetType,
                        isBottomSheetVisible = screenBottomSheetType != EditCategoryScreenBottomSheetType.None,
                        isCtaButtonEnabled = isCtaButtonEnabled,
                        isLoading = isLoading,
                        isSupportingTextVisible = titleError != EditCategoryScreenTitleError.None,
                        titleError = titleError,
                        selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                        transactionTypesChipUIData = validTransactionTypes.map { transactionType ->
                            ChipUIData(
                                text = transactionType.title,
                            )
                        },
                        emoji = emoji,
                        emojiSearchText = searchText,
                        title = title,
                    )
                }
            }
        }
    }
    // endregion
}
