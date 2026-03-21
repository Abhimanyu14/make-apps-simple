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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    Scaffold(
        topBar = {
            CosmosTopAppBar(
                titleStringResource = CosmosStringResource.Text(
                    text = "Colors",
                ),
                navigationAction = screenViewModel::navigateUp,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(
                    state = rememberScrollState(),
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            val colors = listOf(
                CosmosColor.PRIMARY to "Primary",
                CosmosColor.ON_PRIMARY to "On Primary",
                CosmosColor.PRIMARY_CONTAINER to "Primary Container",
                CosmosColor.ON_PRIMARY_CONTAINER to "On Primary Container",
                CosmosColor.INVERSE_PRIMARY to "Inverse Primary",
                CosmosColor.SECONDARY to "Secondary",
                CosmosColor.ON_SECONDARY to "On Secondary",
                CosmosColor.SECONDARY_CONTAINER to "Secondary Container",
                CosmosColor.ON_SECONDARY_CONTAINER to "On Secondary Container",
                CosmosColor.TERTIARY to "Tertiary",
                CosmosColor.ON_TERTIARY to "On Tertiary",
                CosmosColor.TERTIARY_CONTAINER to "Tertiary Container",
                CosmosColor.ON_TERTIARY_CONTAINER to "On Tertiary Container",
                CosmosColor.BACKGROUND to "Background",
                CosmosColor.ON_BACKGROUND to "On Background",
                CosmosColor.SURFACE to "Surface",
                CosmosColor.ON_SURFACE to "On Surface",
                CosmosColor.SURFACE_VARIANT to "Surface Variant",
                CosmosColor.ON_SURFACE_VARIANT to "On Surface Variant",
                CosmosColor.INVERSE_SURFACE to "Inverse Surface",
                CosmosColor.INVERSE_ON_SURFACE to "Inverse On Surface",
                CosmosColor.ERROR to "Error",
                CosmosColor.ON_ERROR to "On Error",
                CosmosColor.ERROR_CONTAINER to "Error Container",
                CosmosColor.ON_ERROR_CONTAINER to "On Error Container",
                CosmosColor.OUTLINE to "Outline",
            )
            for (colorPair in colors) {
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
    name: String,
    color: CosmosColor,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = color.composeColor)
            .padding(16.dp),
    ) {
        CosmosText(
            text = name,
            style = CosmosTextStyle.Body2,
        )
    }
}
