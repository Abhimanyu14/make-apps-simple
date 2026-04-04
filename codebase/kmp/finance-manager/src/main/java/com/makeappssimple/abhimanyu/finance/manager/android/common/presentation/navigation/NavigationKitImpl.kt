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

package com.makeappssimple.abhimanyu.finance.manager.android.common.presentation.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

internal class NavigationKitImpl(
    private val coroutineScope: CoroutineScope,
) : NavigationKit {
    private val _command: MutableSharedFlow<NavigationCommand> =
        MutableSharedFlow(
            replay = 0,
            extraBufferCapacity = 1,
        )
    override val command: SharedFlow<NavigationCommand> = _command

    override fun navigateToAccountsScreen(): Job {
        return navigate(
            navigationCommand = FinanceManagerNavigationDirections.Accounts,
        )
    }

    override fun navigateToAddAccountScreen(): Job {
        return navigate(
            navigationCommand = FinanceManagerNavigationDirections.AddAccount,
        )
    }

    override fun navigateToAddCategoryScreen(
        transactionType: String,
    ): Job {
        return navigate(
            navigationCommand = FinanceManagerNavigationDirections.AddCategory(
                transactionType = transactionType,
            ),
        )
    }

    override fun navigateToAddTransactionScreen(
        transactionId: Int?,
    ): Job {
        return navigate(
            navigationCommand = FinanceManagerNavigationDirections.AddTransaction(
                transactionId = transactionId,
            ),
        )
    }

    override fun navigateToAddTransactionForScreen(): Job {
        return navigate(
            navigationCommand = FinanceManagerNavigationDirections.AddTransactionFor,
        )
    }

    override fun navigateToAnalysisScreen(): Job {
        return navigate(
            navigationCommand = FinanceManagerNavigationDirections.Analysis,
        )
    }

    override fun navigateToCategoriesScreen(): Job {
        return navigate(
            navigationCommand = FinanceManagerNavigationDirections.Categories,
        )
    }

    override fun navigateToEditAccountScreen(
        accountId: Int,
    ): Job {
        return navigate(
            navigationCommand = FinanceManagerNavigationDirections.EditAccount(
                accountId = accountId,
            ),
        )
    }

    override fun navigateToEditCategoryScreen(
        categoryId: Int,
    ): Job {
        return navigate(
            navigationCommand = FinanceManagerNavigationDirections.EditCategory(
                categoryId = categoryId,
            ),
        )
    }

    override fun navigateToEditTransactionScreen(
        transactionId: Int,
    ): Job {
        return navigate(
            navigationCommand = FinanceManagerNavigationDirections.EditTransaction(
                transactionId = transactionId,
            ),
        )
    }

    override fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    ): Job {
        return navigate(
            navigationCommand = FinanceManagerNavigationDirections.EditTransactionFor(
                transactionForId = transactionForId,
            ),
        )
    }

    override fun navigateToHomeScreen(): Job {
        return navigate(
            navigationCommand = FinanceManagerNavigationDirections.Home,
        )
    }

    override fun navigateToOpenSourceLicensesScreen(): Job {
        return navigate(
            navigationCommand = FinanceManagerNavigationDirections.OpenSourceLicenses,
        )
    }

    override fun navigateToSettingsScreen(): Job {
        return navigate(
            navigationCommand = FinanceManagerNavigationDirections.Settings,
        )
    }

    override fun navigateToTransactionForValuesScreen(): Job {
        return navigate(
            navigationCommand = FinanceManagerNavigationDirections.TransactionForValues,
        )
    }

    override fun navigateToTransactionsScreen(): Job {
        return navigate(
            navigationCommand = FinanceManagerNavigationDirections.Transactions,
        )
    }

    override fun navigateUp(): Job {
        return navigate(
            navigationCommand = FinanceManagerNavigationDirections.NavigateUp,
        )
    }

    override fun navigateToViewTransactionScreen(
        transactionId: Int,
    ): Job {
        return navigate(
            navigationCommand = FinanceManagerNavigationDirections.ViewTransaction(
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
