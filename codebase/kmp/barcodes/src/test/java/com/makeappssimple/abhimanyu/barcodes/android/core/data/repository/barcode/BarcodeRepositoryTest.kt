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

package com.makeappssimple.abhimanyu.barcodes.android.core.data.repository.barcode

import com.makeappssimple.abhimanyu.barcodes.android.core.data.barcode.fake.FakeBarcodeDao
import com.makeappssimple.abhimanyu.barcodes.android.core.data.mapper.BarcodeDataToDomainMapper
import com.makeappssimple.abhimanyu.barcodes.android.core.data.mapper.BarcodeDomainToDataMapper
import com.makeappssimple.abhimanyu.barcodes.android.core.domain.model.BarcodeDomainModel
import com.makeappssimple.abhimanyu.barcodes.android.core.domain.model.BarcodeSourceDomainModel
import com.makeappssimple.abhimanyu.barcodes.android.core.domain.repository.BarcodeRepository
import com.makeappssimple.abhimanyu.common.result.MyResult
import com.makeappssimple.abhimanyu.core.coroutines.CoroutineDispatcherProvider
import com.makeappssimple.abhimanyu.core.coroutines.test.TestCoroutineDispatcherProviderImpl
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
    private lateinit var coroutineDispatcherProvider: CoroutineDispatcherProvider
    private val barcodeDataToDomainMapper = BarcodeDataToDomainMapper()
    private val barcodeDomainToDataMapper = BarcodeDomainToDataMapper()

    @Before
    fun setUp() {
        fakeBarcodeDao = FakeBarcodeDao()
        coroutineDispatcherProvider = TestCoroutineDispatcherProviderImpl(
            testDispatcher = Dispatchers.Unconfined,
        )
        barcodeRepository = BarcodeRepositoryImpl(
            barcodeDao = fakeBarcodeDao,
            coroutineDispatcherProvider = coroutineDispatcherProvider,
            barcodeDataToDomainMapper = barcodeDataToDomainMapper,
            barcodeDomainToDataMapper = barcodeDomainToDataMapper,
        )
    }

    @Test
    fun insertBarcode_insertsAndReturnsId() = runTestWithTimeout {
        val barcode = getBarcode(
            id = 1,
        )

        val result = barcodeRepository.insertBarcode(
            barcode = barcode,
        )

        (result as MyResult.Success).data.shouldBe(
            expected = 1L,
        )
        val savedBarcodeDataModel = fakeBarcodeDao.fakeBarcodeEntities[0]
        barcodeDataToDomainMapper.toDomainModel(
            barcodeDataModel = savedBarcodeDataModel,
        ).shouldBe(
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
        barcodeRepository.insertBarcode(
            barcode = barcode1,
        )
        barcodeRepository.insertBarcode(
            barcode = barcode2,
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
        barcodeRepository.insertBarcode(
            barcode = barcode,
        )

        val result = barcodeRepository.getBarcodeById(
            id = 1,
        )

        (result as MyResult.Success).data.shouldBe(
            expected = barcode,
        )
    }

    @Test
    fun updateBarcodes_updatesExistingBarcode() = runTestWithTimeout {
        val barcode = getBarcode(
            id = 1,
            value = "old",
        )
        barcodeRepository.insertBarcode(
            barcode = barcode,
        )
        val updatedBarcode = barcode.copy(
            value = "new",
        )

        val result = barcodeRepository.updateBarcodes(
            updatedBarcode,
        )
        val barcodeResult = barcodeRepository.getBarcodeById(
            id = 1,
        )

        (result as MyResult.Success).data.shouldBe(
            expected = 1,
        )
        (barcodeResult as MyResult.Success).data.shouldBe(
            expected = updatedBarcode,
        )
    }

    @Test
    fun deleteBarcodes_deletesBarcode() = runTestWithTimeout {
        val barcode = getBarcode(
            id = 1,
        )
        barcodeRepository.insertBarcode(
            barcode = barcode,
        )

        val result = barcodeRepository.deleteBarcodes(
            barcode,
        )
        val barcodeResult = barcodeRepository.getBarcodeById(
            id = 1,
        )

        (result as MyResult.Success).data.shouldBe(
            expected = 1,
        )
        (barcodeResult as MyResult.Success).data.shouldBeNull()
    }

    private fun getBarcode(
        id: Int = 0,
        value: String = "test-value",
    ): BarcodeDomainModel {
        return BarcodeDomainModel(
            id = id,
            value = value,
            format = 256,
            name = "Test BarcodeDomainModel",
            source = BarcodeSourceDomainModel.Scanned,
            timestamp = 1680155040000L,
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
