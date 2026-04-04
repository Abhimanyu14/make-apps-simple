/*
 * Copyright 2025-2026 Abhimanyu
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
import com.makeappssimple.abhimanyu.barcodes.android.core.data.barcode.BarcodeDao
import com.makeappssimple.abhimanyu.barcodes.android.core.data.database.local.BarcodesRoomDatabase
import com.makeappssimple.abhimanyu.barcodes.android.core.data.model.BarcodeDataModel
import com.makeappssimple.abhimanyu.barcodes.android.core.data.model.BarcodeSourceDataModel
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.ints.shouldBeZero
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
internal class BarcodeDaoTest {
    private val testCoroutineDispatcher = StandardTestDispatcher()

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
    fun deleteAllBarcodes_returnsCountOfBarcodesDeleted() = runTestWithTimeout {
        barcodeDao.insertBarcode(
            getBarcodeDataModel(
                value = "1",
            )
        )
        barcodeDao.insertBarcode(
            getBarcodeDataModel(
                value = "2",
            )
        )

        val count = barcodeDao.deleteAllBarcodes()
        val allBarcodes = barcodeDao.getAllBarcodesFlow().first()

        count.shouldBe(
            expected = 2,
        )
        allBarcodes.shouldBeEmpty()
    }

    @Test
    fun deleteBarcode_validBarcode_returnsCountOfBarcodesDeleted() =
        runTestWithTimeout {
            val barcodeDataModel1 = getBarcodeDataModel(
                id = 1,
            )
            val barcodeDataModel2 = getBarcodeDataModel(
                id = 2,
            )
            barcodeDao.insertBarcode(
                barcodeDataModel1,
            )
            barcodeDao.insertBarcode(
                barcodeDataModel2,
            )

            val count = barcodeDao.deleteBarcodes(
                barcodeDataModel2,
            )
            val allBarcodes = barcodeDao.getAllBarcodesFlow().first()

            count.shouldBe(
                expected = 1,
            )
            allBarcodes.size.shouldBe(
                expected = 1,
            )
            allBarcodes.first().shouldBe(
                expected = barcodeDataModel1,
            )
        }

    @Test
    fun deleteBarcode_invalidBarcode_returnsCountOfBarcodesDeleted() =
        runTestWithTimeout {
            val barcodeDataModel1 = getBarcodeDataModel(
                id = 1,
            )
            val barcodeDataModel2 = getBarcodeDataModel(
                id = 2,
            )
            val barcodeDataModel3 = getBarcodeDataModel(
                id = 3,
            )
            barcodeDao.insertBarcode(
                barcodeDataModel1,
            )
            barcodeDao.insertBarcode(
                barcodeDataModel2,
            )

            val count = barcodeDao.deleteBarcodes(
                barcodeDataModel3,
            )
            val allBarcodes = barcodeDao.getAllBarcodesFlow().first()

            count.shouldBeZero()
            allBarcodes.size.shouldBe(
                expected = 2,
            )
            allBarcodes.any { it == barcodeDataModel1 }.shouldBeTrue()
            allBarcodes.any { it == barcodeDataModel2 }.shouldBeTrue()
        }

    @Test
    fun getAllBarcodesFlow_returnsAllBarcodesInFlow() = runTestWithTimeout {
        val barcodeDataModel1 = getBarcodeDataModel(
            id = 1,
            value = "1",
        )
        val barcodeDataModel2 = getBarcodeDataModel(
            id = 2,
            value = "2",
        )
        barcodeDao.insertBarcode(
            barcodeDataModel1,
        )
        barcodeDao.insertBarcode(
            barcodeDataModel2,
        )

        val allBarcodes = barcodeDao.getAllBarcodesFlow().first()

        allBarcodes.size.shouldBe(
            expected = 2,
        )
        allBarcodes.any { it == barcodeDataModel1 }.shouldBeTrue()
        allBarcodes.any { it == barcodeDataModel2 }.shouldBeTrue()
    }

    @Test
    fun getBarcodeById_validId_returnsBarcode() = runTestWithTimeout {
        val barcodeDataModel1 = getBarcodeDataModel(
            id = 1,
            value = "1",
        )
        val barcodeDataModel2 = getBarcodeDataModel(
            id = 2,
            value = "2",
        )
        barcodeDao.insertBarcode(
            barcodeDataModel1,
        )
        barcodeDao.insertBarcode(
            barcodeDataModel2,
        )

        val result = barcodeDao.getBarcodeById(
            id = 1,
        )

        result.shouldBe(
            expected = barcodeDataModel1,
        )
    }

    @Test
    fun getBarcodeById_invalidId_returnsNull() = runTestWithTimeout {
        val barcodeDataModel1 = getBarcodeDataModel(
            id = 1,
            value = "1",
        )
        val barcodeDataModel2 = getBarcodeDataModel(
            id = 2,
            value = "2",
        )
        barcodeDao.insertBarcode(
            barcodeDataModel1,
        )
        barcodeDao.insertBarcode(
            barcodeDataModel2,
        )

        val result = barcodeDao.getBarcodeById(
            id = 3,
        )

        result.shouldBeNull()
    }

    @Test
    fun insertBarcodes_returnsListOfIdsOfInsertedBarcodes() =
        runTestWithTimeout {
            val barcodeDataModel1 = getBarcodeDataModel(
                id = 1,
            )
            val barcodeDataModel2 = getBarcodeDataModel(
                id = 2,
            )

            val insertedBarcodeId1 = barcodeDao.insertBarcode(
                barcodeDataModel1,
            )
            val insertedBarcodeId2 = barcodeDao.insertBarcode(
                barcodeDataModel2,
            )
            val allBarcodes = barcodeDao.getAllBarcodesFlow().first()

            insertedBarcodeId1.shouldBe(1L)
            insertedBarcodeId2.shouldBe(2L)
            allBarcodes.size.shouldBe(
                expected = 2,
            )
            allBarcodes.any { it == barcodeDataModel1 }.shouldBeTrue()
            allBarcodes.any { it == barcodeDataModel2 }.shouldBeTrue()
        }

    @Test
    fun updateBarcodes_validBarcode_returnsCountOfBarcodesUpdated() =
        runTestWithTimeout {
            val barcodeDataModel1 = getBarcodeDataModel(
                id = 1,
            )
            barcodeDao.insertBarcode(
                barcodeDataModel1,
            )
            val insertedBarcode =
                barcodeDao.getAllBarcodesFlow().first().first()
            val updatedBarcode = insertedBarcode.copy(
                name = "Updated Name",
            )

            val count = barcodeDao.updateBarcodes(
                updatedBarcode,
            )
            val result = barcodeDao.getBarcodeById(
                id = 1,
            )

            count.shouldBe(
                expected = 1,
            )
            result.shouldBe(
                expected = updatedBarcode,
            )
        }

    @Test
    fun updateBarcodes_idUpdated_returnsCountOfBarcodesUpdated() =
        runTestWithTimeout {
            val barcodeDataModel1 = getBarcodeDataModel(
                id = 1,
            )
            barcodeDao.insertBarcode(
                barcodeDataModel1,
            )
            val insertedBarcode =
                barcodeDao.getAllBarcodesFlow().first().first()
            val updatedBarcode = insertedBarcode.copy(
                id = 2,
                name = "Updated Name",
            )

            val count = barcodeDao.updateBarcodes(
                updatedBarcode,
            )
            val result = barcodeDao.getBarcodeById(
                id = 1,
            )

            count.shouldBeZero()
            result.shouldBe(
                expected = barcodeDataModel1,
            )
        }

    private fun getBarcodeDataModel(
        id: Int = 0,
        value: String = "test-value",
    ): BarcodeDataModel {
        return BarcodeDataModel(
            source = BarcodeSourceDataModel.Scanned,
            format = 256,
            id = id,
            timestamp = System.currentTimeMillis(),
            name = "Test BarcodeDomainModel",
            value = value,
        )
    }

    private fun runTestWithTimeout(
        testBody: suspend TestScope.() -> Unit,
    ) {
        runTest(
            context = testCoroutineDispatcher,
            timeout = 3.seconds,
        ) {
            testBody()
        }
    }
}
