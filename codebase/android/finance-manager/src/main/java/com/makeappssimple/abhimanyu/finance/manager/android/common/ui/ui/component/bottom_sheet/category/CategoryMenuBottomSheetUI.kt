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
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.common.MyBottomSheetList
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.common.MyBottomSheetListData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.common.MyBottomSheetListItemData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.common.MyBottomSheetListItemDataAndEventHandler
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.common.MyBottomSheetListItemEvent
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun CategoryMenuBottomSheetUI(
    modifier: Modifier = Modifier,
    items: ImmutableList<CategoryMenuBottomSheetItemData>,
) {
    MyBottomSheetList(
        modifier = modifier,
        data = MyBottomSheetListData(
            items = items.map { itemData ->
                MyBottomSheetListItemDataAndEventHandler(
                    data = MyBottomSheetListItemData(
                        imageVector = itemData.imageVector,
                        text = itemData.text,
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is MyBottomSheetListItemEvent.OnClick -> {
                                itemData.onClick()
                            }
                        }
                    },
                )
            },
        ),
    )
}
