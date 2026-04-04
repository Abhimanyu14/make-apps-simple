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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.default_tag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme

import com.makeappssimple.abhimanyu.library.finance.manager.android.R

private object MyDefaultTagConstants {
    val paddingHorizontal = 8.dp
    val paddingVertical = 1.dp
}

@Composable
internal fun MyDefaultTag(
    modifier: Modifier = Modifier,
) {
    CosmosText(
        modifier = modifier
            .clip(
                shape = CircleShape,
            )
            .background(
                color = CosmosAppTheme.colorScheme.primary,
            )
            .padding(
                horizontal = MyDefaultTagConstants.paddingHorizontal,
                vertical = MyDefaultTagConstants.paddingVertical,
            ),
        stringResource = CosmosStringResource.Id(
            id = R.string.finance_manager_default_tag,
        ),
        style = CosmosAppTheme.typography.labelSmall
            .copy(
                color = CosmosAppTheme.colorScheme.onPrimary,
            ),
    )
}
