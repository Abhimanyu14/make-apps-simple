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

package com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.category.fake

import com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.category.CategoryRepository
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public class FakeCategoryRepositoryImpl : CategoryRepository {
    override fun getAllCategoriesFlow(): Flow<ImmutableList<Category>> {
        return flow {
            persistentListOf<Category>()
        }
    }

    override suspend fun getAllCategories(): ImmutableList<Category> {
        return persistentListOf()
    }

    override suspend fun getAllCategoriesCount(): Int {
        return 0
    }

    override suspend fun getCategory(
        id: Int,
    ): Category? {
        return null
    }

    override suspend fun insertCategories(
        vararg categories: Category,
    ): ImmutableList<Long> {
        return persistentListOf()
    }

    override suspend fun updateCategories(
        vararg categories: Category,
    ): Boolean {
        return false
    }

    override suspend fun deleteCategory(
        id: Int,
    ): Boolean {
        return false
    }

    override suspend fun deleteCategories(
        vararg categories: Category,
    ): Boolean {
        return false
    }
}
