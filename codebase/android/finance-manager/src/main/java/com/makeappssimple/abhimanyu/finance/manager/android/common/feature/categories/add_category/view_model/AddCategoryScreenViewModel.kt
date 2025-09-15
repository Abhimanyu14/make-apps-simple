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

package com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.add_category.view_model

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.uri_decoder.UriDecoder
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.category.InsertCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.add_category.bottom_sheet.AddCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.add_category.state.AddCategoryScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.add_category.state.AddCategoryScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.add_category.state.AddCategoryScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.add_category.use_case.AddCategoryScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.feature.categories.navigation.AddCategoryScreenArgs
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
internal class AddCategoryScreenViewModel(
    navigationKit: NavigationKit,
    savedStateHandle: SavedStateHandle,
    uriDecoder: UriDecoder,
    private val addCategoryScreenDataValidationUseCase: AddCategoryScreenDataValidationUseCase,
    private val coroutineScope: CoroutineScope,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    internal val logKit: LogKit,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit,
    NavigationKit by navigationKit {
    // region screen args
    private val screenArgs = AddCategoryScreenArgs(
        savedStateHandle = savedStateHandle,
        uriDecoder = uriDecoder,
    )
    // endregion

    // region data
    private var screenBottomSheetType: AddCategoryScreenBottomSheetType =
        AddCategoryScreenBottomSheetType.None
    private var addCategoryScreenDataValidationState: AddCategoryScreenDataValidationState =
        AddCategoryScreenDataValidationState()
    private var isLoading: Boolean = true
    private val validTransactionTypes: ImmutableList<TransactionType> =
        persistentListOf(
            TransactionType.INCOME,
            TransactionType.EXPENSE,
            TransactionType.INVESTMENT,
        )
    private var selectedTransactionTypeIndex: Int = validTransactionTypes
        .indexOf(
            element = TransactionType.EXPENSE,
        )
    private var emoji: String = EmojiConstants.GRINNING_FACE_WITH_BIG_EYES
    private var emojiSearchText: String = ""
    private val transactionType: String = getTransactionType()
    private var titleTextFieldState: TextFieldState = TextFieldState()
    // endregion

    // region uiState
    private val _uiState: MutableStateFlow<AddCategoryScreenUIState> =
        MutableStateFlow(
            value = AddCategoryScreenUIState(),
        )
    internal val uiState: StateFlow<AddCategoryScreenUIState> =
        _uiState.asStateFlow()
    // endregion

    // region uiStateEvents
    internal val uiStateEvents: AddCategoryScreenUIStateEvents =
        AddCategoryScreenUIStateEvents(
            clearEmojiSearchText = ::clearEmojiSearchText,
            clearTitle = ::clearTitle,
            insertCategory = ::insertCategory,
            navigateUp = ::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            updateEmoji = ::updateEmoji,
            updateEmojiSearchText = ::updateEmojiSearchText,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
            updateSelectedTransactionTypeIndex = ::updateSelectedTransactionTypeIndex,
            updateTitle = ::updateTitle,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        coroutineScope.launch {
            observeData()
            fetchData()
            completeLoading()
        }
    }
    // endregion

    // region refreshUiState
    private fun refreshUiState(): Job {
        return coroutineScope.launch {
            addCategoryScreenDataValidationState =
                addCategoryScreenDataValidationUseCase(
                    enteredTitle = titleTextFieldState.text.toString(),
                )
            updateUiState()
        }
    }

    private fun updateUiState() {
        _uiState.update {
            AddCategoryScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != AddCategoryScreenBottomSheetType.None,
                isCtaButtonEnabled = addCategoryScreenDataValidationState.isCtaButtonEnabled,
                isLoading = isLoading,
                isSupportingTextVisible = addCategoryScreenDataValidationState.titleError != AddCategoryScreenTitleError.None,
                titleError = addCategoryScreenDataValidationState.titleError,
                selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                transactionTypesChipUIData = validTransactionTypes.map { transactionType ->
                    ChipUIData(
                        text = transactionType.title,
                    )
                },
                emoji = emoji,
                emojiSearchText = emojiSearchText,
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
    private fun fetchData() {
        updateSelectedTransactionTypeIndex(
            updatedSelectedTransactionTypeIndex = validTransactionTypes.indexOf(
                element = TransactionType.entries.find {
                    it.title == transactionType
                },
            ),
        )
    }
    // endregion

    // region state events
    private fun clearEmojiSearchText(): Job {
        return updateEmojiSearchText(
            updatedEmojiSearchText = "",
        )
    }

    private fun clearTitle() {
        updateTitle(
            updatedTitle = "",
        )
    }

    private fun insertCategory(): Job {
        return coroutineScope.launch {
            insertCategoryUseCase(
                emoji = emoji,
                title = titleTextFieldState.text.toString(),
                transactionType = validTransactionTypes[selectedTransactionTypeIndex],
            )
            navigateUp()
        }
    }

    private fun resetScreenBottomSheetType(): Job {
        return updateScreenBottomSheetType(
            updatedAddCategoryScreenBottomSheetType = AddCategoryScreenBottomSheetType.None,
        )
    }

    private fun updateEmoji(
        updatedEmoji: String,
    ): Job {
        emoji = updatedEmoji
        return refreshUiState()
    }

    private fun updateEmojiSearchText(
        updatedEmojiSearchText: String,
    ): Job {
        emojiSearchText = updatedEmojiSearchText
        return refreshUiState()
    }

    private fun updateScreenBottomSheetType(
        updatedAddCategoryScreenBottomSheetType: AddCategoryScreenBottomSheetType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenBottomSheetType = updatedAddCategoryScreenBottomSheetType
        return refreshUiState()
    }

    private fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
        shouldRefresh: Boolean = true,
    ): Job {
        selectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex
        return refreshUiState()
    }

    private fun updateTitle(
        updatedTitle: String,
        shouldRefresh: Boolean = true,
    ): Job {
        titleTextFieldState.setTextAndPlaceCursorAtEnd(
            text = updatedTitle,
        )
        return refreshUiState()
    }
    // endregion

    // region screen args
    private fun getTransactionType(): String {
        return screenArgs.transactionType
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
