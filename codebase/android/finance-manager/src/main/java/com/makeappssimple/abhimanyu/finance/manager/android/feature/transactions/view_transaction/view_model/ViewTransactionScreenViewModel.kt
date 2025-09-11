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
import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.common.core.coroutines.getCompletedJob
import com.makeappssimple.abhimanyu.common.core.log_kit.LogKit
import com.makeappssimple.abhimanyu.common.core.uri_decoder.UriDecoder
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.DeleteTransactionUseByIdCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.GetTransactionDataByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class ViewTransactionScreenViewModel(
    navigationKit: NavigationKit,
    savedStateHandle: SavedStateHandle,
    uriDecoder: UriDecoder,
    private val coroutineScope: CoroutineScope,
    private val dateTimeKit: DateTimeKit,
    private val deleteTransactionUseByIdCase: DeleteTransactionUseByIdCase,
    private val getTransactionDataByIdUseCase: GetTransactionDataByIdUseCase,
    internal val logKit: LogKit,
) : ViewModel(
    viewModelScope = coroutineScope,
), LogKit by logKit,
    NavigationKit by navigationKit {
    // region screen args
    private val screenArgs = ViewTransactionScreenArgs(
        savedStateHandle = savedStateHandle,
        uriDecoder = uriDecoder,
    )
    // endregion

    // region data
    private var isLoading: Boolean = true
    private var transactionIdToDelete: Int? = null
    private var refundTransactionsListItemData: ImmutableList<TransactionListItemData> =
        persistentListOf()
    private var currentTransactionListItemData: TransactionListItemData? = null
    private var originalTransactionListItemData: TransactionListItemData? = null
    private var screenBottomSheetType: ViewTransactionScreenBottomSheetType =
        ViewTransactionScreenBottomSheetType.None
    // endregion

    // region uiState
    private val _uiState: MutableStateFlow<ViewTransactionScreenUIState> =
        MutableStateFlow(
            value = ViewTransactionScreenUIState(),
        )
    internal val uiState: StateFlow<ViewTransactionScreenUIState> =
        _uiState.asStateFlow()
    // endregion

    // region uiStateEvents
    internal val uiStateEvents: ViewTransactionScreenUIStateEvents =
        ViewTransactionScreenUIStateEvents(
            deleteTransaction = ::deleteTransaction,
            navigateUp = ::navigateUp,
            navigateToEditTransactionScreen = ::navigateToEditTransactionScreen,
            navigateToViewTransactionScreen = ::navigateToViewTransactionScreen,
            onRefundButtonClick = ::navigateToAddTransactionScreen,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            updateScreenBottomSheetType = ::updateScreenBottomSheetType,
            updateTransactionIdToDelete = ::updateTransactionIdToDelete,
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        coroutineScope.launch {
            fetchData()
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
            ViewTransactionScreenUIState(
                isBottomSheetVisible = screenBottomSheetType != ViewTransactionScreenBottomSheetType.None,
                isLoading = isLoading,
                refundTransactionsListItemData = refundTransactionsListItemData,
                originalTransactionListItemData = originalTransactionListItemData,
                transactionListItemData = currentTransactionListItemData,
                screenBottomSheetType = screenBottomSheetType,
            )
        }
    }
    // endregion

    // region fetchData
    private suspend fun fetchData() {
        getCurrentTransactionData()
    }

    private suspend fun getCurrentTransactionData() {
        val currentTransactionId = getCurrentTransactionId()
        val currentTransactionData = getTransactionData(
            transactionId = currentTransactionId,
        )
        currentTransactionListItemData = getTransactionListItemData(
            transactionData = currentTransactionData,
        )
        currentTransactionData.transaction.originalTransactionId?.let { originalTransactionId ->
            updateOriginalTransactionData(
                originalTransactionId = originalTransactionId,
            )
        }
        currentTransactionData.transaction.refundTransactionIds?.let { refundTransactionIds ->
            updateRefundTransactionsData(
                refundTransactionIds = refundTransactionIds.toImmutableList(),
            )
        }
    }

    private suspend fun updateOriginalTransactionData(
        originalTransactionId: Int,
    ) {
        val originalTransactionData = getTransactionData(
            transactionId = originalTransactionId,
        )
        originalTransactionListItemData = getTransactionListItemData(
            transactionData = originalTransactionData,
        )
    }

    private suspend fun updateRefundTransactionsData(
        refundTransactionIds: ImmutableList<Int>,
    ) {
        refundTransactionsListItemData = refundTransactionIds
            .map { refundTransactionId ->
                val refundTransactionData = getTransactionData(
                    transactionId = refundTransactionId,
                )
                getTransactionListItemData(
                    transactionData = refundTransactionData,
                )
            }
            .toImmutableList()
    }

    private suspend fun getTransactionData(
        transactionId: Int,
    ): TransactionData {
        val transactionData = getTransactionDataByIdUseCase(
            id = transactionId,
        )
        checkNotNull(
            value = transactionData,
            lazyMessage = {
                "Transaction data for ID $transactionId must not be null."
            },
        )
        return transactionData
    }

    private fun getTransactionListItemData(
        transactionData: TransactionData,
    ): TransactionListItemData {
        val transaction = transactionData.transaction
        return transactionData
            .toTransactionListItemData(
                getReadableDateAndTime = dateTimeKit::getReadableDateAndTime,
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

    // region state events
    private fun deleteTransaction(): Job {
        val id = checkNotNull(
            value = transactionIdToDelete,
            lazyMessage = {
                "Transaction ID to delete must not be null."
            },
        )
        return coroutineScope.launch {
            startLoading()
            val isTransactionDeleted = deleteTransactionUseByIdCase(
                id = id,
            )
            if (isTransactionDeleted) {
                navigateUp()
            } else {
                resetScreenBottomSheetType()
                updateTransactionIdToDelete(
                    updatedTransactionIdToDelete = null,
                    shouldRefresh = false,
                )
                completeLoading()
                // TODO(Abhi): Show error message
            }
        }
    }

    private fun resetScreenBottomSheetType(): Job {
        return updateScreenBottomSheetType(
            updatedViewTransactionScreenBottomSheetType = ViewTransactionScreenBottomSheetType.None,
        )
    }

    private fun updateScreenBottomSheetType(
        updatedViewTransactionScreenBottomSheetType: ViewTransactionScreenBottomSheetType,
        shouldRefresh: Boolean = true,
    ): Job {
        screenBottomSheetType = updatedViewTransactionScreenBottomSheetType
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }

    private fun updateTransactionIdToDelete(
        updatedTransactionIdToDelete: Int?,
        shouldRefresh: Boolean = false,
    ): Job {
        transactionIdToDelete = updatedTransactionIdToDelete
        return if (shouldRefresh) {
            refreshUiState()
        } else {
            getCompletedJob()
        }
    }
    // endregion

    // region screen args
    private fun getCurrentTransactionId(): Int {
        return screenArgs.currentTransactionId
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
