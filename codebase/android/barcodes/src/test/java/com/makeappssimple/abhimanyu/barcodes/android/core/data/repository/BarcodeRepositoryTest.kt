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

import com.google.common.truth.Truth.assertThat
import com.makeappssimple.abhimanyu.barcodes.android.core.database.dao.fake.FakeBarcodeDao
import com.makeappssimple.abhimanyu.barcodes.android.core.database.placeholder.asExternalModel
import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.model.BarcodeSource
import com.makeappssimple.abhimanyu.common.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.common.core.coroutines.test.TestDispatcherProviderImpl
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

        assertThat(insertedBarcodeIds.size).isEqualTo(1)
        assertThat(insertedBarcodeIds[0]).isEqualTo(1)
        assertThat(fakeBarcodeDao.fakeBarcodeEntities[0].asExternalModel()).isEqualTo(
            barcode
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

        assertThat(result.size).isEqualTo(2)
        assertThat(result[0]).isEqualTo(barcode1)
        assertThat(result[1]).isEqualTo(barcode2)
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

        assertThat(result).isEqualTo(barcode)
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

        assertThat(count).isEqualTo(1)
        assertThat(result).isEqualTo(updatedBarcode)
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

        assertThat(count).isEqualTo(1)
        assertThat(result).isNull()
    }

    private fun getBarcode(
        id: Int = 0,
        value: String = "test-value",
    ): Barcode {
        return Barcode(
            id = id,
            value = value,
            format = 256,
            name = "Test Barcode",
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
