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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.view_transaction.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.common.core.extensions.orEmpty
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.uri_decoder.UriDecoder
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.DeleteTransactionUseByIdCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetTransactionDataByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.transaction.toTransactionListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.navigation.ViewTransactionScreenArgs
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.view_transaction.bottom_sheet.ViewTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.view_transaction.state.ViewTransactionScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.view_transaction.state.ViewTransactionScreenUIStateEvents
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class ViewTransactionScreenViewModel(
    navigationKit: NavigationKit,
    screenUIStateDelegate: ScreenUIStateDelegate,
    savedStateHandle: SavedStateHandle,
    uriDecoder: UriDecoder,
    private val coroutineScope: CoroutineScope,
    private val dateTimeKit: DateTimeKit,
    private val deleteTransactionUseByIdCase: DeleteTransactionUseByIdCase,
    private val getTransactionDataByIdUseCase: GetTransactionDataByIdUseCase,
    internal val logKit: LogKit,
) : ScreenViewModel(
    coroutineScope = coroutineScope,
    logKit = logKit,
    navigationKit = navigationKit,
    screenUIStateDelegate = screenUIStateDelegate,
) {
    // region screen args
    private val screenArgs = ViewTransactionScreenArgs(
        savedStateHandle = savedStateHandle,
        uriDecoder = uriDecoder,
    )
    // endregion

    // region initial data
    private var currentTransactionListItemData: TransactionListItemData? = null
    private var originalTransactionListItemData: TransactionListItemData? = null
    private var refundTransactionsListItemData: ImmutableList<TransactionListItemData> =
        persistentListOf()
    private var transactionIdToDelete: Int? = null
    // endregion

    // region UI state
    private val screenBottomSheetType: MutableStateFlow<ViewTransactionScreenBottomSheetType> =
        MutableStateFlow(
            value = ViewTransactionScreenBottomSheetType.None,
        )
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<ViewTransactionScreenUIState> =
        MutableStateFlow(
            value = ViewTransactionScreenUIState(),
        )
    internal val uiStateEvents: ViewTransactionScreenUIStateEvents =
        ViewTransactionScreenUIStateEvents(
            deleteTransaction = ::deleteTransaction,
            navigateUp = ::navigateUp,
            navigateToEditTransactionScreen = ::navigateToEditTransactionScreen,
            navigateToViewTransactionScreen = ::navigateToViewTransactionScreen,
            onRefundButtonClick = ::onRefundButtonClick,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
            updateTransactionIdToDelete = {
                transactionIdToDelete = it
            },
        )
    // endregion

    // region updateUiStateAndStateEvents
    override fun updateUiStateAndStateEvents() {
        // TODO(Abhi): Fix screenBottomSheetType
        uiState.update {
            ViewTransactionScreenUIState(
                isBottomSheetVisible = screenBottomSheetType != ViewTransactionScreenBottomSheetType.None,
                isLoading = isLoading,
                refundTransactionsListItemData = refundTransactionsListItemData.orEmpty(),
                originalTransactionListItemData = originalTransactionListItemData,
                transactionListItemData = currentTransactionListItemData,
                screenBottomSheetType = screenBottomSheetType.value,
            )
        }
    }
    // endregion

    // region fetchData
    override fun fetchData(): Job {
        return viewModelScope.launch {
            withLoadingSuspend {
                getCurrentTransactionData()
            }
        }
    }
    // endregion

    // region state events
    private fun deleteTransaction() {
        val id = transactionIdToDelete
            ?: return // TODO(Abhi): Handle this error scenario
        coroutineScope.launch {
            startLoading()
            val isTransactionDeleted = deleteTransactionUseByIdCase(
                id = id,
            )
            resetScreenBottomSheetType()
            if (isTransactionDeleted) {
                // TODO(Abhi): Show success message
                transactionIdToDelete = null
                navigateUp()
            } else {
                // TODO(Abhi): Show error message
            }
            completeLoading()
        }
    }

    private fun onRefundButtonClick(
        transactionId: Int,
    ) {
        navigateToAddTransactionScreen(
            transactionId = transactionId,
        )
    }

    private fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedViewTransactionScreenBottomSheetType = ViewTransactionScreenBottomSheetType.None,
        )
    }

    private fun updateScreenBottomSheetType(
        updatedViewTransactionScreenBottomSheetType: ViewTransactionScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedViewTransactionScreenBottomSheetType
        }
    }
    // endregion

    // region getCurrentTransactionData
    private suspend fun getCurrentTransactionData() {
        val currentTransactionId = screenArgs.transactionId ?: return
        val transactionData = getTransactionDataByIdUseCase(
            id = currentTransactionId,
        ) ?: return // TODO(Abhi): Show error message
        currentTransactionListItemData = getTransactionListItemData(
            transactionData = transactionData,
        )
        transactionData.transaction.originalTransactionId?.let { transactionId ->
            getOriginalTransactionData(
                transactionId = transactionId,
            )
        }
        transactionData.transaction.refundTransactionIds?.let { transactionIds ->
            getRefundTransactionsData(
                transactionIds = transactionIds.toImmutableList(),
            )
        }
    }

    private suspend fun getOriginalTransactionData(
        transactionId: Int,
    ) {
        val transactionData = getTransactionDataByIdUseCase(
            id = transactionId,
        ) ?: return // TODO(Abhi): Show error message
        originalTransactionListItemData = getTransactionListItemData(
            transactionData = transactionData,
        )
    }

    private suspend fun getRefundTransactionsData(
        transactionIds: ImmutableList<Int>,
    ) {
        refundTransactionsListItemData =
            transactionIds.mapNotNull { transactionId ->
                getTransactionDataByIdUseCase(
                    id = transactionId,
                )?.let { transactionData ->
                    getTransactionListItemData(
                        transactionData = transactionData,
                    ) // TODO(Abhi): Show error message
                }
            }.toImmutableList()
    }

    private fun getTransactionListItemData(
        transactionData: TransactionData,
    ): TransactionListItemData {
        val transaction = transactionData.transaction
        return transactionData.toTransactionListItemData(
            dateTimeKit = dateTimeKit,
        )
            .copy(
                isDeleteButtonEnabled = transaction.refundTransactionIds.isNullOrEmpty(),
                isDeleteButtonVisible = true,
                isEditButtonVisible = transaction.transactionType != TransactionType.ADJUSTMENT,
                isExpanded = true,
                isRefundButtonVisible = transaction.transactionType == TransactionType.EXPENSE,
            )
    }
    // endregion
}
