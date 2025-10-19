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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.edit_transaction.event

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.base.ScreenUIEvent
import java.time.LocalDate
import java.time.LocalTime

@Immutable
internal sealed class EditTransactionScreenUIEvent : ScreenUIEvent {
    data object OnBottomSheetDismissed : EditTransactionScreenUIEvent()
    data object OnNavigationBackButtonClick : EditTransactionScreenUIEvent()
    data object OnClearAmountButtonClick : EditTransactionScreenUIEvent()
    data object OnClearTitleButtonClick : EditTransactionScreenUIEvent()
    data object OnCtaButtonClick : EditTransactionScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick :
        EditTransactionScreenUIEvent()

    data object OnCategoryTextFieldClick : EditTransactionScreenUIEvent()
    data object OnTransactionDateTextFieldClick : EditTransactionScreenUIEvent()
    data object OnTransactionTimeTextFieldClick : EditTransactionScreenUIEvent()
    data object OnAccountFromTextFieldClick : EditTransactionScreenUIEvent()
    data object OnAccountToTextFieldClick : EditTransactionScreenUIEvent()
    data object OnTransactionDatePickerDismissed :
        EditTransactionScreenUIEvent()

    data object OnTransactionTimePickerDismissed :
        EditTransactionScreenUIEvent()

    data object OnSnackbarDismissed : EditTransactionScreenUIEvent()

    data class OnAmountUpdated(
        val updatedAmount: TextFieldValue,
    ) : EditTransactionScreenUIEvent()

    data class OnCategoryUpdated(
        val updatedCategory: Category?,
    ) : EditTransactionScreenUIEvent()

    data class OnSelectedTransactionForIndexUpdated(
        val updatedSelectedTransactionForIndex: Int,
    ) : EditTransactionScreenUIEvent()

    data class OnSelectedTransactionTypeIndexUpdated(
        val updatedSelectedTransactionTypeIndex: Int,
    ) : EditTransactionScreenUIEvent()

    data class OnAccountFromUpdated(
        val updatedAccountFrom: Account?,
    ) : EditTransactionScreenUIEvent()

    data class OnAccountToUpdated(
        val updatedAccountTo: Account?,
    ) : EditTransactionScreenUIEvent()

    data class OnTitleUpdated(
        val updatedTitle: TextFieldValue,
    ) : EditTransactionScreenUIEvent()

    data class OnTransactionDateUpdated(
        val updatedTransactionDate: LocalDate,
    ) : EditTransactionScreenUIEvent()

    data class OnTransactionTimeUpdated(
        val updatedTransactionTime: LocalTime,
    ) : EditTransactionScreenUIEvent()
}
