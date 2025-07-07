package com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.viewmodel

import com.makeappssimple.abhimanyu.barcodes.android.core.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.NavigationKit
import kotlinx.coroutines.Job

internal class WebViewScreenUIStateDelegateImpl(
    private val navigationKit: NavigationKit,
    private val screenUICommonState: ScreenUICommonState,
) : WebViewScreenUIStateDelegate {
    // region UI state
    override var screenTitle: String = ""
    // endregion

    // region state events
    override fun navigateUp(): Job {
        return navigationKit.navigateUp()
    }

    override fun updateScreenTitle(
        updatedScreenTitle: String,
        shouldRefresh: Boolean,
    ): Job? {
        screenTitle = updatedScreenTitle
        if (shouldRefresh) {
            return screenUICommonState.refresh()
        }
        return null
    }
    // endregion
}
