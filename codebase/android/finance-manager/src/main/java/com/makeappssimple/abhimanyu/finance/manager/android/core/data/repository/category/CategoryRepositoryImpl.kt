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

package com.makeappssimple.abhimanyu.finance.manager.android.core.data.repository.category

import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val dispatcherProvider: DispatcherProvider,
) : CategoryRepository {
    override fun getAllCategoriesFlow(): Flow<ImmutableList<Category>> {
        return categoryDao.getAllCategoriesFlow().map {
            it.map(
                transform = CategoryEntity::asExternalModel,
            )
        }
    }

    override suspend fun getAllCategories(): ImmutableList<Category> {
        return dispatcherProvider.executeOnIoDispatcher {
            categoryDao.getAllCategories().map(
                transform = CategoryEntity::asExternalModel,
            )
        }
    }

    override suspend fun getAllCategoriesCount(): Int {
        return dispatcherProvider.executeOnIoDispatcher {
            categoryDao.getAllCategoriesCount()
        }
    }

    override suspend fun getCategory(
        id: Int,
    ): Category? {
        return dispatcherProvider.executeOnIoDispatcher {
            categoryDao.getCategory(
                id = id,
            )?.asExternalModel()
        }
    }

    override suspend fun insertCategories(
        vararg categories: Category,
    ): ImmutableList<Long> {
        return dispatcherProvider.executeOnIoDispatcher {
            categoryDao.insertCategories(
                categories = categories.map(
                    transform = Category::asEntity,
                ).toTypedArray(),
            ).toImmutableList()
        }
    }

    override suspend fun updateCategories(
        vararg categories: Category,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            categoryDao.updateCategories(
                categories = categories.map(
                    transform = Category::asEntity,
                ).toTypedArray(),
            ) == categories.size
        }
    }

    override suspend fun deleteCategory(
        id: Int,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            categoryDao.deleteCategory(
                id = id,
            ) == 1
        }
    }

    override suspend fun deleteCategories(
        vararg categories: Category,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            categoryDao.deleteCategories(
                categories = categories.map(
                    transform = Category::asEntity,
                ).toTypedArray(),
            ) == categories.size
        }
    }
}
