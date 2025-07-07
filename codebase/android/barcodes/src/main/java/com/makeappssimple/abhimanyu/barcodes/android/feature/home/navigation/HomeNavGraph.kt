package com.makeappssimple.abhimanyu.barcodes.android.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.feature.home.home.screen.HomeScreen

public fun NavGraphBuilder.homeNavGraph() {
    composable(
        route = Screen.Home.route,
    ) {
        HomeScreen()
    }
}
