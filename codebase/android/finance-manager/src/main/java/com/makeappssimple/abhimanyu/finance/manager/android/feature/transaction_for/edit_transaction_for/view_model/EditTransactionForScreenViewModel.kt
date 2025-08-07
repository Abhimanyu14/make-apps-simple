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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.edit_transaction_for.view_model

import androidx.compose.ui.text.TextRange
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.common.core.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.common.core.uri_decoder.UriDecoder
import com.makeappssimple.abhimanyu.common.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.GetTransactionForUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.UpdateTransactionForUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.edit_transaction_for.bottom_sheet.EditTransactionForScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.edit_transaction_for.state.EditTransactionForScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.edit_transaction_for.state.EditTransactionForScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.edit_transaction_for.use_case.EditTransactionForScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.navigation.EditTransactionForScreenArgs
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class EditTransactionForScreenViewModel(
    coroutineScope: CoroutineScope,
    savedStateHandle: SavedStateHandle,
    uriDecoder: UriDecoder,
    private val editTransactionForScreenDataValidationUseCase: EditTransactionForScreenDataValidationUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getTransactionForUseCase: GetTransactionForUseCase,
    private val navigationKit: NavigationKit,
    private val updateTransactionForUseCase: UpdateTransactionForUseCase,
    internal val logKit: LogKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
),
    EditTransactionForScreenUIStateDelegate by EditTransactionForScreenUIStateDelegateImpl(
        coroutineScope = coroutineScope,
        navigationKit = navigationKit,
        updateTransactionForUseCase = updateTransactionForUseCase,
    ) {
    // region screen args
    private val screenArgs = EditTransactionForScreenArgs(
        savedStateHandle = savedStateHandle,
        uriDecoder = uriDecoder,
    )
    // endregion

    // region initial data
    private var allTransactionForValues: ImmutableList<TransactionFor> =
        persistentListOf()
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<EditTransactionForScreenUIState> =
        MutableStateFlow(
            value = EditTransactionForScreenUIState(),
        )
    internal val uiStateEvents: EditTransactionForScreenUIStateEvents =
        EditTransactionForScreenUIStateEvents(
            clearTitle = ::clearTitle,
            navigateUp = ::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
            updateTitle = ::updateTitle,
            updateTransactionFor = {
                updateTransactionFor(
                    uiState = uiState.value
                )
            },
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
                getAllTransactionForValues()
                getCurrentTransactionFor()
                processCurrentTransactionFor()
            }
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEvents()
    }
    // endregion

    // region getAllTransactionForValues
    private suspend fun getAllTransactionForValues() {
        allTransactionForValues = getAllTransactionForValuesUseCase()
    }
    // endregion

    // region getOriginalTransactionFor
    private suspend fun getCurrentTransactionFor() {
        val transactionForId = screenArgs.transactionForId ?: return
        currentTransactionFor = getTransactionForUseCase(
            id = transactionForId,
        )
    }
    // endregion

    // region processCurrentTransactionFor
    private fun processCurrentTransactionFor() {
        val currentTransactionForValue = currentTransactionFor ?: return
        updateTitle(
            updatedTitle = title.value
                .copy(
                    text = currentTransactionForValue.title,
                    selection = TextRange(
                        index = currentTransactionForValue.title.length,
                    ),
                ),
        )
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                title,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        title,
                    ),
                ->
                val validationState =
                    editTransactionForScreenDataValidationUseCase(
                        allTransactionForValues = allTransactionForValues,
                        currentTransactionFor = currentTransactionFor,
                        enteredTitle = title.text,
                    )
                uiState.update {
                    EditTransactionForScreenUIState(
                        isBottomSheetVisible = screenBottomSheetType != EditTransactionForScreenBottomSheetType.None,
                        isCtaButtonEnabled = validationState.isCtaButtonEnabled,
                        isLoading = isLoading,
                        screenBottomSheetType = screenBottomSheetType,
                        titleError = validationState.titleError,
                        title = title,
                    )
                }
            }
        }
    }
    // endregion
}
