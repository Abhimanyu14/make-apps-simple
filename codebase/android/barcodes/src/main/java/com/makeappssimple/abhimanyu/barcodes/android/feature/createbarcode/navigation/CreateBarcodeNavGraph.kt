package com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.constants.NavigationArguments
import com.makeappssimple.abhimanyu.barcodes.android.feature.createbarcode.createbarcode.screen.CreateBarcodeScreen

public fun NavGraphBuilder.createBarcodeNavGraph() {
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
