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

internal const val DEEPLINK_BROWSER_BASE_URL: String =
    "https://www.makeappssimple.barcodes.com"
internal const val DEEPLINK_BASE_URL: String = "makeappssimple://barcodes"

internal sealed class BarcodesScreen(
    val route: String,
) {
    data object BarcodeDetails : BarcodesScreen(
        route = "barcode_details",
    )

    data object CreateBarcode : BarcodesScreen(
        route = "create_barcode",
    )

    data object Credits : BarcodesScreen(
        route = "credits",
    )

    data object Home : BarcodesScreen(
        route = "home",
    )

    data object ScanBarcode : BarcodesScreen(
        route = "scan_barcode",
    )

    data object Settings : BarcodesScreen(
        route = "settings",
    )

    data object WebView : BarcodesScreen(
        route = "web_view",
    )
}
