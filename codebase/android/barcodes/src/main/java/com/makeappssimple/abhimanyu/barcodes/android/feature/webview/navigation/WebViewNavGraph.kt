package com.makeappssimple.abhimanyu.barcodes.android.feature.webview.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.constants.DeeplinkUrl
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.screen.WebViewScreen

public fun NavGraphBuilder.webViewNavGraph() {
    composable(
        route = "${Screen.WebView.route}/{${NavigationArguments.URL}}",
        arguments = listOf(
            navArgument(NavigationArguments.URL) {
                type = NavType.StringType
            },
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BROWSER_BASE_URL}/${Screen.WebView.route}/{${NavigationArguments.URL}}"
            },
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BASE_URL}/${Screen.WebView.route}/{${NavigationArguments.URL}}"
            },
        ),
    ) {
        WebViewScreen()
    }
}
