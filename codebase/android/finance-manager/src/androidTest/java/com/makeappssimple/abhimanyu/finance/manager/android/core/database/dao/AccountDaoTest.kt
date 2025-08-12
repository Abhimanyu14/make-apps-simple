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

package com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.local.database.FinanceManagerRoomDatabase
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.AmountEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
internal class AccountDaoTest {
    private val testCoroutineDispatcher = StandardTestDispatcher()

    private lateinit var database: FinanceManagerRoomDatabase
    private lateinit var accountDao: AccountDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room
            .inMemoryDatabaseBuilder(
                context = context,
                klass = FinanceManagerRoomDatabase::class.java,
            )
            .allowMainThreadQueries()
            .build()
        accountDao = database.accountDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        database.close()
    }

    @Test
    fun deleteAccountById_validId_returnsCountOfDeletedAccounts() =
        runTestWithTimeout {
            val accountId = 10
            val accountEntity = getAccountEntity(
                id = accountId,
            )
            accountDao.insertAccounts(
                accountEntity,
            )

            val count = accountDao.deleteAccountById(
                id = accountId,
            )
            val allAccounts = accountDao.getAllAccounts()

            assertThat(count).isEqualTo(1)
            assertThat(allAccounts).isEmpty()
        }

    @Test
    fun deleteAccountById_invalidId_returnsCountOfDeletedAccounts() =
        runTestWithTimeout {
            val accountEntity = getAccountEntity(
                id = 10,
            )
            accountDao.insertAccounts(
                accountEntity,
            )

            val count = accountDao.deleteAccountById(
                id = 20,
            )
            val allAccounts = accountDao.getAllAccounts()

            assertThat(count).isEqualTo(0)
            assertThat(allAccounts.size).isEqualTo(1)
        }

    @Test
    fun deleteAllAccounts() = runTestWithTimeout {
        accountDao.insertAccounts(
            getAccountEntity(
                id = 1,
            ),
            getAccountEntity(
                id = 2,
            ),
            getAccountEntity(
                id = 3,
            ),
        )

        val count = accountDao.deleteAllAccounts()
        val allAccounts = accountDao.getAllAccounts()

        assertThat(count).isEqualTo(3)
        assertThat(allAccounts).isEmpty()
    }

    @Test
    fun getAccountById_validId() = runTestWithTimeout {
        val accountEntity1 = getAccountEntity(
            id = 1,
        )
        val accountEntity2 = getAccountEntity(
            id = 2,
        )
        accountDao.insertAccounts(
            accountEntity1,
            accountEntity2,
        )

        val result = accountDao.getAccountById(
            id = 1,
        )

        assertThat(result).isEqualTo(accountEntity1)
    }

    @Test
    fun getAccountById_invalidId() = runTestWithTimeout {
        val accountEntity1 = getAccountEntity(
            id = 1,
        )
        val accountEntity2 = getAccountEntity(
            id = 2,
        )
        accountDao.insertAccounts(
            accountEntity1,
            accountEntity2,
        )

        val result = accountDao.getAccountById(
            id = 3,
        )

        assertThat(result).isEqualTo(null)
    }

    @Test
    fun getAccounts() = runTestWithTimeout {
        val accountEntity1 = getAccountEntity(
            id = 1,
        )
        val accountEntity2 = getAccountEntity(
            id = 2,
        )
        accountDao.insertAccounts(
            accountEntity1,
            accountEntity2,
        )

        val result = accountDao.getAccounts(
            listOf(1, 3),
        )

        assertThat(result.size).isEqualTo(1)
        assertThat(result.first()).isEqualTo(accountEntity1)
    }

    @Test
    fun getAllAccounts() = runTestWithTimeout {
        val accountEntity1 = getAccountEntity(
            id = 1,
        )
        val accountEntity2 = getAccountEntity(
            id = 2,
        )
        accountDao.insertAccounts(
            accountEntity1,
            accountEntity2,
        )

        val result = accountDao.getAllAccounts()

        assertThat(result.size).isEqualTo(2)
        assertThat(result.any { it == accountEntity1 }).isTrue()
        assertThat(result.any { it == accountEntity2 }).isTrue()
    }

    @Test
    fun getAllAccountsFlow() = runTestWithTimeout {
        val accountEntity1 = getAccountEntity(
            id = 1,
        )
        val accountEntity2 = getAccountEntity(
            id = 2,
        )
        accountDao.insertAccounts(
            accountEntity1,
            accountEntity2,
        )

        val allAccountsFlow = accountDao.getAllAccountsFlow()
        val allAccounts = allAccountsFlow.first()

        assertThat(allAccounts.size).isEqualTo(2)
        assertThat(allAccounts.any { it == accountEntity1 }).isTrue()
        assertThat(allAccounts.any { it == accountEntity2 }).isTrue()
    }

    @Test
    fun insertAccounts() = runTestWithTimeout {
        val accountEntity1 = getAccountEntity(
            id = 1,
        )
        val accountEntity2 = getAccountEntity(
            id = 2,
        )

        val insertedAccountIds = accountDao.insertAccounts(
            accountEntity1,
            accountEntity2,
        )
        val allAccounts = accountDao.getAllAccounts()

        assertThat(insertedAccountIds.size).isEqualTo(2)
        assertThat(insertedAccountIds[0]).isEqualTo(1)
        assertThat(insertedAccountIds[1]).isEqualTo(2)
        assertThat(allAccounts.size).isEqualTo(2)
        assertThat(allAccounts.any { it == accountEntity1 }).isTrue()
        assertThat(allAccounts.any { it == accountEntity2 }).isTrue()
    }

    @Test
    fun updateAccounts() = runTestWithTimeout {
        val accountEntity = getAccountEntity(
            id = 1,
        )
        accountDao.insertAccounts(
            accountEntity,
        )
        val insertedAccount = accountDao.getAllAccounts().first()
        val updatedAccount = insertedAccount.copy(
            name = "Updated Name",
        )

        val count = accountDao.updateAccounts(
            updatedAccount,
        )
        val result = accountDao.getAccountById(
            id = insertedAccount.id,
        )

        assertThat(count).isEqualTo(1)
        assertThat(result).isEqualTo(updatedAccount)
    }

    @Test
    fun updateAccounts_idChanged() = runTestWithTimeout {
        val accountEntity = getAccountEntity(
            id = 1,
        )
        accountDao.insertAccounts(
            accountEntity,
        )
        val insertedAccount = accountDao.getAllAccounts().first()
        val updatedAccount = insertedAccount.copy(
            id = 2,
        )

        val count = accountDao.updateAccounts(
            updatedAccount,
        )
        val allAccounts = accountDao.getAllAccounts()

        assertThat(count).isEqualTo(0)
        assertThat(allAccounts.size).isEqualTo(1)
        assertThat(allAccounts.first()).isEqualTo(accountEntity)
    }

    private fun getAccountEntity(
        id: Int = 0,
        name: String = "test-account",
        balanceAmount: AmountEntity = AmountEntity(),
    ): AccountEntity {
        return AccountEntity(
            id = id,
            name = name,
            balanceAmount = balanceAmount,
        )
    }

    private fun runTestWithTimeout(
        testBody: suspend TestScope.() -> Unit,
    ) {
        runTest(
            context = testCoroutineDispatcher,
            timeout = 3.seconds,
        ) {
            testBody()
        }
    }
}
