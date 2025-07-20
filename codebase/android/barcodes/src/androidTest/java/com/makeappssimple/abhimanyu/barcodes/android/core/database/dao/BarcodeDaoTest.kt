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
import com.makeappssimple.abhimanyu.barcodes.android.core.database.local.MyRoomDatabase
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
    private lateinit var myRoomDatabase: MyRoomDatabase
    private lateinit var barcodeDao: BarcodeDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        myRoomDatabase = Room
            .inMemoryDatabaseBuilder(
                context = context,
                klass = MyRoomDatabase::class.java,
            )
            .allowMainThreadQueries()
            .build()
        barcodeDao = myRoomDatabase.barcodeDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        myRoomDatabase.close()
    }

    @Test
    fun insertAndGetBarcode() = runTest {
        val entity = testBarcodeEntity()

        val ids = barcodeDao.insertBarcodes(
            entity,
        )
        val all = barcodeDao.getAllBarcodes()

        assertThat(ids).isNotEmpty()
        assertThat(all.size).isEqualTo(1)
        assertThat(all[0].value).isEqualTo(entity.value)
    }

    @Test
    fun updateBarcode() = runTest {
        val entity = testBarcodeEntity()
        barcodeDao.insertBarcodes(
            entity,
        )
        val inserted = barcodeDao.getAllBarcodes().first()
        val updated = inserted.copy(
            name = "Updated Name",
        )

        val count = barcodeDao.updateBarcodes(
            updated,
        )
        val result = barcodeDao.getBarcode(
            id = inserted.id,
        )

        assertThat(count).isEqualTo(1)
        assertThat(result?.name).isEqualTo("Updated Name")
    }

    @Test
    fun deleteBarcode() = runTest {
        val entity = testBarcodeEntity()
        barcodeDao.insertBarcodes(
            entity,
        )
        val inserted = barcodeDao.getAllBarcodes().first()

        val count = barcodeDao.deleteBarcodes(
            inserted,
        )
        val all = barcodeDao.getAllBarcodes()

        assertThat(count).isEqualTo(1)
        assertThat(all).isEmpty()
    }

    @Test
    fun deleteAllBarcodes() = runTest {
        barcodeDao.insertBarcodes(
            testBarcodeEntity(
                value = "1",
            ),
            testBarcodeEntity(
                value = "2",
            ),
        )

        val count = barcodeDao.deleteAllBarcodes()
        val all = barcodeDao.getAllBarcodes()

        assertThat(count).isEqualTo(2)
        assertThat(all).isEmpty()
    }

    @Test
    fun getAllBarcodesFlow() = runTest {
        barcodeDao.insertBarcodes(
            testBarcodeEntity(
                value = "1",
            ),
            testBarcodeEntity(
                value = "2",
            ),
        )

        val flow = barcodeDao.getAllBarcodesFlow()
        val list = flow.first()

        assertThat(list.size).isEqualTo(2)
        assertThat(list.any { it.value == "1" }).isTrue()
        assertThat(list.any { it.value == "2" }).isTrue()
    }

    private fun testBarcodeEntity(
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
