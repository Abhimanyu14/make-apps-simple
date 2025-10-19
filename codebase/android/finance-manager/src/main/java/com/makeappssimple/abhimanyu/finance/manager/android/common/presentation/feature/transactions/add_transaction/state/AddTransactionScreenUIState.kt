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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.state

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Category
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.bottom_sheet.AddTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.snackbar.AddTransactionScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transactions.add_transaction.view_model.AddTransactionScreenUiVisibilityState
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.LocalTime

@Stable
internal data class AddTransactionScreenUIState(
    val accountFrom: Account? = null,
    val accountFromText: AccountFromText = AccountFromText.Account,
    val accountTo: Account? = null,
    val accountToText: AccountToText = AccountToText.Account,
    val screenBottomSheetType: AddTransactionScreenBottomSheetType = AddTransactionScreenBottomSheetType.None,
    val screenSnackbarType: AddTransactionScreenSnackbarType = AddTransactionScreenSnackbarType.None,
    val uiVisibilityState: AddTransactionScreenUiVisibilityState = AddTransactionScreenUiVisibilityState.Expense(),
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
    val currentLocalDate: LocalDate = LocalDate.MIN,
    val transactionDate: LocalDate = LocalDate.MIN,
    val transactionTime: LocalTime = LocalTime.MIN,
    val amountErrorText: String? = null,
    val amountTextFieldState: TextFieldState = TextFieldState(),
    val titleTextFieldState: TextFieldState = TextFieldState(),
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
