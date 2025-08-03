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

import com.makeappssimple.abhimanyu.finance.manager.android.core.model.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

public interface CategoryRepository {
    public fun getAllCategoriesFlow(): Flow<ImmutableList<Category>>

    public suspend fun getAllCategories(): ImmutableList<Category>

    public suspend fun getAllCategoriesCount(): Int

    public suspend fun getCategory(
        id: Int,
    ): Category?

    public suspend fun insertCategories(
        vararg categories: Category,
    ): ImmutableList<Long>

    public suspend fun updateCategories(
        vararg categories: Category,
    ): Boolean

    public suspend fun deleteCategory(
        id: Int,
    ): Boolean

    public suspend fun deleteCategories(
        vararg categories: Category,
    ): Boolean
}
