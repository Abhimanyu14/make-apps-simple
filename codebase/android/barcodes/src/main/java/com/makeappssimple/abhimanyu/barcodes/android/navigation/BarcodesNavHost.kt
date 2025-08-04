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

package com.makeappssimple.abhimanyu.barcodes.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.navigation.barcodeDetailsNavGraph
import com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.navigation.createBarcodeNavGraph
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.navigation.homeNavGraph
import com.makeappssimple.abhimanyu.barcodes.android.feature.scanbarcode.navigation.scanBarcodeNavGraph
import com.makeappssimple.abhimanyu.barcodes.android.feature.settings.navigation.settingsNavGraph
import com.makeappssimple.abhimanyu.barcodes.android.feature.webview.navigation.webViewNavGraph

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
