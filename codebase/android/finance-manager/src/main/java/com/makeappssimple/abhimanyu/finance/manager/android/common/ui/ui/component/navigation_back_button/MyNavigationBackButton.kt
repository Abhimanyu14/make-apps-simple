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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.navigation_back_button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button.CosmosIconButton
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.design_system.theme.FinanceManagerAppTheme
import com.makeappssimple.abhimanyu.library.finance.manager.android.R

@Composable
internal fun MyNavigationBackButton(
    modifier: Modifier = Modifier,
    handleEvent: (event: MyNavigationBackButtonEvents) -> Unit = {},
) {
    CosmosIconButton(
        onClickLabelStringResource = CosmosStringResource.Id(
            id = R.string.finance_manager_navigation_back_button_navigation_icon_content_description,
        ),
        onClick = {
            handleEvent(MyNavigationBackButtonEvents.OnClick)
        },
        modifier = modifier,
    ) {
        CosmosIcon(
            iconResource = CosmosIcons.ArrowBack,
            tint = FinanceManagerAppTheme.colorScheme.primary,
        )
    }
}
