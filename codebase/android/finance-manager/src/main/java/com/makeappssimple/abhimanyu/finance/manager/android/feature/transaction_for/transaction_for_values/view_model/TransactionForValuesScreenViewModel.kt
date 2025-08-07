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
import com.makeappssimple.abhimanyu.common.log_kit.LogKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.CheckIfTransactionForValuesAreUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.DeleteTransactionForUseCase
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
    coroutineScope: CoroutineScope,
    private val getAllTransactionForValuesFlowUseCase: GetAllTransactionForValuesFlowUseCase,
    private val checkIfTransactionForValuesAreUsedInTransactionsUseCase: CheckIfTransactionForValuesAreUsedInTransactionsUseCase,
    private val deleteTransactionForUseCase: DeleteTransactionForUseCase,
    private val navigationKit: NavigationKit,
    internal val logKit: LogKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
),
    TransactionForValuesScreenUIStateDelegate by TransactionForValuesScreenUIStateDelegateImpl(
        coroutineScope = coroutineScope,
        deleteTransactionForUseCase = deleteTransactionForUseCase,
        navigationKit = navigationKit,
    ) {
    // region initial data
    private var transactionForListItemDataList: ImmutableList<TransactionForListItemData> =
        persistentListOf()
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

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                transactionForIdToDelete,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        transactionForIdToDelete,
                    ),
                ->
                uiState.update {
                    TransactionForValuesScreenUIState(
                        isBottomSheetVisible = screenBottomSheetType != TransactionForValuesScreenBottomSheetType.None,
                        isLoading = isLoading,
                        transactionForIdToDelete = transactionForIdToDelete,
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
