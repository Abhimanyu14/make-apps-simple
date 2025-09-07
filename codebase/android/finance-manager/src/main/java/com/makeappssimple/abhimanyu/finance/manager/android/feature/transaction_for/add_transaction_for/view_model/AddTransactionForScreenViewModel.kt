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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.add_transaction_for.view_model

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.InsertTransactionForUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.add_transaction_for.state.AddTransactionForScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.add_transaction_for.state.AddTransactionForScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.add_transaction_for.use_case.AddTransactionForScreenDataValidationUseCase
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
internal class AddTransactionForScreenViewModel(
    navigationKit: NavigationKit,
    private val addTransactionForScreenDataValidationUseCase: AddTransactionForScreenDataValidationUseCase,
    private val coroutineScope: CoroutineScope,
    private val insertTransactionForUseCase: InsertTransactionForUseCase,
    internal val logKit: LogKit,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit,
    NavigationKit by navigationKit {
    // region data
    private var addTransactionForScreenDataValidationState: AddTransactionForScreenDataValidationState =
        AddTransactionForScreenDataValidationState()
    private var isLoading: Boolean = false
    private var titleTextFieldState: TextFieldState = TextFieldState()
    // endregion

    // region uiState
    private val _uiState: MutableStateFlow<AddTransactionForScreenUIState> =
        MutableStateFlow(
            value = AddTransactionForScreenUIState(
                isLoading = false,
            ),
        )
    internal val uiState: StateFlow<AddTransactionForScreenUIState> =
        _uiState.asStateFlow()
    // endregion

    // region uiStateEvents
    internal val uiStateEvents: AddTransactionForScreenUIStateEvents =
        AddTransactionForScreenUIStateEvents(
            clearTitle = ::clearTitle,
            insertTransactionFor = ::insertTransactionFor,
            navigateUp = ::navigateUp,
            updateTitle = ::updateTitle,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        coroutineScope.launch {
            observeData()
        }
    }
    // endregion

    // region refreshUiState
    private fun refreshUiState() {
        coroutineScope.launch {
            addTransactionForScreenDataValidationState =
                addTransactionForScreenDataValidationUseCase(
                    enteredTitle = titleTextFieldState.text.toString(),
                )
            updateUiState()
        }
    }

    private fun updateUiState() {
        _uiState.update {
            AddTransactionForScreenUIState(
                titleError = addTransactionForScreenDataValidationState.titleError,
                isCtaButtonEnabled = addTransactionForScreenDataValidationState.isCtaButtonEnabled,
                isLoading = isLoading,
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

    // region state events
    private fun clearTitle() {
        updateTitle(
            updatedTitle = "",
        )
    }

    private fun insertTransactionFor(): Job {
        return coroutineScope.launch {
            startLoading()
            val isTransactionForInserted = insertTransactionForUseCase(
                title = titleTextFieldState.text.toString(),
            ) != -1L
            if (isTransactionForInserted) {
                navigateUp()
            } else {
                completeLoading()
                // TODO(Abhi): Show error
            }
        }
    }

    private fun updateTitle(
        updatedTitle: String,
    ) {
        titleTextFieldState.setTextAndPlaceCursorAtEnd(
            text = updatedTitle,
        )
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
