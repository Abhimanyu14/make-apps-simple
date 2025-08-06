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

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.bottom_sheet.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.component.VerticalSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.bottom_sheet.common.MyBottomSheetTitle
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.bottom_sheet.common.MyBottomSheetTitleData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.grid.CategoriesGrid
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.grid_item.CategoriesGridItemData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.minimumBottomSheetHeight
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun SelectCategoryBottomSheetUI(
    modifier: Modifier = Modifier,
    items: ImmutableList<SelectCategoryBottomSheetItemData>,
) {
    Column(
        modifier = modifier
            .defaultMinSize(
                minHeight = minimumBottomSheetHeight,
            ),
    ) {
        MyBottomSheetTitle(
            data = MyBottomSheetTitleData(
                textStringResourceId = R.string.finance_manager_bottom_sheet_select_category_title,
            )
        )
        CategoriesGrid(
            categoriesGridItemDataList = items.map {
                CategoriesGridItemData(
                    isSelected = it.isSelected,
                    category = it.category,
                )
            },
            onItemClick = {
                items[it].onClick()
            },
        )
        NavigationBarsAndImeSpacer()
        VerticalSpacer(
            height = 16.dp,
        )
    }
}
