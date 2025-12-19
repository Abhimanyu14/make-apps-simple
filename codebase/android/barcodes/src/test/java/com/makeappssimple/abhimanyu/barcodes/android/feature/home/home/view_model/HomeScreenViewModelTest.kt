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

package com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.view_model

import app.cash.turbine.test
import com.makeappssimple.abhimanyu.barcodes.android.core.analytics.FirebaseAnalyticsKitImpl
import com.makeappssimple.abhimanyu.barcodes.android.core.data.repository.BarcodeRepositoryImpl
import com.makeappssimple.abhimanyu.barcodes.android.core.database.dao.fake.FakeBarcodeDao
import com.makeappssimple.abhimanyu.barcodes.android.core.database.placeholder.asExternalModel
import com.makeappssimple.abhimanyu.barcodes.android.core.model.Barcode
import com.makeappssimple.abhimanyu.barcodes.android.core.model.BarcodeFormat
import com.makeappssimple.abhimanyu.barcodes.android.core.model.BarcodeSource
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.BarcodesNavigationDirections
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.NavigationKitImpl
import com.makeappssimple.abhimanyu.common.core.coroutines.test.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.common.core.date_time.DateTimeKitImpl
import com.makeappssimple.abhimanyu.common.core.log_kit.fake.FakeLogKitImpl
import com.makeappssimple.abhimanyu.common.core.uri_encoder.UriEncoder
import com.makeappssimple.abhimanyu.common.core.uri_encoder.UriEncoderImpl
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
    private val testDispatcherProvider = TestDispatcherProviderImpl(
        testDispatcher = testCoroutineDispatcher,
    )

    private val analyticsKit = FirebaseAnalyticsKitImpl()
    private val fakeBarcodeDao = FakeBarcodeDao()
    private val barcodeRepository = BarcodeRepositoryImpl(
        barcodeDao = fakeBarcodeDao,
        dispatcherProvider = testDispatcherProvider,
    )
    private val dateTimeKit = DateTimeKitImpl()
    private val uriEncoder: UriEncoder = UriEncoderImpl()
    private val navigationKit = NavigationKitImpl(
        coroutineScope = testCoroutineScope,
        uriEncoder = uriEncoder,
    )
    private val fakeLogKit = FakeLogKitImpl()

    private lateinit var homeScreenViewModel: HomeScreenViewModel

    @OptIn(ExperimentalTime::class)
    @Before
    fun setUp() {
        homeScreenViewModel = HomeScreenViewModel(
            analyticsKit = analyticsKit,
            coroutineScope = testCoroutineScope,
            barcodeRepository = barcodeRepository,
            dateTimeKit = dateTimeKit,
            navigationKit = navigationKit,
            logKit = fakeLogKit,
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
    fun saveBarcode() = runTestWithTimeout {
        val barcode = Barcode(
            source = BarcodeSource.CREATED,
            format = BarcodeFormat.QrCode.value,
            id = 1,
            timestamp = 1680155040000L,
            name = "Test BarcodeDomainModel",
            value = "test-value",
        )

        homeScreenViewModel.saveBarcode(
            barcode = barcode,
        ).join()

        fakeBarcodeDao.fakeBarcodeEntities[0].asExternalModel().shouldBe(
            expected = barcode,
        )
    }

    @Test
    fun deleteBarcodes() = runTestWithTimeout {
        val barcode1 = Barcode(
            source = BarcodeSource.CREATED,
            format = BarcodeFormat.QrCode.value,
            id = 1,
            timestamp = 1680155040000L,
            name = "Test BarcodeDomainModel 1",
            value = "test-value-1",
        )
        val barcode2 = Barcode(
            source = BarcodeSource.CREATED,
            format = BarcodeFormat.QrCode.value,
            id = 2,
            timestamp = 1680155040000L,
            name = "Test BarcodeDomainModel 2",
            value = "test-value-2",
        )
        val barcodes = listOf(
            barcode1,
            barcode2
        )

        homeScreenViewModel.saveBarcode(
            barcode = barcode1,
        ).join()
        homeScreenViewModel.saveBarcode(
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
