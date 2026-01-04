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

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.common.core.extensions.isNull
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button.CosmosIconButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.spacer.CosmosVerticalSpacer
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.constants.TestTags.COMPONENT_OVERVIEW_CARD
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.chart.compose_pie.ComposePieChart
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.extensions.shimmer.shimmer
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Immutable
internal sealed class OverviewCardEvent {
    data object OnClick : OverviewCardEvent()

    internal data class OnOverviewTabClick(
        val index: Int,
    ) : OverviewCardEvent()

    internal data class OnOverviewCardAction(
        val overviewCardAction: OverviewCardAction,
    ) : OverviewCardEvent()
}

internal enum class OverviewTabOption(
    val title: String,
) {
    DAY(
        title = "DAY",
    ),

    // TODO(Abhi): Enable week later
    // WEEK("Week"),

    MONTH(
        title = "MONTH",
    ),
    YEAR(
        title = "YEAR",
    ),
}

internal enum class OverviewCardAction {
    NEXT,
    PREV,
}

internal data class OverviewCardViewModelData(
    val income: Float = 0F,
    val expense: Float = 0F,
    val title: String = "",
)

internal fun OverviewCardViewModelData?.orDefault(): OverviewCardViewModelData {
    return if (this.isNull()) {
        OverviewCardViewModelData()
    } else {
        this
    }
}

@Composable
internal fun OverviewCard(
    modifier: Modifier = Modifier,
    data: OverviewCardData,
    handleEvent: (event: OverviewCardEvent) -> Unit = {},
) {
    val modifierWithTestTag = modifier
        .testTag(
            tag = COMPONENT_OVERVIEW_CARD,
        )
    if (data.isLoading) {
        OverviewCardLoadingUI(
            modifier = modifierWithTestTag,
        )
    } else {
        OverviewCardUI(
            modifier = modifierWithTestTag,
            data = data,
            handleEvent = handleEvent,
        )
    }
}

@Composable
private fun OverviewCardUI(
    modifier: Modifier = Modifier,
    data: OverviewCardData,
    handleEvent: (event: OverviewCardEvent) -> Unit = {},
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 32.dp,
                vertical = 16.dp,
            )
            .clip(
                shape = FinanceManagerAppTheme.shapes.medium,
            )
            .conditionalClickable(
                onClick = {
                    handleEvent(OverviewCardEvent.OnClick)
                },
            ),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = FinanceManagerAppTheme.colorScheme.primaryContainer,
                )
                .padding(
                    all = 12.dp,
                )
                .animateContentSize(),
        ) {
            OverviewTab(
                data = OverviewTabData(
                    items = OverviewTabOption.entries
                        .map {
                            it.title
                        },
                    selectedItemIndex = data.overviewTabSelectionIndex,
                ),
                handleEvent = { event ->
                    when (event) {
                        is OverviewTabEvent.OnClick -> {
                            handleEvent(
                                OverviewCardEvent.OnOverviewTabClick(
                                    index = event.index,
                                )
                            )
                        }
                    }
                },
            )
            CosmosVerticalSpacer(
                height = 8.dp,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // TODO(Abhi): Disable the buttons conditionally
                CosmosIconButton(
                    onClickLabelStringResource = CosmosStringResource.Id(
                        id = R.string.finance_manager_overview_card_previous_button_content_description,
                    ),
                    onClick = {
                        handleEvent(
                            OverviewCardEvent.OnOverviewCardAction(
                                overviewCardAction = OverviewCardAction.PREV,
                            )
                        )
                    },
                ) {
                    CosmosIcon(
                        iconResource = CosmosIcons.ChevronLeft,
                        tint = FinanceManagerAppTheme.colorScheme.primary,
                    )
                }
                CosmosText(
                    modifier = Modifier
                        .padding(
                            horizontal = 8.dp,
                            vertical = 8.dp,
                        )
                        .weight(
                            weight = 1F,
                        ),
                    stringResource = CosmosStringResource.Text(
                        text = data.title,
                    ),
                    style = FinanceManagerAppTheme.typography.labelLarge
                        .copy(
                            color = FinanceManagerAppTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                        ),
                )
                CosmosIconButton(
                    onClickLabelStringResource = CosmosStringResource.Id(
                        id = R.string.finance_manager_overview_card_next_button_content_description,
                    ),
                    onClick = {
                        handleEvent(
                            OverviewCardEvent.OnOverviewCardAction(
                                overviewCardAction = OverviewCardAction.NEXT,
                            )
                        )
                    },
                ) {
                    CosmosIcon(
                        iconResource = CosmosIcons.ChevronRight,
                        tint = FinanceManagerAppTheme.colorScheme.primary,
                    )
                }
            }
            CosmosVerticalSpacer(
                height = 8.dp,
            )
            data.pieChartData?.let { pieChartDataValue ->
                ComposePieChart(
                    data = pieChartDataValue,
                )
            }
        }
    }
}

@Composable
private fun OverviewCardLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(
                height = 223.dp,
            )
            .padding(
                horizontal = 32.dp,
                vertical = 16.dp,
            )
            .clip(
                shape = FinanceManagerAppTheme.shapes.medium,
            )
            .shimmer(),
    )
}
