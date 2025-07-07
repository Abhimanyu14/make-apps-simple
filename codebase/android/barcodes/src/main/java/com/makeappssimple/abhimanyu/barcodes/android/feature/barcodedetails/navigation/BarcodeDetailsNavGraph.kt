package com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.barcodes.android.feature.barcodedetails.barcodedetails.screen.BarcodeDetailsScreen

public fun NavGraphBuilder.barcodeDetailsNavGraph() {
    composable(
        route = "${Screen.BarcodeDetails.route}/{${NavigationArguments.BARCODE_ID}}",
        arguments = listOf(
            navArgument(NavigationArguments.BARCODE_ID) {
                type = NavType.IntType
            },
        ),
    ) {
        BarcodeDetailsScreen()
    }
}
