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

package com.makeappssimple.abhimanyu.barcodes.android.features.web_view.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation.BarcodesScreen
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation.constants.DeeplinkUrl
import com.makeappssimple.abhimanyu.barcodes.android.core.presentation.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.barcodes.android.features.web_view.presentation.web_view.view_model.WebViewScreenViewModel
import com.makeappssimple.abhimanyu.barcodes.android.features.web_view.ui.web_view.screen.WebViewScreen
import org.koin.compose.viewmodel.koinViewModel

internal fun NavGraphBuilder.webViewNavGraph() {
    composable(
        route = "${BarcodesScreen.WebView.route}/{${NavigationArguments.URL}}",
        arguments = listOf(
            navArgument(NavigationArguments.URL) {
                type = NavType.StringType
            },
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BROWSER_BASE_URL}/${BarcodesScreen.WebView.route}/{${NavigationArguments.URL}}"
            },
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BASE_URL}/${BarcodesScreen.WebView.route}/{${NavigationArguments.URL}}"
            },
        ),
    ) {
        WebViewScreen(
            screenViewModel = koinViewModel<WebViewScreenViewModel>(),
        )
    }
}
