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

package com.makeappssimple.abhimanyu.cosmos.design.system.catalog.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.navigation.CosmosDesignSystemCatalogScreen
import com.makeappssimple.abhimanyu.cosmos.design.system.components.list.CosmosListItem
import com.makeappssimple.abhimanyu.cosmos.design.system.components.list.CosmosListItemData
import com.makeappssimple.abhimanyu.cosmos.design.system.components.list.CosmosListItemDataEvent
import com.makeappssimple.abhimanyu.cosmos.design.system.components.top_app_bar.CosmosTopAppBar
import com.makeappssimple.abhimanyu.cosmos.design.system.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.theme.CosmosAppTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
public fun CosmosDesignSystemCatalogHomeScreen(
    screenViewModel: CosmosDesignSystemCatalogHomeScreenViewModel = koinViewModel(),
) {
    val screens = listOf(
        CosmosDesignSystemCatalogScreen.Typography,
        CosmosDesignSystemCatalogScreen.Colors,
        CosmosDesignSystemCatalogScreen.Shapes,
        CosmosDesignSystemCatalogScreen.Icons,
        CosmosDesignSystemCatalogScreen.Components,
    )

    Column(
        modifier = Modifier
            .background(
                color = CosmosAppTheme.colorScheme.background,
            )
            .fillMaxSize(),
    ) {
        CosmosTopAppBar(
            // TODO(Abhi): Move to string resources
            titleStringResource = CosmosStringResource.Text(
                value = "Cosmos Design System Catalog",
            ),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            screens.forEach { screen ->
                CosmosListItem(
                    data = CosmosListItemData(
                        stringResource = CosmosStringResource.Text(
                            value = screen.route.replaceFirstChar {
                                it.uppercase()
                            },
                        ),
                    ),
                    handleEvent = { event ->
                        when (event) {
                            is CosmosListItemDataEvent.OnClick -> {
                                screenViewModel.handleRouteClick(screen)
                            }

                            else -> {}
                        }
                    },
                )
            }
        }
    }
}
