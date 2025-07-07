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

package com.makeappssimple.abhimanyu.barcodes.android.core.database.dao

import android.database.sqlite.SQLiteException
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.makeappssimple.abhimanyu.barcodes.android.core.database.model.BarcodeEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for barcode_table.
 */
@Dao
internal interface BarcodeDao {
    /**
     * Delete all barcodes from the table.
     * @return Number of rows deleted
     * @throws [SQLiteException]
     */
    @Query("DELETE FROM barcode_table")
    suspend fun deleteAllBarcodes(): Int

    /**
     * Delete barcodes from the table.
     * @return Number of rows deleted
     * @throws [SQLiteException]
     */
    @Delete
    suspend fun deleteBarcodes(
        vararg barcodeEntities: BarcodeEntity,
    ): Int

    /**
     * Get all barcodes as a Flow.
     * @throws [SQLiteException]
     */
    @Query("SELECT * from barcode_table ORDER BY id ASC")
    fun getAllBarcodesFlow(): Flow<List<BarcodeEntity>>

    /**
     * Get all barcodes as a list.
     * @return Returns all barcodes ordered by [BarcodeEntity.id]
     * @throws [SQLiteException]
     */
    @Query("SELECT * from barcode_table ORDER BY id ASC")
    suspend fun getAllBarcodes(): List<BarcodeEntity>

    /**
     * Get a barcode by id.
     * @param id Required barcode id
     * @return Barcode with given [id] or returns null if no barcode has the given id.
     * @throws [SQLiteException]
     */
    @Query(value = "SELECT * from barcode_table WHERE id = :id")
    suspend fun getBarcode(
        id: Int,
    ): BarcodeEntity?

    /**
     * Insert barcodes into the table.
     * @return Row id of inserted rows. First valid row id is 1.
     * @throws [SQLiteException]
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBarcodes(
        vararg barcodeEntities: BarcodeEntity,
    ): LongArray

    /**
     * Update barcodes in the table.
     * Only updates the existing rows using the primary key
     * @return Number of rows updated
     * @throws [SQLiteException]
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBarcodes(
        vararg barcodeEntities: BarcodeEntity,
    ): Int
}
