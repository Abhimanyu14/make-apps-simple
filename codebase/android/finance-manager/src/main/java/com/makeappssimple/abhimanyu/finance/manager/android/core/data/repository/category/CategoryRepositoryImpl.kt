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

import android.database.sqlite.SQLiteConstraintException
import androidx.sqlite.SQLiteException
import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.common.core.extensions.map
import com.makeappssimple.abhimanyu.finance.manager.android.core.data.model.asEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.dao.CategoryDao
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

internal class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val dispatcherProvider: DispatcherProvider,
) : CategoryRepository {
    override suspend fun deleteCategories(
        vararg categories: Category,
    ): Boolean {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                categoryDao.deleteCategories(
                    categories = categories.map(
                        transform = Category::asEntity,
                    ).toTypedArray(),
                ) == categories.size
            } catch (
                _: SQLiteException,
            ) {
                false
            }
        }
    }

    override suspend fun deleteCategoryById(
        id: Int,
    ): Int {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                categoryDao.deleteCategoryById(
                    id = id,
                )
            } catch (
                _: SQLiteException,
            ) {
                0
            }
        }
    }

    override suspend fun getAllCategories(): ImmutableList<Category> {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                categoryDao.getAllCategories().map(
                    transform = CategoryEntity::asExternalModel,
                )
            } catch (
                _: SQLiteException,
            ) {
                persistentListOf()
            }
        }
    }

    override fun getAllCategoriesFlow(): Flow<ImmutableList<Category>> {
        return try {
            categoryDao.getAllCategoriesFlow().map {
                it.map(
                    transform = CategoryEntity::asExternalModel,
                )
            }
        } catch (
            _: SQLiteException,
        ) {
            emptyFlow()
        }
    }

    override suspend fun getCategoryById(
        id: Int,
    ): Category? {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                categoryDao.getCategoryById(
                    id = id,
                )?.asExternalModel()
            } catch (
                _: SQLiteException,
            ) {
                null
            }
        }
    }

    override suspend fun insertCategories(
        vararg categories: Category,
    ): ImmutableList<Long> {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                categoryDao.insertCategories(
                    categories = categories.map(
                        transform = Category::asEntity,
                    ).toTypedArray(),
                ).toImmutableList()
            } catch (
                _: SQLiteConstraintException,
            ) {
                // TODO(Abhi): Check if this needs additional handling
                persistentListOf()
            } catch (
                _: SQLiteException,
            ) {
                persistentListOf()
            }
        }
    }

    override suspend fun updateCategories(
        vararg categories: Category,
    ): Int {
        return dispatcherProvider.executeOnIoDispatcher {
            try {
                categoryDao.updateCategories(
                    categories = categories.map(
                        transform = Category::asEntity,
                    ).toTypedArray(),
                )
            } catch (
                _: SQLiteException,
            ) {
                0
            }
        }
    }
}
