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

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.makeappssimple.abhimanyu.finance.manager.android.core.database.model.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
public interface CategoryDao {
    @Query(value = "SELECT * from category_table ORDER BY id ASC")
    public fun getAllCategoriesFlow(): Flow<List<CategoryEntity>>

    @Query(value = "SELECT * from category_table ORDER BY id ASC")
    public suspend fun getAllCategories(): List<CategoryEntity>

    @Query(value = "SELECT COUNT(*) FROM category_table")
    public suspend fun getAllCategoriesCount(): Int

    @Query(value = "SELECT * from category_table WHERE id = :id")
    public suspend fun getCategory(
        id: Int,
    ): CategoryEntity?

    // TODO(Abhi): Handle conflicts with error handling properly
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public suspend fun insertCategories(
        vararg categories: CategoryEntity,
    ): List<Long>

    @Update
    public suspend fun updateCategories(
        vararg categories: CategoryEntity,
    ): Int

    @Query(value = "DELETE FROM category_table WHERE id = :id")
    public suspend fun deleteCategory(
        id: Int,
    ): Int

    @Delete
    public suspend fun deleteCategories(
        vararg categories: CategoryEntity,
    ): Int

    @Query(value = "DELETE FROM category_table")
    public suspend fun deleteAllCategories(): Int
}
