package com.makeappssimple.abhimanyu.barcodes.android.feature.webview.webview.state

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIStateEvents

@Immutable
@Keep
internal data class WebViewScreenUIStateEvents(
    val navigateUp: () -> Unit = {},
    val updateScreenTitle: (updatedScreenTitle: String) -> Unit = {},
) : ScreenUIStateEvents
