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

@file:OptIn(ExperimentalMaterial3Api::class)

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.top_app_bar

import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.constants.FinanceManagerStrings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.cosmos.design.system.components.button.CosmosIconButton
import com.makeappssimple.abhimanyu.cosmos.design.system.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.theme.CosmosAppTheme
import com.makeappssimple.abhimanyu.cosmos.design.system.typealiases.ComposableContent
import com.makeappssimple.abhimanyu.cosmos.design.system.typealiases.RowScopedComposableContent
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Composable
internal fun MySelectionModeTopAppBar(
    modifier: Modifier = Modifier,
    appBarActions: RowScopedComposableContent = {},
    onNavigationButtonClick: () -> Unit = {},
    title: ComposableContent = {},
) {
    MySelectionModeTopAppBarUI(
        modifier = modifier,
        appBarActions = appBarActions,
        onNavigationButtonClick = onNavigationButtonClick,
        title = title,
    )
}

@Composable
internal fun MySelectionModeTopAppBarUI(
    modifier: Modifier = Modifier,
    appBarActions: RowScopedComposableContent,
    onNavigationButtonClick: () -> Unit,
    title: ComposableContent,
) {
    TopAppBar(
        title = title,
        navigationIcon = {
            CosmosIconButton(
                onClickLabelStringResource = CosmosStringResource.Text(value = FinanceManagerStrings.get(template = FinanceManagerStrings.finance_manager_navigation_close_button_navigation_icon_content_description)),
                onClick = onNavigationButtonClick,
            ) {
                CosmosIcon(
                    iconResource = CosmosIcons.Close,
                    tint = CosmosAppTheme.colorScheme.onBackground,
                )
            }
        },
        actions = appBarActions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CosmosAppTheme.colorScheme.background,
        ),
        modifier = modifier,
    )
}
