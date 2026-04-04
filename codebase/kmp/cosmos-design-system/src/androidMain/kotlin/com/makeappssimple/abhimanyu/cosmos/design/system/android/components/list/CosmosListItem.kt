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

package com.makeappssimple.abhimanyu.cosmos.design.system.android.components.list

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
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosIconResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme

@Immutable
public data class CosmosListItemDataEventDataAndEventHandler(
    val data: CosmosListItemData,
    val handleEvent: (event: CosmosListItemDataEvent) -> Unit = {},
)

@Immutable
public data class CosmosListItemData(
    val stringResource: CosmosStringResource,
    val iconResource: CosmosIconResource? = null,
    val isSelectionMode: Boolean = false,
    val isSelected: Boolean = false,
)

@Immutable
public sealed class CosmosListItemDataEvent {
    public data object OnClick : CosmosListItemDataEvent()
    public data object OnLongClick : CosmosListItemDataEvent()
    public data object OnToggleSelection : CosmosListItemDataEvent()
}

@Composable
public fun CosmosListItem(
    modifier: Modifier = Modifier,
    data: CosmosListItemData,
    handleEvent: (event: CosmosListItemDataEvent) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                role = Role.Button,
                onClick = {
                    if (data.isSelectionMode) {
                        handleEvent(CosmosListItemDataEvent.OnToggleSelection)
                    } else {
                        handleEvent(CosmosListItemDataEvent.OnClick)
                    }
                },
                onLongClick = {
                    handleEvent(CosmosListItemDataEvent.OnLongClick)
                },
            ),
    ) {
        ListItem(
            headlineContent = {
                CosmosText(
                    stringResource = data.stringResource,
                    style = CosmosAppTheme.typography.bodyMedium,
                )
            },
            leadingContent = if (data.isSelectionMode) {
                {
                    Checkbox(
                        checked = data.isSelected,
                        onCheckedChange = {
                            handleEvent(CosmosListItemDataEvent.OnToggleSelection)
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
                        CosmosIcon(
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
            color = CosmosAppTheme.colorScheme.outline,
        )
    }
}
