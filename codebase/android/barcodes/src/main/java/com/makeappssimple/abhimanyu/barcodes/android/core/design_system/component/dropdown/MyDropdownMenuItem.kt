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

package com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.dropdown

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.icon.MyIcon
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.text.MyText
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.resource.IconResource
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.resource.StringResource

@Composable
internal fun MyDropdownMenuItem(
    modifier: Modifier = Modifier,
    leadingIconContentDescriptionStringResource: StringResource,
    stringResource: StringResource,
    onClick: () -> Unit,
    leadingIconResource: IconResource,
) {
    DropdownMenuItem(
        text = {
            MyText(
                stringResource = stringResource,
            )
        },
        onClick = onClick,
        leadingIcon = {
            MyIcon(
                iconResource = leadingIconResource,
                contentDescriptionStringResource = leadingIconContentDescriptionStringResource,
            )
        },
        modifier = modifier,
    )
}
