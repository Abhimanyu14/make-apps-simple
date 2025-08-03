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

import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.TransactionForDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.local.database.MyRoomDatabase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
public class DaosModule {
    @Single
    internal fun providesCategoryDao(
        myRoomDatabase: MyRoomDatabase,
    ): CategoryDao {
        return myRoomDatabase.categoryDao()
    }

    @Single
    internal fun providesAccountDao(
        myRoomDatabase: MyRoomDatabase,
    ): AccountDao {
        return myRoomDatabase.accountDao()
    }

    @Single
    internal fun providesTransactionDao(
        myRoomDatabase: MyRoomDatabase,
    ): TransactionDao {
        return myRoomDatabase.transactionDao()
    }

    @Single
    internal fun providesTransactionForDao(
        myRoomDatabase: MyRoomDatabase,
    ): TransactionForDao {
        return myRoomDatabase.transactionForDao()
    }
}
