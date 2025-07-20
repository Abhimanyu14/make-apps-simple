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

import com.makeappssimple.abhimanyu.barcodes.android.core.database.dao.BarcodeDao
import com.makeappssimple.abhimanyu.barcodes.android.core.database.model.BarcodeEntity
import kotlinx.coroutines.flow.flow

internal class FakeBarcodeDao : BarcodeDao {
    val entities = mutableListOf<BarcodeEntity>()

    override suspend fun deleteAllBarcodes(): Int {
        val count = entities.size
        entities.clear()
        return count
    }

    override suspend fun deleteBarcodes(
        vararg barcodeEntities: BarcodeEntity,
    ): Int {
        val before = entities.size
        entities.removeAll { e -> barcodeEntities.any { it.id == e.id } }
        return before - entities.size
    }

    override fun getAllBarcodesFlow(
    ) = flow {
        emit(
            value = entities.toList(),
        )
    }

    override suspend fun getAllBarcodes(
    ): List<BarcodeEntity> =
        entities.toList()

    override suspend fun getBarcode(
        id: Int,
    ): BarcodeEntity? =
        entities.find { it.id == id }

    override suspend fun insertBarcodes(
        vararg barcodeEntities: BarcodeEntity,
    ): LongArray {
        val ids = mutableListOf<Long>()
        barcodeEntities.forEach { entity ->
            entities.removeAll { it.id == entity.id }
            entities.add(
                element = entity,
            )
            ids.add(
                element = entity.id.toLong(),
            )
        }
        return ids.toLongArray()
    }

    override suspend fun updateBarcodes(
        vararg barcodeEntities: BarcodeEntity,
    ): Int {
        var count = 0
        barcodeEntities.forEach { entity ->
            val index = entities.indexOfFirst { it.id == entity.id }
            if (index != -1) {
                entities[index] = entity
                count++
            }
        }
        return count
    }
}
