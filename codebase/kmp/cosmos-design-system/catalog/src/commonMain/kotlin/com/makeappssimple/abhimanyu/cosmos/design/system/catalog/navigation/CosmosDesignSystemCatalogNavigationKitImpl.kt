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

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

public class CosmosDesignSystemCatalogNavigationKitImpl(
    private val coroutineScope: CoroutineScope,
) : CosmosDesignSystemCatalogNavigationKit {
    private val _command: MutableSharedFlow<CosmosDesignSystemCatalogNavigationCommand> =
        MutableSharedFlow()
    override val command: SharedFlow<CosmosDesignSystemCatalogNavigationCommand> = _command

    override fun navigateToColorsScreen(): Job {
        return navigate(
            cosmosDesignSystemCatalogNavigationCommand = CosmosDesignSystemCatalogNavigationDirections.Colors,
        )
    }

    override fun navigateToComponentsScreen(): Job {
        return navigate(
            cosmosDesignSystemCatalogNavigationCommand = CosmosDesignSystemCatalogNavigationDirections.Components,
        )
    }

    override fun navigateToIconsScreen(): Job {
        return navigate(
            cosmosDesignSystemCatalogNavigationCommand = CosmosDesignSystemCatalogNavigationDirections.Icons,
        )
    }

    override fun navigateToShapesScreen(): Job {
        return navigate(
            cosmosDesignSystemCatalogNavigationCommand = CosmosDesignSystemCatalogNavigationDirections.Shapes,
        )
    }

    override fun navigateToTypographyScreen(): Job {
        return navigate(
            cosmosDesignSystemCatalogNavigationCommand = CosmosDesignSystemCatalogNavigationDirections.Typography,
        )
    }

    override fun navigateToBarcodeDetailsScreen(
        barcodeId: Int,
    ): Job {
        return navigate(
            cosmosDesignSystemCatalogNavigationCommand = CosmosDesignSystemCatalogNavigationDirections.NavigateUp,
        )
    }

    override fun navigateToCreateBarcodeScreen(
        barcodeId: Int?,
    ): Job {
        return navigate(
            cosmosDesignSystemCatalogNavigationCommand = CosmosDesignSystemCatalogNavigationDirections.NavigateUp,
        )
    }

    override fun navigateToCreditsScreen(): Job {
        return navigate(
            cosmosDesignSystemCatalogNavigationCommand = CosmosDesignSystemCatalogNavigationDirections.NavigateUp,
        )
    }

    override fun navigateToHomeScreen(): Job {
        return navigate(
            cosmosDesignSystemCatalogNavigationCommand = CosmosDesignSystemCatalogNavigationDirections.Home,
        )
    }

    override fun navigateToScanBarcodeScreen(): Job {
        return navigate(
            cosmosDesignSystemCatalogNavigationCommand = CosmosDesignSystemCatalogNavigationDirections.NavigateUp,
        )
    }

    override fun navigateToSettingsScreen(): Job {
        return navigate(
            cosmosDesignSystemCatalogNavigationCommand = CosmosDesignSystemCatalogNavigationDirections.NavigateUp,
        )
    }

    override fun navigateUp(): Job {
        return navigate(
            cosmosDesignSystemCatalogNavigationCommand = CosmosDesignSystemCatalogNavigationDirections.NavigateUp,
        )
    }

    override fun navigateToWebViewScreen(
        url: String,
    ): Job {
        return navigate(
            cosmosDesignSystemCatalogNavigationCommand = CosmosDesignSystemCatalogNavigationDirections.NavigateUp,
        )
    }

    private fun navigate(
        cosmosDesignSystemCatalogNavigationCommand: CosmosDesignSystemCatalogNavigationCommand,
    ): Job {
        return coroutineScope.launch {
            _command.emit(
                value = cosmosDesignSystemCatalogNavigationCommand,
            )
        }
    }
}
