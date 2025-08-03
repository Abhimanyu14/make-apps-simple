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

import com.makeappssimple.abhimanyu.finance.manager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.account.AccountRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.account.AccountRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.category.CategoryRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.preferences.MyPreferencesRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transaction.TransactionRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.transactionfor.TransactionForRepositoryImpl
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.datasource.CommonDataSource
import com.makeappssimple.abhimanyu.finance.manager.android.core.datastore.MyPreferencesDataSource
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
    internal fun providesMyPreferencesRepository(
        dispatcherProvider: DispatcherProvider,
        myPreferencesDataSource: MyPreferencesDataSource,
    ): MyPreferencesRepository {
        return MyPreferencesRepositoryImpl(
            dispatcherProvider = dispatcherProvider,
            myPreferencesDataSource = myPreferencesDataSource,
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
