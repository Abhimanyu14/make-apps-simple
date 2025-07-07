package com.makeappssimple.abhimanyu.barcodes.android.feature.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.barcodes.android.feature.settings.credits.screen.CreditsScreen
import com.makeappssimple.abhimanyu.barcodes.android.feature.settings.settings.screen.SettingsScreen

public fun NavGraphBuilder.settingsNavGraph() {
    composable(
        route = Screen.Credits.route,
    ) {
        CreditsScreen()
    }

    composable(
        route = Screen.Settings.route,
    ) {
        SettingsScreen()
    }
}
