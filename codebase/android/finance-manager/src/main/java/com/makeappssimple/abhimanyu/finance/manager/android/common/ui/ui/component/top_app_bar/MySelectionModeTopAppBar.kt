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

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.component.button.MyIconButton
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.icons.MyIcons
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.typealiases.ComposableContent
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.typealiases.RowScopedComposableContent
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
            MyIconButton(
                tint = FinanceManagerAppTheme.colorScheme.onBackground,
                imageVector = MyIcons.Close,
                contentDescriptionStringResourceId = R.string.finance_manager_navigation_close_button_navigation_icon_content_description,
                onClick = onNavigationButtonClick,
            )
        },
        actions = appBarActions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = FinanceManagerAppTheme.colorScheme.background,
        ),
        modifier = modifier,
    )
}
