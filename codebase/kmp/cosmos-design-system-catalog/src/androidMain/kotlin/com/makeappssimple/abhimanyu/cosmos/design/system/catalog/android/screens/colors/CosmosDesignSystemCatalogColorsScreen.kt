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

package com.makeappssimple.abhimanyu.cosmos.design.system.catalog.android.screens.colors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.scaffold.CosmosScaffold
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosTextStyle
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.top_app_bar.CosmosTopAppBar
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosColor
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CosmosDesignSystemCatalogColorsScreen(
    screenViewModel: CosmosDesignSystemCatalogColorsScreenViewModel = koinViewModel(),
) {
    val colors = listOf(
        CosmosColor.Primary to "Primary",
        CosmosColor.OnPrimary to "On Primary",
        CosmosColor.PrimaryContainer to "Primary Container",
        CosmosColor.OnPrimaryContainer to "On Primary Container",
        CosmosColor.InversePrimary to "Inverse Primary",
        CosmosColor.Secondary to "Secondary",
        CosmosColor.OnSecondary to "On Secondary",
        CosmosColor.SecondaryContainer to "Secondary Container",
        CosmosColor.OnSecondaryContainer to "On Secondary Container",
        CosmosColor.Tertiary to "Tertiary",
        CosmosColor.OnTertiary to "On Tertiary",
        CosmosColor.TertiaryContainer to "Tertiary Container",
        CosmosColor.OnTertiaryContainer to "On Tertiary Container",
        CosmosColor.Background to "Background",
        CosmosColor.OnBackground to "On Background",
        CosmosColor.Surface to "Surface",
        CosmosColor.OnSurface to "On Surface",
        CosmosColor.SurfaceVariant to "Surface Variant",
        CosmosColor.OnSurfaceVariant to "On Surface Variant",
        CosmosColor.InverseSurface to "Inverse Surface",
        CosmosColor.OnInverseSurface to "Inverse On Surface",
        CosmosColor.Error to "Error",
        CosmosColor.OnError to "On Error",
        CosmosColor.ErrorContainer to "Error Container",
        CosmosColor.OnErrorContainer to "On Error Container",
        CosmosColor.Outline to "Outline",
    )
    CosmosScaffold(
        topBar = {
            CosmosTopAppBar(
                titleStringResource = CosmosStringResource.Text(
                    text = "Colors",
                ),
                navigationAction = screenViewModel::navigateUp,
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = 16.dp,
                )
                .verticalScroll(
                    state = rememberScrollState(),
                ),
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
            ),
        ) {
            colors.forEach { colorPair ->
                ColorItem(
                    name = colorPair.second,
                    color = colorPair.first,
                )
            }
        }
    }
}

@Composable
private fun ColorItem(
    color: CosmosColor,
    name: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = color.composeColor,
            )
            .padding(
                all = 16.dp,
            ),
    ) {
        CosmosText(
            text = name,
            style = CosmosTextStyle.Body2,
        )
    }
}
