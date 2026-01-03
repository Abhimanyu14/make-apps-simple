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

package com.makeappssimple.abhimanyu.barcodes.android.common.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.barcode_details.navigation.barcodeDetailsNavGraph
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.create_barcode.navigation.createBarcodeNavGraph
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.home.navigation.homeNavGraph
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.scan_barcode.navigation.scanBarcodeNavGraph
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.settings.navigation.settingsNavGraph
import com.makeappssimple.abhimanyu.barcodes.android.common.presentation.feature.web_view.navigation.webViewNavGraph

@Composable
internal fun BarcodesNavHost(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route,
    ) {
        barcodeDetailsNavGraph()
        createBarcodeNavGraph()
        homeNavGraph()
        scanBarcodeNavGraph()
        settingsNavGraph()
        webViewNavGraph()
    }
}
