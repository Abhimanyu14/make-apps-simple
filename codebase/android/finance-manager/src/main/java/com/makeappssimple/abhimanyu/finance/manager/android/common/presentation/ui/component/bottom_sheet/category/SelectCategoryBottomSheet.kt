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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.bottom_sheet.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.model.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
public data class SelectCategoryBottomSheetData(
    val filteredCategories: ImmutableList<Category> = persistentListOf(),
    val selectedCategoryId: Int? = null,
)

@Immutable
public sealed class SelectCategoryBottomSheetEvent {
    public data object ResetBottomSheetType : SelectCategoryBottomSheetEvent()
    public data class UpdateCategory(
        val updatedCategory: Category,
    ) : SelectCategoryBottomSheetEvent()
}

@Composable
public fun SelectCategoryBottomSheet(
    modifier: Modifier = Modifier,
    data: SelectCategoryBottomSheetData,
    handleEvent: (event: SelectCategoryBottomSheetEvent) -> Unit = {},
) {
    SelectCategoryBottomSheetUI(
        modifier = modifier,
        items = data.filteredCategories
            .map { category ->
                SelectCategoryBottomSheetItemData(
                    category = category,
                    isSelected = category.id == data.selectedCategoryId,
                    onClick = {
                        handleEvent(
                            SelectCategoryBottomSheetEvent.UpdateCategory(
                                updatedCategory = category,
                            )
                        )
                        handleEvent(SelectCategoryBottomSheetEvent.ResetBottomSheetType)
                    },
                )
            },
    )
}
