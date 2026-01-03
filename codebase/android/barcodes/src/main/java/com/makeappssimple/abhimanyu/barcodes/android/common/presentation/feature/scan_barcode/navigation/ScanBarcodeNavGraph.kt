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

package com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.scan_barcode.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.navigation.DEEPLINK_BASE_URL
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.navigation.DEEPLINK_BROWSER_BASE_URL
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.barcodes.android.common.ui.feature.scan_barcode.scan_barcode.screen.ScanBarcodeScreen

internal fun NavGraphBuilder.scanBarcodeNavGraph() {
    composable(
        route = "${Screen.ScanBarcode.route}?${NavigationArguments.DEEPLINK}={${NavigationArguments.DEEPLINK}}",
        arguments = listOf(
            navArgument(NavigationArguments.DEEPLINK) {
                type = NavType.BoolType
                defaultValue = false
            },
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern =
                    "$DEEPLINK_BROWSER_BASE_URL/${Screen.ScanBarcode.route}/?${NavigationArguments.DEEPLINK}={${NavigationArguments.DEEPLINK}}"
            },
            navDeepLink {
                uriPattern =
                    "$DEEPLINK_BASE_URL/${Screen.ScanBarcode.route}/?${NavigationArguments.DEEPLINK}={${NavigationArguments.DEEPLINK}}"
            },
        ),
    ) {
        ScanBarcodeScreen()
    }
}
