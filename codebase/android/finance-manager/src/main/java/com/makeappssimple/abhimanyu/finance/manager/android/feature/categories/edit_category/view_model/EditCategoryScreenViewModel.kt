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
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.GetCategoryByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.UpdateCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.bottom_sheet.EditCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.state.EditCategoryScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.state.EditCategoryScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.state.EditCategoryScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.use_case.EditCategoryScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.navigation.EditCategoryScreenArgs
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class EditCategoryScreenViewModel(
    navigationKit: NavigationKit,
    savedStateHandle: SavedStateHandle,
    screenUIStateDelegate: ScreenUIStateDelegate,
    private val coroutineScope: CoroutineScope,
    private val editCategoryScreenDataValidationUseCase: EditCategoryScreenDataValidationUseCase,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    internal val logKit: LogKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    logKit = logKit,
    navigationKit = navigationKit,
    screenUIStateDelegate = screenUIStateDelegate,
) {
    // region screen args
    private val screenArgs = EditCategoryScreenArgs(
        savedStateHandle = savedStateHandle,
    )
    // endregion

    // region initial data
    private val validTransactionTypes: ImmutableList<TransactionType> =
        persistentListOf(
            TransactionType.INCOME,
            TransactionType.EXPENSE,
            TransactionType.INVESTMENT,
        )
    private val transactionTypesChipUIData =
        validTransactionTypes.map { transactionType ->
            ChipUIData(
                text = transactionType.title,
            )
        }
    private var currentCategory: Category? = null
    // endregion

    // region UI state
    private var title = TextFieldValue()
    private var emojiSearchText = ""
    private var emoji = EmojiConstants.GRINNING_FACE_WITH_BIG_EYES
    private var selectedTransactionTypeIndex = validTransactionTypes.indexOf(
        element = TransactionType.EXPENSE,
    )
    private var screenBottomSheetType: EditCategoryScreenBottomSheetType =
        EditCategoryScreenBottomSheetType.None
    // endregion

    // region uiStateAndStateEvents
    private val _uiState: MutableStateFlow<EditCategoryScreenUIState> =
        MutableStateFlow(
            value = EditCategoryScreenUIState(),
        )
    internal val uiState: StateFlow<EditCategoryScreenUIState> =
        _uiState.asStateFlow()
    internal val uiStateEvents: EditCategoryScreenUIStateEvents =
        EditCategoryScreenUIStateEvents(
            clearTitle = ::clearTitle,
            navigateUp = ::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            updateCategory = ::updateCategory,
            updateEmoji = ::updateEmoji,
            updateEmojiSearchText = ::updateEmojiSearchText,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
            updateSelectedTransactionTypeIndex = ::updateSelectedTransactionTypeIndex,
            updateTitle = ::updateTitle,
        )
    // endregion

    // region updateUiStateAndStateEvents
    override fun updateUiStateAndStateEvents() {
        coroutineScope.launch {
            val editCategoryScreenDataValidationState =
                editCategoryScreenDataValidationUseCase(
                    enteredTitle = title.text.trim(),
                    currentCategory = currentCategory,
                )
            _uiState.update {
                EditCategoryScreenUIState(
                    screenBottomSheetType = screenBottomSheetType,
                    isBottomSheetVisible = screenBottomSheetType != EditCategoryScreenBottomSheetType.None,
                    isCtaButtonEnabled = editCategoryScreenDataValidationState.isCtaButtonEnabled,
                    isLoading = isLoading,
                    isSupportingTextVisible = editCategoryScreenDataValidationState.titleError != EditCategoryScreenTitleError.None,
                    titleError = editCategoryScreenDataValidationState.titleError,
                    selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                    transactionTypesChipUIData = transactionTypesChipUIData,
                    emoji = emoji,
                    emojiSearchText = emojiSearchText,
                    title = title,
                )
            }
        }
    }
    // endregion

    // region fetchData
    override fun fetchData(): Job {
        return coroutineScope.launch {
            getCurrentCategory()
        }
    }

    private fun getCurrentCategory(
        shouldRefresh: Boolean = false,
    ) {
        val currentCategoryId = getCurrentCategoryId()
        coroutineScope.launch {
            currentCategory = getCategoryByIdUseCase(
                id = currentCategoryId,
            )
            processCurrentCategory(
                currentCategory = requireNotNull(
                    value = currentCategory,
                    lazyMessage = {
                        "Category with ID $currentCategoryId not found."
                    },
                ),
                shouldRefresh = shouldRefresh,
            )
        }
    }

    private fun processCurrentCategory(
        currentCategory: Category,
        shouldRefresh: Boolean = false,
    ) {
        updateSelectedTransactionTypeIndex(
            updatedSelectedTransactionTypeIndex = validTransactionTypes.indexOf(
                element = currentCategory.transactionType,
            ),
            shouldRefresh = shouldRefresh,
        )
        updateTitle(
            updatedTitle = title.copy(
                text = currentCategory.title,
                selection = TextRange(
                    index = currentCategory.title.length,
                ),
            ),
            shouldRefresh = shouldRefresh,
        )
        updateEmoji(
            updatedEmoji = currentCategory.emoji,
            shouldRefresh = shouldRefresh,
        )
    }
    // endregion

    // region state events
    private fun clearTitle(): Job {
        return updateTitle(
            updatedTitle = title.copy(
                text = "",
            ),
        )
    }

    private fun resetScreenBottomSheetType(): Job {
        return updateScreenBottomSheetType(
            updatedEditCategoryScreenBottomSheetType = EditCategoryScreenBottomSheetType.None,
        )
    }

    private fun updateCategory(): Job {
        return coroutineScope.launch {
            val isCategoryUpdated = updateCategoryUseCase(
                currentCategory = requireNotNull(
                    value = currentCategory,
                    lazyMessage = {
                        "Current category is null. Cannot update category."
                    },
                ),
                emoji = emoji,
                title = title.text,
                transactionType = validTransactionTypes[selectedTransactionTypeIndex],
            ) == 1
            if (isCategoryUpdated) {
                navigateUp()
            } else {
                completeLoading()
                // TODO(Abhi): Show error
            }
        }
    }

    private fun updateEmoji(
        updatedEmoji: String,
        shouldRefresh: Boolean = true,
    ): Job {
        emoji = updatedEmoji
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateEmojiSearchText(
        updatedEmojiSearchText: String,
        shouldRefresh: Boolean = true,
    ): Job {
        emojiSearchText = updatedEmojiSearchText
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateScreenBottomSheetType(
        updatedEditCategoryScreenBottomSheetType: EditCategoryScreenBottomSheetType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenBottomSheetType = updatedEditCategoryScreenBottomSheetType
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
        shouldRefresh: Boolean = true,
    ): Job {
        selectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun updateTitle(
        updatedTitle: TextFieldValue,
        shouldRefresh: Boolean = true,
    ): Job {
        title = updatedTitle
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }
    // endregion

    // region screen args
    private fun getCurrentCategoryId(): Int {
        return screenArgs.currentCategoryId
    }
    // endregion
}
