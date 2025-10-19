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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.bottom_sheet.account

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.VerticalSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.bottom_sheet.common.MyBottomSheetTitle
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.bottom_sheet.common.MyBottomSheetTitleData
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.accounts.AccountsListItemContent
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.util.minimumBottomSheetHeight

@Composable
internal fun SelectAccountBottomSheetUI(
    modifier: Modifier = Modifier,
    data: SelectAccountListItemBottomSheetUIData,
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
                ),
            )
        }
        items(
            items = data.data,
            key = { listItem ->
                listItem.hashCode()
            },
        ) { listItem ->
            AccountsListItemContent(
                data = listItem.data,
                handleEvent = listItem.handleEvent,
            )
        }
        item {
            NavigationBarsAndImeSpacer()
        }
        item {
            VerticalSpacer(
                height = 16.dp,
            )
        }
    }
}
