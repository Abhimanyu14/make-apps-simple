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

package com.makeappssimple.abhimanyu.cosmos.design.system.catalog.di

import com.makeappssimple.abhimanyu.core.log.kit.LogKit
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.app.CosmosDesignSystemCatalogActivityViewModel
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.navigation.CosmosDesignSystemCatalogNavigationKit
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.navigation.CosmosDesignSystemCatalogNavigationKitImpl
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.screens.colors.CosmosDesignSystemCatalogColorsScreenViewModel
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.screens.components.CosmosDesignSystemCatalogComponentsScreenViewModel
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.screens.home.CosmosDesignSystemCatalogHomeScreenViewModel
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.screens.icons.CosmosDesignSystemCatalogIconsScreenViewModel
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.screens.shapes.CosmosDesignSystemCatalogShapesScreenViewModel
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.screens.typography.CosmosDesignSystemCatalogTypographyScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val cosmosDesignSystemCatalogAppModule: Module = module {
    single<CoroutineScope> {
        CoroutineScope(
            context = Dispatchers.Default + SupervisorJob(),
        )
    }

    single<LogKit> {
        object : LogKit {
            override fun logError(
                message: String,
                tag: String,
            ) = Unit
        }
    }

    single<CosmosDesignSystemCatalogNavigationKit> {
        CosmosDesignSystemCatalogNavigationKitImpl(
            coroutineScope = get(),
        )
    }

    viewModel {
        CosmosDesignSystemCatalogActivityViewModel(
            cosmosDesignSystemCatalogNavigationKit = get(),
            logKit = get(),
        )
    }

    viewModel {
        CosmosDesignSystemCatalogColorsScreenViewModel(
            cosmosDesignSystemCatalogNavigationKit = get(),
        )
    }

    viewModel {
        CosmosDesignSystemCatalogComponentsScreenViewModel(
            cosmosDesignSystemCatalogNavigationKit = get(),
        )
    }

    viewModel {
        CosmosDesignSystemCatalogHomeScreenViewModel(
            cosmosDesignSystemCatalogNavigationKit = get(),
        )
    }

    viewModel {
        CosmosDesignSystemCatalogIconsScreenViewModel(
            cosmosDesignSystemCatalogNavigationKit = get(),
        )
    }

    viewModel {
        CosmosDesignSystemCatalogShapesScreenViewModel(
            cosmosDesignSystemCatalogNavigationKit = get(),
        )
    }

    viewModel {
        CosmosDesignSystemCatalogTypographyScreenViewModel(
            cosmosDesignSystemCatalogNavigationKit = get(),
        )
    }
}
