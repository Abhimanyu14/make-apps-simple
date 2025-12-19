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

package com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.top_app_bar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.barcodes.android.core.constants.TestTags.COMPONENT_MY_TOP_APP_BAR
import com.makeappssimple.abhimanyu.barcodes.android.core.constants.TestTags.COMPONENT_MY_TOP_APP_BAR_NAVIGATION_BUTTON
import com.makeappssimple.abhimanyu.barcodes.android.core.constants.TestTags.COMPONENT_MY_TOP_APP_BAR_TITLE_TEXT
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.button.MyIconButton
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.icon.MyIcon
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.component.text.MyText
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.resource.IconResource
import com.makeappssimple.abhimanyu.barcodes.android.core.design_system.theme.BarcodesAppTheme
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.library.barcodes.android.R

@Composable
internal fun MyTopAppBar(
    modifier: Modifier = Modifier,
    navigationIconResource: IconResource? = null,
    @StringRes titleStringResourceId: Int,
    navigationLabel: String? = null,
    navigationAction: (() -> Unit)? = null,
    appBarActions: @Composable (() -> Unit)? = null,
) {
    MyTopAppBar(
        modifier = modifier,
        navigationIconResource = navigationIconResource,
        titleText = stringResource(
            id = titleStringResourceId,
        ),
        navigationLabel = navigationLabel,
        navigationAction = navigationAction,
        appBarActions = appBarActions,
    )
}

@Composable
internal fun MyTopAppBar(
    modifier: Modifier = Modifier,
    titleText: String,
    navigationIconResource: IconResource? = null,
    navigationLabel: String? = null,
    navigationAction: (() -> Unit)? = null,
    appBarActions: @Composable (() -> Unit)? = null,
) {
    MyTopAppBarUI(
        isNavigationIconVisible = navigationAction.isNotNull(),
        titleText = titleText,
        modifier = modifier,
        appBarActions = appBarActions,
        navigationButton = {
            MyTopAppBarNavigationButton(
                onClickLabel = navigationLabel ?: stringResource(
                    id = R.string.barcodes_navigation_back_button_navigation_icon_content_description,
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
private fun MyTopAppBarUI(
    modifier: Modifier = Modifier,
    isNavigationIconVisible: Boolean,
    titleText: String,
    appBarActions: @Composable (() -> Unit)?,
    navigationButton: @Composable () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            MyText(
                text = titleText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = BarcodesAppTheme.typography.titleLarge,
                modifier = modifier
                    .testTag(
                        tag = COMPONENT_MY_TOP_APP_BAR_TITLE_TEXT,
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
                containerColor = BarcodesAppTheme.colorScheme.background,
            ),
        modifier = modifier
            .testTag(
                tag = COMPONENT_MY_TOP_APP_BAR,
            ),
    )
}

@Composable
internal fun MyTopAppBarActionButton(
    iconResource: IconResource,
    @StringRes iconContentDescriptionStringResourceId: Int,
    @StringRes onClickLabelStringResourceId: Int,
    onClick: () -> Unit,
) {
    MyIconButton(
        onClickLabelStringResourceId = onClickLabelStringResourceId,
        onClick = onClick,
        modifier = Modifier
            .padding(
                end = 4.dp,
            ),
    ) {
        MyIcon(
            iconResource = iconResource,
            contentDescriptionStringResourceId = iconContentDescriptionStringResourceId,
        )
    }
}

@Composable
private fun MyTopAppBarNavigationButton(
    iconResource: IconResource? = null,
    onClickLabel: String,
    onClick: () -> Unit,
) {
    MyIconButton(
        onClickLabel = onClickLabel,
        onClick = onClick,
        modifier = Modifier
            .testTag(
                tag = COMPONENT_MY_TOP_APP_BAR_NAVIGATION_BUTTON,
            ),
    ) {
        MyIcon(
            iconResource = iconResource ?: MyIcons.ArrowBack,
            // TODO(Abhi): To update
            // contentDescription = onClickLabel,
        )
    }
}
