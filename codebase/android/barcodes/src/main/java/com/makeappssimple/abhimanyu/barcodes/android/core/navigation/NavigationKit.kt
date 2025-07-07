package com.makeappssimple.abhimanyu.barcodes.android.core.navigation

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

public interface NavigationKit {
    public val command: SharedFlow<NavigationCommand>

    public fun navigateToBarcodeDetailsScreen(
        barcodeId: Int,
    ): Job

    public fun navigateToCreateBarcodeScreen(
        barcodeId: Int? = null,
    ): Job

    public fun navigateToCreditsScreen(): Job

    public fun navigateToHomeScreen(): Job

    public fun navigateToScanBarcodeScreen(): Job

    public fun navigateToSettingsScreen(): Job

    public fun navigateUp(): Job

    public fun navigateUpAndNavigateToBarcodeDetailsScreen(
        barcodeId: Int,
    ): Job

    public fun navigateToWebViewScreen(
        url: String,
    ): Job
}
