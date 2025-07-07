package com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.viewmodel

import kotlinx.coroutines.Job

internal interface WebViewScreenUIStateDelegate {
    // region UI state
    val screenTitle: String
    // endregion

    // region state events
    fun navigateUp(): Job

    fun updateScreenTitle(
        updatedScreenTitle: String,
        shouldRefresh: Boolean = true,
    ): Job?
    // endregion
}
