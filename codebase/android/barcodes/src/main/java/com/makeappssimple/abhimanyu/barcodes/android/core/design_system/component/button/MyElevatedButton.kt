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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.text.MyText
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.extensions.shimmer
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.resource.StringResource
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.theme.BarcodesAppTheme

private object MyElevatedButtonConstants {
    val defaultHeight = 40.dp
    val defaultWidth = 128.dp
    val contentMinimumWidth = 80.dp
}

@Composable
internal fun MyElevatedButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    stringResource: StringResource,
    onClick: () -> Unit = {},
) {
    if (isLoading) {
        MyElevatedButtonLoadingUI(
            modifier = modifier,
        )
    } else {
        ElevatedButton(
            onClick = onClick,
            enabled = isEnabled,
            colors = ButtonDefaults
                .buttonColors(
                    containerColor = BarcodesAppTheme.colorScheme.primary,
                    contentColor = BarcodesAppTheme.colorScheme.onPrimary,
                ),
            modifier = modifier,
        ) {
            MyText(
                modifier = Modifier
                    .defaultMinSize(
                        minWidth = MyElevatedButtonConstants.contentMinimumWidth,
                    ),
                stringResource = stringResource,
                style = BarcodesAppTheme.typography.labelLarge
                    .copy(
                        textAlign = TextAlign.Center,
                    ),
            )
        }
    }
}

@Composable
private fun MyElevatedButtonLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(
                height = MyElevatedButtonConstants.defaultHeight,
                width = MyElevatedButtonConstants.defaultWidth,
            )
            .clip(
                shape = CircleShape,
            )
            .shimmer(),
    )
}
