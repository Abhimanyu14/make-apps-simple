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

package com.makeappssimple.abhimanyu.finance.manager.android.common.ui.ui.component.listitem.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme


@Composable
internal fun SettingsListItemAppVersion(
    modifier: Modifier = Modifier,
    data: SettingsListItemAppVersionData,
) {
    CosmosText(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 8.dp,
            ),
        stringResource = data.appVersionStringResource,
        style = CosmosAppTheme.typography.headlineLarge
            .copy(
                color = CosmosAppTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
            ),
    )
}
