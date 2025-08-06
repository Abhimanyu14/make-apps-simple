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

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.bottom_sheet.account

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.orEmpty
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.accounts.AccountsListItemContentData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.accounts.AccountsListItemContentDataAndEventHandler
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.accounts.AccountsListItemContentEvent
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Composable
public fun SelectAccountBottomSheet(
    modifier: Modifier = Modifier,
    data: SelectAccountBottomSheetData,
    handleEvent: (event: SelectAccountBottomSheetEvent) -> Unit = {},
) {
    SelectAccountBottomSheetUI(
        modifier = modifier,
        data = SelectAccountListItemBottomSheetUIData(
            titleTextStringResourceId = R.string.finance_manager_bottom_sheet_select_account_title,
            data = data.accounts
                .map { account ->
                    AccountsListItemContentDataAndEventHandler(
                        data = AccountsListItemContentData(
                            isLowBalance = account.balanceAmount < account.minimumAccountBalanceAmount.orEmpty(),
                            isSelected = account.id == data.selectedAccountId,
                            icon = account.type.icon,
                            accountId = account.id,
                            balance = account.balanceAmount.toString(),
                            name = account.name,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is AccountsListItemContentEvent.OnClick -> {
                                    handleEvent(
                                        SelectAccountBottomSheetEvent.UpdateAccount(
                                            updatedAccount = account,
                                        )
                                    )
                                    handleEvent(SelectAccountBottomSheetEvent.ResetBottomSheetType)
                                }
                            }
                        },
                    )
                }
                .toList(),
        ),
    )
}
