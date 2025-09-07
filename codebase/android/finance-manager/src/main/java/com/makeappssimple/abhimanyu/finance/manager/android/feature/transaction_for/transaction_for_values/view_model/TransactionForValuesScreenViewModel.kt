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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.transaction_for_values.view_model

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.common.core.coroutines.getCompletedJob
import com.makeappssimple.abhimanyu.common.core.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.common.core.extensions.mapIndexed
import com.makeappssimple.abhimanyu.common.core.extensions.orFalse
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.CheckIfTransactionForValuesAreUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.DeleteTransactionForByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.transaction_for.TransactionForListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.isDefaultTransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.transaction_for_values.bottom_sheet.TransactionForValuesScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.transaction_for_values.state.TransactionForValuesScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.transaction_for_values.state.TransactionForValuesScreenUIStateEvents
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
internal class TransactionForValuesScreenViewModel(
    navigationKit: NavigationKit,
    private val coroutineScope: CoroutineScope,
    private val getAllTransactionForValuesFlowUseCase: GetAllTransactionForValuesFlowUseCase,
    private val checkIfTransactionForValuesAreUsedInTransactionsUseCase: CheckIfTransactionForValuesAreUsedInTransactionsUseCase,
    private val deleteTransactionForByIdUseCase: DeleteTransactionForByIdUseCase,
    internal val logKit: LogKit,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit,
    NavigationKit by navigationKit {
    // region data
    private var isLoading: Boolean = false
    private var transactionForListItemDataList: ImmutableList<TransactionForListItemData> =
        persistentListOf()
    private var transactionForIdToDelete: Int? = null
    private var screenBottomSheetType: TransactionForValuesScreenBottomSheetType =
        TransactionForValuesScreenBottomSheetType.None
    // endregion

    // region uiState
    private val _uiState: MutableStateFlow<TransactionForValuesScreenUIState> =
        MutableStateFlow(
            value = TransactionForValuesScreenUIState(),
        )
    internal val uiState: StateFlow<TransactionForValuesScreenUIState> =
        _uiState.asStateFlow()
    // endregion

    // region uiStateEvents
    internal val uiStateEvents: TransactionForValuesScreenUIStateEvents =
        TransactionForValuesScreenUIStateEvents(
            deleteTransactionFor = ::deleteTransactionFor,
            navigateToAddTransactionForScreen = ::navigateToAddTransactionForScreen,
            navigateToEditTransactionForScreen = ::navigateToEditTransactionForScreen,
            navigateUp = ::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
            updateTransactionForIdToDelete = ::updateTransactionForIdToDelete,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        coroutineScope.launch {
            observeData()
            completeLoading()
        }
    }
    // endregion

    // region refreshUiState
    private fun refreshUiState(): Job {
        return coroutineScope.launch {
            updateUiState()
        }
    }

    private fun updateUiState() {
        _uiState.update {
            TransactionForValuesScreenUIState(
                isBottomSheetVisible = screenBottomSheetType != TransactionForValuesScreenBottomSheetType.None,
                isLoading = isLoading,
                transactionForListItemDataList = transactionForListItemDataList,
                screenBottomSheetType = screenBottomSheetType,
            )
        }
    }
    // endregion

    // region observeData
    private fun observeData() {
        observeForTransactionForListItemDataList()
    }
    // endregion

    // region state events
    private fun deleteTransactionFor(): Job {
        val id = requireNotNull(
            value = transactionForIdToDelete,
        ) {
            "transactionForIdToDelete should not be null when deleting a transaction for"
        }
        return coroutineScope.launch {
            startLoading()
            val isTransactionForDeleted = deleteTransactionForByIdUseCase(
                id = id,
            )
            if (isTransactionForDeleted) {
                // TODO(Abhi): Show snackbar
                transactionForIdToDelete = null
            } else {
                throw IllegalStateException("TransactionFor with id $id could not be deleted")
            }
            completeLoading()
        }
    }

    private fun resetScreenBottomSheetType(): Job {
        return updateScreenBottomSheetType(
            updatedTransactionForValuesScreenBottomSheetType = TransactionForValuesScreenBottomSheetType.None,
        )
    }

    private fun updateScreenBottomSheetType(
        updatedTransactionForValuesScreenBottomSheetType: TransactionForValuesScreenBottomSheetType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenBottomSheetType = updatedTransactionForValuesScreenBottomSheetType
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateTransactionForIdToDelete(
        updatedTransactionForIdToDelete: Int?,
        shouldRefresh: Boolean = false,
    ): Job {
        transactionForIdToDelete = updatedTransactionForIdToDelete
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }
    // endregion

    // region observeForTransactionForListItemDataList
    private fun observeForTransactionForListItemDataList(): Job {
        return coroutineScope.launch {
            getAllTransactionForValuesFlowUseCase().collectLatest { updatedAllTransactionForValues ->
                val transactionForValuesIsUsedInTransactions =
                    checkIfTransactionForValuesAreUsedInTransactionsUseCase(
                        transactionForValues = updatedAllTransactionForValues,
                    )
                transactionForListItemDataList = updatedAllTransactionForValues
                    .mapIndexed { index: Int, transactionFor: TransactionFor ->
                        TransactionForListItemData(
                            transactionForId = transactionFor.id,
                            isMoreOptionsIconButtonVisible = true,
                            isDeleteBottomSheetMenuItemVisible = !isDefaultTransactionFor(
                                transactionFor = transactionFor.title,
                            ) && transactionForValuesIsUsedInTransactions.getOrNull(
                                index = index,
                            )?.not().orFalse(),
                            title = transactionFor.title.capitalizeWords(),
                        )
                    }
                refreshUiState()
            }
        }
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
