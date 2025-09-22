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
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.database.dao.AccountDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.database.local.database.FinanceManagerRoomDatabase
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.finance.manager.android.common.core.database.model.AmountEntity
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.ints.shouldBeZero
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
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
import kotlin.test.assertFailsWith
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

            count.shouldBe(
                expected = 1,
            )
            allAccounts.shouldBeEmpty()
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

            count.shouldBeZero()
            allAccounts.size.shouldBe(
                expected = 1,
            )
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

        count.shouldBe(
            expected = 3,
        )
        allAccounts.shouldBeEmpty()
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

        result.shouldBe(
            expected = accountEntity1,
        )
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

        result.shouldBeNull()
    }

    @Test
    fun getAccountsByIds() = runTestWithTimeout {
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

        val result = accountDao.getAccountsByIds(
            listOf(1, 3),
        )

        result.size.shouldBe(
            expected = 1,
        )
        result.first().shouldBe(
            expected = accountEntity1,
        )
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

        result.size.shouldBe(
            expected = 2,
        )
        result.any { it == accountEntity1 }.shouldBeTrue()
        result.any { it == accountEntity2 }.shouldBeTrue()
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

        allAccounts.size.shouldBe(
            expected = 2,
        )
        allAccounts.any { it == accountEntity1 }.shouldBeTrue()
        allAccounts.any { it == accountEntity2 }.shouldBeTrue()
    }

    @Test
    fun insertAccounts_validAccounts_accountsAreInserted() =
        runTestWithTimeout {
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

            insertedAccountIds.size.shouldBe(
                expected = 2,
            )
            insertedAccountIds[0].shouldBe(
                expected = 1,
            )
            insertedAccountIds[1].shouldBe(
                expected = 2,
            )
            allAccounts.size.shouldBe(
                expected = 2,
            )
            allAccounts.any { it == accountEntity1 }.shouldBeTrue()
            allAccounts.any { it == accountEntity2 }.shouldBeTrue()
        }

    @Test
    fun insertAccounts_invalidAccounts_exceptionIsThrown() =
        runTestWithTimeout {
            val accountEntity1 = getAccountEntity(
                id = 1,
            )
            val accountEntity2 = getAccountEntity(
                id = 2,
            )
            val accountEntity3 = getAccountEntity(
                id = 3,
            )

            accountDao.insertAccounts(
                accountEntity1,
                accountEntity2,
            )
            assertFailsWith(
                exceptionClass = SQLiteConstraintException::class,
            ) {
                accountDao.insertAccounts(
                    accountEntity2,
                    accountEntity3,
                )
            }
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

        count.shouldBe(
            expected = 1,
        )
        result.shouldBe(
            expected = updatedAccount,
        )
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

        count.shouldBe(
            expected = 0,
        )
        allAccounts.size.shouldBe(
            expected = 1,
        )
        allAccounts.first().shouldBe(
            expected = accountEntity,
        )
    }

    // region common
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
    // endregion
}
