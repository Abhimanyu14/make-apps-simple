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

package com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.constants.DeeplinkUrl
import com.makeappssimple.abhimanyu.finance.manager.android.core.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.screen.AddTransactionScreen
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.edit_transaction.screen.EditTransactionScreen
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.transactions.screen.TransactionsScreen
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.view_transaction.screen.ViewTransactionScreen

public fun NavGraphBuilder.transactionsNavGraph() {
    composable(
        route = "${Screen.AddTransaction.route}/{${NavigationArguments.TRANSACTION_ID}}",
        arguments = listOf(
            navArgument(NavigationArguments.TRANSACTION_ID) {
                type = NavType.StringType
                nullable = true
            },
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BROWSER_BASE_URL}/${Screen.AddTransaction.route}/${NavigationArguments.TRANSACTION_ID}"
            },
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BASE_URL}/${Screen.AddTransaction.route}/${NavigationArguments.TRANSACTION_ID}"
            },
        ),
    ) {
        AddTransactionScreen()
    }

    composable(
        route = "${Screen.EditTransaction.route}/{${NavigationArguments.TRANSACTION_ID}}",
        arguments = listOf(
            navArgument(NavigationArguments.TRANSACTION_ID) {
                type = NavType.StringType
            },
        ),
    ) {
        EditTransactionScreen()
    }

    composable(
        route = Screen.Transactions.route,
        deepLinks = listOf(
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BROWSER_BASE_URL}/${Screen.Transactions.route}"
            },
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BASE_URL}/${Screen.Transactions.route}"
            },
        ),
    ) {
        TransactionsScreen()
    }

    composable(
        route = "${Screen.ViewTransaction.route}/{${NavigationArguments.TRANSACTION_ID}}",
        arguments = listOf(
            navArgument(NavigationArguments.TRANSACTION_ID) {
                type = NavType.StringType
            },
        ),
    ) {
        ViewTransactionScreen()
    }
}
