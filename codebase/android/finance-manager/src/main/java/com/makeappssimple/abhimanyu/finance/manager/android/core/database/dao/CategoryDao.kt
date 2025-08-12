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

/**
 * Data Access Object for category_table.
 */
@Dao
public interface CategoryDao {
    /**
     * Delete all categories from the table.
     * @return Number of rows deleted
     */
    @Query(value = "DELETE FROM category_table")
    public suspend fun deleteAllCategories(): Int

    /**
     * Delete categories from the table.
     * @param categories Categories to delete
     * @return Number of rows deleted
     */
    @Delete
    public suspend fun deleteCategories(
        vararg categories: CategoryEntity,
    ): Int

    /**
     * Delete a category by id.
     * @param id Required category id
     * @return Number of rows deleted
     */
    @Query(value = "DELETE FROM category_table WHERE id = :id")
    public suspend fun deleteCategoryById(
        id: Int,
    ): Int

    /**
     * Get all categories as a list.
     * @return Returns all categories ordered by [CategoryEntity.id]
     */
    @Query(value = "SELECT * from category_table ORDER BY id ASC")
    public suspend fun getAllCategories(): List<CategoryEntity>

    /**
     * Get all categories as a Flow.
     * @return Flow emitting the list of all categories ordered by [CategoryEntity.id]
     */
    @Query(value = "SELECT * from category_table ORDER BY id ASC")
    public fun getAllCategoriesFlow(): Flow<List<CategoryEntity>>

    /**
     * Get a category by id.
     * @param id Category id
     * @return Category with given [id] or null if not found
     */
    @Query(value = "SELECT * from category_table WHERE id = :id")
    public suspend fun getCategoryById(
        id: Int,
    ): CategoryEntity?

    /**
     * Insert categories into the table.
     * @param categories Categories to insert
     * @return List of row ids for inserted categories. -1 if a conflict occurred for that item.
     */
    // TODO(Abhi): Handle conflicts with error handling properly
    @Insert(onConflict = OnConflictStrategy.ABORT)
    public suspend fun insertCategories(
        vararg categories: CategoryEntity,
    ): List<Long>

    /**
     * Update categories in the table.
     * Only updates the existing rows using the primary key
     * @param categories Categories to update
     * @return Number of rows updated
     */
    @Update
    public suspend fun updateCategories(
        vararg categories: CategoryEntity,
    ): Int
}
