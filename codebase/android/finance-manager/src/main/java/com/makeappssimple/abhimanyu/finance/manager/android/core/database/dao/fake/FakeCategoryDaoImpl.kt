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

package com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.fake

import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.CategoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

public class FakeCategoryDaoImpl : CategoryDao {
    override fun getAllCategoriesFlow(): Flow<List<CategoryEntity>> {
        return emptyFlow()
    }

    override suspend fun getAllCategories(): List<CategoryEntity> {
        return emptyList()
    }

    override suspend fun getAllCategoriesCount(): Int {
        return 0
    }

    override suspend fun getCategoryById(
        id: Int,
    ): CategoryEntity? {
        return null
    }

    override suspend fun insertCategories(
        vararg categories: CategoryEntity,
    ): List<Long> {
        return emptyList()
    }

    override suspend fun updateCategories(
        vararg categories: CategoryEntity,
    ): Int {
        return 0
    }

    override suspend fun deleteCategoryById(
        id: Int,
    ): Int {
        return 0
    }

    override suspend fun deleteCategories(
        vararg categories: CategoryEntity,
    ): Int {
        return 0
    }

    override suspend fun deleteAllCategories(): Int {
        return 0
    }
}
