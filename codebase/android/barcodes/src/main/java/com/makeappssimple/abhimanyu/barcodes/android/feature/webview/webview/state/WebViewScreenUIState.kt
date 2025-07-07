package com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIState

@Stable
internal class WebViewScreenUIState(
    val url: String = "",
    val screenTitle: String = "",
) : ScreenUIState
