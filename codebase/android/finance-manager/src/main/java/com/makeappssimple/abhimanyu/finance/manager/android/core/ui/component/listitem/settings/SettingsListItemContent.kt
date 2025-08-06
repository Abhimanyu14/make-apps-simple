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

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.listitem.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.common.core.extensions.orFalse
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.icons.MyIcons

@Composable
public fun SettingsListItemContent(
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
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
        headlineContent = {
            MyText(
                modifier = Modifier
                    .fillMaxWidth(),
                textStringResourceId = data.textStringResourceId,
                style = MaterialTheme.typography.bodyLarge
                    .copy(
                        color = MaterialTheme.colorScheme.onBackground,
                    ),
            )
        },
        trailingContent = if (data.hasToggle) {
            {
                // TODO(Abhi): Create a wrapper for this M3 component
                Switch(
                    checked = data.isChecked.orFalse(),
                    onCheckedChange = {
                        handleEvent(
                            SettingsListItemContentEvent.OnCheckedChange
                        )
                    },
                    thumbContent = if (data.isChecked.orFalse()) {
                        {
                            Icon(
                                imageVector = MyIcons.Check,
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                            )
                        }
                    } else {
                        null
                    },
                    colors = SwitchDefaults.colors(
                        uncheckedThumbColor = MaterialTheme.colorScheme.background,
                    ),
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
