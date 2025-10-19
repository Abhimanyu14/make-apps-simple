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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.edit_transaction_for.view_model

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.transaction_for.GetTransactionForByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.use_case.transaction_for.UpdateTransactionForUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.edit_transaction_for.state.EditTransactionForScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.edit_transaction_for.state.EditTransactionForScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.edit_transaction_for.use_case.EditTransactionForScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.navigation.EditTransactionForScreenArgs
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.navigation.NavigationKit
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
internal class EditTransactionForScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val coroutineScope: CoroutineScope,
    private val editTransactionForScreenDataValidationUseCase: EditTransactionForScreenDataValidationUseCase,
    private val getTransactionForByIdUseCase: GetTransactionForByIdUseCase,
    private val navigationKit: NavigationKit,
    private val updateTransactionForUseCase: UpdateTransactionForUseCase,
    internal val logKit: LogKit,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit {
    // region screen args
    private val screenArgs = EditTransactionForScreenArgs(
        savedStateHandle = savedStateHandle,
    )
    // endregion

    // region data
    private var isLoading: Boolean = true
    private var editTransactionForScreenDataValidationState: EditTransactionForScreenDataValidationState =
        EditTransactionForScreenDataValidationState()
    private val titleTextFieldState: TextFieldState = TextFieldState()
    private var currentTransactionFor: TransactionFor? = null
    // endregion

    // region uiState
    private val _uiState: MutableStateFlow<EditTransactionForScreenUIState> =
        MutableStateFlow(
            value = EditTransactionForScreenUIState(),
        )
    internal val uiState: StateFlow<EditTransactionForScreenUIState> =
        _uiState.asStateFlow()
    // endregion

    // region uiStateEvents
    internal val uiStateEvents: EditTransactionForScreenUIStateEvents =
        EditTransactionForScreenUIStateEvents(
            clearTitle = ::clearTitle,
            navigateUp = navigationKit::navigateUp,
            updateTitle = ::updateTitle,
            updateTransactionFor = ::updateTransactionFor,
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
    private fun refreshUiState() {
        coroutineScope.launch {
            editTransactionForScreenDataValidationState =
                editTransactionForScreenDataValidationUseCase(
                    currentTransactionFor = currentTransactionFor,
                    enteredTitle = titleTextFieldState.text.toString(),
                )
            updateUiState()
        }
    }

    private fun updateUiState() {
        _uiState.update {
            EditTransactionForScreenUIState(
                isCtaButtonEnabled = editTransactionForScreenDataValidationState.isCtaButtonEnabled,
                isLoading = isLoading,
                titleError = editTransactionForScreenDataValidationState.titleError,
                titleTextFieldState = titleTextFieldState,
            )
        }
    }
    // endregion

    // region fetchData
    private suspend fun fetchData() {
        getCurrentTransactionFor()
    }

    private suspend fun getCurrentTransactionFor() {
        val currentTransactionForId = getCurrentTransactionForId()
        currentTransactionFor = getTransactionForByIdUseCase(
            id = currentTransactionForId,
        )
        processCurrentTransactionFor(
            currentTransactionFor = requireNotNull(
                value = currentTransactionFor,
                lazyMessage = {
                    "Transaction for with id $currentTransactionForId not found"
                },
            ),
        )
    }

    private fun processCurrentTransactionFor(
        currentTransactionFor: TransactionFor,
    ) {
        updateTitle(
            updatedTitle = currentTransactionFor.title,
        )
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

    // region state events
    private fun clearTitle() {
        updateTitle(
            updatedTitle = "",
        )
    }

    private fun updateTitle(
        updatedTitle: String,
    ) {
        titleTextFieldState.setTextAndPlaceCursorAtEnd(
            text = updatedTitle,
        )
    }

    private fun updateTransactionFor(): Job {
        val currentTransactionForValue = currentTransactionFor
            ?: throw IllegalStateException("Current transaction for is null")
        return coroutineScope.launch {
            startLoading()
            updateTransactionForUseCase(
                currentTransactionFor = currentTransactionForValue,
                title = titleTextFieldState.text.toString(),
            )
            navigationKit.navigateUp()
        }
    }
    // endregion

    // region screen args
    private fun getCurrentTransactionForId(): Int {
        return screenArgs.currentTransactionForId
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
