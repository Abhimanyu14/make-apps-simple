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

internal object FinanceManagerNavigationDirections {
    // Default
    object Default : NavigationCommand {
        override val command: Command = Command.NOOP
        override val destination: String = ""
        override val screen: String = ""
    }

    // Navigate up
    object NavigateUp : NavigationCommand {
        override val command: Command = Command.NAVIGATE_UP
        override val destination: String = ""
        override val screen: String = ""
    }

    // Clear backstack
    object ClearBackstack : NavigationCommand {
        override val command: Command = Command.CLEAR_BACKSTACK_AND_NAVIGATE
        override val destination: String = ""
        override val screen: String = ""
    }

    // Clear till root
    object ClearTillRoot : NavigationCommand {
        override val command: Command = Command.CLEAR_TILL_ROOT
        override val destination: String = ""
        override val screen: String = ""
    }

    // App specific
    object Accounts : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.Accounts.route
        override val screen: String = Screen.Accounts.route
    }

    object AddAccount : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.AddAccount.route
        override val screen: String = Screen.AddAccount.route
    }

    internal data class AddCategory(
        private val transactionType: String,
    ) : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String =
            "${Screen.AddCategory.route}/${transactionType}"
        override val screen: String = Screen.AddCategory.route
    }

    internal data class AddTransaction(
        private val transactionId: Int? = null,
    ) : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String =
            "${Screen.AddTransaction.route}/${transactionId}"
        override val screen: String = Screen.AddTransaction.route
    }

    object AddTransactionFor : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.AddTransactionFor.route
        override val screen: String = Screen.AddTransactionFor.route
    }

    object Analysis : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.Analysis.route
        override val screen: String = Screen.Analysis.route
    }

    object Categories : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.Categories.route
        override val screen: String = Screen.Categories.route
    }

    internal data class EditAccount(
        private val accountId: Int,
    ) : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String =
            "${Screen.EditAccount.route}/${accountId}"
        override val screen: String = Screen.EditAccount.route
    }

    internal data class EditCategory(
        private val categoryId: Int,
    ) : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String =
            "${Screen.EditCategory.route}/${categoryId}"
        override val screen: String = Screen.EditCategory.route
    }

    internal data class EditTransaction(
        private val transactionId: Int,
    ) : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String =
            "${Screen.EditTransaction.route}/${transactionId}"
        override val screen: String = Screen.EditTransaction.route
    }

    internal data class EditTransactionFor(
        private val transactionForId: Int,
    ) : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String =
            "${Screen.EditTransactionFor.route}/${transactionForId}"
        override val screen: String = Screen.EditTransactionFor.route
    }

    object Home : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.Home.route
        override val screen: String = Screen.Home.route
    }

    object OpenSourceLicenses : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.OpenSourceLicenses.route
        override val screen: String = Screen.OpenSourceLicenses.route
    }

    object Settings : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.Settings.route
        override val screen: String = Screen.Settings.route
    }

    object TransactionForValues : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.TransactionForValues.route
        override val screen: String = Screen.TransactionForValues.route
    }

    object Transactions : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String = Screen.Transactions.route
        override val screen: String = Screen.Transactions.route
    }

    internal data class ViewTransaction(
        private val transactionId: Int,
    ) : NavigationCommand {
        override val command: Command = Command.NAVIGATE
        override val destination: String =
            "${Screen.ViewTransaction.route}/${transactionId}"
        override val screen: String = Screen.ViewTransaction.route
    }
}
