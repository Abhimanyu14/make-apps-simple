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

package com.makeappssimple.abhimanyu.cosmos.design.system.catalog.android.navigation

internal object CosmosDesignSystemCatalogNavigationDirections {
    // Default
    object Default : CosmosDesignSystemCatalogNavigationCommand {
        override val cosmosDesignSystemCatalogCommand = CosmosDesignSystemCatalogCommand.NOOP
        override val cosmosDesignSystemCatalogScreen = ""
        override val destination = ""
    }

    // Navigate up
    object NavigateUp : CosmosDesignSystemCatalogNavigationCommand {
        override val cosmosDesignSystemCatalogCommand = CosmosDesignSystemCatalogCommand.NAVIGATE_UP
        override val cosmosDesignSystemCatalogScreen = ""
        override val destination = ""
    }

    // Clear backstack
    object ClearBackstack : CosmosDesignSystemCatalogNavigationCommand {
        override val cosmosDesignSystemCatalogCommand =
            CosmosDesignSystemCatalogCommand.CLEAR_BACKSTACK_AND_NAVIGATE
        override val cosmosDesignSystemCatalogScreen = ""
        override val destination = ""
    }

    // Clear till root
    object ClearTillRoot : CosmosDesignSystemCatalogNavigationCommand {
        override val cosmosDesignSystemCatalogCommand = CosmosDesignSystemCatalogCommand.CLEAR_TILL_ROOT
        override val cosmosDesignSystemCatalogScreen = ""
        override val destination = ""
    }

    // App specific
    object Colors : CosmosDesignSystemCatalogNavigationCommand {
        override val cosmosDesignSystemCatalogCommand = CosmosDesignSystemCatalogCommand.NAVIGATE
        override val cosmosDesignSystemCatalogScreen = CosmosDesignSystemCatalogScreen.Colors.route
        override val destination = CosmosDesignSystemCatalogScreen.Colors.route
    }

    object Components : CosmosDesignSystemCatalogNavigationCommand {
        override val cosmosDesignSystemCatalogCommand = CosmosDesignSystemCatalogCommand.NAVIGATE
        override val cosmosDesignSystemCatalogScreen = CosmosDesignSystemCatalogScreen.Components.route
        override val destination = CosmosDesignSystemCatalogScreen.Components.route
    }

    object Home : CosmosDesignSystemCatalogNavigationCommand {
        override val cosmosDesignSystemCatalogCommand = CosmosDesignSystemCatalogCommand.NAVIGATE
        override val cosmosDesignSystemCatalogScreen = CosmosDesignSystemCatalogScreen.Home.route
        override val destination = CosmosDesignSystemCatalogScreen.Home.route
    }

    object Icons : CosmosDesignSystemCatalogNavigationCommand {
        override val cosmosDesignSystemCatalogCommand = CosmosDesignSystemCatalogCommand.NAVIGATE
        override val cosmosDesignSystemCatalogScreen = CosmosDesignSystemCatalogScreen.Icons.route
        override val destination = CosmosDesignSystemCatalogScreen.Icons.route
    }

    object Shapes : CosmosDesignSystemCatalogNavigationCommand {
        override val cosmosDesignSystemCatalogCommand = CosmosDesignSystemCatalogCommand.NAVIGATE
        override val cosmosDesignSystemCatalogScreen = CosmosDesignSystemCatalogScreen.Shapes.route
        override val destination = CosmosDesignSystemCatalogScreen.Shapes.route
    }

    object Typography : CosmosDesignSystemCatalogNavigationCommand {
        override val cosmosDesignSystemCatalogCommand = CosmosDesignSystemCatalogCommand.NAVIGATE
        override val cosmosDesignSystemCatalogScreen = CosmosDesignSystemCatalogScreen.Typography.route
        override val destination = CosmosDesignSystemCatalogScreen.Typography.route
    }
}
