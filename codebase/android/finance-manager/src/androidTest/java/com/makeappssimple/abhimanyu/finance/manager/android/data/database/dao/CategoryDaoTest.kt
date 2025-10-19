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

package com.makeappssimple.abhimanyu.finance.manager.android.data.database.dao

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.local.database.FinanceManagerRoomDatabase
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.TransactionType
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
internal class CategoryDaoTest {
    private val testCoroutineDispatcher = StandardTestDispatcher()

    private lateinit var database: FinanceManagerRoomDatabase
    private lateinit var categoryDao: CategoryDao

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
        categoryDao = database.categoryDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        database.close()
    }

    @Test
    fun deleteCategoryById_validId_returnsCountOfDeletedCategories() =
        runTestWithTimeout {
            val categoryId = 10
            val categoryEntity = getCategoryEntity(
                id = categoryId,
            )
            categoryDao.insertCategories(
                categoryEntity,
            )

            val count = categoryDao.deleteCategoryById(
                id = categoryId,
            )
            val allCategories = categoryDao.getAllCategories()

            count.shouldBe(
                expected = 1,
            )
            allCategories.shouldBeEmpty()
        }

    @Test
    fun deleteCategoryById_invalidId_returnsCountOfDeletedCategories() =
        runTestWithTimeout {
            val categoryEntity = getCategoryEntity(
                id = 10,
            )
            categoryDao.insertCategories(
                categoryEntity,
            )

            val count = categoryDao.deleteCategoryById(
                id = 20,
            )
            val allCategories = categoryDao.getAllCategories()

            count.shouldBeZero()
            allCategories.size.shouldBe(
                expected = 1,
            )
        }

    @Test
    fun deleteCategories() = runTestWithTimeout {
        val categoryEntity1 = getCategoryEntity(
            id = 1,
        )
        val categoryEntity2 = getCategoryEntity(
            id = 2,
        )
        val categoryEntity3 = getCategoryEntity(
            id = 3,
        )
        categoryDao.insertCategories(
            categoryEntity1,
            categoryEntity2,
            categoryEntity3,
        )

        val count = categoryDao.deleteCategories(
            categoryEntity1,
            categoryEntity3,
        )
        val allCategories = categoryDao.getAllCategories()

        count.shouldBe(
            expected = 2,
        )
        allCategories.size.shouldBe(
            expected = 1,
        )
        allCategories.first().shouldBe(
            expected = categoryEntity2,
        )
    }

    @Test
    fun deleteAllCategories() = runTestWithTimeout {
        categoryDao.insertCategories(
            getCategoryEntity(
                id = 1,
            ),
            getCategoryEntity(
                id = 2,
            ),
            getCategoryEntity(
                id = 3,
            ),
        )

        val count = categoryDao.deleteAllCategories()
        val allCategories = categoryDao.getAllCategories()

        count.shouldBe(
            expected = 3,
        )
        allCategories.shouldBeEmpty()
    }

    @Test
    fun getCategoryById_validId() = runTestWithTimeout {
        val categoryEntity1 = getCategoryEntity(
            id = 1,
        )
        val categoryEntity2 = getCategoryEntity(
            id = 2,
        )
        categoryDao.insertCategories(
            categoryEntity1,
            categoryEntity2,
        )

        val result = categoryDao.getCategoryById(
            id = 1,
        )

        result.shouldBe(
            expected = categoryEntity1,
        )
    }

    @Test
    fun getCategoryById_invalidId() = runTestWithTimeout {
        val categoryEntity1 = getCategoryEntity(
            id = 1,
        )
        val categoryEntity2 = getCategoryEntity(
            id = 2,
        )
        categoryDao.insertCategories(
            categoryEntity1,
            categoryEntity2,
        )

        val result = categoryDao.getCategoryById(
            id = 3,
        )

        result.shouldBeNull()
    }

    @Test
    fun getAllCategories() = runTestWithTimeout {
        val categoryEntity1 = getCategoryEntity(
            id = 1,
        )
        val categoryEntity2 = getCategoryEntity(
            id = 2,
        )
        categoryDao.insertCategories(
            categoryEntity1,
            categoryEntity2,
        )

        val result = categoryDao.getAllCategories()

        result.size.shouldBe(
            expected = 2,
        )
        result.any { it == categoryEntity1 }.shouldBeTrue()
        result.any { it == categoryEntity2 }.shouldBeTrue()
    }

    @Test
    fun getAllCategoriesFlow() = runTestWithTimeout {
        val categoryEntity1 = getCategoryEntity(
            id = 1,
        )
        val categoryEntity2 = getCategoryEntity(
            id = 2,
        )
        categoryDao.insertCategories(
            categoryEntity1,
            categoryEntity2,
        )

        val allCategoriesFlow = categoryDao.getAllCategoriesFlow()
        val allCategories = allCategoriesFlow.first()

        allCategories.size.shouldBe(
            expected = 2,
        )
        allCategories.any { it == categoryEntity1 }.shouldBeTrue()
        allCategories.any { it == categoryEntity2 }.shouldBeTrue()
    }

    @Test
    fun insertCategories_validCategories_categoriesAreInserted() =
        runTestWithTimeout {
            val categoryEntity1 = getCategoryEntity(
                id = 1,
            )
            val categoryEntity2 = getCategoryEntity(
                id = 2,
            )

            val insertedCategoryIds = categoryDao.insertCategories(
                categoryEntity1,
                categoryEntity2,
            )
            val allCategories = categoryDao.getAllCategories()

            insertedCategoryIds.size.shouldBe(
                expected = 2,
            )
            insertedCategoryIds[0].shouldBe(
                expected = 1,
            )
            insertedCategoryIds[1].shouldBe(
                expected = 2,
            )
            allCategories.size.shouldBe(
                expected = 2,
            )
            allCategories.any { it == categoryEntity1 }.shouldBeTrue()
            allCategories.any { it == categoryEntity2 }.shouldBeTrue()
        }

    @Test
    fun insertCategories_invalidCategories_exceptionIsThrown() =
        runTestWithTimeout {
            val categoryEntity1 = getCategoryEntity(
                id = 1,
            )
            val categoryEntity2 = getCategoryEntity(
                id = 2,
            )
            val categoryEntity3 = getCategoryEntity(
                id = 3,
            )

            categoryDao.insertCategories(
                categoryEntity1,
                categoryEntity2,
            )
            assertFailsWith(
                exceptionClass = SQLiteConstraintException::class,
            ) {
                categoryDao.insertCategories(
                    categoryEntity2,
                    categoryEntity3,
                )
            }
        }

    @Test
    fun updateCategories() = runTestWithTimeout {
        val categoryEntity = getCategoryEntity(
            id = 1,
        )
        categoryDao.insertCategories(
            categoryEntity,
        )
        val insertedCategory = categoryDao.getAllCategories().first()
        val updatedCategory = insertedCategory.copy(
            title = "Updated Title",
        )

        val count = categoryDao.updateCategories(
            updatedCategory,
        )
        val result = categoryDao.getCategoryById(
            id = insertedCategory.id,
        )

        count.shouldBe(
            expected = 1,
        )
        result.shouldBe(
            expected = updatedCategory,
        )
    }

    @Test
    fun updateCategories_idChanged() = runTestWithTimeout {
        val categoryEntity = getCategoryEntity(
            id = 1,
        )
        categoryDao.insertCategories(
            categoryEntity,
        )
        val insertedCategory = categoryDao.getAllCategories().first()
        val updatedCategory = insertedCategory.copy(
            id = 2,
        )

        val count = categoryDao.updateCategories(
            updatedCategory,
        )
        val allCategories = categoryDao.getAllCategories()

        count.shouldBeZero()
        allCategories.size.shouldBe(
            expected = 1,
        )
        allCategories.first().shouldBe(
            expected = categoryEntity,
        )
    }

    // region common
    private fun getCategoryEntity(
        id: Int = 0,
        title: String = "test-category",
        transactionType: TransactionType = TransactionType.EXPENSE,
    ): CategoryEntity {
        return CategoryEntity(
            id = id,
            title = title,
            transactionType = transactionType,
            emoji = "💰",
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
