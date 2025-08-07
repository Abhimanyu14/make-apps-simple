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

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.common.core.uri_decoder.UriDecoder
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.InsertCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class AddCategoryScreenViewModel(
    coroutineScope: CoroutineScope,
    savedStateHandle: SavedStateHandle,
    uriDecoder: UriDecoder,
    private val addCategoryScreenDataValidationUseCase: AddCategoryScreenDataValidationUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val insertCategoriesUseCase: InsertCategoriesUseCase,
    private val navigationKit: NavigationKit,
    internal val logKit: LogKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), AddCategoryScreenUIStateDelegate by AddCategoryScreenUIStateDelegateImpl(
    coroutineScope = coroutineScope,
    insertCategoriesUseCase = insertCategoriesUseCase,
    navigationKit = navigationKit,
) {
    // region screen args
    private val screenArgs = AddCategoryScreenArgs(
        savedStateHandle = savedStateHandle,
        uriDecoder = uriDecoder,
    )
    // endregion

    // region initial data
    private val transactionType: String? = screenArgs.transactionType
    private var categories: ImmutableList<Category> = persistentListOf()
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<AddCategoryScreenUIState> =
        MutableStateFlow(
            value = AddCategoryScreenUIState(),
        )
    internal val uiStateEvents: AddCategoryScreenUIStateEvents =
        AddCategoryScreenUIStateEvents(
            clearSearchText = ::clearSearchText,
            clearTitle = ::clearTitle,
            insertCategory = ::insertCategory,
            navigateUp = ::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            updateEmoji = ::updateEmoji,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
            updateSearchText = ::updateSearchText,
            updateSelectedTransactionTypeIndex = ::updateSelectedTransactionTypeIndex,
            updateTitle = ::updateTitle,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        observeData()
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            fetchCategories()
            transactionType?.let { originalTransactionType ->
                updateSelectedTransactionTypeIndex(
                    updatedSelectedTransactionTypeIndex = validTransactionTypes.indexOf(
                        element = TransactionType.entries.find { transactionType ->
                            transactionType.title == originalTransactionType
                        },
                    ),
                )
            }
            completeLoading()
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEvents()
    }
    // endregion

    // region fetchCategories
    private suspend fun fetchCategories() {
        categories = getAllCategoriesUseCase()
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        observeForRefreshSignal()
    }

    private fun observeForRefreshSignal() {
        viewModelScope.launch {
            refreshSignal.collectLatest {
                updateUiStateAndStateEvents()
            }
        }
    }

    private fun updateUiStateAndStateEvents(
        isLoading: Boolean = false,
    ) {
        val validationState = addCategoryScreenDataValidationUseCase(
            categories = categories,
            enteredTitle = title.text.trim(),
        )
        uiState.update {
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
                emojiSearchText = searchText,
                title = title,
            )
        }
    }
    // endregion
}
