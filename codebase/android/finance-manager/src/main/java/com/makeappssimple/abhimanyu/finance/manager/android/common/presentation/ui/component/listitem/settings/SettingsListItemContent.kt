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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.common.core.extensions.orFalse
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.toggle.Toggle
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.theme.FinanceManagerAppTheme

@Composable
internal fun SettingsListItemContent(
    modifier: Modifier = Modifier,
    data: SettingsListItemContentData,
    handleEvent: (event: SettingsListItemContentEvent) -> Unit = {},
) {
    ListItem(
        leadingContent = data.imageVector?.let {
            {
                Icon(
                    imageVector = data.imageVector,
                    contentDescription = null,
                    tint = FinanceManagerAppTheme.colorScheme.onBackground,
                )
            }
        },
        headlineContent = {
            MyText(
                modifier = Modifier
                    .fillMaxWidth(),
                textStringResourceId = data.textStringResourceId,
                style = FinanceManagerAppTheme.typography.bodyLarge
                    .copy(
                        color = FinanceManagerAppTheme.colorScheme.onBackground,
                    ),
            )
        },
        trailingContent = if (data.hasToggle) {
            {
                Toggle(
                    isChecked = data.isChecked.orFalse(),
                    onCheckedChange = {
                        handleEvent(SettingsListItemContentEvent.OnCheckedChange)
                    },
                )
            }
        } else {
            null
        },
        modifier = modifier
            .conditionalClickable(
                onClick = if (data.isEnabled) {
                    {
                        handleEvent(SettingsListItemContentEvent.OnClick)
                    }
                } else {
                    null
                },
            ),
    )
}
