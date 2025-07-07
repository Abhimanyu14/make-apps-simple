package com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.event

import com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.state.WebViewScreenUIStateEvents

internal class WebViewScreenUIEventHandler internal constructor(
    private val uiStateEvents: WebViewScreenUIStateEvents,
) {
    fun handleUIEvent(
        uiEvent: WebViewScreenUIEvent,
    ) {
        when (uiEvent) {
            is WebViewScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is WebViewScreenUIEvent.OnPageFinished -> {
                uiStateEvents.updateScreenTitle(uiEvent.screenTitle)
            }
        }
    }
}
