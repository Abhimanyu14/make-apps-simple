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

package com.makeappssimple.abhimanyu.finance.manager.android.common.di

import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.common.date_time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.repository.account.AccountRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.repository.category.CategoryRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.repository.preferences.FinanceManagerPreferencesRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.repository.transaction.TransactionRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.repository.transaction_data.TransactionDataRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.repository.transaction_data.TransactionDataRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.repository.transaction_for.TransactionForRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.data.repository.transaction_for.TransactionForRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.database.dao.TransactionDataDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.database.datasource.CommonDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.datastore.FinanceManagerPreferencesDataSource
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
public class RepositoryModule {
    @Single
    internal fun providesAccountRepository(
        accountDao: AccountDao,
        dispatcherProvider: DispatcherProvider,
    ): AccountRepository {
        return AccountRepositoryImpl(
            accountDao = accountDao,
            dispatcherProvider = dispatcherProvider,
        )
    }

    @Single
    internal fun providesCategoryRepository(
        categoryDao: CategoryDao,
        dispatcherProvider: DispatcherProvider,
    ): CategoryRepository {
        return CategoryRepositoryImpl(
            categoryDao = categoryDao,
            dispatcherProvider = dispatcherProvider,
        )
    }

    @Single
    internal fun providesFinanceManagerPreferencesRepository(
        dateTimeKit: DateTimeKit,
        dispatcherProvider: DispatcherProvider,
        financeManagerPreferencesDataSource: FinanceManagerPreferencesDataSource,
    ): FinanceManagerPreferencesRepository {
        return FinanceManagerPreferencesRepositoryImpl(
            dateTimeKit = dateTimeKit,
            dispatcherProvider = dispatcherProvider,
            financeManagerPreferencesDataSource = financeManagerPreferencesDataSource,
        )
    }

    @Single
    internal fun providesTransactionRepository(
        commonDataSource: CommonDataSource,
        dispatcherProvider: DispatcherProvider,
        transactionDao: TransactionDao,
    ): TransactionRepository {
        return TransactionRepositoryImpl(
            commonDataSource = commonDataSource,
            dispatcherProvider = dispatcherProvider,
            transactionDao = transactionDao,
        )
    }

    @Single
    internal fun providesTransactionDataRepository(
        commonDataSource: CommonDataSource,
        dispatcherProvider: DispatcherProvider,
        transactionDataDao: TransactionDataDao,
    ): TransactionDataRepository {
        return TransactionDataRepositoryImpl(
            commonDataSource = commonDataSource,
            dispatcherProvider = dispatcherProvider,
            transactionDataDao = transactionDataDao,
        )
    }

    @Single
    internal fun providesTransactionForRepository(
        dispatcherProvider: DispatcherProvider,
        transactionForDao: TransactionForDao,
    ): TransactionForRepository {
        return TransactionForRepositoryImpl(
            dispatcherProvider = dispatcherProvider,
            transactionForDao = transactionForDao,
        )
    }
}
