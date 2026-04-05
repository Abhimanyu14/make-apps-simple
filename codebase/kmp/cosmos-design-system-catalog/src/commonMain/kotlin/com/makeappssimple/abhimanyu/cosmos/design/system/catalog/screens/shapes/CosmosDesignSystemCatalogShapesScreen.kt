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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.icon.CosmosIcon
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.text.CosmosText
import com.makeappssimple.abhimanyu.cosmos.design.system.android.icons.CosmosIcons
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosAppTheme
import com.makeappssimple.abhimanyu.cosmos.design.system.android.theme.CosmosColor
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.navigation.CosmosDesignSystemCatalogNavigationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun CosmosDesignSystemCatalogShapesScreen(
    navigationState: CosmosDesignSystemCatalogNavigationState,
    screenViewModel: CosmosDesignSystemCatalogShapesScreenViewModel = viewModel {
        CosmosDesignSystemCatalogShapesScreenViewModel(navigationState)
    },
) {
    val shapes = listOf(
        CosmosAppTheme.shapes.extraSmall to "Extra Small",
        CosmosAppTheme.shapes.small to "Small",
        CosmosAppTheme.shapes.medium to "Medium",
        CosmosAppTheme.shapes.large to "Large",
        CosmosAppTheme.shapes.extraLarge to "Extra Large",
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Shapes")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { screenViewModel.navigateUp() },
                    ) {
                        CosmosIcon(
                            iconResource = CosmosIcons.ArrowBack,
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(
                    state = rememberScrollState(),
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
            .fillMaxWidth()
            .padding(
                all = 16.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
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
                    color = CosmosColor.Primary.composeColor,
                ),
        )
        CosmosText(
            stringResource = CosmosStringResource.Text(text = name),
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
        )
    }
}
