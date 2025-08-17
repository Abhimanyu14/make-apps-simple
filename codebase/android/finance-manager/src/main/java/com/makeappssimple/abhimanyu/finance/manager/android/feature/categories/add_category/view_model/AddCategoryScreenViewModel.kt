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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.view_model

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.uri_decoder.UriDecoder
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.InsertCategoryUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.bottom_sheet.AddCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.state.AddCategoryScreenTitleError
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.state.AddCategoryScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.state.AddCategoryScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.use_case.AddCategoryScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.navigation.AddCategoryScreenArgs
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
internal class AddCategoryScreenViewModel(
    navigationKit: NavigationKit,
    savedStateHandle: SavedStateHandle,
    screenUIStateDelegate: ScreenUIStateDelegate,
    uriDecoder: UriDecoder,
    private val addCategoryScreenDataValidationUseCase: AddCategoryScreenDataValidationUseCase,
    private val coroutineScope: CoroutineScope,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    internal val logKit: LogKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    logKit = logKit,
    navigationKit = navigationKit,
    screenUIStateDelegate = screenUIStateDelegate,
) {
    // region screen args
    private val screenArgs = AddCategoryScreenArgs(
        savedStateHandle = savedStateHandle,
        uriDecoder = uriDecoder,
    )
    // endregion

    // region initial data
    private val transactionType: String = screenArgs.transactionType
    private val validTransactionTypes: ImmutableList<TransactionType> =
        persistentListOf(
            TransactionType.INCOME,
            TransactionType.EXPENSE,
            TransactionType.INVESTMENT,
        )
    // endregion

    // region UI state
    private var title = TextFieldValue()
    private var emoji = EmojiConstants.GRINNING_FACE_WITH_BIG_EYES
    private var emojiSearchText = ""
    private var selectedTransactionTypeIndex = validTransactionTypes
        .indexOf(
            element = TransactionType.EXPENSE,
        )
    private var screenBottomSheetType: AddCategoryScreenBottomSheetType =
        AddCategoryScreenBottomSheetType.None
    // endregion

    // region uiStateAndStateEvents
    private val _uiState: MutableStateFlow<AddCategoryScreenUIState> =
        MutableStateFlow(
            value = AddCategoryScreenUIState(),
        )
    internal val uiState: StateFlow<AddCategoryScreenUIState> =
        _uiState.asStateFlow()
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

    // region updateUiStateAndStateEvents
    override fun updateUiStateAndStateEvents() {
        coroutineScope.launch {
            val validationState = addCategoryScreenDataValidationUseCase(
                enteredTitle = title.text,
            )
            _uiState.update {
                AddCategoryScreenUIState(
                    screenBottomSheetType = screenBottomSheetType,
                    isBottomSheetVisible = screenBottomSheetType != AddCategoryScreenBottomSheetType.None,
                    isCtaButtonEnabled = validationState.isCtaButtonEnabled,
                    isLoading = isLoading,
                    isSupportingTextVisible = validationState.titleError != AddCategoryScreenTitleError.None,
                    titleError = validationState.titleError,
                    selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                    transactionTypesChipUIData = validTransactionTypes.map { transactionType ->
                        ChipUIData(
                            text = transactionType.title,
                        )
                    },
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
            updateSelectedTransactionTypeIndex(
                updatedSelectedTransactionTypeIndex = validTransactionTypes.indexOf(
                    element = TransactionType.entries.find {
                        it.title == transactionType
                    },
                ),
            )
        }
    }
    // endregion

    // region state events
    private fun clearEmojiSearchText(
        shouldRefresh: Boolean = true,
    ): Job {
        updateEmojiSearchText(
            updatedEmojiSearchText = "",
        )
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun clearTitle(
        shouldRefresh: Boolean = true,
    ): Job {
        updateTitle(
            updatedTitle = title.copy(
                text = "",
            ),
        )
        return refreshIfRequired(
            shouldRefresh = shouldRefresh,
        )
    }

    private fun insertCategory(): Job {
        return coroutineScope.launch {
            val isCategoryInserted = insertCategoryUseCase(
                emoji = emoji,
                title = title.text,
                transactionType = validTransactionTypes[selectedTransactionTypeIndex],
            ) != -1L
            if (isCategoryInserted) {
                navigateUp()
            } else {
                completeLoading()
                // TODO(Abhi): Show error
            }
        }
    }

    private fun resetScreenBottomSheetType(): Job {
        return updateScreenBottomSheetType(
            updatedAddCategoryScreenBottomSheetType = AddCategoryScreenBottomSheetType.None,
        )
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
        updatedAddCategoryScreenBottomSheetType: AddCategoryScreenBottomSheetType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenBottomSheetType = updatedAddCategoryScreenBottomSheetType
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
}
