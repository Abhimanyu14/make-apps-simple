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

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

internal interface NavigationKit {
    val command: SharedFlow<NavigationCommand>

    fun navigateToAccountsScreen(): Job

    fun navigateToAddAccountScreen(): Job

    fun navigateToAddCategoryScreen(
        transactionType: String,
    ): Job

    fun navigateToAddTransactionScreen(
        transactionId: Int? = null,
    ): Job

    fun navigateToAddTransactionForScreen(): Job

    fun navigateToAnalysisScreen(): Job

    fun navigateToCategoriesScreen(): Job

    fun navigateToEditAccountScreen(
        accountId: Int,
    ): Job

    fun navigateToEditCategoryScreen(
        categoryId: Int,
    ): Job

    fun navigateToEditTransactionScreen(
        transactionId: Int,
    ): Job

    fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    ): Job

    fun navigateToHomeScreen(): Job

    fun navigateToOpenSourceLicensesScreen(): Job

    fun navigateToSettingsScreen(): Job

    fun navigateToTransactionForValuesScreen(): Job

    fun navigateToTransactionsScreen(): Job

    fun navigateUp(): Job

    fun navigateToViewTransactionScreen(
        transactionId: Int,
    ): Job
}
