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

package com.makeappssimple.abhimanyu.cosmos.design.system.components.navigation_back_button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.cosmos.design.system.components.button.CosmosIconButton
import com.makeappssimple.abhimanyu.cosmos.design.system.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.theme.CosmosAppTheme

@Composable
public fun CosmosNavigationBackButton(
    modifier: Modifier = Modifier,
    handleEvent: (event: CosmosNavigationBackButtonEvents) -> Unit = {},
) {
    CosmosIconButton(
        onClickLabelStringResource = CosmosStringResource.Text(
            text = "Navigate Back",
        ),
        onClick = {
            handleEvent(CosmosNavigationBackButtonEvents.OnClick)
        },
        modifier = modifier,
    ) {
        CosmosIcon(
            iconResource = CosmosIcons.ArrowBack,
            tint = CosmosAppTheme.colorScheme.primary,
        )
    }
}
