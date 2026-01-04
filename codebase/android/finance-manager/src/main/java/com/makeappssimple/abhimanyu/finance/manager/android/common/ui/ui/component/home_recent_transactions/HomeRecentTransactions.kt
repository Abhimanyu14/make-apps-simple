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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.home_recent_transactions

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.text
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme

import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Composable
internal fun HomeRecentTransactions(
    modifier: Modifier = Modifier,
    data: HomeRecentTransactionsData,
    handleEvent: (event: HomeRecentTransactionsEvent) -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 16.dp,
                bottom = 8.dp,
            )
            .clip(
                shape = CircleShape,
            )
            .conditionalClickable(
                onClickLabel = CosmosStringResource.Id(
                    id = R.string.finance_manager_screen_home_view_all_transactions,
                ).text,
                onClick = {
                    handleEvent(HomeRecentTransactionsEvent.OnClick)
                },
            )
            .padding(
                top = 12.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 12.dp,
            ),
    ) {
        CosmosText(
            stringResource = CosmosStringResource.Id(
                id = R.string.finance_manager_screen_home_recent_transactions,
            ),
            style = CosmosAppTheme.typography.headlineLarge
                .copy(
                    color = CosmosAppTheme.colorScheme.onBackground,
                ),
            modifier = Modifier
                .weight(
                    weight = 1F,
                ),
        )
        if (data.isTrailingTextVisible) {
            CosmosText(
                stringResource = CosmosStringResource.Id(
                    id = R.string.finance_manager_screen_home_view_all_transactions,
                ),
                style = CosmosAppTheme.typography.headlineMedium
                    .copy(
                        color = CosmosAppTheme.colorScheme.primary,
                    ),
            )
        }
    }
}
