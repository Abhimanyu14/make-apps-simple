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

package com.makeappssimple.abhimanyu.barcodes.android.core.navigation

import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.constants.NavigationArguments.BARCODE_ID

internal object MyNavigationDirections {
    // Default
    object Default : NavigationCommand {
        override val command = Command.NOOP
        override val destination = ""
        override val screen = ""
    }

    // Navigate up
    object NavigateUp : NavigationCommand {
        override val command = Command.NAVIGATE_UP
        override val destination = ""
        override val screen = ""
    }

    // Clear backstack
    object ClearBackstack : NavigationCommand {
        override val command = Command.CLEAR_BACKSTACK_AND_NAVIGATE
        override val destination = ""
        override val screen = ""
    }

    // Clear till root
    object ClearTillRoot : NavigationCommand {
        override val command = Command.CLEAR_TILL_ROOT
        override val destination = ""
        override val screen = ""
    }

    // App specific
    data class BarcodeDetails(
        private val barcodeId: Int,
    ) : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = "${Screen.BarcodeDetails.route}/${barcodeId}"
        override val screen = Screen.BarcodeDetails.route
    }

    data class CreateBarcode(
        private val barcodeId: Int?,
    ) : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination =
            "${Screen.CreateBarcode.route}?${BARCODE_ID}=${barcodeId}"
        override val screen = Screen.CreateBarcode.route
    }

    object Credits : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = Screen.Credits.route
        override val screen = Screen.Credits.route
    }

    object Home : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = Screen.Home.route
        override val screen = Screen.Home.route
    }

    object ScanBarcode : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = Screen.ScanBarcode.route
        override val screen = Screen.ScanBarcode.route
    }

    object Settings : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = Screen.Settings.route
        override val screen = Screen.Settings.route
    }

    data class WebView(
        private val url: String,
    ) : NavigationCommand {
        override val command = Command.NAVIGATE
        override val destination = "${Screen.WebView.route}/${url}"
        override val screen = Screen.WebView.route
    }
}
