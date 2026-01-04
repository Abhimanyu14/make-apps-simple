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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.listitem.transaction

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.cosmos.design.system.android.extensions.cosmosShimmer
import com.makeappssimple.abhimanyu.cosmos.design.system.android.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.theme.ExpandedListItemShape
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.theme.composeColor
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.MyExpandableItemIconButton
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.MyExpandableItemIconButtonData
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.MyExpandableItemIconButtonEvent
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.MyExpandableItemUIWrapper
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.emoji_circle.MyEmojiCircle
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.emoji_circle.MyEmojiCircleData
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Composable
internal fun TransactionListItem(
    modifier: Modifier = Modifier,
    data: TransactionListItemData,
    handleEvent: (event: TransactionListItemEvent) -> Unit = {},
) {
    if (data.isLoading) {
        TransactionListItemLoadingUI(
            modifier = modifier,
        )
    } else {
        TransactionListItemUI(
            modifier = modifier,
            data = data,
            handleEvent = handleEvent,
        )
    }
}

@Composable
private fun TransactionListItemUI(
    modifier: Modifier = Modifier,
    data: TransactionListItemData,
    handleEvent: (event: TransactionListItemEvent) -> Unit = {},
) {
    val accountTextStringResource: CosmosStringResource = if (
        data.accountFromName.isNotNull() &&
        data.accountToName.isNotNull()
    ) {
        CosmosStringResource.Id(
            id = R.string.finance_manager_transaction_list_item_account,
            args = listOf(
                data.accountFromName,
                data.accountToName,
            ),
        )
    } else {
        CosmosStringResource.Text(
            text = data.accountFromName ?: data.accountToName.orEmpty(),
        )
    }

    MyExpandableItemUIWrapper(
        isExpanded = data.isExpanded,
        isSelected = data.isSelected,
        modifier = modifier
            .padding(
                vertical = 4.dp,
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    shape = if (data.isExpanded) {
                        ExpandedListItemShape
                    } else {
                        CosmosAppTheme.shapes.large
                    },
                )
                .conditionalClickable(
                    onClick = {
                        handleEvent(TransactionListItemEvent.OnClick)
                    },
                    onLongClick = {
                        handleEvent(TransactionListItemEvent.OnLongClick)
                    },
                )
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = if (data.isExpanded) {
                        16.dp
                    } else {
                        4.dp
                    },
                    bottom = if (data.isExpanded) {
                        16.dp
                    } else {
                        4.dp
                    },
                ),
        ) {
            if (data.isInSelectionMode) {
                if (data.isSelected) {
                    CosmosIcon(
                        iconResource = CosmosIcons.CheckCircle,
                        tint = CosmosAppTheme.colorScheme.primary,
                    )
                } else {
                    CosmosIcon(
                        iconResource = CosmosIcons.RadioButtonUnchecked,
                        tint = CosmosAppTheme.colorScheme.outline,
                    )
                }
            } else {
                MyEmojiCircle(
                    data = MyEmojiCircleData(
                        backgroundColor = CosmosAppTheme.colorScheme.outline,
                        emoji = data.emoji,
                    ),
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                    ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    CosmosText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(
                                weight = 1F,
                            ),
                        stringResource = CosmosStringResource.Text(
                            text = data.title,
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = CosmosAppTheme.typography.headlineMedium
                            .copy(
                                color = CosmosAppTheme.colorScheme.onBackground,
                            ),
                    )
                    CosmosText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(
                                weight = 1F,
                            ),
                        stringResource = CosmosStringResource.Text(
                            text = data.amountText,
                        ),
                        style = CosmosAppTheme.typography.headlineMedium
                            .copy(
                                color = data.amountColor.composeColor,
                                textAlign = TextAlign.End,
                            ),
                    )
                }
                CosmosText(
                    modifier = Modifier
                        .fillMaxWidth(),
                    stringResource = CosmosStringResource.Text(
                        text = data.transactionForText,
                    ),
                    style = CosmosAppTheme.typography.bodySmall
                        .copy(
                            color = CosmosAppTheme.colorScheme.onBackground,
                        ),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    CosmosText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(
                                weight = 1F,
                            ),
                        stringResource = CosmosStringResource.Text(
                            text = data.dateAndTimeText,
                        ),
                        style = CosmosAppTheme.typography.bodySmall
                            .copy(
                                color = CosmosAppTheme.colorScheme.onBackground,
                            ),
                    )
                    CosmosText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(
                                weight = 1F,
                            ),
                        stringResource = accountTextStringResource,
                        style = CosmosAppTheme.typography.bodySmall
                            .copy(
                                color = CosmosAppTheme.colorScheme.onBackground,
                                textAlign = TextAlign.End,
                            ),
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(
                            height = 2.dp,
                        ),
                )
                AnimatedVisibility(
                    visible = data.isExpanded.not(),
                ) {
                    HorizontalDivider(
                        color = CosmosAppTheme.colorScheme.outline,
                        thickness = 0.5.dp,
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = data.isExpanded,
        ) {
            HorizontalDivider(
                color = CosmosAppTheme.colorScheme.outline,
                thickness = 0.5.dp,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 8.dp,
                    ),
            ) {
                // TODO(Abhi): Disable edit transaction feature
                //  Fix this later.
                /*
                if (data.isEditButtonVisible) {
                    MyExpandableItemIconButton(
                        data = MyExpandableItemIconButtonData(
                            isClickable = true,
                            isEnabled = true,
                            iconImageVector = CosmosIcons.Edit,
                            labelText = CosmosStringResource.Id(
                                id = R.string.finance_manager_transaction_list_item_edit,
                            ).text,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is MyExpandableItemIconButtonEvent.OnClick -> {
                                    handleEvent(TransactionListItemEvent.OnEditButtonClick)
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(
                                weight = 1F,
                            ),
                    )
                }
                */
                if (data.isRefundButtonVisible) {
                    MyExpandableItemIconButton(
                        data = MyExpandableItemIconButtonData(
                            isClickable = true,
                            isEnabled = true,
                            iconResource = CosmosIcons.CurrencyExchange,
                            labelStringResource = CosmosStringResource.Id(
                                id = R.string.finance_manager_transaction_list_item_refund,
                            ),
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is MyExpandableItemIconButtonEvent.OnClick -> {
                                    handleEvent(TransactionListItemEvent.OnRefundButtonClick)
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(
                                weight = 1F,
                            ),
                    )
                }
                if (data.isDeleteButtonVisible) {
                    MyExpandableItemIconButton(
                        data = MyExpandableItemIconButtonData(
                            isClickable = true,
                            isEnabled = data.isDeleteButtonEnabled,
                            iconResource = CosmosIcons.Delete,
                            labelStringResource = CosmosStringResource.Id(
                                id = R.string.finance_manager_transaction_list_item_delete,
                            ),
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is MyExpandableItemIconButtonEvent.OnClick -> {
                                    handleEvent(TransactionListItemEvent.OnDeleteButtonClick)
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(
                                weight = 1F,
                            ),
                    )
                }
            }
        }
    }
}

@Composable
private fun TransactionListItemLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp,
            )
            .fillMaxWidth()
            .height(
                height = 72.dp,
            )
            .clip(
                shape = RoundedCornerShape(
                    size = 24.dp,
                ),
            )
            .cosmosShimmer(),
    )
}
