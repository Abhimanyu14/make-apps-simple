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

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.fake

import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.finance.manager.android.common.data.database.model.CategoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * In-memory fake implementation of [CategoryDao] for testing purposes.
 */
internal class FakeCategoryDaoImpl : CategoryDao {
    private val categories = mutableListOf<CategoryEntity>()
    private val categoriesFlow: MutableStateFlow<List<CategoryEntity>> =
        MutableStateFlow(
            value = emptyList(),
        )
    private var nextId = 1

    override suspend fun deleteAllCategories(): Int {
        val count = categories.size
        categories.clear()
        categoriesFlow.value = emptyList()
        return count
    }

    override suspend fun deleteCategories(
        vararg categories: CategoryEntity,
    ): Int {
        var deletedCount = 0
        for (category in categories) {
            val removed = this.categories.removeIf { categoryEntity ->
                categoryEntity.id == category.id
            }
            if (removed) {
                deletedCount++
            }
        }
        categoriesFlow.value = this.categories.sortedBy { categoryEntity ->
            categoryEntity.id
        }
        return deletedCount
    }

    override suspend fun deleteCategoryById(
        id: Int,
    ): Int {
        val removed = categories.removeIf { categoryEntity ->
            categoryEntity.id == id
        }
        if (removed) {
            categoriesFlow.value = categories.sortedBy { categoryEntity ->
                categoryEntity.id
            }
            return 1
        }
        return 0
    }

    override suspend fun getAllCategories(): List<CategoryEntity> {
        return categories.toList()
    }

    override fun getAllCategoriesFlow(): Flow<List<CategoryEntity>> {
        return categoriesFlow.asStateFlow()
    }

    override suspend fun getCategoryById(
        id: Int,
    ): CategoryEntity? {
        return categories.find { categoryEntity ->
            categoryEntity.id == id
        }
    }

    override suspend fun insertCategories(
        vararg categories: CategoryEntity,
    ): List<Long> {
        val result = mutableListOf<Long>()
        for (category in categories) {
            val id = if (category.id == 0) {
                nextId++
            } else {
                category.id
            }
            val exists =
                this@FakeCategoryDaoImpl.categories.any { categoryEntity ->
                    categoryEntity.id == id
                }
            if (exists) {
                result.add(
                    element = -1L,
                )
            } else {
                val entity = category.copy(
                    id = id,
                )
                this@FakeCategoryDaoImpl.categories.add(
                    element = entity,
                )
                result.add(
                    element = id.toLong(),
                )
            }
        }
        categoriesFlow.value =
            this@FakeCategoryDaoImpl.categories.sortedBy { categoryEntity ->
                categoryEntity.id
            }
        return result
    }

    override suspend fun updateCategories(
        vararg categories: CategoryEntity,
    ): Int {
        var updatedCount = 0
        for (category in categories) {
            val index =
                this@FakeCategoryDaoImpl.categories.indexOfFirst { categoryEntity ->
                    categoryEntity.id == category.id
                }
            if (index != -1) {
                this@FakeCategoryDaoImpl.categories[index] = category
                updatedCount++
            }
        }
        categoriesFlow.value =
            this@FakeCategoryDaoImpl.categories.sortedBy { categoryEntity ->
                categoryEntity.id
            }
        return updatedCount
    }
}
