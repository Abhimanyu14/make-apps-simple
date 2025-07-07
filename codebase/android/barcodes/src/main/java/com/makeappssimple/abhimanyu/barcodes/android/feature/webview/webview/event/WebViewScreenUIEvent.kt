package com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.event

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIEvent

@Immutable
internal sealed class WebViewScreenUIEvent : ScreenUIEvent {
    data object OnTopAppBarNavigationButtonClick : WebViewScreenUIEvent()

    data class OnPageFinished(
        val screenTitle: String,
    ) : WebViewScreenUIEvent()
}
