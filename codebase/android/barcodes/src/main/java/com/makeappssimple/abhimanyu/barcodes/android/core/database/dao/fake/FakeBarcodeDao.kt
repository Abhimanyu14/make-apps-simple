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

package com.makeappssimple.abhimanyu.barcodes.android.core.database.dao.fake

import androidx.annotation.VisibleForTesting
import com.makeappssimple.abhimanyu.barcodes.android.core.database.dao.BarcodeDao
import com.makeappssimple.abhimanyu.barcodes.android.core.database.placeholder.BarcodeEntity
import kotlinx.coroutines.flow.flow

internal class FakeBarcodeDao : BarcodeDao {
    @VisibleForTesting
    internal val fakeBarcodeEntities = mutableListOf<BarcodeEntity>()

    override suspend fun deleteAllBarcodes(): Int {
        val count = fakeBarcodeEntities.size
        fakeBarcodeEntities.clear()
        return count
    }

    override suspend fun deleteBarcodes(
        vararg barcodeEntities: BarcodeEntity,
    ): Int {
        val sizeBeforeDeletion = fakeBarcodeEntities.size
        fakeBarcodeEntities.removeAll { entity ->
            barcodeEntities.any {
                it.id == entity.id
            }
        }
        return sizeBeforeDeletion - fakeBarcodeEntities.size
    }

    override fun getAllBarcodesFlow(
    ) = flow {
        emit(
            value = fakeBarcodeEntities.toList(),
        )
    }

    override suspend fun getAllBarcodes(
    ): List<BarcodeEntity> {
        return fakeBarcodeEntities.toList()
    }

    override suspend fun getBarcodeById(
        id: Int,
    ): BarcodeEntity? {
        return fakeBarcodeEntities.find { it.id == id }
    }

    override suspend fun insertBarcodes(
        vararg barcodeEntities: BarcodeEntity,
    ): LongArray {
        val insertedBarcodeIds = mutableListOf<Long>()
        barcodeEntities.forEach { entity ->
            fakeBarcodeEntities.removeAll { it.id == entity.id }
            fakeBarcodeEntities.add(
                element = entity,
            )
            insertedBarcodeIds.add(
                element = entity.id.toLong(),
            )
        }
        return insertedBarcodeIds.toLongArray()
    }

    override suspend fun updateBarcodes(
        vararg barcodeEntities: BarcodeEntity,
    ): Int {
        var count = 0
        barcodeEntities.forEach { entity ->
            val index = fakeBarcodeEntities.indexOfFirst { it.id == entity.id }
            if (index != -1) {
                fakeBarcodeEntities[index] = entity
                count++
            }
        }
        return count
    }
}
