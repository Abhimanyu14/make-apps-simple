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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.makeappssimple.abhimanyu.cosmos.design.system.catalog.android.activity.CosmosDesignSystemCatalogActivityViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CosmosDesignSystemCatalogNavGraph(
    cosmosDesignSystemCatalogActivityViewModel: CosmosDesignSystemCatalogActivityViewModel = koinViewModel(),
    navHostController: NavHostController = rememberNavController(),
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(
        key1 = lifecycle,
    ) {
        lifecycle.repeatOnLifecycle(
            state = Lifecycle.State.STARTED,
        ) {
            cosmosDesignSystemCatalogActivityViewModel.cosmosDesignSystemCatalogNavigationKit.command.collect { command ->
                keyboardController?.hide()
                when (command.cosmosDesignSystemCatalogCommand) {
                    CosmosDesignSystemCatalogCommand.NAVIGATE -> {
                        navHostController.navigate(
                            route = command.destination,
                        )
                    }

                    CosmosDesignSystemCatalogCommand.NAVIGATE_UP -> {
                        navHostController.navigateUp()
                    }

                    CosmosDesignSystemCatalogCommand.CLEAR_BACKSTACK_AND_NAVIGATE -> {
                        navHostController.navigate(
                            route = command.destination,
                        ) {
                            popUpTo(
                                id = navHostController.graph.findStartDestination().id,
                            ) {
                                inclusive = true
                            }
                        }
                    }

                    CosmosDesignSystemCatalogCommand.CLEAR_TILL_ROOT -> {
                        navHostController.popBackStack(
                            destinationId = navHostController.graph.findStartDestination().id,
                            inclusive = false,
                        )
                    }

                    CosmosDesignSystemCatalogCommand.NOOP -> {}
                }
            }
        }
    }

    CosmosDesignSystemCatalogNavHost(
        navHostController = navHostController,
    )
}
