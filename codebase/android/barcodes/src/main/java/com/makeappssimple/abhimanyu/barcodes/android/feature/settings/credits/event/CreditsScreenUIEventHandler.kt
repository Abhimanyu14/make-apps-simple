package com.makeappssimple.abhimanyu.barcodes.android.feature.settings.credits.event

import com.makeappssimple.abhimanyu.barcodes.android.feature.settings.credits.state.CreditsScreenUIStateEvents

internal class CreditsScreenUIEventHandler internal constructor(
    private val uiStateEvents: CreditsScreenUIStateEvents,
) {
    fun handleUIEvent(
        uiEvent: CreditsScreenUIEvent,
    ) {
        when (uiEvent) {
            is CreditsScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is CreditsScreenUIEvent.OnLinkClick -> {
                uiStateEvents.navigateToWebViewScreen(uiEvent.url)
            }
        }
    }
}
