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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.view_model

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.bottom_sheet.AddTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.snackbar.AddTransactionScreenSnackbarType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalTime

internal interface AddTransactionScreenUIStateDelegate {
    // region initial data
    var originalTransactionData: TransactionData?
    var transactionForValues: ImmutableList<TransactionFor>
    var selectedTransactionType: TransactionType?
    // endregion

    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val screenBottomSheetType: MutableStateFlow<AddTransactionScreenBottomSheetType>
    val screenSnackbarType: MutableStateFlow<AddTransactionScreenSnackbarType>
    val selectedTransactionTypeIndex: MutableStateFlow<Int>
    val amount: MutableStateFlow<TextFieldValue>
    val category: MutableStateFlow<Category?>
    val title: MutableStateFlow<TextFieldValue>
    val selectedTransactionForIndex: MutableStateFlow<Int>
    val accountFrom: MutableStateFlow<Account?>
    val accountTo: MutableStateFlow<Account?>
    val transactionDate: MutableStateFlow<LocalDate>
    val transactionTime: MutableStateFlow<LocalTime>
    val isTransactionDatePickerDialogVisible: MutableStateFlow<Boolean>
    val isTransactionTimePickerDialogVisible: MutableStateFlow<Boolean>
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
    fun clearAmount()

    fun clearTitle()

    fun insertTransaction()

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun resetScreenSnackbarType()

    fun updateAccountFrom(
        updatedAccountFrom: Account?,
    )

    fun updateAccountTo(
        updatedAccountTo: Account?,
    )

    fun updateAmount(
        updatedAmount: TextFieldValue,
    )

    fun updateAmount(
        updatedAmount: String,
    )

    fun updateCategory(
        updatedCategory: Category?,
    )

    fun updateIsTransactionDatePickerDialogVisible(
        updatedIsTransactionDatePickerDialogVisible: Boolean,
    )

    fun updateIsTransactionTimePickerDialogVisible(
        updatedIsTransactionTimePickerDialogVisible: Boolean,
    )

    fun updateScreenBottomSheetType(
        updatedAddTransactionScreenBottomSheetType: AddTransactionScreenBottomSheetType,
    )

    fun updateScreenSnackbarType(
        updatedAddTransactionScreenSnackbarType: AddTransactionScreenSnackbarType,
    )

    fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    )

    fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    )

    fun updateTitle(
        updatedTitle: TextFieldValue,
    )

    fun updateTransactionDate(
        updatedTransactionDate: LocalDate,
    )

    fun updateTransactionTime(
        updatedTransactionTime: LocalTime,
    )
    // endregion
}
