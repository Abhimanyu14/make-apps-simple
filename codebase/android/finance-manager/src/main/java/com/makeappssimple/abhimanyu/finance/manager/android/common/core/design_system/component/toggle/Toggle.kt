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

package com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.component.toggle

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.design_system.theme.FinanceManagerAppTheme

@Composable
internal fun Toggle(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
) {
    Switch(
        checked = isChecked,
        onCheckedChange = onCheckedChange,
        thumbContent = if (isChecked) {
            {
                Icon(
                    imageVector = MyIcons.Check,
                    contentDescription = null,
                    modifier = Modifier
                        .size(
                            size = SwitchDefaults.IconSize,
                        ),
                )
            }
        } else {
            null
        },
        colors = SwitchDefaults.colors(
            uncheckedThumbColor = FinanceManagerAppTheme.colorScheme.background,
        ),
    )
}
