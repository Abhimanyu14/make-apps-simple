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

import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation.constants.NavigationArguments

internal object BarcodesNavigationDirections {
    // Default
    object Default : BarcodesNavigationCommand {
        override val barcodesCommand = BarcodesCommand.NOOP
        override val barcodesScreen = ""
        override val destination = ""
    }

    // Navigate up
    object NavigateUp : BarcodesNavigationCommand {
        override val barcodesCommand = BarcodesCommand.NAVIGATE_UP
        override val barcodesScreen = ""
        override val destination = ""
    }

    // Clear backstack
    object ClearBackstack : BarcodesNavigationCommand {
        override val barcodesCommand =
            BarcodesCommand.CLEAR_BACKSTACK_AND_NAVIGATE
        override val barcodesScreen = ""
        override val destination = ""
    }

    // Clear till root
    object ClearTillRoot : BarcodesNavigationCommand {
        override val barcodesCommand = BarcodesCommand.CLEAR_TILL_ROOT
        override val barcodesScreen = ""
        override val destination = ""
    }

    // App specific
    data class BarcodeDetails(
        private val barcodeId: Int,
    ) : BarcodesNavigationCommand {
        override val barcodesCommand = BarcodesCommand.NAVIGATE
        override val barcodesScreen = BarcodesScreen.BarcodeDetails.route
        override val destination =
            "${BarcodesScreen.BarcodeDetails.route}/${barcodeId}"
    }

    data class CreateBarcode(
        private val barcodeId: Int?,
    ) : BarcodesNavigationCommand {
        override val barcodesCommand = BarcodesCommand.NAVIGATE
        override val barcodesScreen = BarcodesScreen.CreateBarcode.route
        override val destination =
            "${BarcodesScreen.CreateBarcode.route}?${NavigationArguments.BARCODE_ID}=${barcodeId}"
    }

    object Credits : BarcodesNavigationCommand {
        override val barcodesCommand = BarcodesCommand.NAVIGATE
        override val barcodesScreen = BarcodesScreen.Credits.route
        override val destination = BarcodesScreen.Credits.route
    }

    object Home : BarcodesNavigationCommand {
        override val barcodesCommand = BarcodesCommand.NAVIGATE
        override val barcodesScreen = BarcodesScreen.Home.route
        override val destination = BarcodesScreen.Home.route
    }

    object ScanBarcode : BarcodesNavigationCommand {
        override val barcodesCommand = BarcodesCommand.NAVIGATE
        override val barcodesScreen = BarcodesScreen.ScanBarcode.route
        override val destination = BarcodesScreen.ScanBarcode.route
    }

    object Settings : BarcodesNavigationCommand {
        override val barcodesCommand = BarcodesCommand.NAVIGATE
        override val barcodesScreen = BarcodesScreen.Settings.route
        override val destination = BarcodesScreen.Settings.route
    }

    data class WebView(
        private val url: String,
    ) : BarcodesNavigationCommand {
        override val barcodesCommand = BarcodesCommand.NAVIGATE
        override val barcodesScreen = BarcodesScreen.WebView.route
        override val destination = "${BarcodesScreen.WebView.route}/${url}"
    }
}
