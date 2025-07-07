package com.makeappssimple.abhimanyu.barcodes.android.feature.settings.credits.viewmodel

import com.makeappssimple.abhimanyu.barcodes.android.core.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.barcodes.android.core.navigation.NavigationKit
import kotlinx.coroutines.Job

internal class CreditsScreenUIStateDelegateImpl(
    private val navigationKit: NavigationKit,
    private val screenUICommonState: ScreenUICommonState,
) : CreditsScreenUIStateDelegate, ScreenUICommonState by screenUICommonState {
    // region state events
    override fun navigateUp(): Job {
        return navigationKit.navigateUp()
    }

    override fun navigateToWebViewScreen(
        url: String,
    ): Job {
        return navigationKit.navigateToWebViewScreen(
            url = url,
        )
    }
    // endregion
}
