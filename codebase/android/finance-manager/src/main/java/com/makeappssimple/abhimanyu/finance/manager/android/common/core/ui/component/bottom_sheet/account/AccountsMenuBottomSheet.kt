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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.ui.component.bottom_sheet.account

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.collections.immutable.toImmutableList

@Composable
public fun AccountsMenuBottomSheet(
    isDeleteVisible: Boolean,
    isEditVisible: Boolean,
    isSetAsDefaultVisible: Boolean,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    onSetAsDefaultClick: () -> Unit,
) {
    val items = mutableListOf<AccountsMenuBottomSheetItemData>()
    if (isEditVisible) {
        items.add(
            AccountsMenuBottomSheetItemData(
                imageVector = MyIcons.Edit,
                text = stringResource(
                    id = R.string.finance_manager_bottom_sheet_accounts_menu_edit,
                ),
                onClick = onEditClick,
            )
        )
    }
    if (isSetAsDefaultVisible) {
        items.add(
            AccountsMenuBottomSheetItemData(
                imageVector = MyIcons.CheckCircle,
                text = stringResource(
                    id = R.string.finance_manager_bottom_sheet_accounts_menu_set_as_default_account,
                ),
                onClick = onSetAsDefaultClick,
            )
        )
    }
    if (isDeleteVisible) {
        items.add(
            AccountsMenuBottomSheetItemData(
                imageVector = MyIcons.Delete,
                text = stringResource(
                    id = R.string.finance_manager_bottom_sheet_accounts_menu_delete,
                ),
                onClick = onDeleteClick,
            )
        )
    }

    AccountsMenuBottomSheetUI(
        items = items.toImmutableList(),
    )
}
