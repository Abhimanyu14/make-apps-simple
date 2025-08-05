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
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.usecase.transaction.CheckIfAccountIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.usecase.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.usecase.GetAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.usecase.GetAllAccountsListItemDataListUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.usecase.GetDefaultAccountIdFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.accounts.usecase.GetIsAccountsUsedInTransactionFlowUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.add_account.usecase.AddAccountScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.accounts.edit_account.usecase.EditAccountScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.add_category.usecase.AddCategoryScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.categories.edit_category.usecase.EditCategoryScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactionfor.add_transaction_for.usecase.AddTransactionForScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactionfor.edit_transaction_for.usecase.EditTransactionForScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.add_transaction.usecase.AddTransactionScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.finance.manager.android.feature.transactions.edit_transaction.usecase.EditTransactionScreenDataValidationUseCase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
public class FeatureUseCaseModule {
    // region accounts
    @Single
    internal fun providesGetAccountsTotalBalanceAmountValueUseCase(): GetAccountsTotalBalanceAmountValueUseCase {
        return GetAccountsTotalBalanceAmountValueUseCase()
    }

    @Single
    internal fun providesGetAccountsTotalMinimumBalanceAmountValueUseCase(): GetAccountsTotalMinimumBalanceAmountValueUseCase {
        return GetAccountsTotalMinimumBalanceAmountValueUseCase()
    }

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
    internal fun providesAddAccountScreenDataValidationUseCase(): AddAccountScreenDataValidationUseCase {
        return AddAccountScreenDataValidationUseCase()
    }

    @Single
    internal fun providesEditAccountScreenDataValidationUseCase(): EditAccountScreenDataValidationUseCase {
        return EditAccountScreenDataValidationUseCase()
    }
    // endregion

    // region categories
    @Single
    internal fun providesAddCategoryScreenDataValidationUseCase(): AddCategoryScreenDataValidationUseCase {
        return AddCategoryScreenDataValidationUseCase()
    }

    @Single
    internal fun providesEditCategoryScreenDataValidationUseCase(): EditCategoryScreenDataValidationUseCase {
        return EditCategoryScreenDataValidationUseCase()
    }
    // endregion

    // region transaction for
    @Single
    internal fun providesAddTransactionForScreenDataValidationUseCase(): AddTransactionForScreenDataValidationUseCase {
        return AddTransactionForScreenDataValidationUseCase()
    }

    @Single
    internal fun providesEditTransactionForScreenDataValidationUseCase(): EditTransactionForScreenDataValidationUseCase {
        return EditTransactionForScreenDataValidationUseCase()
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
