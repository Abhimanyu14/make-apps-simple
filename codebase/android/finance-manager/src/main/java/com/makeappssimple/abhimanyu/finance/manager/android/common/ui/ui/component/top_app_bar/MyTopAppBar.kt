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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.top_app_bar

import androidx.annotation.StringRes
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.common.core.extensions.isNotNull
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.component.MyText
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.typealiases.ComposableContent
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.typealiases.NullableComposableContent
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.navigation_back_button.MyNavigationBackButton
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.navigation_back_button.MyNavigationBackButtonEvents

@Composable
internal fun MyTopAppBar(
    modifier: Modifier = Modifier,
    @StringRes titleTextStringResourceId: Int,
    onNavigationButtonClick: (() -> Unit)? = null,
    appBarActions: NullableComposableContent = null,
) {
    val isNavigationButtonVisible = remember(
        key1 = onNavigationButtonClick,
    ) {
        onNavigationButtonClick.isNotNull()
    }

    MyTopAppBarUI(
        titleText = stringResource(
            id = titleTextStringResourceId,
        ),
        modifier = modifier,
        appBarActions = appBarActions,
        navigationButton = {
            if (isNavigationButtonVisible) {
                MyNavigationBackButton(
                    handleEvent = { events ->
                        when (events) {
                            is MyNavigationBackButtonEvents.OnClick -> {
                                onNavigationButtonClick?.invoke()
                            }
                        }
                    },
                )
            }
        },
    )
}

@Composable
private fun MyTopAppBarUI(
    modifier: Modifier = Modifier,
    titleText: String,
    appBarActions: NullableComposableContent,
    navigationButton: ComposableContent,
) {
    CenterAlignedTopAppBar(
        title = {
            MyText(
                text = titleText,
                style = FinanceManagerAppTheme.typography.titleLarge
                    .copy(
                        color = FinanceManagerAppTheme.colorScheme.primary,
                    ),
            )
        },
        navigationIcon = {
            navigationButton()
        },
        actions = {
            appBarActions?.invoke()
        },
        colors = TopAppBarDefaults
            .centerAlignedTopAppBarColors(
                containerColor = FinanceManagerAppTheme.colorScheme.background,
            ),
        modifier = modifier,
    )
}
