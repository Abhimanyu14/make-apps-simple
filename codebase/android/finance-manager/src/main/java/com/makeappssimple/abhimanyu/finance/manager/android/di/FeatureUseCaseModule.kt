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

package com.makeappssimple.abhimanyu.finance.manager.android.di

import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction.CheckIfAccountIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.use_case.transaction_for.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.use_case.GetAllAccountsListItemDataListUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.use_case.GetDefaultAccountIdFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.use_case.GetIsAccountsUsedInTransactionFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.use_case.AddAccountScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.use_case.EditAccountScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.use_case.AddCategoryScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.use_case.EditCategoryScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.add_transaction_for.use_case.AddTransactionForScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transaction_for.edit_transaction_for.use_case.EditTransactionForScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.use_case.AddTransactionScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.edit_transaction.use_case.EditTransactionScreenDataValidationUseCase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
public class FeatureUseCaseModule {
    // region accounts
    @Single
    internal fun providesGetAllAccountsListItemDataListUseCase(
        checkIfAccountIsUsedInTransactionsUseCase: CheckIfAccountIsUsedInTransactionsUseCase,
    ): GetAllAccountsListItemDataListUseCase {
        return GetAllAccountsListItemDataListUseCase(
            checkIfAccountIsUsedInTransactionsUseCase = checkIfAccountIsUsedInTransactionsUseCase,
        )
    }

    @Single
    internal fun providesGetDefaultAccountIdFlowUseCase(
        financeManagerPreferencesRepository: FinanceManagerPreferencesRepository,
    ): GetDefaultAccountIdFlowUseCase {
        return GetDefaultAccountIdFlowUseCase(
            financeManagerPreferencesRepository = financeManagerPreferencesRepository,
        )
    }

    @Single
    internal fun providesGetIsAccountsUsedInTransactionFlowUseCase(
        checkIfAccountIsUsedInTransactionsUseCase: CheckIfAccountIsUsedInTransactionsUseCase,
    ): GetIsAccountsUsedInTransactionFlowUseCase {
        return GetIsAccountsUsedInTransactionFlowUseCase(
            checkIfAccountIsUsedInTransactionsUseCase = checkIfAccountIsUsedInTransactionsUseCase,
        )
    }

    @Single
    internal fun providesAddAccountScreenDataValidationUseCase(
        getAllAccountsUseCase: GetAllAccountsUseCase,
    ): AddAccountScreenDataValidationUseCase {
        return AddAccountScreenDataValidationUseCase(
            getAllAccountsUseCase = getAllAccountsUseCase,
        )
    }

    @Single
    internal fun providesEditAccountScreenDataValidationUseCase(
        getAllAccountsUseCase: GetAllAccountsUseCase,
    ): EditAccountScreenDataValidationUseCase {
        return EditAccountScreenDataValidationUseCase(
            getAllAccountsUseCase = getAllAccountsUseCase,
        )
    }
    // endregion

    // region categories
    @Single
    internal fun providesAddCategoryScreenDataValidationUseCase(
        getAllCategoriesUseCase: GetAllCategoriesUseCase,
    ): AddCategoryScreenDataValidationUseCase {
        return AddCategoryScreenDataValidationUseCase(
            getAllCategoriesUseCase = getAllCategoriesUseCase,
        )
    }

    @Single
    internal fun providesEditCategoryScreenDataValidationUseCase(
        getAllCategoriesUseCase: GetAllCategoriesUseCase,
    ): EditCategoryScreenDataValidationUseCase {
        return EditCategoryScreenDataValidationUseCase(
            getAllCategoriesUseCase = getAllCategoriesUseCase,
        )
    }
    // endregion

    // region transaction for
    @Single
    internal fun providesAddTransactionForScreenDataValidationUseCase(
        getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    ): AddTransactionForScreenDataValidationUseCase {
        return AddTransactionForScreenDataValidationUseCase(
            getAllTransactionForValuesUseCase = getAllTransactionForValuesUseCase,
        )
    }

    @Single
    internal fun providesEditTransactionForScreenDataValidationUseCase(
        getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    ): EditTransactionForScreenDataValidationUseCase {
        return EditTransactionForScreenDataValidationUseCase(
            getAllTransactionForValuesUseCase = getAllTransactionForValuesUseCase,
        )
    }
    // endregion

    // region transactions
    @Single
    internal fun providesAddTransactionScreenDataValidationUseCase(): AddTransactionScreenDataValidationUseCase {
        return AddTransactionScreenDataValidationUseCase()
    }

    @Single
    internal fun providesEditTransactionScreenDataValidationUseCase(): EditTransactionScreenDataValidationUseCase {
        return EditTransactionScreenDataValidationUseCase()
    }
    // endregion
}
