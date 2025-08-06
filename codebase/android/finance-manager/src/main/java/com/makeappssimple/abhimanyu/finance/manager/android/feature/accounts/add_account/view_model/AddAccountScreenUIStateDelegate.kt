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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.view_model

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegate
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.bottom_sheet.AddAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.snackbar.AddAccountScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.state.AddAccountScreenUIState
import kotlinx.collections.immutable.ImmutableList

internal interface AddAccountScreenUIStateDelegate : ScreenUIStateDelegate {
    // region initial data
    val validAccountTypesForNewAccount: ImmutableList<AccountType>
    // endregion

    // region UI state
    val screenBottomSheetType: AddAccountScreenBottomSheetType
    val screenSnackbarType: AddAccountScreenSnackbarType
    val selectedAccountTypeIndex: Int
    val name: TextFieldValue
    val minimumAccountBalanceAmountValue: TextFieldValue
    // endregion

    // region state events
    fun clearMinimumAccountBalanceAmountValue(
        refresh: Boolean = true,
    )

    fun clearName(
        refresh: Boolean = true,
    )

    fun insertAccount(
        uiState: AddAccountScreenUIState,
    )

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun resetScreenSnackbarType()

    fun updateMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
        refresh: Boolean = true,
    )

    fun updateName(
        updatedName: TextFieldValue,
        refresh: Boolean = true,
    )

    fun updateScreenBottomSheetType(
        updatedAddAccountScreenBottomSheetType: AddAccountScreenBottomSheetType,
        refresh: Boolean = true,
    )

    fun updateScreenSnackbarType(
        updatedAddAccountScreenSnackbarType: AddAccountScreenSnackbarType,
        refresh: Boolean = true,
    )

    fun updateSelectedAccountTypeIndex(
        updatedSelectedAccountTypeIndex: Int,
        refresh: Boolean = true,
    )
    // endregion
}
