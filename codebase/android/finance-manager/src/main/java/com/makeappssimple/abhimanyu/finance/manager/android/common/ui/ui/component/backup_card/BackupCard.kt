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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.backup_card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.extensions.shimmer.shimmer
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Composable
internal fun BackupCard(
    modifier: Modifier = Modifier,
    data: BackupCardData,
    handleEvent: (event: BackupCardEvent) -> Unit = {},
) {
    if (data.isLoading) {
        BackupCardLoadingUI(
            modifier = modifier,
        )
    } else {
        BackupCardUI(
            modifier = modifier,
            handleEvent = handleEvent,
        )
    }
}

@Composable
private fun BackupCardUI(
    modifier: Modifier = Modifier,
    handleEvent: (event: BackupCardEvent) -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 32.dp,
                vertical = 4.dp,
            )
            .clip(
                shape = FinanceManagerAppTheme.shapes.medium,
            )
            .conditionalClickable(
                onClick = {
                    handleEvent(BackupCardEvent.OnClick)
                },
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = FinanceManagerAppTheme.colorScheme.primaryContainer,
                )
                .padding(
                    all = 16.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = MyIcons.Backup,
                contentDescription = null,
                tint = FinanceManagerAppTheme.colorScheme.onBackground,
            )
            MyText(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                    )
                    .fillMaxWidth(),
                textStringResourceId = R.string.finance_manager_backup_card,
                style = FinanceManagerAppTheme.typography.bodyLarge
                    .copy(
                        color = FinanceManagerAppTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                    ),
            )
        }
    }
}

@Composable
private fun BackupCardLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(
                size = DpSize(
                    width = 315.dp,
                    height = 223.dp,
                ),
            )
            .clip(
                shape = FinanceManagerAppTheme.shapes.medium,
            )
            .shimmer(),
    )
}
