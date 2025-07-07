package com.makeappssimple.abhimanyu.barcodes.android.feature.scanbarcode.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.DEEPLINK_BASE_URL
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.DEEPLINK_BROWSER_BASE_URL
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.barcodes.android.feature.scanbarcode.scanbarcode.screen.ScanBarcodeScreen

public fun NavGraphBuilder.scanBarcodeNavGraph() {
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
