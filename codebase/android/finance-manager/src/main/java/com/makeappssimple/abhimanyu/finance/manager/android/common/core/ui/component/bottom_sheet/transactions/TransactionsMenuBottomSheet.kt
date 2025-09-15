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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.bottom_sheet.transactions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.collections.immutable.toImmutableList

@Immutable
public data class TransactionsMenuBottomSheetData(
    val isDuplicateTransactionMenuOptionVisible: Boolean = false,
)

@Composable
public fun TransactionsMenuBottomSheet(
    modifier: Modifier = Modifier,
    data: TransactionsMenuBottomSheetData,
    handleEvent: (event: TransactionsMenuBottomSheetEvent) -> Unit = {},
) {
    val menuItems = mutableListOf<TransactionsMenuBottomSheetItemData>()
    if (data.isDuplicateTransactionMenuOptionVisible) {
        menuItems.add(
            element = TransactionsMenuBottomSheetItemData(
                imageVector = MyIcons.Copy,
                text = stringResource(
                    id = R.string.finance_manager_bottom_sheet_transactions_menu_duplicate_transaction,
                ),
                onClick = {
                    handleEvent(TransactionsMenuBottomSheetEvent.OnDuplicateTransactionClick)
                },
            ),
        )
    }
    menuItems.add(
        element = TransactionsMenuBottomSheetItemData(
            imageVector = MyIcons.Edit,
            text = stringResource(
                id = R.string.finance_manager_bottom_sheet_transactions_menu_update_transaction_for,
            ),
            onClick = {
                handleEvent(TransactionsMenuBottomSheetEvent.OnUpdateTransactionForClick)
            },
        ),
    )
    menuItems.add(
        element = TransactionsMenuBottomSheetItemData(
            imageVector = MyIcons.Checklist,
            text = stringResource(
                id = R.string.finance_manager_bottom_sheet_transactions_menu_select_all_transactions,
            ),
            onClick = {
                handleEvent(TransactionsMenuBottomSheetEvent.OnSelectAllTransactionsClick)
            },
        ),
    )

    TransactionsMenuBottomSheetUI(
        modifier = modifier,
        items = menuItems.toImmutableList(),
    )
}
