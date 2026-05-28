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

package com.makeappssimple.abhimanyu.cosmos.design.system.catalog.screens.shapes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.cosmos.design.system.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.components.top_app_bar.CosmosTopAppBar
import com.makeappssimple.abhimanyu.cosmos.design.system.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.theme.CosmosAppTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
public fun CosmosDesignSystemCatalogShapesScreen(
    screenViewModel: CosmosDesignSystemCatalogShapesScreenViewModel = koinViewModel(),
) {
    val shapes = listOf(
        CosmosAppTheme.shapes.extraSmall to "Extra Small",
        CosmosAppTheme.shapes.small to "Small",
        CosmosAppTheme.shapes.medium to "Medium",
        CosmosAppTheme.shapes.large to "Large",
        CosmosAppTheme.shapes.extraLarge to "Extra Large",
    )

    Column(
        modifier = Modifier
            .background(
                color = CosmosAppTheme.colorScheme.background,
            )
            .fillMaxSize(),
    ) {
        CosmosTopAppBar(
            titleStringResource = CosmosStringResource.Text(
                text = "Shapes",
            ),
            navigationAction = screenViewModel::navigateUp,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                )
                .verticalScroll(
                    state = rememberScrollState(),
                ),
            verticalArrangement = Arrangement
                .spacedBy(
                    space = 8.dp,
                ),
        ) {
            shapes.forEach { shape ->
                ShapeItem(
                    name = shape.second,
                    shape = shape.first,
                )
            }
        }
    }
}

@Composable
private fun ShapeItem(
    name: String,
    shape: Shape,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement
            .spacedBy(
                space = 16.dp,
            ),
    ) {
        Box(
            modifier = Modifier
                .size(
                    size = 64.dp,
                )
                .clip(
                    shape = shape,
                )
                .background(
                    color = CosmosAppTheme.colorScheme.primary,
                ),
        )
        CosmosText(
            stringResource = CosmosStringResource.Text(
                text = name,
            ),
            style = CosmosAppTheme.typography.bodyMedium,
        )
    }
}
