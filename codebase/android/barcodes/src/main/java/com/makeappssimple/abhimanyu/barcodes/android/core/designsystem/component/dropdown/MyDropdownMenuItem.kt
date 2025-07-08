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

package com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.dropdown

import androidx.annotation.StringRes
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.icon.MyIcon
import com.makeappssimple.abhimanyu.barcodes.android.core.designsystem.component.text.MyText

@Composable
internal fun MyDropdownMenuItem(
    modifier: Modifier = Modifier,
    @StringRes leadingIconContentDescriptionStringResourceId: Int,
    @StringRes textStringResourceId: Int,
    onClick: () -> Unit,
    leadingIconImageVector: ImageVector,
) {
    DropdownMenuItem(
        text = {
            MyText(
                textStringResourceId = textStringResourceId,
            )
        },
        onClick = onClick,
        leadingIcon = {
            MyIcon(
                imageVector = leadingIconImageVector,
                contentDescriptionStringResourceId = leadingIconContentDescriptionStringResourceId,
            )
        },
        modifier = modifier,
    )
}
