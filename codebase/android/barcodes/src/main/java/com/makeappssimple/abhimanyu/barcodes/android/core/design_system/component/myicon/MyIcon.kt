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

package com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.myicon

import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@Composable
internal fun MyIcon(
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    imageVector: ImageVector,
    @StringRes contentDescriptionStringResourceId: Int?,
) {
    MyIcon(
        modifier = modifier,
        tint = tint,
        imageVector = imageVector,
        contentDescription = contentDescriptionStringResourceId?.run {
            stringResource(
                id = this,
            )
        },
    )
}

@Composable
internal fun MyIcon(
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    imageVector: ImageVector,
    contentDescription: String? = null,
) {
    Icon(
        modifier = modifier,
        tint = tint,
        imageVector = imageVector,
        contentDescription = contentDescription,
    )
}

@Composable
internal fun MyIcon(
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    painter: Painter,
    contentDescription: String? = null,
) {
    Icon(
        modifier = modifier,
        tint = tint,
        painter = painter,
        contentDescription = contentDescription,
    )
}
