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

package com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.component.button

import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics

@Composable
public fun MyIconButton(
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    imageVector: ImageVector,
    @StringRes contentDescriptionStringResourceId: Int,
    onClick: () -> Unit,
) {
    MyIconButton(
        modifier = modifier,
        tint = tint,
        imageVector = imageVector,
        contentDescription = stringResource(
            id = contentDescriptionStringResourceId,
        ),
        onClick = onClick,
    )
}

/**
 * Avoid using this unless absolutely unavoidable.
 *
 * Recommended to use [MyIconButton] with [contentDescriptionStringResourceId] over this.
 */
@Composable
public fun MyIconButton(
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        content = {
            Icon(
                imageVector = imageVector,
                tint = tint,
                contentDescription = contentDescription,
            )
        },
        modifier = modifier
            .semantics {
                onClick(
                    label = contentDescription,
                    action = null,
                )
            },
    )
}
