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

package com.makeappssimple.abhimanyu.finance.manager.android.common.di

import com.makeappssimple.abhimanyu.core.coroutines.CoroutineDispatcherProvider
import com.makeappssimple.abhimanyu.core.date.time.DateTimeKit
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.AccountDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.TransactionDataDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.datasource.CommonDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.datastore.FinanceManagerPreferencesDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.repository.account.AccountRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.repository.category.CategoryRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.repository.preferences.FinanceManagerPreferencesRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.repository.transaction.TransactionRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.repository.transaction_data.TransactionDataRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.repository.transaction_for.TransactionForRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.preferences.FinanceManagerPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.transaction_data.TransactionDataRepository
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.repository.transaction_for.TransactionForRepository
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
internal class RepositoryModule {
    @Single
    internal fun providesAccountRepository(
        accountDao: AccountDao,
        coroutineDispatcherProvider: CoroutineDispatcherProvider,
    ): AccountRepository {
        return AccountRepositoryImpl(
            accountDao = accountDao,
            coroutineDispatcherProvider = coroutineDispatcherProvider,
        )
    }

    @Single
    internal fun providesCategoryRepository(
        categoryDao: CategoryDao,
        coroutineDispatcherProvider: CoroutineDispatcherProvider,
    ): CategoryRepository {
        return CategoryRepositoryImpl(
            categoryDao = categoryDao,
            coroutineDispatcherProvider = coroutineDispatcherProvider,
        )
    }

    @Single
    internal fun providesFinanceManagerPreferencesRepository(
        dateTimeKit: DateTimeKit,
        coroutineDispatcherProvider: CoroutineDispatcherProvider,
        financeManagerPreferencesDataSource: FinanceManagerPreferencesDataSource,
    ): FinanceManagerPreferencesRepository {
        return FinanceManagerPreferencesRepositoryImpl(
            dateTimeKit = dateTimeKit,
            coroutineDispatcherProvider = coroutineDispatcherProvider,
            financeManagerPreferencesDataSource = financeManagerPreferencesDataSource,
        )
    }

    @Single
    internal fun providesTransactionRepository(
        commonDataSource: CommonDataSource,
        coroutineDispatcherProvider: CoroutineDispatcherProvider,
        transactionDao: TransactionDao,
    ): TransactionRepository {
        return TransactionRepositoryImpl(
            commonDataSource = commonDataSource,
            coroutineDispatcherProvider = coroutineDispatcherProvider,
            transactionDao = transactionDao,
        )
    }

    @Single
    internal fun providesTransactionDataRepository(
        commonDataSource: CommonDataSource,
        coroutineDispatcherProvider: CoroutineDispatcherProvider,
        transactionDataDao: TransactionDataDao,
    ): TransactionDataRepository {
        return TransactionDataRepositoryImpl(
            commonDataSource = commonDataSource,
            coroutineDispatcherProvider = coroutineDispatcherProvider,
            transactionDataDao = transactionDataDao,
        )
    }

    @Single
    internal fun providesTransactionForRepository(
        coroutineDispatcherProvider: CoroutineDispatcherProvider,
        transactionForDao: TransactionForDao,
    ): TransactionForRepository {
        return TransactionForRepositoryImpl(
            coroutineDispatcherProvider = coroutineDispatcherProvider,
            transactionForDao = transactionForDao,
        )
    }
}
