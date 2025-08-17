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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.GetTransactionForByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.UpdateTransactionForUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.edit_transaction_for.state.EditTransactionForScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.edit_transaction_for.state.EditTransactionForScreenUIStateEvents
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.edit_transaction_for.use_case.EditTransactionForScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.navigation.EditTransactionForScreenArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class EditTransactionForScreenViewModel(
    navigationKit: NavigationKit,
    screenUIStateDelegate: ScreenUIStateDelegate,
    savedStateHandle: SavedStateHandle,
    private val coroutineScope: CoroutineScope,
    private val editTransactionForScreenDataValidationUseCase: EditTransactionForScreenDataValidationUseCase,
    private val getTransactionForByIdUseCase: GetTransactionForByIdUseCase,
    private val updateTransactionForUseCase: UpdateTransactionForUseCase,
    internal val logKit: LogKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    logKit = logKit,
    navigationKit = navigationKit,
    screenUIStateDelegate = screenUIStateDelegate,
) {
    // region screen args
    private val screenArgs = EditTransactionForScreenArgs(
        savedStateHandle = savedStateHandle,
    )
    // endregion

    // region initial data
    private var currentTransactionFor: TransactionFor? = null
    // endregion

    // region UI state
    private var title = TextFieldValue()
    // endregion

    // region uiStateAndStateEvents
    private val _uiState: MutableStateFlow<EditTransactionForScreenUIState> =
        MutableStateFlow(
            value = EditTransactionForScreenUIState(),
        )
    internal val uiState: StateFlow<EditTransactionForScreenUIState> =
        _uiState.asStateFlow()
    internal val uiStateEvents: EditTransactionForScreenUIStateEvents =
        EditTransactionForScreenUIStateEvents(
            clearTitle = ::clearTitle,
            navigateUp = ::navigateUp,
            updateTitle = ::updateTitle,
            updateTransactionFor = ::updateTransactionFor,
        )
    // endregion

    // region updateUiStateAndStateEvents
    override fun updateUiStateAndStateEvents() {
        coroutineScope.launch {
            val validationState =
                editTransactionForScreenDataValidationUseCase(
                    currentTransactionFor = currentTransactionFor,
                    enteredTitle = title.text,
                )
            _uiState.update {
                EditTransactionForScreenUIState(
                    isCtaButtonEnabled = validationState.isCtaButtonEnabled,
                    isLoading = isLoading,
                    titleError = validationState.titleError,
                    title = title,
                )
            }
        }
    }
    // endregion

    // region fetchData
    override fun fetchData(): Job {
        return coroutineScope.launch {
            getCurrentTransactionFor()
        }
    }
    // endregion

    // region state events
    private fun clearTitle(): Job {
        return updateTitle(
            updatedTitle = title.copy(
                text = "",
            )
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

    private fun updateTransactionFor(): Job {
        val currentTransactionForValue = currentTransactionFor
            ?: throw IllegalStateException("Current transaction for is null")
        return coroutineScope.launch {
            startLoading()
            val isTransactionForUpdated = updateTransactionForUseCase(
                currentTransactionFor = currentTransactionForValue,
                title = title.text,
            )
            if (isTransactionForUpdated) {
                navigateUp()
            } else {
                completeLoading()
                // TODO(Abhi): Show error
            }
        }
    }
    // endregion

    // region getOriginalTransactionFor
    private suspend fun getCurrentTransactionFor(
        shouldRefresh: Boolean = false,
    ) {
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
            shouldRefresh = shouldRefresh,
        )
    }

    private fun processCurrentTransactionFor(
        currentTransactionFor: TransactionFor,
        shouldRefresh: Boolean = false,
    ) {
        updateTitle(
            updatedTitle = title
                .copy(
                    text = currentTransactionFor.title,
                    selection = TextRange(
                        index = currentTransactionFor.title.length,
                    ),
                ),
            shouldRefresh = shouldRefresh,
        )
    }
    // endregion

    // region common
    private fun getCurrentTransactionForId(): Int {
        return screenArgs.currentTransactionForId
    }
    // endregion
}
