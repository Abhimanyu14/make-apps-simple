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

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.barcodes.android.core.database.local.BarcodesRoomDatabase
import com.makeappssimple.abhimanyu.barcodes.android.core.database.model.BarcodeEntity
import com.makeappssimple.abhimanyu.barcodes.android.core.model.BarcodeSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
internal class BarcodeDaoTest {
    private lateinit var barcodesRoomDatabase: BarcodesRoomDatabase
    private lateinit var barcodeDao: BarcodeDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        barcodesRoomDatabase = Room
            .inMemoryDatabaseBuilder(
                context = context,
                klass = BarcodesRoomDatabase::class.java,
            )
            .allowMainThreadQueries()
            .build()
        barcodeDao = barcodesRoomDatabase.barcodeDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        barcodesRoomDatabase.close()
    }

    @Test
    fun insertAndGetBarcode() = runTest {
        val barcodeEntity = getBarcodeEntity(
            id = 1,
        )

        val insertedBarcodeIds = barcodeDao.insertBarcodes(
            barcodeEntity,
        )
        val allBarcodes = barcodeDao.getAllBarcodes()

        assertThat(insertedBarcodeIds.size).isEqualTo(1)
        assertThat(insertedBarcodeIds[0]).isEqualTo(1)
        assertThat(allBarcodes.size).isEqualTo(1)
        assertThat(allBarcodes[0]).isEqualTo(barcodeEntity)
    }

    @Test
    fun updateBarcode() = runTest {
        val barcodeEntity = getBarcodeEntity()
        barcodeDao.insertBarcodes(
            barcodeEntity,
        )
        val insertedBarcode = barcodeDao.getAllBarcodes().first()
        val updatedBarcode = insertedBarcode.copy(
            name = "Updated Name",
        )

        val count = barcodeDao.updateBarcodes(
            updatedBarcode,
        )
        val result = barcodeDao.getBarcode(
            id = insertedBarcode.id,
        )

        assertThat(count).isEqualTo(1)
        assertThat(result).isEqualTo(updatedBarcode)
    }

    @Test
    fun deleteBarcode() = runTest {
        val barcodeEntity = getBarcodeEntity()
        barcodeDao.insertBarcodes(
            barcodeEntity,
        )
        val insertedBarcode = barcodeDao.getAllBarcodes().first()

        val count = barcodeDao.deleteBarcodes(
            insertedBarcode,
        )
        val allBarcodes = barcodeDao.getAllBarcodes()

        assertThat(count).isEqualTo(1)
        assertThat(allBarcodes).isEmpty()
    }

    @Test
    fun deleteAllBarcodes() = runTest {
        barcodeDao.insertBarcodes(
            getBarcodeEntity(
                value = "1",
            ),
            getBarcodeEntity(
                value = "2",
            ),
        )

        val count = barcodeDao.deleteAllBarcodes()
        val allBarcodes = barcodeDao.getAllBarcodes()

        assertThat(count).isEqualTo(2)
        assertThat(allBarcodes).isEmpty()
    }

    @Test
    fun getAllBarcodesFlow() = runTest {
        val barcodeEntity1 = getBarcodeEntity(
            id = 1,
            value = "1",
        )
        val barcodeEntity2 = getBarcodeEntity(
            id = 2,
            value = "2",
        )
        barcodeDao.insertBarcodes(
            barcodeEntity1,
            barcodeEntity2,
        )

        val allBarcodesFlow = barcodeDao.getAllBarcodesFlow()
        val allBarcodes = allBarcodesFlow.first()

        assertThat(allBarcodes.size).isEqualTo(2)
        assertThat(allBarcodes.any { it == barcodeEntity1 }).isTrue()
        assertThat(allBarcodes.any { it == barcodeEntity2 }).isTrue()
    }

    private fun getBarcodeEntity(
        id: Int = 0,
        value: String = "test-value",
    ): BarcodeEntity {
        return BarcodeEntity(
            source = BarcodeSource.SCANNED,
            format = 256,
            id = id,
            timestamp = System.currentTimeMillis(),
            name = "Test Barcode",
            value = value,
        )
    }
}
