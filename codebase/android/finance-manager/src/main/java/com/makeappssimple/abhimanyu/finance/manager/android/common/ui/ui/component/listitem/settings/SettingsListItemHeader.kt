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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.listitem.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.theme.FinanceManagerAppTheme

@Composable
internal fun SettingsListItemHeader(
    modifier: Modifier = Modifier,
    data: SettingsListItemHeaderData,
) {
    MyText(
        modifier = modifier
            .padding(
                start = 16.dp,
                top = 8.dp,
                end = 16.dp,
            )
            .fillMaxWidth(),
        textStringResourceId = data.textStringResourceId,
        style = FinanceManagerAppTheme.typography.headlineMedium
            .copy(
                color = FinanceManagerAppTheme.colorScheme.onBackground,
            ),
    )
}
