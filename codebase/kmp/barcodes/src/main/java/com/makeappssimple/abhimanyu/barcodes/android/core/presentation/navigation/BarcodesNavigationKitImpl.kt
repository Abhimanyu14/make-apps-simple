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

import com.makeappssimple.abhimanyu.common.uri_encoder.UriEncoder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single

@Single(
    binds = [
        BarcodesNavigationKit::class,
    ],
)
internal class BarcodesNavigationKitImpl(
    private val coroutineScope: CoroutineScope,
    private val uriEncoder: UriEncoder,
) : BarcodesNavigationKit {
    private val _command: MutableSharedFlow<BarcodesNavigationCommand> =
        MutableSharedFlow()
    override val command: SharedFlow<BarcodesNavigationCommand> = _command

    override fun navigateToBarcodeDetailsScreen(
        barcodeId: Int,
    ): Job {
        return navigate(
            barcodesNavigationCommand = BarcodesNavigationDirections.BarcodeDetails(
                barcodeId = barcodeId,
            )
        )
    }

    override fun navigateToCreateBarcodeScreen(
        barcodeId: Int?,
    ): Job {
        return navigate(
            barcodesNavigationCommand = BarcodesNavigationDirections.CreateBarcode(
                barcodeId = barcodeId,
            )
        )
    }

    override fun navigateToCreditsScreen(): Job {
        return navigate(
            barcodesNavigationCommand = BarcodesNavigationDirections.Credits
        )
    }

    override fun navigateToHomeScreen(): Job {
        return navigate(
            barcodesNavigationCommand = BarcodesNavigationDirections.Home
        )
    }

    override fun navigateToScanBarcodeScreen(): Job {
        return navigate(
            barcodesNavigationCommand = BarcodesNavigationDirections.ScanBarcode
        )
    }

    override fun navigateToSettingsScreen(): Job {
        return navigate(
            barcodesNavigationCommand = BarcodesNavigationDirections.Settings
        )
    }

    override fun navigateUp(): Job {
        return navigate(
            barcodesNavigationCommand = BarcodesNavigationDirections.NavigateUp
        )
    }

    override fun navigateToWebViewScreen(
        url: String,
    ): Job {
        return navigate(
            barcodesNavigationCommand = BarcodesNavigationDirections.WebView(
                url = uriEncoder.encode(
                    string = url,
                ),
            )
        )
    }

    private fun navigate(
        barcodesNavigationCommand: BarcodesNavigationCommand,
    ): Job {
        return coroutineScope.launch {
            _command.emit(
                value = barcodesNavigationCommand,
            )
        }
    }
}
