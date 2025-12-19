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

package com.makeappssimple.abhimanyu.barcodes.android.common.presentation.navigation

import com.makeappssimple.abhimanyu.common.core.uri_encoder.UriEncoder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

internal class NavigationKitImpl(
    private val coroutineScope: CoroutineScope,
    private val uriEncoder: UriEncoder,
) : NavigationKit {
    private val _command: MutableSharedFlow<NavigationCommand> =
        MutableSharedFlow()
    override val command: SharedFlow<NavigationCommand> = _command

    override fun navigateToBarcodeDetailsScreen(
        barcodeId: Int,
    ): Job {
        return navigate(
            navigationCommand = BarcodesNavigationDirections.BarcodeDetails(
                barcodeId = barcodeId,
            )
        )
    }

    override fun navigateToCreateBarcodeScreen(
        barcodeId: Int?,
    ): Job {
        return navigate(
            navigationCommand = BarcodesNavigationDirections.CreateBarcode(
                barcodeId = barcodeId,
            )
        )
    }

    override fun navigateToCreditsScreen(): Job {
        return navigate(
            navigationCommand = BarcodesNavigationDirections.Credits
        )
    }

    override fun navigateToHomeScreen(): Job {
        return navigate(
            navigationCommand = BarcodesNavigationDirections.Home
        )
    }

    override fun navigateToScanBarcodeScreen(): Job {
        return navigate(
            navigationCommand = BarcodesNavigationDirections.ScanBarcode
        )
    }

    override fun navigateToSettingsScreen(): Job {
        return navigate(
            navigationCommand = BarcodesNavigationDirections.Settings
        )
    }

    override fun navigateUp(): Job {
        return navigate(
            navigationCommand = BarcodesNavigationDirections.NavigateUp
        )
    }

    override fun navigateToWebViewScreen(
        url: String,
    ): Job {
        return navigate(
            navigationCommand = BarcodesNavigationDirections.WebView(
                url = uriEncoder.encode(
                    string = url,
                ),
            )
        )
    }

    private fun navigate(
        navigationCommand: NavigationCommand,
    ): Job {
        return coroutineScope.launch {
            _command.emit(navigationCommand)
        }
    }
}
