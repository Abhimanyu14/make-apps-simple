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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.categories.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.navigation.Screen
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.navigation.constants.DeeplinkUrl
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.feature.categories.add_category.screen.AddCategoryScreen
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.feature.categories.categories.screen.CategoriesScreen
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.feature.categories.edit_category.screen.EditCategoryScreen

internal fun NavGraphBuilder.categoriesNavGraph() {
    composable(
        route = "${Screen.AddCategory.route}/{${NavigationArguments.TRANSACTION_TYPE}}",
        arguments = listOf(
            navArgument(NavigationArguments.TRANSACTION_TYPE) {
                type = NavType.StringType
            },
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BROWSER_BASE_URL}/${Screen.AddCategory.route}/{${NavigationArguments.TRANSACTION_TYPE}}"
            },
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BASE_URL}/${Screen.AddCategory.route}/{${NavigationArguments.TRANSACTION_TYPE}}"
            },
        ),
    ) {
        AddCategoryScreen()
    }

    composable(
        route = Screen.Categories.route,
        deepLinks = listOf(
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BROWSER_BASE_URL}/${Screen.Categories.route}"
            },
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BASE_URL}/${Screen.Categories.route}"
            },
        ),
    ) {
        CategoriesScreen()
    }

    composable(
        route = "${Screen.EditCategory.route}/{${NavigationArguments.CATEGORY_ID}}",
        arguments = listOf(
            navArgument(NavigationArguments.CATEGORY_ID) {
                type = NavType.IntType
            },
        ),
    ) {
        EditCategoryScreen()
    }
}
