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

package com.makeappssimple.abhimanyu.cosmos.design.system.android.components.action_button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.dot.Dot
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.extensions.cosmosShimmer
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme

private object ActionButtonConstants {
    val loadingUISize = 40.dp
    val loadingUIPadding = 4.dp
}

@Composable
public fun ActionButton(
    modifier: Modifier = Modifier,
    data: ActionButtonData,
    handleEvent: (event: ActionButtonEvent) -> Unit = {},
) {
    if (data.isLoading) {
        ActionButtonLoadingUI(
            modifier = modifier,
        )
    } else {
        ActionButtonUI(
            modifier = modifier,
            data = data,
            handleEvent = handleEvent,
        )
    }
}

@Composable
private fun ActionButtonUI(
    modifier: Modifier = Modifier,
    data: ActionButtonData,
    handleEvent: (event: ActionButtonEvent) -> Unit,
) {
    ElevatedCard(
        onClick = {
            handleEvent(ActionButtonEvent.OnClick)
        },
        modifier = modifier,
    ) {
        Box {
            CosmosIcon(
                iconResource = data.iconResource,
                tint = CosmosAppTheme.colorScheme.onPrimaryContainer,
                contentDescriptionStringResource = CosmosStringResource.Id(
                    id = data.contentDescriptionStringResourceId,
                ),
                modifier = Modifier
                    .background(
                        color = CosmosAppTheme.colorScheme.primaryContainer,
                    )
                    .padding(
                        all = 8.dp,
                    ),
            )
            if (data.isIndicatorVisible) {
                Dot(
                    modifier = Modifier
                        .align(
                            alignment = Alignment.TopEnd,
                        )
                        .padding(
                            all = 8.dp,
                        ),
                    color = CosmosAppTheme.colorScheme.error,
                )
            }
        }
    }
}

@Composable
private fun ActionButtonLoadingUI(
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier
            .padding(
                all = ActionButtonConstants.loadingUIPadding,
            ),
    ) {
        Box(
            modifier = modifier
                .size(
                    size = DpSize(
                        width = ActionButtonConstants.loadingUISize,
                        height = ActionButtonConstants.loadingUISize,
                    ),
                )
                .cosmosShimmer(),
        )
    }
}
