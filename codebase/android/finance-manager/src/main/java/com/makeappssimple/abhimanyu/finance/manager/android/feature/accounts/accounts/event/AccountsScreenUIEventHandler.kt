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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.event

import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.bottom_sheet.AccountsScreenBottomSheetType
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.state.AccountsScreenUIStateEvents

internal class AccountsScreenUIEventHandler internal constructor(
    private val uiStateEvents: AccountsScreenUIStateEvents,
) : ScreenUIEventHandler<AccountsScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: AccountsScreenUIEvent,
    ) {
        when (uiEvent) {
            is AccountsScreenUIEvent.OnAccountsDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.updateClickedItemId(null)
            }

            is AccountsScreenUIEvent.OnAccountsDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.deleteAccount()
            }

            is AccountsScreenUIEvent.OnAccountsMenuBottomSheet.DeleteButtonClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    AccountsScreenBottomSheetType.DeleteConfirmation
                )
            }

            is AccountsScreenUIEvent.OnAccountsMenuBottomSheet.EditButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.navigateToEditAccountScreen(uiEvent.accountId)
            }

            is AccountsScreenUIEvent.OnAccountsMenuBottomSheet.SetAsDefaultButtonClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    AccountsScreenBottomSheetType.SetAsDefaultConfirmation
                )
            }

            is AccountsScreenUIEvent.OnAccountsSetAsDefaultConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.updateClickedItemId(null)
            }

            is AccountsScreenUIEvent.OnAccountsSetAsDefaultConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.updateDefaultAccountIdInDataStore()
            }

            is AccountsScreenUIEvent.OnAccountsListItemContent.Click -> {
                uiEvent.accountId?.let { accountId ->
                    uiStateEvents.updateScreenBottomSheetType(
                        AccountsScreenBottomSheetType.Menu(
                            isDeleteVisible = uiEvent.isDeleteEnabled,
                            isEditVisible = true,
                            isSetAsDefaultVisible = !uiEvent.isDefault,
                            accountId = accountId,
                        )
                    )
                }
                uiStateEvents.updateClickedItemId(uiEvent.accountId)
            }

            is AccountsScreenUIEvent.OnFloatingActionButtonClick -> {
                uiStateEvents.navigateToAddAccountScreen()
            }

            is AccountsScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is AccountsScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }
        }
    }
}
