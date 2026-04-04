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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.category

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.cosmos.design.system.android.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun CategoryMenuBottomSheet(
    data: CategoryMenuBottomSheetData,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    onSetAsDefaultClick: () -> Unit,
) {
    val items = mutableListOf<CategoryMenuBottomSheetItemData>()
    if (data.isEditVisible) {
        items.add(
            CategoryMenuBottomSheetItemData(
                iconResource = CosmosIcons.Edit,
                stringResource = CosmosStringResource.Id(
                    id = R.string.finance_manager_bottom_sheet_category_menu_edit,
                ),
                onClick = onEditClick,
            )
        )
    }
    if (data.isSetAsDefaultVisible) {
        items.add(
            CategoryMenuBottomSheetItemData(
                iconResource = CosmosIcons.CheckCircle,
                stringResource = CosmosStringResource.Id(
                    id = R.string.finance_manager_bottom_sheet_category_menu_set_as_default_category,
                ),
                onClick = onSetAsDefaultClick,
            )
        )
    }
    if (data.isDeleteVisible) {
        items.add(
            CategoryMenuBottomSheetItemData(
                iconResource = CosmosIcons.Delete,
                stringResource = CosmosStringResource.Id(
                    id = R.string.finance_manager_bottom_sheet_category_menu_delete,
                ),
                onClick = onDeleteClick,
            )
        )
    }

    CategoryMenuBottomSheetUI(
        items = items.toImmutableList(),
    )
}
