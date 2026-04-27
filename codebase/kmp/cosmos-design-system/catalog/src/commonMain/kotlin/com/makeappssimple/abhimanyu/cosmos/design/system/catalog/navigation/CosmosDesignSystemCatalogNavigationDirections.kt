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

package com.makeappssimple.abhimanyu.cosmos.design.system.catalog.navigation

public sealed class CosmosDesignSystemCatalogNavigationDirections(
    override val cosmosDesignSystemCatalogCommand: CosmosDesignSystemCatalogCommand,
    override val destination: String,
) : CosmosDesignSystemCatalogNavigationCommand {
    override val cosmosDesignSystemCatalogScreen: String = destination

    public data object Colors : CosmosDesignSystemCatalogNavigationDirections(
        cosmosDesignSystemCatalogCommand = CosmosDesignSystemCatalogCommand.Navigate,
        destination = CosmosDesignSystemCatalogScreen.Colors.route,
    )

    public data object Components : CosmosDesignSystemCatalogNavigationDirections(
        cosmosDesignSystemCatalogCommand = CosmosDesignSystemCatalogCommand.Navigate,
        destination = CosmosDesignSystemCatalogScreen.Components.route,
    )

    public data object Home : CosmosDesignSystemCatalogNavigationDirections(
        cosmosDesignSystemCatalogCommand = CosmosDesignSystemCatalogCommand.Navigate,
        destination = CosmosDesignSystemCatalogScreen.Home.route,
    )

    public data object Icons : CosmosDesignSystemCatalogNavigationDirections(
        cosmosDesignSystemCatalogCommand = CosmosDesignSystemCatalogCommand.Navigate,
        destination = CosmosDesignSystemCatalogScreen.Icons.route,
    )

    public data object Shapes : CosmosDesignSystemCatalogNavigationDirections(
        cosmosDesignSystemCatalogCommand = CosmosDesignSystemCatalogCommand.Navigate,
        destination = CosmosDesignSystemCatalogScreen.Shapes.route,
    )

    public data object Typography : CosmosDesignSystemCatalogNavigationDirections(
        cosmosDesignSystemCatalogCommand = CosmosDesignSystemCatalogCommand.Navigate,
        destination = CosmosDesignSystemCatalogScreen.Typography.route,
    )

    public data object NavigateUp : CosmosDesignSystemCatalogNavigationDirections(
        cosmosDesignSystemCatalogCommand = CosmosDesignSystemCatalogCommand.NavigateUp,
        destination = "",
    )

    public data object ClearBackStackAndNavigateToHome : CosmosDesignSystemCatalogNavigationDirections(
        cosmosDesignSystemCatalogCommand = CosmosDesignSystemCatalogCommand.ClearBackStackAndNavigate,
        destination = CosmosDesignSystemCatalogScreen.Home.route,
    )
}
