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

package com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.icon

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.resource.IconResource
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.resource.StringResource
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.resource.text

@Composable
internal fun MyIcon(
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    iconResource: IconResource,
    contentDescriptionStringResource: StringResource? = null,
) {
    when (iconResource) {
        is IconResource.Id -> {
            Icon(
                modifier = modifier,
                tint = tint,
                painter = painterResource(
                    id = iconResource.id,
                ),
                contentDescription = contentDescriptionStringResource?.text,
            )
        }

        is IconResource.ImageVector -> {
            // TODO(Abhi): Add later
        }
    }
}
