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

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.selection_group

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.chip.ChipUI
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.chip.ChipUIEvent
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.extensions.fading_edge.fadingEdge

@Composable
public fun MyHorizontalScrollingSelectionGroup(
    modifier: Modifier = Modifier,
    data: MyHorizontalScrollingSelectionGroupData,
    handleEvent: (event: MyHorizontalScrollingSelectionGroupEvent) -> Unit = {},
) {
    LazyRow(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fadingEdge(),
    ) {
        if (data.isLoading) {
            items(
                count = data.loadingItemSize,
                key = { listItem ->
                    listItem.hashCode()
                },
            ) {
                ChipUI(
                    data = ChipUIData(
                        isLoading = true,
                        isSelected = false,
                    ),
                )
            }
        } else {
            itemsIndexed(
                items = data.items,
                key = { _, listItem ->
                    listItem.hashCode()
                },
            ) { index, data ->
                ChipUI(
                    data = data
                        .copy(
                            isSelected = false,
                        ),
                    handleEvent = { event ->
                        when (event) {
                            is ChipUIEvent.OnClick -> {
                                handleEvent(
                                    MyHorizontalScrollingSelectionGroupEvent.OnSelectionChange(
                                        index = index,
                                    )
                                )
                            }
                        }
                    },
                )
            }
        }
    }
}
