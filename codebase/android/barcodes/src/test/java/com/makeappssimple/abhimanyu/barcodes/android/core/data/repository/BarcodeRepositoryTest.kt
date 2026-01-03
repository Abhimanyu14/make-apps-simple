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

package com.makeappssimple.abhimanyu.barcodes.android.core.data.repository

import com.makeappssimple.abhimanyu.barcodes.android.core.database.dao.fake.FakeBarcodeDao
import com.makeappssimple.abhimanyu.barcodes.android.core.database.placeholder.asExternalModel
import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.model.BarcodeSource
import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.common.core.coroutines.test.TestDispatcherProviderImpl
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

internal class BarcodeRepositoryTest {
    private val testCoroutineDispatcher = StandardTestDispatcher()

    private lateinit var fakeBarcodeDao: FakeBarcodeDao
    private lateinit var barcodeRepository: BarcodeRepository
    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        fakeBarcodeDao = FakeBarcodeDao()
        dispatcherProvider = TestDispatcherProviderImpl(
            testDispatcher = Dispatchers.Unconfined,
        )
        barcodeRepository = BarcodeRepositoryImpl(
            barcodeDao = fakeBarcodeDao,
            dispatcherProvider = dispatcherProvider,
        )
    }

    @Test
    fun insertBarcodes_insertsAndReturnsIds() = runTestWithTimeout {
        val barcode = getBarcode(
            id = 1,
        )

        val insertedBarcodeIds = barcodeRepository.insertBarcodes(
            barcode,
        )

        insertedBarcodeIds.size.shouldBe(
            expected = 1,
        )
        insertedBarcodeIds[0].shouldBe(
            expected = 1,
        )
        fakeBarcodeDao.fakeBarcodeEntities[0].asExternalModel().shouldBe(
            expected = barcode,
        )
    }

    @Test
    fun getAllBarcodesFlow_returnsAllBarcodes() = runTestWithTimeout {
        val barcode1 = getBarcode(
            id = 1,
        )
        val barcode2 = getBarcode(
            id = 2,
        )
        barcodeRepository.insertBarcodes(
            barcode1,
            barcode2,
        )

        val result = barcodeRepository.getAllBarcodesFlow().first()

        result.size.shouldBe(
            expected = 2,
        )
        result[0].shouldBe(
            expected = barcode1,
        )
        result[1].shouldBe(
            expected = barcode2,
        )
    }

    @Test
    fun getBarcodeById_returnsCorrectBarcode() = runTestWithTimeout {
        val barcode = getBarcode(
            id = 1,
        )
        barcodeRepository.insertBarcodes(
            barcode,
        )

        val result = barcodeRepository.getBarcodeById(
            id = 1,
        )

        result.shouldBe(
            expected = barcode,
        )
    }

    @Test
    fun updateBarcodes_updatesExistingBarcode() = runTestWithTimeout {
        val barcode = getBarcode(
            id = 1,
            value = "old",
        )
        barcodeRepository.insertBarcodes(
            barcode,
        )
        val updatedBarcode = barcode.copy(
            value = "new",
        )

        val count = barcodeRepository.updateBarcodes(
            updatedBarcode,
        )
        val result = barcodeRepository.getBarcodeById(
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
    fun deleteBarcodes_deletesBarcode() = runTestWithTimeout {
        val barcode = getBarcode(
            id = 1,
        )
        barcodeRepository.insertBarcodes(
            barcode,
        )

        val count = barcodeRepository.deleteBarcodes(
            barcode,
        )
        val result = barcodeRepository.getBarcodeById(
            id = 1,
        )

        count.shouldBe(
            expected = 1,
        )
        result.shouldBeNull()
    }

    private fun getBarcode(
        id: Int = 0,
        value: String = "test-value",
    ): Barcode {
        return Barcode(
            id = id,
            value = value,
            format = 256,
            name = "Test BarcodeDomainModel",
            source = BarcodeSource.SCANNED,
            timestamp = System.currentTimeMillis(),
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
