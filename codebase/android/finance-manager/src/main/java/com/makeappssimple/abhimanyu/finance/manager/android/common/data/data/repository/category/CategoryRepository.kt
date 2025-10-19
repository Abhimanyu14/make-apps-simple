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

package com.makeappssimple.abhimanyu.finance.manager.android.common.data.data.repository.category

import com.makeappssimple.abhimanyu.finance.manager.android.common.domain.model.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

internal interface CategoryRepository {
    suspend fun deleteCategories(
        vararg categories: Category,
    ): Boolean

    suspend fun deleteCategoryById(
        id: Int,
    ): Int

    suspend fun getAllCategories(): ImmutableList<Category>

    fun getAllCategoriesFlow(): Flow<ImmutableList<Category>>

    suspend fun getCategoryById(
        id: Int,
    ): Category?

    suspend fun insertCategories(
        vararg categories: Category,
    ): ImmutableList<Long>

    suspend fun updateCategories(
        vararg categories: Category,
    ): Int
}
