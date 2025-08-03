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

@file:OptIn(ExperimentalMaterial3Api::class)

package com.makeappssimple.abhimanyu.finance.manager.android.core.ui.component.top_app_bar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.finance.manager.android.core.designsystem.component.button.MyIconButton
import com.makeappssimple.abhimanyu.finance.manager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.finance.manager.android.core.designsystem.typealiases.ComposableContent
import com.makeappssimple.abhimanyu.finance.manager.android.core.designsystem.typealiases.RowScopedComposableContent
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Composable
public fun MySelectionModeTopAppBar(
    modifier: Modifier = Modifier,
    appBarActions: RowScopedComposableContent = {},
    navigationAction: () -> Unit = {},
    title: ComposableContent = {},
) {
    MySelectionModeTopAppBarUI(
        modifier = modifier,
        appBarActions = appBarActions,
        navigationAction = navigationAction,
        title = title,
    )
}

@Composable
public fun MySelectionModeTopAppBarUI(
    modifier: Modifier = Modifier,
    appBarActions: RowScopedComposableContent,
    navigationAction: () -> Unit,
    title: ComposableContent,
) {
    TopAppBar(
        title = title,
        navigationIcon = {
            MyIconButton(
                tint = MaterialTheme.colorScheme.onBackground,
                imageVector = MyIcons.Close,
                contentDescriptionStringResourceId = R.string.navigation_close_button_navigation_icon_content_description,
                onClick = navigationAction,
            )
        },
        actions = appBarActions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        modifier = modifier,
    )
}
