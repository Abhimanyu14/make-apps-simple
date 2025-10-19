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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.navigation

public sealed class Screen(
    public val route: String,
) {
    public data object Accounts : Screen(
        route = "accounts",
    )

    public data object AddAccount : Screen(
        route = "add_account",
    )

    public data object AddCategory : Screen(
        route = "add_category",
    )

    public data object AddTransaction : Screen(
        route = "add_transaction",
    )

    public data object AddTransactionFor : Screen(
        route = "add_transaction_for",
    )

    public data object Analysis : Screen(
        route = "analysis",
    )

    public data object Categories : Screen(
        route = "categories",
    )

    public data object EditAccount : Screen(
        route = "edit_account",
    )

    public data object EditCategory : Screen(
        route = "edit_category",
    )

    public data object EditTransaction : Screen(
        route = "edit_transaction",
    )

    public data object EditTransactionFor : Screen(
        route = "edit_transaction_for",
    )

    public data object Home : Screen(
        route = "home",
    )

    public data object OpenSourceLicenses : Screen(
        route = "open_source_licenses",
    )

    public data object Settings : Screen(
        route = "settings",
    )

    public data object TransactionForValues : Screen(
        route = "transaction_for_values",
    )

    public data object Transactions : Screen(
        route = "transactions",
    )

    public data object ViewTransaction : Screen(
        route = "view_transaction",
    )
}
