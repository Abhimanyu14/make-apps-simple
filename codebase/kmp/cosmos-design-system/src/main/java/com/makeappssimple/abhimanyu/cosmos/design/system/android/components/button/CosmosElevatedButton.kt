/*
 * Copyright 2025-2026 Abhimanyu
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

package com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button

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
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.extensions.cosmosShimmer
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme

private object CosmosElevatedButtonConstants {
    val defaultHeight = 40.dp
    val defaultWidth = 128.dp
    val contentMinimumWidth = 80.dp
}

@Composable
public fun CosmosElevatedButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    stringResource: CosmosStringResource,
    onClick: () -> Unit = {},
) {
    if (isLoading) {
        CosmosElevatedButtonLoadingUI(
            modifier = modifier,
        )
    } else {
        ElevatedButton(
            onClick = onClick,
            enabled = isEnabled,
            colors = ButtonDefaults
                .buttonColors(
                    containerColor = CosmosAppTheme.colorScheme.primary,
                    contentColor = CosmosAppTheme.colorScheme.onPrimary,
                ),
            modifier = modifier,
        ) {
            CosmosText(
                modifier = Modifier
                    .defaultMinSize(
                        minWidth = CosmosElevatedButtonConstants.contentMinimumWidth,
                    ),
                stringResource = stringResource,
                style = CosmosAppTheme.typography.labelLarge
                    .copy(
                        textAlign = TextAlign.Center,
                    ),
            )
        }
    }
}

@Composable
private fun CosmosElevatedButtonLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(
                height = CosmosElevatedButtonConstants.defaultHeight,
                width = CosmosElevatedButtonConstants.defaultWidth,
            )
            .clip(
                shape = CircleShape,
            )
            .cosmosShimmer(),
    )
}
