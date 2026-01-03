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
import com.makeappssimple.abhimanyu.barcodes.android.core.database.local.BarcodesRoomDatabase
import com.makeappssimple.abhimanyu.barcodes.android.core.database.placeholder.BarcodeEntity
import com.makeappssimple.abhimanyu.barcodes.android.core.model.BarcodeSource
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
        barcodeDao.insertBarcodes(
            getBarcodeEntity(
                value = "1",
            ),
            getBarcodeEntity(
                value = "2",
            ),
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
            val barcodeEntity1 = getBarcodeEntity(
                id = 1,
            )
            val barcodeEntity2 = getBarcodeEntity(
                id = 2,
            )
            barcodeDao.insertBarcodes(
                barcodeEntity1,
                barcodeEntity2,
            )

            val count = barcodeDao.deleteBarcodes(
                barcodeEntity2,
            )
            val allBarcodes = barcodeDao.getAllBarcodesFlow().first()

            count.shouldBe(
                expected = 1,
            )
            allBarcodes.size.shouldBe(
                expected = 1,
            )
            allBarcodes.first().shouldBe(
                expected = barcodeEntity1,
            )
        }

    @Test
    fun deleteBarcode_invalidBarcode_returnsCountOfBarcodesDeleted() =
        runTestWithTimeout {
            val barcodeEntity1 = getBarcodeEntity(
                id = 1,
            )
            val barcodeEntity2 = getBarcodeEntity(
                id = 2,
            )
            val barcodeEntity3 = getBarcodeEntity(
                id = 3,
            )
            barcodeDao.insertBarcodes(
                barcodeEntity1,
                barcodeEntity2,
            )

            val count = barcodeDao.deleteBarcodes(
                barcodeEntity3,
            )
            val allBarcodes = barcodeDao.getAllBarcodesFlow().first()

            count.shouldBeZero()
            allBarcodes.size.shouldBe(
                expected = 2,
            )
            allBarcodes.any { it == barcodeEntity1 }.shouldBeTrue()
            allBarcodes.any { it == barcodeEntity2 }.shouldBeTrue()
        }

    @Test
    fun getAllBarcodesFlow_returnsAllBarcodesInFlow() = runTestWithTimeout {
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

        val allBarcodes = barcodeDao.getAllBarcodesFlow().first()

        allBarcodes.size.shouldBe(
            expected = 2,
        )
        allBarcodes.any { it == barcodeEntity1 }.shouldBeTrue()
        allBarcodes.any { it == barcodeEntity2 }.shouldBeTrue()
    }

    @Test
    fun getBarcodeById_validId_returnsBarcode() = runTestWithTimeout {
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

        val result = barcodeDao.getBarcodeById(
            id = 1,
        )

        result.shouldBe(
            expected = barcodeEntity1,
        )
    }

    @Test
    fun getBarcodeById_invalidId_returnsNull() = runTestWithTimeout {
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

        val result = barcodeDao.getBarcodeById(
            id = 3,
        )

        result.shouldBeNull()
    }

    @Test
    fun insertBarcodes_returnsListOfIdsOfInsertedBarcodes() =
        runTestWithTimeout {
            val barcodeEntity1 = getBarcodeEntity(
                id = 1,
            )
            val barcodeEntity2 = getBarcodeEntity(
                id = 2,
            )

            val insertedBarcodeIds = barcodeDao.insertBarcodes(
                barcodeEntity1,
                barcodeEntity2,
            )
            val allBarcodes = barcodeDao.getAllBarcodesFlow().first()

            insertedBarcodeIds.size.shouldBe(
                expected = 2,
            )
            insertedBarcodeIds.any { it == 1L }.shouldBeTrue()
            insertedBarcodeIds.any { it == 2L }.shouldBeTrue()
            allBarcodes.size.shouldBe(
                expected = 2,
            )
            allBarcodes.any { it == barcodeEntity1 }.shouldBeTrue()
            allBarcodes.any { it == barcodeEntity2 }.shouldBeTrue()
        }

    @Test
    fun updateBarcodes_validBarcode_returnsCountOfBarcodesUpdated() =
        runTestWithTimeout {
            val barcodeEntity1 = getBarcodeEntity(
                id = 1,
            )
            barcodeDao.insertBarcodes(
                barcodeEntity1,
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
            val barcodeEntity1 = getBarcodeEntity(
                id = 1,
            )
            barcodeDao.insertBarcodes(
                barcodeEntity1,
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
                expected = barcodeEntity1,
            )
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
