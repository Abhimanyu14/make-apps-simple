/*
 * Copyright 2025-2026 Abhimanyu
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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.edit_transaction.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.MyLocalDate
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.date_time.MyLocalTime
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.edit_transaction.bottom_sheet.EditTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.edit_transaction.snackbar.EditTransactionScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.edit_transaction.view_model.EditTransactionScreenUiVisibilityState
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class EditTransactionScreenUIState(
    val accountFrom: Account? = null,
    val accountFromText: AccountFromText = AccountFromText.Account,
    val accountTo: Account? = null,
    val accountToText: AccountToText = AccountToText.Account,
    val screenBottomSheetType: EditTransactionScreenBottomSheetType = EditTransactionScreenBottomSheetType.None,
    val screenSnackbarType: EditTransactionScreenSnackbarType = EditTransactionScreenSnackbarType.None,
    val uiVisibilityState: EditTransactionScreenUiVisibilityState = EditTransactionScreenUiVisibilityState.Expense,
    val isBottomSheetVisible: Boolean = false,
    val isCtaButtonEnabled: Boolean = false,
    val isLoading: Boolean = true,
    val isTransactionDatePickerDialogVisible: Boolean = false,
    val isTransactionTimePickerDialogVisible: Boolean = false,
    val category: Category? = null,
    val selectedTransactionForIndex: Int = 0,
    val selectedTransactionTypeIndex: Int? = null,
    val accounts: ImmutableList<Account> = persistentListOf(),
    val filteredCategories: ImmutableList<Category> = persistentListOf(),
    val titleSuggestionsChipUIData: ImmutableList<ChipUIData> = persistentListOf(),
    val transactionForValuesChipUIData: ImmutableList<ChipUIData> = persistentListOf(),
    val transactionTypesForNewTransactionChipUIData: ImmutableList<ChipUIData> = persistentListOf(),
    val titleSuggestions: ImmutableList<String> = persistentListOf(),
    val currentLocalDate: MyLocalDate = MyLocalDate.MIN,
    val transactionDate: MyLocalDate = MyLocalDate.MIN,
    val transactionTime: MyLocalTime = MyLocalTime.MIN,
    val amountErrorText: String? = null,
    val amount: TextFieldValue = TextFieldValue(),
    val title: TextFieldValue = TextFieldValue(),
    val selectedTransactionType: TransactionType = TransactionType.EXPENSE,
) : ScreenUIState

internal sealed class AccountFromText {
    data object AccountFrom : AccountFromText()
    data object Account : AccountFromText()
}

internal val AccountFromText.stringResourceId: Int
    get() {
        return when (this) {
            AccountFromText.AccountFrom -> R.string.finance_manager_screen_add_or_edit_transaction_account_from
            AccountFromText.Account -> R.string.finance_manager_screen_add_or_edit_transaction_account
        }
    }

internal sealed class AccountToText {
    data object AccountTo : AccountToText()
    data object Account : AccountToText()
}

internal val AccountToText.stringResourceId: Int
    get() {
        return when (this) {
            AccountToText.AccountTo -> R.string.finance_manager_screen_add_or_edit_transaction_account_to
            AccountToText.Account -> R.string.finance_manager_screen_add_or_edit_transaction_account
        }
    }
