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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.view_model

import com.makeappssimple.abhimanyu.finance.manager.android.core.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.DeleteAccountByIdUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.bottom_sheet.AccountsScreenBottomSheetType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class AccountsScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val deleteAccountByIdUseCase: DeleteAccountByIdUseCase,
    private val financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    private val navigationKit: NavigationKit,
    private val screenUICommonState: ScreenUICommonState,
) : AccountsScreenUIStateDelegate, ScreenUICommonState by screenUICommonState {
    // region UI state
    override var screenBottomSheetType: AccountsScreenBottomSheetType =
        AccountsScreenBottomSheetType.None
    override var clickedItemId: Int? = null
    // endregion

    // region state events
    override fun deleteAccount() {
        coroutineScope.launch {
            clickedItemId?.let { id ->
                val isAccountDeleted = deleteAccountByIdUseCase(
                    id = id,
                )
                if (isAccountDeleted) {
                    updateClickedItemId(
                        updatedClickedItemId = null,
                    )
                } else {
                    // TODO(Abhi): Handle this error scenario
                }
            } ?: run {
                // TODO(Abhi): Handle this error scenario
            }
        }
    }

    override fun navigateToAddAccountScreen() {
        navigationKit.navigateToAddAccountScreen()
    }

    override fun navigateToEditAccountScreen(
        accountId: Int,
    ) {
        navigationKit.navigateToEditAccountScreen(
            accountId = accountId,
        )
    }

    override fun navigateUp() {
        navigationKit.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedAccountsScreenBottomSheetType = AccountsScreenBottomSheetType.None,
        )
    }

    override fun updateClickedItemId(
        updatedClickedItemId: Int?,
        shouldRefresh: Boolean,
    ) {
        clickedItemId = updatedClickedItemId
        if (shouldRefresh) {
            refresh()
        }
    }

    override fun updateDefaultAccountIdInDataStore() {
        coroutineScope.launch {
            clickedItemId?.let { accountId ->
                val isDefaultAccountUpdated =
                    financeManagerPreferencesRepository.updateDefaultAccountId(
                        accountId = accountId,
                    )
                if (!isDefaultAccountUpdated) {
                    // TODO(Abhi): Use the result to show snackbar
                }
            }
        }
    }

    override fun updateScreenBottomSheetType(
        updatedAccountsScreenBottomSheetType: AccountsScreenBottomSheetType,
        shouldRefresh: Boolean,
    ) {
        screenBottomSheetType = updatedAccountsScreenBottomSheetType
        if (shouldRefresh) {
            refresh()
        }
    }
    // endregion
}
