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

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.transactionfor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import com.makeappssimple.abhimanyu.finance.manager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.core.designsystem.component.button.MyIconButton
import com.makeappssimple.abhimanyu.finance.manager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.finance.manager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.util.minimumListItemHeight

@Composable
public fun TransactionForListItem(
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
        MyText(
            text = data.title,
            style = MaterialTheme.typography.bodyLarge
                .copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
        )
        if (data.isMoreOptionsIconButtonVisible) {
            MyIconButton(
                tint = MaterialTheme.colorScheme.onBackground,
                imageVector = MyIcons.MoreVert,
                contentDescriptionStringResourceId = R.string.transaction_for_list_item_more_options_content_description,
                onClick = {
                    handleEvent(TransactionForListItemEvent.OnMoreOptionsIconButtonClick)
                },
            )
        }
    }
}
