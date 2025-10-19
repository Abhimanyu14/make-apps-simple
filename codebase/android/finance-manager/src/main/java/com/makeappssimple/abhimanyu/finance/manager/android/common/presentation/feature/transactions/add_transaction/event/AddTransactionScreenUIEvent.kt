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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.event

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.base.ScreenUIEvent
import java.time.LocalDate
import java.time.LocalTime

@Immutable
internal sealed class AddTransactionScreenUIEvent : ScreenUIEvent {
    data object OnBottomSheetDismissed : AddTransactionScreenUIEvent()
    data object OnNavigationBackButtonClick : AddTransactionScreenUIEvent()
    data object OnClearAmountButtonClick : AddTransactionScreenUIEvent()
    data object OnClearTitleButtonClick : AddTransactionScreenUIEvent()
    data object OnCtaButtonClick : AddTransactionScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : AddTransactionScreenUIEvent()
    data object OnCategoryTextFieldClick : AddTransactionScreenUIEvent()
    data object OnTransactionDateTextFieldClick : AddTransactionScreenUIEvent()
    data object OnTransactionTimeTextFieldClick : AddTransactionScreenUIEvent()
    data object OnAccountFromTextFieldClick : AddTransactionScreenUIEvent()
    data object OnAccountToTextFieldClick : AddTransactionScreenUIEvent()
    data object OnTransactionDatePickerDismissed : AddTransactionScreenUIEvent()
    data object OnTransactionTimePickerDismissed : AddTransactionScreenUIEvent()
    data object OnSnackbarDismissed : AddTransactionScreenUIEvent()

    data class OnAmountUpdated(
        val updatedAmount: String,
    ) : AddTransactionScreenUIEvent()

    data class OnCategoryUpdated(
        val updatedCategory: Category?,
    ) : AddTransactionScreenUIEvent()

    data class OnSelectedTransactionForIndexUpdated(
        val updatedSelectedTransactionForIndex: Int,
    ) : AddTransactionScreenUIEvent()

    data class OnSelectedTransactionTypeIndexUpdated(
        val updatedSelectedTransactionTypeIndex: Int,
    ) : AddTransactionScreenUIEvent()

    data class OnAccountFromUpdated(
        val updatedAccountFrom: Account?,
    ) : AddTransactionScreenUIEvent()

    data class OnAccountToUpdated(
        val updatedAccountTo: Account?,
    ) : AddTransactionScreenUIEvent()

    data class OnTitleUpdated(
        val updatedTitle: String,
    ) : AddTransactionScreenUIEvent()

    data class OnTransactionDateUpdated(
        val updatedTransactionDate: LocalDate,
    ) : AddTransactionScreenUIEvent()

    data class OnTransactionTimeUpdated(
        val updatedTransactionTime: LocalTime,
    ) : AddTransactionScreenUIEvent()
}
