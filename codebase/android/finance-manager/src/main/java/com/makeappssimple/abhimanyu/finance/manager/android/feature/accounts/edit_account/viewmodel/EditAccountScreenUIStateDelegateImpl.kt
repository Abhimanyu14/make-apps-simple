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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.finance.manager.android.core.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.account.UpdateAccountUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Account
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.bottomsheet.EditAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.snackbar.EditAccountScreenSnackbarType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.makeappssimple.abhimanyu.common.core.extensions.filter
import com.makeappssimple.abhimanyu.common.core.extensions.map

internal class EditAccountScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val navigationKit: NavigationKit,
    private val screenUICommonState: ScreenUICommonState,
    private val updateAccountUseCase: UpdateAccountUseCase,
) : EditAccountScreenUIStateDelegate,
    ScreenUICommonState by screenUICommonState {
    // region initial data
    override var currentAccount: Account? = null
    override val validAccountTypesForNewAccount: ImmutableList<AccountType> =
        AccountType.entries.filter {
            it != AccountType.CASH
        }
    // endregion

    // region UI state
    // override var isLoading: Boolean = true
    override var minimumAccountBalanceAmountValue: TextFieldValue =
        TextFieldValue()
    override var name = TextFieldValue()
    override var balanceAmountValue = TextFieldValue()
    override var screenBottomSheetType: EditAccountScreenBottomSheetType =
        EditAccountScreenBottomSheetType.None
    override var screenSnackbarType: EditAccountScreenSnackbarType =
        EditAccountScreenSnackbarType.None
    override var selectedAccountTypeIndex: Int = validAccountTypesForNewAccount
        .indexOf(
            element = AccountType.BANK,
        )
    // endregion

    // region state events
    override fun clearBalanceAmountValue(): Job? {
        return updateBalanceAmountValue(
            updatedBalanceAmountValue = balanceAmountValue
                .copy(
                    text = "",
                ),
        )
    }

    override fun clearMinimumAccountBalanceAmountValue(): Job? {
        return updateMinimumAccountBalanceAmountValue(
            updatedMinimumAccountBalanceAmountValue = minimumAccountBalanceAmountValue
                .copy(
                    text = "",
                ),
        )
    }

    override fun clearName(): Job? {
        return updateName(
            updatedName = name
                .copy(
                    text = "",
                ),
        )
    }

    override fun navigateUp(): Job {
        return navigationKit.navigateUp()
    }

    override fun resetScreenBottomSheetType(): Job? {
        return updateScreenBottomSheetType(
            updatedEditAccountScreenBottomSheetType = EditAccountScreenBottomSheetType.None,
        )
    }

    override fun updateAccount(): Job {
        return coroutineScope.launch {
            startLoading()

            val isAccountUpdated = updateAccountUseCase(
                currentAccount = currentAccount,
                validAccountTypesForNewAccount = validAccountTypesForNewAccount,
                selectedAccountTypeIndex = selectedAccountTypeIndex,
                balanceAmountValue = balanceAmountValue.text,
                minimumAccountBalanceAmountValue = minimumAccountBalanceAmountValue.text,
                name = name.text,
            )
            if (isAccountUpdated) {
                navigationKit.navigateUp()
            } else {
                completeLoading()
                // TODO: Show Error
            }
        }
    }

    override fun updateBalanceAmountValue(
        updatedBalanceAmountValue: TextFieldValue,
        shouldRefresh: Boolean,
    ): Job? {
        balanceAmountValue = updatedBalanceAmountValue
        if (shouldRefresh) {
            return refresh()
        }
        return null
    }

    override fun updateMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
        shouldRefresh: Boolean,
    ): Job? {
        minimumAccountBalanceAmountValue =
            updatedMinimumAccountBalanceAmountValue
        if (shouldRefresh) {
            return refresh()
        }
        return null
    }

    override fun updateName(
        updatedName: TextFieldValue,
        shouldRefresh: Boolean,
    ): Job? {
        name = updatedName
        if (shouldRefresh) {
            return refresh()
        }
        return null
    }

    override fun updateScreenBottomSheetType(
        updatedEditAccountScreenBottomSheetType: EditAccountScreenBottomSheetType,
        shouldRefresh: Boolean,
    ): Job? {
        screenBottomSheetType = updatedEditAccountScreenBottomSheetType
        if (shouldRefresh) {
            return refresh()
        }
        return null
    }

    override fun updateScreenSnackbarType(
        updatedEditAccountScreenSnackbarType: EditAccountScreenSnackbarType,
        shouldRefresh: Boolean,
    ): Job? {
        screenSnackbarType = updatedEditAccountScreenSnackbarType
        if (shouldRefresh) {
            return refresh()
        }
        return null
    }

    override fun updateSelectedAccountTypeIndex(
        updatedSelectedAccountTypeIndex: Int,
        shouldRefresh: Boolean,
    ): Job? {
        selectedAccountTypeIndex = updatedSelectedAccountTypeIndex
        if (shouldRefresh) {
            return refresh()
        }
        return null
    }
    // endregion
}
