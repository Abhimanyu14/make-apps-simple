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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.util.minimumBottomSheetHeight
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.component.VerticalSpacer
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.common.MyBottomSheetTitle
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.bottom_sheet.common.MyBottomSheetTitleData
import com.makeappssimple.abhimanyu.library.finance.manager.android.R
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun TransactionsSortBottomSheetUI(
    modifier: Modifier = Modifier,
    data: ImmutableList<TransactionsSortBottomSheetData>,
) {
    Column(
        modifier = modifier
            .verticalScroll(
                state = rememberScrollState(),
            )
            .defaultMinSize(
                minHeight = minimumBottomSheetHeight,
            ),
    ) {
        MyBottomSheetTitle(
            data = MyBottomSheetTitleData(
                textStringResourceId = R.string.finance_manager_bottom_sheet_transactions_sort_title,
            )
        )
        data.forEach { listItem ->
            TransactionsSortBottomSheetItem(
                data = listItem.data,
                handleEvent = listItem.handleEvent,
            )
        }
        NavigationBarsAndImeSpacer()
        VerticalSpacer(
            height = 16.dp,
        )
    }
}

@Composable
private fun TransactionsSortBottomSheetItem(
    data: TransactionsSortBottomSheetItemData,
    handleEvent: (event: TransactionsSortBottomSheetItemEvent) -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .conditionalClickable(
                onClickLabel = data.transactionSortOption.title,
                role = Role.Button,
                onClick = {
                    handleEvent(TransactionsSortBottomSheetItemEvent.OnClick)
                },
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
    ) {
        CosmosText(
            modifier = Modifier
                .padding(
                    vertical = 6.dp,
                ),
            stringResource = CosmosStringResource.Text(
                text = data.transactionSortOption.title,
            ),
            style = FinanceManagerAppTheme.typography.headlineMedium
                .copy(
                    color = if (data.isSelected) {
                        FinanceManagerAppTheme.colorScheme.primary
                    } else {
                        FinanceManagerAppTheme.colorScheme.onBackground
                    },
                ),
        )
        Spacer(
            modifier = Modifier
                .weight(
                    weight = 1F,
                ),
        )
        if (data.isSelected) {
            CosmosIcon(
                iconResource = CosmosIcons.Check,
                tint = FinanceManagerAppTheme.colorScheme.primary,
            )
        }
    }
}
