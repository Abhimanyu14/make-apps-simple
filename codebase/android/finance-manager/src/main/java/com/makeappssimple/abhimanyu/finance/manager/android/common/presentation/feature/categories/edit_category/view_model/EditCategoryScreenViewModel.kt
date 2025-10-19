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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.edit_category.view_model

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.common.core.coroutines.getCompletedJob
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.category.GetCategoryByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.category.UpdateCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.edit_category.bottom_sheet.EditCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.edit_category.state.EditCategoryScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.edit_category.state.EditCategoryScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.edit_category.state.EditCategoryScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.edit_category.use_case.EditCategoryScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.navigation.EditCategoryScreenArgs
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class EditCategoryScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val coroutineScope: CoroutineScope,
    private val editCategoryScreenDataValidationUseCase: EditCategoryScreenDataValidationUseCase,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val navigationKit: NavigationKit,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    internal val logKit: LogKit,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit {
    // region screen args
    private val screenArgs = EditCategoryScreenArgs(
        savedStateHandle = savedStateHandle,
    )
    // endregion

    // region data
    private var isLoading: Boolean = true
    private var currentCategory: Category? = null
    private var screenBottomSheetType: EditCategoryScreenBottomSheetType =
        EditCategoryScreenBottomSheetType.None
    private var editCategoryScreenDataValidationState: EditCategoryScreenDataValidationState =
        EditCategoryScreenDataValidationState()
    private val validTransactionTypes: ImmutableList<TransactionType> =
        persistentListOf(
            TransactionType.INCOME,
            TransactionType.EXPENSE,
            TransactionType.INVESTMENT,
        )
    private val transactionTypesChipUIData: ImmutableList<ChipUIData> =
        validTransactionTypes.map { transactionType ->
            ChipUIData(
                text = transactionType.title,
            )
        }
    private var selectedTransactionTypeIndex: Int =
        validTransactionTypes.indexOf(
            element = TransactionType.EXPENSE,
        )
    private var emoji: String = EmojiConstants.GRINNING_FACE_WITH_BIG_EYES
    private var titleTextFieldState: TextFieldState = TextFieldState()
    // endregion

    // region uiState
    private val _uiState: MutableStateFlow<EditCategoryScreenUIState> =
        MutableStateFlow(
            value = EditCategoryScreenUIState(),
        )
    internal val uiState: StateFlow<EditCategoryScreenUIState> =
        _uiState.asStateFlow()
    // endregion

    // region uiStateEvents
    internal val uiStateEvents: EditCategoryScreenUIStateEvents =
        EditCategoryScreenUIStateEvents(
            clearTitle = ::clearTitle,
            navigateUp = navigationKit::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            updateCategory = ::updateCategory,
            updateEmoji = ::updateEmoji,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
            updateSelectedTransactionTypeIndex = ::updateSelectedTransactionTypeIndex,
            updateTitle = ::updateTitle,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        coroutineScope.launch {
            fetchData()
            completeLoading()
        }
        observeData()
    }
    // endregion

    // region refreshUiState
    private fun refreshUiState(): Job {
        return coroutineScope.launch {
            editCategoryScreenDataValidationState =
                editCategoryScreenDataValidationUseCase(
                    enteredTitle = titleTextFieldState.text.toString().trim(),
                    currentCategory = currentCategory,
                )
            updateUiState()
        }
    }

    private fun updateUiState() {
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
                titleTextFieldState = titleTextFieldState,
            )
        }
    }
    // endregion

    // region observeData
    private fun observeData() {
        observeTextFieldState(
            textFieldState = titleTextFieldState,
        )
    }

    private fun observeTextFieldState(
        textFieldState: TextFieldState,
    ) {
        coroutineScope.launch {
            snapshotFlow {
                textFieldState.text.toString()
            }.collectLatest {
                refreshUiState()
            }
        }
    }
    // endregion

    // region fetchData
    private suspend fun fetchData() {
        getCurrentCategory()
    }

    private suspend fun getCurrentCategory(
        shouldRefresh: Boolean = false,
    ) {
        val currentCategoryId = getCurrentCategoryId()
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
            updatedTitle = currentCategory.title,
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
            updatedTitle = "",
        )
    }

    private fun resetScreenBottomSheetType(): Job {
        return updateScreenBottomSheetType(
            updatedEditCategoryScreenBottomSheetType = EditCategoryScreenBottomSheetType.None,
        )
    }

    private fun updateCategory(): Job {
        return coroutineScope.launch {
            updateCategoryUseCase(
                currentCategory = requireNotNull(
                    value = currentCategory,
                    lazyMessage = {
                        "Current category is null. Cannot update category."
                    },
                ),
                emoji = emoji,
                title = titleTextFieldState.text.toString(),
                transactionType = validTransactionTypes[selectedTransactionTypeIndex],
            )
            navigationKit.navigateUp()
        }
    }

    private fun updateEmoji(
        updatedEmoji: String,
        shouldRefresh: Boolean = true,
    ): Job {
        emoji = updatedEmoji
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateScreenBottomSheetType(
        updatedEditCategoryScreenBottomSheetType: EditCategoryScreenBottomSheetType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenBottomSheetType = updatedEditCategoryScreenBottomSheetType
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
        shouldRefresh: Boolean = true,
    ): Job {
        selectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateTitle(
        updatedTitle: String,
        shouldRefresh: Boolean = true,
    ): Job {
        titleTextFieldState.setTextAndPlaceCursorAtEnd(
            text = updatedTitle,
        )
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }
    // endregion

    // region screen args
    private fun getCurrentCategoryId(): Int {
        return screenArgs.currentCategoryId
    }
    // endregion

    // region loading
    private fun completeLoading() {
        isLoading = false
        refreshUiState()
    }

    private fun startLoading() {
        isLoading = true
        refreshUiState()
    }
    // endregion
}
