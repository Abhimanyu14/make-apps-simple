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

package com.makeappssimple.abhimanyu.cosmos.design.system.catalog.android.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.list.CosmosListItem
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.list.CosmosListItemData
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.list.CosmosListItemDataEvent
import com.makeappssimple.abhimanyu.cosmos.design.system.android.components.top_app_bar.CosmosTopAppBar
import com.makeappssimple.abhimanyu.cosmos.design.system.android.resource.CosmosStringResource
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.android.navigation.CosmosDesignSystemCatalogScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CosmosDesignSystemCatalogHomeScreen(
    screenViewModel: CosmosDesignSystemCatalogHomeScreenViewModel = koinViewModel(),
) {
    val screens = listOf(
        CosmosDesignSystemCatalogScreen.Typography,
        CosmosDesignSystemCatalogScreen.Colors,
        CosmosDesignSystemCatalogScreen.Shapes,
        CosmosDesignSystemCatalogScreen.Icons,
        CosmosDesignSystemCatalogScreen.Components,
    )
    Scaffold(
        topBar = {
            CosmosTopAppBar(
                titleStringResource = CosmosStringResource.Text(
                    text = "Cosmos Design System Catalog",
                ),
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            items(
                items = screens,
                key = { it.route },
            ) { screen ->
                CosmosListItem(
                    data = CosmosListItemData(
                        stringResource = CosmosStringResource.Text(
                            text = screen.route.replaceFirstChar { it.uppercase() },
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
