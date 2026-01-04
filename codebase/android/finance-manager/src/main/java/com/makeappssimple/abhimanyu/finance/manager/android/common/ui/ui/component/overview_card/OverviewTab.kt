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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.overview_card

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.theme.FinanceManagerAppTheme

private val overviewTabMinimumWidth = 64.dp
private val overviewTextHorizontalPadding = 12.dp
private val overviewTextVerticalPadding = 8.dp

@Composable
internal fun OverviewTab(
    modifier: Modifier = Modifier,
    data: OverviewTabData,
    handleEvent: (event: OverviewTabEvent) -> Unit = {},
) {
    val textStyle = FinanceManagerAppTheme.typography.labelLarge

    Box(
        modifier = modifier
            .clip(
                shape = CircleShape,
            )
            .background(
                color = FinanceManagerAppTheme.colorScheme.background,
            )
            .height(
                intrinsicSize = IntrinsicSize.Min,
            ),
    ) {
        OverviewTabIndicator(
            data = data,
            textStyle = textStyle,
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(
                    shape = CircleShape,
                ),
        ) {
            data.items.mapIndexed { index, text ->
                OverviewTabText(
                    text = text,
                    textStyle = textStyle,
                    isSelected = index == data.selectedItemIndex,
                    onClick = {
                        handleEvent(
                            OverviewTabEvent.OnClick(
                                index = index,
                            )
                        )
                    },
                )
            }
        }
    }
}

@Composable
internal fun OverviewTabText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val defaultTextColor = FinanceManagerAppTheme.colorScheme.onBackground
    val selectedTextColor = FinanceManagerAppTheme.colorScheme.onPrimary
    val targetValue = remember(isSelected) {
        if (isSelected) {
            selectedTextColor
        } else {
            defaultTextColor
        }
    }

    val tabTextColor by animateColorAsState(
        targetValue = targetValue,
        animationSpec = tween(
            easing = LinearEasing,
        ),
        label = "tab_text_color",
    )

    BasicText(
        modifier = modifier
            .widthIn(
                min = overviewTabMinimumWidth,
            )
            .clip(
                shape = CircleShape,
            )
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
                onClick = onClick,
            )
            .padding(
                horizontal = overviewTextHorizontalPadding,
                vertical = overviewTextVerticalPadding,
            )
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
            .drawWithContent {
                drawContent()

                drawRect(
                    color = tabTextColor,
                    blendMode = BlendMode.SrcIn
                )
            },
        text = text,
        color = { tabTextColor },
        style = textStyle
            .copy(
                textAlign = TextAlign.Center,
            ),
    )
}

@Composable
private fun OverviewTabIndicator(
    data: OverviewTabData,
    textStyle: TextStyle,
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val indicatorColor = FinanceManagerAppTheme.colorScheme.primary

    val tabWidths = remember(
        key1 = data.items,
    ) {
        data.items.map {
            with(
                receiver = density,
            ) {
                max(
                    a = overviewTabMinimumWidth,
                    b = textMeasurer.measure(
                        text = it,
                        style = textStyle,
                    ).size.width.toDp() + (overviewTextHorizontalPadding * 2)
                )
            }
        }
    }
    val indicatorOffsetTargetMap = remember(
        key1 = data.items,
    ) {
        var offsetSum = 0.dp
        val map = mutableMapOf<Int, Dp>()
        tabWidths.forEachIndexed { index, dp ->
            map[index] = offsetSum
            offsetSum += dp
        }
        map
    }
    val indicatorWidth: Dp by animateDpAsState(
        targetValue = tabWidths.getOrElse(
            index = data.selectedItemIndex,
        ) {
            64.dp
        },
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing,
        ),
        label = "tab_indicator_width",
    )
    val indicatorOffset: Dp by animateDpAsState(
        targetValue = indicatorOffsetTargetMap.getOrDefault(
            key = data.selectedItemIndex,
            defaultValue = overviewTabMinimumWidth,
        ),
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing,
        ),
        label = "tab_indicator_offset",
    )

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .drawBehind {
                drawRoundRect(
                    color = indicatorColor,
                    topLeft = Offset(
                        x = with(
                            receiver = density,
                        ) {
                            indicatorOffset.toPx()
                        },
                        y = 0F,
                    ),
                    size = size
                        .copy(
                            width = with(
                                receiver = density,
                            ) {
                                indicatorWidth.toPx()
                            },
                        ),
                    cornerRadius = CornerRadius(
                        x = size.height / 2,
                        y = size.height / 2,
                    ),
                )
            },
    )
}

@Preview
@Composable
private fun OverviewSelectionPreview() {
    var selectedIndex by remember {
        mutableIntStateOf(
            value = 0,
        )
    }
    FinanceManagerAppTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(
                    color = FinanceManagerAppTheme.colorScheme.onBackground,
                )
                .fillMaxSize(),
        ) {
            OverviewTab(
                data = OverviewTabData(
                    items = listOf(
                        "Day",
                        "Week",
                        "Month",
                        "Year",
                    ),
                    selectedItemIndex = selectedIndex,
                ),
                handleEvent = { event ->
                    when (event) {
                        is OverviewTabEvent.OnClick -> {
                            selectedIndex = event.index
                        }
                    }
                },
            )
        }
    }
}
