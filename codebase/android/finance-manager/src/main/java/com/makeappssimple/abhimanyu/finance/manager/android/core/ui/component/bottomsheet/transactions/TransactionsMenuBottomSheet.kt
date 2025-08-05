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

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.finance.manager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.collections.immutable.persistentListOf

@Composable
public fun TransactionsMenuBottomSheet(
    modifier: Modifier = Modifier,
    handleEvent: (event: TransactionsMenuBottomSheetEvent) -> Unit = {},
) {
    val items = persistentListOf(
        TransactionsMenuBottomSheetItemData(
            imageVector = MyIcons.Edit,
            text = stringResource(
                id = R.string.finance_manager_bottom_sheet_transactions_menu_update_transaction_for,
            ),
            onClick = {
                handleEvent(TransactionsMenuBottomSheetEvent.OnUpdateTransactionForClick)
            },
        ),
        TransactionsMenuBottomSheetItemData(
            imageVector = MyIcons.Checklist,
            text = stringResource(
                id = R.string.finance_manager_bottom_sheet_transactions_menu_select_all_transactions,
            ),
            onClick = {
                handleEvent(TransactionsMenuBottomSheetEvent.OnSelectAllTransactionsClick)
            },
        )
    )

    TransactionsMenuBottomSheetUI(
        modifier = modifier,
        items = items,
    )
}
