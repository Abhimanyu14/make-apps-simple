package com.makeappssimple.abhimanyu.barcodes.android.feature.settings.credits.viewmodel

import kotlinx.coroutines.Job

internal interface CreditsScreenUIStateDelegate {
    // region state events
    fun navigateUp(): Job

    fun navigateToWebViewScreen(
        url: String,
    ): Job
    // endregion
}
