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

package com.makeappssimple.abhimanyu.cosmos.design.system.android.components.button

import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosIconResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme

@Composable
public fun CosmosFloatingActionButton(
    modifier: Modifier = Modifier,
    iconResource: CosmosIconResource,
    contentDescriptionStringResource: CosmosStringResource,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        containerColor = CosmosAppTheme.colorScheme.primary,
        onClick = onClick,
        modifier = modifier,
    ) {
        CosmosIcon(
            iconResource = iconResource,
            contentDescriptionStringResource = contentDescriptionStringResource,
            tint = CosmosAppTheme.colorScheme.onPrimary,
        )
    }
}
