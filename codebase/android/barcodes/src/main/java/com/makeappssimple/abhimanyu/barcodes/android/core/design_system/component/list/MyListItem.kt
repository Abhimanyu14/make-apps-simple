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

package com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.list

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.icon.MyIcon
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.text.MyText
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.resource.IconResource
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.resource.StringResource
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.theme.BarcodesAppTheme

@Immutable
internal data class MyListItemDataEventDataAndEventHandler(
    val data: MyListItemData,
    val handleEvent: (event: MyListItemDataEvent) -> Unit = {},
)

@Immutable
internal data class MyListItemData(
    val stringResource: StringResource,
    val iconResource: IconResource? = null,
    val isSelectionMode: Boolean = false,
    val isSelected: Boolean = false,
)

@Immutable
internal sealed class MyListItemDataEvent {
    data object OnClick : MyListItemDataEvent()
    data object OnLongClick : MyListItemDataEvent()
    data object OnToggleSelection : MyListItemDataEvent()
}

@Composable
internal fun MyListItem(
    modifier: Modifier = Modifier,
    data: MyListItemData,
    handleEvent: (event: MyListItemDataEvent) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                role = Role.Button,
                onClick = {
                    if (data.isSelectionMode) {
                        handleEvent(MyListItemDataEvent.OnToggleSelection)
                    } else {
                        handleEvent(MyListItemDataEvent.OnClick)
                    }
                },
                onLongClick = {
                    handleEvent(MyListItemDataEvent.OnLongClick)
                },
            ),
    ) {
        ListItem(
            headlineContent = {
                MyText(
                    stringResource = data.stringResource,
                    style = BarcodesAppTheme.typography.bodyMedium,
                )
            },
            leadingContent = if (data.isSelectionMode) {
                {
                    Checkbox(
                        checked = data.isSelected,
                        onCheckedChange = {
                            handleEvent(MyListItemDataEvent.OnToggleSelection)
                        },
                        modifier = Modifier
                            .size(
                                size = 24.dp,
                            ),
                    )
                }
            } else {
                if (data.iconResource != null) {
                    {
                        MyIcon(
                            iconResource = data.iconResource,
                            modifier = Modifier
                                .size(
                                    size = 24.dp,
                                ),
                        )
                    }
                } else {
                    null
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
        )
        HorizontalDivider(
            color = BarcodesAppTheme.colorScheme.outline,
        )
    }
}
