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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transactionfor.transaction_for_values.viewmodel

import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactionfor.transaction_for_values.bottomsheet.TransactionForValuesScreenBottomSheetType
import kotlinx.coroutines.flow.MutableStateFlow

internal interface TransactionForValuesScreenUIStateDelegate {
    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val transactionForIdToDelete: MutableStateFlow<Int?>
    val screenBottomSheetType: MutableStateFlow<TransactionForValuesScreenBottomSheetType>
    // endregion

    // region loading
    fun startLoading()

    fun completeLoading()

    fun <T> withLoading(
        block: () -> T,
    ): T

    suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T
    // endregion

    // region state events
    fun deleteTransactionFor()

    fun navigateToAddTransactionForScreen()

    fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    )

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun updateScreenBottomSheetType(
        updatedTransactionForValuesScreenBottomSheetType: TransactionForValuesScreenBottomSheetType,
    )

    fun updateTransactionForIdToDelete(
        updatedTransactionForIdToDelete: Int?,
    )
    // endregion
}
