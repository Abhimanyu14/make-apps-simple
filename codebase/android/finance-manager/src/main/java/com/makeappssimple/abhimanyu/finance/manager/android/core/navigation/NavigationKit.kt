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

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

public interface NavigationKit {
    public val command: SharedFlow<NavigationCommand>

    public fun navigateToAccountsScreen(): Job

    public fun navigateToAddAccountScreen(): Job

    public fun navigateToAddCategoryScreen(
        transactionType: String,
    ): Job

    public fun navigateToAddTransactionScreen(
        transactionId: Int? = null,
    ): Job

    public fun navigateToAddTransactionForScreen(): Job

    public fun navigateToAnalysisScreen(): Job

    public fun navigateToCategoriesScreen(): Job

    public fun navigateToEditAccountScreen(
        accountId: Int,
    ): Job

    public fun navigateToEditCategoryScreen(
        categoryId: Int,
    ): Job

    public fun navigateToEditTransactionScreen(
        transactionId: Int,
    ): Job

    public fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    ): Job

    public fun navigateToHomeScreen(): Job

    public fun navigateToOpenSourceLicensesScreen(): Job

    public fun navigateToSettingsScreen(): Job

    public fun navigateToTransactionForValuesScreen(): Job

    public fun navigateToTransactionsScreen(): Job

    public fun navigateUp(): Job

    public fun navigateToViewTransactionScreen(
        transactionId: Int,
    ): Job
}
