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

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.grid

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.griditem.CategoriesGridItem
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.griditem.CategoriesGridItemData
import kotlinx.collections.immutable.ImmutableList

@Composable
public fun CategoriesGrid(
    modifier: Modifier = Modifier,
    bottomPadding: Dp = 0.dp,
    topPadding: Dp = 0.dp,
    categoriesGridItemDataList: ImmutableList<CategoriesGridItemData>,
    onItemClick: ((index: Int) -> Unit)? = null,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(
            minSize = 100.dp,
        ),
        contentPadding = PaddingValues(
            top = topPadding,
            bottom = bottomPadding,
        ),
        modifier = modifier,
    ) {
        itemsIndexed(
            items = categoriesGridItemDataList,
            key = { _, listItem ->
                listItem.category.id
            },
        ) { index, listItem ->
            CategoriesGridItem(
                isSelected = listItem.isSelected,
                category = listItem.category,
                onClick = {
                    onItemClick?.invoke(index)
                },
            )
        }
    }
}
