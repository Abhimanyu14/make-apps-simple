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

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.chip.ChipUI
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.chip.ChipUIEvent

@Composable
public fun MySelectionGroup(
    modifier: Modifier = Modifier,
    data: MySelectionGroupData,
    handleEvent: (event: MySelectionGroupEvent) -> Unit = {},
) {
    FlowRow(
        modifier = modifier,
    ) {
        if (data.isLoading) {
            SelectionGroupLoadingUI(
                size = data.loadingItemSize,
            )
        } else {
            data.items.mapIndexed { index, chipUIData ->
                ChipUI(
                    data = chipUIData
                        .copy(
                            isMultiSelect = true,
                            isSelected = data.selectedItemsIndices.contains(
                                index
                            ),
                        ),
                    handleEvent = { event ->
                        when (event) {
                            is ChipUIEvent.OnClick -> {
                                handleEvent(
                                    MySelectionGroupEvent.OnSelectionChange(
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
