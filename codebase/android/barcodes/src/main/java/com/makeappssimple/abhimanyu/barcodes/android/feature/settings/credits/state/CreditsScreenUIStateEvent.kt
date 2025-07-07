package com.makeappssimple.abhimanyu.barcodes.android.feature.settings.credits.state

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.barcodes.android.core.ui.base.ScreenUIStateEvents

@Immutable
@Keep
internal data class CreditsScreenUIStateEvents(
    val navigateUp: () -> Unit = {},
    val navigateToWebViewScreen: (url: String) -> Unit = {},
) : ScreenUIStateEvents
