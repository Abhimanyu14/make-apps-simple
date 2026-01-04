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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.chip

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.cosmos.design.system.android.extensions.cosmosShimmer
import com.makeappssimple.abhimanyu.cosmos.design.system.android.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosIconResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme

private object ChipUIConstants {
    val borderWidth = 1.dp
    val leadingIconPaddingBottom = 2.dp
    val leadingIconPaddingEnd = 2.dp
    val leadingIconPaddingStart = 12.dp
    val leadingIconPaddingTop = 2.dp
    const val leadingIconScale = 0.75F
    val loadingUIHeight = 30.dp
    val loadingUIPadding = 4.dp
    val loadingUIWidth = 80.dp
    val padding = 4.dp
    val shape = CircleShape
    val textPaddingBottom = 6.dp
    val textPaddingEnd = 16.dp
    val textPaddingStart = 16.dp
    val textPaddingStartWithIcon = 0.dp
    val textPaddingTop = 6.dp
}

@Composable
internal fun ChipUI(
    modifier: Modifier = Modifier,
    data: ChipUIData,
    handleEvent: (event: ChipUIEvent) -> Unit = {},
) {
    if (data.isLoading) {
        ChipLoadingUI(
            modifier = modifier,
        )
    } else {
        ChipUIContainer(
            modifier = modifier,
            data = data,
            handleEvent = handleEvent,
        ) {
            val iconResource: CosmosIconResource? =
                if (data.isMultiSelect && data.isSelected) {
                    CosmosIcons.Check
                } else {
                    data.iconResource
                }
            ChipUIIcon(
                iconResource = iconResource,
                isSelected = data.isSelected,
            )
            ChipUIText(
                iconResource = iconResource,
                data = data,
            )
        }
    }
}

@Composable
internal fun ChipLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(
                all = ChipUIConstants.loadingUIPadding,
            )
            .size(
                height = ChipUIConstants.loadingUIHeight,
                width = ChipUIConstants.loadingUIWidth,
            )
            .clip(
                shape = ChipUIConstants.shape,
            )
            .cosmosShimmer(),
    )
}

@Composable
private fun ChipUIContainer(
    modifier: Modifier = Modifier,
    data: ChipUIData,
    handleEvent: (event: ChipUIEvent) -> Unit = {},
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(
                all = ChipUIConstants.padding,
            )
            .clip(
                shape = ChipUIConstants.shape,
            )
            .border(
                width = ChipUIConstants.borderWidth,
                color = if (data.borderColor.isNotNull()) {
                    data.borderColor
                } else if (data.isSelected) {
                    CosmosAppTheme.colorScheme.primary
                } else {
                    CosmosAppTheme.colorScheme.outline
                },
                shape = ChipUIConstants.shape,
            )
            .conditionalClickable(
                onClick = {
                    handleEvent(ChipUIEvent.OnClick)
                },
            )
            .background(
                if (data.isSelected) {
                    CosmosAppTheme.colorScheme.primary
                } else {
                    Color.Transparent
                }
            )
            .animateContentSize(),
        content = content,
    )
}

@Composable
private fun ChipUIIcon(
    iconResource: CosmosIconResource?,
    isSelected: Boolean,
) {
    AnimatedVisibility(
        visible = iconResource.isNotNull(),
        enter = scaleIn(),
        exit = scaleOut(),
    ) {
        iconResource?.let {
            CosmosIcon(
                iconResource = iconResource,
                tint = if (isSelected) {
                    CosmosAppTheme.colorScheme.onPrimary
                } else {
                    CosmosAppTheme.colorScheme.primary
                },
                modifier = Modifier
                    .scale(
                        scale = ChipUIConstants.leadingIconScale,
                    )
                    .padding(
                        start = ChipUIConstants.leadingIconPaddingStart,
                        end = ChipUIConstants.leadingIconPaddingEnd,
                        top = ChipUIConstants.leadingIconPaddingTop,
                        bottom = ChipUIConstants.leadingIconPaddingBottom,
                    ),
            )
        }
    }
}

@Composable
private fun ChipUIText(
    iconResource: CosmosIconResource?,
    data: ChipUIData,
) {
    CosmosText(
        modifier = Modifier
            .padding(
                top = ChipUIConstants.textPaddingTop,
                bottom = ChipUIConstants.textPaddingBottom,
                end = ChipUIConstants.textPaddingEnd,
                start = if (iconResource.isNotNull()) {
                    ChipUIConstants.textPaddingStartWithIcon
                } else {
                    ChipUIConstants.textPaddingStart
                },
            ),
        stringResource = data.stringResource,
        style = CosmosAppTheme.typography.labelMedium
            .copy(
                color = if (data.textColor.isNotNull()) {
                    data.textColor
                } else if (data.isSelected) {
                    CosmosAppTheme.colorScheme.onPrimary
                } else {
                    CosmosAppTheme.colorScheme.onBackground
                },
            ),
    )
}
