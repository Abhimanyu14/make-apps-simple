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

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.navigation.CosmosDesignSystemCatalogNavigationState
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.navigation.CosmosDesignSystemCatalogScreen

public class CosmosDesignSystemCatalogHomeScreenViewModel(
    private val navigationState: CosmosDesignSystemCatalogNavigationState,
) : ViewModel() {
    public fun handleRouteClick(
        screen: CosmosDesignSystemCatalogScreen,
    ) {
        when (screen) {
            CosmosDesignSystemCatalogScreen.Colors -> {
                navigationState.navigateToColorsScreen()
            }

            CosmosDesignSystemCatalogScreen.Components -> {
                navigationState.navigateToComponentsScreen()
            }

            CosmosDesignSystemCatalogScreen.Home -> {}

            CosmosDesignSystemCatalogScreen.Icons -> {
                navigationState.navigateToIconsScreen()
            }

            CosmosDesignSystemCatalogScreen.Shapes -> {
                navigationState.navigateToShapesScreen()
            }

            CosmosDesignSystemCatalogScreen.Typography -> {
                navigationState.navigateToTypographyScreen()
            }
        }
    }
}
