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
import com.makeappssimple.abhimanyu.common.core.extensions.filter
import com.makeappssimple.abhimanyu.common.core.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.InsertAccountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIStateDelegateImpl
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.bottom_sheet.AddAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.snackbar.AddAccountScreenSnackbarType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.state.AddAccountScreenUIState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class AddAccountScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val insertAccountUseCase: InsertAccountUseCase,
    private val navigationKit: NavigationKit,
) : AddAccountScreenUIStateDelegate, ScreenUIStateDelegateImpl() {
    // region initial data
    override val validAccountTypesForNewAccount: ImmutableList<AccountType> =
        AccountType.entries.filter {
            it != AccountType.CASH
        }
    // endregion

    // region UI state
    override var screenBottomSheetType: AddAccountScreenBottomSheetType =
        AddAccountScreenBottomSheetType.None
    override var screenSnackbarType: AddAccountScreenSnackbarType =
        AddAccountScreenSnackbarType.None
    override var selectedAccountTypeIndex = validAccountTypesForNewAccount
        .indexOf(
            element = AccountType.BANK,
        )
    override var name = TextFieldValue()
    override var minimumAccountBalanceAmountValue = TextFieldValue()
    // endregion

    // region state events
    override fun clearMinimumAccountBalanceAmountValue(
        refresh: Boolean,
    ) {
        minimumAccountBalanceAmountValue =
            minimumAccountBalanceAmountValue.copy(
                text = "",
            )
        if (refresh) {
            refresh()
        }
    }

    override fun clearName(
        refresh: Boolean,
    ) {
        name = name.copy(
            text = "",
        )
        if (refresh) {
            refresh()
        }
    }

    override fun insertAccount(
        uiState: AddAccountScreenUIState,
    ) {
        startLoading()
        coroutineScope.launch {
            val isAccountInserted = insertAccountUseCase(
                accountType = uiState.selectedAccountType,
                minimumAccountBalanceAmountValue = minimumAccountBalanceAmountValue.text.toLongOrZero(),
                name = name.text,
            )
            if (isAccountInserted == -1L) {
                // TODO(Abhi): Show error
            } else {
                navigateUp()
            }
        }
        completeLoading()
    }

    override fun navigateUp() {
        navigationKit.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedAddAccountScreenBottomSheetType = AddAccountScreenBottomSheetType.None,
        )
    }

    override fun resetScreenSnackbarType() {
        updateScreenSnackbarType(
            updatedAddAccountScreenSnackbarType = AddAccountScreenSnackbarType.None,
        )
    }

    override fun updateMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
        refresh: Boolean,
    ) {
        minimumAccountBalanceAmountValue =
            updatedMinimumAccountBalanceAmountValue
        if (refresh) {
            refresh()
        }
    }

    override fun updateName(
        updatedName: TextFieldValue,
        refresh: Boolean,
    ) {
        name = updatedName
        if (refresh) {
            refresh()
        }
    }

    override fun updateScreenBottomSheetType(
        updatedAddAccountScreenBottomSheetType: AddAccountScreenBottomSheetType,
        refresh: Boolean,
    ) {
        screenBottomSheetType = updatedAddAccountScreenBottomSheetType
        if (refresh) {
            refresh()
        }
    }

    override fun updateScreenSnackbarType(
        updatedAddAccountScreenSnackbarType: AddAccountScreenSnackbarType,
        refresh: Boolean,
    ) {
        screenSnackbarType = updatedAddAccountScreenSnackbarType
        if (refresh) {
            refresh()
        }
    }

    override fun updateSelectedAccountTypeIndex(
        updatedSelectedAccountTypeIndex: Int,
        refresh: Boolean,
    ) {
        selectedAccountTypeIndex = updatedSelectedAccountTypeIndex
        if (refresh) {
            refresh()
        }
    }
    // endregion
}
