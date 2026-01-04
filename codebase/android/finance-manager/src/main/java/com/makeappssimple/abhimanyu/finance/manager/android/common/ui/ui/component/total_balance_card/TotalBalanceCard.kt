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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.total_balance_card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.cosmos.design.system.android.extensions.cosmosShimmer
import com.makeappssimple.abhimanyu.cosmos.design.system.android.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Amount
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.constants.TestTags.COMPONENT_TOTAL_BALANCE_CARD
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.model.toDefaultString
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.chip.ChipUI
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.chip.ChipUIEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.extensions.match_row_size.matchRowSize
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

private object TotalBalanceCardConstants {
    val loadingUIHeight = 96.dp
    val loadingUIHorizontalPadding = 32.dp
    val loadingUIVerticalPadding = 16.dp
}

/**
 * This is coupled with [Amount].
 */
@Composable
internal fun TotalBalanceCard(
    modifier: Modifier = Modifier,
    data: TotalBalanceCardData,
    handleEvent: (event: TotalBalanceCardEvent) -> Unit = {},
) {
    val modifierWithTestTag = modifier
        .testTag(
            tag = COMPONENT_TOTAL_BALANCE_CARD,
        )
    if (data.isLoading) {
        TotalBalanceCardLoadingUI(
            modifier = modifierWithTestTag,
        )
    } else {
        TotalBalanceCardUI(
            modifier = modifierWithTestTag,
            data = data,
            handleEvent = handleEvent,
        )
    }
}

@Composable
private fun TotalBalanceCardUI(
    modifier: Modifier = Modifier,
    data: TotalBalanceCardData,
    handleEvent: (event: TotalBalanceCardEvent) -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 32.dp,
                vertical = 16.dp,
            )
            .clip(
                shape = CosmosAppTheme.shapes.medium,
            )
            .background(
                color = CosmosAppTheme.colorScheme.tertiary,
            )
            .conditionalClickable(
                onClick = if (data.isClickable) {
                    {
                        handleEvent(TotalBalanceCardEvent.OnClick)
                    }
                } else {
                    null
                },
            )
            .padding(
                all = 16.dp,
            ),
    ) {
        if (data.isBalanceVisible) {
            CosmosText(
                modifier = Modifier
                    .fillMaxWidth(),
                stringResource = CosmosStringResource.Id(
                    id = R.string.finance_manager_total_balance_card_title,
                ),
                style = CosmosAppTheme.typography.displaySmall
                    .copy(
                        color = CosmosAppTheme.colorScheme.onTertiary,
                        textAlign = TextAlign.Center,
                    ),
            )
            CosmosText(
                modifier = Modifier
                    .fillMaxWidth(),
                stringResource = CosmosStringResource.Text(
                    text = Amount(
                        value = data.totalBalanceAmount,
                    ).toDefaultString(),
                ),
                style = CosmosAppTheme.typography.displayLarge
                    .copy(
                        color = CosmosAppTheme.colorScheme.onTertiary,
                        textAlign = TextAlign.Center,
                    ),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CosmosIcon(
                    iconResource = CosmosIcons.Lock,
                    tint = CosmosAppTheme.colorScheme.onTertiary,
                    modifier = Modifier
                        .padding(
                            all = 2.dp,
                        )
                        .matchRowSize(),
                )
                CosmosText(
                    stringResource = CosmosStringResource.Text(
                        text = Amount(
                            value = data.totalMinimumBalanceAmount,
                        ).toDefaultString(),
                    ),
                    style = CosmosAppTheme.typography.bodySmall
                        .copy(
                            fontWeight = FontWeight.Bold,
                            color = CosmosAppTheme.colorScheme.onTertiary,
                            textAlign = TextAlign.End,
                        ),
                )
            }
        } else {
            ChipUI(
                modifier = Modifier,
                data = ChipUIData(
                    borderColor = CosmosAppTheme.colorScheme.onTertiary,
                    textColor = CosmosAppTheme.colorScheme.onTertiary,
                    stringResource = CosmosStringResource.Id(
                        id = R.string.finance_manager_total_balance_card_view_balance,
                    ),
                ),
                handleEvent = { event ->
                    when (event) {
                        is ChipUIEvent.OnClick -> {
                            handleEvent(TotalBalanceCardEvent.OnViewBalanceClick)
                        }
                    }
                },
            )
        }
    }
}

@Composable
private fun TotalBalanceCardLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(
                height = TotalBalanceCardConstants.loadingUIHeight,
            )
            .padding(
                horizontal = TotalBalanceCardConstants.loadingUIHorizontalPadding,
                vertical = TotalBalanceCardConstants.loadingUIVerticalPadding,
            )
            .clip(
                shape = CosmosAppTheme.shapes.medium,
            )
            .cosmosShimmer(),
    )
}
