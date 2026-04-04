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

package com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation

import app.cash.turbine.test
import com.makeappssimple.abhimanyu.common.uri_encoder.UriEncoder
import com.makeappssimple.abhimanyu.common.uri_encoder.fake.FakeUriEncoderImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

internal class BarcodesNavigationKitTest {
    private val testCoroutineDispatcher = StandardTestDispatcher()
    private val testCoroutineScope = CoroutineScope(
        context = testCoroutineDispatcher,
    )

    private lateinit var barcodesNavigationKit: BarcodesNavigationKit
    private lateinit var uriEncoder: UriEncoder

    @Before
    fun setUp() {
        barcodesNavigationKit = BarcodesNavigationKitImpl(
            coroutineScope = testCoroutineScope,
            uriEncoder = uriEncoder,
        )
        uriEncoder = FakeUriEncoderImpl()
    }

    @Test
    fun navigateToBarcodeDetailsScreenTest() = runTestWithTimeout {
        val barcodeId = 1
        barcodesNavigationKit.command.test {
            barcodesNavigationKit.navigateToBarcodeDetailsScreen(
                barcodeId = barcodeId,
            )

            Assert.assertEquals(
                awaitItem(),
                BarcodesNavigationDirections.BarcodeDetails(
                    barcodeId = barcodeId,
                ),
            )
        }
    }

    @Test
    fun navigateToCreateBarcodeScreen_barcodeIdIsNull() = runTestWithTimeout {
        val barcodeId = null
        barcodesNavigationKit.command.test {
            barcodesNavigationKit.navigateToCreateBarcodeScreen(
                barcodeId = barcodeId,
            )

            Assert.assertEquals(
                awaitItem(),
                BarcodesNavigationDirections.CreateBarcode(
                    barcodeId = barcodeId,
                ),
            )
        }
    }

    @Test
    fun navigateToCreateBarcodeScreen_barcodeIdIsNotNull() =
        runTestWithTimeout {
            val barcodeId = 1
            barcodesNavigationKit.command.test {
                barcodesNavigationKit.navigateToCreateBarcodeScreen(
                    barcodeId = barcodeId,
                )

                Assert.assertEquals(
                    awaitItem(),
                    BarcodesNavigationDirections.CreateBarcode(
                        barcodeId = barcodeId,
                    ),
                )
            }
        }

    @Test
    fun navigateToCreditsScreenTest() = runTestWithTimeout {
        barcodesNavigationKit.command.test {
            barcodesNavigationKit.navigateToCreditsScreen()

            Assert.assertEquals(
                awaitItem(),
                BarcodesNavigationDirections.Credits,
            )
        }
    }

    @Test
    fun navigateToHomeScreenTest() = runTestWithTimeout {
        barcodesNavigationKit.command.test {
            barcodesNavigationKit.navigateToHomeScreen()

            Assert.assertEquals(
                awaitItem(),
                BarcodesNavigationDirections.Home,
            )
        }
    }

    @Test
    fun navigateToScanBarcodeScreenTest() = runTestWithTimeout {
        barcodesNavigationKit.command.test {
            barcodesNavigationKit.navigateToScanBarcodeScreen()

            Assert.assertEquals(
                awaitItem(),
                BarcodesNavigationDirections.ScanBarcode,
            )
        }
    }

    @Test
    fun navigateToSettingsScreenTest() = runTestWithTimeout {
        barcodesNavigationKit.command.test {
            barcodesNavigationKit.navigateToSettingsScreen()

            Assert.assertEquals(
                awaitItem(),
                BarcodesNavigationDirections.Settings,
            )
        }
    }

    @Test
    fun navigateUpTest() = runTestWithTimeout {
        barcodesNavigationKit.command.test {
            barcodesNavigationKit.navigateUp()

            Assert.assertEquals(
                awaitItem(),
                BarcodesNavigationDirections.NavigateUp,
            )
        }
    }

    @Test
    fun navigateToWebViewScreenTest() = runTestWithTimeout {
        val url = "testUrl"
        barcodesNavigationKit.command.test {
            barcodesNavigationKit.navigateToWebViewScreen(
                url = url,
            )

            Assert.assertEquals(
                awaitItem(),
                BarcodesNavigationDirections.WebView(
                    url = url,
                ),
            )
        }
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
