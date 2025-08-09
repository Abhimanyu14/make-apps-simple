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

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.common.core.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.common.core.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.common.core.extensions.mapIndexed
import com.makeappssimple.abhimanyu.common.core.extensions.orFalse
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.CheckIfTransactionForValuesAreUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.DeleteTransactionForByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.transaction_for.TransactionForListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.isDefaultTransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.transaction_for_values.bottom_sheet.TransactionForValuesScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.transaction_for_values.state.TransactionForValuesScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.transaction_for_values.state.TransactionForValuesScreenUIStateEvents
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class TransactionForValuesScreenViewModel(
    private val coroutineScope: CoroutineScope,
    private val getAllTransactionForValuesFlowUseCase: GetAllTransactionForValuesFlowUseCase,
    private val checkIfTransactionForValuesAreUsedInTransactionsUseCase: CheckIfTransactionForValuesAreUsedInTransactionsUseCase,
    private val deleteTransactionForByIdUseCase: DeleteTransactionForByIdUseCase,
    private val navigationKit: NavigationKit,
    internal val logKit: LogKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
) {
    // region initial data
    private var transactionForListItemDataList: ImmutableList<TransactionForListItemData> =
        persistentListOf()
    // endregion

    // region UI state
    val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    var transactionForIdToDelete: Int? = null
    val screenBottomSheetType: MutableStateFlow<TransactionForValuesScreenBottomSheetType> =
        MutableStateFlow(
            value = TransactionForValuesScreenBottomSheetType.None,
        )
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<TransactionForValuesScreenUIState> =
        MutableStateFlow(
            value = TransactionForValuesScreenUIState(),
        )
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
        observeData()
        fetchData()
    }

    private fun fetchData() {}

    private fun observeData() {
        observeForUiStateAndStateEvents()
        observeForTransactionForListItemDataList()
    }
    // endregion

    // region loading
    fun startLoading() {
        isLoading.update {
            true
        }
    }

    fun completeLoading() {
        isLoading.update {
            false
        }
    }

    fun <T> withLoading(
        block: () -> T,
    ): T {
        startLoading()
        val result = block()
        completeLoading()
        return result
    }

    suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T {
        startLoading()
        try {
            return block()
        } finally {
            completeLoading()
        }
    }
    // endregion

    // region state events
    fun deleteTransactionFor() {
        coroutineScope.launch {
            transactionForIdToDelete?.let { id ->
                val isTransactionForDeleted = deleteTransactionForByIdUseCase(
                    id = id,
                )
                if (isTransactionForDeleted) {
                    // TODO(Abhi): Show snackbar
                    transactionForIdToDelete = null
                } else {
                    // TODO(Abhi): Handle this error scenario
                }
            } ?: run {
                // TODO(Abhi): Handle this error scenario
            }
        }
    }

    fun navigateToAddTransactionForScreen() {
        navigationKit.navigateToAddTransactionForScreen()
    }

    fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    ) {
        navigationKit.navigateToEditTransactionForScreen(
            transactionForId = transactionForId,
        )
    }

    fun navigateUp() {
        navigationKit.navigateUp()
    }

    fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedTransactionForValuesScreenBottomSheetType = TransactionForValuesScreenBottomSheetType.None,
        )
    }

    fun updateScreenBottomSheetType(
        updatedTransactionForValuesScreenBottomSheetType: TransactionForValuesScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedTransactionForValuesScreenBottomSheetType
        }
    }

    fun updateTransactionForIdToDelete(
        updatedTransactionForIdToDelete: Int?,
    ) {
        transactionForIdToDelete = updatedTransactionForIdToDelete
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                    ),
                ->
                uiState.update {
                    TransactionForValuesScreenUIState(
                        isBottomSheetVisible = screenBottomSheetType != TransactionForValuesScreenBottomSheetType.None,
                        isLoading = isLoading,
                        transactionForListItemDataList = transactionForListItemDataList,
                        screenBottomSheetType = screenBottomSheetType,
                    )
                }
            }
        }
    }
    // endregion

    // region observeForTransactionForListItemDataList
    private fun observeForTransactionForListItemDataList() {
        viewModelScope.launch {
            getAllTransactionForValuesFlowUseCase().collectLatest { updatedAllTransactionForValues ->
                startLoading()
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
                completeLoading()
            }
        }
    }
    // endregion
}
