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

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.save_button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.finance.manager.android.core.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.core.ui.extensions.shimmer.shimmer

@Composable
public fun SaveButton(
    modifier: Modifier = Modifier,
    data: SaveButtonData,
    handleEvent: (event: SaveButtonEvent) -> Unit = {},
) {
    if (data.isLoading) {
        SaveButtonLoadingUI(
            modifier = modifier,
        )
    } else {
        SaveButtonUI(
            modifier = modifier,
            data = data,
            handleEvent = handleEvent,
        )
    }
}

@Composable
private fun SaveButtonUI(
    modifier: Modifier = Modifier,
    data: SaveButtonData,
    handleEvent: (event: SaveButtonEvent) -> Unit = {},
) {
    ElevatedButton(
        modifier = modifier,
        onClick = {
            handleEvent(SaveButtonEvent.OnClick)
        },
        enabled = data.isEnabled,
        colors = ButtonDefaults
            .buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
    ) {
        MyText(
            modifier = Modifier
                .defaultMinSize(
                    minWidth = 80.dp,
                ),
            textStringResourceId = data.textStringResourceId,
            style = MaterialTheme.typography.labelLarge
                .copy(
                    textAlign = TextAlign.Center,
                ),
        )
    }
}

@Composable
private fun SaveButtonLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(
                height = 40.dp,
                width = 128.dp,
            )
            .clip(
                shape = CircleShape,
            )
            .shimmer(),
    )
}
