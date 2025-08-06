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

package com.makeappssimple.abhimanyu.barcodes.android.feature.create_barcode.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.barcodes.android.feature.create_barcode.create_barcode.screen.CreateBarcodeScreen

internal fun NavGraphBuilder.createBarcodeNavGraph() {
    composable(
        route = "${Screen.CreateBarcode.route}?${NavigationArguments.BARCODE_ID}={${NavigationArguments.BARCODE_ID}}",
        arguments = listOf(
            navArgument(NavigationArguments.BARCODE_ID) {
                // Note: Int cannot be nullable, so nullable string
                type = NavType.StringType
                nullable = true
            },
        ),
    ) {
        CreateBarcodeScreen()
    }
}
