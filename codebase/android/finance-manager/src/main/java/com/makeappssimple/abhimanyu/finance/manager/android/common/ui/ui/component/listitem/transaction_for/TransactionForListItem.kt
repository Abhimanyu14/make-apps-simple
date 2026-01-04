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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.listitem.transaction_for

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button.CosmosIconButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.cosmos.design.system.android.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.util.minimumListItemHeight

import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Composable
internal fun TransactionForListItem(
    modifier: Modifier = Modifier,
    data: TransactionForListItemData,
    handleEvent: (event: TransactionForListItemEvent) -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(
                minHeight = minimumListItemHeight,
            )
            .conditionalClickable(
                onClick = {
                    handleEvent(TransactionForListItemEvent.OnClick)
                },
            )
            .padding(
                start = 16.dp,
                end = 8.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CosmosText(
            stringResource = CosmosStringResource.Text(
                text = data.title,
            ),
            style = CosmosAppTheme.typography.bodyLarge
                .copy(
                    color = CosmosAppTheme.colorScheme.onBackground,
                ),
        )
        if (data.isMoreOptionsIconButtonVisible) {
            CosmosIconButton(
                onClickLabelStringResource = CosmosStringResource.Id(
                    id = R.string.finance_manager_transaction_for_list_item_more_options_content_description,
                ),
                onClick = {
                    handleEvent(TransactionForListItemEvent.OnMoreOptionsIconButtonClick)
                },
            ) {
                CosmosIcon(
                    iconResource = CosmosIcons.MoreVert,
                    tint = CosmosAppTheme.colorScheme.onBackground,
                )
            }
        }
    }
}
