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

import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.DeleteTransactionForUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.transaction_for_values.bottom_sheet.TransactionForValuesScreenBottomSheetType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class TransactionForValuesScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val deleteTransactionForUseCase: DeleteTransactionForUseCase,
    private val navigationKit: NavigationKit,
) : TransactionForValuesScreenUIStateDelegate {
    // region UI state
    override val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    override val transactionForIdToDelete: MutableStateFlow<Int?> =
        MutableStateFlow(
            value = null,
        )
    override val screenBottomSheetType: MutableStateFlow<TransactionForValuesScreenBottomSheetType> =
        MutableStateFlow(
            value = TransactionForValuesScreenBottomSheetType.None,
        )
    // endregion

    // region loading
    override fun startLoading() {
        isLoading.update {
            true
        }
    }

    override fun completeLoading() {
        isLoading.update {
            false
        }
    }

    override fun <T> withLoading(
        block: () -> T,
    ): T {
        startLoading()
        val result = block()
        completeLoading()
        return result
    }

    override suspend fun <T> withLoadingSuspend(
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
    override fun deleteTransactionFor(
    ) {
        coroutineScope.launch {
            transactionForIdToDelete.value?.let { id ->
                val isTransactionForDeleted = deleteTransactionForUseCase(
                    id = id,
                )
                if (!isTransactionForDeleted) {
                    // TODO(Abhi): Show error
                }
            }
        }
    }

    override fun navigateToAddTransactionForScreen() {
        navigationKit.navigateToAddTransactionForScreen()
    }

    override fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    ) {
        navigationKit.navigateToEditTransactionForScreen(
            transactionForId = transactionForId,
        )
    }

    override fun navigateUp() {
        navigationKit.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedTransactionForValuesScreenBottomSheetType = TransactionForValuesScreenBottomSheetType.None,
        )
    }

    override fun updateScreenBottomSheetType(
        updatedTransactionForValuesScreenBottomSheetType: TransactionForValuesScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedTransactionForValuesScreenBottomSheetType
        }
    }

    override fun updateTransactionForIdToDelete(
        updatedTransactionForIdToDelete: Int?,
    ) {
        transactionForIdToDelete.update {
            updatedTransactionForIdToDelete
        }
    }
    // endregion
}
