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

package com.makeappssimple.abhimanyu.finance.manager.android.core.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

public class NavigationKitImpl(
    private val coroutineScope: CoroutineScope,
) : NavigationKit {
    private val _command: MutableSharedFlow<NavigationCommand> =
        MutableSharedFlow()
    override val command: SharedFlow<NavigationCommand> = _command

    override fun navigateToAccountsScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.Accounts,
        )
    }

    override fun navigateToAddAccountScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.AddAccount,
        )
    }

    override fun navigateToAddCategoryScreen(
        transactionType: String,
    ): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.AddCategory(
                transactionType = transactionType,
            ),
        )
    }

    override fun navigateToAddTransactionScreen(
        transactionId: Int?,
    ): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.AddTransaction(
                transactionId = transactionId,
            ),
        )
    }

    override fun navigateToAddTransactionForScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.AddTransactionFor,
        )
    }

    override fun navigateToAnalysisScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.Analysis,
        )
    }

    override fun navigateToCategoriesScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.Categories,
        )
    }

    override fun navigateToEditAccountScreen(
        accountId: Int,
    ): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.EditAccount(
                accountId = accountId,
            ),
        )
    }

    override fun navigateToEditCategoryScreen(
        categoryId: Int,
    ): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.EditCategory(
                categoryId = categoryId,
            ),
        )
    }

    override fun navigateToEditTransactionScreen(
        transactionId: Int,
    ): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.EditTransaction(
                transactionId = transactionId,
            ),
        )
    }

    override fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    ): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.EditTransactionFor(
                transactionForId = transactionForId,
            ),
        )
    }

    override fun navigateToHomeScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.Home,
        )
    }

    override fun navigateToOpenSourceLicensesScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.OpenSourceLicenses,
        )
    }

    override fun navigateToSettingsScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.Settings,
        )
    }

    override fun navigateToTransactionForValuesScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.TransactionForValues,
        )
    }

    override fun navigateToTransactionsScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.Transactions,
        )
    }

    override fun navigateUp(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.NavigateUp,
        )
    }

    override fun navigateToViewTransactionScreen(
        transactionId: Int,
    ): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.ViewTransaction(
                transactionId = transactionId,
            ),
        )
    }

    private fun navigate(
        navigationCommand: NavigationCommand,
    ): Job {
        return coroutineScope.launch {
            _command.emit(
                value = navigationCommand,
            )
        }
    }
}
