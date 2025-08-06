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
import com.makeappssimple.abhimanyu.common.core.extensions.filterDigits
import com.makeappssimple.abhimanyu.common.core.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.bottom_sheet.AddTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.snackbar.AddTransactionScreenSnackbarType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

internal class AddTransactionScreenUIStateDelegateImpl(
    dateTimeKit: DateTimeKit,
    private val coroutineScope: CoroutineScope,
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val navigationKit: NavigationKit,
) : AddTransactionScreenUIStateDelegate {
    // region initial data
    override var originalTransactionData: TransactionData? = null
    override var transactionForValues: ImmutableList<TransactionFor> =
        persistentListOf()
    override var selectedTransactionType: TransactionType? = null
    // endregion

    // region UI state
    override val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    override val screenBottomSheetType: MutableStateFlow<AddTransactionScreenBottomSheetType> =
        MutableStateFlow(
            value = AddTransactionScreenBottomSheetType.None,
        )
    override val screenSnackbarType: MutableStateFlow<AddTransactionScreenSnackbarType> =
        MutableStateFlow(
            value = AddTransactionScreenSnackbarType.None,
        )
    override val selectedTransactionTypeIndex: MutableStateFlow<Int> =
        MutableStateFlow(
            value = 0,
        )
    override val amount: MutableStateFlow<TextFieldValue> =
        MutableStateFlow(
            value = TextFieldValue(),
        )
    override val category: MutableStateFlow<Category?> =
        MutableStateFlow(
            value = null,
        )
    override val title: MutableStateFlow<TextFieldValue> =
        MutableStateFlow(
            value = TextFieldValue(),
        )
    override val selectedTransactionForIndex: MutableStateFlow<Int> =
        MutableStateFlow(
            value = 0,
        )
    override val accountFrom: MutableStateFlow<Account?> =
        MutableStateFlow(
            value = null,
        )
    override val accountTo: MutableStateFlow<Account?> =
        MutableStateFlow(
            value = null,
        )
    override val transactionDate: MutableStateFlow<LocalDate> =
        MutableStateFlow(
            value = dateTimeKit.getCurrentLocalDate(),
        )
    override val transactionTime: MutableStateFlow<LocalTime> =
        MutableStateFlow(
            value = dateTimeKit.getCurrentLocalTime(),
        )
    override val isTransactionDatePickerDialogVisible: MutableStateFlow<Boolean> =
        MutableStateFlow(
            value = false,
        )
    override val isTransactionTimePickerDialogVisible: MutableStateFlow<Boolean> =
        MutableStateFlow(
            value = false,
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
    override fun clearAmount() {
        amount.update {
            it.copy(
                text = "",
            )
        }
    }

    override fun clearTitle() {
        title.update {
            it.copy(
                text = "",
            )
        }
    }

    override fun insertTransaction() {
        val selectedAccountFrom = accountFrom.value
        val selectedAccountTo = accountTo.value
        val selectedCategoryId = category.value?.id
        val selectedTransactionForId =
            if (selectedTransactionForIndex.value != -1) {
                transactionForValues[selectedTransactionForIndex.value].id
            } else {
                -1
            }
        val selectedTransactionDate = transactionDate.value
        val selectedTransactionTime = transactionTime.value
        val enteredAmountValue = amount.value.text.toLongOrZero()
        val enteredTitle = title.value.text
        val selectedTransactionType = this.selectedTransactionType ?: return
        val originalTransaction = originalTransactionData?.transaction

        coroutineScope.launch {
            val isTransactionInserted = insertTransactionUseCase(
                selectedAccountFrom = selectedAccountFrom,
                selectedAccountTo = selectedAccountTo,
                selectedCategoryId = selectedCategoryId,
                selectedTransactionForId = selectedTransactionForId,
                selectedTransactionDate = selectedTransactionDate,
                selectedTransactionTime = selectedTransactionTime,
                enteredAmountValue = enteredAmountValue,
                enteredTitle = enteredTitle,
                selectedTransactionType = selectedTransactionType,
                originalTransaction = originalTransaction,
            )
            if (isTransactionInserted) {
                updateScreenSnackbarType(AddTransactionScreenSnackbarType.AddTransactionSuccessful)
                navigationKit.navigateUp()
            } else {
                updateScreenSnackbarType(AddTransactionScreenSnackbarType.AddTransactionFailed)
            }
        }
    }

    override fun navigateUp() {
        navigationKit.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedAddTransactionScreenBottomSheetType = AddTransactionScreenBottomSheetType.None,
        )
    }

    override fun resetScreenSnackbarType() {
        updateScreenSnackbarType(
            updatedAddTransactionScreenSnackbarType = AddTransactionScreenSnackbarType.None,
        )
    }

    override fun updateAccountFrom(
        updatedAccountFrom: Account?,
    ) {
        accountFrom.update {
            updatedAccountFrom
        }
    }

    override fun updateAccountTo(
        updatedAccountTo: Account?,
    ) {
        accountTo.update {
            updatedAccountTo
        }
    }

    override fun updateAmount(
        updatedAmount: TextFieldValue,
    ) {
        amount.update {
            updatedAmount.copy(
                text = updatedAmount.text.filterDigits(),
            )
        }
    }

    override fun updateAmount(
        updatedAmount: String,
    ) {
        amount.update {
            it.copy(
                text = updatedAmount.filterDigits(),
            )
        }
    }

    override fun updateCategory(
        updatedCategory: Category?,
    ) {
        category.update {
            updatedCategory
        }
    }

    override fun updateIsTransactionDatePickerDialogVisible(
        updatedIsTransactionDatePickerDialogVisible: Boolean,
    ) {
        isTransactionDatePickerDialogVisible.update {
            updatedIsTransactionDatePickerDialogVisible
        }
    }

    override fun updateIsTransactionTimePickerDialogVisible(
        updatedIsTransactionTimePickerDialogVisible: Boolean,
    ) {
        isTransactionTimePickerDialogVisible.update {
            updatedIsTransactionTimePickerDialogVisible
        }
    }

    override fun updateScreenBottomSheetType(
        updatedAddTransactionScreenBottomSheetType: AddTransactionScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedAddTransactionScreenBottomSheetType
        }
    }

    override fun updateScreenSnackbarType(
        updatedAddTransactionScreenSnackbarType: AddTransactionScreenSnackbarType,
    ) {
        screenSnackbarType.update {
            updatedAddTransactionScreenSnackbarType
        }
    }

    override fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    ) {
        selectedTransactionForIndex.update {
            updatedSelectedTransactionForIndex
        }
    }

    override fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        selectedTransactionTypeIndex.update {
            updatedSelectedTransactionTypeIndex
        }
    }

    override fun updateTitle(
        updatedTitle: TextFieldValue,
    ) {
        title.update {
            updatedTitle
        }
    }

    override fun updateTransactionDate(
        updatedTransactionDate: LocalDate,
    ) {
        transactionDate.update {
            updatedTransactionDate
        }
    }

    override fun updateTransactionTime(
        updatedTransactionTime: LocalTime,
    ) {
        transactionTime.update {
            updatedTransactionTime
        }
    }
    // endregion
}
