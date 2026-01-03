/*
 * Copyright 2025-2026 Abhimanyu
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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.transaction_for

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun TransactionForValuesMenuBottomSheet(
    isDeleteVisible: Boolean,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    val items = mutableListOf<TransactionForValuesMenuBottomSheetItemData>()
    items.add(
        element = TransactionForValuesMenuBottomSheetItemData(
            imageVector = MyIcons.Edit,
            text = stringResource(
                id = R.string.finance_manager_bottom_sheet_transaction_for_values_menu_edit,
            ),
            onClick = onEditClick,
        ),
    )
    if (isDeleteVisible) {
        items.add(
            element = TransactionForValuesMenuBottomSheetItemData(
                imageVector = MyIcons.Delete,
                text = stringResource(
                    id = R.string.finance_manager_bottom_sheet_transaction_for_values_menu_delete,
                ),
                onClick = onDeleteClick,
            ),
        )
    }

    TransactionForValuesMenuBottomSheetUI(
        items = items.toImmutableList(),
    )
}
