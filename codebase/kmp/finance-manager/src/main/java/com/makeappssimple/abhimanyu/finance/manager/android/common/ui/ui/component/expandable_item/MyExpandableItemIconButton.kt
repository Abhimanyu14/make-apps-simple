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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.expandable_item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme

@Composable
internal fun MyExpandableItemIconButton(
    modifier: Modifier = Modifier,
    data: MyExpandableItemIconButtonData,
    handleEvent: (event: MyExpandableItemIconButtonEvent) -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(
                shape = CircleShape,
            )
            .conditionalClickable(
                onClick = if (data.isEnabled && data.isClickable) {
                    {
                        handleEvent(MyExpandableItemIconButtonEvent.OnClick)
                    }
                } else {
                    null
                }
            ),
    ) {
        CosmosIcon(
            iconResource = data.iconResource,
            tint = if (data.isEnabled) {
                CosmosAppTheme.colorScheme.onSurfaceVariant
            } else {
                CosmosAppTheme.colorScheme.surfaceVariant
            },
        )
        CosmosText(
            stringResource = data.labelStringResource,
            style = CosmosAppTheme.typography.labelMedium
                .copy(
                    color = if (data.isEnabled) {
                        CosmosAppTheme.colorScheme.onSurfaceVariant
                    } else {
                        CosmosAppTheme.colorScheme.surfaceVariant
                    },
                ),
        )
    }
}
