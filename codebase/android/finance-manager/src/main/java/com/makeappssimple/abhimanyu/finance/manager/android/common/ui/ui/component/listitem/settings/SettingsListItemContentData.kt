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

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
internal data class SettingsListItemContentData(
    override val type: SettingsListItemType = SettingsListItemType.CONTENT,
    val isChecked: Boolean? = null,
    val isEnabled: Boolean = false,
    val hasToggle: Boolean = false,
    val imageVector: ImageVector? = null,
    @StringRes val textStringResourceId: Int,
) : SettingsListItemData
