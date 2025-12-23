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

package com.makeappssimple.abhimanyu.barcodes.android.common.domain.repository

import com.makeappssimple.abhimanyu.barcodes.android.common.domain.model.BarcodeDomainModel
import com.makeappssimple.abhimanyu.common.core.result.MyResult
import kotlinx.coroutines.flow.Flow

/**
 * Responsibilities
 * 1. To handle persistence errors
 * 2. Make the calls thread safe
 */
internal interface BarcodeRepository {
    /**
     * @return Number of rows deleted
     */
    suspend fun deleteBarcodes(
        vararg barcodes: BarcodeDomainModel,
    ): MyResult<Int>

    fun getAllBarcodesFlow(): Flow<List<BarcodeDomainModel>>

    /**
     * @param id Required barcode id
     * @return BarcodeDomainModel with given [id] or returns null if no barcode has the given id.
     */
    suspend fun getBarcodeById(
        id: Int,
    ): MyResult<BarcodeDomainModel?>

    /**
     * @return Row id of inserted rows. First valid row id is 1.
     * Returns empty array if failed to insert.
     */
    suspend fun insertBarcode(
        barcode: BarcodeDomainModel,
    ): MyResult<Long>

    /**
     * Only updates the existing rows using the primary key
     *
     * @return Number of rows updated
     */
    suspend fun updateBarcodes(
        vararg barcodes: BarcodeDomainModel,
    ): MyResult<Int>
}
