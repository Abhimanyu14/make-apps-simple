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

public const val DEEPLINK_BROWSER_BASE_URL: String =
    "https://www.makeappssimple.barcodes.com"
public const val DEEPLINK_BASE_URL: String = "makeappssimple://barcodes"

public sealed class Screen(
    public val route: String,
) {
    public data object BarcodeDetails : Screen(
        route = "barcode_details",
    )

    public data object CreateBarcode : Screen(
        route = "create_barcode",
    )

    public data object Credits : Screen(
        route = "credits",
    )

    public data object Home : Screen(
        route = "home",
    )

    public data object ScanBarcode : Screen(
        route = "scan_barcode",
    )

    public data object Settings : Screen(
        route = "settings",
    )

    public data object WebView : Screen(
        route = "web_view",
    )
}
