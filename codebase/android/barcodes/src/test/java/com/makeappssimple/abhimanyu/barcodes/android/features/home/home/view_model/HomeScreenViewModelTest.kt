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

package com.makeappssimple.abhimanyu.barcodes.android.features.home.home.view_model

import app.cash.turbine.test
import com.makeappssimple.abhimanyu.barcodes.android.core.data.barcode.fake.FakeBarcodeDao
import com.makeappssimple.abhimanyu.barcodes.android.core.data.mapper.BarcodeDataToDomainMapper
import com.makeappssimple.abhimanyu.barcodes.android.core.data.mapper.BarcodeDomainToDataMapper
import com.makeappssimple.abhimanyu.barcodes.android.core.data.repository.barcode.BarcodeRepositoryImpl
import com.makeappssimple.abhimanyu.barcodes.android.core.domain.use_case.barcode.DeleteBarcodesUseCase
import com.makeappssimple.abhimanyu.barcodes.android.core.domain.use_case.barcode.GetAllBarcodesFlowUseCase
import com.makeappssimple.abhimanyu.barcodes.android.core.domain.use_case.barcode.InsertBarcodesUseCase
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.mapper.BarcodeDomainToUiMapper
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.mapper.BarcodeUiToDomainMapper
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.model.BarcodeFormatUiModel
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.model.BarcodeSourceUiModel
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.model.BarcodeUiModel
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation.BarcodesNavigationDirections
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation.BarcodesNavigationKitImpl
import com.makeappssimple.abhimanyu.barcodes.android.features.home.presentation.home.view_model.HomeScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.shared.ui.analytics.FirebaseAnalyticsKitImpl
import com.makeappssimple.abhimanyu.common.coroutines.test.TestCoroutineDispatcherProviderImpl
import com.makeappssimple.abhimanyu.common.log_kit.fake.FakeLogKitImpl
import com.makeappssimple.abhimanyu.common.uri_encoder.UriEncoder
import com.makeappssimple.abhimanyu.common.uri_encoder.UriEncoderImpl
import com.makeappssimple.abhimanyu.core.date.time.DateTimeKitImpl
import io.kotest.matchers.ints.shouldBeZero
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class HomeScreenViewModelTest {
    private val testCoroutineDispatcher = StandardTestDispatcher()
    private val testCoroutineScope = CoroutineScope(
        context = testCoroutineDispatcher,
    )
    private val testDispatcherProvider = TestCoroutineDispatcherProviderImpl(
        testDispatcher = testCoroutineDispatcher,
    )

    private val analyticsKit = FirebaseAnalyticsKitImpl()
    private val fakeBarcodeDao = FakeBarcodeDao()
    private val barcodeDataToDomainMapper = BarcodeDataToDomainMapper()
    private val barcodeDomainToDataMapper = BarcodeDomainToDataMapper()
    private val barcodeDomainToUiMapper = BarcodeDomainToUiMapper()
    private val barcodeUiToDomainMapper = BarcodeUiToDomainMapper()

    private val barcodeRepository = BarcodeRepositoryImpl(
        barcodeDao = fakeBarcodeDao,
        coroutineDispatcherProvider = testDispatcherProvider,
        barcodeDataToDomainMapper = barcodeDataToDomainMapper,
        barcodeDomainToDataMapper = barcodeDomainToDataMapper,
    )

    private val dateTimeKit = DateTimeKitImpl()
    private val uriEncoder: UriEncoder = UriEncoderImpl()
    private val navigationKit = BarcodesNavigationKitImpl(
        coroutineScope = testCoroutineScope,
        uriEncoder = uriEncoder,
    )
    private val fakeLogKit = FakeLogKitImpl()

    private val deleteBarcodesUseCase = DeleteBarcodesUseCase(
        barcodeRepository = barcodeRepository,
    )
    private val getAllBarcodesFlowUseCase = GetAllBarcodesFlowUseCase(
        barcodeRepository = barcodeRepository,
    )
    private val insertBarcodesUseCase = InsertBarcodesUseCase(
        barcodeRepository = barcodeRepository,
        dateTimeKit = dateTimeKit,
    )

    private lateinit var homeScreenViewModel: HomeScreenViewModel

    @OptIn(ExperimentalTime::class)
    @Before
    fun setUp() {
        homeScreenViewModel = HomeScreenViewModel(
            analyticsKit = analyticsKit,
            barcodesNavigationKit = navigationKit,
            coroutineScope = testCoroutineScope,
            getAllBarcodesFlowUseCase = getAllBarcodesFlowUseCase,
            logKit = fakeLogKit,
            barcodeDomainToUiMapper = barcodeDomainToUiMapper,
            barcodeUiToDomainMapper = barcodeUiToDomainMapper,
            dateTimeKit = dateTimeKit,
            deleteBarcodesUseCase = deleteBarcodesUseCase,
            insertBarcodesUseCase = insertBarcodesUseCase,
        )
    }

    @Test
    fun navigateToCreateBarcodeScreenTest() = runTestWithTimeout {
        navigationKit.command.test {
            homeScreenViewModel.navigateToCreateBarcodeScreen()

            awaitItem().shouldBe(
                expected = BarcodesNavigationDirections.CreateBarcode(
                    barcodeId = null,
                ),
            )
        }
    }

    @Test
    fun navigateToScanBarcodeScreenTest() = runTestWithTimeout {
        navigationKit.command.test {
            homeScreenViewModel.navigateToScanBarcodeScreen()

            awaitItem().shouldBe(
                expected = BarcodesNavigationDirections.ScanBarcode,
            )
        }
    }

    @Test
    fun navigateToSettingsScreenTest() = runTestWithTimeout {
        navigationKit.command.test {
            homeScreenViewModel.navigateToSettingsScreen()

            awaitItem().shouldBe(
                expected = BarcodesNavigationDirections.Settings,
            )
        }
    }

    @Test
    fun navigateToBarcodeDetailsScreenTest() = runTestWithTimeout {
        val barcodeId = 123
        navigationKit.command.test {
            homeScreenViewModel.navigateToBarcodeDetailsScreen(
                barcodeId = barcodeId,
            )

            awaitItem().shouldBe(
                expected = BarcodesNavigationDirections.BarcodeDetails(
                    barcodeId = barcodeId,
                ),
            )
        }
    }

    @Test
    fun restoreBarcode() = runTestWithTimeout {
        val barcode = BarcodeUiModel(
            source = BarcodeSourceUiModel.Created,
            format = BarcodeFormatUiModel.QrCode,
            id = 1,
            timestamp = 1680155040000L,
            name = "Test BarcodeDomainModel",
            value = "test-value",
        )

        homeScreenViewModel.restoreBarcode(
            barcode = barcode,
        ).join()

        val savedBarcodeDataModel = fakeBarcodeDao.fakeBarcodeEntities[0]
        val savedBarcodeUiModel = barcodeDomainToUiMapper.toUiModel(
            barcodeDomainModel = barcodeDataToDomainMapper.toDomainModel(
                barcodeDataModel = savedBarcodeDataModel,
            ),
        )
        savedBarcodeUiModel.shouldBe(
            expected = barcode,
        )
    }

    @Test
    fun deleteBarcodes() = runTestWithTimeout {
        val barcode1 = BarcodeUiModel(
            source = BarcodeSourceUiModel.Created,
            format = BarcodeFormatUiModel.QrCode,
            id = 1,
            timestamp = 1680155040000L,
            name = "Test BarcodeDomainModel 1",
            value = "test-value-1",
        )
        val barcode2 = BarcodeUiModel(
            source = BarcodeSourceUiModel.Created,
            format = BarcodeFormatUiModel.QrCode,
            id = 2,
            timestamp = 1680155040000L,
            name = "Test BarcodeDomainModel 2",
            value = "test-value-2",
        )
        val barcodes = listOf(
            barcode1,
            barcode2
        )

        homeScreenViewModel.restoreBarcode(
            barcode = barcode1,
        ).join()
        homeScreenViewModel.restoreBarcode(
            barcode = barcode2,
        ).join()
        homeScreenViewModel.deleteBarcodes(
            barcodes = barcodes,
        ).join()

        fakeBarcodeDao.fakeBarcodeEntities.size.shouldBeZero()
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
