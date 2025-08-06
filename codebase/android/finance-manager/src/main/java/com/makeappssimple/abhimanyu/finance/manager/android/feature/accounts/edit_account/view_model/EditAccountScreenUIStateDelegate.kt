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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.view_model

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.bottom_sheet.EditAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.snackbar.EditAccountScreenSnackbarType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Job

internal interface EditAccountScreenUIStateDelegate : ScreenUICommonState {
    // region initial data
    var currentAccount: Account?
    val validAccountTypesForNewAccount: ImmutableList<AccountType>
    // endregion

    // region UI state
    val balanceAmountValue: TextFieldValue
    val minimumAccountBalanceAmountValue: TextFieldValue
    val name: TextFieldValue
    val screenBottomSheetType: EditAccountScreenBottomSheetType
    val screenSnackbarType: EditAccountScreenSnackbarType
    val selectedAccountTypeIndex: Int
    // endregion

    // region state events
    fun clearBalanceAmountValue(): Job?

    fun clearMinimumAccountBalanceAmountValue(): Job?

    fun clearName(): Job?

    fun navigateUp(): Job

    fun resetScreenBottomSheetType(): Job?

    fun updateAccount(): Job

    fun updateBalanceAmountValue(
        updatedBalanceAmountValue: TextFieldValue,
        shouldRefresh: Boolean = true,
    ): Job?

    fun updateMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
        shouldRefresh: Boolean = true,
    ): Job?

    fun updateName(
        updatedName: TextFieldValue,
        shouldRefresh: Boolean = true,
    ): Job?

    fun updateScreenBottomSheetType(
        updatedEditAccountScreenBottomSheetType: EditAccountScreenBottomSheetType,
        shouldRefresh: Boolean = true,
    ): Job?

    fun updateScreenSnackbarType(
        updatedEditAccountScreenSnackbarType: EditAccountScreenSnackbarType,
        shouldRefresh: Boolean = true,
    ): Job?

    fun updateSelectedAccountTypeIndex(
        updatedSelectedAccountTypeIndex: Int,
        shouldRefresh: Boolean = true,
    ): Job?
    // endregion
}
