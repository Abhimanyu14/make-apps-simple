/*
 * Copyright 2025-2025 Abhimanyu
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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.feature.transaction_for.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.navigation.Screen
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.navigation.constants.DeeplinkUrl
import com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.feature.transaction_for.add_transaction_for.screen.AddTransactionForScreen
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.feature.transaction_for.edit_transaction_for.screen.EditTransactionForScreen
import com.makeappssimple.abhimanyu.finance.manager.android.common.ui.feature.transaction_for.transaction_for_values.screen.TransactionForValuesScreen

internal fun NavGraphBuilder.transactionForNavGraph() {
    composable(
        route = Screen.AddTransactionFor.route,
        deepLinks = listOf(
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BROWSER_BASE_URL}/${Screen.AddTransactionFor.route}"
            },
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BASE_URL}/${Screen.AddTransactionFor.route}"
            },
        ),
    ) {
        AddTransactionForScreen()
    }

    composable(
        route = "${Screen.EditTransactionFor.route}/{${NavigationArguments.TRANSACTION_FOR_ID}}",
        arguments = listOf(
            navArgument(NavigationArguments.TRANSACTION_FOR_ID) {
                type = NavType.IntType
            },
        ),
    ) {
        EditTransactionForScreen()
    }

    composable(
        route = Screen.TransactionForValues.route,
        deepLinks = listOf(
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BROWSER_BASE_URL}/${Screen.TransactionForValues.route}"
            },
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BASE_URL}/${Screen.TransactionForValues.route}"
            },
        ),
    ) {
        TransactionForValuesScreen()
    }
}
