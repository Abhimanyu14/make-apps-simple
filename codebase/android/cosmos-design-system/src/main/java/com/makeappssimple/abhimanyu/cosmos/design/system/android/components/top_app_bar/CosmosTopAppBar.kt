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

package com.makeappssimple.abhimanyu.cosmos.design.system.android.components.top_app_bar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.cosmos.design.system.android.TestTags.COMPONENT_COSMOS_TOP_APP_BAR
import com.makeappssimple.abhimanyu.cosmos.design.system.android.TestTags.COMPONENT_COSMOS_TOP_APP_BAR_NAVIGATION_BUTTON
import com.makeappssimple.abhimanyu.cosmos.design.system.android.TestTags.COMPONENT_COSMOS_TOP_APP_BAR_TITLE_TEXT
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button.CosmosIconButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.extensions.isNotNull
import com.makeappssimple.abhimanyu.cosmos.design.system.android.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosIconResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme
import com.makeappssimple.abhimanyu.library.cosmos.design.system.android.R

@Composable
public fun CosmosTopAppBar(
    modifier: Modifier = Modifier,
    navigationIconResource: CosmosIconResource? = null,
    titleStringResource: CosmosStringResource,
    // TODO(Abhi): Update to StringResource
    navigationLabelStringResource: CosmosStringResource? = null,
    navigationAction: (() -> Unit)? = null,
    appBarActions: @Composable (() -> Unit)? = null,
) {
    CosmosTopAppBarUI(
        isNavigationIconVisible = navigationAction.isNotNull(),
        titleStringResource = titleStringResource,
        modifier = modifier,
        appBarActions = appBarActions,
        navigationButton = {
            CosmosTopAppBarNavigationButton(
                onClickLabelStringResource = navigationLabelStringResource
                    ?: CosmosStringResource.Id(
                        id = R.string.cosmos_navigation_back_button_navigation_icon_content_description,
                    ),
                iconResource = navigationIconResource,
                onClick = {
                    navigationAction?.invoke()
                },
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CosmosTopAppBarUI(
    modifier: Modifier = Modifier,
    isNavigationIconVisible: Boolean,
    titleStringResource: CosmosStringResource,
    appBarActions: @Composable (() -> Unit)?,
    navigationButton: @Composable () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            CosmosText(
                stringResource = titleStringResource,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = CosmosAppTheme.typography.titleLarge,
                modifier = Modifier
                    .testTag(
                        tag = COMPONENT_COSMOS_TOP_APP_BAR_TITLE_TEXT,
                    ),
            )
        },
        navigationIcon = {
            if (isNavigationIconVisible) {
                navigationButton()
            }
        },
        actions = {
            appBarActions?.invoke()
        },
        colors = TopAppBarDefaults
            .centerAlignedTopAppBarColors(
                containerColor = CosmosAppTheme.colorScheme.background,
            ),
        modifier = modifier
            .testTag(
                tag = COMPONENT_COSMOS_TOP_APP_BAR,
            ),
    )
}

@Composable
public fun CosmosTopAppBarActionButton(
    modifier: Modifier = Modifier,
    iconResource: CosmosIconResource,
    iconContentDescriptionStringResource: CosmosStringResource,
    onClickLabelStringResource: CosmosStringResource,
    onClick: () -> Unit,
) {
    CosmosIconButton(
        onClickLabelStringResource = onClickLabelStringResource,
        onClick = onClick,
        modifier = modifier
            .padding(
                end = 4.dp,
            ),
    ) {
        CosmosIcon(
            iconResource = iconResource,
            contentDescriptionStringResource = iconContentDescriptionStringResource,
        )
    }
}

@Composable
private fun CosmosTopAppBarNavigationButton(
    iconResource: CosmosIconResource? = null,
    onClickLabelStringResource: CosmosStringResource,
    onClick: () -> Unit,
) {
    CosmosIconButton(
        onClickLabelStringResource = onClickLabelStringResource,
        onClick = onClick,
        modifier = Modifier
            .testTag(
                tag = COMPONENT_COSMOS_TOP_APP_BAR_NAVIGATION_BUTTON,
            ),
    ) {
        CosmosIcon(
            iconResource = iconResource ?: CosmosIcons.ArrowBack,
            // TODO(Abhi): To update
            // contentDescription = onClickLabel,
        )
    }
}
