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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.listitem.accounts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.common.getLogoUrl
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.component.default_tag.MyDefaultTag
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.extensions.shimmer.shimmer
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.ui.util.minimumListItemHeight
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

private object AccountsListItemContentConstants {
    val loadingUIHeight = 40.dp
    val loadingUIHorizontalPadding = 16.dp
    val loadingUIVerticalPadding = 4.dp
}

@Immutable
internal data class AccountsListItemContentDataAndEventHandler(
    val data: AccountsListItemContentData,
    val handleEvent: (event: AccountsListItemContentEvent) -> Unit = {},
)

@Immutable
internal data class AccountsListItemContentData(
    override val type: AccountsListItemType = AccountsListItemType.CONTENT,
    val isDefault: Boolean = false,
    val isDeleteEnabled: Boolean = false,
    val isHeading: Boolean = false,
    val isLoading: Boolean = false,
    val isLowBalance: Boolean = false,
    val isMoreOptionsIconButtonVisible: Boolean = false,
    val isSelected: Boolean = false,
    val icon: ImageVector? = null,
    val accountId: Int? = null,
    val balance: String? = null,
    val name: String,
) : AccountsListItemData

@Immutable
internal sealed class AccountsListItemContentEvent {
    data object OnClick : AccountsListItemContentEvent()
}

@Composable
internal fun AccountsListItemContent(
    modifier: Modifier = Modifier,
    data: AccountsListItemContentData,
    handleEvent: (event: AccountsListItemContentEvent) -> Unit = {},
) {
    if (data.isLoading) {
        AccountsListItemContentLoadingUI(
            modifier = modifier,
        )
    } else {
        AccountsListItemContentUI(
            modifier = modifier,
            data = data,
            handleEvent = handleEvent,
        )
    }
}

@Composable
internal fun AccountsListItemContentLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(
                height = AccountsListItemContentConstants.loadingUIHeight,
            )
            .padding(
                horizontal = AccountsListItemContentConstants.loadingUIHorizontalPadding,
                vertical = AccountsListItemContentConstants.loadingUIVerticalPadding,
            )
            .clip(
                shape = FinanceManagerAppTheme.shapes.small,
            )
            .shimmer(),
    )
}

@Composable
private fun AccountsListItemContentUI(
    modifier: Modifier = Modifier,
    data: AccountsListItemContentData,
    handleEvent: (event: AccountsListItemContentEvent) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(
                minHeight = minimumListItemHeight,
            )
            .conditionalClickable(
                onClick = {
                    handleEvent(AccountsListItemContentEvent.OnClick)
                },
            )
            .padding(
                start = 16.dp,
                end = 16.dp,
            ),
    ) {
        val logoUrl = getLogoUrl(
            name = data.name,
        )
        if (logoUrl != null) {
            val context = LocalPlatformContext.current
            AsyncImage(
                model = ImageRequest
                    .Builder(
                        context = context,
                    )
                    .data(
                        data = logoUrl,
                    )
                    .decoderFactory(
                        factory = SvgDecoder.Factory(),
                    )
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(
                        end = 8.dp,
                    )
                    .size(
                        size = 28.dp,
                    )
                    .clip(
                        shape = CircleShape,
                    )
                    .background(
                        color = FinanceManagerAppTheme.colorScheme.primaryContainer,
                    )
                    .padding(
                        all = 4.dp,
                    ),
            )
        } else {
            data.icon?.let {
                Icon(
                    imageVector = data.icon,
                    contentDescription = null,
                    tint = FinanceManagerAppTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(
                            end = 8.dp,
                        ),
                )
            }
        }
        MyText(
            modifier = Modifier
                .padding(
                    end = 16.dp,
                ),
            text = data.name,
            style = FinanceManagerAppTheme.typography.headlineLarge
                .copy(
                    color = if (data.isSelected) {
                        FinanceManagerAppTheme.colorScheme.primary
                    } else {
                        FinanceManagerAppTheme.colorScheme.onBackground
                    },
                ),
        )
        AnimatedVisibility(
            visible = data.isDefault,
        ) {
            MyDefaultTag()
        }
        Spacer(
            modifier = Modifier
                .weight(
                    weight = 1F,
                ),
        )
        data.balance?.let {
            MyText(
                text = data.balance,
                style = FinanceManagerAppTheme.typography.headlineLarge
                    .copy(
                        color = if (data.isSelected) {
                            FinanceManagerAppTheme.colorScheme.primary
                        } else if (data.isLowBalance) {
                            FinanceManagerAppTheme.colorScheme.error
                        } else {
                            FinanceManagerAppTheme.colorScheme.onBackground
                        },
                    ),
            )
        }
        if (data.isMoreOptionsIconButtonVisible) {
            Icon(
                imageVector = MyIcons.MoreVert,
                contentDescription = stringResource(
                    id = R.string.finance_manager_account_list_item_more_options_content_description,
                ),
                tint = FinanceManagerAppTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                    ),
            )
        }
    }
}
