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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.transaction_for

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.spacer.CosmosNavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.spacer.CosmosVerticalSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.util.minimumBottomSheetHeight
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.common.MyBottomSheetTitle
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.common.MyBottomSheetTitleData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.listitem.transaction_for.TransactionForListItem

@Composable
internal fun SelectTransactionForBottomSheetUI(
    modifier: Modifier = Modifier,
    data: SelectTransactionForListItemBottomSheetUIData,
) {
    LazyColumn(
        modifier = modifier
            .defaultMinSize(
                minHeight = minimumBottomSheetHeight,
            ),
    ) {
        stickyHeader {
            MyBottomSheetTitle(
                data = MyBottomSheetTitleData(
                    textStringResourceId = data.titleTextStringResourceId,
                )
            )
        }
        items(
            items = data.data,
            key = { listItem ->
                listItem.hashCode()
            },
        ) { listItem ->
            TransactionForListItem(
                data = listItem.data,
                handleEvent = listItem.handleEvent,
            )
        }
        item {
            CosmosNavigationBarsAndImeSpacer()
        }
        item {
            CosmosVerticalSpacer(
                height = 16.dp,
            )
        }
    }
}
