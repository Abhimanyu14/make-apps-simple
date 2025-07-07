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

package com.makeappssimple.abhimanyu.barcodes.android.core.data.repository

import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import kotlinx.coroutines.flow.Flow

/**
 * Responsibilities
 * 1. To handle SQL exceptions
 * 2. Make the calls thread safe
 *
 * Project convention
 * Method ordering - Create, Read, Update and Delete
 */
internal interface BarcodeRepository {
    /**
     * @return Row id of inserted rows. First valid row id is 1.
     * Returns empty array if failed to insert.
     */
    suspend fun insertBarcodes(
        vararg barcodes: Barcode,
    ): LongArray

    fun getAllBarcodesFlow(): Flow<List<Barcode>>

    /**
     * @param id Required barcode id
     * @return Barcode with given [id] or returns null if no barcode has the given id.
     */
    suspend fun getBarcode(
        id: Int,
    ): Barcode?

    /**
     * Only updates the existing rows using the primary key
     *
     * @return Number of rows updated
     */
    suspend fun updateBarcodes(
        vararg barcodes: Barcode,
    ): Int

    /**
     * @return Number of rows deleted
     */
    suspend fun deleteBarcodes(
        vararg barcodes: Barcode,
    ): Int
}
