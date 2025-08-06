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

package com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.button

import androidx.annotation.StringRes
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.icon.MyIcon

@Composable
internal fun MyFloatingActionButton(
    modifier: Modifier = Modifier,
    iconImageVector: ImageVector,
    @StringRes contentDescriptionStringResourceId: Int,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.primary,
        onClick = onClick,
        modifier = modifier,
    ) {
        MyIcon(
            imageVector = iconImageVector,
            contentDescriptionStringResourceId = contentDescriptionStringResourceId,
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}
